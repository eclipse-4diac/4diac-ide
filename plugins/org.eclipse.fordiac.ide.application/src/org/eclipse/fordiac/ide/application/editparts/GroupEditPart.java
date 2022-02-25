/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.figures.InstanceCommentFigure;
import org.eclipse.fordiac.ide.gef.editparts.AbstractPositionableElementEditPart;
import org.eclipse.fordiac.ide.gef.editparts.FigureCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.TextDirectEditManager;
import org.eclipse.fordiac.ide.gef.figures.BorderedRoundedRectangle;
import org.eclipse.fordiac.ide.gef.figures.RoundedRectangleShadowBorder;
import org.eclipse.fordiac.ide.gef.policies.AbstractViewRenameEditPolicy;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteGroupCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class GroupEditPart extends AbstractPositionableElementEditPart {
	private GroupContentNetwork groupContents;
	private InstanceCommentFigure commentFigure;

	private class GroupCommentRenameEditPolicy extends AbstractViewRenameEditPolicy {
		@Override
		protected Command getDirectEditCommand(final DirectEditRequest request) {
			if (getHost() instanceof GroupEditPart) {
				final String str = (String) request.getCellEditor().getValue();
				if (!InstanceCommentFigure.EMPTY_COMMENT.equals(str)) {
					return new ChangeCommentCommand(((GroupEditPart) getHost()).getModel(), str);
				}
			}
			return null;
		}

		@Override
		protected void showCurrentEditValue(final DirectEditRequest request) {
			final String value = (String) request.getCellEditor().getValue();
			commentFigure.setText(value);
		}
	}

	@Override
	public Group getModel() {
		return (Group) super.getModel();
	}

	@Override
	protected PositionableElement getPositionableElement() {
		return getModel();
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel();
	}

	@Override
	public Label getNameLabel() {
		// we don't have a name label in groups
		return null;
	}

	@Override
	protected void refreshName() {
		// as we don't have a name label we don't want to do anything here
	}

	@Override
	protected Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				final Object feature = notification.getFeature();
				if (LibraryElementPackage.eINSTANCE.getGroup_GroupElements().equals(feature)) {
					// group elements changed tell the parent that fbs may now be at different places
					getParent().refresh();
				} else if (LibraryElementPackage.eINSTANCE.getGroup_Width().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getGroup_Height().equals(feature)) {
					refreshPosition();
				}
			}
		};
	}

	@Override
	protected IPropertyChangeListener getPreferenceChangeListener() {
		// for now we don't need a preference change listener
		return null;
	}

	@Override
	protected IFigure createFigureForModel() {
		final RoundedRectangle mainFigure = new BorderedRoundedRectangle();
		mainFigure.setOutline(false);
		mainFigure.setCornerDimensions(new Dimension(DiagramPreferences.CORNER_DIM, DiagramPreferences.CORNER_DIM));
		mainFigure.setFillXOR(false);
		mainFigure.setOpaque(false);
		mainFigure.setBorder(new RoundedRectangleShadowBorder());
		mainFigure.setLayoutManager(new ToolbarLayout());
		commentFigure = new InstanceCommentFigure();
		mainFigure.add(commentFigure);
		refreshComment();
		return mainFigure;
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		super.addChildVisual(childEditPart, -1);
	}

	@Override
	protected List getModelChildren() {
		final List<Object> children = new ArrayList<>(2);
		children.add(getSubappContents());
		return children;
	}

	@Override
	public void performRequest(final Request request) {
		if ((request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN)
				&& (request instanceof SelectionRequest)) {
			// if it is direct edit request and inside of the content area forward request to there so we are creating
			// fbs inside
			final Point location = ((SelectionRequest) request).getLocation().getCopy();
			getFigure().translateToRelative(location);
			final GroupContentEditPart groupContentEP = getGroupContentEP();
			if ((groupContentEP != null) && (groupContentEP.getFigure().getBounds().contains(location))) {
				groupContentEP.performRequest(request);
				return;
			}
		}
		super.performRequest(request);
	}

	@Override
	protected void performDirectEdit() {
		new TextDirectEditManager(this, new FigureCellEditorLocator(commentFigure)) {
			@Override
			protected CellEditor createCellEditorOn(final Composite composite) {
				return new TextCellEditor(composite, SWT.MULTI | SWT.WRAP);
			}

			@Override
			protected void initCellEditor() {
				super.initCellEditor();
				getCellEditor().setValue(getModel().getComment());
			}
		}.show();
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
			@Override
			protected Command createDeleteCommand(final GroupRequest request) {
				return new DeleteGroupCommand((Group) getHost().getModel());
			}
		});
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new GroupCommentRenameEditPolicy());
	}

	@Override
	protected void refreshPosition() {
		if (getParent() != null) {
			final Position position = getModel().getPosition();
			final Point asPoint = position.asPoint();
			final Rectangle bounds = new Rectangle(asPoint, getGroupSize());
			((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
		}
	}

	@Override
	protected void refreshComment() {
		commentFigure.setText(getModel().getComment());
	}

	private Dimension getGroupSize() {
		return new Dimension(getModel().getWidth(), getModel().getHeight());
	}

	private GroupContentEditPart getGroupContentEP() {
		return (GroupContentEditPart) getChildren().stream().filter(GroupContentEditPart.class::isInstance).findAny()
				.orElse(null);
	}

	private GroupContentNetwork getSubappContents() {
		if (null == groupContents) {
			groupContents = new GroupContentNetwork(getModel());
		}
		return groupContents;
	}

}
