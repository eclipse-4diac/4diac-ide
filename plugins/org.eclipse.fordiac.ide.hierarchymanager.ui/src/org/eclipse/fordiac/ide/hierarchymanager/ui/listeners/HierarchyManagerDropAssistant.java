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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;

public class HierarchyManagerDropAssistant extends CommonDropAdapterAssistant {

	public HierarchyManagerDropAssistant() {
		super.init(getContentService());
	}

	@Override
	public IStatus validateDrop(final Object target, final int operation, final TransferData transferType) {
		if (operation != DND.DROP_MOVE) {
			return Status.CANCEL_STATUS;
		}

		if (!super.isSupportedType(transferType)) {
			return Status.CANCEL_STATUS;
		}

		return Status.OK_STATUS;
	}

	@Override
	public IStatus handleDrop(final CommonDropAdapter aDropAdapter, final DropTargetEvent aDropTargetEvent,
			final Object aTarget) {
		// System.out.println("handle");
		return Status.OK_STATUS;
	}

	@Override
	public boolean isSupportedType(final TransferData aTransferType) {
		return super.isSupportedType(aTransferType) && getCurrentEvent().data instanceof SubApp;
	}
}