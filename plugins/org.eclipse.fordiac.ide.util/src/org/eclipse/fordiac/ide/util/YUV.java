/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH, TU Vienna/ACIN,
 *                    Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians
 *     - reduces visibility of fields
 *   Alois Zoitl
 *     - turned into record class removed RGB dependency
 *******************************************************************************/
package org.eclipse.fordiac.ide.util;

/**
 * simple class for representing colors in YUV color space
 *
 */
public record YUV(double y, double u, double v) {

	public YUV(final int r, final int g, final int b) {
		this(0.299 * r + 0.587 * g + 0.114 * b, -0.14713 * r - 0.28886 * g + 0.436 * b,
				0.615 * r - 0.51499 * g - 0.10001 * b);
	}

	public boolean nearbyColor(final YUV yuv) {
		final double diffY = y - yuv.y;
		final double diffU = u - yuv.u;
		final double diffV = v - yuv.v;

		final double squaredDistance = (diffY * diffY + diffU * diffU + diffV * diffV);
		return (squaredDistance < 600);
	}

}
