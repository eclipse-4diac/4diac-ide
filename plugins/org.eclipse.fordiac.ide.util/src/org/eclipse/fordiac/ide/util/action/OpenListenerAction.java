/*******************************************************************************
 * Copyright (c) 2008, 2011, 2017 Profactor GmbH, fortiss GmbH,
 * 				 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.action;

import org.eclipse.fordiac.ide.util.IOpenListener;
import org.eclipse.fordiac.ide.util.OpenListenerManager;
import org.eclipse.jface.action.Action;

/** A default implementation handling open action for IOpenListener implementers
 */
public class OpenListenerAction extends Action {
	private final IOpenListener openListener;

	public OpenListenerAction(final IOpenListener openListener) {
		this.openListener = openListener;
		setText(openListener.getActionText());
	}

	@Override
	public void run() {
		OpenListenerManager.setDefaultOpenListener(openListener.getHandledClass(), openListener.getOpenListenerID());
		openListener.run(null);
	}

}