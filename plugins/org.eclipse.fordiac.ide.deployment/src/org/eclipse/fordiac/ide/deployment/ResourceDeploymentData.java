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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

/** Class for storing the information for deplyoing a resources
 * 
 *  This is the FBs collected from the resource and the mapped subapps as well as the connections and the
 *  subapp interface crossing connections
 */
class ResourceDeploymentData {
	
	class SubAppInterfaceCrossingConnection{
		public IInterfaceElement source;
		public IInterfaceElement destination;
	}
	
	public final Resource res;

	public List<FB> fbs = new ArrayList<>();
	
	public List<Connection> connections = new ArrayList<>();
	
	public List<SubAppInterfaceCrossingConnection> subAppInterfaceCrossingConns = new ArrayList<>();
	
	public ResourceDeploymentData(final Resource res){
		this.res = res;
		addFBNetworkElements(res.getFBNetwork());
	}
	
	private void addFBNetworkElements(FBNetwork fbNetwork) {
		for (FBNetworkElement fbnElement : fbNetwork.getNetworkElements()) {
			if(fbnElement instanceof FB){
				fbs.add((FB)fbnElement);
			}else if(fbnElement instanceof SubApp){
				//TODO get inner subapp element and recursivly go through the elemetns in it
			}
		}
		for (Connection con : fbNetwork.getEventConnections()) {
			addConnection(con);
		}
		for (Connection con : fbNetwork.getDataConnections()) {
			addConnection(con);
		}
		for (Connection con : fbNetwork.getAdapterConnections()) {
			addConnection(con);
		}
		
	}
	
	private void addConnection(Connection con){
		//TODO for typed subapps we may have to check also for null
		if(con.getSourceElement() instanceof SubApp){
			//we don't need to do anything here as we area always creating subapp interface crossing connections from source to dest
		} else if(con.getSourceElement() instanceof SubApp){
			//TODO model refactoring find the non sub app endpoint for this connection
		} else{
			connections.add(con);
		}
	}

	private void addSubAppInterfaceCrossingConnection(IInterfaceElement source, IInterfaceElement destination){
		SubAppInterfaceCrossingConnection con = new SubAppInterfaceCrossingConnection();
		con.source = source;
		con.destination = destination;
		subAppInterfaceCrossingConns.add(con);
	}
	
}
