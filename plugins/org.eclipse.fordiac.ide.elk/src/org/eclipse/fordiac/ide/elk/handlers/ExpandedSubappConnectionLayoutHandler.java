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
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.elk.connection.ConnectionRoutingHelper;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.handlers.HandlerUtil;

public class ExpandedSubappConnectionLayoutHandler extends AbstractConnectionLayoutHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final var part = HandlerUtil.getActiveEditor(event);
		assert part != null;
		final var viewer = part.getAdapter(GraphicalViewer.class);

		// property tester ensures type and size == 1
		final var subappEp = (SubAppForFBNetworkEditPart) viewer.getSelectedEditParts().get(0);

		final var mapping = run(subappEp.getContentEP());
		final var data = ConnectionRoutingHelper.calculateConnections(mapping);
		runSubapps(mapping, data);
		executeCommand(event, part, data);

		return Status.OK_STATUS;
	}

}
