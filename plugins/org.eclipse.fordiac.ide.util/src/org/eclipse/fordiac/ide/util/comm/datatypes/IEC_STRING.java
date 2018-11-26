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
import java.io.UnsupportedEncodingException;

import org.eclipse.fordiac.ide.util.Activator;

public class IEC_STRING extends IEC_WSTRING {

	/**
	 * 
	 */
	public IEC_STRING() {
		super();
		value = ""; //$NON-NLS-1$
	}

	public IEC_STRING(String initial) {
			this.value = new String(initial.getBytes());
	}

	/**
	 * @param in
	 */
	public IEC_STRING(DataInputStream in) {
		super(in);
	}

	@Override
	public void decodeValueFrom(DataInputStream in) {
		StringBuilder result =new StringBuilder();
		int size;
		try {
			size = in.readShort();
			for (int i = 0; i < size; i++) {
				result.append((char) in.readUnsignedByte());
			}
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		value = result.toString();
	}

	@Override
	public byte[] encodeTag() {
		byte[] retval = new byte[1];
		retval[0] = (0xff & (ASN1.APPLICATION + ASN1.STRING));
		return retval;
	}

	@Override
	public byte[] encodeValue() {
		ByteArrayOutputStream myOut = new ByteArrayOutputStream();
		DataOutputStream outputString = new DataOutputStream(myOut);
		
		try {
			outputString.writeShort(value.length());
			outputString.write(value.getBytes());
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		
		return myOut.toByteArray();
	}
	
	/**
	 * @param value
	 *            the value to set
	 */
	@Override
	public boolean setValue(String value) {
		try {
			this.value = new String(value.getBytes("UTF-8")); //$NON-NLS-1$
			return true;
		} catch (UnsupportedEncodingException e) {
			Activator.getDefault().logError(e.getMessage(), e);
			return false;			
		}
	}

	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_STRING)source).getValue();
			retval=true; 
		}
		return retval;
	}

	
	
}
