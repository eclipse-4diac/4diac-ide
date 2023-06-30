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
package org.eclipse.fordiac.ide.globalconstantseditor.ui.editor.reconciler;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.globalconstantseditor.resource.GlobalConstantsResource;
import org.eclipse.fordiac.ide.globalconstantseditor.util.GlobalConstantsPartitioner;
import org.eclipse.fordiac.ide.globalconstantseditor.util.GlobalConstantsReconciler;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.reconciler.XtextDocumentReconcileStrategy;

import com.google.inject.Inject;

public class GlobalConstantsDocumentReconcileStrategy extends XtextDocumentReconcileStrategy {
	@Inject
	private GlobalConstantsPartitioner partitioner;

	@Override
	protected void postParse(final XtextResource resource, final IProgressMonitor monitor) {
		super.postParse(resource, monitor);
		if (monitor.isCanceled()) {
			return;
		}
		if (resource instanceof final GlobalConstantsResource globalConstantsResource) {
			final var globalConstants = globalConstantsResource.getGlobalConstants();
			final var partition = partitioner.partition(resource);
			Display.getDefault().asyncExec(() -> GlobalConstantsReconciler.reconcile(globalConstants, partition, null));
		}
	}
}
