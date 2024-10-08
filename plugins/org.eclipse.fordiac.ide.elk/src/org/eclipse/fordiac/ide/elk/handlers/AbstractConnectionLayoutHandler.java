/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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
import org.eclipse.elk.core.RecursiveGraphLayoutEngine;
import org.eclipse.elk.core.util.BasicProgressMonitor;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.elk.connection.ConnectionLayoutMapping;
import org.eclipse.fordiac.ide.elk.connection.ConnectionRoutingHelper;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchPart;

public abstract class AbstractConnectionLayoutHandler extends AbstractLayoutHandler {

	protected static void executeCommand(final ExecutionEvent event, final IWorkbenchPart part, final Command cmd) {
		if (isAutomaticLayout(event)) {
			// passes the cmd back to the editor to be handled there
			((Event) event.getTrigger()).data = cmd;
		} else {
			part.getAdapter(CommandStack.class).execute(cmd);
		}
	}

	protected static boolean isAutomaticLayout(final ExecutionEvent event) {
		// widget is in case of manual activation the menu item
		// the automatic execution is programmatic and does therefore not have a widget
		return event.getTrigger() instanceof final Event ev && ev.widget == null;
	}

	protected static void runSubapps(final ConnectionLayoutMapping mapping, final FordiacLayoutData data) {
		for (final UnfoldedSubappContentEditPart subapp : mapping.getExpandedSubapps()) {
			try {
				final ConnectionLayoutMapping subappMapping = run(subapp);
				final FordiacLayoutData subappData = ConnectionRoutingHelper.calculateConnections(subappMapping);
				runSubapps(subappMapping, subappData);

				// combine the recursive subapp runs
				data.getConnectionPoints().addAll(subappData.getConnectionPoints());
			} catch (final Exception e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
	}

	protected static ConnectionLayoutMapping run(final Object parent) {
		final ConnectionLayoutMapping mapping = getLayoutMapping(parent);

		if (mapping != null && mapping.hasNetwork()) {
			// build graph
			ConnectionRoutingHelper.buildGraph(mapping);
			// layout graph
			new RecursiveGraphLayoutEngine().layout(mapping.getLayoutGraph(), new BasicProgressMonitor());
		}

		return mapping;
	}

	protected static ConnectionLayoutMapping getLayoutMapping(final Object parent) {
		if (parent instanceof final IWorkbenchPart wbp) {
			return new ConnectionLayoutMapping(wbp);
		}
		if (parent instanceof final UnfoldedSubappContentEditPart unfSubAppContentEP) {
			return new ConnectionLayoutMapping(unfSubAppContentEP);
		}
		return null;
	}
}
