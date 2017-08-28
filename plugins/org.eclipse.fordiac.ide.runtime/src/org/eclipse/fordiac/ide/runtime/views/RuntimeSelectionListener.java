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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * The listener interface for receiving runtimeSelection events.
 * The class that is interested in processing a runtimeSelection
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addRuntimeSelectionListener<code> method. When
 * the runtimeSelection event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see RuntimeSelectionEvent
 */
public class RuntimeSelectionListener implements SelectionListener {
	
	protected IRuntimeLauncher launcher;
	
	/**
	 * Instantiates a new runtime selection listener.
	 * 
	 * @param iLauncher the i launcher
	 */
	RuntimeSelectionListener (IRuntimeLauncher iLauncher) {
		launcher = iLauncher;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {		
	}

}
