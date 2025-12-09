/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.resource;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.editors.ITypeEditorInput;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResource;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.xtext.ui.editor.model.ResourceForIEditorInputFactory;

public class STCoreResourceForIEditorInputFactory extends ResourceForIEditorInputFactory {
	@Override
	public Resource createResource(final IEditorInput editorInput) {
		final Resource resource = super.createResource(editorInput);
		if (resource instanceof final LibraryElementXtextResource libraryElementXtextResource) {
			final LibraryElement libraryElement = getLibraryElement(editorInput);
			libraryElementXtextResource.setLibraryElement(libraryElement);
			libraryElementXtextResource.setIncludeInternalLibraryElement(libraryElement instanceof BaseFBType);
			if (resource instanceof final STCoreResource stCoreResource) {
				stCoreResource.getDefaultLoadOptions().put(STCoreResource.OPTION_PLAIN_ST, Boolean.TRUE);
			}
		}
		return resource;
	}

	protected static LibraryElement getLibraryElement(final IEditorInput editorInput) {
		if (editorInput instanceof final ITypeEditorInput typeEditorInput) {
			return typeEditorInput.getContent();
		}
		if (editorInput instanceof final IFileEditorInput fileEditorInput) {
			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(fileEditorInput.getFile());
			if (typeEntry != null) {
				return typeEntry.copyType();
			}
		}
		return null;
	}
}
