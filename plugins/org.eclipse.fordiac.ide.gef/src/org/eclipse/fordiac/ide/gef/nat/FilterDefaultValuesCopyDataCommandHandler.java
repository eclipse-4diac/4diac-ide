/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.gef.nat;

import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.copy.command.CopyDataCommandHandler;
import org.eclipse.nebula.widgets.nattable.copy.command.CopyDataToClipboardCommand;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;

public class FilterDefaultValuesCopyDataCommandHandler extends CopyDataCommandHandler {

	public FilterDefaultValuesCopyDataCommandHandler(final SelectionLayer selectionLayer) {
		super(selectionLayer);
	}

	@Override
	protected void internalDoCommand(final CopyDataToClipboardCommand command,
			final ILayerCell[][] assembledCopiedDataStructure) {
		for (final ILayerCell[] row : assembledCopiedDataStructure) {
			for (int i = 0; i < row.length; i++) {
				if (row[i] != null && shouldExclude(row[i])) {
					row[i] = null;
				}
			}
		}
		super.internalDoCommand(command, assembledCopiedDataStructure);
	}

	private static boolean shouldExclude(final ILayerCell cell) {
		return cell.getConfigLabels().hasLabel(NatTableWidgetFactory.DEFAULT_CELL) || cell.getDataValue() == null;
	}
}
