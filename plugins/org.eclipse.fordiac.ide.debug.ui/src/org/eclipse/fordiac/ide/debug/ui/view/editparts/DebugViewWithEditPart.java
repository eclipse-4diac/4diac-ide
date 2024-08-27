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
package org.eclipse.fordiac.ide.debug.ui.view.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.debug.ui.view.figure.FBDebugViewFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.WithEditPart;

public class DebugViewWithEditPart extends WithEditPart {

	@Override
	protected void activateFigure() {
		final IFigure targetFigure = getTargetFigure();
		if (targetFigure != null) {
			targetFigure.add(getFigure());
		} else {
			super.activateFigure();
		}
	}

	@Override
	protected void deactivateFigure() {
		final IFigure targetFigure = getTargetFigure();
		if (targetFigure != null) {
			targetFigure.remove(getFigure());
			getConnectionFigure().setSourceAnchor(null);
			getConnectionFigure().setTargetAnchor(null);
		} else {
			super.deactivateFigure();
		}

	}

	private IFigure getTargetFigure() {
		final FBDebugViewFigure debugViewFigure = getDebugViewFigure();
		if (debugViewFigure != null) {
			return (isInput()) ? debugViewFigure.getInputWiths() : debugViewFigure.getOutputWiths();
		}
		return null;
	}

	private FBDebugViewFigure getDebugViewFigure() {
		if (getRoot().getChildren().getFirst() instanceof final FBDebugViewRootEditPart debugViewEP) {
			return debugViewEP.getFigure();
		}
		return null;
	}

}
