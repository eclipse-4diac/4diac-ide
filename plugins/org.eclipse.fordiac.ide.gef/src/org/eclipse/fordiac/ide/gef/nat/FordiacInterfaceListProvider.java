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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;

public interface FordiacInterfaceListProvider<T extends IInterfaceElement> {

	void setInput(List<T> varDecl);

	default IInterfaceElement getLastSelectedVariable(final NatTable table) {
		final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(table);
		if (selectionLayer != null) {
			final int[] rows = selectionLayer.getFullySelectedRowPositions();
			if (rows.length > 0) {
				final DataLayer dataLayer = (DataLayer) selectionLayer.getUnderlyingLayerByPosition(0, 0);
				final Object rowObject = ((ListDataProvider<?>) dataLayer.getDataProvider())
						.getRowObject(rows[rows.length - 1]);
				return ((IInterfaceElement) rowObject);
			}
		}
		return null;
	}
}