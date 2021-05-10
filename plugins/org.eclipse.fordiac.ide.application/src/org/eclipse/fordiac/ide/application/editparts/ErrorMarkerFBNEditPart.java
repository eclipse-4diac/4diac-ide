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
import org.eclipse.fordiac.ide.application.figures.ErrorMarkerFBNeworkElementFigure;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class ErrorMarkerFBNEditPart extends AbstractFBNElementEditPart {

	private ErrorMarkerFBNeworkElementFigure errorMarkerFBNeworkElementFigure;

	@Override
	public ErrorMarkerFBNElement getModel() {
		return (ErrorMarkerFBNElement) super.getModel();
	}

	@Override
	protected IFigure createFigureForModel() {
		errorMarkerFBNeworkElementFigure = new ErrorMarkerFBNeworkElementFigure(getModel(), this);
		errorMarkerFBNeworkElementFigure.setOpaque(false);
		errorMarkerFBNeworkElementFigure.setBackgroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED));
		buildErrorText();

		return errorMarkerFBNeworkElementFigure;
	}

	private void buildErrorText() {
		final StringBuilder errorText = new StringBuilder();

		if (getModel().getPaletteEntry() != null) {
			final FBType type = (FBType) getModel().getPaletteEntry().getType();
			if (type != null) {
				errorText.append("old type: " + type.getName()); //$NON-NLS-1$
				errorText.append(System.lineSeparator());
			}
		}

		if (getModel().getErrorMessage() != null) {
			errorText.append(getModel().getErrorMessage());
		}

		if (errorText.length() == 0) {
			errorText.append("Error Marker"); //$NON-NLS-1$
		}

		setText(errorText.toString());
	}


	public void setText(final String text) {
		if (errorMarkerFBNeworkElementFigure != null) {
			errorMarkerFBNeworkElementFigure.setErrorMessage(text);
		}
	}

}
