/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal - initial implementation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.application.figures.StructuredTypeFigure;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles.AnnotationCompoundBorder;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public class ErrorMarkerDataTypeEditPart extends AbstractGraphicalEditPart {
	public ErrorMarkerDataTypeEditPart() {
	}

	@Override
	public ErrorMarkerDataType getModel() {
		return (ErrorMarkerDataType) super.getModel();
	}

	void refreshValue() {
		getFigure().setText(getModel().getName());
	}

	@Override
	public void refresh() {
		super.refresh();
		refreshValue();
	}

	@Override
	public StructuredTypeFigure getFigure() {
		final StructuredTypeFigure fig = (StructuredTypeFigure) super.getFigure();
		fig.setBorder(new AnnotationCompoundBorder(figure.getBorder(), GraphicalAnnotationStyles.ERROR_RED));
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		removeEditPolicy(EditPolicy.DIRECT_EDIT_ROLE);

	}

	@Override
	protected IFigure createFigure() {
		final StructuredTypeFigure fig = new StructuredTypeFigure();
		fig.setIcon(FordiacImage.ICON_DATA_TYPE.getImage());
		return fig;
	}
}
