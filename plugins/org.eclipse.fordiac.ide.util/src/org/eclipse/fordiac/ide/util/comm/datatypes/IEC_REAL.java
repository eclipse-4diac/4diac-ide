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

public class IEC_REAL extends IEC_ANY {

	private float value;
	

	public IEC_REAL() {
		super();
		value=0;
	}

	public IEC_REAL(float initial) {
		value = initial;
	}

	public IEC_REAL(DataInputStream in) {
		super(in);
	}

	@Override
	public void decodeValueFrom(DataInputStream in) {
		try {
			value = in.readFloat();
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}
	
	@Override
	public byte[] encodeTag() {
		byte[] retval = new byte[1];
		retval[0] = (0xff & (ASN1.APPLICATION + ASN1.REAL));
		return retval;
	}

	@Override
	public byte[] encodeValue() {
		ByteArrayOutputStream myOut=new ByteArrayOutputStream();
		DataOutputStream DOS=new DataOutputStream(myOut);
		
		try {
			DOS.writeFloat(value);
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		
		return myOut.toByteArray();
	}

	/**
	 * @return the value
	 */
	public float getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(float value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Float.toString(value);
	}
	
	@Override
	public boolean equals(Object data) {
		if (data instanceof IEC_REAL)
			return ((IEC_REAL) data).value == value;
		if (data instanceof IEC_LREAL) {
			return ((IEC_LREAL) data).value == value;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Float.valueOf(value).hashCode();
	}

	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_REAL)source).getValue();
			retval=true; 
		}
		return retval;
	}

	@Override
	public boolean setValue(String source) {
		try {
			this.value = Float.parseFloat(source);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
