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
package org.eclipse.fordiac.ide.util.comm.datatypes.derived;

public interface ASN1Derived {

	/** IEC 61131-3 datatypes of Class PRIVATE */
	static final class PRIVATE {
		/** IEC 61131-3 primitive data type tag ids */
		public static final class PRIMITIVE {
			/* ITA DirectlyDerivedType REAL */
			public static final int FREQ =13;
		}

		/** IEC 61131-3 constructed data type tag ids */
		public static final class CONSTRUCTED {
			/** TESTBED Datatype for representation of palette object */
			public static final int PALETTE = 1;
		}
	}

	/*
	 * if derived datatypes of other classes (UNIVERSAL, APPLICATION, CONTEXT)
	 * should be supported, appropriate classes in this interface have to be
	 * created
	 */

}
