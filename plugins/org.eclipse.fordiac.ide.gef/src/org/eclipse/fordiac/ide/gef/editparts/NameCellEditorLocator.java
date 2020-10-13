/*******************************************************************************
 * Copyright (c) 2008, 2009, 2013 Profactor GbmH, fortiss GmbH
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

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

/**
 * The Class NameCellEditorLocator.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class NameCellEditorLocator implements CellEditorLocator {

	private Label label;

	/**
	 * Instantiates a new name cell editor locator.
	 *
	 * @param label the label
	 */
	public NameCellEditorLocator(final Label label) {
		setLabel(label);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.gef.tools.CellEditorLocator#relocate(org.eclipse.jface.viewers.
	 * CellEditor)
	 */
	@Override
	public void relocate(final CellEditor celleditor) {
		if (null != celleditor) {
			Text text = (Text) celleditor.getControl();
			Dimension singleChar = FigureUtilities.getStringExtents("M", text.getFont()); //$NON-NLS-1$
			Point pref = text.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			Rectangle rect = label.getBounds().getCopy();
			label.translateToAbsolute(rect);
			text.setBounds(rect.x - 1, rect.y - 1, pref.x + singleChar.width, pref.y + 1);
		}
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
	 * @param label The label to set
	 */
	protected void setLabel(final Label label) {
		this.label = label;
	}

}
