/*******************************************************************************
 * Copyright (c) 2013, 2015 fortiss GmbH
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
package org.eclipse.fordiac.ide.util;

import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;

public abstract class AbstractUntypedEditorInputFactory implements
		IElementFactory {
	
   /**
     * Tag for the automation system name.
     */
    private static final String TAG_AUTOMATION_SYSTEM = "SYSTEM"; //$NON-NLS-1$
    
    
    protected static void saveAutomationSystem(IMemento memento, AutomationSystem system){
    	if(null != system){
    		memento.putString(TAG_AUTOMATION_SYSTEM, system.getName());
    	}
    }
    
    protected static String loadAutomationSystemName(IMemento memento){
    	return memento.getString(TAG_AUTOMATION_SYSTEM);
    }

}
