/*******************************************************************************
 * Copyright (c) 2008, 2009  Profactor GbmH 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Point;

/**
 * The Class ComboCellEditorLocator.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ComboCellEditorLocator implements CellEditorLocator {

	private Label label;

	/**
	 * Instantiates a new combo cell editor locator.
	 * 
	 * @param label the label
	 */
	public ComboCellEditorLocator(final Label label) {
		setLabel(label);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.CellEditorLocator#relocate(org.eclipse.jface.viewers.CellEditor)
	 */
	@Override
	public void relocate(final CellEditor celleditor) {
		CCombo combo = (CCombo) celleditor.getControl();
		Point pref = combo.computeSize(-1, -1);
		Rectangle rect = label.getTextBounds().getCopy();
		label.translateToAbsolute(rect);
		combo.setBounds(rect.x - 1, rect.y - 1, pref.x + 1, pref.y + 1);
	}

	/**
	 * @return the label
	 */
	protected Label getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 * 
	 * @param label
	 *            The label to set
	 */
	protected void setLabel(final Label label) {
		this.label = label;
	}

}
