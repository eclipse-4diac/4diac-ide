/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo 
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.RGB;

public class ChangeBackgroundcolorCommand extends Command {
	private static final String CHANGE = Messages.ChangeBackgroundcolorCommand_LABEL_ChangeColor;
	private org.eclipse.fordiac.ide.model.libraryElement.Color fordiacColor;
	private org.eclipse.fordiac.ide.model.libraryElement.Color oldColor;
	private final RGB rgb;
	private final ColorizableElement colorizeableElement;

	@Override
	public boolean canExecute() {
		return colorizeableElement != null;
	}

	public ChangeBackgroundcolorCommand(final ColorizableElement colorizeableElement, final RGB rgb) {
		super(CHANGE);
		this.rgb = rgb;
		this.colorizeableElement = colorizeableElement;
	}

	@Override
	public void execute() {
		fordiacColor = LibraryElementFactory.eINSTANCE.createColor();
		fordiacColor.setBlue(rgb.blue);
		fordiacColor.setRed(rgb.red);
		fordiacColor.setGreen(rgb.green);
		oldColor = colorizeableElement.getColor();
		colorizeableElement.setColor(fordiacColor);
	}

	@Override
	public void undo() {
		colorizeableElement.setColor(oldColor);
	}

	@Override
	public void redo() {
		colorizeableElement.setColor(fordiacColor);
	}
}