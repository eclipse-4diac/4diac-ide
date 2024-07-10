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

import org.eclipse.xtext.Constants;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/** @deprecated Use {@link LibraryElementXtextDocumentUpdater} directly */
@Deprecated(forRemoval = true)
public class FBTypeXtextDocumentUpdater extends LibraryElementXtextDocumentUpdater {

	@Inject
	public FBTypeXtextDocumentUpdater(@Named(Constants.LANGUAGE_NAME) final String name) {
		super(name, new DefaultLibraryElementChangeAdapterFilter());
	}

	@Override
	public FBTypeXtextDocument getDocument() {
		return (FBTypeXtextDocument) super.getDocument();
	}
}
