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
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.fordiac.ide.runtime.RuntimeLaunchTab;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.widget.FileChooserControl;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class StopForteTab extends AbstractLaunchConfigurationTab {

	private FileChooserControl forteChooser;

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

	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		try {
			forteChooser.setValue(configuration.getAttribute(RuntimeLaunchTab.ATTR_LOCATION, "")); //$NON-NLS-1$
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(RuntimeLaunchTab.ATTR_LOCATION, forteChooser.getValue());
	}

	@Override
	public boolean isValid(final ILaunchConfiguration launchConfig) {
		setErrorMessage(null);

		if (forteChooser.getValue().isBlank()) {
			setErrorMessage(Messages.ForteLaunchTabError_No4diacFORTEExecutableSet);
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
	}

}
