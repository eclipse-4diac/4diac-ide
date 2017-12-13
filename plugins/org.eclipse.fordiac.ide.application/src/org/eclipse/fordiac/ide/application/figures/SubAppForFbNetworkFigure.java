/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

/**
 * The Class SubAppForFbNetworkFigure.
 */
public class SubAppForFbNetworkFigure extends AbstractFBNetworkElementFigure {
	
	public SubAppForFbNetworkFigure(SubApp model, SubAppForFBNetworkEditPart editPart) {
		super(model, editPart);
	}

	@Override
	protected boolean isResoruceTypeFBNElement() {
		// Nothing todo here
		return false;
	}
	
	@Override
	protected void setupTypeNameAndVersion(FBNetworkElement model, Figure container) {
		super.setupTypeNameAndVersion(model, container);
		if(null == model.getType()) {
			//we have an untyped sub app don't show any text in the label
			typeLabel.setText("");  //$NON-NLS-1$
		}		
	}
}
