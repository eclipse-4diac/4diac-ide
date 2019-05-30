/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 2012 Profactor GbmH, TU Wien ACIN 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import java.lang.reflect.Constructor;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;

public class ComboDirectEditManager extends DirectEditManager {

	/** The label. */
	private final Label label;

	private CCombo combo;

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
	@SuppressWarnings("rawtypes")
	public ComboDirectEditManager(final GraphicalEditPart source, final Class editorType,
			final CellEditorLocator locator, final Label label) {
		super(source, editorType, locator);
		this.label = label;
		this.editorType = editorType;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected CellEditor createCellEditorOn(final Composite composite) {
		try {
			Constructor constructor = editorType.getConstructor(new Class[] { Composite.class, String[].class });
			return (CellEditor) constructor.newInstance(composite, new String[] {});
		} catch (Exception e) {
			return null;
		}
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.DirectEditManager#bringDown()
	 */
	@Override
	protected void bringDown() {
		if (getEditPart() instanceof ValueEditPart) {
			((ValueEditPart) getEditPart()).refreshValue();
		}
		if (getEditPart() instanceof AbstractViewEditPart) {
			((AbstractViewEditPart) getEditPart()).refreshName();
		}
		Font disposeFont = scaledFont;
		scaledFont = null;
		super.bringDown();
		if (disposeFont != null) {
			disposeFont.dispose();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.DirectEditManager#initCellEditor()
	 */
	@Override
	protected void initCellEditor() {
		combo = (CCombo) getCellEditor().getControl();
		combo.setEditable(false);
		combo.addModifyListener(e -> setDirty(true));

		if (null != comboData) {
			updateComboData(comboData);
			setSelectedItem(selectedItem);
		}

		IFigure figure = getEditPart().getFigure();
		scaledFont = figure.getFont();
		FontData data = scaledFont.getFontData()[0];
		Dimension fontSize = new Dimension(0, data.getHeight());
		label.translateToAbsolute(fontSize);
		data.setHeight(fontSize.height);
		scaledFont = new Font(null, data);
		combo.setFont(scaledFont);
	}

	public void updateComboData(List<String> comboData) {
		this.comboData = comboData;
		if ((null != combo) && (!combo.isDisposed())) {
			combo.removeAll();
			for (String string : comboData) {
				combo.add(string);
			}
		}
	}

	public void setSelectedItem(int newVal) {
		selectedItem = newVal;
		if ((null != combo) && (!combo.isDisposed())) {
			combo.select(newVal);
		}
	}

	public CCombo getComboBox() {
		return combo;
	}
}
