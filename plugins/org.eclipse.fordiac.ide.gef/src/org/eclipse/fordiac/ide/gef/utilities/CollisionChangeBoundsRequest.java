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
import java.util.Objects;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.requests.ChangeBoundsRequest;

public class CollisionChangeBoundsRequest extends ChangeBoundsRequest {

	private final List<Figure> figureBounds;
	private final List<Figure> parentFigure;

	public CollisionChangeBoundsRequest(final Object type, final List<Figure> figureBounds,
			final List<Figure> parentFigure) {
		super(type);
		this.figureBounds = figureBounds;
		this.parentFigure = parentFigure;
	}

	public List<Figure> getFigures() {
		return figureBounds;
	}

	public boolean checkCollision(final Rectangle bounds) {
		if (parentFigure.stream().filter(Objects::nonNull).map(Figure::getBounds)
				.anyMatch(parentBounds -> !parentBounds.contains(bounds))) {
			return true;
		}
		return figureBounds.stream().map(Figure::getBounds).anyMatch(bounds::intersects);
	}
}
