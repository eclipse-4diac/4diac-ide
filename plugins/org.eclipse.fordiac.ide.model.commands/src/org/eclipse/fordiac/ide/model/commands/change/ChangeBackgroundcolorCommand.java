/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IEditorPart;

public class ChangeBackgroundcolorCommand extends Command {
	private static final String CHANGE = Messages.ChangeBackgroundcolorCommand_LABEL_ChangeColor;
	private org.eclipse.fordiac.ide.model.libraryElement.Color fordiacColor;
	private org.eclipse.fordiac.ide.model.libraryElement.Color oldColor;
	private final RGB rgb;
	private final ColorizableElement colorizeableElement;
	private IEditorPart editor;

	@Override
	public boolean canUndo() {
		if(null != editor) {
			return editor.equals(Abstract4DIACUIPlugin.getCurrentActiveEditor());
		}else {
			return false;
		}
	}

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
		editor = Abstract4DIACUIPlugin.getCurrentActiveEditor();
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