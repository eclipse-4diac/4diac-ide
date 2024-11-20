/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH,
 *                          Johannes Kepler University, Linz
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *   Prankur Agarwal - fixed the issues with pasting
 *   Martin Jobst - check editable before pasting
 *******************************************************************************/

package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.fordiac.ide.ui.providers.RowHeaderDataProvider;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.copy.action.PasteDataAction;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.ui.action.IKeyAction;
import org.eclipse.swt.events.KeyEvent;

public class PasteDataIntoTableAction implements IKeyAction {

	private final I4diacNatTableUtil section;

	public PasteDataIntoTableAction() {
		this.section = null;
	}

	public PasteDataIntoTableAction(final I4diacNatTableUtil section) {
		this.section = section;
	}

	@Override
	public void run(final NatTable natTable, final KeyEvent event) {
		final Object clipboardElementsContents = Clipboard.getDefault().getContents();

		if (clipboardElementsContents != null) {
			pasteClipboardElementsContents(natTable, clipboardElementsContents);
		} else {
			new PasteDataAction().run(natTable, event);
		}
	}

	private void pasteClipboardElementsContents(final NatTable natTable, final Object contents) {
		if (contents instanceof final Object[] contentsArray && section != null && section.isEditable()) {
			final CompoundCommand cmpCommand = new CompoundCommand();
			final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(natTable);
			final RowHeaderDataProvider rowHeaderDataProvider = (RowHeaderDataProvider) (((DataLayer) ((GridLayer) natTable
					.getUnderlyingLayerByPosition(0, 0)).getRowHeaderLayer().getUnderlyingLayerByPosition(0, 0))
					.getDataProvider());

			if (selectionLayer == null) {
				int i = 0;
				for (final Object entry : contentsArray) {
					section.addEntry(entry, rowHeaderDataProvider.isInput(), i, cmpCommand);
					i++;
				}
				section.executeCompoundCommand(cmpCommand);
			} else {
				final int[] rows = selectionLayer.getFullySelectedRowPositions();
				final int[] selectedIndices = new int[contentsArray.length];
				int index = rows.length != 0 ? rows[rows.length - 1] + 1 : natTable.getRowCount();
				int i = 0;
				for (final Object entry : contentsArray) {
					selectedIndices[i] = index;
					i++;
					section.addEntry(entry, rowHeaderDataProvider.isInput(), index, cmpCommand);
					index++;
				}
				section.executeCompoundCommand(cmpCommand);
				for (final int ind : selectedIndices) {
					selectionLayer.selectRow(0, ind, false, true);
				}
			}
		}
	}
}