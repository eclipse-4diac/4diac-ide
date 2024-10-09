/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 2012 Profactor GbmH, TU Wien ACIN
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - changed activation behavior to immediately show the combo list
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import java.lang.reflect.Constructor;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;

public class ComboDirectEditManager extends DirectEditManager {

	/** The label. */
	private final Label label;

	/** The scaled font. */
	private Font scaledFont;

	private List<String> comboData;

	private int selectedItem = -1;

	@SuppressWarnings("rawtypes")
	private final Class editorType;

	/**
	 * The Constructor.
	 *
	 * @param source     the source
	 * @param editorType the editor type
	 * @param locator    the locator
	 * @param label      the label
	 */
	public ComboDirectEditManager(final GraphicalEditPart source, final Class<? extends CellEditor> editorType,
			final CellEditorLocator locator, final Label label) {
		super(source, editorType, locator);
		this.label = label;
		this.editorType = editorType;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected CellEditor createCellEditorOn(final Composite composite) {
		try {
			final Constructor constructor = editorType.getConstructor(Composite.class, String[].class);
			return (CellEditor) constructor.newInstance(composite, new String[] {});
		} catch (final Exception e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public void show() {
		super.show();
		getComboBox().setListVisible(true);
	}

	/**
	 * Show.
	 *
	 * @param initialChar the initial char
	 */
	public void show(final char initialChar) {
		this.show();
		setDirty(true);
		getLocator().relocate(getCellEditor());
		getComboBox().setListVisible(true);
	}

	@Override
	protected void bringDown() {
		if (getEditPart() instanceof ValueEditPart) {
			((ValueEditPart) getEditPart()).refreshValue();
		}
		if (getEditPart() instanceof AbstractViewEditPart) {
			((AbstractViewEditPart) getEditPart()).refreshName();
		}
		final Font disposeFont = scaledFont;
		scaledFont = null;
		super.bringDown();
		if (disposeFont != null) {
			disposeFont.dispose();
		}
	}

	@Override
	protected void initCellEditor() {
		final CCombo combo = getComboBox();
		combo.setEditable(false);
		combo.addModifyListener(e -> setDirty(true));
		ComboBoxWidgetFactory.configureTypeaheadHandling(combo);

		if (null != comboData) {
			updateComboData(comboData);
			setSelectedItem(selectedItem);
		}

		final IFigure figure = getEditPart().getFigure();
		scaledFont = figure.getFont();
		final FontData data = scaledFont.getFontData()[0];
		final Dimension fontSize = new Dimension(0, data.getHeight());
		label.translateToAbsolute(fontSize);
		data.setHeight(fontSize.height);
		scaledFont = new Font(null, data);
		combo.setFont(scaledFont);
	}

	public void updateComboData(final List<String> comboData) {
		this.comboData = comboData;
		final CCombo combo = getComboBox();
		if ((null != combo) && (!combo.isDisposed())) {
			combo.removeAll();
			for (final String string : comboData) {
				combo.add(string);
			}
		}
	}

	public void setSelectedItem(final int newVal) {
		selectedItem = newVal;
		final CCombo combo = getComboBox();
		if ((null != combo) && (!combo.isDisposed())) {
			combo.select(newVal);
		}
	}

	public CCombo getComboBox() {
		if (null != getCellEditor() && getCellEditor().getControl() instanceof CCombo) {
			return (CCombo) getCellEditor().getControl();
		}
		return null;
	}
}
