/*******************************************************************************
 * Copyright (c) 2009, 2011 Profactor GmbH
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
package org.eclipse.fordiac.ide.application.actions;

import org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl;
import org.eclipse.fordiac.ide.util.IOpenListener;
import org.eclipse.fordiac.ide.util.OpenListenerManager;
import org.eclipse.jface.action.Action;

/**
 * The Class SubAppOpenListenerAction.
 */
public class SubAppOpenListenerAction extends Action {
	private final IOpenListener openListener;

	/**
	 * Instantiates a new sub app open listener action.
	 * 
	 * @param openListener the open listener
	 */
	public SubAppOpenListenerAction(final IOpenListener openListener) {
		this.openListener = openListener;
		setText("Subapp Network");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		OpenListenerManager.getInstance().setDefaultOpenListener(SubAppImpl.class,
				"org.eclipse.fordiac.ide.application.actions.OpenSubApplicationEditorAction"); //$NON-NLS-1$
		openListener.run(null);
	}

}