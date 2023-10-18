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

import java.text.MessageFormat;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.figures.ErrorMarkerFBNeworkElementFigure;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

public class ErrorMarkerFBNEditPart extends AbstractFBNElementEditPart {

	private ErrorMarkerFBNeworkElementFigure errorMarkerFBNeworkElementFigure;

	@Override
	public ErrorMarkerFBNElement getModel() {
		return (ErrorMarkerFBNElement) super.getModel();
	}

	@Override
	protected IFigure createFigureForModel() {
		errorMarkerFBNeworkElementFigure = new ErrorMarkerFBNeworkElementFigure(getModel(), this);
		updateErrorText();
		return errorMarkerFBNeworkElementFigure;
	}

	private void updateErrorText() {
		final StringBuilder errorText = new StringBuilder();

		if (getModel().getTypeEntry() != null) {
			final FBType type = (FBType) getModel().getTypeEntry().getType();
			if (type != null) {
				errorText.append(MessageFormat.format(Messages.ErrorMarkerFBNEditPart_OldType, type.getName()));
			}
		}

		if (errorText.length() == 0) {
			errorText.append(Messages.ErrorMarkerFBNEditPart_ErrorMarker);
		}

		setText(errorText.toString());
	}

	public void setText(final String text) {
		if (errorMarkerFBNeworkElementFigure != null) {
			errorMarkerFBNeworkElementFigure.setErrorMessage(text);
		}
	}

	@Override
	protected void refreshToolTip() {
		updateErrorText();
		super.refreshToolTip();
	}

}
