/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, AIT, fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
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
 *   Daniel Lindhuber - altered methods to work with the elk layouter
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.SpecificLayerEditPart;
import org.eclipse.fordiac.ide.application.policies.FBNetworkXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

public class UISubAppNetworkEditPart extends EditorWithInterfaceEditPart {
	private Adapter contentAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			switch (notification.getEventType()) {
			case Notification.ADD:
			case Notification.ADD_MANY:
			case Notification.MOVE:
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

	private Adapter subAppInterfaceAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			switch (notification.getEventType()) {
			case Notification.ADD:
			case Notification.ADD_MANY:
			case Notification.MOVE:
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				refreshChildren();
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void activate() {
		super.activate();
		if ((null != getModel()) && !getModel().eAdapters().contains(contentAdapter)) {
			getModel().eAdapters().add(contentAdapter);
			if ((null != getSubApp()) && !getSubApp().getInterface().eAdapters().contains(subAppInterfaceAdapter)) {
				getSubApp().getInterface().eAdapters().add(subAppInterfaceAdapter);
			}
		}
	}

	@Override
	public void deactivate() {
		super.deactivate();
		if (null != getModel()) {
			getModel().eAdapters().remove(contentAdapter);
			if (null != getSubApp()) {
				getSubApp().getInterface().eAdapters().remove(subAppInterfaceAdapter);
			}
		}
	}

	@Override
	protected List<?> getModelChildren() {
		ArrayList<Object> children = new ArrayList<>(super.getModelChildren());
		InterfaceList il = getSubApp().getInterface();
		children.addAll(il.getEventOutputs());
		children.addAll(il.getOutputVars());
		children.addAll(il.getPlugs());
		children.addAll(il.getEventInputs());
		children.addAll(il.getInputVars());
		children.addAll(il.getSockets());
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

	public void addChildVisualInterfaceElement(final InterfaceEditPart childEditPart) {
		IFigure child = childEditPart.getFigure();
		if (childEditPart.getModel().isIsInput()) { // use model isInput! because EditPart.isInput treats inputs as
													// outputs for visual appearance
			if (childEditPart.isEvent()) {
				int index = getSubApp().getInterface().getEventInputs().indexOf(childEditPart.getModel());
				int containerSize = getLeftEventInterfaceContainer().getChildren().size();
				getLeftEventInterfaceContainer().add(child, (index >= containerSize) ? containerSize : index);
			} else if (childEditPart.isAdapter()) {
				int index = getSubApp().getInterface().getSockets().indexOf(childEditPart.getModel());
				int containerSize = getLeftAdapterInterfaceContainer().getChildren().size();
				getLeftAdapterInterfaceContainer().add(child, (index >= containerSize) ? containerSize : index);
			} else {
				int index = getSubApp().getInterface().getInputVars().indexOf(childEditPart.getModel());
				int containerSize = getLeftVarInterfaceContainer().getChildren().size();
				getLeftVarInterfaceContainer().add(child, (index >= containerSize) ? containerSize : index);
			}
		} else {
			if (childEditPart.isEvent()) {
				int index = getSubApp().getInterface().getEventOutputs().indexOf(childEditPart.getModel());
				int containerSize = getRightEventInterfaceContainer().getChildren().size();
				getRightEventInterfaceContainer().add(child, (index >= containerSize) ? containerSize : index);
			} else if (childEditPart.isAdapter()) {
				int index = getSubApp().getInterface().getPlugs().indexOf(childEditPart.getModel());
				int containerSize = getRightAdapterInterfaceContainer().getChildren().size();
				getRightAdapterInterfaceContainer().add(child, (index >= containerSize) ? containerSize : index);
			} else {
				int index = getSubApp().getInterface().getOutputVars().indexOf(childEditPart.getModel());
				int containerSize = getRightVarInterfaceContainer().getChildren().size();
				getRightVarInterfaceContainer().add(child, (index >= containerSize) ? containerSize : index);
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

	public void removeChildVisualInterfaceElement(final InterfaceEditPart childEditPart) {
		IFigure child = childEditPart.getFigure();
		if (childEditPart.getModel().isIsInput()) {
			if (childEditPart.isEvent()) {
				try {
					getLeftEventInterfaceContainer().remove(child);
				} catch (IllegalArgumentException e) {
					getCastedFigure().remove(child);
				}
			} else if (childEditPart.isAdapter()) {
				try {
					getLeftAdapterInterfaceContainer().remove(child);
				} catch (IllegalArgumentException e) {
					getCastedFigure().remove(child);
				}
			} else {
				try {
					getLeftVarInterfaceContainer().remove(child);
				} catch (IllegalArgumentException e) {
					getCastedFigure().remove(child);
				}
			}
		} else {
			if (childEditPart.isEvent()) {
				try {
					getRightEventInterfaceContainer().remove(child);
				} catch (IllegalArgumentException e) {
					getCastedFigure().remove(child);
				}
			} else if (childEditPart.isAdapter()) {
				try {
					getRightAdapterInterfaceContainer().remove(child);
				} catch (IllegalArgumentException e) {
					getCastedFigure().remove(child);
				}
			} else {
				try {
					getRightVarInterfaceContainer().remove(child);
				} catch (IllegalArgumentException e) {
					getCastedFigure().remove(child);
				}
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

	public void enableElkLayouting(SubAppInternalInterfaceEditPart part) {
		removeChildVisual(part);
		getCastedFigure().add(part.getFigure());
	}

}