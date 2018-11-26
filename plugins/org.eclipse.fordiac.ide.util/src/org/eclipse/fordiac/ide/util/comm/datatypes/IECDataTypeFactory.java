/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN, 2018 Johannes Kepler University
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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.fordiac.ide.util.comm.datatypes.derived.DerivedDataTypeFactory;
import org.eclipse.fordiac.ide.util.comm.exceptions.DataTypeValueOutOfBoundsException;

public final class IECDataTypeFactory {

	private IECDataTypeFactory() {
		/* Utility classes shall not have a public ctor */
	}

	private static final Map<String, Integer> ASN1map;

	static {
		ASN1map = new HashMap<>();
		ASN1map.put("BOOL", Integer.valueOf(ASN1.APPLICATION + ASN1.BOOL)); //$NON-NLS-1$
		ASN1map.put("SINT", Integer.valueOf(ASN1.APPLICATION + ASN1.SINT)); //$NON-NLS-1$
		ASN1map.put("INT", Integer.valueOf(ASN1.APPLICATION + ASN1.INT)); //$NON-NLS-1$
		ASN1map.put("DINT", Integer.valueOf(ASN1.APPLICATION + ASN1.DINT)); //$NON-NLS-1$
		ASN1map.put("LINT", Integer.valueOf(ASN1.APPLICATION + ASN1.LINT)); //$NON-NLS-1$
		ASN1map.put("USINT", Integer.valueOf(ASN1.APPLICATION + ASN1.USINT)); //$NON-NLS-1$
		ASN1map.put("UINT", Integer.valueOf(ASN1.APPLICATION + ASN1.UINT)); //$NON-NLS-1$
		ASN1map.put("UDINT", Integer.valueOf(ASN1.APPLICATION + ASN1.UDINT)); //$NON-NLS-1$
		ASN1map.put("ULINT", Integer.valueOf(ASN1.APPLICATION + ASN1.ULINT)); //$NON-NLS-1$

		ASN1map.put("REAL", Integer.valueOf(ASN1.APPLICATION + ASN1.REAL)); //$NON-NLS-1$
		ASN1map.put("LREAL", Integer.valueOf(ASN1.APPLICATION + ASN1.LREAL)); //$NON-NLS-1$

		ASN1map.put("DATE", Integer.valueOf(ASN1.APPLICATION + ASN1.DATE)); //$NON-NLS-1$
		ASN1map.put("TIME", Integer.valueOf(ASN1.APPLICATION + ASN1.TIME)); //$NON-NLS-1$
		ASN1map.put("TIME_OF_DAY", Integer.valueOf(ASN1.APPLICATION + ASN1.TIME_OF_DAY)); //$NON-NLS-1$
		ASN1map.put("DATE_AND_TIME", Integer.valueOf(ASN1.APPLICATION + ASN1.DATE_AND_TIME)); //$NON-NLS-1$
		ASN1map.put("STRING", Integer.valueOf(ASN1.APPLICATION + ASN1.STRING)); //$NON-NLS-1$
		ASN1map.put("WSTRING", Integer.valueOf(ASN1.APPLICATION + ASN1.WSTRING)); //$NON-NLS-1$
		ASN1map.put("BYTE", Integer.valueOf(ASN1.APPLICATION + ASN1.BYTE)); //$NON-NLS-1$
		ASN1map.put("WORD", Integer.valueOf(ASN1.APPLICATION + ASN1.WORD)); //$NON-NLS-1$
		ASN1map.put("DWORD", Integer.valueOf(ASN1.APPLICATION + ASN1.DWORD)); //$NON-NLS-1$
		ASN1map.put("LWORD", Integer.valueOf(ASN1.APPLICATION + ASN1.LWORD)); //$NON-NLS-1$

		ASN1map.put("ANY", Integer.valueOf(ASN1.APPLICATION + ASN1.STRING)); //$NON-NLS-1$
	}

	public static IEC_ANY getIECTypeByTypename(String typeName) {
		Integer key = ASN1map.get(typeName);
		if (key != null) {
			switch (key.intValue()) {
			case (ASN1.APPLICATION + ASN1.BOOL):
				return new IEC_BOOL(false);
			case (ASN1.APPLICATION + ASN1.SINT):
				return new IEC_SINT(0);
			case (ASN1.APPLICATION + ASN1.INT):
				return new IEC_INT(0);
			case (ASN1.APPLICATION + ASN1.DINT):
				return new IEC_DINT(0);
			case (ASN1.APPLICATION + ASN1.LINT):
				return new IEC_LINT(0);
			case (ASN1.APPLICATION + ASN1.USINT):
				return new IEC_USINT(0);
			case (ASN1.APPLICATION + ASN1.UINT):
				return new IEC_UINT(0);
			case (ASN1.APPLICATION + ASN1.UDINT):
				return new IEC_UDINT(0);
			case (ASN1.APPLICATION + ASN1.ULINT):
				throw new DataTypeValueOutOfBoundsException("Data type ULINT not supported.");
			case (ASN1.APPLICATION + ASN1.REAL):
				return new IEC_REAL(0.0f);
			case (ASN1.APPLICATION + ASN1.LREAL):
				return new IEC_LREAL(0.0);
			case (ASN1.APPLICATION + ASN1.TIME):
				return new IEC_TIME();
			case (ASN1.APPLICATION + ASN1.DATE):
				return new IEC_DATE();
			case (ASN1.APPLICATION + ASN1.TIME_OF_DAY):
				return new IEC_TIME_OF_DAY();
			case (ASN1.APPLICATION + ASN1.DATE_AND_TIME):
				return new IEC_DATE_AND_TIME();
			case (ASN1.APPLICATION + ASN1.STRING):
				return new IEC_STRING();
			case (ASN1.APPLICATION + ASN1.BYTE):
				return new IEC_BYTE();
			case (ASN1.APPLICATION + ASN1.WORD):
				return new IEC_WORD();
			case (ASN1.APPLICATION + ASN1.DWORD):
				return new IEC_DWORD();
			case (ASN1.APPLICATION + ASN1.LWORD):
				return new IEC_LWORD();
			case (ASN1.APPLICATION + ASN1.WSTRING):
				return new IEC_WSTRING();
			default:
				throw new IllegalArgumentException("ASN.1 token unkown");
			}
		}
		return null;
	}

	public static IEC_ANY getIECType(int identifierOctet, DataInputStream in) {
		switch (identifierOctet) {
		// Event only
		case ASN1.NULL:
			return null;
		case (ASN1.APPLICATION + 0): // Since 0 is not an ASN.1 type --> boolean 'false'
			return new IEC_BOOL(false);
		case (ASN1.APPLICATION + ASN1.BOOL): // boolean 'true'
			return new IEC_BOOL(true);
		case (ASN1.APPLICATION + ASN1.SINT):
			return new IEC_SINT(in);
		case (ASN1.APPLICATION + ASN1.INT):
			return new IEC_INT(in);
		case (ASN1.APPLICATION + ASN1.DINT):
			return new IEC_DINT(in);
		case (ASN1.APPLICATION + ASN1.LINT):
			return new IEC_LINT(in);
		case (ASN1.APPLICATION + ASN1.USINT):
			return new IEC_USINT(in);
		case (ASN1.APPLICATION + ASN1.UINT):
			return new IEC_UINT(in);
		case (ASN1.APPLICATION + ASN1.UDINT):
			return new IEC_UDINT(in);
		case (ASN1.APPLICATION + ASN1.ULINT):
			throw new DataTypeValueOutOfBoundsException("Data type ULINT not supported.");
		case (ASN1.APPLICATION + ASN1.REAL):
			return new IEC_REAL(in);
		case (ASN1.APPLICATION + ASN1.LREAL):
			return new IEC_LREAL(in);
		case (ASN1.APPLICATION + ASN1.TIME):
			return new IEC_TIME(in);
		case (ASN1.APPLICATION + ASN1.DATE):
			return new IEC_DATE(in);
		case (ASN1.APPLICATION + ASN1.TIME_OF_DAY):
			return new IEC_TIME_OF_DAY(in);
		case (ASN1.APPLICATION + ASN1.DATE_AND_TIME):
			return new IEC_DATE_AND_TIME(in);
		case (ASN1.APPLICATION + ASN1.STRING):
			return new IEC_STRING(in);
		case (ASN1.APPLICATION + ASN1.BYTE):
			return new IEC_BYTE(in);
		case (ASN1.APPLICATION + ASN1.WORD):
			return new IEC_WORD(in);
		case (ASN1.APPLICATION + ASN1.DWORD):
			return new IEC_DWORD(in);
		case (ASN1.APPLICATION + ASN1.LWORD):
			return new IEC_LWORD(in);
		case (ASN1.APPLICATION + ASN1.WSTRING):
			return new IEC_WSTRING(in);
		// implicit array of FBData
		case (ASN1.APPLICATION + ASN1.CONSTRUCTED + ASN1.ARRAY):
			return new IEC_ARRAY(in);
		default:
			return DerivedDataTypeFactory.getDerivedType(identifierOctet, in);
		}
	}
}