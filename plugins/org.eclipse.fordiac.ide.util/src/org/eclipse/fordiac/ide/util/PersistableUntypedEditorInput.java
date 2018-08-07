/*******************************************************************************
 * Copyright (c) 2013 Profactor GbmH
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

import org.eclipse.ui.IPersistable;
import org.eclipse.ui.IPersistableElement;

/**
 * An EditorInput for opening IEC 61499 Editors with specified content. The
 * equals method is adapted that EditorInput is equal to another EditorInput if
 * the content is equal.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public abstract class PersistableUntypedEditorInput extends UntypedEditorInput implements  IPersistableElement, IPersistable {

	public PersistableUntypedEditorInput(Object content, String name,
			String toolTip) {
		super(content, name, toolTip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	@Override
	public IPersistableElement getPersistable() {
		return this;
	}

	
}
