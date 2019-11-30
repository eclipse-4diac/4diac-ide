/*******************************************************************************
 * Copyright (c) 2012 - 2016 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors;

import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * The Class TextEditorCreator.
 */
public class TextEditorCreator implements IAlgorithmEditorCreator {

	/**
	 * Instantiates a new text editor creator.
	 */
	public TextEditorCreator() {
	}

	@Override
	public IAlgorithmEditor createAlgorithmEditor(final Composite parent, BaseFBType fbType) {
		TextEditor editor = new TextEditor(parent, null, null, false,
				SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		return editor;
	}

}
