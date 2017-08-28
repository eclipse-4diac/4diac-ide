/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FB;

/**
 * The visualization of an FB. It Provides several containers for its interface.
 */
public class FBFigure extends AbstractFBNetworkElementFigure {
	
	public FBFigure(FB model, FBEditPart editpart) {
		super(model, editpart);
	}

	@Override
	protected boolean isResoruceTypeFBNElement() {
		return ((FB)model).isResourceTypeFB();
	}

}
