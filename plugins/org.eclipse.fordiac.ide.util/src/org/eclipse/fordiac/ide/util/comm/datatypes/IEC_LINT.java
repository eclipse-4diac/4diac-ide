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
import org.eclipse.fordiac.ide.util.comm.exceptions.DataTypeValueOutOfBoundsException;

public class IEC_LINT extends IEC_INT {
	
	private static final long BOUNDS_MASK=0x0L;
	private static final long BOUNDS_MASK_32bit=0xFFFFFFFF00000000L;
	protected long value;
	
	public IEC_LINT(){
		super();
		value=0L;
	}
	
	public IEC_LINT(int initial){
		super(initial);
	}
	
	public IEC_LINT(long initial){
		super();
		value = initial;
	}

	public IEC_LINT(DataInputStream in) {
		super(in);
	}

	@Override
	public void decodeValueFrom(DataInputStream in) {
		try {
			value = in.readLong();
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}
	
	@Override
	public byte[] encodeTag() {
		byte[] retval = new byte[1];
		retval[0] = (0xff & (ASN1.APPLICATION + ASN1.LINT));
		return retval;
	}
	
	@Override
	public byte[] encodeValue() {
		ByteArrayOutputStream myOut=new ByteArrayOutputStream();
		DataOutputStream DOS=new DataOutputStream(myOut);
		
		try {
			DOS.writeLong(value);
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		
		return myOut.toByteArray();
	}

	
	
	@Override
	public int getValue() {
		if((value&BOUNDS_MASK_32bit) ==0){
			return super.getValue();
		}
		throw new DataTypeValueOutOfBoundsException("Value out of range. Use getLongValue() instead.");
	}

	/**
	 * @return the value
	 */
	public long getLongValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(long value) {
		this.value = value;
	}
	
	protected long verifyValue(long val) {
		// check whether val is in the range -128..127
		if ((val & BOUNDS_MASK) == 0) {
			return val;
		} 
		throw new DataTypeValueOutOfBoundsException("Data type value out of bounds.");
	}
	
	@Override
	public String toString() {
		return Long.toString(value);
	}

	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_LINT)source).getValue();
			retval=true; 
		}
		return retval;
	}
	
	@Override
	public boolean setValue(String source) {
		try {
		value=verifyValue(Long.parseLong(source));
		return true;
		} catch (Exception e) {
			return false;	
		}
	}

	
}
