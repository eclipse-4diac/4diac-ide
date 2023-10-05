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

import org.eclipse.fordiac.ide.ui.widget.ColumnCachingDataLayer;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.command.UpdateDataCommand;
import org.eclipse.nebula.widgets.nattable.edit.command.UpdateDataCommandHandler;

public class VarDeclarationDataLayer extends ColumnCachingDataLayer<VarDeclarationTableColumn> {

	public VarDeclarationDataLayer(final IDataProvider dataProvider, final List<VarDeclarationTableColumn> columns) {
		super(dataProvider, columns, VarDeclarationTableColumn.INITIAL_VALUE);
	}

	@Override
	protected void registerCommandHandlers() {
		super.registerCommandHandlers();

		// always update value
		unregisterCommandHandler(UpdateDataCommand.class);
		registerCommandHandler(new UpdateDataCommandHandler(this, false));
	}
}
