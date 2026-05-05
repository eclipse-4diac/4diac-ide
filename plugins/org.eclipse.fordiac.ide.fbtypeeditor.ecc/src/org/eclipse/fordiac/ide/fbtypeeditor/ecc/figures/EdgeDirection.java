/*******************************************************************************
 * Copyright (c) 2026 Vikash Kumar Sinha
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vikash Kumar Sinha - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Vector;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;

public enum EdgeDirection {
	TOP, BOTTOM, LEFT, RIGHT;

	public static EdgeDirection of(final Point reference, final Point center) {
		return of(reference, center, null);
	}

	public static EdgeDirection of(final Point reference, final Point center, final ECState state) {
		final int dx = reference.x - center.x;
		final int dy = reference.y - center.y;

		final boolean hasActions = state != null && !state.getECAction().isEmpty();

		if (Math.abs(dy) >= Math.abs(dx)) {
			return (dy >= 0) ? BOTTOM : TOP;
		}
		if (dx < 0) {
			return LEFT;
		}

		if (hasActions) {
			return (dy >= 0) ? BOTTOM : TOP;
		}
		return RIGHT;
	}

	public Vector toNormal() {
		return switch (this) {
		case LEFT -> new Vector(-1, 0);
		case RIGHT -> new Vector(1, 0);
		case TOP -> new Vector(0, -1);
		case BOTTOM -> new Vector(0, 1);
		};
	}
}
