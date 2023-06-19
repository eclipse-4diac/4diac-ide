/**
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.editor.reconciler

import com.google.inject.Inject
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResource
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartitioner
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreReconciler
import org.eclipse.swt.widgets.Display
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.editor.reconciler.XtextDocumentReconcileStrategy

class STCoreDocumentReconcileStrategy extends XtextDocumentReconcileStrategy {

	@Inject
	extension STCorePartitioner partitioner

	@Inject
	extension STCoreReconciler reconciler

	override postParse(XtextResource resource, IProgressMonitor monitor) {
		super.postParse(resource, monitor)
		if(monitor.canceled) return;
		if (resource instanceof STCoreResource) {
			val fbType = resource.fbType
			val partition = resource.partition
			if(partition !== null) {
				Display.^default.asyncExec[fbType.reconcile(partition)]
			}
		}
	}
}
