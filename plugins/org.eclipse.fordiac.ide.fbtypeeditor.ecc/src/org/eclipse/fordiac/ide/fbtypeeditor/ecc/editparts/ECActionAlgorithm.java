/*******************************************************************************
 * Copyright (c) 2011, 2013 TU Wien ACIN, Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import org.eclipse.fordiac.ide.model.libraryElement.ECAction;

public class ECActionAlgorithm {

	private ECAction action;
	
	public ECAction getAction(){
		return action;
	}
	
	public ECActionAlgorithm(ECAction action) {
		this.action = action;
	}
	
	String getLabel(){
		return action.getAlgorithm().getName();
	}
	
	
}
