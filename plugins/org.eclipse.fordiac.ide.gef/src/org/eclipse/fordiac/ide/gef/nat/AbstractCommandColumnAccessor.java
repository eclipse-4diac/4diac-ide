/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.util.List;

import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.nattable.AbstractColumnAccessor;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumn;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.internal.C;

public abstract class AbstractCommandColumnAccessor<T, C extends NatTableColumn> extends AbstractColumnAccessor<T, C> {
	private final CommandExecutor commandExecutor;

	protected AbstractCommandColumnAccessor(final CommandExecutor commandExecutor, final List<C> columns) {
		super(columns);
		this.commandExecutor = commandExecutor;
	}

	protected CommandExecutor getCommandExecutor() {
		return commandExecutor;
	}

	@Override
	public void setDataValue(final T rowObject, final C column, final Object newValue) {
		final Command cmd = createCommand(rowObject, column, newValue);
		if (cmd.canExecute()) {
			commandExecutor.executeCommand(cmd);
		}
	}

	public abstract Command createCommand(final T rowObject, final C column, final Object newValue);
}
