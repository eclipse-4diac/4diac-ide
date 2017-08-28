/*******************************************************************************
 * Copyright (c) 2011, 2013 TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.util.comm.exceptions;

public class IllegalEncodingException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public IllegalEncodingException() {
	}

	/**
	 * @param arg0
	 */
	public IllegalEncodingException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public IllegalEncodingException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public IllegalEncodingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
