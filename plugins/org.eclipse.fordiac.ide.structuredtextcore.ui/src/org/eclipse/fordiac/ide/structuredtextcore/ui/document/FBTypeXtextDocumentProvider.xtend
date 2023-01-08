/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 				 2022 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 * 	 Christoph Binder - Extracted code from STAlgorithmDocumentProvider, to enable possibility to reuse this class for multiple xtexteditors
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.document

import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager
import org.eclipse.fordiac.ide.structuredtextcore.FBTypeXtextResource
import org.eclipse.jface.text.IDocument
import org.eclipse.ui.IEditorInput
import org.eclipse.ui.IFileEditorInput
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel
import org.eclipse.xtext.ui.editor.model.XtextDocument
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider

abstract class FBTypeXtextDocumentProvider extends XtextDocumentProvider {
	
	override setDocumentContent(IDocument document, IEditorInput editorInput, String encoding) {
		var result = false
		if (editorInput instanceof IFileEditorInput) {
			val typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(editorInput.file)
			if (typeEntry !== null) {
				val libraryElement = typeEntry.typeEditable
				if (libraryElement instanceof BaseFBType) {
					setDocumentContent(document, libraryElement)
					result = true
				}
			}
		}
		if (result) {
			setDocumentResource(document as XtextDocument, editorInput, encoding)
		}
		return result
	}
	
	override doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) {
		if (element instanceof IFileEditorInput) {
			val typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(element.file)
			if (typeEntry !== null) {
				val libraryElement = typeEntry.typeEditable
				if (libraryElement instanceof BaseFBType) {
					if (document instanceof XtextDocument) {
						doSaveDocument(monitor, element, libraryElement, document)
					}
				}
			}
			val info = element.getElementInfo as FileInfo;
			if (info !== null) {
				val model = info.fModel
				if (model instanceof ResourceMarkerAnnotationModel) {
					model.updateMarkers(info.fDocument)
				}
				info.fModificationStamp = computeModificationStamp(element.file);
			}
		}
	}
		
	override handleElementContentChanged(IFileEditorInput fileEditorInput) {
		val info = getElementInfo(fileEditorInput) as FileInfo;
		if (info === null) {
			return
		}
		val document = info.fDocument as XtextDocument
		super.handleElementContentChanged(fileEditorInput)
		if (document === info.fDocument) { // still unchanged? -> update FB reference and reparse
			val typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(fileEditorInput.file)
			if (typeEntry !== null) {
				val libraryElement = typeEntry.typeEditable
				if (libraryElement instanceof BaseFBType) {
					removeUnchangedElementListeners(fileEditorInput, info);

					document.internalModify [ resource |
						if (resource instanceof FBTypeXtextResource) {
							resource.fbType = libraryElement
						}
						null
					]

					addUnchangedElementListeners(fileEditorInput, info);
				}
			}
		}
	}

	def abstract void doSaveDocument(IProgressMonitor monitor, IFileEditorInput fileEditorInput, BaseFBType element, XtextDocument document)
	
	def abstract void setDocumentContent(IDocument monitor, BaseFBType element)
}
