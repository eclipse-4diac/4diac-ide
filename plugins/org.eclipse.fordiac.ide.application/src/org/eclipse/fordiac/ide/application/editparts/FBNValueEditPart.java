/*******************************************************************************
 * Copyright (c) 2022 Primetals Austria GmbH
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
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.gef.GraphicalEditPart;

public class FBNValueEditPart extends ValueEditPart {

	@Override
	protected Point calculatePos() {
		final Point pos = super.calculatePos();
		if (getParent() instanceof AbstractContainerContentEditPart) {
			final Point topLeft = ((GraphicalEditPart) getParent()).getFigure().getClientArea().getTopLeft();
			pos.performTranslate(-topLeft.x, -topLeft.y);
		}
		return pos;
	}

}
