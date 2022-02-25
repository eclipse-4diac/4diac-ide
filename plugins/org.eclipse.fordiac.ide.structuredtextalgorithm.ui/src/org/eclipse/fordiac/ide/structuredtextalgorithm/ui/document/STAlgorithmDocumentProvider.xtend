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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.document

import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary
import org.eclipse.jface.text.IDocument
import org.eclipse.ui.IEditorInput
import org.eclipse.ui.IFileEditorInput
import org.eclipse.xtext.ui.editor.model.XtextDocument
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider

class STAlgorithmDocumentProvider extends XtextDocumentProvider {
	override setDocumentContent(IDocument document, IEditorInput editorInput, String encoding) {
		var result = false
		if (editorInput instanceof IFileEditorInput) {
			val paletteEntry = TypeLibrary.getPaletteEntryForFile(editorInput.file)
			if (paletteEntry !== null) {
				val libraryElement = paletteEntry.typeEditable
				if (libraryElement instanceof SimpleFBType) {
					val algorithm = libraryElement.algorithm.head
					if (algorithm instanceof STAlgorithm) {
						document.set(algorithm.text)
						result = true
					}
				}
			}
		}
		if (result) {
			setDocumentResource(document as XtextDocument, editorInput, encoding)
		}
		return result;
	}

	override doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) {
		if (element instanceof IFileEditorInput) {
			val paletteEntry = TypeLibrary.getPaletteEntryForFile(element.file)
			if (paletteEntry !== null) {
				val libraryElement = paletteEntry.typeEditable
				if (libraryElement instanceof SimpleFBType) {
					val algorithm = libraryElement.algorithm.head
					if (algorithm instanceof STAlgorithm) {
						algorithm.text = document.get
					}
				}
			}
		}
	}
}
