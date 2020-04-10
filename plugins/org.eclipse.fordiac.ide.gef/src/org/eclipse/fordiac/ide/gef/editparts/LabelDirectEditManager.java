/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013, 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Text;

/**
 * The Class LabelDirectEditManager.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class LabelDirectEditManager extends TextDirectEditManager {

	/** The label. */
	private Label label;

	/** The scaled font. */
	private Font scaledFont;

	/** The initial string. */
	private String initialString = null;
	private VerifyListener aditionalVerify = null;

	/**
	 * The Constructor.
	 *
	 * @param source the source
	 * @param label  the label
	 */
	public LabelDirectEditManager(final GraphicalEditPart source, final Label label) {
		this(source, label, null);
	}

	/**
	 * The Constructor.
	 *
	 * @param source                  the source
	 * @param label                   the label
	 * @param aditionalVerifyListener the aditional verify listener
	 */
	public LabelDirectEditManager(final GraphicalEditPart source, final Label label,
			VerifyListener aditionalVerifyListener) {
		super(source, TextCellEditor.class, new NameCellEditorLocator(label));
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
		Font disposeFont = scaledFont;
		scaledFont = null;
		initialString = null;
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
			initialLabelText = initialString;
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
	}

	public void setInitialString(String val) {
		initialString = val;
		if (null != getCellEditor()) {
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
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}
}
