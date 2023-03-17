/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Triangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public class HiddenPinIndicatorEditPart extends AbstractGraphicalEditPart {

	@Override
	protected IFigure createFigure() {
		final Triangle triangle = new Triangle();
		triangle.setBorder(null);
		triangle.setBounds(new Rectangle(0, 0, 15, 8));
		triangle.setBackgroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ANY_INT_CONNECTOR_COLOR));
		return triangle;
	}

	@Override
	protected void createEditPolicies() {
		// nothing to do here
	}

}
