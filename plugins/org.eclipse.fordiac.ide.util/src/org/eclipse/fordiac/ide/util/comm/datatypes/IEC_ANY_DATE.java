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

/**
 * Abstract superclass for all date and time datatypes defined in IEC 61131-3
 */
public abstract class IEC_ANY_DATE extends IEC_ANY {

	protected long value;
	
	/**
	 * 
	 */
	public IEC_ANY_DATE() {

	}

	/**
	 * @param in {@link DataInputStream} holding ASN1 encoded data
	 */
	public IEC_ANY_DATE(DataInputStream in) {
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
		if (data instanceof IEC_ANY_DATE)
			return ((IEC_ANY_DATE) data).value == value;
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

}
