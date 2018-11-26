/*******************************************************************************
 * Copyright (c) 2011, 2014, 2015 TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny, Oliver Hummer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.comm.datatypes;

import java.io.DataInputStream;

public class IEC_BOOL extends IEC_ANY {

	private boolean value;

	
	public IEC_BOOL(int ASN1typeID) {
		switch (ASN1typeID) {
		case ASN1.ANY:
			this.value = false;
			break;
		case ASN1.BOOL:
			this.value = true;
			break;
		default:
			this.value = false;
		}
	}
	

	public IEC_BOOL(boolean value) {
		this.value = value;
	}

	public IEC_BOOL() {
		super();
		value=false;
	}
	
	
	@Override
	public void decodeValueFrom(DataInputStream in) {

	}
	
	@Override
	public byte[] encode() {
		return encodeTag();
	}

	@Override
	public byte[] encodeTag() {
		byte[] retval = new byte[1];
		if (value)
			retval[0]=(0xff & (ASN1.APPLICATION+ASN1.BOOL));
		else
			retval[0]=(0xff & (ASN1.APPLICATION+0));
		return retval;
	}

	@Override
	public byte[] encodeValue() {
		//value is encoded in Tag already
		return null;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof IEC_BOOL){
			return ((IEC_BOOL)obj).value==value;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Boolean.valueOf(value).hashCode();
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}


	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_BOOL)source).value;
			retval=true; 
		}
		return retval;
	}


	@Override
	public boolean setValue(String source) {
		this.value=Boolean.parseBoolean(source);
		return true;
	}
	
	
}
