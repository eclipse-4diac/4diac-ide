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
package org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist;

import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class STCoreContentAssistPreferences {

	public static class Initializer implements IPreferenceStoreInitializer {
		@Override
		public void initialize(final IPreferenceStoreAccess access) {
			access.getWritablePreferenceStore().setDefault(COMPLETION_AUTO_ACTIVATION_CHARS, "."); //$NON-NLS-1$
		}
	}

	public static class CompletionAutoActivationCharsProvider implements Provider<String> {
		@Inject
		STCoreContentAssistPreferences preferences;

		@Override
		public String get() {
			return preferences.getCompletionAutoActivationChars();
		}
	}

	public static final String COMPLETION_AUTO_ACTIVATION_CHARS = "completionAutoActivationChars"; //$NON-NLS-1$

	@Inject
	private IPreferenceStoreAccess preferenceStoreAccess;

	public String getCompletionAutoActivationChars() {
		return preferenceStoreAccess.getPreferenceStore().getString(COMPLETION_AUTO_ACTIVATION_CHARS);
	}

	public void setCompletionAutoActivationChars(final String completionAutoActivationChars) {
		preferenceStoreAccess.getWritablePreferenceStore().setValue(COMPLETION_AUTO_ACTIVATION_CHARS,
				completionAutoActivationChars);
	}
}
