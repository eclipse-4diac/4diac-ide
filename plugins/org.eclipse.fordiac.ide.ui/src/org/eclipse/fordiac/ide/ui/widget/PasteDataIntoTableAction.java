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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.command.UpdateDataCommand;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.ui.action.IKeyAction;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

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
		final Object clipboardCellsContents = new Clipboard(Display.getCurrent())
				.getContents(TextTransfer.getInstance());
		final Object clipboardElementsContents = org.eclipse.gef.ui.actions.Clipboard.getDefault().getContents();

		if (clipboardCellsContents != null) {
			pasteClipboardCellsContents(natTable, clipboardCellsContents);
		} else if (clipboardElementsContents != null) {
			pasteClipboardElementsContents(natTable, clipboardElementsContents);
		}
	}

	private static void pasteClipboardCellsContents(final NatTable natTable, final Object contents) {
		final String[][] cellsContent = parseContent(contents);

		final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(natTable);

		final List<Rectangle> ranges = selectionLayer.getSelectionModel().getSelections().stream().distinct()
				.collect(Collectors.toList());

		if (ranges.size() != 1) {
			MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "", //$NON-NLS-1$
					FordiacMessages.NatTable_TEXT_Paste);
			return;
		}

		final int startingRow = ranges.get(0).y;
		final int startingColumn = ranges.get(0).x;
		for (int r = 0; r < cellsContent.length; r++) {
			for (int c = 0; c < cellsContent[0].length; c++) {
				final ILayerCell cell = selectionLayer.getCellByPosition(startingColumn + c, startingRow + r);
				if (cell != null) {
					selectionLayer.doCommand(new UpdateDataCommand(selectionLayer, cell.getColumnIndex(),
							cell.getRowIndex(), cellsContent[r][c]));
					selectionLayer.selectRegion(startingColumn, startingRow, cell.getColumnIndex() - startingColumn + 1,
							cell.getRowIndex() - startingRow + 1);
				}
			}
		}
	}

	private void pasteClipboardElementsContents(final NatTable natTable, final Object contents) {
		if (contents instanceof Object[] && section != null) {

			final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(natTable);

			final int[] rows = selectionLayer != null ? selectionLayer.getFullySelectedRowPositions() :  new int[natTable.getColumnCount()];


			final int[] selectedIndices = new int[((Object[]) contents).length];

			int index = selectionLayer != null ? rows[rows.length - 1] + 1 : 0;
			final CompoundCommand cmpCommand = new CompoundCommand();
			int i = 0;
			final boolean isInput = section
					.checkIsInput((ListDataProvider<?>) NatTableWidgetFactory.getDataLayer(natTable).getDataProvider());

			for (final Object entry : (Object[]) contents) {
				selectedIndices[i++] = index;
				section.addEntry(entry, isInput, index++, cmpCommand);
			}

			section.executeCompoundCommand(cmpCommand);
			if (selectionLayer != null) {
				for (final int ind : selectedIndices) {
					selectionLayer.selectRow(0, ind, false, true);
				}
			}
		}
	}

	private static String[][] parseContent(final Object contents) {
		final List<String[]> lines = new ArrayList<>();
		((String) contents).lines().forEach(s -> {
			final String[] st = s.split("\t", -1); //$NON-NLS-1$
			lines.add(st);
		});

		final String[][] content = new String[lines.size()][];
		for (int i = 0; i < lines.size(); i++) {
			content[i] = lines.get(i);
		}

		return content;
	}
}