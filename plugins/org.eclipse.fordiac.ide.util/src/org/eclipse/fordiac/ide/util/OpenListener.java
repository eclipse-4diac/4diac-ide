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
package org.eclipse.fordiac.ide.util;

import org.eclipse.ui.IEditorPart;

public abstract class OpenListener implements IOpenListener {

	protected IEditorPart editor = null;
	
	@Override
	public IEditorPart getOpenedEditor() {
		return editor;
	}
	
}
