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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.resource

import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.ui.IEditorInput
import org.eclipse.ui.IFileEditorInput
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.editor.model.ResourceForIEditorInputFactory

class STAlgorithmResourceForIEditorInputFactory extends ResourceForIEditorInputFactory {
	override createResource(IEditorInput editorInput) {
		val resource = super.createResource(editorInput)
		if (resource instanceof XtextResource) {
			if (editorInput instanceof IFileEditorInput) {
				val paletteEntry = TypeLibrary.getPaletteEntryForFile(editorInput.file)
				if (paletteEntry !== null) {
					val libraryElement = paletteEntry.typeEditable
					if (libraryElement instanceof FBType) {
						if (resource instanceof STAlgorithmResource) {
							resource.fbType = libraryElement
						}
					}
				}
			}
		}
		return resource
	}
}
