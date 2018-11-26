/*******************************************************************************
 * Copyright (c) 2011, 2014, 2017 TU Wien ACIN, fortiss GmbH
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

public class IEC_DINT extends IEC_ANY {
	
	private static final int BOUNDS_MASK = 0x00000000;
	protected int value;

	public IEC_DINT(){
		super();
		value=0;
	}
	
	public IEC_DINT(int initial){
		value = verifyValue(initial);
	}
	
	public IEC_DINT(DataInputStream in) {
		super(in);
	}

	@Override
	public void decodeValueFrom(DataInputStream in) {
		try {
			value = in.readInt();
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	@Override
	public byte[] encodeTag() {
		byte[] retval = new byte[1];
		retval[0] = (0xff & (ASN1.APPLICATION + ASN1.DINT));
		return retval;
	}
	
	@Override
	public byte[] encodeValue() {
		ByteArrayOutputStream myOut=new ByteArrayOutputStream();
		DataOutputStream DOS=new DataOutputStream(myOut);
		try {
			DOS.writeInt(value);
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		
		return myOut.toByteArray();
	}
	
	@Override
	public boolean equals(Object data) {
		if (data instanceof IEC_INT)
			return ((IEC_INT) data).value == value;
		else if (data instanceof IEC_LINT) {
			return ((IEC_LINT) data).value == value;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		this.value = verifyValue(value);
	}

	protected int verifyValue(int val) {
		// check whether val is in the range -128..127
		if ((val & BOUNDS_MASK) == 0) {
			return val;
		} 
		throw new DataTypeValueOutOfBoundsException("Data type value out of bounds.");
	}


	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_DINT)source).getValue();
			retval=true; 
		}
		return retval;
	}

	@Override
	public boolean setValue(String source) {
		try {
		value=verifyValue(Integer.parseInt(source));
		return true;
		} catch (Exception e) {
			return false;	
		}
	}
}
