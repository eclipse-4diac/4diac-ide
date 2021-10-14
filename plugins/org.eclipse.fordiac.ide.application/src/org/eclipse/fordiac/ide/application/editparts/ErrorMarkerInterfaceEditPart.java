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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;

public class ErrorMarkerInterfaceEditPart extends InterfaceEditPart {

	public ErrorMarkerInterfaceEditPart() {
		super();
	}

	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		// we don't want to allow any connection additions here therefore return null
		return null;
	}

	@Override
	protected String getLabelText() {
		final ErrorMarkerInterface model = (ErrorMarkerInterface) getModel();
		return model.getErrorMessage() != null ? model.getErrorMessage() + super.getLabelText() : super.getLabelText();
	}

	@Override
	protected IFigure createFigure() {
		final IFigure figure = new InterfaceFigure();
		figure.setBackgroundColor(ColorConstants.red);
		figure.setOpaque(true);
		return figure;
	}


}
