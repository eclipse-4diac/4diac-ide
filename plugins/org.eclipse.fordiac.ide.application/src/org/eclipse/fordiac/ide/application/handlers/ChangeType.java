/*******************************************************************************
 * Copyright (c) 2020, 2025 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.editparts.AbstractBlockFBNElementEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class ChangeType extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final AbstractBlockFBNElementEditPart element = getSelectedFBElementEditPart(event);
		if (null != element) {
			element.performDirectEdit();
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	private static AbstractBlockFBNElementEditPart getSelectedFBElementEditPart(final ExecutionEvent event) {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof final StructuredSelection structSel
				&& structSel.getFirstElement() instanceof final AbstractBlockFBNElementEditPart fbnElEP) {
			return fbnElEP;
		}
		return null;
	}
}
