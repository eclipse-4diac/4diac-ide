/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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

import org.eclipse.nebula.widgets.nattable.copy.command.CopyDataCommandHandler;
import org.eclipse.nebula.widgets.nattable.copy.command.CopyDataToClipboardCommand;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

public class FordiacCopyDataCommandHandler extends CopyDataCommandHandler {
	public FordiacCopyDataCommandHandler(final SelectionLayer selectionLayer) {
		super(selectionLayer);
	}

	@Override
	protected void internalDoCommand(final CopyDataToClipboardCommand command,
			final ILayerCell[][] assembledCopiedDataStructure) {
		super.internalDoCommand(command, assembledCopiedDataStructure);

		final Clipboard clipboard = new Clipboard(Display.getDefault());
		final int[] rows = selectionLayer.getFullySelectedRowPositions();
		if (rows.length > 0) {
			final ListDataProvider<?> provider = (ListDataProvider<?>) ((DataLayer) selectionLayer
					.getUnderlyingLayerByPosition(0, 0)).getDataProvider();

			int i = 0;
			final Object[] objects = new Object[rows.length];
			for (final int row : rows) {
				objects[i] = provider.getRowObject(row);
				i++;
			}

			clipboard.setContents(new Object[] { objects }, new Transfer[] { DataObjectTransfer.getInstance() });
		}

		clipboard.dispose();
	}
}
