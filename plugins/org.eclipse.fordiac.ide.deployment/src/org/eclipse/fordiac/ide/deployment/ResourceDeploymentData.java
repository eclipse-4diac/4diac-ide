/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

/** Class for storing the information for deplyoing a resources
 * 
 *  This is the FBs collected from the resource and the mapped subapps as well as the connections and the
 *  subapp interface crossing connections
 */
class ResourceDeploymentData {
	
	public final Resource res;

	public List<FBDeploymentData> fbs = new ArrayList<>();
	
	public List<ConnectionDeploymentData> connections = new ArrayList<>();
		
	public ResourceDeploymentData(final Resource res){
		this.res = res;
		Deque<FBNetwork> fbNetworkhierachy = new ArrayDeque<>();
		fbNetworkhierachy.add(res.getFBNetwork());
		addFBNetworkElements(fbNetworkhierachy, ""); //$NON-NLS-1$
	}
	
	private void addFBNetworkElements(Deque<FBNetwork> fbNetworkHierarchy, String prefix) {
		FBNetwork fbNetwork = fbNetworkHierarchy.peekLast();
		for (FBNetworkElement fbnElement : fbNetwork.getNetworkElements()) {
			if(fbnElement instanceof FB){
				fbs.add(new FBDeploymentData(prefix, (FB)fbnElement));
			}else if(fbnElement instanceof SubApp){
				FBNetwork subAppInternalNetwork = getFBNetworkForSubApp((SubApp)fbnElement);
				if(null != subAppInternalNetwork) {    //TODO somehow inform the user that we could not get the internals of the subapp and therefore are not deploying its internals
					fbNetworkHierarchy.addLast(subAppInternalNetwork);
					addFBNetworkElements(fbNetworkHierarchy, prefix + fbnElement.getName() + "."); //$NON-NLS-1$
					fbNetworkHierarchy.removeLast();
				}
			}
		}
		for (Connection con : fbNetwork.getEventConnections()) {
			addConnection(con, prefix);
		}
		for (Connection con : fbNetwork.getDataConnections()) {
			addConnection(con, prefix);
		}
		for (Connection con : fbNetwork.getAdapterConnections()) {
			addConnection(con, prefix);
		}
		
	}
	
	private FBNetwork getFBNetworkForSubApp(SubApp subApp) {
		FBNetwork retVal = subApp.getSubAppNetwork();
		if(null == retVal) {
			if(null != subApp.getType()) {
				//we have a typed subapp
				retVal = subApp.getType().getFBNetwork();
			} else if(null != subApp.getOpposite()) {
				retVal = ((SubApp)subApp.getOpposite()).getSubAppNetwork();
			}
		}		
		return retVal;
	}
	
	private class ConDeploymentDest{
		final String prefix;
		final IInterfaceElement destination;
		
		public ConDeploymentDest(String prefix, IInterfaceElement destination) {
			super();
			this.prefix = prefix;
			this.destination = destination;
		}
		
	}

	private void addConnection(Connection con, String prefix){
		//Only handle the conneciton if it is no subapp, typed subapp originated or resourcetype connection
		if(null != con.getSourceElement() && !(con.getSourceElement() instanceof SubApp) && !con.isResTypeConnection()) {
			for (ConDeploymentDest destData : getConnectionEndPoint(prefix, con.getDestination())) {
				connections.add(new ConnectionDeploymentData(prefix, con.getSource(), destData.prefix, destData.destination));				
			}			
		}
	}
	
	private List<ConDeploymentDest> getConnectionEndPoint(String prefix, IInterfaceElement destination) {
		ArrayList<ConDeploymentDest> retVal = new ArrayList<>();
		if(null != destination.getFBNetworkElement() && !(destination.getFBNetworkElement() instanceof SubApp)) {
			//we reached an FB endpoint return it
			retVal.add(new ConDeploymentDest(prefix, destination));
		} else {
			retVal.addAll(getSubappInterfaceconnections(prefix, destination));
		}
		return retVal;
	}

	private List<ConDeploymentDest> getSubappInterfaceconnections(String prefix, IInterfaceElement destination) {
		ArrayList<ConDeploymentDest> retVal = new ArrayList<>();
		if(destination.isIsInput()) {	
			//we are entering a subapplication
			String newPrefix = prefix + destination.getFBNetworkElement().getName() + "."; //$NON-NLS-1$
			IInterfaceElement internalElement = getSubAppInteralElement(destination);
			for(Connection con : internalElement.getOutputConnections()) {
				retVal.addAll(getConnectionEndPoint(newPrefix, con.getDestination()));
			}
		}
		
		return retVal;
	}

	private IInterfaceElement getSubAppInteralElement(IInterfaceElement destination) {
		SubApp subApp = (SubApp)destination.getFBNetworkElement();
		if(null != subApp.getSubAppNetwork()) {
			return destination;
		}else {
			InterfaceList interfaceList = null;
			if(null != subApp.getType()) {
				//we have a typed subapp
				interfaceList = subApp.getType().getInterfaceList();
			} else if(null != subApp.getOpposite()) {
				interfaceList = ((SubApp)subApp.getOpposite()).getInterface();
			}
			if(null != interfaceList) {
				return interfaceList.getInterfaceElement(destination.getName());
			}
		}
		return null;
	}

	
}
