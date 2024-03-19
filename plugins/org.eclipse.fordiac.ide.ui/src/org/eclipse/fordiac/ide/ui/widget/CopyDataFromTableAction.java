/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.copy.action.CopyDataAction;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.ui.action.IKeyAction;
import org.eclipse.swt.events.KeyEvent;

public class CopyDataFromTableAction implements IKeyAction {

	@Override
	public void run(final NatTable natTable, final KeyEvent event) {
		final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(natTable);
		if (selectionLayer != null && selectionLayer.hasRowSelection()) {
			final int[] rows = selectionLayer.getFullySelectedRowPositions();

			if (rows.length > 0) {
				final ListDataProvider<?> provider = (ListDataProvider<?>) NatTableWidgetFactory.getDataLayer(natTable)
						.getDataProvider();

				int i = 0;
				final Object[] objects = new Object[rows.length];
				for (final int row : rows) {
					objects[i] = provider.getRowObject(row);
					i++;
				}

				Clipboard.getDefault().setContents(objects);
			} else {
				new CopyDataAction().run(natTable, event);
			}
		}
	}
}
