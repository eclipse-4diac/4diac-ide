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

	public PasteDataFromClipboardCommandHandler(final SelectionLayer selectionLayer) {
		this.selectionLayer = selectionLayer;
	}

	@Override
	protected boolean doCommand(final PasteDataCommand command) {
		final Clipboard clipboard = new Clipboard(Display.getDefault());
		final Object clipboardCellsContents = clipboard.getContents(TextTransfer.getInstance());
		clipboard.dispose();

		if (clipboardCellsContents != null) {
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
