/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
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
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class ChangeType extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final AbstractFBNElementEditPart element = getSelectedFBElementEditPart(event);
		if (null != element) {
			element.performDirectEdit();
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	private static AbstractFBNElementEditPart getSelectedFBElementEditPart(ExecutionEvent event) {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof StructuredSelection) {
			final Object selObj = ((StructuredSelection) selection).getFirstElement();
			if (selObj instanceof AbstractFBNElementEditPart) {
				return (AbstractFBNElementEditPart) selObj;
			}
		}
		return null;
	}
}
