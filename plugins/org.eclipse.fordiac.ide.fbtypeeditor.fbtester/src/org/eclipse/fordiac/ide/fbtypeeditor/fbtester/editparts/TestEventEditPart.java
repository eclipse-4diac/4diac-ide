/*******************************************************************************
 * Copyright (c) 2012 - 2016 Profactor GmbH, fortiss GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts;

import org.eclipse.draw2d.Button;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.SpecificLayerEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.TestingManager;

/**
 * The Class TestEventEditPart.
 */
public class TestEventEditPart extends TestEditPart implements SpecificLayerEditPart {

	@Override
	public void activate() {
		super.activate();
		registerTriggerElement();
	}

	/**
	 * Register element.
	 */

	protected void registerTriggerElement() {
		TestingManager.getInstance().addTriggerElement(getModel());
	}

	@Override
	protected void createEditPolicies() {
		// currently nothing to be done here
	}

	@Override
	protected void updatePos() {
		if (null != getParentPart()) {
			final Rectangle bounds = getParentPart().getFigure().getBounds();
			int x = 0;
			if (isInput()) {

				final int width = getFigure().getBounds().width;
				x = bounds.x - 10 - width - 15 * getModel().getFb().getInterface().getEventInputs().size();
			} else {
				x = bounds.x + bounds.width + 10 + 15 * getModel().getFb().getInterface().getEventInputs().size();

			}
			final int y = bounds.y;
			if (x != oldx || y != oldy) {
				getModel().updatePosition(x, y);
				oldx = x;
				oldy = y;
			}
		}
	}

	@Override
	protected IFigure createFigureForModel() {
		final Button bt = new Button(getModel().getInterfaceElement().getName());
		bt.addActionListener(e -> getModel().sendEvent());
		return bt;
	}

	@Override
	public void setValue(final String string) {
		// Nothing To do

	}

}
