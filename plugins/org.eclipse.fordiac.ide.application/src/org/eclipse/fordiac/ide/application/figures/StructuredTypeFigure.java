/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.fordiac.ide.gef.listeners.IFontUpdateListener;

public final class StructuredTypeFigure extends Label implements IFontUpdateListener {

	public StructuredTypeFigure() {
		setLabelAlignment(PositionConstants.CENTER);
		setTextAlignment(PositionConstants.CENTER);
	}

	@Override
	public void updateFonts() {
		invalidateTree();
		revalidate();
	}
}
