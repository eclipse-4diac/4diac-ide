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
 * 	 Christoph Binder - Refactoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.resource

import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextcore.FBTypeXtextResource
import org.eclipse.ui.IEditorInput
import org.eclipse.ui.IFileEditorInput
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.editor.model.ResourceForIEditorInputFactory

class STAlgorithmResourceForIEditorInputFactory extends ResourceForIEditorInputFactory {
	override createResource(IEditorInput editorInput) {
		val resource = super.createResource(editorInput)
		if (resource instanceof XtextResource) {
			if (editorInput instanceof IFileEditorInput) {
				val typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(editorInput.file)
				if (typeEntry !== null) {
					val libraryElement = typeEntry.typeEditable
					if (libraryElement instanceof FBType) {
						if (resource instanceof FBTypeXtextResource) {
							resource.fbType = libraryElement
							if(resource instanceof STAlgorithmResource)
								resource.defaultLoadOptions.put(STAlgorithmResource.OPTION_PLAIN_ST, Boolean.TRUE)
						}
					}
				}
			}
		}
		return resource
	}
}
