/*******************************************************************************
 * Copyright (c) 2013, 2016, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.util.AbstractUntypedEditorInputFactory;
import org.eclipse.ui.IMemento;

public class SubApplicationEditorInputFactory extends AbstractUntypedEditorInputFactory {
	
	/**
     * Factory id. The workbench plug-in registers a factory by this name
     * with the "org.eclipse.ui.elementFactories" extension point.
     */
    private static final String ID_FACTORY = "org.eclipse.fordiac.ide.application.SubApplicationEditorInputFactory"; //$NON-NLS-1$
    
    /**
     * Tag for the application name.
     */
    private static final String TAG_APPLICATION = "APPLICATION"; //$NON-NLS-1$
    
    /**
     * Tag for the subapplication name.
     */
    private static final String TAG_SUB_APPLICATION = "SUB_APPLICATION"; //$NON-NLS-1$

	@Override
	public IAdaptable createElement(IMemento memento) {
    	String systemName = loadAutomationSystemName(memento);
    	String applicationName = memento.getString(TAG_APPLICATION);
    	String subApplicationName = memento.getString(TAG_SUB_APPLICATION);
    	if((null != systemName) && (null != applicationName)){
    		AutomationSystem system = SystemManager.INSTANCE.getSystemForName(systemName);
			if(null != system){
				Application application = system.getApplicationNamed(applicationName);
				SubApp subApp = application.getFBNetwork().getSubAppNamed(subApplicationName);
				if(null != subApp){
					return new SubApplicationEditorInput(subApp);
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
    public static void saveState(IMemento memento, SubApplicationEditorInput input) {
    	Application app = input.getApplication();
    	if(null != app) {
	    	saveAutomationSystem(memento, app.getAutomationSystem());
	    	memento.putString(TAG_APPLICATION, app.getName());
	    	memento.putString(TAG_SUB_APPLICATION, input.getName());
    	}
    }

}
