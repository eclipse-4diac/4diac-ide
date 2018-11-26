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

	/** The verify listener. */
	// protected VerifyListener verifyListener = new VerifyListener() {
	// public void verifyText(VerifyEvent event) {
	// Text text = (Text) getCellEditor().getControl();
	// String oldText = text.getText();
	// String leftText = oldText.substring(0, event.start);
	// String rightText = oldText.substring(event.end, oldText.length());
	// GC gc = new GC(text);
	// Point size = gc.textExtent(leftText + event.text + rightText);
	// gc.dispose();
	// if (size.x != 0) {
	// size = text.computeSize(size.x, SWT.DEFAULT);
	// }
	// getCellEditor().getControl().setSize(size.x, size.y);
	// }
	// };

	@SuppressWarnings("rawtypes")
	private final Class editorType;

	/**
	 * The Constructor.
	 * 
	 * @param source the source
	 * @param editorType the editor type
	 * @param locator the locator
	 * @param label the label
	 */
	@SuppressWarnings("rawtypes")
	public ComboDirectEditManager(final GraphicalEditPart source,
			final Class editorType, final CellEditorLocator locator, final Label label) {
		super(source, editorType, locator);
		this.label = label;
		this.editorType = editorType;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected CellEditor createCellEditorOn(final Composite composite) {
		try {
			Constructor constructor = editorType.getConstructor(new Class[] {
					Composite.class, String[].class });
			return (CellEditor) constructor.newInstance(new Object[] { composite,
					new String[] {} });
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
		//initialString = null;
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
		
		combo.addModifyListener(new ModifyListener() {			
			@Override
			public void modifyText(ModifyEvent e) {
				setDirty(true);
			}
		});
		// combo.addVerifyListener(verifyListener);
		// String initialLabelText = ""; //$NON-NLS-1$
		// if (initialString == null) {
		// initialLabelText = label.getText();
		// getCellEditor().setValue(initialLabelText);
		// } else {
		// initialLabelText = initialString.toString();
		// getCellEditor().setValue(initialLabelText);
		// }

		if(null != comboData){
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

		// combo.selectAll();
	}
	
	public void updateComboData(List<String> comboData){
		this.comboData = comboData;
		if((null != combo) && (!combo.isDisposed())){
			combo.removeAll();
			for (String string : comboData) {
				combo.add(string);
			}
		}
	}
	
	public void setSelectedItem(int newVal){
		selectedItem = newVal;
		if((null != combo) && (!combo.isDisposed())){
			combo.select(newVal);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.DirectEditManager#unhookListeners()
	 */
	@Override
	protected void unhookListeners() {
		try {
			super.unhookListeners();
			// Text text = (Text) getCellEditor().getControl();
			// text.removeVerifyListener(verifyListener);
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}
	
	public CCombo getComboBox(){
		return combo;
	}
}
