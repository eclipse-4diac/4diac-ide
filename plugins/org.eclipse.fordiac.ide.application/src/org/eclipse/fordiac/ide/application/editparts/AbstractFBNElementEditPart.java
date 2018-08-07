/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.figures.AbstractFBNetworkElementFigure;
import org.eclipse.fordiac.ide.application.policies.DeleteFBNElementEditPolicy;
import org.eclipse.fordiac.ide.application.policies.FBNElementSelectionPolicy;
import org.eclipse.fordiac.ide.gef.editparts.AbstractPositionableElementEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.gef.editparts.NameCellEditorLocator;
import org.eclipse.fordiac.ide.gef.policies.AbstractViewRenameEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeFBNetworkElementName;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.fordiac.ide.util.preferences.PreferenceConstants;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.TextCellEditor;

/**
 * This class implements an EditPart for a FunctionBlock.
 */
public abstract class AbstractFBNElementEditPart extends AbstractPositionableElementEditPart {

	private Device referencedDevice;

	/** necessary that the gradient pattern can be scaled accordingly */
	private final ZoomManager zoomManager;
	

	public ZoomManager getZoomManager() {
		return zoomManager;
	}

	public AbstractFBNElementEditPart(final ZoomManager zoomManager) {
		super();
		this.zoomManager = zoomManager;
	}

	private EContentAdapter colorChangeListener = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			if (notification.getFeature() == LibraryElementPackage.eINSTANCE.getColorizableElement_Color()) {
				backgroundColorChanged(getFigure());
			}
		}
	};

	@Override
	protected EContentAdapter createContentAdapter() {
		return new EContentAdapter() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				refreshToolTip(); //TODO add here checks that better define when the tooltip should be refreshed
				if (notification.getFeature() == LibraryElementPackage.eINSTANCE.getFBNetworkElement_Mapping()) {
					updateDeviceListener();
				}
			}

		};
	}

	/** The i named element content adapter. */
	private final EContentAdapter annotationContentAdapter = new EContentAdapter() {

		@Override
		public void notifyChanged(Notification notification) {
			if (notification.getFeature() == LibraryElementPackage.eINSTANCE
					.getI4DIACElement_Annotations()) {
				refreshName();
				
			}
		}

	};

	protected void updateDeviceListener() {
		Device device = findDevice();
		if (device != referencedDevice) {
			if (referencedDevice != null) {
				referencedDevice.eAdapters().remove(colorChangeListener);
			}
			referencedDevice = device;
			if (referencedDevice != null) {
				referencedDevice.eAdapters().add(colorChangeListener);
			}			
			backgroundColorChanged(getFigure());
		}
	}

	@Override
	public void activate() {
		super.activate();
		updateDeviceListener();
		if (getModel() != null) {
			getModel().eAdapters().add(annotationContentAdapter);
		}
	}

	@Override
	public void deactivate() {
		super.deactivate();
		if (referencedDevice != null) {
			referencedDevice.eAdapters().remove(colorChangeListener);
		}
		if (getModel() != null) {
			getModel().eAdapters().remove(annotationContentAdapter);
		}
	}
	
	public boolean isOnlyThisOrNothingSelected() {
		@SuppressWarnings("unchecked")
		List<EditPart> selection = getViewer().getSelectedEditParts();
		if(selection.size() > 1){
			return false;
		} else if(selection.size() == 1){
			return selection.get(0) == this;
		}
		return true;
	}

	protected void refreshToolTip() {
		getCastedFigure().refreshToolTips();
	}

	@Override
	protected void refreshComment() {
		refreshToolTip();
	}

	@Override
	protected void refreshName() {
		super.refreshName();
		getCastedFigure().refreshIcon();
	}

	
	@Override
	public FBNetworkElement getModel() {
		return (FBNetworkElement) super.getModel();
	}
	
	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		// allow delete of a FB
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteFBNElementEditPolicy());

		// Highlight In and Outconnections of the selected fb, allow alignment of FBs
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new FBNElementSelectionPolicy());

		//FBNetwork elements need a special rename command therefore we remove the standard edit policy and add a adjusted one
		removeEditPolicy(EditPolicy.DIRECT_EDIT_ROLE);
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new AbstractViewRenameEditPolicy() {
			 @Override
			protected Command getDirectEditCommand(DirectEditRequest request) {
			        if (getHost() instanceof AbstractFBNElementEditPart) {
			            return new ChangeFBNetworkElementName(((AbstractFBNElementEditPart) getHost()).getModel(),
						(String) request.getCellEditor().getValue());
			        }
			        return null;
			    }
		});
	}


	/**
	 * Returns the label wich contains the instance name of a FB.
	 * 
	 * @return the label
	 */
	public Label getInstanceNameLabel() {
		if (getCastedFigure() != null) {
			return getCastedFigure().getInstanceNameLabel();
		}
		return null;
	}

	private AbstractFBNetworkElementFigure getCastedFigure() {
		if (getFigure() instanceof AbstractFBNetworkElementFigure) {
			return (AbstractFBNetworkElementFigure) getFigure();
		}
		return null;
	}

	/** The listener. */
	private IPropertyChangeListener listener;

	/**
	 * Returns an <code>IPropertyChangeListener</code> with implemented
	 * <code>propertyChange()</code>. e.g. a color change event repaints the
	 * FunctionBlock.
	 * 
	 * @return the preference change listener
	 */
	@Override
	public org.eclipse.jface.util.IPropertyChangeListener getPreferenceChangeListener() {
		if (listener == null) {
			listener = new org.eclipse.jface.util.IPropertyChangeListener() {
				@Override
				public void propertyChange(final PropertyChangeEvent event) {
					if (event.getProperty().equals(
							PreferenceConstants.P_EVENT_CONNECTOR_COLOR)
							|| event.getProperty().equals(
									PreferenceConstants.P_DATA_CONNECTOR_COLOR)
							|| event.getProperty().equals(
									PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR)){
						getFigure().repaint();
					}
				}
			};
		}
		return listener;
	}

	@Override
	protected void backgroundColorChanged(IFigure figure) {
		Color color = null;
		if (getModel() != null) {
			Device dev = findDevice();
			if (dev != null) {
				color = LibraryElementFactory.eINSTANCE.createColor();
				color.setRed(dev.getColor().getRed());
				color.setGreen(dev.getColor().getGreen());
				color.setBlue(dev.getColor().getBlue());
			}
		}
		setColor(figure, color);
	}

	private Device findDevice() {
		Resource res = null;
		if (null != getModel() && getModel().isMapped()) {
			res = getModel().getResource();			
		}
		return (null != res) ? res.getDevice() : null;
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof InterfaceEditPart) {
			InterfaceEditPart interfaceEditPart = (InterfaceEditPart) childEditPart;
			if (interfaceEditPart.isInput()) {
				if (interfaceEditPart.isEvent()) {
					getCastedFigure().getEventInputs().add(child, getModel().getInterface().getEventInputs().indexOf(interfaceEditPart.getModel()));
				} else{
					if(interfaceEditPart.isAdapter()){
						getCastedFigure().getSockets().add(child, getModel().getInterface().getSockets().indexOf(interfaceEditPart.getModel()));
					}else if (interfaceEditPart.isVariable()) {
						getCastedFigure().getDataInputs().add(child, getModel().getInterface().getInputVars().indexOf(interfaceEditPart.getModel()));
					}
				}
			} else {
				if (interfaceEditPart.isEvent()) {
					getCastedFigure().getEventOutputs().add(child, getModel().getInterface().getEventOutputs().indexOf(interfaceEditPart.getModel()));
				} else { 
					if(interfaceEditPart.isAdapter()){
						getCastedFigure().getPlugs().add(child, getModel().getInterface().getPlugs().indexOf(interfaceEditPart.getModel()));
					}else if (interfaceEditPart.isVariable()) {
						getCastedFigure().getDataOutputs().add(child, getModel().getInterface().getOutputVars().indexOf(interfaceEditPart.getModel()));
					}
				}

			}
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		if (childEditPart instanceof InterfaceEditPart) {
			InterfaceEditPart interfaceEditPart = (InterfaceEditPart) childEditPart;
			if (interfaceEditPart.isInput()) {
				if (interfaceEditPart.isEvent()) {
					getCastedFigure().getEventInputs().remove(child);
				} else { 
					if(interfaceEditPart.isAdapter()){
						getCastedFigure().getSockets().remove(child);
					}else if (interfaceEditPart.isVariable()) {
						getCastedFigure().getDataInputs().remove(child);
					}	
				}
			} else {
				if (interfaceEditPart.isEvent()) {
					getCastedFigure().getEventOutputs().remove(child);
				} else { 
					if(interfaceEditPart.isAdapter()){
						getCastedFigure().getPlugs().remove(child);
					}else if (interfaceEditPart.isVariable()) {
						getCastedFigure().getDataOutputs().remove(child);
					}
				}

			}
		} else {
			super.removeChildVisual(childEditPart);
		}
	}

	@Override
	protected List<Object> getModelChildren() {
		ArrayList<Object> elements = new ArrayList<>();
		elements.addAll(getModel().getInterface().getAllInterfaceElements());
		return elements;
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel();
	}
	
	@Override
	protected PositionableElement getPositionableElement(){
		return getModel();
	}

	@Override
	public Label getNameLabel() {
		return getInstanceNameLabel();
	}

	/**
	 * Gets the manager.
	 * 
	 * @return the manager
	 */
	@Override
	public DirectEditManager getManager() {
		if (manager == null) {
			Label l = getNameLabel();
			manager = new LabelDirectEditManager(this, TextCellEditor.class,
					new NameCellEditorLocator(l), l,
					new IdentifierVerifyListener());
		}

		return manager;
	}

	@Override
	public void setTransparency(int value) {
		for (Object ep : getChildren()) {
			if (ep instanceof AbstractViewEditPart) {
				((AbstractViewEditPart) ep).setTransparency(value);
			}
		}
		super.setTransparency(value);
	}

}
