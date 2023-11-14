/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
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
import org.eclipse.elk.core.RecursiveGraphLayoutEngine;
import org.eclipse.elk.core.util.BasicProgressMonitor;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.elk.Messages;
import org.eclipse.fordiac.ide.elk.commands.ConnectionLayoutCommand;
import org.eclipse.fordiac.ide.elk.connection.ConnectionLayoutMapping;
import org.eclipse.fordiac.ide.elk.connection.ConnectionRoutingHelper;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConnectionLayoutHandler extends AbstractLayoutHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchPart part = HandlerUtil.getActiveEditor(event);
		if (null != part) {
			try {
				final ConnectionLayoutMapping mapping = run(part);
				final FordiacLayoutData data = ConnectionRoutingHelper.calculateConnections(mapping);
				runSubapps(mapping, data);
				executeCommand(event, part, data);
			} catch (final LibavoidServerException e) {
				MessageDialog.openWarning(HandlerUtil.getActiveShell(event), Messages.ConnectionLayout_TimeoutTitle,
						MessageFormat.format(Messages.ConnectionLayout_TimeoutMessage, e.getMessage()));
			}
		}
		return Status.OK_STATUS;
	}

	private static void executeCommand(final ExecutionEvent event, final IWorkbenchPart part,
			final FordiacLayoutData data) {
		final ConnectionLayoutCommand cmd = new ConnectionLayoutCommand(data);
		if (isAutomaticLayout(event)) {
			// passes the cmd back to the editor to be handled there
			((Event) event.getTrigger()).data = cmd;
		} else {
			part.getAdapter(CommandStack.class).execute(cmd);
		}
	}

	private static boolean isAutomaticLayout(final ExecutionEvent event) {
		// widget is in case of manual activation the menu item
		// the automatic execution is programmatic and does therefore not have a widget
		return event.getTrigger() instanceof final Event ev && ev.widget == null;
	}

	public static void runSubapps(final ConnectionLayoutMapping mapping, final FordiacLayoutData data) {
		for (final UnfoldedSubappContentEditPart subapp : mapping.getExpandedSubapps()) {
			final ConnectionLayoutMapping subappMapping = run(subapp);
			final FordiacLayoutData subappData = ConnectionRoutingHelper.calculateConnections(subappMapping);
			runSubapps(subappMapping, subappData);
			// combine the recursive subapp runs
			data.getConnectionPoints().addAll(subappData.getConnectionPoints());
		}
	}

	private static ConnectionLayoutMapping run(final Object parent) {
		final ConnectionLayoutMapping mapping = getLayoutMapping(parent);

		if (mapping != null && mapping.hasNetwork()) {
			// build graph
			ConnectionRoutingHelper.buildGraph(mapping);
			// layout graph
			new RecursiveGraphLayoutEngine().layout(mapping.getLayoutGraph(), new BasicProgressMonitor());
		}

		return mapping;
	}

	private static ConnectionLayoutMapping getLayoutMapping(final Object parent) {
		if (parent instanceof final IWorkbenchPart wbp) {
			return new ConnectionLayoutMapping(wbp);
		}
		if (parent instanceof final UnfoldedSubappContentEditPart unfSubAppContentEP) {
			return new ConnectionLayoutMapping(unfSubAppContentEP);
		}
		return null;
	}

}
