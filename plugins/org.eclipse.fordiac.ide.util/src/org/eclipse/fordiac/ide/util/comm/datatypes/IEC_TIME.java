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

public class IEC_TIME extends IEC_ANY {

	/**
	 * Duration time value in microseconds.
	 */
	private long value;


	public IEC_TIME() {
		value = 0L;
	}

	public IEC_TIME(long initial) {
		super();
		value = initial;
	}

	public IEC_TIME(String initial) {
		super();
		fromString(initial);
	}

	/**
	 * @param valueString
	 * @throws DataTypeValueOutOfBoundsException
	 * @throws NumberFormatException
	 */
	private void fromString(String paValueString)
			throws DataTypeValueOutOfBoundsException, NumberFormatException {
		String valueString = paValueString.toLowerCase();
		if (!(valueString.startsWith("t#") || valueString.startsWith("time#"))) { //$NON-NLS-1$ //$NON-NLS-2$
			throw new DataTypeValueOutOfBoundsException("Illegal value");
		}
		valueString = valueString.split("#")[1]; //$NON-NLS-1$
		String[] temp = valueString.split("_"); //$NON-NLS-1$
		long buf = 0;
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].endsWith("us")) { //$NON-NLS-1$
				buf+=Long.parseLong(temp[i].substring(0, temp[i].length()-2));
			}
			else if (temp[i].endsWith("ms")) { //$NON-NLS-1$
				buf+=Long.parseLong(temp[i].substring(0, temp[i].length()-2))*1000;
			}
			else if (temp[i].endsWith("s")) { //$NON-NLS-1$
				buf+=Long.parseLong(temp[i].substring(0, temp[i].length()-1))*1000000;
			}
			else if (temp[i].endsWith("m")) { //$NON-NLS-1$
				buf+=Long.parseLong(temp[i].substring(0, temp[i].length()-1))*60000000;
			}
			else if (temp[i].endsWith("h")) { //$NON-NLS-1$
				buf+=Long.parseLong(temp[i].substring(0, temp[i].length()-1))*3600000000L;
			}
			else if (temp[i].endsWith("d")) { //$NON-NLS-1$
				buf+=Long.parseLong(temp[i].substring(0, temp[i].length()-1))*86400000000L;
			}
			
		}

		value=buf;
	}

	/**
	 * @param in
	 */
	public IEC_TIME(DataInputStream in) {
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
		retval[0] = (0xff & (ASN1.APPLICATION + ASN1.TIME));
		return retval;
	}

	@Override
	public byte[] encodeValue() {
		ByteArrayOutputStream myOut = new ByteArrayOutputStream();
		DataOutputStream DOS = new DataOutputStream(myOut);

		try {
			DOS.writeLong(value);
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}

		return myOut.toByteArray();
	}
	
	@Override
	public String toString() {
		return Long.toString(value);
	}
	
	@Override
	public boolean equals(Object data) {
		if (data instanceof IEC_TIME)
			return ((IEC_TIME) data).value == value;
		return false;
	}
	
	@Override
	public int hashCode() {
		return Long.valueOf(value).hashCode();
	}
	
	/**
	 * @return the value
	 */
	public long getValue() {
		return value;
	}
	
	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_TIME)source).getValue();
			retval=true; 
		}
		return retval;
	}

	@Override
	public boolean setValue(String source) {
		try {
			fromString(source);
			return true;
		} catch (Exception e) {
		return false;
		}
	}
}
