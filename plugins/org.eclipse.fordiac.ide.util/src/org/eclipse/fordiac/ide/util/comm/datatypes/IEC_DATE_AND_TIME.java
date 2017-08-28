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

public class IEC_DATE_AND_TIME extends IEC_ANY_DATE {

	/**
	 * 
	 */
	public IEC_DATE_AND_TIME() {
		value = System.currentTimeMillis();
	}

	/**
	 * 
	 */
	public IEC_DATE_AND_TIME(long initial) {
		value = initial;
	}

	/**
	 * 
	 */
	public IEC_DATE_AND_TIME(Date initial) {
		value = initial.getTime();
	}

	public IEC_DATE_AND_TIME(String initial) {
		super();
		fromString(initial);
	}

	private void fromString(final String paValueString)
			throws DataTypeValueOutOfBoundsException, NumberFormatException {
		String valueString = paValueString.toLowerCase();
		if (!(valueString.startsWith("dt#") || valueString.startsWith("date_and_time#"))) { //$NON-NLS-1$ //$NON-NLS-2$
			throw new DataTypeValueOutOfBoundsException("Illegal value");
		}
		valueString = valueString.split("#")[1]; //$NON-NLS-1$
		String[] temp = valueString.split("-"); //$NON-NLS-1$
		if (temp.length != 4) {
			throw new DataTypeValueOutOfBoundsException("Illegal value");
		}
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]) - 1, Integer
				.parseInt(temp[2]));
		String[] time = temp[3].split(":"); //$NON-NLS-1$
		if (time.length != 3) {
			throw new DataTypeValueOutOfBoundsException("Illegal value");
		}
		c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
		c.set(Calendar.MINUTE, Integer.parseInt(time[1]));
		float seconds = Float.parseFloat(time[2]);
		c.set(Calendar.SECOND, (int) seconds);
		c.set(Calendar.MILLISECOND, (int) (seconds - (int) seconds) * 1000);
		value = c.getTimeInMillis();
	}

	/**
	 * @param in
	 */
	public IEC_DATE_AND_TIME(DataInputStream in) {
		super(in);
	}

	@Override
	public byte[] encodeTag() {
		byte[] retval = new byte[1];
		retval[0] = (0xff & (ASN1.APPLICATION + ASN1.DATE_AND_TIME));
		return retval;
	}


	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_DATE_AND_TIME)source).getValue();
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
