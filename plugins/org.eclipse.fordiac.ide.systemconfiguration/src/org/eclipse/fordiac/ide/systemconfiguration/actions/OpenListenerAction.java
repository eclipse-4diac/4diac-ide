/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 Profactor GbmH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.actions;

import org.eclipse.fordiac.ide.model.libraryElement.impl.SystemConfigurationImpl;
import org.eclipse.fordiac.ide.util.IOpenListener;
import org.eclipse.fordiac.ide.util.OpenListenerManager;
import org.eclipse.jface.action.Action;

/**
 * The Class OpenListenerAction.
 */
public class OpenListenerAction extends Action {
	private final IOpenListener openListener;

	/**
	 * Instantiates a new open listener action.
	 * 
	 * @param openListener the open listener
	 */
	public OpenListenerAction(final IOpenListener openListener) {
		this.openListener = openListener;
		setText("System Configuration");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		OpenListenerManager
				.getInstance()
				.setDefaultOpenListener(SystemConfigurationImpl.class,
						"org.eclipse.fordiac.ide.systemconfiguration.actions.OpenSystemConfEditorAction"); //$NON-NLS-1$
		openListener.run(null);
	}

}