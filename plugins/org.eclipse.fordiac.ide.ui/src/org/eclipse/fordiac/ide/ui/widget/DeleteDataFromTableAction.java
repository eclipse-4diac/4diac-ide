/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.command.DeleteSelectionCommand;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.ui.action.IKeyAction;
import org.eclipse.swt.events.KeyEvent;

public class DeleteDataFromTableAction implements IKeyAction {
	private final I4diacNatTableUtil section;

	public DeleteDataFromTableAction(final I4diacNatTableUtil section) {
		this.section = section;
	}

	@Override
	public void run(final NatTable natTable, final KeyEvent event) {
		final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(natTable);
		if (selectionLayer != null) {
			final int[] rows = selectionLayer.getFullySelectedRowPositions();

			if (rows.length > 0 && section != null) {
				final CompoundCommand cmpCommand = new CompoundCommand();
				final ListDataProvider<?> provider = (ListDataProvider<?>) NatTableWidgetFactory.getDataLayer(natTable)
						.getDataProvider();
				for (final int row : rows) {
					section.removeEntry(provider.getList().get(row), cmpCommand);
				}
				section.executeCompoundCommand(cmpCommand);
			} else {
				natTable.doCommand(new DeleteSelectionCommand(natTable.getConfigRegistry()));
			}
		}
	}
}
