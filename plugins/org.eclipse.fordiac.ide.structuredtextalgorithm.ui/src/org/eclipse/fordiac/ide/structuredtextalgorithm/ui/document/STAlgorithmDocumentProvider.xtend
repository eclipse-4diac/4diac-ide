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
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmReconciler
import org.eclipse.fordiac.ide.structuredtextcore.ui.document.FBTypeXtextDocumentProvider
import org.eclipse.jface.text.IDocument
import org.eclipse.swt.widgets.Display
import org.eclipse.xtext.ui.editor.model.XtextDocument

class STAlgorithmDocumentProvider extends FBTypeXtextDocumentProvider {
	@Inject
	extension STAlgorithmDocumentPartitioner partitioner

	@Inject
	extension STAlgorithmReconciler reconciler


	override void setDocumentContent(IDocument document, BaseFBType fbType) {
		document.set(fbType.combine)
	}

	override void doSaveDocument(IProgressMonitor monitor, BaseFBType element, XtextDocument document) {
		monitor.beginTask("Saving", 2)
		try {
			monitor.subTask("Partitioning")
			val partition = document.partition
			monitor.worked(1)
			monitor.subTask("Reconciling")
			Display.^default.syncExec [
				element.callables.reconcile(partition)
				element.typeEntry.save
			]
		} catch (Exception e) {
			Platform.getLog(class).error("Error saving algorithms to FB type", e)
		} finally {
			monitor.done
		}
	}
	
}
