/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *               
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.ui.document

import org.eclipse.core.runtime.IAdaptable
import org.eclipse.xtext.ui.editor.model.DocumentTokenSource
import org.eclipse.xtext.ui.editor.model.XtextDocument
import org.eclipse.xtext.ui.editor.model.edit.ITextEditComposer
import javax.inject.Inject

class GlobalConstantsDocument extends XtextDocument implements IAdaptable  {
	@Inject
	new(DocumentTokenSource tokenSource, ITextEditComposer composer) {
		super(tokenSource, composer)
	}
	
	// XtextDocument does provide an implementation for getAdapter but does not implement IAdaptable
	override <T> T getAdapter(Class<T> adapterType) {
		super.getAdapter(adapterType)
	}
	
}