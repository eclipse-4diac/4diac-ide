/*******************************************************************************
 * Copyright (c) 2009, 2014, 2016 Profactor GmbH, fortiss GmbH, 2018 TU Vienna/ACIN
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *   Martin MelikMerkumians
 *     - Makes class final and ctor private, changes Hashtable to HashMap/Map
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.export.ICompareEditorOpener;
import org.eclipse.fordiac.ide.export.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;

/**
 * The Class CompareEditorOpenerUtil is a helper class to get the
 * implementations of the openCompareEditor extension point.
 */
public final class CompareEditorOpenerUtil {

	private CompareEditorOpenerUtil() {
		/* static util class shall not be instantiable */
	}

	/**
	 * Gets a Map of available <code>ICompareEditorOpeners</code> with their names
	 * as key.
	 *
	 * @return the compare editor openers
	 */
	public static Map<String, ICompareEditorOpener> getCompareEditorOpeners() {
		final HashMap<String, ICompareEditorOpener> openers = HashMap.newHashMap(2);
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] elems = registry
				.getConfigurationElementsFor("org.eclipse.fordiac.ide.export.openCompareEditor"); //$NON-NLS-1$
		for (final IConfigurationElement element : elems) {
			try {
				final Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				final String name = element.getAttribute("name"); //$NON-NLS-1$
				if (object instanceof final ICompareEditorOpener compareEditorOpener) {
					openers.put(name, compareEditorOpener);
				}
			} catch (final CoreException corex) {
				FordiacLogHelper.logError("Error loading Compareeditor", corex); //$NON-NLS-1$
			}
		}
		return openers;
	}

	/**
	 * Gets the opener selected in the Preference Page. If none is specified the
	 * first one which is found is used. If there exists none - <code>null</code> is
	 * returned.
	 *
	 * @return the opener
	 */
	public static ICompareEditorOpener getOpener() {
		final String compareEditor = Platform.getPreferencesService().getString("org.eclipse.fordiac.ide.export", //$NON-NLS-1$
				PreferenceConstants.P_COMPARE_EDITOR, "", null); //$NON-NLS-1$
		final Map<String, ICompareEditorOpener> openers = getCompareEditorOpeners();
		ICompareEditorOpener opener = openers.get(compareEditor);
		if (opener == null && openers.size() >= 1) { // simply use the first compare
			// editor found if the
			// selected does not exist
			opener = (ICompareEditorOpener) openers.values().toArray()[0];
		}
		return opener;
	}

}
