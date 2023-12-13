/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.runtime;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.util.stream.Stream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.fordiac.ide.debug.ui.MainLaunchConfigurationTab;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.fordiac.ide.deployment.eval.DeploymentEvaluatorConfiguration;
import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.DeviceTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.ResourceTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class RuntimeLaunchConfigurationTab extends AbstractLaunchConfigurationTab {
	public static final String ID = "org.eclipse.fordiac.ide.deployment.debug.ui.runtimeTab"; //$NON-NLS-1$

	private Text runtimeText;
	private Combo profileCombo;
	private Text deviceTypeText;
	private Text resourceTypeText;

	@Override
	public void createControl(final Composite parent) {
		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(comp);
		setControl(comp);

		final Composite runtimeComponent = createRuntimeComponent(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(runtimeComponent);

		final Composite optionsComponent = createOptionsComponent(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(optionsComponent);
	}

	protected Composite createRuntimeComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().applyTo(group);
		group.setText(Messages.RuntimeLaunchConfigurationTab_RuntimeGroup);

		final Composite comp = new Composite(group, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(comp);

		final Label locationLabel = new Label(comp, SWT.NONE);
		locationLabel.setText(Messages.RuntimeLaunchConfigurationTab_LocationLabel);
		GridDataFactory.swtDefaults().applyTo(locationLabel);

		runtimeText = new Text(comp, SWT.BORDER);
		runtimeText.setMessage(Messages.RuntimeLaunchConfigurationTab_LocationMessage);
		runtimeText.addModifyListener(e -> scheduleUpdateJob());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(runtimeText);
		return group;
	}

	protected Composite createOptionsComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().applyTo(group);
		group.setText(Messages.RuntimeLaunchConfigurationTab_OptionsGroup);

		final Composite comp = new Composite(group, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(3).applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(comp);

		final Label profileLabel = new Label(comp, SWT.NONE);
		profileLabel.setText(Messages.RuntimeLaunchConfigurationTab_ProfileLabel);
		GridDataFactory.swtDefaults().applyTo(profileLabel);

		profileCombo = new Combo(comp, SWT.DROP_DOWN | SWT.READ_ONLY);
		profileCombo
				.setItems(DeviceManagementInteractorFactory.INSTANCE.getAvailableProfileNames().toArray(String[]::new));
		profileCombo.addModifyListener(e -> updateLaunchConfigurationDialog());
		GridDataFactory.fillDefaults().span(2, 1).grab(true, false).applyTo(profileCombo);

		final Label deviceTypeLabel = new Label(comp, SWT.NONE);
		deviceTypeLabel.setText(Messages.RuntimeLaunchConfigurationTab_DeviceTypeLabel);
		GridDataFactory.swtDefaults().applyTo(deviceTypeLabel);

		deviceTypeText = new Text(comp, SWT.BORDER);
		deviceTypeText.setEditable(false);
		deviceTypeText.setMessage(Messages.RuntimeLaunchConfigurationTab_DeviceTypeMessage);
		deviceTypeText.addModifyListener(e -> scheduleUpdateJob());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(deviceTypeText);

		final Button deviceTypeButton = new Button(comp, SWT.BORDER);
		deviceTypeButton.setText(Messages.RuntimeLaunchConfigurationTab_Dots);
		deviceTypeButton.addSelectionListener(widgetSelectedAdapter(e -> handleDeviceTypeButtonSelected()));
		GridDataFactory.swtDefaults().applyTo(deviceTypeButton);

		final Label resourceTypeLabel = new Label(comp, SWT.NONE);
		resourceTypeLabel.setText(Messages.RuntimeLaunchConfigurationTab_ResourceTypeLabel);
		GridDataFactory.swtDefaults().applyTo(resourceTypeLabel);

		resourceTypeText = new Text(comp, SWT.BORDER);
		resourceTypeText.setEditable(false);
		resourceTypeText.setMessage(Messages.RuntimeLaunchConfigurationTab_ResourceTypeMessage);
		resourceTypeText.addModifyListener(e -> scheduleUpdateJob());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(resourceTypeText);

		final Button resourceTypeButton = new Button(comp, SWT.BORDER);
		resourceTypeButton.setText(Messages.RuntimeLaunchConfigurationTab_Dots);
		resourceTypeButton.addSelectionListener(widgetSelectedAdapter(e -> handleResourceTypeButtonSelected()));
		GridDataFactory.swtDefaults().applyTo(resourceTypeButton);
		return group;
	}

	private void handleDeviceTypeButtonSelected() {
		final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(getShell(),
				DeviceTypeSelectionTreeContentProvider.INSTANCE);
		dialog.setTitle(Messages.RuntimeLaunchConfigurationTab_DeviceTypeDialogTitle);
		dialog.setMessage(Messages.RuntimeLaunchConfigurationTab_DeviceTypeDialogMessage);
		dialog.setInput(getTypeLibrary());
		dialog.setAllowMultiple(false);
		if ((dialog.open() == Window.OK)
				&& (dialog.getFirstResult() instanceof final TypeNode node && !node.isDirectory())) {
			deviceTypeText.setText(node.getFullName());
			scheduleUpdateJob();
		}
	}

	private void handleResourceTypeButtonSelected() {
		final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(getShell(),
				ResourceTypeSelectionTreeContentProvider.INSTANCE);
		dialog.setTitle(Messages.RuntimeLaunchConfigurationTab_ResourceTypeDialogTitle);
		dialog.setMessage(Messages.RuntimeLaunchConfigurationTab_ResourceTypeDialogMessage);
		dialog.setInput(getTypeLibrary());
		dialog.setAllowMultiple(false);
		if ((dialog.open() == Window.OK)
				&& (dialog.getFirstResult() instanceof final TypeNode node && !node.isDirectory())) {
			resourceTypeText.setText(node.getFullName());
			scheduleUpdateJob();
		}
	}

	protected TypeLibrary getTypeLibrary() {
		final IResource targetResource = getTargetResource();
		if (targetResource != null) {
			return TypeLibraryManager.INSTANCE.getTypeLibrary(targetResource.getProject());
		}
		return null;
	}

	protected IResource getTargetResource() {
		final MainLaunchConfigurationTab mainLaunchConfigurationTab = getMainLaunchConfigurationTab();
		if (mainLaunchConfigurationTab != null) {
			return mainLaunchConfigurationTab.getResource();
		}
		return null;
	}

	protected MainLaunchConfigurationTab getMainLaunchConfigurationTab() {
		return (MainLaunchConfigurationTab) Stream.of(getLaunchConfigurationDialog().getTabs())
				.filter(MainLaunchConfigurationTab.class::isInstance).findAny().orElse(null);
	}

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.removeAttribute(DeploymentEvaluatorConfiguration.MGR_ID);
		configuration.removeAttribute(DeploymentEvaluatorConfiguration.DEVICE_PROFILE);
		configuration.removeAttribute(DeploymentEvaluatorConfiguration.DEVICE_TYPE);
		configuration.removeAttribute(DeploymentEvaluatorConfiguration.RESOURCE_TYPE);
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		try {
			runtimeText.setText(configuration.getAttribute(DeploymentEvaluatorConfiguration.MGR_ID,
					DeploymentEvaluatorConfiguration.DEFAULT_MGR_ID));
			profileCombo.setText(configuration.getAttribute(DeploymentEvaluatorConfiguration.DEVICE_PROFILE,
					DeploymentEvaluatorConfiguration.DEFAULT_DEVICE_PROFILE));
			deviceTypeText.setText(configuration.getAttribute(DeploymentEvaluatorConfiguration.DEVICE_TYPE,
					DeploymentEvaluatorConfiguration.DEFAULT_DEVICE_TYPE));
			resourceTypeText.setText(configuration.getAttribute(DeploymentEvaluatorConfiguration.RESOURCE_TYPE,
					DeploymentEvaluatorConfiguration.DEFAULT_RESOURCE_TYPE));
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(DeploymentEvaluatorConfiguration.MGR_ID, runtimeText.getText());
		configuration.setAttribute(DeploymentEvaluatorConfiguration.DEVICE_PROFILE, profileCombo.getText());
		configuration.setAttribute(DeploymentEvaluatorConfiguration.DEVICE_TYPE, deviceTypeText.getText());
		configuration.setAttribute(DeploymentEvaluatorConfiguration.RESOURCE_TYPE, resourceTypeText.getText());
	}

	@Override
	public String getName() {
		return Messages.RuntimeLaunchConfigurationTab_RuntimeTabName;
	}

	@Override
	public String getId() {
		return ID;
	}
}
