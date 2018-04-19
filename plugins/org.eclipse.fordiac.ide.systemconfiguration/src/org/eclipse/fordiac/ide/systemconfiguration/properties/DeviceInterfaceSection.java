/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
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

import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.IDeploymentExecutor;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ChangeProfileCommand;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.DeviceEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class DeviceInterfaceSection extends AbstractDevResInterfaceSection {
	private static String[] profileNames = null;
	private Combo profile;
	
	protected Device getInputType(Object input) {
		if(input instanceof DeviceEditPart){
			return ((DeviceEditPart) input).getModel();
		}
		return null;
	}

	@Override
	protected void createFBInfoGroup(Composite parent) {
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(4, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, "Instance Name:"); 
		nameText = createGroupText(composite, true);
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeNameCommand(getType(), nameText.getText()));
				addContentAdapter();
			}
		});
		
		getWidgetFactory().createCLabel(composite, "Profile:"); 
		profile = new Combo(composite, SWT.SINGLE | SWT.READ_ONLY);
		profile.setItems(getAvailableProfileNames());
		profile.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				removeContentAdapter();				
				executeCommand(new ChangeProfileCommand((Device) getType(), profile.getText()));
				refresh();
				addContentAdapter();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		getWidgetFactory().createCLabel(composite, "Instance Comment:"); 
		commentText = createGroupText(composite, true);
		GridData gridData = new GridData(SWT.FILL, 0, true, false);
		gridData.horizontalSpan = 3;
		commentText.setLayoutData(gridData);
		commentText.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				removeContentAdapter();
				executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
				addContentAdapter();
			}
		});
	}
	
	@Override
	public void refresh() {
		super.refresh();
		if(null != type) {
			setProfile();		
		}
	}
	
	private void setProfile() {
		int i = 0;
		for(String p : profile.getItems()){
			if(p.equals(((Device)getType()).getProfile())){
				profile.select(i);
				break;
			}
			i++;
		}
	}
	
	private static String[] getAvailableProfileNames() {
		if (null == profileNames) {
			List<IDeploymentExecutor> deploymentExecutors = DeploymentCoordinator.loadDeploymentExecutors();
			String newProfileNames[] = new String[deploymentExecutors.size()];
			int i = 0;		
			for (IDeploymentExecutor idepExec : deploymentExecutors) {
				newProfileNames[i] = idepExec.getProfileName();
				i++;
			}
			profileNames = newProfileNames;
		}
		return profileNames;
	}
}
