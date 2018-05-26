/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014, 2015, 2017 Profactor GbmH, fortiss GmbH 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(DiagramPreferences.SNAP_TO_GRID, true);
		store.setDefault(DiagramPreferences.SHOW_GRID, true);
		store.setDefault(DiagramPreferences.GRID_SPACING, 20);
		store.setDefault(DiagramPreferences.CONNECTION_ROUTER, "Adjustable Router (no Jumplinks)"); //$NON-NLS-1$
		PreferenceConverter.setDefault(store, DiagramPreferences.SELECTION_COLOR, ColorConstants.gray.getRGB());
		store.setDefault(DiagramPreferences.CORNER_DIM, 14);
	}
}
