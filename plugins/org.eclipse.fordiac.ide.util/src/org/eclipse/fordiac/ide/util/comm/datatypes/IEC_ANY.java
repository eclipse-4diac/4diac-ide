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
import java.io.IOException;

import org.eclipse.fordiac.ide.util.Activator;

/**
 * Abstract superclass of all IEC data types.
 */
public abstract class IEC_ANY implements Cloneable{

	/**
	 * Default constructor
	 */
	public IEC_ANY() {

	}

	/**
	 * Constructor initializing the data type�s value with a value read from a
	 * {@link DataInputStream}
	 * 
	 * @param in
	 *            the {@link DataInputStream} to read from
	 */
	public IEC_ANY(DataInputStream in) {
		decodeValueFrom(in);
	}

	/**
	 * Decodes the
	 * data type�s value from a {@link DataInputStream} and saves it to an
	 * internal value field. This value field must be of a suitable Java data
	 * type capable of holding values of the whole range of the according IEC
	 * data type.
	 * 
	 * @param in
	 *            the {@link DataInputStream} to read from
	 */
	public abstract void decodeValueFrom(DataInputStream in);

	/**
	 * Encodes the data of an IEC data type (tag and value) according to
	 * <code>ASN.1</code>
	 * 
	 * @return the <code>ASN.1</code> encoded data of the IEC data type (tag
	 *         and value) as byte array
	 */
	public byte[] encode() {
		ByteArrayOutputStream myOut = new ByteArrayOutputStream();
		try {
			myOut.write(encodeTag());
			myOut.write(encodeValue());
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}

		return myOut.toByteArray();

	}

	/**
	 * Encodes the <code>ASN.1</code> tag for the data type.
	 * 
	 * @return the encoded <code>ASN.1</code> tag as byte array
	 */
	public abstract byte[] encodeTag();

	/**
	 * Encodes the data type�s value according to <code>ASN.1</code>
	 * 
	 * @return the <code>ASN.1</code> encoded value of the data type as byte
	 *         array
	 */
	public abstract byte[] encodeValue();

	public abstract boolean setValue(IEC_ANY source);
	
	public abstract boolean setValue(String source);
	
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			Activator.getDefault().logError(e.getMessage(), e);
			return null;
		}
	}
	
}
