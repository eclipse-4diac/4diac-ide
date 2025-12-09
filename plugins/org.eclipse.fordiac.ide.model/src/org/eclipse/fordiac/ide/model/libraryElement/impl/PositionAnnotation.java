/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;

public class PositionAnnotation {

	static Point toScreenPoint(final Position pos) {
		final int x = CoordinateConverter.INSTANCE.iec61499ToScreen(pos.getX());
		final int y = CoordinateConverter.INSTANCE.iec61499ToScreen(pos.getY());
		return new org.eclipse.draw2d.geometry.Point(x, y);
	}

	static void updatePositionFromScreenCoordinates(final PositionableElement posElem, final int x, final int y) {
		final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
		pos.setX(CoordinateConverter.INSTANCE.screenToIEC61499(x));
		pos.setY(CoordinateConverter.INSTANCE.screenToIEC61499(y));
		posElem.setPosition(pos);
	}

	private PositionAnnotation() {
		throw new UnsupportedOperationException();
	}
}
