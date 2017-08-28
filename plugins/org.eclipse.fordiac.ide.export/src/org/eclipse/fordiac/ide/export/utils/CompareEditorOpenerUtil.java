/*******************************************************************************
 * Copyright (c) 2009, 2014, 2016 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.utils;

import java.util.Hashtable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.export.Activator;

/**
 * The Class CompareEditorOpenerUtil is a helper class to get the
 * implementations of the openCompareEditor extension point.
 */
public class CompareEditorOpenerUtil {

	/**
	 * Gets a hastable of available <code>ICompareEditorOpeners</code> with their
	 * names as key.
	 * 
	 * @return the compare editor openers
	 */
	public static Hashtable<String, ICompareEditorOpener> getCompareEditorOpeners() {
		Hashtable<String, ICompareEditorOpener> openers = new Hashtable<String, ICompareEditorOpener>(
				2);
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(
				Activator.PLUGIN_ID, "ExportCompareOpener"); //$NON-NLS-1$
		for (int i = 0; i < elems.length; i++) {
			IConfigurationElement element = elems[i];
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				String name = element.getAttribute("name"); //$NON-NLS-1$
				if (object instanceof ICompareEditorOpener) {
					ICompareEditorOpener compareEditorOpener = (ICompareEditorOpener) object;
					openers.put(name, compareEditorOpener);
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError("Error loading Compareeditor", corex); //$NON-NLS-1$
			}
		}
		return openers;
	}

	/**
	 * Gets the opener selected in the Preference Page. If none is specified the
	 * first one which is found is used. If there exists none - <code>null</code>
	 * is returned.
	 * 
	 * @return the opener
	 */
	public static ICompareEditorOpener getOpener() {
		String compareEditor = org.eclipse.fordiac.ide.export.Activator.getDefault()
				.getPreferenceStore().getString(PreferenceConstants.P_COMPARE_EDITOR);
		Hashtable<String, ICompareEditorOpener> openers = getCompareEditorOpeners();
		ICompareEditorOpener opener = openers.get(compareEditor);
		if (opener == null && openers.size() >= 1) { // simply use the first compare
			// editor found if the
			// selected does not exist
			opener = (ICompareEditorOpener) openers.values().toArray()[0];
		}
		return opener;
	}

}
