/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, AIT, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *               2020 Primetals Technologies Germany GmbH
 *               2021 Primetals Technologies Austria GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.application.figures.SubAppForFbNetworkFigure;
import org.eclipse.fordiac.ide.application.policies.FBAddToSubAppLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

public class SubAppForFBNetworkEditPart extends AbstractFBNElementEditPart {
	private UnfoldedSubappContentNetwork subappContents;
	private InstanceComment instanceComment;

	@Override
	public Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				switch (notification.getEventType()) {
				case Notification.ADD:
				case Notification.ADD_MANY:
				case Notification.MOVE:
					if (notification.getNewValue() instanceof IInterfaceElement) {
						refreshChildren();
					}
					if (LibraryElementPackage.eINSTANCE.getConfigurableObject_Attributes()
							.equals(notification.getFeature())) {
						refreshChildren();
						refreshInterfaceEditParts();
						refreshRoot();
					}
					break;
				case Notification.REMOVE:
				case Notification.REMOVE_MANY:
					if (notification.getOldValue() instanceof IInterfaceElement) {
						refreshChildren();
					}
					if (LibraryElementPackage.eINSTANCE.getConfigurableObject_Attributes()
							.equals(notification.getFeature())) {
						refreshChildren();
						refreshInterfaceEditParts();
						refreshRoot();
					}
					break;
				case Notification.SET:
					refreshVisuals();
					break;
				default:
					break;
				}
				refreshToolTip();
				backgroundColorChanged(getFigure());
			}

			private void refreshRoot() {
				final EditPart root = getRoot();
				if (root != null) {
					root.getChildren().forEach(child -> ((EditPart) child).refresh());
				}
			}

			private void refreshInterfaceEditParts() {
				getChildren().forEach(ep -> {
					if (ep instanceof InterfaceEditPart) {
						((InterfaceEditPart) ep).refresh();
					}
				});
			}
		};
	}
	@Override
	protected List<Object> getModelChildren() {
		final List<Object> children = super.getModelChildren();
		if (getModel().isUnfolded()) {
			children.add(getSubappContents());
			children.add(getInstanceComment());
		}
		return children;
	}

	private UnfoldedSubappContentNetwork getSubappContents() {
		if (null == subappContents) {
			subappContents = new UnfoldedSubappContentNetwork(getModel());
		}
		return subappContents;
	}

	private InstanceComment getInstanceComment() {
		if (null == instanceComment) {
			instanceComment = new InstanceComment(getModel());
		}
		return instanceComment;
	}

	public SubAppForFBNetworkEditPart() {
		super();
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
		// Add policy to handle drag&drop of fbs
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new FBAddToSubAppLayoutEditPolicy());
	}

	@Override
	public void performRequest(final Request request) {
		if (request.getType().equals(RequestConstants.REQ_OPEN)) {
			openSubAppEditor();
		} else {
			super.performRequest(request);
		}
	}
	private void openSubAppEditor() {
		SubApp subApp = getModel();
		if (subAppIsMapped(subApp)) {
			subApp = (SubApp) subApp.getOpposite();
		}
		OpenListenerManager.openEditor(subApp);

	}

	private boolean subAppIsMapped(final SubApp subApp) {
		return null == getModel().getPaletteEntry() && (null == subApp.getSubAppNetwork()) && subApp.isMapped();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		getFigure().updateTypeLabel(getModel());
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if (childEditPart instanceof UnfoldedSubappContentEditPart) {
			getFigure().getBottom().add(((UnfoldedSubappContentEditPart) childEditPart).getFigure(), 1);
		} else if (childEditPart instanceof InstanceCommentEditPart) {
			getFigure().getTop().add(((InstanceCommentEditPart) childEditPart).getFigure(), 1);
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		if (childEditPart instanceof UnfoldedSubappContentEditPart) {
			getFigure().getBottom().remove(((UnfoldedSubappContentEditPart) childEditPart).getFigure());
		} else if (childEditPart instanceof InstanceCommentEditPart) {
			getFigure().getTop().remove(((InstanceCommentEditPart) childEditPart).getFigure());
		} else {
			super.removeChildVisual(childEditPart);
		}
	}
}
