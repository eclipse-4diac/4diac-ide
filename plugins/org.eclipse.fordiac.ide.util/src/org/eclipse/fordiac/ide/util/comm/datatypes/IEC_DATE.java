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
import java.util.Calendar;
import java.util.Date;

import org.eclipse.fordiac.ide.util.comm.exceptions.DataTypeValueOutOfBoundsException;

public class IEC_DATE extends IEC_ANY_DATE {

	/**
	 * 
	 */
	public IEC_DATE() {
		Calendar c = Calendar.getInstance();
		reduceToDate(c);
		value=c.getTimeInMillis();
	}

	/**
	 * 
	 */
	public IEC_DATE(long initial) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(initial));
		reduceToDate(c);
		value = c.getTimeInMillis();
	}

	private void reduceToDate(Calendar c) {
		int[] temp ={c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE)};
		c.clear();
		c.set(temp[0],temp[1],temp[2]);
	}

	/**
	 * 
	 */
	public IEC_DATE(Date initial) {
		Calendar c = Calendar.getInstance();
		c.setTime(initial);
		reduceToDate(c);
		value = initial.getTime();
	}

	public IEC_DATE(String initial) {
		super();
		fromString(initial);
	}

	private void fromString(final String paValueString)
			throws DataTypeValueOutOfBoundsException, NumberFormatException {
		String valueString = paValueString.toLowerCase();
		if (!(valueString.startsWith("d#") || valueString.startsWith("date#"))) { //$NON-NLS-1$ //$NON-NLS-2$
			throw new DataTypeValueOutOfBoundsException("Illegal value"); 
		}
		valueString = valueString.split("#")[1]; //$NON-NLS-1$
		String[] temp = valueString.split("-"); //$NON-NLS-1$
		if (temp.length == 3) {
			throw new DataTypeValueOutOfBoundsException("Illegal value"); 
		}
		Calendar instance = Calendar.getInstance();
		instance.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])-1,
				Integer.parseInt(temp[2]));
		value = instance.getTimeInMillis();
	}

	/**
	 * @param in
	 */
	public IEC_DATE(DataInputStream in) {
		super(in);
	}

	@Override
	public byte[] encodeTag() {
		byte[] retval = new byte[1];
		retval[0] = (0xff & (ASN1.APPLICATION + ASN1.DATE));
		return retval;
	}


	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_DATE)source).getValue();
			retval=true; 
		}
		return retval;
	}

	@Override
	public boolean setValue(String source) {
		try {
			fromString(source);
			return true;
		} catch (Exception e) {
		  return false;
		}
	}
	
}
