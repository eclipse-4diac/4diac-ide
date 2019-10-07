/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, AIT, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.SpecificLayerEditPart;
import org.eclipse.fordiac.ide.application.policies.FBNetworkXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

public class UISubAppNetworkEditPart extends EditorWithInterfaceEditPart {
	private EContentAdapter contentAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (LibraryElementPackage.eINSTANCE.getFBNetworkElement_Interface().equals(notification.getFeature())) {
				refresh();
			}

			switch (notification.getEventType()) {
			case Notification.ADD:
			case Notification.ADD_MANY:
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				refreshChildren();
				break;
			case Notification.SET:
				refreshVisuals();
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void activate() {
		super.activate();
		if (null != getModel() && !getModel().eAdapters().contains(contentAdapter)) {
			getModel().eAdapters().add(contentAdapter);
			if (null != getSubApp() && !getSubApp().eAdapters().contains(contentAdapter)) {
				getSubApp().eAdapters().add(contentAdapter);
			}
		}
	}

	@Override
	public void deactivate() {
		super.deactivate();
		if (null != getModel()) {
			getModel().eAdapters().remove(contentAdapter);
			if (null != getSubApp()) {
				getSubApp().eAdapters().remove(contentAdapter);
			}
		}
	}

	@Override
	protected List<?> getModelChildren() {
		ArrayList<Object> children = new ArrayList<>();
		children.addAll(getSubApp().getInterface().getAllInterfaceElements());
		children.addAll(super.getModelChildren());
		return children;
	}

	public SubApp getSubApp() {
		return (SubApp) getModel().eContainer();
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if (childEditPart instanceof InterfaceEditPart) {
			addChildVisualInterfaceElement((InterfaceEditPart) childEditPart);
		} else if (childEditPart instanceof SpecificLayerEditPart) {
			String layer = ((SpecificLayerEditPart) childEditPart).getSpecificLayer();
			IFigure layerFig = getLayer(layer);
			if (layerFig != null) {
				IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
				layerFig.add(child);
			} else { // if layer does not exist use default layer
				super.addChildVisual(childEditPart, index);
			}
		} else {
			super.addChildVisual(childEditPart, -1);
		}
	}

	private void addChildVisualInterfaceElement(final InterfaceEditPart childEditPart) {
		IFigure child = childEditPart.getFigure();
		if (childEditPart.getModel().isIsInput()) {
			if (childEditPart.isEvent()) {
				getLeftEventInterfaceContainer().add(child,
						getSubApp().getInterface().getEventInputs().indexOf(childEditPart.getModel()));
			} else if (childEditPart.isAdapter()) {
				getLeftAdapterInterfaceContainer().add(child,
						getSubApp().getInterface().getSockets().indexOf(childEditPart.getModel()));
			} else {
				getLeftVarInterfaceContainer().add(child,
						getSubApp().getInterface().getInputVars().indexOf(childEditPart.getModel()));
			}
		} else {
			if (childEditPart.isEvent()) {
				getRightEventInterfaceContainer().add(child,
						getSubApp().getInterface().getEventOutputs().indexOf(childEditPart.getModel()));
			} else if (childEditPart.isAdapter()) {
				getRightAdapterInterfaceContainer().add(child,
						getSubApp().getInterface().getPlugs().indexOf(childEditPart.getModel()));
			} else {
				getRightVarInterfaceContainer().add(child,
						getSubApp().getInterface().getOutputVars().indexOf(childEditPart.getModel()));
			}
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		if (childEditPart instanceof InterfaceEditPart) {
			removeChildVisualInterfaceElement((InterfaceEditPart) childEditPart);
		} else if (childEditPart instanceof SpecificLayerEditPart) {
			String layer = ((SpecificLayerEditPart) childEditPart).getSpecificLayer();
			IFigure layerFig = getLayer(layer);
			if (layerFig != null) {
				IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
				layerFig.remove(child);
			} else { // if layer does not exist use default layer
				super.removeChildVisual(childEditPart);
			}
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	private void removeChildVisualInterfaceElement(final InterfaceEditPart childEditPart) {
		IFigure child = childEditPart.getFigure();
		if (childEditPart.isInput()) {
			if (childEditPart.isEvent()) {
				getLeftEventInterfaceContainer().remove(child);
			} else if (childEditPart.isAdapter()) {
				getLeftAdapterInterfaceContainer().remove(child);
			} else {
				getLeftVarInterfaceContainer().remove(child);
			}
		} else {
			if (childEditPart.isEvent()) {
				getRightEventInterfaceContainer().remove(child);
			} else if (childEditPart.isAdapter()) {
				getRightAdapterInterfaceContainer().remove(child);
			} else {
				getRightVarInterfaceContainer().remove(child);
			}
		}
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// handles constraint changes (e.g. moving and/or resizing) of model
		// elements and creation of new model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new FBNetworkXYLayoutEditPolicy());
	}
}