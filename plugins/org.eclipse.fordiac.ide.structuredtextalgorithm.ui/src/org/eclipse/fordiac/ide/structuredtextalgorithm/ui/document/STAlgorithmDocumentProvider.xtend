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

import com.google.inject.Inject
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Platform
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmReconciler
import org.eclipse.jface.text.IDocument
import org.eclipse.swt.widgets.Display
import org.eclipse.ui.IEditorInput
import org.eclipse.ui.IFileEditorInput
import org.eclipse.xtext.ui.editor.model.XtextDocument
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider

class STAlgorithmDocumentProvider extends XtextDocumentProvider {
	@Inject
	extension STAlgorithmDocumentPartitioner partitioner

	@Inject
	extension STAlgorithmReconciler reconciler

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

	def void setDocumentContent(IDocument document, BaseFBType fbType) {
		document.set(fbType.combine)
	}

	override doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) {
		if (element instanceof IFileEditorInput) {
			val typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(element.file)
			if (typeEntry !== null) {
				val libraryElement = typeEntry.typeEditable
				if (libraryElement instanceof BaseFBType) {
					if (document instanceof XtextDocument) {
						doSaveDocument(monitor, libraryElement, document)
					}
				}
			}
		}
	}

	def void doSaveDocument(IProgressMonitor monitor, BaseFBType element, XtextDocument document) {
		monitor.beginTask("Saving", 2)
		try {
			monitor.subTask("Partitioning")
			val partition = document.partition
			monitor.worked(1)
			monitor.subTask("Reconciling")
			Display.^default.syncExec[
				element.callables.reconcile(partition)
				AbstractTypeExporter.saveType(element.typeEntry);
			]
		} catch (Exception e) {
			Platform.getLog(class).error("Error saving algorithms to FB type", e)
		} finally {
			monitor.done
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
						if (resource instanceof STAlgorithmResource) {
							resource.fbType = libraryElement
							resource.relink
						}
						null
					]

					addUnchangedElementListeners(fileEditorInput, info);
				}
			}
		}
	}
}
