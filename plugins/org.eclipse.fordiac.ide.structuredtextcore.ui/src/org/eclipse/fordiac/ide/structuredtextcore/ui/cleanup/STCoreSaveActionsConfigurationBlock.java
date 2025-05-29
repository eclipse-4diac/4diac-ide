/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.cleanup;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.xtext.ui.preferences.OptionsConfigurationBlock;

@SuppressWarnings("restriction")
public class STCoreSaveActionsConfigurationBlock extends OptionsConfigurationBlock {

	private static final String PROPERTY_PREFIX = "SaveActionsConfiguration"; //$NON-NLS-1$
	protected static final String[] BOOLEAN_VALUES = new String[] { IPreferenceStore.TRUE, IPreferenceStore.FALSE };

	@Override
	protected Control doCreateContents(final Composite parent) {
		setShell(parent.getShell());
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(composite);
		addCheckBox(composite, Messages.STCoreSaveActionsPreferencePage_EnableSaveActions,
				STCoreSaveActionsPreferences.ENABLE_SAVE_ACTIONS, BOOLEAN_VALUES, 0);
		addCheckBox(composite, Messages.STCoreSaveActionsPreferencePage_EnableFormat,
				STCoreSaveActionsPreferences.ENABLE_FORMAT, BOOLEAN_VALUES, 32);
		return composite;
	}

	@Override
	protected void validateSettings(final String changedKey, final String oldValue, final String newValue) {
		// do nothing
	}

	@Override
	public String getPropertyPrefix() {
		return PROPERTY_PREFIX;
	}

	@Override
	protected Job getBuildJob(final IProject project) {
		return null; // no build necessary
	}

	@Override
	protected String[] getFullBuildDialogStrings(final boolean workspaceSettings) {
		return null; // do not ask for build //NOSONAR
	}
}
