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
package org.eclipse.fordiac.ide.util.comm.datatypes.derived;

import java.io.DataInputStream;
import java.io.IOException;

import org.eclipse.fordiac.ide.util.comm.datatypes.ASN1;
import org.eclipse.fordiac.ide.util.comm.datatypes.IEC_ANY;
import org.eclipse.fordiac.ide.util.comm.exceptions.DataTypeValueOutOfBoundsException;
import org.eclipse.fordiac.ide.util.comm.exceptions.IllegalEncodingException;

public final class DerivedDataTypeFactory {

	// should be 31 according to IEC 8825-1
	private static final int MAX_LOW_TAG_NUMBER = 30;

	public static IEC_ANY getDerivedType(int identifierOctet, DataInputStream in) {

		int asn1typeID = identifierOctet & 31;
		int asn1Class = identifierOctet & 192;
		boolean asn1Constructed = (identifierOctet & 32) > 0;

		// are more Octets needed for representation of ASN1typeID?
		if (asn1typeID >= MAX_LOW_TAG_NUMBER) { 
			//evaluate larger ids (consuming more octets here...
			boolean finished = false;
			int buf=0;
			while(!finished){
				try {
					buf = in.readUnsignedByte();
				} catch (IOException e) {
					throw new IllegalEncodingException("Illegal encoding of tag number.");
				}
				if((buf&0x80)==0) finished=true;
				asn1typeID=(asn1typeID<<7)+buf;
				
			}
			
		}

		switch (asn1Class) {
		case ASN1.UNIVERSAL:
			return getUNIVERSALType(asn1typeID, asn1Constructed, in);
		case ASN1.APPLICATION:
			return getAPPLICATIONType(asn1typeID, asn1Constructed, in);
		case ASN1.CONTEXT:
			return getCONTEXTType(asn1typeID, asn1Constructed, in);
		case ASN1.PRIVATE:
			return getPRIVATEType(asn1typeID, asn1Constructed, in);
		}
		return null;

	}

	private static IEC_ANY getUNIVERSALType(int asn1TypeID,
			boolean constructed, DataInputStream in) {
		// Our own compliance profile: derived datatypes of class PRIVATE only!
		throw new DataTypeValueOutOfBoundsException(
				"Derived Types are supposed to be of class ASN1.PRIVATE!");

		// return null;
	}

	private static IEC_ANY getAPPLICATIONType(int asn1TypeID,
			boolean constructed, DataInputStream in) {
		// IEC 61499-1 E binds ASN1TypeIDs 0 to 22 for Class APPLICATION
		if (asn1TypeID <= ASN1.ARRAY)
			throw new DataTypeValueOutOfBoundsException(
					"TypeIDs <23 are reserved for elementary datatypes in Class \"Application\"");

		// Our own compliance profile: derived datatypes of class PRIVATE only!
		throw new DataTypeValueOutOfBoundsException(
				"Derived Types are supposed to be of class ASN1.PRIVATE!");

		// return null;
	}

	private static IEC_ANY getCONTEXTType(int asn1TypeID, boolean constructed,
			DataInputStream in) {
		// Our own compliance profile: derived datatypes of class PRIVATE only!
		throw new DataTypeValueOutOfBoundsException(
				"Derived Types are supposed to be of class ASN1.PRIVATE!");
		// return null;
	}

	private static IEC_ANY getPRIVATEType(int asn1TypeID, boolean constructed,
			DataInputStream in) {
		if (constructed) {

			switch (asn1TypeID) {
			default:
				throw new DataTypeValueOutOfBoundsException(
						"Datatype not supported yet.");
			}
		}
		switch (asn1TypeID) {
		default:
			throw new DataTypeValueOutOfBoundsException(
					"Datatype not supported yet.");
		}
	}
	
	private DerivedDataTypeFactory() {
		throw new UnsupportedOperationException("DerivedDataTypeFactory utility class should not be instantiated!"); //$NON-NLS-1$
	}
}