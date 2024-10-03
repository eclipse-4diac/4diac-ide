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
 *   Daniel Lindhuber - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.elk.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.elk.FordiacLayout;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConnectionLayoutHandlerMule extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final var part = HandlerUtil.getActiveEditor(event);
		final var mule = (Event) event.getTrigger();
		mule.data = (part != null) ? FordiacLayout.getConnectionLayoutCommand(part) : UnexecutableCommand.INSTANCE;
		return Status.OK_STATUS;
	}

}
