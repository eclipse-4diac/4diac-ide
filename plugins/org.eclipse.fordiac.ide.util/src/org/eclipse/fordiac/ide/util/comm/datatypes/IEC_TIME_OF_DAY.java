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


public class IEC_TIME_OF_DAY extends IEC_ANY_DATE {

	/**
	 * 
	 */
	public IEC_TIME_OF_DAY() {
		Calendar c = Calendar.getInstance();
		reduceToTime(c);
		value = c.getTimeInMillis();
	}
	
	public IEC_TIME_OF_DAY(long initial) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(initial));
		reduceToTime(c);
		value = c.getTimeInMillis();
	}
	
	/**
	 * 
	 */
	public IEC_TIME_OF_DAY(Date initial) {
		Calendar c = Calendar.getInstance();
		c.setTime(initial);
		reduceToTime(c);
		value = initial.getTime();
	}
	
	public IEC_TIME_OF_DAY(String initial) {
		super();
		fromString(initial);
	}

	private void fromString(String paValueString)
			throws DataTypeValueOutOfBoundsException, NumberFormatException {
		String valueString = paValueString.toLowerCase();
		if (!(valueString.startsWith("tod#") || valueString.startsWith("time_of_day#"))) { //$NON-NLS-1$ //$NON-NLS-2$
			throw new DataTypeValueOutOfBoundsException("Illegal value"); 
		}
		valueString = valueString.split("#")[1];		 //$NON-NLS-1$
		Calendar c = Calendar.getInstance();
		c.clear();
		String[] time = valueString.split(":"); //$NON-NLS-1$
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
	public IEC_TIME_OF_DAY(DataInputStream in) {
		super(in);
	}

	@Override
	public byte[] encodeTag() {
		byte[] retval = new byte[1];
		retval[0] = (0xff & (ASN1.APPLICATION + ASN1.TIME_OF_DAY));
		return retval;
	}

	private void reduceToTime(Calendar c) {
		int[] temp = { c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
				c.get(Calendar.SECOND), c.get(Calendar.MILLISECOND) };
		c.clear();
		c.set(Calendar.HOUR_OF_DAY, temp[0]);
		c.set(Calendar.MINUTE, temp[1]);
		c.set(Calendar.SECOND, temp[2]);
		c.set(Calendar.MILLISECOND, temp[3]);
	}

	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_TIME_OF_DAY)source).getValue();
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
