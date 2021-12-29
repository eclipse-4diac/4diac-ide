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
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class ErrorMarkerFBNeworkElementFigure extends FBNetworkElementFigure {

	public ErrorMarkerFBNeworkElementFigure(final FBNetworkElement model, final AbstractFBNElementEditPart editPart) {
		super(model, editPart);
	}

	public void setErrorMessage(final String text) {
		changeTypeLabelText(text);
	}

	@Override
	protected void setupTypeNameAndVersion(final FBType type, final Figure container) {
		super.setupTypeNameAndVersion(type, container);
		getMiddle().setOpaque(false);
		getMiddle().setBackgroundColor(ColorConstants.red);
	}

}
