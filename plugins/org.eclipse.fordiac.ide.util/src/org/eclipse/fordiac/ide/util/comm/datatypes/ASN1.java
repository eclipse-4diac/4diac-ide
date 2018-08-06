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
import java.io.IOException;

/**
 * A class providing necessary constants and methods for <code>ASN.1</code>
 * tag decoding.
 */
public final class ASN1 {
	
	private ASN1() {
		/* Utility class - no public constructor desired */ 		
	}
	
	/** ASN.1 tag classes*/
	/** The ASN.1 NULL element */
	public static final int NULL = 5;
	/** The ASN.1 UNIVERSAL tag class */
	public static final int UNIVERSAL = 0;
	/** The ASN.1 APPLICATION tag class */
	public static final int APPLICATION = 64;
	/** The ASN.1 Context-specific tag class */
	public static final int CONTEXT = 128;
	/** The ASN.1 Private tag class */
	public static final int PRIVATE = 192;
	/** The ASN.1 PRIMITIVE encoding. */
	public static final int PRIMITIVE = 0;
	/** The ASN.1 CONSTRUCTED encoding. */
	public static final int CONSTRUCTED = 32;

	/** IEC 61131-3 data type tag numbers */
	/** IEC 61131-3 compact encoding boolean false */
	public static final int ANY = 0;
	/** IEC 61131-3 compact encoding boolean true */
	public static final int BOOL = 1;
	/** IEC 61131-3 compact encoding SINT */
	public static final int SINT = 2;
	/** IEC 61131-3 compact encoding INT */
	public static final int INT = 3;
	/** IEC 61131-3 compact encoding DINT */
	public static final int DINT = 4;
	/** IEC 61131-3 compact encoding LINT */
	public static final int LINT = 5;
	/** IEC 61131-3 compact encoding USINT */
	public static final int USINT = 6;
	/** IEC 61131-3 compact encoding UINT */
	public static final int UINT = 7;
	/** IEC 61131-3 compact encoding UDINT */
	public static final int UDINT = 8;
	/** IEC 61131-3 compact encoding ULINT */
	public static final int ULINT = 9;
	/** IEC 61131-3 compact encoding REAL */
	public static final int REAL = 10;
	/** IEC 61131-3 compact encoding LREAL */
	public static final int LREAL = 11;
	/** IEC 61131-3 compact encoding TIME */
	public static final int TIME = 12;
	/** IEC 61131-3 compact encoding DATE */
	public static final int DATE = 13;
	/** IEC 61131-3 compact encoding TIME_OF_DAY */
	public static final int TIME_OF_DAY = 14;
	/** IEC 61131-3 compact encoding DATE_AND_TIME */
	public static final int DATE_AND_TIME = 15;
	/** IEC 61131-3 compact encoding STRING */
	public static final int STRING = 16;
	/** IEC 61131-3 compact encoding BYTE */
	public static final int BYTE = 17;
	/** IEC 61131-3 compact encoding WORD */
	public static final int WORD = 18;
	/** IEC 61131-3 compact encoding DWORD */
	public static final int DWORD = 19;
	/** IEC 61131-3 compact encoding LWORD */
	public static final int LWORD = 20;
	/** IEC 61131-3 compact encoding WSTRING */
	public static final int WSTRING = 21;
	/** IEC 61131-3 compact encoding ARRAY */
	public static final int ARRAY = 22;
	
	public static final int DERIVED_DATA = 26;
	public static final int DIRECTLY_DERIVED_DATA = 27;
	public static final int ENUMERATED_DATA = 28;
	public static final int SUBRANGE_DATA = 29;
	public static final int STRUCTURED = 31;
	
	/**
	 * Retrieves the first identifier octet from a {@link DataInputStream} and
	 * delegates decoding of the data type to class {@link IECDataTypeFactory}
	 * 
	 * @param inputStream the {@link DataInputStream} to read from
	 * @return the decoded IEC data type
	 * @throws IOException
	 */
	public static IEC_ANY decodeFrom(DataInputStream inputStream)
			throws IOException {
		
		// throws IOException, if stream is closed by tcp-stack for example
		int identifierOctet = inputStream.readByte();
		if (identifierOctet == 0) {
			return null;
		}
		return (IECDataTypeFactory.getIECType(identifierOctet, inputStream));
	}

}
