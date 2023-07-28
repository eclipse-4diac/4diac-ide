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
package org.eclipse.fordiac.ide.globalconstantseditor.ui.document;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.fordiac.ide.globalconstantseditor.resource.GlobalConstantsResource;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.xtext.ui.editor.model.DocumentTokenSource;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.ITextEditComposer;

import com.google.inject.Inject;

public class GlobalConstantsDocument extends XtextDocument implements IAdaptable {

	@Inject
	public GlobalConstantsDocument(final DocumentTokenSource tokenSource, final ITextEditComposer composer) {
		super(tokenSource, composer);
	}

	@Override
	public <T> T getAdapter(final Class<T> adapterType) {
		if (adapterType.isAssignableFrom(GlobalConstants.class)) {
			return adapterType.cast(getResourceGlobalConstants());
		}
		return super.getAdapter(adapterType);
	}

	public GlobalConstants getResourceGlobalConstants() {
		return readOnly(resource -> resource instanceof final GlobalConstantsResource globalConstantsResource
				? globalConstantsResource.getGlobalConstants()
				: null);
	}
}
