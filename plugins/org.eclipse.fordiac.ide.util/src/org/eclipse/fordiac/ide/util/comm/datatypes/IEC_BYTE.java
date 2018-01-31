/*******************************************************************************
 * Copyright (c) 2011, 2014 TU Wien ACIN, fortiss GmbH
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

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.fordiac.ide.util.Activator;

public class IEC_BYTE extends IEC_ANY_BIT {

	protected byte value;
	protected final int OCTETS=1;
	/**
	 * 
	 */
	public IEC_BYTE() {
		super();
		value = 0;
	}

	public IEC_BYTE(byte initial) {
		this.value = initial;
	}

	public IEC_BYTE(DataInputStream in) {
		super(in);
	}

	@Override
	public void decodeValueFrom(DataInputStream in) {
		try {
			value = in.readByte();
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	
	@Override
	public byte[] encodeTag() {
		byte[] retval = new byte[1];
		retval[0] = (0xff & (ASN1.APPLICATION + ASN1.BYTE));
		return retval;
	}

	@Override
	public byte[] encodeValue() {
		ByteArrayOutputStream myOut=new ByteArrayOutputStream();
		DataOutputStream DOS=new DataOutputStream(myOut);
		try {
			DOS.writeByte(value);
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		
		return myOut.toByteArray();
	}
	
	/**
	 * @return the value
	 */
	public byte getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(byte value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IEC_BYTE)
			if (this.value == ((IEC_BYTE) obj).value)
				return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		return Integer.valueOf(value).hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.toHexString();
	}

	protected String ConvertHexString(){
		return Integer.toHexString(value);
	}
	
	protected String ConvertBinString(){
		return Integer.toBinaryString(value);
	}
	
	
	/**
	 * This method converts the value of <code>IEC_BYTE</code> to a binary string representation according to IEC 61131-3
	 * 
	 * @return String representation of the value according to IEC 61131-3 (e.g., 2#11111111, 2#00000001)
	 */
	public String toBinString(){
		return super.toBinString(OCTETS);
	}
	
	/**
	 * This method converts the value of <code>IEC_BYTE</code> to a hexadecimal string representation according to IEC 61131-3
	 * 
	 * @return String representation of the value according to IEC 61131-3 (e.g., 16#FF, 16#01)
	 */	
	public String toHexString(){
		return super.toHexString(OCTETS);
	}

	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_BYTE)source).getValue();
			retval=true; 
		}
		return retval;
	}

	@Override
	public boolean setValue(String source) {
		try {
		this.value = Byte.parseByte(source);
		return true;
		}
		catch (NumberFormatException ne) {
			return false;
		}
	}
	
	
}
