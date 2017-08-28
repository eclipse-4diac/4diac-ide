/*******************************************************************************
 * Copyright (c) 2013, 2016 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.editor;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.util.AbstractUntypedEditorInputFactory;
import org.eclipse.ui.IMemento;

public class SystemConfigurationEditorInputFactory extends AbstractUntypedEditorInputFactory {
	
	/**
     * Factory id. The workbench plug-in registers a factory by this name
     * with the "org.eclipse.ui.elementFactories" extension point.
     */
    private static final String ID_FACTORY = "org.eclipse.fordiac.ide.systemconfiguration.editor.SystemConfigurationEditorInputFactory"; //$NON-NLS-1$
    
 
	@Override
	public IAdaptable createElement(IMemento memento) {
    	String systemName = loadAutomationSystemName(memento);
    	if(null != systemName){
    		AutomationSystem system = SystemManager.INSTANCE.getSystemForName(systemName);
			if(null != system){
				return new SystemConfigurationEditorInput(system.getSystemConfiguration());
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
    public static void saveState(IMemento memento, SystemConfigurationEditorInput input) {
    	saveAutomationSystem(memento, (AutomationSystem)input.getSystemConfiguration().eContainer());
    }

}
