/*******************************************************************************
 * Copyright (c) 2008, 2009, 2013 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martijn Rooker, Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.runtime.views;

import org.eclipse.fordiac.ide.runtime.IRuntimeLauncher;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;


/**
 * The listener interface for receiving runtimeModify events.
 * The class that is interested in processing a runtimeModify
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addRuntimeModifyListener<code> method. When
 * the runtimeModify event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see RuntimeModifyEvent
 */
public class RuntimeModifyListener implements ModifyListener {

	private IRuntimeLauncher launcher;
	private String name;
	
	/**
	 * Instantiates a new runtime modify listener.
	 * 
	 * @param iLauncher the i launcher
	 * @param name the name
	 */
	RuntimeModifyListener (IRuntimeLauncher iLauncher, String name) {
		launcher = iLauncher;
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	@Override
	public void modifyText(ModifyEvent e) {		
	}
	
	public IRuntimeLauncher getLauncher() {
		return launcher;
	}
	
	public String getName() {
		return name;
	}

}
