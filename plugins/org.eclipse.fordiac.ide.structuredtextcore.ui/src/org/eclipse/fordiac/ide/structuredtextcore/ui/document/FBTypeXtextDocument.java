/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.document;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.xtext.ui.editor.model.DocumentTokenSource;
import org.eclipse.xtext.ui.editor.model.edit.ITextEditComposer;

import com.google.inject.Inject;

/** @deprecated Use {@link LibraryElementXtextDocument} directly */
@Deprecated(forRemoval = true)
public class FBTypeXtextDocument extends LibraryElementXtextDocument {

	@Inject
	public FBTypeXtextDocument(final DocumentTokenSource tokenSource, final ITextEditComposer composer) {
		super(tokenSource, composer);
	}

	/** @deprecated Use {@link LibraryElementXtextDocument#getResourceLibraryElement} directly */
	@Deprecated(forRemoval = true)
	public FBType getResourceFBType() {
		if (getResourceLibraryElement() instanceof final FBType fbType) {
			return fbType;
		}
		return null;
	}
}
