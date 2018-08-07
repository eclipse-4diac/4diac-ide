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

public class IEC_LWORD extends IEC_ANY_BIT {

	private long value;
	private static final int OCTETS=8;
	/**
	 * 
	 */
	public IEC_LWORD() {
		super();
		value=0;
	}
	
	public IEC_LWORD(long initial) {
		this.value=initial;
	}
	
	public IEC_LWORD(DataInputStream in) {
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
		retval[0] = (0xff & (ASN1.APPLICATION + ASN1.LWORD));
		return retval;
	}
	
	@Override
	public byte[] encodeValue() {
		ByteArrayOutputStream myOut=new ByteArrayOutputStream();
		DataOutputStream DOS=new DataOutputStream(myOut);
		
		try {
			DOS.writeInt((int)(0xFFFFFFFF&value));
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		
		return myOut.toByteArray();
	}
	
	/**
	 * @return the value
	 */
	public long getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(long value) {
		this.value = value;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IEC_LWORD)
			if (this.value == ((IEC_LWORD) obj).value)
				return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		return Long.valueOf(value).hashCode();
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
	
	@Override
	protected String ConvertHexString()
	{
		return Long.toHexString(value);
	}
	
	@Override
	protected String ConvertBinString()
	{
		return Long.toBinaryString(value);
	}
	
	public String toBinString(){
		return super.toBinString(OCTETS);
	}
	
	public String toHexString(){
		return super.toHexString(OCTETS);
	}

	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_LWORD)source).getValue();
			retval=true; 
		}
		return retval;
	}

	@Override
	public boolean setValue(String source) {
		// TODO Implement String-Parsing for LWORD
		return false;
	}
	
}
