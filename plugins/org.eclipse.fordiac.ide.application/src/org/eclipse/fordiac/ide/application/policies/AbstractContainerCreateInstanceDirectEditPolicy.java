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
 *   Alois Zoitl - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.requests.LocationRequest;

public abstract class AbstractContainerCreateInstanceDirectEditPolicy extends AbstractCreateInstanceDirectEditPolicy {

	@Override
	public Point getInsertPos(final LocationRequest request) {
		final Point insertPos = super.getInsertPos(request);
		final Point topLeft = getHost().getFigure().getClientArea().getTopLeft();
		insertPos.translate(-topLeft.x, -topLeft.y);
		return insertPos;
	}
}
