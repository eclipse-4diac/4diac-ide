/*******************************************************************************
 * Copyright (c) 2022 - 2024 Primetals Technologies Austria GmbH
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

import java.text.MessageFormat;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.elk.alg.libavoid.server.LibavoidServerException;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.elk.Messages;
import org.eclipse.fordiac.ide.elk.commands.ConnectionLayoutCommand;
import org.eclipse.fordiac.ide.elk.connection.ConnectionRoutingHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConnectionLayoutHandler extends AbstractConnectionLayoutHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final var selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
		final var part = HandlerUtil.getActiveEditor(event);

		if (isSubappLayout(selection)) {
			final var content = (UnfoldedSubappContentEditPart) selection.getFirstElement();
			executeCommand(event, part, getLayoutCommand(content));
		} else if (null != part) {
			executeCommand(event, part, getLayoutCommandNonHierarchical(part));
		}

		return Status.OK_STATUS;
	}

	public static Command getLayoutCommand(final IWorkbenchPart part) {
		return getLayoutCommand(part, true);
	}

	public static Command getLayoutCommand(final UnfoldedSubappContentEditPart content) {
		return getLayoutCommand(content, true);
	}

	public static Command getLayoutCommandNonHierarchical(final IWorkbenchPart part) {
		return getLayoutCommand(part, false);
	}

	public static Command getLayoutCommandNonHierarchical(final UnfoldedSubappContentEditPart content) {
		return getLayoutCommand(content, false);
	}

	private static Command getLayoutCommand(final Object obj, final boolean isHierarchical) {
		try {
			final var mapping = run(obj);
			final var data = ConnectionRoutingHelper.calculateConnections(mapping);
			if (isHierarchical) {
				runSubapps(mapping, data);
			}
			return new ConnectionLayoutCommand(data);
		} catch (final LibavoidServerException e) {
			MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					Messages.ConnectionLayout_TimeoutTitle,
					MessageFormat.format(Messages.ConnectionLayout_TimeoutMessage, e.getMessage()));
		}
		return UnexecutableCommand.INSTANCE;
	}

}
