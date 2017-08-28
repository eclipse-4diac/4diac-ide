/*******************************************************************************
 * Copyright (c) 2011, 2012 Profactor GbmH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Michael Hofmann, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.dnd;

public class TransferDataSelectionFBParameter {
	private String name = null;
	private String value = null;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public TransferDataSelectionFBParameter(){
		name = null;
		value = null;
	}
	
	public TransferDataSelectionFBParameter(String _name){
		name = _name;
		value = null;
	}
	
	public TransferDataSelectionFBParameter(String _name, String _value){
		name = _name;
		value = _value;
	}
}
