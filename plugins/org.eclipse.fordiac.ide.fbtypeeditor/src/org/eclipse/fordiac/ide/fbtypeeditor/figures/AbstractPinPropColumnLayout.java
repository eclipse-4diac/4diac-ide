/*******************************************************************************
 * Copyright (c) 2026 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.fbtypeeditor.figures;

import org.eclipse.draw2d.AbstractConstraintLayout;
import org.eclipse.draw2d.IFigure;

abstract class AbstractPinPropColumnLayout extends AbstractConstraintLayout {

	private final boolean inputSide;

	public AbstractPinPropColumnLayout(final boolean inputSide) {
		this.inputSide = inputSide;
	}

	public boolean isInputSide() {
		return inputSide;
	}

	protected int getChildYPos(final IFigure child) {
		return (getConstraint(child) instanceof final Integer i) ? i.intValue() : 0;
	}

}
