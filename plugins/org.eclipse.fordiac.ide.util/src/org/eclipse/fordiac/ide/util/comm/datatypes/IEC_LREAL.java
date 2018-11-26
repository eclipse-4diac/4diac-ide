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

public class IEC_LREAL extends IEC_REAL {

	private static final long BOUNDS_MASK_32bit=0xFFFFFFFF00000000L;
	protected double value;
	
	/**
	 * 
	 */
	public IEC_LREAL() {
		super();
		value=0;
	}

	/**
	 * @param initial
	 */
	public IEC_LREAL(double initial) {
		value=initial;
	}

	/**
	 * @param in
	 */
	public IEC_LREAL(DataInputStream in) {
		super(in);
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public float getValue() {
		if(((int)value&BOUNDS_MASK_32bit) == 0){
			return super.getValue();
		}

		throw new DataTypeValueOutOfBoundsException("Value out of range. Use getDoubleValue() instead.");
	}

	/**
	 * @return the value
	 */
	public double getDoubleValue() {
		return value;
	}
	
	@Override
	public void decodeValueFrom(DataInputStream in) {
		try {
			value = in.readDouble();
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}
	
	@Override
	public byte[] encodeTag() {
		byte[] retval = new byte[1];
		retval[0] = (0xff & (ASN1.APPLICATION + ASN1.LREAL));
		return retval;
	}

	@Override
	public byte[] encodeValue() {
		ByteArrayOutputStream myOut=new ByteArrayOutputStream();
		DataOutputStream DOS=new DataOutputStream(myOut);
		
		try {
			DOS.writeDouble(value);
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		
		return myOut.toByteArray();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Double.toString(value);
	}

	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_LREAL)source).getValue();
			retval=true; 
		}
		return retval;
	}

	@Override
	public boolean setValue(String source) {
		try {
			this.value = Double.parseDouble(source);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
}
