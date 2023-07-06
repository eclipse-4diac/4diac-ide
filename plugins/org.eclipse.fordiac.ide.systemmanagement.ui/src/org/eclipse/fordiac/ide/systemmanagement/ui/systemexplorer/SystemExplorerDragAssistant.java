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
package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.navigator.CommonDragAdapterAssistant;

public class SystemExplorerDragAssistant extends CommonDragAdapterAssistant {

	@Override
	public Transfer[] getSupportedTransferTypes() {
		return new Transfer[] { TemplateTransfer.getInstance() };
	}

	@Override
	public void dragStart(final DragSourceEvent anEvent, final IStructuredSelection aSelection) {
		if (aSelection.getFirstElement() instanceof final SubApp subapp) {
			TemplateTransfer.getInstance().setTemplate(subapp);
		} else {
			anEvent.doit = false;
		}
		super.dragStart(anEvent, aSelection);
	}

	@Override
	public boolean setDragData(final DragSourceEvent anEvent, final IStructuredSelection aSelection) {
		if (aSelection.getFirstElement() instanceof final SubApp subapp) {
			anEvent.data = subapp;
			return true;
		}
		return false;
	}

}
