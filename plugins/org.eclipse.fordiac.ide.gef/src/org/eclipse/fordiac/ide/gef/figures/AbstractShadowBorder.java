/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.AbstractBackground;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;

public abstract class AbstractShadowBorder extends AbstractBackground {

	protected static final int SHADOW_ALPHA = 30;
	protected static final int SHADOW_SIZE = 4;
	protected static final int SHADOW_CORNER_RADIUS = GefPreferenceConstants.CORNER_DIM + 2;
	public static final Insets SHADOW_INSETS = new Insets(2, 2, SHADOW_SIZE + 1, SHADOW_SIZE * 2 / 3 + 1);

	@Override
	public Insets getInsets(final IFigure figure) {
		return SHADOW_INSETS;
	}

	@Override
	public boolean isOpaque() {
		return true;
	}

}