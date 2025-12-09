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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.providers.RowHeaderDataProvider;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.nebula.widgets.nattable.command.AbstractLayerCommandHandler;
import org.eclipse.nebula.widgets.nattable.copy.command.PasteDataCommand;
import org.eclipse.nebula.widgets.nattable.edit.command.EditUtils;
import org.eclipse.nebula.widgets.nattable.edit.command.UpdateDataCommand;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class PasteDataFromClipboardCommandHandler extends AbstractLayerCommandHandler<PasteDataCommand> {

	protected final SelectionLayer selectionLayer;
	protected final I4diacNatTableUtil section;
	private final RowHeaderDataProvider rowHeaderDataProvider;

	public PasteDataFromClipboardCommandHandler(final SelectionLayer selectionLayer) {
		this(selectionLayer, null, null);
	}

	public PasteDataFromClipboardCommandHandler(final SelectionLayer selectionLayer, final I4diacNatTableUtil section,
			final RowHeaderDataProvider rowHeaderDataProvider) {
		this.selectionLayer = selectionLayer;
		this.section = section;
		this.rowHeaderDataProvider = rowHeaderDataProvider;
	}

	@Override
	protected boolean doCommand(final PasteDataCommand command) {
		final Clipboard clipboard = new Clipboard(Display.getDefault());
		final Object clipboardCellsContents = clipboard.getContents(TextTransfer.getInstance());
		final Object clipboardElementsContents = clipboard.getContents(DataObjectTransfer.getInstance());
		clipboard.dispose();

		if (clipboardElementsContents != null) {
			pasteClipboardElementsContents(clipboardElementsContents);
		} else if (clipboardCellsContents != null) {
			pasteClipboardCellsContents(command, clipboardCellsContents);
		}
		return true;
	}

	protected void pasteClipboardCellsContents(final PasteDataCommand command, final Object contents) {
		final String[][] cellsContent = parseContent(contents);

		if (!EditUtils.allCellsEditable(selectionLayer, command.configRegistry)) {
			return;
		}

		final List<Rectangle> ranges = selectionLayer.getSelectionModel().getSelections().stream().distinct().toList();

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

	private void pasteClipboardElementsContents(final Object contents) {
		if (!(contents instanceof final Object[] contentsArray) || rowHeaderDataProvider == null || section == null
				|| !section.isEditable()) {
			return;
		}

		final CompoundCommand cmpCommand = new CompoundCommand();
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
			int index = rows.length != 0 ? rows[rows.length - 1] + 1 : selectionLayer.getRowCount();
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
			updateNewRow(selectedIndices);
		}
	}

	protected void updateNewRow(final int[] rowIndices) {
		// allow subclasses to provide additional functionality
	}

	@SuppressWarnings("static-method") // allow subclasses to provide special versions
	protected String[][] parseContent(final Object contents) {
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

	@Override
	public Class<PasteDataCommand> getCommandClass() {
		return PasteDataCommand.class;
	}
}
