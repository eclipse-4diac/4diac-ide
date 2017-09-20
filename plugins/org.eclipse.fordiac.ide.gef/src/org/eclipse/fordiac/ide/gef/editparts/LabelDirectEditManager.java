/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013, 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH 
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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.CellEditorActionHandler;

/**
 * The Class LabelDirectEditManager.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class LabelDirectEditManager extends DirectEditManager {
	
	private IActionBars actionBars;
	private CellEditorActionHandler actionHandler;
	private IAction copy, cut, paste, undo, redo, find, selectAll, delete;

	/** The label. */
	protected Label label;

	/** The scaled font. */
	protected Font scaledFont;

	/** The initial string. */
	protected String initialString = null;
	private VerifyListener aditionalVerify = null;

	/**
	 * The Constructor.
	 * 
	 * @param source the source
	 * @param editorType the editor type
	 * @param locator the locator
	 * @param label the label
	 */
	@SuppressWarnings("rawtypes")
	public LabelDirectEditManager(final GraphicalEditPart source,
			final Class editorType, final CellEditorLocator locator, final Label label) {
		super(source, editorType, locator);
		this.label = label;
	}

	/**
	 * The Constructor.
	 * 
	 * @param source the source
	 * @param editorType the editor type
	 * @param locator the locator
	 * @param label the label
	 * @param aditionalVerifyListener the aditional verify listener
	 */
	@SuppressWarnings("rawtypes")
	public LabelDirectEditManager(final GraphicalEditPart source,
			final Class editorType, final CellEditorLocator locator,
			final Label label, VerifyListener aditionalVerifyListener) {
		super(source, editorType, locator);
		this.label = label;
		this.aditionalVerify = aditionalVerifyListener;
	}

	/**
	 * Show.
	 * 
	 * @param initialChar the initial char
	 */
	public void show(final char initialChar) {
		initialString = new String(new char[] { initialChar });
		this.show();
		// Get the Text control
		Text textControl = (Text) getCellEditor().getControl();
		// Set the controls text and position the caret at the end of the text
		textControl.setSelection(1);
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
		if(getEditPart() instanceof AbstractDirectEditableEditPart) {
			((AbstractDirectEditableEditPart)getEditPart()).refreshName();
		}
		Font disposeFont = scaledFont;
		scaledFont = null;
		initialString = null;

		if (actionHandler != null) {
			actionHandler.dispose();
			actionHandler = null;
		}
		if (actionBars != null) {
			restoreSavedActions(actionBars);
			actionBars.updateActionBars();
			actionBars = null;
		}
		
		getLocator().relocate(getCellEditor());
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
		Text text = (Text) getCellEditor().getControl();

		if (aditionalVerify != null) {
			text.addVerifyListener(aditionalVerify);
		}

		String initialLabelText = ""; //$NON-NLS-1$
		if (initialString == null) {
			initialLabelText = label.getText();
			getCellEditor().setValue(initialLabelText);
		} else {
			initialLabelText = initialString.toString();
			getCellEditor().setValue(initialLabelText);
		}
		IFigure figure = getEditPart().getFigure();
		scaledFont = figure.getFont();
		FontData data = scaledFont.getFontData()[0];
		Dimension fontSize = new Dimension(0, data.getHeight());
		label.translateToAbsolute(fontSize);
		data.setHeight(fontSize.height);
		scaledFont = new Font(null, data);
		text.setFont(scaledFont);
		text.selectAll();
		
		// Hook the cell editor's copy/paste actions to the actionBars so that
		// they can
		// be invoked via keyboard shortcuts.
		actionBars = Abstract4DIACUIPlugin.getCurrentActiveEditor().getEditorSite()
				.getActionBars();
		saveCurrentActions(actionBars);
		actionHandler = new CellEditorActionHandler(actionBars);
		actionHandler.addCellEditor(getCellEditor());
		actionBars.updateActionBars();
	}
	
	private void restoreSavedActions(IActionBars actionBars) {
		actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), copy);
		actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), paste);
		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), delete);
		actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),
				selectAll);
		actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), cut);
		actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(), find);
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), undo);
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), redo);
	}

	private void saveCurrentActions(IActionBars actionBars) {
		copy = actionBars.getGlobalActionHandler(ActionFactory.COPY.getId());
		paste = actionBars.getGlobalActionHandler(ActionFactory.PASTE.getId());
		delete = actionBars
				.getGlobalActionHandler(ActionFactory.DELETE.getId());
		selectAll = actionBars.getGlobalActionHandler(ActionFactory.SELECT_ALL
				.getId());
		cut = actionBars.getGlobalActionHandler(ActionFactory.CUT.getId());
		find = actionBars.getGlobalActionHandler(ActionFactory.FIND.getId());
		undo = actionBars.getGlobalActionHandler(ActionFactory.UNDO.getId());
		redo = actionBars.getGlobalActionHandler(ActionFactory.REDO.getId());
	}
	
	public void setInitialString(String val){
		initialString = val;
		if(null != getCellEditor()){
			getCellEditor().setValue(initialString);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.DirectEditManager#unhookListeners()
	 */
	@Override
	protected void unhookListeners() {
		super.unhookListeners();
		try {
			Text text = (Text) getCellEditor().getControl();
			if (aditionalVerify != null) {
				text.removeVerifyListener(aditionalVerify);
			}
		} catch (Exception e) {
			// TODO check why this method sometimes crash on exit
			
		}
	}
}
