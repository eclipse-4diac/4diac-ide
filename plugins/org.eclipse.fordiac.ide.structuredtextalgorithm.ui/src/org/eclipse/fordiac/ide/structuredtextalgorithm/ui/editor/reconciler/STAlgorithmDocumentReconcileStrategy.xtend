/**
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
 */
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.reconciler

import com.google.inject.Inject
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmPartitioner
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmReconciler
import org.eclipse.swt.widgets.Display
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.editor.reconciler.XtextDocumentReconcileStrategy

class STAlgorithmDocumentReconcileStrategy extends XtextDocumentReconcileStrategy {

	@Inject
	extension STAlgorithmPartitioner partitioner

	@Inject
	extension STAlgorithmReconciler reconciler

	override postParse(XtextResource resource, IProgressMonitor monitor) {
		super.postParse(resource, monitor)
		if(monitor.canceled) return;
		if (resource instanceof STAlgorithmResource) {
			val fbType = resource.fbType
			if (fbType instanceof BaseFBType) {
				val partition = resource.partition
				Display.^default.asyncExec[fbType.callables.reconcile(partition)]
			}
		}
	}
}
