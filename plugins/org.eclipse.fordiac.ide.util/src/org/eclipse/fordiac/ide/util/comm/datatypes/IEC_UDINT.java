/*******************************************************************************
 * Copyright (c) 2011, 2013, 2014, 2017 TU Wien ACIN, fortiss GmbH
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
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.fordiac.ide.util.Activator;

public class IEC_UDINT extends IEC_LINT {

	private static final long BOUNDS_MASK = 0xFFFFFFFF00000000L;
	
	public IEC_UDINT() {
		super();
	}
	
	public IEC_UDINT(long initial){
		super(initial);
	}

	public IEC_UDINT(DataInputStream in) {
		super(in);
	}

	@Override
	public void decodeValueFrom(DataInputStream in) {
		try {
			 int firstByte = (0x000000FF & (in.readByte()));
			 int secondByte = (0x000000FF & (in.readByte()));
		     int thirdByte = (0x000000FF & (in.readByte()));
		     int fourthByte = (0x000000FF & (in.readByte()));
		     value = ((firstByte << 24
	                | secondByte << 16
                    | thirdByte << 8
                    | fourthByte))
                   & 0xFFFFFFFFL;
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}
	
	@Override
	public byte[] encodeTag() {
		byte[] retval = new byte[1];
		retval[0] = (0xff & (ASN1.APPLICATION + ASN1.UDINT));
		return retval;
	}

	@Override
	public byte[] encodeValue() {
		ByteArrayOutputStream myOut=new ByteArrayOutputStream();
		DataOutputStream DOS=new DataOutputStream(myOut);
		
		try {
		     int tempByte = (int)(value & 0xFFL);
		     DOS.writeByte(tempByte);
		     tempByte = (int)((value & 0xFF00L)>>8);
		     DOS.writeByte(tempByte);
		     
		     tempByte = (int)((value & 0xFF0000L)>>16);
		     DOS.writeByte(tempByte);
		     tempByte = (int)((value & 0xFF000000L)>>24);
		     DOS.writeByte(tempByte);
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		
		return myOut.toByteArray();
	}

	@Override
	public boolean setValue(IEC_ANY source) {
		boolean retval=false;
		if (source.getClass().equals(this.getClass())) {
			this.value = ((IEC_UDINT)source).getValue();
			retval=true; 
		}
		return retval;
	}
	
}
