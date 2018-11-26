/*******************************************************************************
 * Copyright (c) 2012 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Matthias Plasch, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring     
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.deployment.monitoringbase.IMonitoringListener;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.gef.editparts.IChildrenProvider;
import org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;


public class MonitoringChildren implements IMonitoringListener,
		IChildrenProvider {

	public MonitoringChildren() {
		MonitoringManager.getInstance().registerMonitoringListener(this);
	}

	@Override
	public List<IEditPartCreator> getChildren(FBNetwork fbNetwork) {
		List<IEditPartCreator> arrayList = new ArrayList<>();
		
		//TODO - model refactoring fetch only the list of monitored elements of the same system
		for (MonitoringBaseElement element : MonitoringManager.getInstance().getElementsToMonitor()) {			
			if(null != element){
				if(fbNetwork.getNetworkElements().contains(element.getPort().getFb())){
					arrayList.add(element);
				}
				else if(null != element.getPort().getFb().getResource() && (!element.getPort().getFb().isResourceFB())){
					//check if we are in the resource diagram editor for a mapped FB	
					 if(element.getPort().getFb().getResource().getFBNetwork().equals(fbNetwork)){
						arrayList.add(element);
					 }
				}
			}
			
		}
		return arrayList;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


	@Override
	public void notifyAddPort(PortElement port) {
		// nothing to do
	}

	@Override
	public void notifyRemovePort(PortElement port) {
		// nothing to do
	}

	@Override
	public void notifyTriggerEvent(PortElement port) {
		// nothing to do
	}

}
