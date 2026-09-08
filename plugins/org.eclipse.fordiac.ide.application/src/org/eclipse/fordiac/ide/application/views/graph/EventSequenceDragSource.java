/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.application.views.graph;

import org.eclipse.fordiac.ide.model.graph.FBNetworkGraph;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;

public class EventSequenceDragSource extends DragSourceAdapter {
	private final StructuredViewer viewer;

	public EventSequenceDragSource(final StructuredViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void dragStart(final DragSourceEvent event) {
		if (viewer.getStructuredSelection().isEmpty()
				|| !viewer.getStructuredSelection().stream().allMatch(FBNetworkGraph.Node.class::isInstance)) {
			event.doit = false;
			return;
		}
		LocalSelectionTransfer.getTransfer().setSelection(viewer.getSelection());
	}

	@Override
	public void dragFinished(final DragSourceEvent event) {
		LocalSelectionTransfer.getTransfer().setSelection(null);
	}
}