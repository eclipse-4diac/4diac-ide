/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
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
 *   Alois Zoitl - removed editor check from canUndo
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.RGB;

public class ChangeBackgroundcolorCommand extends Command implements ScopedCommand {
	private static final String CHANGE = Messages.ChangeBackgroundcolorCommand_LABEL_ChangeColor;
	private org.eclipse.fordiac.ide.model.libraryElement.Color fordiacColor;
	private org.eclipse.fordiac.ide.model.libraryElement.Color oldColor;
	private final RGB rgb;
	private final ColorizableElement colorizeableElement;

	public ChangeBackgroundcolorCommand(final ColorizableElement colorizeableElement, final RGB rgb) {
		super(CHANGE);
		this.rgb = rgb;
		this.colorizeableElement = Objects.requireNonNull(colorizeableElement);
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

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(colorizeableElement);
	}

}