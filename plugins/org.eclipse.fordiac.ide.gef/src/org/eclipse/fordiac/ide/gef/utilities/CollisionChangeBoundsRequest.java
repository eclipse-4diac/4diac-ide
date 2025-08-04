/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.utilities;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.requests.ChangeBoundsRequest;

public class CollisionChangeBoundsRequest extends ChangeBoundsRequest {

	private final List<Figure> figureBounds;

	public CollisionChangeBoundsRequest(final Object type, final List<Figure> figureBounds) {
		super(type);
		this.figureBounds = figureBounds;
	}

	public List<Figure> getFigures() {
		return figureBounds;
	}

	public boolean checkCollision(final Rectangle bounds) {
		return figureBounds.stream().map(Figure::getBounds).anyMatch(bounds::intersects);
	}
}
