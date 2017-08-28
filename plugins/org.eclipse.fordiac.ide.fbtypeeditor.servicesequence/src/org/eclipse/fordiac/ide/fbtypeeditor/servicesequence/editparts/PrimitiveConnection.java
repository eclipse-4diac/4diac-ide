/*******************************************************************************
 * Copyright (c) 2008, 2009, 2015 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

public class PrimitiveConnection {

	private boolean inputPrimitive;
	private boolean isLeft;

	public PrimitiveConnection(final boolean isInputPrimitive) {
		this.inputPrimitive = isInputPrimitive;
		this.isLeft = true;
	}
	
	public void setPrimitiveType(final boolean isInputPrimitive){
		this.inputPrimitive = isInputPrimitive;
	}

	public void setInputDirection(final boolean isLeft){
		this.isLeft = isLeft;
	}
	
	public boolean isInputPrimitive() {
		return inputPrimitive;
	}

	public boolean isLeft() {
		return isLeft;
	}
}
