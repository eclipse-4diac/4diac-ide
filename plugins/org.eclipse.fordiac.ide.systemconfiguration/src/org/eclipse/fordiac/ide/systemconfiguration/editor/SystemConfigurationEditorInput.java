/*******************************************************************************
 * Copyright (c) 2013, 2015 fortiss GmbH, Profactor GbmH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.editor;

import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.util.PersistableUntypedEditorInput;
import org.eclipse.ui.IMemento;

public class SystemConfigurationEditorInput extends PersistableUntypedEditorInput {

	public SystemConfigurationEditorInput(SystemConfiguration sysConf) {
		super(sysConf,  sysConf.getAutomationSystem().getName());
	}
	
	@Override
	public void saveState(IMemento memento) {
		SystemConfigurationEditorInputFactory.saveState(memento, this);
	}

	@Override
	public String getFactoryId() {
		return SystemConfigurationEditorInputFactory.getFactoryId();
	}
	
	@Override
	public SystemConfiguration getContent(){
		return (SystemConfiguration)super.getContent();
	}
}
