/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.listeners;

import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Node;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.navigator.CommonDragAdapterAssistant;
import org.eclipse.ui.part.ResourceTransfer;

public class HierarchyManagerDragAssistant extends CommonDragAdapterAssistant {

	@Override
	public Transfer[] getSupportedTransferTypes() {
		return new Transfer[] { ResourceTransfer.getInstance() };
	}

	@Override
	public void dragStart(final DragSourceEvent anEvent, final IStructuredSelection aSelection) {
		if (!(aSelection.getFirstElement() instanceof Node)) {
			anEvent.doit = false;
		}

		super.dragStart(anEvent, aSelection);
	}

	@Override
	public boolean setDragData(final DragSourceEvent anEvent, final IStructuredSelection aSelection) {
		if (aSelection.getFirstElement() instanceof Node) {
			anEvent.data = aSelection.getFirstElement();
			return true;
		}

		return false;
	}
}