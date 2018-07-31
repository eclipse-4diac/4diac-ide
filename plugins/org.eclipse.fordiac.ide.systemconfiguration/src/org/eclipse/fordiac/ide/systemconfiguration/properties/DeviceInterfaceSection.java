/*******************************************************************************
 * Copyright (c) 2017 -2018 fortiss GmbH, Johannes Kepler University
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Monika Wenger, Alois Zoitl 
 *      - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.properties;

import java.util.List;

import org.eclipse.fordiac.ide.deployment.Activator;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.iec61499.DynamicTypeLoadDeploymentExecutor;
import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.gef.properties.AbstractDeviceInterfaceSection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.DeviceEditPart;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

public class DeviceInterfaceSection extends AbstractDeviceInterfaceSection {
	@Override
	protected Device getInputType(Object input) {
		if(input instanceof DeviceEditPart){
			return ((DeviceEditPart) input).getModel();
		}
		return null;
	}

	@Override
	protected void createFBInfoGroup(Composite parent) {
		super.createFBInfoGroup(parent);
		profile.setItems(getAvailableProfileNames());
		getResources.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {	
				if(type instanceof Device){
					IDeviceManagementInteractor interactor = DeviceManagementInteractorFactory.INSTANCE.getDeviceManagementInteractor((Device)getType());
					if(interactor instanceof DynamicTypeLoadDeploymentExecutor) {
						DeploymentCoordinator.getInstance().enableOutput(interactor);
						try {
							interactor.connect();
							((DynamicTypeLoadDeploymentExecutor) interactor).queryResources((Device)getType());
						} catch (Exception e) {
							Activator.getDefault().logError(e.getMessage(), e);
						}finally {
							try {
								interactor.disconnect();
							} catch (DeploymentException e) {
								Activator.getDefault().logError(e.getMessage(), e);
							}							
						}
						DeploymentCoordinator.getInstance().disableOutput(interactor);
					}
				}
			}
		});
	}
	
	private static String[] getAvailableProfileNames() {
		if (null == profileNames) {
			List<String> newProfileNames = DeviceManagementInteractorFactory.INSTANCE.getAvailableProfileNames();
			profileNames = newProfileNames.toArray(new String[newProfileNames.size()]);
		}
		return profileNames;
	}
}
