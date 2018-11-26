/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny, Oliver Hummer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.comm.datatypes;

import java.io.DataInputStream;

public abstract class IEC_ANY_BIT extends IEC_ANY {

	private static final int NIBBLES_PER_OCTET = 2;
	private static final int BITS_PER_OCTET = 8;

	/**
	 * 
	 */
	public IEC_ANY_BIT() {
		
	}

	/**
	 * @param in
	 */
	public IEC_ANY_BIT(DataInputStream in) {
		super(in);
	}
	
	/**
	 * Helper method for conversion of the value to a hexadecimal string representation according to IEC 61131-3.
	 * @return hexadecimal representation of value as string 
	 */
	protected abstract String ConvertHexString();

	
	/**
	 * Helper method for conversion of the value to a binary string representation according to IEC 61131-3.
	 * @return binary representation of value as string 
	 */
	protected abstract String ConvertBinString();
	
	
	/**
	 * This method converts the value of the Bit string to a hexadecimal string representation according to IEC 61131-3
	 * 
	 * @param octets Number of octets of the datatype to convert
	 * @return String representation of the value according to IEC 61131-3 (e.g., 16#FF, 16#FFFF)
	 */
	protected String toHexString(int octets) {
		int nibbles=octets*NIBBLES_PER_OCTET;
		String myTempString=ConvertHexString();
		int length=myTempString.length();
		
		if (length>nibbles)
			myTempString=myTempString.substring(length-nibbles, length);
		if (length<nibbles)
			for (int i=0; i<nibbles-length;i++)
				myTempString="0"+myTempString; //$NON-NLS-1$
		return "16#"+myTempString; //$NON-NLS-1$
	}

	/**
	 * This method converts the value of the Bit string to a binary string representation according to IEC 61131-3
	 * 
	 * @param octets Number of octets of the datatype to convert
	 * @return String representation of the value according to IEC 61131-3 (e.g., 2#10110111)
	 */
	protected String toBinString(int octets) {
		int bits=octets*BITS_PER_OCTET;
		String myTempString = ConvertBinString();
		int length=myTempString.length();
		
		if (length>bits)
			myTempString=myTempString.substring(length-bits, length);
		if (length<bits)
			for (int i=0; i<bits-length;i++)
				myTempString="0"+myTempString; //$NON-NLS-1$
		
		return "2#"+myTempString; //$NON-NLS-1$
	}

}
