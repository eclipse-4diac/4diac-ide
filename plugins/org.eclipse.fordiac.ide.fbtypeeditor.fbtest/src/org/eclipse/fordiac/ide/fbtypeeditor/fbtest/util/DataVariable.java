/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util;

public class DataVariable {
	private String dataName;
	private String dataType;
	private String dataValue;
	private int dataID;
	
	private boolean input;
	
	public DataVariable(String paDataName, int paDataID, String paDataType,boolean paIsInput, String paValue) {
		dataName = paDataName;
		setDataType(paDataType);
		dataID = paDataID;
		input = paIsInput;
		dataValue = paValue;
	}
	
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public int getDataID() {
		return dataID;
	}
	public void setDataID(int dataID) {
		this.dataID = dataID;
	}
	public boolean isInput() {
		return input;
	}
	public void setInput(boolean input) {
		this.input = input;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	
	@Override
	public String toString() {
		String retval;
		if (input) {
			retval="IN: ";
		} else {
			retval="OUT: ";
		}
		retval+=dataName+"("+dataID+") := "+dataValue;
		return retval;
	}
	
	
}
