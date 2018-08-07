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
import java.util.Arrays;

import org.eclipse.fordiac.ide.util.Activator;

public class IEC_ARRAY extends IEC_ANY {

	private IEC_ANY[] value;

	/**
	 * 
	 */
	public IEC_ARRAY() {
		super();
		value = new IEC_ANY[0];
	}

	public IEC_ARRAY(IEC_ANY[] initial) {
		value = initial.clone();
	}

	/**
	 * @param in
	 */
	public IEC_ARRAY(DataInputStream in) {
		super(in);
	}

	@Override
	public void decodeValueFrom(DataInputStream in) {
		int size = 0;
		int ASN1IdentifierOctet = 0;
		try {
			size = in.readShort();
			ASN1IdentifierOctet = in.readByte() & 0xFF;
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		IEC_ANY[] temp = new IEC_ANY[size];
		for (int i = 0; i < size; i++) {
			temp[i] = IECDataTypeFactory.getIECType(ASN1IdentifierOctet, in);
		}
		value = temp;
	}

	@Override
	public byte[] encodeTag() {
		byte[] retval = new byte[1];
		retval[0] = (0xff & (ASN1.APPLICATION + ASN1.CONSTRUCTED + ASN1.ARRAY));
		return retval;
	}

	@Override
	public byte[] encodeValue() {
		ByteArrayOutputStream myOut = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(myOut);
		try {

			dos.writeShort(value.length);
			dos.write(value[0].encodeTag());

			for (int i = 0; i < value.length; i++) {
				dos.write(value[i].encodeValue());
			}
			
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		return myOut.toByteArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IEC_ARRAY) {
			boolean equal = true;
			IEC_ARRAY that = (IEC_ARRAY) obj;
			if (that.value.length == this.value.length) {
				if (that.value.length == 0) {
					return true;
				} 
				if (!that.value[0].getClass().equals(this.value[0].getClass())) {
					return false;
				}
				for (int i = 0; i < this.value.length; i++) {
					if (!this.value[i].equals(that.value[i])) {
						equal = false;
					}
				}
			} else {
				return false;
			}
			return equal;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(value);
	}

	/**
	 * @return the value
	 */
	public IEC_ANY[] getValue() {
		return value.clone();
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(IEC_ANY[] value) {
		this.value = value.clone();
	}

	@Override
	public String toString() {
		StringBuilder tempString = new StringBuilder("["); //$NON-NLS-1$
		for (int i = 0; i < value.length; i++) {
			if (i != 0) {
				tempString.append(", "); //$NON-NLS-1$
			}
			tempString.append(value[i].toString());
		}
		tempString.append("]"); //$NON-NLS-1$
		return tempString.toString();
	}

	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_ARRAY)source).getValue();
			retval=true; 
		}
		return retval;
	}

	@Override
	public boolean setValue(String source) {
		// TODO implement String-Parsing for ARRAY
		return false;
	}

}
