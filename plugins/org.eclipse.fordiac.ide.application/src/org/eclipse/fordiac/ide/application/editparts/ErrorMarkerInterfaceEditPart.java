/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class ErrorMarkerInterfaceEditPart extends InterfaceEditPart {

	public ErrorMarkerInterfaceEditPart() {
		super();
		// changeBackgroundColor();
	}

	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IFigure createFigure() {
		final IFigure figure = new InterfaceFigure();
		final Display display = Display.getCurrent();
		final Color red = display.getSystemColor(SWT.COLOR_RED);
		figure.setBackgroundColor(red);
		figure.setOpaque(true);
		return figure;
	}


}
