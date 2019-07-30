/*******************************************************************************
 * Copyright (c) 2017 -2019 fortiss GmbH, Johannes Kepler University
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Monika Wenger, Alois Zoitl 
 *      - initial API and implementation and/or initial documentation
 *    Bianca Wiesmayr
 *      - merged two DeviceInterfaceSection plus Abstract Class into DeviceSection
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.properties;

import java.util.List;
import org.eclipse.fordiac.ide.deployment.Activator;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.iec61499.DynamicTypeLoadDeploymentExecutor;
import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.gef.commands.ChangeProfileCommand;
import org.eclipse.fordiac.ide.gef.properties.AbstractDevResInterfaceSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;


public class DeviceSection extends AbstractDevResInterfaceSection {
	protected static String[] profileNames;
	protected Combo profile;
	protected Button getResources;

	@Override
	public void refresh() {
		super.refresh();
		if (null != type) {
			setProfile();
			getResources.setEnabled("DynamicTypeLoad".equals(((Device) getType()).getProfile()));
		}
	}

	private void setProfile() {
		int i = 0;
		for (String p : profile.getItems()) {
			if (p.equals(((Device) getType()).getProfile())) {
				profile.select(i);
				break;
			}
			i++;
		}
	}

	@Override
	protected Device getInputType(Object input) {
		if (input instanceof EditPart) {
			input = ((EditPart) input).getModel();
		}
		if (input instanceof Device) {
			return ((Device) input);
		}
		return null;
	}

	@Override
	protected void createFBInfoGroup(Composite parent) {
		createDeviceInterfaceTab(parent);
		profile.setItems(getAvailableProfileNames());
		getResources.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (type instanceof Device) {
					IDeviceManagementInteractor interactor = DeviceManagementInteractorFactory.INSTANCE
							.getDeviceManagementInteractor((Device) getType());
					if (interactor instanceof DynamicTypeLoadDeploymentExecutor) {
						DeploymentCoordinator.INSTANCE.enableOutput(interactor);
						try {
							interactor.connect();
							((DynamicTypeLoadDeploymentExecutor) interactor)
									.queryResourcesWithNetwork((Device) getType());
						} catch (Exception e) {
							Activator.getDefault().logError(e.getMessage(), e);
						} finally {
							try {
								interactor.disconnect();
							} catch (DeploymentException e) {
								Activator.getDefault().logError(e.getMessage(), e);
							}
						}
						DeploymentCoordinator.INSTANCE.disableOutput(interactor);
					}
				}
			}
		});
	}

	private void createDeviceInterfaceTab(Composite parent) {
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(4, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, "Instance Name:");
		nameText = createGroupText(composite, true);
		nameText.addModifyListener(event -> {
			removeContentAdapter();
			executeCommand(new ChangeNameCommand(getType(), nameText.getText()));
			addContentAdapter();
		});

		getWidgetFactory().createCLabel(composite, "Profile:");
		profile = new Combo(composite, SWT.SINGLE | SWT.READ_ONLY);
		profile.addListener(SWT.Selection, event -> {
			removeContentAdapter();
			executeCommand(new ChangeProfileCommand((Device) getType(), profile.getText()));
			refresh();
			addContentAdapter();
		});

		getWidgetFactory().createCLabel(composite, "Instance Comment:");
		commentText = createGroupText(composite, true);
		GridData gridData = new GridData(SWT.FILL, 0, true, false);
		commentText.setLayoutData(gridData);
		commentText.addModifyListener(event -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});

		getResources = getWidgetFactory().createButton(composite, "Fetch Resource", SWT.PUSH);
		getResources.setToolTipText("Fetch the resources currently running in the device.");
		GridData getResGridData = new GridData(SWT.FILL, 0, false, false);
		getResGridData.horizontalSpan = 2;
		getResources.setLayoutData(getResGridData);
	}

	protected static String[] getAvailableProfileNames() {
		if (null == profileNames) {
			List<String> newProfileNames = DeviceManagementInteractorFactory.INSTANCE.getAvailableProfileNames();
			profileNames = newProfileNames.toArray(new String[newProfileNames.size()]);
		}
		return profileNames;
	}
}
