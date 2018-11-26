/*******************************************************************************
 * Copyright (c) 2011, 2013, 2014 TU Wien ACIN, fortiss GmbH
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

public class IEC_WSTRING extends IEC_ANY {

	protected String value;

	public IEC_WSTRING() {
		super();
		value=""; //$NON-NLS-1$
	}


	public IEC_WSTRING(DataInputStream in) {
		super(in);
	}

	public IEC_WSTRING(String initial) {
		value=initial;
	}
	
	@Override
	public void decodeValueFrom(DataInputStream in) {
		StringBuilder result = new StringBuilder();
		int size;
		try {
			size = in.readShort();
			for (int i = 0; i < size; i++) {
				result.append(in.readChar());
			}
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		value = result.toString();

	}

	@Override
	public byte[] encodeTag() {
		byte[] retval = new byte[1];
		retval[0] = (0xff & (ASN1.APPLICATION + ASN1.WSTRING));
		return retval;
	}

	@Override
	public byte[] encodeValue() {
		ByteArrayOutputStream myOut = new ByteArrayOutputStream();
		DataOutputStream outputStream = new DataOutputStream(myOut);
		
		try {
			outputStream.writeShort(value.length());
			outputStream.writeChars(value);
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		
		return myOut.toByteArray();
	}
	
	/**
	 * @see java.lang.String#toString()
	 */
	@Override
	public String toString() {
		return value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	@Override
	public boolean setValue(String value) {
		this.value = value;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IEC_WSTRING) {
			return ((IEC_WSTRING) obj).value.equals(this.value);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_WSTRING)source).getValue();
			retval=true; 
		}
		return retval;
	}
	
}
