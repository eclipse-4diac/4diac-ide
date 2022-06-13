/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.document

import com.google.inject.Inject
import org.eclipse.core.runtime.IAdaptable
import org.eclipse.xtext.ui.editor.model.DocumentTokenSource
import org.eclipse.xtext.ui.editor.model.XtextDocument
import org.eclipse.xtext.ui.editor.model.edit.ITextEditComposer

class STFunctionDocument extends XtextDocument implements IAdaptable {

	@Inject
	new(DocumentTokenSource tokenSource, ITextEditComposer composer) {
		super(tokenSource, composer)
	}

	// XtextDocument does provide an implementation for getAdapter but does not implement IAdaptable
	override <T> T getAdapter(Class<T> adapterType) {
		super.getAdapter(adapterType)
	}
}
