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
 *   Alois Zoitl - Extracted from NatTableWidgetFactory
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget.nattable;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.BackgroundPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.PaddingDecorator;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.HorizontalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.style.SelectionStyleLabels;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.style.theme.ModernNatTableThemeConfiguration;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.ui.PlatformUI;

final class FordiacNatTableConfiguration extends ModernNatTableThemeConfiguration {
	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {
		super.configureRegistry(configRegistry);
		Style cellStyle = new Style();

		cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.COLOR_RED);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
				NatTableWidgetFactory.ERROR_CELL);

		cellStyle = new Style();
		cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.getColor(255, 100, 100));
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.SELECT,
				NatTableWidgetFactory.ERROR_CELL);

		cellStyle = new Style();
		cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.COLOR_WHITE);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.EDIT,
				NatTableWidgetFactory.ERROR_CELL);

		cellStyle = new Style();
		cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.COLOR_YELLOW);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
				NatTableWidgetFactory.WARNING_CELL);

		cellStyle = new Style();
		cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.getColor(255, 255, 100));
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.SELECT,
				NatTableWidgetFactory.WARNING_CELL);

		cellStyle = new Style();
		cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.COLOR_WHITE);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.EDIT,
				NatTableWidgetFactory.WARNING_CELL);

		cellStyle = new Style();
		cellStyle.setAttributeValue(CellStyleAttributes.HORIZONTAL_ALIGNMENT, HorizontalAlignmentEnum.LEFT);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
				NatTableWidgetFactory.LEFT_ALIGNMENT);

		final FontRegistry fontRegistry = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme()
				.getFontRegistry();
		final Font boldDialogFont = fontRegistry.getBold(JFaceResources.DIALOG_FONT);

		final Style headerStyle = new Style();
		headerStyle.setAttributeValue(CellStyleAttributes.FONT, boldDialogFont);
		headerStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, this.cHeaderBgColor);
		headerStyle.setAttributeValue(CellStyleAttributes.FOREGROUND_COLOR, this.cHeaderFgColor);

		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, headerStyle, DisplayMode.NORMAL,
				GridRegion.COLUMN_HEADER);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, headerStyle, DisplayMode.SELECT,
				GridRegion.COLUMN_HEADER);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, headerStyle, DisplayMode.EDIT,
				GridRegion.COLUMN_HEADER);

		cellStyle = new Style();
		cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.COLOR_WIDGET_LIGHT_SHADOW);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
				NatTableWidgetFactory.DISABLED_CELL);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
				NatTableWidgetFactory.DISABLED_HEADER);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.SELECT,
				NatTableWidgetFactory.DISABLED_HEADER);

		// Padding for the left aligned cells
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER,
				new BackgroundPainter(new PaddingDecorator(new TextPainter(false, true, false, true), 2, 2, 2, 2)));
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER,
				new BackgroundPainter(new PaddingDecorator(new LeftTruncatingTextPainter(), 2, 2, 2, 2)),
				DisplayMode.NORMAL, NatTableWidgetFactory.LEFT_TRUNCATING);

		configRegistry.unregisterConfigAttribute(CellConfigAttributes.CELL_STYLE, DisplayMode.SELECT,
				SelectionStyleLabels.SELECTION_ANCHOR_STYLE);
	}

	private static class LeftTruncatingTextPainter extends TextPainter {
		public LeftTruncatingTextPainter() {
			super(false, true, false, true);
		}

		@Override
		protected String getTextToDisplay(final ILayerCell cell, final GC gc, final int availableLength,
				final String text) {
			if (text == null || text.isEmpty()) {
				return ""; //$NON-NLS-1$
			}

			final String dots = "..."; //$NON-NLS-1$
			if (gc.textExtent(text).x <= availableLength) {
				return text;
			}

			// Add characters leftward until text fills available space
			final StringBuilder result = new StringBuilder();
			for (int i = text.length() - 1; i >= 0; i--) {
				result.insert(0, text.charAt(i));
				final int currentWidth = gc.textExtent(dots + result.toString()).x;
				if (currentWidth > availableLength) {
					return dots + result.substring(1);
				}
			}

			return dots + result.toString();
		}
	}
}