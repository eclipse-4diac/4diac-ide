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
package org.eclipse.fordiac.ide.deployment.debug.ui.preferences;

import org.eclipse.fordiac.ide.deployment.debug.preferences.DeploymentDebugPreferences;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.fordiac.ide.ui.preferences.FordiacPropertyPreferencePage;
import org.eclipse.jface.preference.BooleanFieldEditor;

public class DeploymentDebugPreferencePage extends FordiacPropertyPreferencePage {

	public DeploymentDebugPreferencePage() {
		super(GRID, DeploymentDebugPreferences.QUALIFIER);
		setDescription(Messages.DeploymentDebugPreferencePage_Description);
	}

	@Override
	public void createFieldEditors() {
		addField(new BooleanFieldEditor(DeploymentDebugPreferences.MONITORING_VALUE_WRITE_THROUGH,
				Messages.DeploymentDebugPreferencePage_MonitoringValueWriteThrough, getFieldEditorParent()));
	}

	@Override
	protected String getPreferencePageID() {
		return "org.eclipse.fordiac.ide.deployment.debug.ui.preferences"; //$NON-NLS-1$
	}

	@Override
	protected String getPropertyPageID() {
		return "org.eclipse.fordiac.ide.deployment.debug.ui.properties"; //$NON-NLS-1$
	}
}