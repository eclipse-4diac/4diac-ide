/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014, 2017 Profactor GmbH, fortiss GmbH
 * 				 2018 Johannes Keppler University
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl 
 *       - initial API and implementation and/or initial documentation
 *  Alois Zoitl - Refactored class hierarchy of xml exporters  
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.util.List;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeFB;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

class FBNetworkExporter extends CommonElementExporter{
	

	FBNetworkExporter(Document dom) {
		super(dom);
	}
	
	
	Element createFBNetworkElement(final FBNetwork fbNetwork){
		String elementName = LibraryElementTags.SUBAPPNETWORK_ELEMENT;
		if( (fbNetwork.eContainer() instanceof FBType && !(fbNetwork.eContainer() instanceof SubAppType)) ||
				(fbNetwork.eContainer() instanceof Resource) ||
				(fbNetwork.eContainer() instanceof ResourceType)){
			elementName = LibraryElementTags.FBNETWORK_ELEMENT;
		}
		
		Element fbNetworkElement = createElement(elementName); 
		
		addFBNetworkElements(fbNetworkElement, fbNetwork);
		addConnections(fbNetworkElement, fbNetwork.getEventConnections(), LibraryElementTags.EVENT_CONNECTIONS_ELEMENT, fbNetwork);
		addConnections(fbNetworkElement, fbNetwork.getDataConnections(), LibraryElementTags.DATA_CONNECTIONS_ELEMENT, fbNetwork);
		addConnections(fbNetworkElement, fbNetwork.getAdapterConnections(), LibraryElementTags.ADAPTERCONNECTIONS_ELEMENT, fbNetwork);

		
		return fbNetworkElement;
	}
	
	private void addFBNetworkElements(final Element fbNetwork, final FBNetwork network) {
		for (FBNetworkElement element : network.getNetworkElements()) {			
			Element fbElement = createFNElementDomNode(element);
			if(null != fbElement){				
				setNameAttribute(fbElement, element.getName());
				if(null != element.getType()){
					setTypeAttribute(fbElement, element.getType());
				}
				setCommentAttribute(fbElement, element);					
				exportXandY(element, fbElement);					
				addParamsConfig(fbElement, element.getInterface().getInputVars());
				fbNetwork.appendChild(fbElement);
			}
		}
	}
	
	private Element createFNElementDomNode(FBNetworkElement element) {
		if (!(element.getType() instanceof AdapterFBType)) {
			if((element instanceof FB) && !(element instanceof ResourceTypeFB)) {				
				return createElement(LibraryElementTags.FB_ELEMENT);
			}
			if(element instanceof SubApp){
				Element subAppElement = createElement(LibraryElementTags.SUBAPP_ELEMENT); 
				if(null == ((SubApp)element).getType()){
					//we have an untyped subapp therefore add the subapp contents to it
					createUntypedSubAppcontents(subAppElement, (SubApp)element);
				}
				return subAppElement;
			}
		}
		return null; 
	}
	
	private void createUntypedSubAppcontents(Element subAppElement, SubApp element) {
		new SubApplicationTypeExporter(getDom()).addInterfaceList(subAppElement, element.getInterface());	
		if(null != element.getSubAppNetwork()){
			//if mapped the subapp may be empty
			subAppElement.appendChild(new FBNetworkExporter(getDom()).createFBNetworkElement(element.getSubAppNetwork()));
		}
	}


	private void addConnections(final Element fbNetworkElement, final List<? extends Connection> connections, 
			final String connectionElementName, FBNetwork fbNetwork) {
		Element connectionList = createElement(connectionElementName);
		for (Connection connection : connections) {
			addConnection(connectionList, connection, fbNetwork);
		}

		if (!connections.isEmpty()) {
			fbNetworkElement.appendChild(connectionList);
		}
	}

	private void addConnection(final Element connectionsContainer, final Connection connection, FBNetwork fbNetwork) {
		Element connectionElement = createElement(LibraryElementTags.CONNECTION_ELEMENT);
		if (connection.getSource() != null
				&& connection.getSource().eContainer() instanceof InterfaceList) {
			connectionElement.setAttribute(LibraryElementTags.SOURCE_ATTRIBUTE, 
					getConnectionEndpointIdentifier(connection.getSource(), fbNetwork));			
		} 
		
		if (connection.getDestination() != null
				&& connection.getDestination().eContainer() instanceof InterfaceList) {
			connectionElement.setAttribute(LibraryElementTags.DESTINATION_ATTRIBUTE, 
					getConnectionEndpointIdentifier(connection.getDestination(), fbNetwork));
		} 
		setCommentAttribute(connectionElement, connection);
		setConnectionCoordinates(connection, connectionElement);
		connectionsContainer.appendChild(connectionElement);
	}

	private static String getConnectionEndpointIdentifier(IInterfaceElement interfaceElement, FBNetwork fbNetwork) {
		String retVal = ""; //$NON-NLS-1$
		if(null != interfaceElement.getFBNetworkElement() && 
				interfaceElement.getFBNetworkElement().getFbNetwork() == fbNetwork){  // this is here to detect that interface elements of subapps
			retVal = interfaceElement.getFBNetworkElement().getName() + ".";  ////$NON-NLS-1$
		} 
			
		retVal += interfaceElement.getName();		
		return retVal;
	}


	private static void setConnectionCoordinates(
			final Connection connection, Element connectionElement) {
		if(0 != connection.getDx1()) {
			//only export connection routing information if not a straight line
			connectionElement.setAttribute(LibraryElementTags.DX1_ATTRIBUTE,
					reConvertCoordinate(connection.getDx1()).toString());
			if(0 != connection.getDx2()) {
				//only export the second two if a five segment connection
				connectionElement.setAttribute(LibraryElementTags.DX2_ATTRIBUTE,
						reConvertCoordinate(connection.getDx2()).toString());
				connectionElement.setAttribute(LibraryElementTags.DY_ATTRIBUTE,
						reConvertCoordinate(connection.getDy()).toString());
			}
		}
	}

}
