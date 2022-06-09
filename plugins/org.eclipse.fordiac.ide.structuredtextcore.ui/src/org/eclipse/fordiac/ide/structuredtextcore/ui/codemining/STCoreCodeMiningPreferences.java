/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.codemining;

import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;

import com.google.inject.Inject;

public class STCoreCodeMiningPreferences {
	public static class Initializer implements IPreferenceStoreInitializer {
		@Override
		public void initialize(final IPreferenceStoreAccess access) {
			access.getWritablePreferenceStore().setDefault(ENABLE_CODE_MININGS, true);
			access.getWritablePreferenceStore().setDefault(ENABLE_LITERAL_TYPE_CODE_MININGS, true);
		}
	}

	public static final String ENABLE_CODE_MININGS = "enableCodeMinings"; //$NON-NLS-1$
	public static final String ENABLE_LITERAL_TYPE_CODE_MININGS = "enableLiteralTypeCodeMinings"; //$NON-NLS-1$

	@Inject
	private IPreferenceStoreAccess preferenceStoreAccess;

	public boolean isEnableCodeMinings() {
		return preferenceStoreAccess.getPreferenceStore().getBoolean(ENABLE_CODE_MININGS);
	}

	public boolean isEnableLiteralTypeCodeMinings() {
		return preferenceStoreAccess.getPreferenceStore().getBoolean(ENABLE_LITERAL_TYPE_CODE_MININGS);
	}

	public void setSaveAllBeforeRefactoring(final boolean isEnableCodeMinings) {
		preferenceStoreAccess.getWritablePreferenceStore().setValue(ENABLE_CODE_MININGS, isEnableCodeMinings);
	}

	public void setUseInlineRefactoring(final boolean isEnableLiteralTypeCodeMinings) {
		preferenceStoreAccess.getWritablePreferenceStore().setValue(ENABLE_LITERAL_TYPE_CODE_MININGS,
				isEnableLiteralTypeCodeMinings);
	}
}
