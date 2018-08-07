/*******************************************************************************
 * Copyright (c) 2011, 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny, Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Activator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		/*blue*/                                                                                                   
		PreferenceConverter.setDefault(store,PreferenceConstants.P_ECC_STATE_COLOR,new RGB(207,205,245));          
		PreferenceConverter.setDefault(store,PreferenceConstants.P_ECC_STATE_BORDER_COLOR,new RGB(78,70,217));     
		PreferenceConverter.setDefault(store,PreferenceConstants.P_ECC_TRANSITION_COLOR,new RGB(78,70,217));       
		                                                                                                           
		/*yellow*/                                                                                                 
		PreferenceConverter.setDefault(store,PreferenceConstants.P_ECC_ALGORITHM_COLOR,new RGB(254,254,194));      
		PreferenceConverter.setDefault(store,PreferenceConstants.P_ECC_ALGORITHM_BORDER_COLOR,new RGB(214,145,1)); 
		                                                                                                           
		/*green*/                                                                                                  
		PreferenceConverter.setDefault(store,PreferenceConstants.P_ECC_EVENT_COLOR,new RGB(190,233,197));          
		PreferenceConverter.setDefault(store,PreferenceConstants.P_ECC_EVENT_BORDER_COLOR,new RGB(57,137,46));
	}

}
