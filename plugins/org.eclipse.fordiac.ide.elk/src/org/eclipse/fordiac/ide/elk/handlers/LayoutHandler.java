/*******************************************************************************
 * Copyright (c) 2021 - 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.elk.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.elk.FordiacLayout;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class LayoutHandler extends AbstractLayoutHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final var part = HandlerUtil.getActiveEditor(event);
		final var selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);

		if (!selection.isEmpty() && canLayout(selection)) {
			FordiacLayout.blockLayout(part, (AbstractFBNetworkEditPart) selection.getFirstElement());
		}

		return Status.OK_STATUS;
	}

}