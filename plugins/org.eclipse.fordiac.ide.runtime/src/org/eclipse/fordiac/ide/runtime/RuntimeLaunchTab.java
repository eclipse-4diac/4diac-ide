/*******************************************************************************
 * Copyright (c) 2024 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.runtime;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public abstract class RuntimeLaunchTab extends AbstractLaunchConfigurationTab {

	protected static final String ATTR_LOCATION = "org.eclipse.ui.externaltools.ATTR_LOCATION"; //$NON-NLS-1$
	protected static final String ATTR_WORKING_DIRECTORY = "org.eclipse.ui.externaltools.ATTR_WORKING_DIRECTORY"; //$NON-NLS-1$
	protected static final String ATTR_BUILD_SCOPE = "org.eclipse.ui.externaltools.ATTR_LAUNCH_CONFIGURATION_BUILD_SCOPE"; //$NON-NLS-1$

	private Text portField;

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.removeAttribute(LaunchRuntimeUtils.ATTR_TOOL_ARGUMENTS);
		configuration.removeAttribute(ATTR_WORKING_DIRECTORY);
		configuration.setAttribute(ATTR_BUILD_SCOPE, "NONE"); //$NON-NLS-1$
	}

	@Override
	public boolean isValid(final ILaunchConfiguration launchConfig) {
		setErrorMessage(null);
		try {
			final int port = Integer.parseInt(portField.getText());
			if ((port < 1024) || (port > 65535)) {
				throw new NumberFormatException();
			}
		} catch (final NumberFormatException nfe) {
			setErrorMessage(Messages.RuntimeLaunchTab_ERROR_WrongPort);
			return false;
		}
		return true;
	}

	protected String getPortValue() {
		return portField.getText();
	}

	protected void setPortValue(final String portValue) {
		portField.setText(portValue);
	}

	protected void createPortSelection(final Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.RuntimeLaunchTab_Port);
		portField = new Text(parent, SWT.BORDER);
		GridDataFactory.fillDefaults().align(GridData.FILL, GridData.CENTER).applyTo(portField);
		portField.addVerifyListener(RuntimeLaunchTab::ensureTextIsValidPortNumber);
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
