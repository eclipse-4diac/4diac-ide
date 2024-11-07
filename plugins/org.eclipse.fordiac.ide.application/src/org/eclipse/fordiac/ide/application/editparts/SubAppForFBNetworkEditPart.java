/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, AIT, fortiss GmbH,
 *                          Johannes Kepler University Linz,
 *                          Primetals Technologies Germany GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Filip Andren, Alois Zoitl, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed untyped subapp interface updates and according code cleanup
 *   Bianca Wiesmayr - fixed untyped subapp interface reorder/delete
 *   Alois Zoitl - separated FBNetworkElement from instance name for better
 *                 direct editing of instance names
 *               - added update support for removing or readding subapp type
 *   Bianca Wiesmayr, Alois Zoitl - unfolded subapp
 *   Daniel Lindhuber - instance comment
 *   				  - root refresh for monitoring elements
 *   Alois Zoitl - improved refresh on expand/collapse, added direct edit for
 *                 supapp comments
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.List;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.figures.InstanceCommentFigure;
import org.eclipse.fordiac.ide.application.figures.SubAppForFbNetworkFigure;
import org.eclipse.fordiac.ide.application.policies.ContainerResizePolicy;
import org.eclipse.fordiac.ide.application.policies.FBAddToSubAppLayoutEditPolicy;
import org.eclipse.fordiac.ide.application.policies.FBNetworkElementNonResizeableEP;
import org.eclipse.fordiac.ide.application.utilities.ExpandedInterfacePositionMap;
import org.eclipse.fordiac.ide.gef.editparts.FigureCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.TextDirectEditManager;
import org.eclipse.fordiac.ide.gef.policies.AbstractViewRenameEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class SubAppForFBNetworkEditPart extends AbstractFBNElementEditPart implements IContainerEditPart {
	private final ExpandedInterfacePositionMap positionMap = new ExpandedInterfacePositionMap(this);

	@Override
	public Adapter createContentAdapter() {
		return new SubappContentAdapter();
	}

	private final class SubappContentAdapter extends AdapterImpl {
		@Override
		public void notifyChanged(final Notification notification) {
			if (!notification.isTouch()) {
				Display.getDefault().execute(() -> handleRefresh(notification));
			}
			super.notifyChanged(notification);
		}

		protected void handleRefresh(final Notification notification) {
			switch (notification.getEventType()) {
			case Notification.ADD, Notification.ADD_MANY, Notification.MOVE:
				handleAddMove(notification);
				break;
			case Notification.REMOVE, Notification.REMOVE_MANY:
				handleRemove(notification);
				break;
			case Notification.SET:
				handleSet(notification);
				break;
			default:
				break;
			}
			if (notification.getFeatureID(SubApp.class) == LibraryElementPackage.SUB_APP__ATTRIBUTES
					&& (isUnfoldedAttribute(notification.getOldValue())
							|| isUnfoldedAttribute(notification.getNewValue()))) {
				// interface figures need to be manually added in order for them to be put
				// into the right container
				reloadInterfaceFigures();
				layoutExpandedInterface();
			}
			refreshToolTip();
			backgroundColorChanged(getFigure());
		}

		private static boolean isUnfoldedAttribute(final Object obj) {
			return obj instanceof final org.eclipse.fordiac.ide.model.libraryElement.Attribute attr
					&& attr.getName().equals("Unfolded"); //$NON-NLS-1$
		}

		private void reloadInterfaceFigures() {
			// @formatter:off
			getChildren().stream()
				.filter(InterfaceEditPart.class::isInstance)
				.map(InterfaceEditPart.class::cast)
				.forEach(ie -> addChildVisual(ie, -1));
			// @formatter:on
		}

		private void handleAddMove(final Notification notification) {
			if (notification.getNewValue() instanceof IInterfaceElement) {
				refreshChildren();
			}
			if (LibraryElementPackage.eINSTANCE.getConfigurableObject_Attributes().equals(notification.getFeature())) {
				refreshVisuals();
				refreshChildren();
				refreshInterfaceEditParts();
				refreshRoot();
			}
			layoutExpandedInterface();
		}

		private void handleRemove(final Notification notification) {
			if (notification.getOldValue() instanceof IInterfaceElement) {
				refreshChildren();
			}
			if (LibraryElementPackage.eINSTANCE.getConfigurableObject_Attributes().equals(notification.getFeature())) {
				refreshVisuals();
				refreshChildren();
				refreshInterfaceEditParts();
				refreshRoot();
			}

			layoutExpandedInterface();
		}

		private void handleSet(final Notification notification) {
			if (LibraryElementPackage.eINSTANCE.getINamedElement_Comment().equals(notification.getFeature())) {
				getFigure().refreshComment();
			}
			if (LibraryElementPackage.eINSTANCE.getSubApp_Width().equals(notification.getFeature())
					|| LibraryElementPackage.eINSTANCE.getSubApp_Height().equals(notification.getFeature())) {
				refreshPosition();
			}
			refreshVisuals();
		}

		private void refreshRoot() {
			final EditPart root = getRoot();
			if (root != null) {
				root.getChildren().forEach(EditPart::refresh);
			}
		}

		private void refreshInterfaceEditParts() {
			getChildren().forEach(ep -> {
				if (ep instanceof final InterfaceEditPart iEP) {
					iEP.refresh();
				}
			});
		}
	}

	private class SubappCommentRenameEditPolicy extends AbstractViewRenameEditPolicy {
		@Override
		protected Command getDirectEditCommand(final DirectEditRequest request) {
			if (getHost().getModel() instanceof final INamedElement namedEl) {
				final String str = (String) request.getCellEditor().getValue();
				if (!InstanceCommentFigure.EMPTY_COMMENT.equals(str)) {
					return new ResizeGroupOrSubappCommand(getHost(), new ChangeCommentCommand(namedEl, str));
				}
			}
			return null;
		}

		@Override
		protected void showCurrentEditValue(final DirectEditRequest request) {
			final String value = (String) request.getCellEditor().getValue();
			getCommentFigure().setText(value);
		}

		@Override
		protected void revertOldEditValue(final DirectEditRequest request) {
			getFigure().refreshComment();
		}

	}

	@Override
	protected List<Object> getModelChildren() {
		final List<Object> children = super.getModelChildren();
		if (getModel().isUnfolded()) {
			children.add(getModel().getSubAppNetwork());
		}
		return children;
	}

	public SubAppForFBNetworkEditPart() {
		// nothing to do here
	}

	@Override
	protected IFigure createFigureForModel() {
		return new SubAppForFbNetworkFigure(getModel(), this);
	}

	@Override
	public SubAppForFbNetworkFigure getFigure() {
		return (SubAppForFbNetworkFigure) super.getFigure();
	}

	@Override
	public SubApp getModel() {
		return (SubApp) super.getModel();
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		if (getModel().isUnfolded()) {
			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new SubappCommentRenameEditPolicy());
		} else {
			installEditPolicy(EditPolicy.LAYOUT_ROLE, new FBAddToSubAppLayoutEditPolicy());
		}
	}

	@Override
	public void performRequest(final Request request) {
		if (request.getType().equals(RequestConstants.REQ_OPEN)) {
			if (getModel().isUnfolded()) {
				performDirectEdit();
			} else {
				openSubAppEditor();
			}
		} else if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			if (getModel().isUnfolded()) {
				performDirectEdit();
			} else {
				super.performRequest(request);
			}
		} else {
			super.performRequest(request);
		}
	}

	@Override
	public void performDirectEdit() {
		if (EditorUtils.getCurrentActiveEditor().getAdapter(FBNetwork.class) != null) {
			if (getModel().isUnfolded()) {
				// if unfolded edit comment
				new TextDirectEditManager(this, new FigureCellEditorLocator(getCommentFigure())) {
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
			} else {
				super.performDirectEdit();
			}
		}
	}

	protected InstanceCommentFigure getCommentFigure() {
		return getFigure().getCommentFigure();
	}

	private void openSubAppEditor() {
		SubApp subApp = getModel();
		if (subAppIsMapped(subApp)) {
			subApp = (SubApp) subApp.getOpposite();
		}
		OpenListenerManager.openEditor(subApp);
	}

	private boolean subAppIsMapped(final SubApp subApp) {
		return (null == getModel().getTypeEntry()) && (null == subApp.getSubAppNetwork()) && subApp.isMapped();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		updateEditPolicies();
		final SubAppForFbNetworkFigure figure = getFigure();
		figure.updateTypeLabel(getModel());
		figure.updateExpandedFigure();
	}

	private void updateEditPolicies() {
		if (getModel().isUnfolded()) {
			if (getFigure().getExpandedMainFigure() == null) {
				installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new SubappCommentRenameEditPolicy());
				installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new ContainerResizePolicy());
				installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
			}
		} else if (getFigure().getExpandedMainFigure() != null) {
			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new TypeDirectEditPolicy());
			installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new FBNetworkElementNonResizeableEP());
			installEditPolicy(EditPolicy.LAYOUT_ROLE, new FBAddToSubAppLayoutEditPolicy());
		}
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if (childEditPart instanceof final UnfoldedSubappContentEditPart unfoldedSubappContentEP) {
			final IFigure contentFigure = unfoldedSubappContentEP.getFigure();
			final GridData contentGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
					| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
			getFigure().getExpandedContentArea().add(contentFigure, contentGridData, -1);
		} else if (childEditPart instanceof final InterfaceEditPart interfaceEditPart && getModel().isUnfolded()) {
			if (interfaceEditPart.isInput()) {
				getFigure().getExpandedInputFigure().add(interfaceEditPart.getFigure());
			} else {
				getFigure().getExpandedOutputFigure().add(interfaceEditPart.getFigure());
			}
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		if (childEditPart instanceof final UnfoldedSubappContentEditPart unfoldedSubappContentEP) {
			if (getFigure().getExpandedContentArea() != null) {
				getFigure().getExpandedContentArea().remove(unfoldedSubappContentEP.getFigure());
			}
		} else if (childEditPart instanceof final InterfaceEditPart interfaceEditPart && getModel().isUnfolded()) {
			if (interfaceEditPart.isInput()) {
				getFigure().getExpandedInputFigure().remove(interfaceEditPart.getFigure());
			} else {
				getFigure().getExpandedOutputFigure().remove(interfaceEditPart.getFigure());
			}
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	@Override
	public Rectangle getCommentBounds() {
		if (getFigure().getCommentFigure() != null) {
			return getFigure().getCommentFigure().getBounds();
		}
		return null;
	}

	@Override
	public GraphicalEditPart getContentEP() {
		return getChildren().stream().filter(UnfoldedSubappContentEditPart.class::isInstance).findAny().orElse(null);
	}

	@Override
	protected void refreshPosition() {
		if (getParent() != null) {
			final Position position = getModel().getPosition();
			final Rectangle bounds = new Rectangle(position.toScreenPoint(), getSubappSize());
			((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
		}
	}

	private Dimension getSubappSize() {
		if (getModel().isUnfolded()) {
			return new Dimension(CoordinateConverter.INSTANCE.iec61499ToScreen(getModel().getWidth()),
					CoordinateConverter.INSTANCE.iec61499ToScreen(getModel().getHeight()));
		}
		return new Dimension(-1, -1);
	}

	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (key == SubApp.class) {
			return key.cast(getModel());
		}
		return super.getAdapter(key);
	}

	@Override
	public int getCommentWidth() {
		return getCommentFigure().getTextWidth();
	}

	@Override
	public int getMinHeight() {
		if (getModel().isUnfolded()) {
			return getFigure().getExpandedIOHeight();
		}
		return 0;
	}

	public ExpandedInterfacePositionMap getInterfacePositionMap() {
		return positionMap;
	}

	public void layoutExpandedInterface() {
		if (getModel().isUnfolded()) {
			Display.getDefault().asyncExec(() -> getFigure().layoutExpandedInterface());
		}
	}

}
