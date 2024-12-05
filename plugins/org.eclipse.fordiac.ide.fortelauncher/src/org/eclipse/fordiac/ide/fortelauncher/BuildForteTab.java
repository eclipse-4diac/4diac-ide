/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fortelauncher;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.RefreshUtil;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.fordiac.ide.runtime.RuntimeLaunchTab;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.widget.DirectoryChooserControl;
import org.eclipse.fordiac.ide.ui.widget.FileChooserControl;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class BuildForteTab extends AbstractLaunchConfigurationTab {

	private static final String ATTR_BUILD_ARGUMENTS = "org.eclipse.fordiac.ide.fortebuilder.arguments"; //$NON-NLS-1$

	private FileChooserControl cmakeChooser;
	private DirectoryChooserControl buildLocationChooser;
	private Text argumentsField;

	@Override
	public void createControl(final Composite parent) {
		GridLayoutFactory.swtDefaults().applyTo(parent);
		GridDataFactory.fillDefaults().align(GridData.FILL, GridData.BEGINNING).grab(true, false).applyTo(parent);

		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(comp);
		GridDataFactory.fillDefaults().align(GridData.FILL, GridData.BEGINNING).grab(true, false).applyTo(comp);
		setControl(comp);

		cmakeChooser = new FileChooserControl(comp, SWT.NONE, Messages.BuildForteTab_CMakeLocation, true);
		cmakeChooser.addChooserValueChangedListener(newVal -> scheduleUpdateJob());

		buildLocationChooser = new DirectoryChooserControl(comp, SWT.NONE, Messages.BuildForteTab_BuildDirectory, true);
		buildLocationChooser.addChooserValueChangedListener(newVal -> scheduleUpdateJob());

		createArguments(comp);
	}

	private void createArguments(final Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.FortePreferencePage_Arguments);
		argumentsField = new Text(parent, SWT.BORDER);
		GridDataFactory.fillDefaults().align(GridData.FILL, GridData.CENTER).applyTo(argumentsField);
		argumentsField.addModifyListener(ev -> scheduleUpdateJob());
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		try {
			cmakeChooser.setValue(configuration.getAttribute(RuntimeLaunchTab.ATTR_LOCATION, "")); //$NON-NLS-1$
			buildLocationChooser.setValue(configuration.getAttribute(RuntimeLaunchTab.ATTR_WORKING_DIRECTORY, "")); //$NON-NLS-1$
			argumentsField.setText(configuration.getAttribute(ATTR_BUILD_ARGUMENTS, "")); //$NON-NLS-1$
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(RuntimeLaunchTab.ATTR_LOCATION, cmakeChooser.getValue());
		configuration.setAttribute(RuntimeLaunchTab.ATTR_WORKING_DIRECTORY, buildLocationChooser.getValue());
		configuration.setAttribute(ATTR_BUILD_ARGUMENTS, argumentsField.getText());
		configuration.setAttribute(RuntimeLaunchTab.ATTR_TOOL_ARGUMENTS, "--build . " + argumentsField.getText()); //$NON-NLS-1$
	}

	@Override
	public boolean isValid(final ILaunchConfiguration launchConfig) {
		setErrorMessage(null);

		if (cmakeChooser.getValue().isBlank()) {
			setErrorMessage(Messages.ForteLaunchTabError_NoCMakeExecutableSet);
			return false;
		}
		if (buildLocationChooser.getValue().isBlank()) {
			setErrorMessage(Messages.BuildForteTab_NoBuildDirectory);
			return false;
		}
		return true;
	}

	@Override
	public String getName() {
		return Messages.BuildForteTab_TabTitle;
	}

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.removeAttribute(RuntimeLaunchTab.ATTR_LOCATION);
		configuration.removeAttribute(RuntimeLaunchTab.ATTR_WORKING_DIRECTORY);
		configuration.removeAttribute(RuntimeLaunchTab.ATTR_TOOL_ARGUMENTS);
		configuration.removeAttribute(RefreshUtil.ATTR_REFRESH_SCOPE);
		configuration.setAttribute(RuntimeLaunchTab.ATTR_BUILD_SCOPE, "NONE"); //$NON-NLS-1$
		configuration.setAttribute(RuntimeLaunchTab.ATTR_APPEND_ENVIRONMENT_VARIABLES, true);
		configuration.setAttribute(RuntimeLaunchTab.ATTR_LUNCH_IN_BACKGROUND, false);
		configuration.setAttribute(DebugPlugin.ATTR_MERGE_OUTPUT, true);
		configuration.removeAttribute(ATTR_BUILD_ARGUMENTS);
	}

}
