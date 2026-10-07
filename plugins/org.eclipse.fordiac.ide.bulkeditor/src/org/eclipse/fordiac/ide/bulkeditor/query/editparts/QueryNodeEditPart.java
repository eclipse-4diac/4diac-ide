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

import java.util.List;
import java.util.Objects;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.commands.ChangeQueryFeatureCommand;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryAttributeDeclarationNodeFigure;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryConstraintNodeFigure;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryNodeFigure;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryPlaceNodeFigure;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryPlaceholderNodeFigure;
import org.eclipse.fordiac.ide.bulkeditor.query.policies.DeleteQueryNodeEditPolicy;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.SelectEditPartTracker;

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
		final QueryNodeFigure figure = createNodeFigure();
		figure.setFeatureChangeHandler(this::changeFeature);
		return figure;
	}

	private QueryNodeFigure createNodeFigure() {
		final EObject element = getModel();
		final FigureCanvas canvas = (FigureCanvas) getViewer().getControl();
		if (QueryModelHelper.isPlace(element)) {
			return new QueryPlaceNodeFigure(element);
		}
		if (QueryModelHelper.isConstraint(element)) {
			return new QueryConstraintNodeFigure(element, canvas);
		}
		if (QueryModelHelper.isPlaceholder(element)) {
			return new QueryPlaceholderNodeFigure(element, canvas);
		}
		if (QueryModelHelper.isAttributeDeclaration(element)) {
			return new QueryAttributeDeclarationNodeFigure(element, canvas, project);
		}
		return new QueryNodeFigure(element);
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteQueryNodeEditPolicy());
	}

	@Override
	protected void refreshVisuals() {
		getFigure().refresh();
	}

	@Override
	public DragTracker getDragTracker(final Request request) {
		// the nodes are placed by the query layout and can only be selected
		return new SelectEditPartTracker(this);
	}

	@Override
	public void performRequest(final Request request) {
		if (RequestConstants.REQ_OPEN.equals(request.getType())) {
			toggleCollapsed();
		} else {
			super.performRequest(request);
		}
	}

	@Override
	protected List<QueryConnection> getModelSourceConnections() {
		return getDiagram().getVisibleChildren(getModel()).stream().map(child -> new QueryConnection(getModel(), child))
				.toList();
	}

	@Override
	protected List<QueryConnection> getModelTargetConnections() {
		final EObject container = getModel().eContainer();
		return container != null ? List.of(new QueryConnection(container, getModel())) : List.of();
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

	private void toggleCollapsed() {
		if (QueryModelHelper.hasCollapsibleChildren(getModel())) {
			getDiagram().toggleCollapsed(getModel());
			getParent().refresh();
			refreshSourceConnections();
		}
	}
}
