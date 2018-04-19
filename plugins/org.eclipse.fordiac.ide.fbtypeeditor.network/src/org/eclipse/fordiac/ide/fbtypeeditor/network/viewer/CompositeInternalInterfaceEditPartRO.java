/*******************************************************************************
 * Copyright (c) 2013 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;

/**
 * The Class CompositeInternalInterfaceEditPart.
 */
public class CompositeInternalInterfaceEditPartRO extends InterfaceEditPart {

	public CompositeInternalInterfaceEditPartRO() {
		super();
		setConnectable(false);
	}
	
	@Override
	protected GraphicalNodeEditPolicy getNodeEditPolicy() {
		return null;
	}

	
	@Override
	protected IFigure createFigure() {
		InterfaceFigure figure = new InterfaceFigure();
		figure.setBorder(new ConnectorBorder(getModel()){
			@Override
			public boolean isInput() {
				return !super.isInput();
			}
		});
		return figure;
	}
	
	@Override
	public boolean isInput() {
		return !super.isInput();
	}
	
}
