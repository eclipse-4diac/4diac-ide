/*******************************************************************************
 * Copyright (c) 2011 Profactor GbmH
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
package org.eclipse.fordiac.ide.util;

import org.eclipse.fordiac.ide.model.libraryElement.I4DIACElement;
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
	 * Supports object.
	 * 
	 * @param clazz the clazz
	 * 
	 * @return true, if successful
	 */
	public boolean supportsObject(Class<? extends I4DIACElement> clazz);

	/**
	 * Gets the open listener action.
	 * 
	 * @return the open listener action
	 */
	public Action getOpenListenerAction();
	
	public IEditorPart getOpenedEditor();

}
