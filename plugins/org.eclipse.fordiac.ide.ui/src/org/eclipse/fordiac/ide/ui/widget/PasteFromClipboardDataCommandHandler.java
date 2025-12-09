/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.ui.widget;

import java.util.Objects;

import org.eclipse.nebula.widgets.nattable.command.AbstractLayerCommandHandler;
import org.eclipse.nebula.widgets.nattable.coordinate.PositionCoordinate;
import org.eclipse.nebula.widgets.nattable.copy.command.PasteDataCommand;
import org.eclipse.nebula.widgets.nattable.edit.command.EditUtils;
import org.eclipse.nebula.widgets.nattable.edit.command.UpdateDataCommand;
import org.eclipse.nebula.widgets.nattable.layer.IUniqueIndexLayer;
import org.eclipse.nebula.widgets.nattable.layer.LayerUtil;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.widgets.Display;

public class PasteFromClipboardDataCommandHandler extends AbstractLayerCommandHandler<PasteDataCommand> {

	private final SelectionLayer selectionLayer;
	private final IUniqueIndexLayer pasteLayer;

	public PasteFromClipboardDataCommandHandler(final SelectionLayer selectionLayer) {
		this(selectionLayer, selectionLayer);
	}

	public PasteFromClipboardDataCommandHandler(final SelectionLayer selectionLayer,
			final IUniqueIndexLayer pasteLayer) {
		this.selectionLayer = selectionLayer;
		this.pasteLayer = pasteLayer;
	}

	@Override
	protected boolean doCommand(final PasteDataCommand command) {
		final String content = getClipboardContent();
		if (content.isEmpty()) {
			return true;
		}

		final String[][] copiedCells = parseContent(content);
		final PositionCoordinate anchor = selectionLayer.getSelectionAnchor();
		int pasteColumn = anchor.getColumnPosition();
		int pasteRow = anchor.getRowPosition();
		if (pasteLayer != selectionLayer) {
			pasteColumn = LayerUtil.convertColumnPosition(selectionLayer, pasteColumn, pasteLayer);
			pasteRow = LayerUtil.convertRowPosition(selectionLayer, pasteRow, pasteLayer);
		}

		for (final String[] copiedRow : copiedCells) {
			for (final String copiedCell : copiedRow) {
				final ILayerCell targetCell = pasteLayer.getCellByPosition(pasteColumn, pasteRow);

				if (EditUtils.isCellEditable(targetCell, command.configRegistry)) {
					pasteLayer.doCommand(new UpdateDataCommand(pasteLayer, pasteColumn, pasteRow, copiedCell));
				}
				pasteColumn++;
				if (pasteColumn >= pasteLayer.getColumnCount()) {
					break;
				}
			}
			pasteRow++;
			pasteColumn = anchor.getColumnPosition();
		}

		return true;
	}

	protected static String getClipboardContent() {
		final Clipboard clipboard = new Clipboard(Display.getDefault());
		try {
			return Objects.toString(clipboard.getContents(TextTransfer.getInstance()), ""); //$NON-NLS-1$
		} finally {
			clipboard.dispose();
		}
	}

	private static String[][] parseContent(final String content) {
		return content.lines().map(PasteFromClipboardDataCommandHandler::parseLine).toArray(String[][]::new);
	}

	private static String[] parseLine(final String line) {
		return line.split("\t", -1); //$NON-NLS-1$
	}

	@Override
	public Class<PasteDataCommand> getCommandClass() {
		return PasteDataCommand.class;
	}
}
