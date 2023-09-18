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
package org.eclipse.fordiac.ide.structuredtextcore.ui.cleanup;

import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;

import com.google.inject.Inject;

public class STCoreSaveActionsPreferences {
	public static class Initializer implements IPreferenceStoreInitializer {
		@Override
		public void initialize(final IPreferenceStoreAccess access) {
			access.getWritablePreferenceStore().setDefault(ENABLE_SAVE_ACTIONS, true);
			access.getWritablePreferenceStore().setDefault(ENABLE_FORMAT, true);
		}
	}

	public static final String ENABLE_SAVE_ACTIONS = "enableSaveActions"; //$NON-NLS-1$
	public static final String ENABLE_FORMAT = "enableFormat"; //$NON-NLS-1$

	@Inject
	private IPreferenceStoreAccess preferenceStoreAccess;

	public boolean isEnableSaveActions() {
		return preferenceStoreAccess.getPreferenceStore().getBoolean(ENABLE_SAVE_ACTIONS);
	}

	public boolean isEnableFormat() {
		return preferenceStoreAccess.getPreferenceStore().getBoolean(ENABLE_FORMAT);
	}

	public void setEnableSaveActions(final boolean isEnableSaveActions) {
		preferenceStoreAccess.getWritablePreferenceStore().setValue(ENABLE_SAVE_ACTIONS, isEnableSaveActions);
	}

	public void setEnableFormat(final boolean isEnableFormat) {
		preferenceStoreAccess.getWritablePreferenceStore().setValue(ENABLE_FORMAT, isEnableFormat);
	}
}
