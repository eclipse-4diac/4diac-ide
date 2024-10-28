/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.annotation;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.deployment.debug.ui.editparts.AdapterWatchValueEditPart;
import org.eclipse.fordiac.ide.deployment.debug.ui.editparts.WatchValueEditPart;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotation;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyler;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public class WatchValueAnnotationStyler implements GraphicalAnnotationStyler {

	@Override
	public void applyStyles(final IFigure figure, final GraphicalAnnotation annotation) {
		// do nothing
	}

	@Override
	public void removeStyles(final IFigure figure, final GraphicalAnnotation annotation) {
		// do nothing
	}

	@Override
	public Color getColor(final GraphicalAnnotation annotation) {
		return null;
	}

	@Override
	public Image getImage(final GraphicalAnnotation annotation) {
		return null;
	}

	@Override
	public Image getOverlayImage(final GraphicalAnnotation annotation) {
		return null;
	}

	@Override
	public EditPart getEditPart(final GraphicalAnnotation annotation) {
		if (annotation instanceof final WatchValueAnnotation watchValueAnnotation) {
			if (watchValueAnnotation.getElement() instanceof AdapterDeclaration) {
				return new AdapterWatchValueEditPart();
			}
			return new WatchValueEditPart();
		}
		return null;
	}
}
