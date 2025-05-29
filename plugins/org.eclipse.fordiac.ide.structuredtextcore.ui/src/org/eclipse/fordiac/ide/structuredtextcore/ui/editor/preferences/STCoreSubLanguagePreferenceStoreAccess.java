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
package org.eclipse.fordiac.ide.structuredtextcore.ui.editor.preferences;

import org.eclipse.fordiac.ide.structuredtextcore.ui.internal.StructuredtextcoreActivator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.editor.preferences.PreferenceStoreAccessImpl;
import org.eclipse.xtext.ui.internal.Activator;

import com.google.inject.Singleton;

@Singleton
public class STCoreSubLanguagePreferenceStoreAccess extends PreferenceStoreAccessImpl {

	private static final String STCORE_QUALIFIER = "org.eclipse.fordiac.ide.structuredtextcore.STCore"; //$NON-NLS-1$

	@Override
	public IPreferenceStore getPreferenceStore() {
		lazyInitialize();
		final Activator activator = Activator.getDefault();
		if (activator != null) {
			return new ChainedPreferenceStore(new IPreferenceStore[] { getWritablePreferenceStore(),
					getSTCorePreferenceStore(), activator.getPreferenceStore(), EditorsUI.getPreferenceStore() });
		}
		return new ChainedPreferenceStore(
				new IPreferenceStore[] { getWritablePreferenceStore(), EditorsUI.getPreferenceStore() });
	}

	@Override
	public IPreferenceStore getContextPreferenceStore(final Object context) {
		lazyInitialize();
		// may be null on shutdown
		final Activator activator = Activator.getDefault();
		if (activator != null) {
			return new ChainedPreferenceStore(
					new IPreferenceStore[] { getWritablePreferenceStore(context), getSTCorePreferenceStore(context),
							activator.getPreferenceStore(), EditorsUI.getPreferenceStore() });
		}
		return new ChainedPreferenceStore(
				new IPreferenceStore[] { getWritablePreferenceStore(context), EditorsUI.getPreferenceStore() });
	}

	public static IPreferenceStore getSTCorePreferenceStore() {
		return getSTCorePreferenceStoreAccess().getWritablePreferenceStore();
	}

	public static IPreferenceStore getSTCorePreferenceStore(final Object context) {
		return getSTCorePreferenceStoreAccess().getWritablePreferenceStore(context);
	}

	private static IPreferenceStoreAccess getSTCorePreferenceStoreAccess() {
		return StructuredtextcoreActivator.getInstance().getInjector(STCORE_QUALIFIER)
				.getInstance(IPreferenceStoreAccess.class);
	}

}
