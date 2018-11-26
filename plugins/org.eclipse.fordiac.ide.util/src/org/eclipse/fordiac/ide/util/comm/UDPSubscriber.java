/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.comm;

import org.eclipse.fordiac.ide.util.comm.channels.IChannel;


public class UDPSubscriber extends IIecNetCommRcv  {

	public UDPSubscriber() {
		super();
	}
	
	@Override
	public boolean initialize(String sID) {
		boolean retval=false;
		retval = initialize(sID, IChannel.UDP);
		return retval;
	}

}