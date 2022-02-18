/*******************************************************************************
 * Copyright (c) 2012, 2018 Profactor GbmH, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.preferences;

import org.eclipse.fordiac.ide.monitoring.Activator;
import org.eclipse.fordiac.ide.monitoring.Messages;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class MonitoringPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public MonitoringPreferences() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.MonitoringPreferences_SettingsTitle);
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {
		final ColorFieldEditor watchColor = new ColorFieldEditor(PreferenceConstants.P_WATCH_COLOR,
				Messages.MonitoringPreferences_WatchColor,
				getFieldEditorParent());
		addField(watchColor);

		final ColorFieldEditor forceColor = new ColorFieldEditor(PreferenceConstants.P_FORCE_COLOR,
				Messages.MonitoringPreferences_ForceColor,
				getFieldEditorParent());
		addField(forceColor);

		final IntegerFieldEditor polling = new IntegerFieldEditor(PreferenceConstants.P_POLLING_INTERVAL,
				Messages.MonitoringPreferences_PollingIntervallMs, getFieldEditorParent(),
				PreferenceConstants.P_POLLING_INTERVAL_DEVAULT_VALUE);
		polling.setValidRange(1, 60000);
		addField(polling);

		final IntegerFieldEditor monitoringTransparency = new IntegerFieldEditor(
				PreferenceConstants.P_MONITORING_TRANSPARENCY, Messages.MonitoringPreferences_TransparencyLevel,
				getFieldEditorParent(), PreferenceConstants.P_MONITORING_TRANSPARENCY_VALUE);
		monitoringTransparency.setValidRange(100, 255);
		addField(monitoringTransparency);

		final BooleanFieldEditor dontAskAgain = new BooleanFieldEditor(
				PreferenceConstants.P_MONITORING_STARTMONITORINGWITHOUTASKING,
				Messages.MonitoringPreferences_StartMonitoringWithoutAsking, getFieldEditorParent());
		addField(dontAskAgain);

	}

	@Override
	public void init(final IWorkbench workbench) {
		// nothing todo here
	}

}