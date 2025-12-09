/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.editor;

import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;

import com.google.inject.Inject;

public class STCoreEditorPreferences {
	public static class Initializer implements IPreferenceStoreInitializer {
		@Override
		public void initialize(final IPreferenceStoreAccess access) {
			access.getWritablePreferenceStore().setDefault(PERFORMANCE_MODE_THRESHOLD, 5000);
		}
	}

	public static final String PERFORMANCE_MODE_THRESHOLD = "performanceModeThreshold"; //$NON-NLS-1$

	@Inject
	private IPreferenceStoreAccess preferenceStoreAccess;

	public int getPerformanceModeThreshold() {
		return preferenceStoreAccess.getPreferenceStore().getInt(PERFORMANCE_MODE_THRESHOLD);
	}

	public void setPerformanceModeThreshold(final int value) {
		preferenceStoreAccess.getWritablePreferenceStore().setValue(PERFORMANCE_MODE_THRESHOLD, value);
	}
}
