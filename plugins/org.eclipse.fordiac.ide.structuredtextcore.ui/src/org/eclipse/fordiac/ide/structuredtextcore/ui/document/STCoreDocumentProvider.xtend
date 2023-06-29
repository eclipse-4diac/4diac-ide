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
package org.eclipse.fordiac.ide.structuredtextcore.ui.document

import com.google.inject.Inject
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Platform
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreReconciler
import org.eclipse.jface.text.IDocument
import org.eclipse.swt.widgets.Display
import org.eclipse.ui.IFileEditorInput
import org.eclipse.ui.part.FileEditorInput
import org.eclipse.xtext.ui.editor.model.XtextDocument

class STCoreDocumentProvider extends FBTypeXtextDocumentProvider {
	@Inject
	extension STCoreDocumentPartitioner partitioner

	@Inject
	extension STCoreReconciler reconciler

	override void setDocumentContent(IDocument document, FBType fbType) {
		document.set(fbType.combine)
	}

	override void doSaveDocument(IProgressMonitor monitor, IFileEditorInput fileEditorInput, FBType element,
		XtextDocument document) {
		monitor.beginTask("Saving", 2)
		try {
			monitor.subTask("Partitioning")
			val partition = document.partition
			monitor.worked(1)
			monitor.subTask("Reconciling")
			if (partition !== null) {
				Display.^default.syncExec [
					element.reconcile(partition)
					// save type if opened directly from a file and not in an FB type editor,
					// indicated by a FileEditorInput instead of a FBTypeEditorInput
					if (fileEditorInput instanceof FileEditorInput) {
						element.typeEntry.save
					}
				]
			}
		} catch (Exception e) {
			Platform.getLog(class).error("Error saving algorithms to FB type", e)
		} finally {
			monitor.done
		}
	}
}
