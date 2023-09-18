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
package org.eclipse.fordiac.ide.structuredtextcore.ui.editor.reconciler;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResource;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartitioner;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreReconciler;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.reconciler.XtextDocumentReconcileStrategy;

import com.google.inject.Inject;

public class STCoreDocumentReconcileStrategy extends XtextDocumentReconcileStrategy {
	@Inject
	private STCorePartitioner partitioner;

	@Inject
	private STCoreReconciler reconciler;

	@Override
	protected void postParse(final XtextResource resource, final IProgressMonitor monitor) {
		super.postParse(resource, monitor);
		if (monitor.isCanceled()) {
			return;
		}
		if (resource instanceof final STCoreResource stCoreResource) {
			final LibraryElement libraryElement = stCoreResource.getLibraryElement();
			if (libraryElement != null) {
				final var partition = partitioner.partition(resource);
				if (partition.isPresent()) {
					Display.getDefault().asyncExec(() -> reconciler.reconcile(libraryElement, partition));
				}
			}
		}
	}

}
