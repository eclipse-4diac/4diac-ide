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

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.elk.core.service.DiagramLayoutEngine;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.elk.FordiacLayoutConnector;
import org.eclipse.fordiac.ide.elk.helpers.FordiacLayoutFactory;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class LayoutHandler extends AbstractLayoutHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final var part = HandlerUtil.getActiveEditor(event);
		final var selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);

		if (isSubappLayout(selection)) {
			final var content = (UnfoldedSubappContentEditPart) selection.getFirstElement();
			FordiacLayoutConnector.executeManually(List.of(content.getParent()));
		} else if (null != part) {
			DiagramLayoutEngine.invokeLayout(part, null, FordiacLayoutFactory.createLayoutParams());
		}

		return Status.OK_STATUS;
	}

}