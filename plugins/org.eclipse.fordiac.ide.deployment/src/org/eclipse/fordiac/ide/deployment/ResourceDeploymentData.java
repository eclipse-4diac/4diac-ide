/*******************************************************************************
 * Copyright (c) 2017, 2018 fortiss GmbH
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

import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/** Class for storing the information for deplyoing a resources
 * 
 *  This is the FBs collected from the resource and the mapped subapps as well as the connections and the
 *  subapp interface crossing connections
 */
class ResourceDeploymentData {
	
	public class ParameterData{
		public String value;
		public VarDeclaration var;
		public String prefix;
		public ParameterData(String value, String prefix, VarDeclaration var) {
			super();
			this.value = value;
			this.var = var;
			this.prefix = prefix;
		}			
	}
	
	public final Resource res;

	public List<FBDeploymentData> fbs = new ArrayList<>();
	
	public List<ConnectionDeploymentData> connections = new ArrayList<>();
	
	public List<ParameterData> params = new ArrayList<>();
		
	public ResourceDeploymentData(final Resource res){
		this.res = res;
		addFBNetworkElements(new ArrayDeque<SubApp>(), res.getFBNetwork(), ""); //$NON-NLS-1$
	}
	
	private void addFBNetworkElements(Deque<SubApp> subAppHierarchy, FBNetwork fbNetwork, String prefix) {
		for (FBNetworkElement fbnElement : fbNetwork.getNetworkElements()) {
			if(fbnElement instanceof FB){
				fbs.add(new FBDeploymentData(prefix, (FB)fbnElement));
			}else if(fbnElement instanceof SubApp){
				addSubAppParams((SubApp)fbnElement, subAppHierarchy, prefix);
				FBNetwork subAppInternalNetwork = getFBNetworkForSubApp((SubApp)fbnElement);
				if(null != subAppInternalNetwork) {    //TODO somehow inform the user that we could not get the internals of the subapp and therefore are not deploying its internals
					subAppHierarchy.addLast((SubApp)fbnElement);
					addFBNetworkElements(subAppHierarchy, subAppInternalNetwork, prefix + fbnElement.getName() + "."); //$NON-NLS-1$
					subAppHierarchy.removeLast();
				}
			}
		}
		for (Connection con : fbNetwork.getEventConnections()) {
			addConnection(subAppHierarchy, con, prefix);
		}
		for (Connection con : fbNetwork.getDataConnections()) {
			addConnection(subAppHierarchy, con, prefix);
		}
		for (Connection con : fbNetwork.getAdapterConnections()) {
			addConnection(subAppHierarchy, con, prefix);
		}
		
	}
	
	private void addSubAppParams(SubApp subApp, Deque<SubApp> subAppHierarchy, String prefix) {
		for (VarDeclaration dataInput : subApp.getInterface().getInputVars()) {
			String val = DeploymentHelper.getVariableValue(dataInput, res.getAutomationSystem());
			if(null != val){
				for (ConDeploymentDest destData : getSubappInterfaceconnections(subAppHierarchy, prefix, dataInput)) {
					params.add(new ParameterData(val, destData.prefix, (VarDeclaration)destData.destination));
				}	
			}
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

	private void addConnection(Deque<SubApp> subAppHierarchy, Connection con, String prefix){
		//Only handle the conneciton if it is no subapp, typed subapp originated or resourcetype connection
		if(null != con.getSourceElement() && !(con.getSourceElement() instanceof SubApp) && !con.isResTypeConnection()) {
			for (ConDeploymentDest destData : getConnectionEndPoint(subAppHierarchy, prefix, con.getDestination())) {
				connections.add(new ConnectionDeploymentData(prefix, con.getSource(), destData.prefix, destData.destination));				
			}			
		}
	}
	
	private List<ConDeploymentDest> getConnectionEndPoint(Deque<SubApp> subAppHierarchy, String prefix, IInterfaceElement destination) {
		ArrayList<ConDeploymentDest> retVal = new ArrayList<>();
		if(null != destination.getFBNetworkElement() && !(destination.getFBNetworkElement() instanceof SubApp)) {
			//we reached an FB endpoint return it
			retVal.add(new ConDeploymentDest(prefix, destination));
		} else {
			retVal.addAll(getSubappInterfaceconnections(subAppHierarchy, prefix, destination));
		}
		return retVal;
	}

	private List<ConDeploymentDest> getSubappInterfaceconnections(Deque<SubApp> subAppHierarchy, String prefix, IInterfaceElement destination) {
		ArrayList<ConDeploymentDest> retVal = new ArrayList<>();
		if(destination.isIsInput()) {	
			//we are entering a subapplication
			String newPrefix = prefix + destination.getFBNetworkElement().getName() + "."; //$NON-NLS-1$
			subAppHierarchy.addLast((SubApp)destination.getFBNetworkElement());
			IInterfaceElement internalElement = getSubAppInteralElement(destination);
			for(Connection con : internalElement.getOutputConnections()) {
				retVal.addAll(getConnectionEndPoint(subAppHierarchy, newPrefix, con.getDestination()));
			}
			subAppHierarchy.removeLast();
		} else {
			//we are leaving a subapp
			String newPrefix = removeLastEntry(prefix);
			SubApp currentSubApp = subAppHierarchy.removeLast();
			IInterfaceElement internalElement = currentSubApp.getInterfaceElement(destination.getName());
			for(Connection con : internalElement.getOutputConnections()) {
				retVal.addAll(getConnectionEndPoint(subAppHierarchy, newPrefix, con.getDestination()));
			}			
			subAppHierarchy.addLast(currentSubApp);
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
	
	private String removeLastEntry(String prefix) {
		int index = prefix.lastIndexOf('.', prefix.length() - 2);
		if(-1 != index) {
			return prefix.substring(0, index);
		}
		return ""; //$NON-NLS-1$
	}
	
}
