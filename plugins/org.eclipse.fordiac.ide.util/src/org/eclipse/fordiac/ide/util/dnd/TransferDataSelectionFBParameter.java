/*******************************************************************************
 * Copyright (c) 2011, 2012 Profactor GbmH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
		this(null, null);
	}
	
	public TransferDataSelectionFBParameter(String name){
		this(name, null);
	}
	
	public TransferDataSelectionFBParameter(String name, String value){
		this.name = name;
		this.value = value;
	}
}
