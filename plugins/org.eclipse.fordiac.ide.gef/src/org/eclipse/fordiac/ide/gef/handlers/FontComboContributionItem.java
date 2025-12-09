/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University
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
package org.eclipse.fordiac.ide.gef.handlers;

import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

public class FontComboContributionItem extends WorkbenchWindowControlContribution {

	private Combo combo;
	private FontRegistry fontRegistry;
	private final String[] options = new String[] { "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24",
			"26", "28", "36", "48", "72" };
	private final IPropertyChangeListener propertyChangeListener = event -> {
		if (event.getProperty().equals(JFaceResources.TEXT_FONT) && event.getNewValue() instanceof FontData[]) {
			updateComboText();
		}
	};

	public FontComboContributionItem() {
		super(GEFActionConstants.ZOOM_TOOLBAR_WIDGET);
	}

	public FontComboContributionItem(final String id) {
		super(id);
	}

	@Override
	protected Control createControl(final Composite parent) {
		fontRegistry = getWorkbenchWindow().getWorkbench().getThemeManager().getCurrentTheme().getFontRegistry();
		fontRegistry.addListener(propertyChangeListener);

		combo = new Combo(parent, SWT.DROP_DOWN);
		combo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleComboSelected();
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				handleComboSelected();
			}
		});
		combo.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(final FocusEvent e) {
				// do nothing
			}

			@Override
			public void focusLost(final FocusEvent e) {
				refresh();
			}
		});

		combo.setItems(options);
		updateComboText();
		return combo;
	}

	@Override
	public void dispose() {
		super.dispose();
		fontRegistry.removeListener(propertyChangeListener);
		combo = null;
	}

	private void refresh() {
		if (combo == null || combo.isDisposed()) {
			return;
		}
		updateComboText();
		combo.pack();
		combo.getParent().pack();
	}

	private void handleComboSelected() {
		final int selected = combo.getSelectionIndex();
		if (selected >= 0) {
			updateFontHeight(Integer.parseInt(combo.getItem(selected)));
		} else if (validParseInt(combo.getText())) {
			updateFontHeight(Integer.parseInt(combo.getText()));
		}
		updateComboText();
	}

	private static boolean validParseInt(final String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (final NumberFormatException e) {
			return false;
		}
	}

	private void updateComboText() {
		combo.setText(Integer.toString(fontRegistry.getFontData(JFaceResources.TEXT_FONT)[0].getHeight()));
	}

	private void updateFontHeight(final int newHeight) {
		if (newHeight < 1 || newHeight > 256) {
			return;
		}
		final FontData[] currentFontData = fontRegistry.getFontData(JFaceResources.TEXT_FONT);
		final FontData[] newFontData = FontDescriptor.createFrom(currentFontData).setHeight(newHeight).getFontData();
		if (newFontData != null) {
			fontRegistry.put(JFaceResources.TEXT_FONT, newFontData);
		}
	}
}
