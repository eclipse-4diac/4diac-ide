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

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.BackgroundPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.PaddingDecorator;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.HorizontalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.style.IStyle;
import org.eclipse.nebula.widgets.nattable.style.SelectionStyleLabels;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.style.theme.ModernNatTableThemeConfiguration;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.ITheme;

final class FordiacNatTableConfiguration extends ModernNatTableThemeConfiguration {

	public FordiacNatTableConfiguration() {

		final ColorRegistry colorRegistry = getCurrentTheme().getColorRegistry();
		defaultBgColor = colorRegistry.get("org.eclipse.fordiac.ide.ui.NatTableDefaultBackground"); //$NON-NLS-1$
		// use GEF Classic colors as this seems to be the only real viable way of
		// getting
		defaultFgColor = colorRegistry.get("org.eclipse.gef.color.list.foreground"); //$NON-NLS-1$
		cHeaderBgColor = colorRegistry.get("org.eclipse.gef.color.button"); //$NON-NLS-1$
		cHeaderFgColor = colorRegistry.get("org.eclipse.gef.color.menu.foreground"); //$NON-NLS-1$
		rHeaderBgColor = cHeaderBgColor;
		rHeaderFgColor = cHeaderFgColor;

		cHeaderSelectionBgColor = cHeaderBgColor;
		cHeaderSelectionFgColor = cHeaderFgColor;
		rHeaderSelectionBgColor = cHeaderBgColor;
		rHeaderSelectionFgColor = cHeaderFgColor;
		selectionAnchorSelectionBgColor = cHeaderBgColor;
		selectionAnchorSelectionFgColor = cHeaderFgColor;
		selectionAnchorBgColor = cHeaderBgColor;
		selectionAnchorFgColor = cHeaderFgColor;

	}

	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {
		super.configureRegistry(configRegistry);

		configureErrorCells(configRegistry);

		configureWarningCells(configRegistry);

		Style cellStyle = new Style();
		cellStyle.setAttributeValue(CellStyleAttributes.HORIZONTAL_ALIGNMENT, HorizontalAlignmentEnum.LEFT);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
				NatTableWidgetFactory.LEFT_ALIGNMENT);

		configureDisabledCells(configRegistry);

		// Padding for the left aligned cells
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER,
				new BackgroundPainter(new PaddingDecorator(new TextPainter(false, true, false, true), 2, 2, 2, 2)));
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER,
				new BackgroundPainter(new PaddingDecorator(new LeftTruncatingTextPainter(), 2, 2, 2, 2)),
				DisplayMode.NORMAL, NatTableWidgetFactory.LEFT_TRUNCATING);

		cellStyle = new Style();
		cellStyle.setAttributeValue(CellStyleAttributes.FOREGROUND_COLOR, GUIHelper.COLOR_DARK_GRAY);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
				NatTableWidgetFactory.DEFAULT_CELL);

		configRegistry.unregisterConfigAttribute(CellConfigAttributes.CELL_STYLE, DisplayMode.SELECT,
				SelectionStyleLabels.SELECTION_ANCHOR_STYLE);
	}

	private void configureErrorCells(final IConfigRegistry configRegistry) {
		Style cellStyle = new Style();

		cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.COLOR_RED);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
				NatTableWidgetFactory.ERROR_CELL);

		cellStyle = new Style();
		cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, new Color(255, 100, 100));
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.SELECT,
				NatTableWidgetFactory.ERROR_CELL);

		cellStyle = new Style();
		cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, defaultBgColor);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.EDIT,
				NatTableWidgetFactory.ERROR_CELL);
	}

	private void configureWarningCells(final IConfigRegistry configRegistry) {
		Style cellStyle = new Style();
		cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.COLOR_YELLOW);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
				NatTableWidgetFactory.WARNING_CELL);

		cellStyle = new Style();
		cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.getColor(255, 255, 100));
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.SELECT,
				NatTableWidgetFactory.WARNING_CELL);

		cellStyle = new Style();
		cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, defaultBgColor);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.EDIT,
				NatTableWidgetFactory.WARNING_CELL);
	}

	private void configureDisabledCells(final IConfigRegistry configRegistry) {
		final Style cellStyle = new Style();
		cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, cHeaderBgColor);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
				NatTableWidgetFactory.DISABLED_CELL);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL,
				NatTableWidgetFactory.DISABLED_HEADER);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.SELECT,
				NatTableWidgetFactory.DISABLED_HEADER);
	}

	@Override
	protected IStyle getColumnHeaderStyle() {
		final IStyle style = super.getColumnHeaderStyle();
		style.setAttributeValue(CellStyleAttributes.FONT, getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		return style;
	}

	@Override
	protected IStyle getColumnHeaderSelectionStyle() {
		final IStyle style = super.getColumnHeaderSelectionStyle();
		style.setAttributeValue(CellStyleAttributes.FONT, getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		return style;
	}

	private static FontRegistry getFontRegistry() {
		return getCurrentTheme().getFontRegistry();
	}

	private static ITheme getCurrentTheme() {
		return PlatformUI.getWorkbench().getThemeManager().getCurrentTheme();
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