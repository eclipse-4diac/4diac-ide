/*******************************************************************************
 * Copyright (c) 2008 - 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.editparts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkEditPart;
import org.eclipse.fordiac.ide.application.policies.FBNetworkXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.swt.graphics.Point;

/**
 * The Class FBNetworkContainerEditPart.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class FBNetworkContainerEditPart extends FBNetworkEditPart {


	/** The content adapter. */
	EContentAdapter contentAdapter;
	
	private final Map<IInterfaceElement, IInterfaceElement> interfaceElementViewMapping = new HashMap<>();
	
	private final Map<IInterfaceElement, IInterfaceElement> elementViewMapping = new HashMap<>();

	
	@Override
	protected EContentAdapter getContentAdapter(){
		if(null == contentAdapter){
			contentAdapter = new EContentAdapter() {
					@Override
					public void notifyChanged(Notification notification) {
						super.notifyChanged(notification);
						Object feature = notification.getFeature();
						if (LibraryElementPackage.eINSTANCE.getFBNetwork_NetworkElements().equals(feature)
								|| LibraryElementPackage.eINSTANCE.getIInterfaceElement_InputConnections().equals(feature)
								|| LibraryElementPackage.eINSTANCE.getIInterfaceElement_OutputConnections().equals(feature)) {
							refreshChildren();
							refreshVisuals();
						}
					}
				};
		}
		return contentAdapter;
	}


	/**
	 * Gets the main interface element view.
	 * 
	 * @param element
	 *          the element
	 * 
	 * @return the main interface element view
	 */
	public IInterfaceElement getMainInterfaceElementView(IInterfaceElement element) {
		return elementViewMapping.get(element);
	}

	/**
	 * Gets the assigned interface element view.
	 * 
	 * @param element
	 *          the element
	 * 
	 * @return the assigned interface element view
	 */
	public IInterfaceElement getAssignedInterfaceElementView(IInterfaceElement element) {
		return interfaceElementViewMapping.get(element);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List getModelChildren() {
		interfaceElementViewMapping.clear();
		elementViewMapping.clear();
		ArrayList<Object> children = new ArrayList<>(super.getModelChildren()); 

		ArrayList<Object> interfaceElements = new ArrayList<>();
		Resource res = (Resource)getModel().eContainer();

		for (Object object : children) {
		    if (object instanceof FBNetworkElement) {
		    	FBNetworkElement fbNetworkelement = (FBNetworkElement)object;
				for (Event event : fbNetworkelement.getInterface().getEventInputs()) {
					for (Connection connection : event.getInputConnections()) {
						if (connection.getSource() != null) {
							if (!isMappedToEqualResource(res, connection.getSourceElement())) {
								createVirtualIO(interfaceElements, fbNetworkelement, event, connection.getSource());
							}
						}
					}
				}
				for (Event event : fbNetworkelement.getInterface().getEventOutputs()) {
					for (Connection connection : event.getOutputConnections()) {
						if (connection.getSource() != null) {
							if (!isMappedToEqualResource(res, connection.getDestinationElement())) {
								createVirtualIO(interfaceElements, fbNetworkelement, event, connection.getDestination());
							}
						}
					}
				}
				for (VarDeclaration var : fbNetworkelement.getInterface().getInputVars()) {
					for (Connection connection : var.getInputConnections()) {
						if (connection.getSource() != null) {
							if (!isMappedToEqualResource(res, connection.getSourceElement())) {
								createVirtualIO(interfaceElements, fbNetworkelement, var, connection.getSource());
							}
						}
					}						
				}
				for (VarDeclaration var : fbNetworkelement.getInterface().getOutputVars()) {
					for (Connection connection : var.getOutputConnections()) {
						if (connection.getSource() != null) {
							if (!isMappedToEqualResource(res, connection.getDestinationElement())) {
								createVirtualIO(interfaceElements, fbNetworkelement, var, connection.getDestination());
							}
						}
					}						
				}
				//TODO add plugs and sockets
			}
		}
		
		interfaceElements.addAll(children);
		return interfaceElements;
	}

	private void createVirtualIO(List<Object> interfaceElements,
			FBNetworkElement fbnetworkElement, IInterfaceElement element, IInterfaceElement source) {
//TODO model refactoring - reimplment when implmenting new virtual IO Bugzilla #490955
//		InterfaceElementView iev = UiFactory.eINSTANCE.createInterfaceElementView();
//		iev.setIInterfaceElement(source);
//		interfaceElements.add(iev);
//		interfaceElementViewMapping.put(element, iev);
//		elementViewMapping.put(iev, getInterfaceElementViewForIInterfaceElement(
//				fbView, element));
	}

	//TODO model refactoring - reimplment when implmenting new virtual IO Bugzilla #490955
//	private void createVirtualIO(List<Object> interfaceElements,
//			MappedSubAppView fbView, IInterfaceElement element,
//			IInterfaceElement source) {
//		InterfaceElementView iev = UiFactory.eINSTANCE.createInterfaceElementView();
//		iev.setIInterfaceElement(source);
//		interfaceElements.add(iev);
//		interfaceElementViewMapping.put(element, iev);
//		elementViewMapping.put(iev, getInterfaceElementViewForIInterfaceElement(
//				fbView, element));
//	}

	private boolean isMappedToEqualResource(Resource res, FBNetworkElement fbNetElement) {		
		
//TODO model refactoring - finish when mapping and subapplication model is finished
//		if (fb.eContainer() instanceof SubAppNetwork
//				&& !(fb.eContainer() instanceof FBNetwork)) {
//			SubAppNetwork subAppNetwork = (SubAppNetwork) fb.eContainer();
//			EObject parent = subAppNetwork.eContainer();
//			while (parent != null && !(parent instanceof SubAppView)) {
//				parent = parent.eContainer();
//			}
//			if (parent == null) {
//				return false;
//			} else if (parent instanceof SubAppView) {
//				SubAppView view = (SubAppView) parent;
//				if (view.getMappedSubApp() != null) {
//					MappedSubAppView mappedView = view.getMappedSubApp();
//					if (mappedView.getSubApp() != null) {
//						SubApp subApp = mappedView.getSubApp();
//						if (subApp.getResource() != null) {
//							return subApp.getResource().eContainer().equals(
//									uiResEditor.getResourceElement());
//						}
//					}
//				}
//			}
//			return false;
//		}
//		if (fbNetElement.getResource() == null) {
//			return false;
//		}
//		if (res == null) {
//			return false;
//		}
//		return res.equals(fbNetElement.getResource());
		return true;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();

		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());		
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new FBNetworkXYLayoutEditPolicy());
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractEditPart#performRequest(org.eclipse.gef
	 * .Request)
	 */
	@Override
	public void performRequest(final Request req) {
		Command cmd = getCommand(req);
		if (cmd != null && cmd.canExecute()) {
			getViewer().getEditDomain().getCommandStack().execute(cmd);
		}
		super.performRequest(req);
	}


	@Override
	protected void refreshVisuals() {
		Point p = getParent().getViewer().getControl().getSize();
		Rectangle rect = new Rectangle(0, 0, p.x, p.y);
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rect);
		super.refreshVisuals();
	}

}
