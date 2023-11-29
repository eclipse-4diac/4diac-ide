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
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.fordiac.ide.fortelauncher.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.runtime.LaunchRuntimeUtils;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.widget.DirectoryChooserControl;
import org.eclipse.fordiac.ide.ui.widget.FileChooserControl;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ForteLaunchTab extends AbstractLaunchConfigurationTab {

	private static final String DEFAULT_FORTE_PORT = "61499"; //$NON-NLS-1$
	private static final String ATTR_FORTE_LOCATION = "org.eclipse.ui.externaltools.ATTR_LOCATION"; //$NON-NLS-1$
	private static final String ATTR_WORKING_DIRECTORY = "org.eclipse.ui.externaltools.ATTR_WORKING_DIRECTORY"; //$NON-NLS-1$
	private static final String ATTR_FORTE_PORT = "org.eclipse.fordiac.ide.fortelauncher.port"; //$NON-NLS-1$
	private static final String ATTR_BUILD_SCOPE = "org.eclipse.ui.externaltools.ATTR_LAUNCH_CONFIGURATION_BUILD_SCOPE"; //$NON-NLS-1$

	private FileChooserControl forteChooser;
	private DirectoryChooserControl workingDirChooser;
	private Text portField;

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
	}

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		final String fortePath = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH);
		if (fortePath != null && !fortePath.isBlank()) {
			configuration.setAttribute(ATTR_FORTE_LOCATION, fortePath);
		} else {
			configuration.removeAttribute(ATTR_FORTE_LOCATION);
		}
		configuration.removeAttribute(LaunchRuntimeUtils.ATTR_TOOL_ARGUMENTS);
		configuration.removeAttribute(ATTR_WORKING_DIRECTORY);
		configuration.setAttribute(ATTR_FORTE_PORT, DEFAULT_FORTE_PORT);
		configuration.setAttribute(ATTR_BUILD_SCOPE, "NONE"); //$NON-NLS-1$
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		try {
			forteChooser.setValue(configuration.getAttribute(ATTR_FORTE_LOCATION, "")); //$NON-NLS-1$
			workingDirChooser.setValue(configuration.getAttribute(ATTR_WORKING_DIRECTORY, "")); //$NON-NLS-1$
			portField.setText(configuration.getAttribute(ATTR_FORTE_PORT, DEFAULT_FORTE_PORT));
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(ATTR_FORTE_LOCATION, forteChooser.getValue());

		final String workingDir = workingDirChooser.getValue();
		if (!workingDir.isBlank()) {
			configuration.setAttribute(ATTR_WORKING_DIRECTORY, workingDir);
		}

		configuration.setAttribute(ATTR_FORTE_PORT, portField.getText());
		configuration.setAttribute(LaunchRuntimeUtils.ATTR_TOOL_ARGUMENTS, "-c localhost:" + portField.getText()); //$NON-NLS-1$
	}

	@Override
	public String getName() {
		return Messages.ForteLaunchTab_TabTitle;
	}

	@Override
	public boolean isValid(final ILaunchConfiguration launchConfig) {
		setErrorMessage(null);

		if (forteChooser.getValue().isBlank()) {
			setErrorMessage(Messages.ForteLaunchTabError_No4diacFORTEExecutableSet);
			return false;
		}

		try {
			final int port = Integer.parseInt(portField.getText());
			if ((port < 1024) || (port > 65535)) {
				throw new NumberFormatException();
			}
		} catch (final NumberFormatException nfe) {
			setErrorMessage(Messages.ForteLauncher_ERROR_WrongPort);
			return false;
		}
		return true;
	}

	private void createPortSelection(final Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.ForteLaunchTab_Port);

		portField = new Text(parent, SWT.BORDER);
		GridDataFactory.fillDefaults().align(GridData.FILL, GridData.CENTER).applyTo(portField);
		portField.addVerifyListener(ForteLaunchTab::ensureTextIsValidPortNumber);
		portField.addModifyListener(ev -> scheduleUpdateJob());
	}

	private static void ensureTextIsValidPortNumber(final VerifyEvent e) {
		final String portFieldValue = e.text;
		e.doit = portFieldValue.matches("\\d*"); //$NON-NLS-1$
		if (e.doit && !portFieldValue.isBlank()) {
			final int port = Integer.parseInt(portFieldValue);
			e.doit = port < 65535;
		}
	}

}
