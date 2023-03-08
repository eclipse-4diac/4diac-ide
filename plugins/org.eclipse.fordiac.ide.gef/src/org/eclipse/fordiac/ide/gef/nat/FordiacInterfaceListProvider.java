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

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;

public interface FordiacInterfaceListProvider<T extends INamedElement> {

	void setInput(List<T> varDecl);

	default INamedElement getLastSelectedVariable(final NatTable table) {
		final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(table);
		if (selectionLayer != null) {
			final int[] rows = selectionLayer.getFullySelectedRowPositions();
			if (rows.length > 0) {
				final DataLayer dataLayer = (DataLayer) selectionLayer.getUnderlyingLayerByPosition(0, 0);
				final Object rowObject = ((ListDataProvider<?>) dataLayer.getDataProvider())
						.getRowObject(rows[rows.length - 1]);
				return ((INamedElement) rowObject);
			}
		}
		return null;
	}
}