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

public class DerivedDataTypeFactory {

	// should be 31 according to IEC 8825-1
	private static final int MAX_LOW_TAG_NUMBER = 30;

	public static IEC_ANY getDerivedType(int IdentifierOctet, DataInputStream in) {

		int ASN1typeID = IdentifierOctet & 31;
		int Class = IdentifierOctet & 192;
		boolean Constructed = (IdentifierOctet & 32) > 0;

		// are more Octets needed for representation of ASN1typeID?
		if (ASN1typeID >= MAX_LOW_TAG_NUMBER) { 
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
				ASN1typeID=(ASN1typeID<<7)+buf;
				
			}
			
		}

		switch (Class) {
		case ASN1.UNIVERSAL:
			return getUNIVERSALType(ASN1typeID, Constructed, in);
		case ASN1.APPLICATION:
			return getAPPLICATIONType(ASN1typeID, Constructed, in);
		case ASN1.CONTEXT:
			return getCONTEXTType(ASN1typeID, Constructed, in);
		case ASN1.PRIVATE:
			return getPRIVATEType(ASN1typeID, Constructed, in);
		}
		return null;

	}

	private static IEC_ANY getUNIVERSALType(int ASN1TypeID,
			boolean constructed, DataInputStream in) {
		// Our own compliance profile: derived datatypes of class PRIVATE only!
		throw new DataTypeValueOutOfBoundsException(
				"Derived Types are supposed to be of class ASN1.PRIVATE!");

		// return null;
	}

	private static IEC_ANY getAPPLICATIONType(int ASN1TypeID,
			boolean constructed, DataInputStream in) {
		// IEC 61499-1 E binds ASN1TypeIDs 0 to 22 for Class APPLICATION
		if (ASN1TypeID <= ASN1.ARRAY)
			throw new DataTypeValueOutOfBoundsException(
					"TypeIDs <23 are reserved for elementary datatypes in Class \"Application\"");

		// Our own compliance profile: derived datatypes of class PRIVATE only!
		throw new DataTypeValueOutOfBoundsException(
				"Derived Types are supposed to be of class ASN1.PRIVATE!");

		// return null;
	}

	private static IEC_ANY getCONTEXTType(int ASN1TypeID, boolean constructed,
			DataInputStream in) {
		// Our own compliance profile: derived datatypes of class PRIVATE only!
		throw new DataTypeValueOutOfBoundsException(
				"Derived Types are supposed to be of class ASN1.PRIVATE!");
		// return null;
	}

	private static IEC_ANY getPRIVATEType(int ASN1TypeID, boolean constructed,
			DataInputStream in) {
		if (constructed) {

			switch (ASN1TypeID) {
			default:
				throw new DataTypeValueOutOfBoundsException(
						"Datatype not supported yet.");
			}
		}
		switch (ASN1TypeID) {
		default:
			throw new DataTypeValueOutOfBoundsException(
					"Datatype not supported yet.");
		}
	}
}