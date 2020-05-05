/*******************************************************************************
 * Copyright (c) 2011 Profactor GbmH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;

/**
 * The Interface OpenListener.
 *
 * @author Gerhard Ebenhofer, gerhard.ebenhofer@profactor.at
 */
public interface IOpenListener extends IObjectActionDelegate {
	/**
	 * Gets the open listener action.
	 *
	 * @return the open listener action
	 */
	Action getOpenListenerAction();

	IEditorPart getOpenedEditor();

	/**
	 * Get the Text to be printed in the Actions menu
	 */
	String getActionText();

	/**
	 * Get the class this open listner handles
	 */
	Class<? extends EObject> getHandledClass();

	/**
	 * Get the id of the open listener as it is registered in the plugin's extension
	 * point.
	 */
	String getOpenListenerID();

}
