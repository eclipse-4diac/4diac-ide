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
package org.eclipse.fordiac.ide.util.comm.channels;

import java.util.List;

import org.eclipse.fordiac.ide.util.comm.datatypes.IEC_ANY;


/**
 * Interface for Callback classes, which are called after receiving data
 */
public interface IIecReceivable extends IIecCommObject {

	/**
	 * method is called by {@link CCommThread} for evaluation (and decoding) of received data 
	 * @param inList
	 */
	void receiveIECData(List<IEC_ANY> inList);
	
	void setMyReceiveData(List<IEC_ANY> loReceiveData);
}
