/*******************************************************************************
 * Copyright (c) 2012 - 2016 Profactor GmbH, fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts;

import org.eclipse.draw2d.ActionEvent;
import org.eclipse.draw2d.ActionListener;
import org.eclipse.draw2d.Button;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.SpecificLayerEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.TestingManager;


/**
 * The Class TestEventEditPart.
 */
public class TestEventEditPart extends TestEditPart implements
		SpecificLayerEditPart {

	@Override
	public void activate() {
		super.activate();
		registerTriggerElement();
	}

	/**
	 * Register element.
	 */

	protected void registerTriggerElement() {
		// if (isVariable()) {
		TestingManager.getInstance().addTriggerElement(getModel());
		// }
	}
	
	@Override
	protected void createEditPolicies() {
	}

	@Override
	protected void updatePos() {
		if(null != parentPart){
			Rectangle bounds = parentPart.getFigure().getBounds();
			int x = 0;
			if (isInput()) {
	
				int width = getFigure().getBounds().width;
				x = bounds.x - 10 - width - 15
						* getModel().getFb().getInterface().getEventInputs().size();
			} else {
				x = bounds.x + bounds.width + 10 + 15
						* getModel().getFb().getInterface().getEventInputs().size();
	
			}
			int y = bounds.y;
			if (x != oldx || y != oldy) {
				getModel().setX(x);
				getModel().setY(y);
				oldx = x;
				oldy = y;
			}
		}
	}

	@Override
	protected IFigure createFigureForModel() {
		Button bt = new Button(getModel().getInterfaceElement().getName());
		bt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				getModel().sendEvent();
				// TestingManager.getInstance().sendEvent(getCastedModel());
			}
		});
		return bt;
	}

	@Override
	public void setValue(String string) {
		// Nothing To do

	}

}
