/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University Linz (JKU)
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.structuredtext.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.fordiac.ide.model.structuredtext.ui.ExtendedStructuredTextActivator;
import org.eclipse.jface.preference.IPreferenceStore;

public class PreferenceInitializer extends AbstractPreferenceInitializer {
	
	public static final String AUTO_INSERT = "enableAutoInsert"; //$NON-NLS-1$

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = ExtendedStructuredTextActivator.getInstance().getPreferenceStore();
		store.setDefault(PreferenceInitializer.AUTO_INSERT, true);
	}

}
