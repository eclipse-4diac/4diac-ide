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
package org.eclipse.fordiac.ide.gef.annotation;

import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public interface GraphicalAnnotationStyler {

	void applyStyles(IFigure figure, GraphicalAnnotation annotation);

	void removeStyles(IFigure figure, GraphicalAnnotation annotation);

	Color getColor(final GraphicalAnnotation annotation);

	Image getImage(GraphicalAnnotation annotation);

	Image getOverlayImage(GraphicalAnnotation annotation);
}
