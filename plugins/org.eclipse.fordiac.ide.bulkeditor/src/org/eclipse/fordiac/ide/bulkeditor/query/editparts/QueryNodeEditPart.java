/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.query.editparts;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.commands.ChangeQueryFeatureCommand;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryAttributeDeclarationNodeFigure;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryConstraintNodeFigure;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryNodeFigure;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryNodeFigure.EditableValue;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryPlaceNodeFigure;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryPlaceholderNodeFigure;
import org.eclipse.fordiac.ide.bulkeditor.query.policies.DeleteQueryNodeEditPolicy;
import org.eclipse.fordiac.ide.bulkeditor.query.policies.QueryDirectEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.widgets.AttributeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.TypeSelectionProposalProvider;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Handle;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.tools.SelectEditPartTracker;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.swt.SWT;

/** Edit part of a query element shown as node of the query viewer. */
public class QueryNodeEditPart extends AbstractGraphicalEditPart implements NodeEditPart {

	private final IProject project;
	private ConnectionAnchor anchor;

	public QueryNodeEditPart(final IProject project) {
		this.project = project;
	}

	@Override
	public EObject getModel() {
		return (EObject) super.getModel();
	}

	@Override
	public QueryNodeFigure getFigure() {
		return (QueryNodeFigure) super.getFigure();
	}

	@Override
	protected IFigure createFigure() {
		final QueryNodeFigure figure = createNodeFigure(getModel());
		figure.setFeatureChangeHandler(this::changeFeature);
		return figure;
	}

	private static QueryNodeFigure createNodeFigure(final EObject element) {
		if (QueryModelHelper.isPlace(element)) {
			return new QueryPlaceNodeFigure(element);
		}
		if (QueryModelHelper.isConstraint(element)) {
			return new QueryConstraintNodeFigure(element);
		}
		if (QueryModelHelper.isPlaceholder(element)) {
			return new QueryPlaceholderNodeFigure(element);
		}
		if (QueryModelHelper.isAttributeDeclaration(element)) {
			return new QueryAttributeDeclarationNodeFigure(element);
		}
		return new QueryNodeFigure(element);
	}

	@Override
	protected void createEditPolicies() {
		final NonResizableEditPolicy selectionPolicy = new NonResizableEditPolicy() {
			@Override
			protected List<Handle> createSelectionHandles() {
				final List<Handle> list = new ArrayList<>(1);
				list.add(new ModifiedMoveHandle(getHost(), new Insets(2), GefPreferenceConstants.CORNER_DIM));
				return list;
			}
		};
		selectionPolicy.setDragAllowed(false);
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, selectionPolicy);
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteQueryNodeEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new QueryDirectEditPolicy());
	}

	@Override
	protected void refreshVisuals() {
		getFigure().refresh();
	}

	@Override
	public DragTracker getDragTracker(final Request request) {
		// the nodes are placed by the query layout and can only be selected
		return new SelectEditPartTracker(this) {
			@Override
			protected boolean handleButtonUp(final int button) {
				if (button == 1 && isInState(STATE_DRAG) && isPlainClick()
						&& getFigure().getEditableValueAt(getLocation()) != null) {
					// values are edited with a single click, like the toggle buttons are pressed
					performSelection();
					final DirectEditRequest directEditRequest = new DirectEditRequest();
					directEditRequest.setLocation(getLocation());
					performRequest(directEditRequest);
					setState(STATE_TERMINAL);
					return true;
				}
				return super.handleButtonUp(button);
			}

			private boolean isPlainClick() {
				return !getCurrentInput().isModKeyDown(SWT.MOD1) && !getCurrentInput().isShiftKeyDown();
			}
		};
	}

	@Override
	public void performRequest(final Request request) {
		final EditableValue editableValue = getEditableValue(request);
		if (editableValue != null) {
			performDirectEdit(editableValue);
		} else if (RequestConstants.REQ_OPEN.equals(request.getType())) {
			toggleCollapsed();
		} else {
			super.performRequest(request);
		}
	}

	@Override
	protected List<QueryConnection> getModelSourceConnections() {
		// placeholders are shown in rows above the query without connections
		return getDiagram().getVisibleChildren(getModel()).stream()
				.filter(child -> !QueryModelHelper.isPlaceholder(child))
				.map(child -> new QueryConnection(getModel(), child)).toList();
	}

	@Override
	protected List<QueryConnection> getModelTargetConnections() {
		final EObject container = getModel().eContainer();
		if (container == null || QueryModelHelper.isPlaceholder(getModel())) {
			return List.of();
		}
		return List.of(new QueryConnection(container, getModel()));
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		return getAnchor();
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		return getAnchor();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return getAnchor();
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return getAnchor();
	}

	private ConnectionAnchor getAnchor() {
		if (anchor == null) {
			anchor = new ChopboxAnchor(getFigure());
		}
		return anchor;
	}

	private QueryDiagram getDiagram() {
		return (QueryDiagram) getParent().getModel();
	}

	private void changeFeature(final EObject element, final String featureName, final Object value) {
		if (!Objects.equals(QueryModelHelper.getFeatureValue(element, featureName), value)) {
			getViewer().getEditDomain().getCommandStack()
					.execute(new ChangeQueryFeatureCommand(element, featureName, value));
		}
	}

	private EditableValue getEditableValue(final Request request) {
		if ((RequestConstants.REQ_OPEN.equals(request.getType())
				|| RequestConstants.REQ_DIRECT_EDIT.equals(request.getType()))
				&& request instanceof final LocationRequest locationRequest) {
			return getFigure().getEditableValueAt(locationRequest.getLocation());
		}
		return null;
	}

	private void performDirectEdit(final EditableValue editableValue) {
		if (QueryModelHelper.isAttributeDeclaration(editableValue.element())) {
			final IContentProposalProvider proposalProvider = new TypeSelectionProposalProvider(
					() -> TypeLibraryManager.INSTANCE.getTypeLibrary(project),
					AttributeSelectionContentProvider.INSTANCE);
			new QueryDirectEditManager(this, editableValue, proposalProvider).show();
		} else {
			new QueryDirectEditManager(this, editableValue).show();
		}
	}

	private void toggleCollapsed() {
		if (QueryModelHelper.hasCollapsibleChildren(getModel())) {
			getDiagram().toggleCollapsed(getModel());
			getParent().refresh();
			refreshSourceConnections();
		}
	}
}
