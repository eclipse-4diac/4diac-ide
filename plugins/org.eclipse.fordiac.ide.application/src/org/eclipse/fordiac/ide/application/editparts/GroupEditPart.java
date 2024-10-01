/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Fabio Gandolfi - added name label to group
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.figures.GroupFigure;
import org.eclipse.fordiac.ide.application.figures.InstanceCommentFigure;
import org.eclipse.fordiac.ide.application.figures.InstanceNameFigure;
import org.eclipse.fordiac.ide.gef.annotation.AnnotableGraphicalEditPart;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModelEvent;
import org.eclipse.fordiac.ide.gef.editparts.AbstractPositionableElementEditPart;
import org.eclipse.fordiac.ide.gef.editparts.FigureCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.TextDirectEditManager;
import org.eclipse.fordiac.ide.gef.policies.AbstractViewRenameEditPolicy;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteGroupCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
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

public class GroupEditPart extends AbstractPositionableElementEditPart
		implements IContainerEditPart, AnnotableGraphicalEditPart {
	private GroupContentNetwork groupContents;
	private InstanceName instanceName;

	private class GroupCommentRenameEditPolicy extends AbstractViewRenameEditPolicy {
		@Override
		protected Command getDirectEditCommand(final DirectEditRequest request) {
			if (getHost() instanceof final GroupEditPart gEP) {
				final String str = (String) request.getCellEditor().getValue();
				if (!InstanceCommentFigure.EMPTY_COMMENT.equals(str)) {
					return new ResizeGroupOrSubappCommand(getHost(), new ChangeCommentCommand(gEP.getModel(), str));
				}
			}
			return null;
		}

		@Override
		protected void showCurrentEditValue(final DirectEditRequest request) {
			final String value = (String) request.getCellEditor().getValue();
			getFigure().getCommentFigure().setText(value);
		}

		@Override
		protected void revertOldEditValue(final DirectEditRequest request) {
			refreshComment();
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
	public Rectangle getCommentBounds() {
		if (getFigure().getCommentFigure() != null) {
			return getFigure().getCommentFigure().getBounds();
		}
		return null;
	}

	@Override
	protected void refreshSourceConnections() {
		getContentEP().getChildren().forEach(ep -> {
			if (ep instanceof final AbstractFBNElementEditPart fbEp) {
				fbEp.getChildren().forEach(pinEp -> {
					if (pinEp instanceof InterfaceEditPartForFBNetwork) {
						pinEp.getSourceConnections().forEach(ConnectionEditPart::refresh);
					}
				});
			}
		});
	}

	@Override
	protected void refreshTargetConnections() {
		getContentEP().getChildren().forEach(ep -> {
			if (ep instanceof final AbstractFBNElementEditPart fbEp) {
				fbEp.getChildren().forEach(pinEp -> {
					if (pinEp instanceof InterfaceEditPartForFBNetwork) {
						pinEp.getTargetConnections().forEach(ConnectionEditPart::refresh);
					}
				});
			}
		});
	}

	@Override
	protected Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				final Object feature = notification.getFeature();
				if (LibraryElementPackage.eINSTANCE.getGroup_GroupElements().equals(feature)
						|| LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)) { // Tooltip stuff
					// group elements changed tell the parent that fbs may now be at different
					// places
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
		final GroupFigure groupFigure = new GroupFigure();
		groupFigure.getCommentFigure().setText(getModel().getComment());

		return groupFigure;
	}

	@Override
	public GroupFigure getFigure() {
		return (GroupFigure) super.getFigure();
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();

		if (child instanceof InstanceNameFigure) {
			getFigure().getNameFigure().add(child,
					new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.GRAB_HORIZONTAL));
		} else {
			final GridData layoutConstraint = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
					| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
			getFigure().getMainFigure().add(child, layoutConstraint);
		}
	}

	@Override
	protected List<Object> getModelChildren() {
		final List<Object> children = new ArrayList<>(3);
		if (instanceName == null) {
			instanceName = new InstanceName(getModel());
		}
		children.add(instanceName);
		children.add(getGroupContents());
		return children;
	}

	@Override
	public void performRequest(final Request request) {
		if (((request.getType() == RequestConstants.REQ_DIRECT_EDIT)
				|| (request.getType() == RequestConstants.REQ_OPEN))
				&& (request instanceof final SelectionRequest selReq)) {
			// if it is direct edit request and inside of the content area forward request
			// to there so we are creating
			// fbs inside
			final GroupContentEditPart groupContentEP = getGroupContentEP();
			if ((groupContentEP != null) && isGroupContentTargetingRequest(selReq, groupContentEP)) {
				groupContentEP.performRequest(request);
				return;
			}
		}
		super.performRequest(request);
	}

	@Override
	protected void performDirectEdit() {
		new TextDirectEditManager(this, new FigureCellEditorLocator(getFigure().getCommentFigure())) {
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
			final Point asPoint = position.toScreenPoint();
			final Rectangle bounds = new Rectangle(asPoint, getGroupSize());
			((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
		}
	}

	@Override
	protected void refreshComment() {
		getFigure().getCommentFigure().setText(getModel().getComment());
	}

	@Override
	public DragTracker getDragTracker(final Request request) {
		if (request instanceof final SelectionRequest selReq) {
			final GroupContentEditPart groupContentEP = getGroupContentEP();
			if ((groupContentEP != null) && isGroupContentTargetingRequest(selReq, groupContentEP)) {
				return groupContentEP.getDragTracker(request);
			}
		}
		return super.getDragTracker(request);
	}

	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (key == Group.class) {
			return key.cast(getModel());
		}
		return super.getAdapter(key);
	}

	@Override
	public GraphicalEditPart getContentEP() {
		return getChildren().stream().filter(GroupContentEditPart.class::isInstance).findAny().orElse(null);
	}

	private Dimension getGroupSize() {
		return new Dimension(CoordinateConverter.INSTANCE.iec61499ToScreen(getModel().getWidth()),
				CoordinateConverter.INSTANCE.iec61499ToScreen(getModel().getHeight()));
	}

	private GroupContentEditPart getGroupContentEP() {
		return (GroupContentEditPart) getChildren().stream().filter(GroupContentEditPart.class::isInstance).findAny()
				.orElse(null);
	}

	private GroupContentNetwork getGroupContents() {
		if (null == groupContents) {
			groupContents = new GroupContentNetwork(getModel());
		}
		return groupContents;
	}

	boolean isGroupContentTargetingRequest(final SelectionRequest request, final GroupContentEditPart groupContentEP) {
		final Point location = request.getLocation().getCopy();
		getFigure().translateToRelative(location);
		return ((groupContentEP != null) && (groupContentEP.getFigure().getBounds().contains(location)));
	}

	@Override
	public int getCommentWidth() {
		return getFigure().getCommentFigure().getTextWidth();
	}

	@Override
	public int getMinHeight() {
		return 0;
	}

	@Override
	public void updateAnnotations(final GraphicalAnnotationModelEvent event) {
		getChildren().stream().filter(InstanceNameEditPart.class::isInstance).map(InstanceNameEditPart.class::cast)
				.forEach(child -> child.updateAnnotations(event));
	}
}
