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
package org.eclipse.fordiac.ide.globalconstantseditor.ui.resource;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.globalconstantseditor.resource.GlobalConstantsResource;
import org.eclipse.fordiac.ide.model.typelibrary.GlobalConstantsEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.xtext.ui.editor.model.ResourceForIEditorInputFactory;

public class GlobalConstantsResourceForIEditorInputFactory extends ResourceForIEditorInputFactory {
	@Override
	public Resource createResource(final IEditorInput editorInput) {
		final Resource resource = super.createResource(editorInput);
		if (resource instanceof final GlobalConstantsResource globalConstantsResource
				&& editorInput instanceof final IFileEditorInput fileEditorInput) {
			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(fileEditorInput.getFile());
			if (typeEntry instanceof final GlobalConstantsEntry globalConstantsEntry) {
				globalConstantsResource.setGlobalConstants(globalConstantsEntry.getTypeEditable());
				globalConstantsResource.getDefaultLoadOptions().put(GlobalConstantsResource.OPTION_PLAIN_ST,
						Boolean.TRUE);
			}
		}
		return resource;
	}
}
