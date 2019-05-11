/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class DeviceDeploymentData {
	
	private final Device dev;
	
	/** indication if the full device is selected and the full device should be started
	 */
	private boolean fullDevice = false; 

	private List<ResourceDeploymentData> resData = new ArrayList<>();
	
	private List<VarDeclaration> selectedDevParams =  Collections.emptyList();
	
	public DeviceDeploymentData(Device device) {
		dev = device;
	}
	
	public Device getDevice() {
		return dev;
	}
	
	public boolean isFullDevice() {
		return fullDevice;
	}
	
	public void setFullDevice(boolean fullDevice) {
		this.fullDevice = fullDevice;
	}

	public void addResourceData(ResourceDeploymentData data) {
		resData.add(data);
	}
	
	public List<ResourceDeploymentData> getResData(){
		return resData;
	}
	
	public void setSeltectedDevParams(List<VarDeclaration> selParams) {
		selectedDevParams = Collections.unmodifiableList(new ArrayList<>(selParams));
	}
	
	
	/**Get the unmodifyable list of the selecte params to be downloaded to this device 
	 */
	public List<VarDeclaration> getSelectedDevParams(){
		return selectedDevParams;
	}
		
}
