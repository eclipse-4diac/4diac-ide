/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.ui.widget;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;

public class DropdownSelectionWidget extends AbstractRegistryConfiguration {

	final org.eclipse.nebula.widgets.nattable.edit.editor.ComboBoxCellEditor cellEditor;

	public DropdownSelectionWidget(final List<String> selectionPool) {
		cellEditor = new org.eclipse.nebula.widgets.nattable.edit.editor.ComboBoxCellEditor(selectionPool);
	}

	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, cellEditor, DisplayMode.EDIT,
				NatTableWidgetFactory.RETAIN_CONFIG_CELL);
	}

}
