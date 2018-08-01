/*******************************************************************************
 * Copyright (c) 2012 - 2018 Profactor GmbH, AIT, fortiss GmbH
 * 							 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Filip Andren, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import java.util.ArrayList;

import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseFactory;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeNetworkViewerEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringFactory;

public final class MonitoringManagerUtils {
	
	private MonitoringManagerUtils() {
		throw new AssertionError();  //class should not be instantiated
	}

	public static boolean canBeMonitored(org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart editPart) {
		PortElement port = MonitoringManagerUtils.createPortElement(editPart);  //FIXME think how we can get away without creating a port element 
		return ((port != null) && (port.getPortString() != null));
	}
	
	public static boolean canBeMonitored(FBEditPart obj) {
		// As a first solution try to find the first interface editpart and see if we can monitoring
		for (Object child : obj.getChildren()) {
			if(child instanceof InterfaceEditPart){
				return canBeMonitored((InterfaceEditPart)child);
			}
		}
		return false;
	}

	public static PortElement createPortElement(org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart editPart) {
		if (editPart.getParent() instanceof FBEditPart
				&& editPart.getParent().getParent() instanceof CompositeNetworkViewerEditPart) {
			return createCompositeInternalPortString(editPart);
		}

		FBNetworkElement obj = editPart.getModel().getFBNetworkElement();
		if(obj instanceof FB){
			FB fb = (FB)obj; 
			return createPortElement(fb, editPart);					
		}
		
		return null;

	}

	private static PortElement createPortElement(FB fb,
			org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart ep) {
		PortElement p;
		if (ep.getModel() instanceof AdapterDeclaration){
			p = MonitoringFactory.eINSTANCE.createAdapterPortElement(); 
		}
		else{
			p = MonitoringBaseFactory.eINSTANCE.createPortElement();
		}

		Resource res = findResourceForFB(fb, p);
		if (res == null) {
			return null;
		}
		
		p.setResource(res);
		p.setFb(fb);
		p.setInterfaceElement(ep.getModel());
		return p;
	}

	private static PortElement createCompositeInternalPortString(
			org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart editPart) {
		
		FBEditPart fbep = (FBEditPart)editPart.getParent();
		CompositeNetworkViewerEditPart cnep = (CompositeNetworkViewerEditPart) editPart
				.getParent().getParent();

		ArrayList<CompositeNetworkViewerEditPart> parents = new ArrayList<>();

		CompositeNetworkViewerEditPart root = cnep; 
		parents.add(0, root);
		while (root.getparentInstanceViewerEditPart() != null) {
			parents.add(0, root.getparentInstanceViewerEditPart());
			root = root.getparentInstanceViewerEditPart();
		}

		FB fb = root.getFbInstance();
		PortElement pe = createPortElement(fb, editPart);
		if (pe != null) {
			pe.setFb(fbep.getModel());

			for (CompositeNetworkViewerEditPart compositeNetworkEditPart : parents) {
				pe.getHierarchy().add(
						compositeNetworkEditPart.getFbInstance().getName());
			}
			return pe;
		}
		return null;
	}

	private static Resource findResourceForFB(FBNetworkElement element, PortElement p) {
		Resource retVal = element.getResource();
		
		if(null == retVal && element.getFbNetwork().eContainer() instanceof SubApp) {
			//if we are in a subapp check if we can find it in the hierarchy we are in a resource
			SubApp subApp = (SubApp)element.getFbNetwork().eContainer();
			retVal = findResourceForFB(subApp, p);
			if(null != retVal) {
				//if we found a resource add the the name recursively to the hierarchy as prefix  
				p.getHierarchy().add(subApp.getName());
			}
		}		
		return retVal;
	}

	
}
