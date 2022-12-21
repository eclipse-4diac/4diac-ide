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
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.convert.DefaultBooleanDisplayConverter;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.CheckBoxCellEditor;
import org.eclipse.nebula.widgets.nattable.painter.cell.CheckBoxPainter;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;

/*
 * Class intended for displaying a check box in the nattable by Nebula. For this particular case you need an
 * editor, painter and a converter. Painter draws the check box and the converter takes the boolean value
 * and represents it as either checked or unchecked.
 */

public class CheckBoxConfigurationNebula extends AbstractRegistryConfiguration {

	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {
		registerCheckBoxEditor(configRegistry);
	}

	private static void registerCheckBoxEditor(final IConfigRegistry configRegistry) {

		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, new CheckBoxCellEditor(),
				DisplayMode.EDIT, NatTableWidgetFactory.CHECKBOX_CELL);

		// Visualized as a check box button
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER,
				new CheckBoxPainter(),
				DisplayMode.NORMAL, NatTableWidgetFactory.CHECKBOX_CELL);

		// Needs a Boolean conversion to work correctly
		configRegistry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER,
				new DefaultBooleanDisplayConverter(), DisplayMode.NORMAL, NatTableWidgetFactory.CHECKBOX_CELL);
	}

}
