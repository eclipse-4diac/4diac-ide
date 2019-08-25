/*******************************************************************************
 * Copyright (c) 2013, 2016, 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.util.AbstractUntypedEditorInputFactory;
import org.eclipse.ui.IMemento;

public class ApplicationEditorInputFactory extends AbstractUntypedEditorInputFactory {
	
	/**
     * Factory id. The workbench plug-in registers a factory by this name
     * with the "org.eclipse.ui.elementFactories" extension point.
     */
    private static final String ID_FACTORY = "org.eclipse.fordiac.ide.application.ApplicationEditorInputFactory"; //$NON-NLS-1$
    
    /**
     * Tag for the automation system name.
     */
    private static final String TAG_APPLICATION = "APPLICATION"; //$NON-NLS-1$

	@Override
	public IAdaptable createElement(IMemento memento) {
    	String systemName = loadAutomationSystemName(memento);
    	String applicationName = memento.getString(TAG_APPLICATION);
    	if((null != systemName) && (null != applicationName)){
    		AutomationSystem system = SystemManager.INSTANCE.getSystemForName(systemName);
			if(null != system){
				Application application = system.getApplicationNamed(applicationName);
				if(null != application){
					return new ApplicationEditorInput(application);
				}
			}    		
    	}
    	return null;
	}
	
	/**
     * Returns the element factory id for this class.
     * 
     * @return the element factory id
     */
    public static String getFactoryId() {
        return ID_FACTORY;
    }

    /**
     * Saves the state of the given file editor input into the given memento.
     *
     * @param memento the storage area for element state
     * @param input the application editor input
     */
    public static void saveState(IMemento memento, ApplicationEditorInput input) {
    	saveAutomationSystem(memento, input.getAutomationSystem());
    	memento.putString(TAG_APPLICATION, input.getName());
    }

}
