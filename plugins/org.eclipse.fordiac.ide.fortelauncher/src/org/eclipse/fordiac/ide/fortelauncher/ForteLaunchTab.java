/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
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
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.fordiac.ide.fortelauncher.preferences.PreferenceConstants;
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

public class ForteLaunchTab extends RuntimeLaunchTab {

	private static final String DEFAULT_FORTE_PORT = "61499"; //$NON-NLS-1$
	private static final String ATTR_FORTE_PORT = "org.eclipse.fordiac.ide.fortelauncher.port"; //$NON-NLS-1$
	private static final String ATTR_FORTE_ARGUMENTS = "org.eclipse.fordiac.ide.fortelauncher.arguments"; //$NON-NLS-1$

	private FileChooserControl forteChooser;
	private DirectoryChooserControl workingDirChooser;
	private Text argumentsField;

	@Override
	public void createControl(final Composite parent) {
		GridLayoutFactory.swtDefaults().applyTo(parent);
		GridDataFactory.fillDefaults().align(GridData.FILL, GridData.BEGINNING).grab(true, false).applyTo(parent);

		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(comp);
		GridDataFactory.fillDefaults().align(GridData.FILL, GridData.BEGINNING).grab(true, false).applyTo(comp);
		setControl(comp);

		forteChooser = new FileChooserControl(comp, SWT.NONE, Messages.FortePreferencePage_FORTELocation, true);
		forteChooser.addChooserValueChangedListener(newVal -> scheduleUpdateJob());
		workingDirChooser = new DirectoryChooserControl(comp, SWT.NONE, Messages.ForteLaunchTab_WorkingDirectory, true);
		workingDirChooser.addChooserValueChangedListener(newVal -> scheduleUpdateJob());
		createPortSelection(comp);
		createArguments(comp);
	}

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		final String fortePath = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH);
		if (fortePath != null && !fortePath.isBlank()) {
			configuration.setAttribute(ATTR_LOCATION, fortePath);
		} else {
			configuration.removeAttribute(ATTR_LOCATION);
		}
		configuration.setAttribute(ATTR_FORTE_PORT, DEFAULT_FORTE_PORT);
		configuration.removeAttribute(ATTR_FORTE_ARGUMENTS);
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		try {
			forteChooser.setValue(configuration.getAttribute(ATTR_LOCATION, "")); //$NON-NLS-1$
			workingDirChooser.setValue(configuration.getAttribute(ATTR_WORKING_DIRECTORY, "")); //$NON-NLS-1$
			setPortValue(configuration.getAttribute(ATTR_FORTE_PORT, DEFAULT_FORTE_PORT));
			argumentsField.setText(configuration.getAttribute(ATTR_FORTE_ARGUMENTS, "")); //$NON-NLS-1$
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(ATTR_LOCATION, forteChooser.getValue());

		final String workingDir = workingDirChooser.getValue();
		if (!workingDir.isBlank()) {
			configuration.setAttribute(ATTR_WORKING_DIRECTORY, workingDir);
		}

		configuration.setAttribute(ATTR_FORTE_PORT, getPortValue());
		configuration.setAttribute(ATTR_FORTE_ARGUMENTS, argumentsField.getText());

		configuration.setAttribute(ATTR_TOOL_ARGUMENTS,
				"-c localhost:" + getPortValue() + " " + argumentsField.getText()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public String getName() {
		return Messages.ForteLaunchTab_TabTitle;
	}

	@Override
	public boolean isValid(final ILaunchConfiguration launchConfig) {
		if (!super.isValid(launchConfig)) {
			return false;
		}

		if (forteChooser.getValue().isBlank()) {
			setErrorMessage(Messages.ForteLaunchTabError_No4diacFORTEExecutableSet);
			return false;
		}

		return true;
	}

	private void createArguments(final Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.FortePreferencePage_Arguments);
		argumentsField = new Text(parent, SWT.BORDER);
		GridDataFactory.fillDefaults().align(GridData.FILL, GridData.CENTER).applyTo(argumentsField);
		argumentsField.addModifyListener(ev -> scheduleUpdateJob());
	}

}
