/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 Profactor GmbH, TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martijn Rooker, Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.runtime;

/**
 * The Class LaunchRuntimeException.
 */
public class LaunchRuntimeException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8564311995940813195L;

	/**
	 * Instantiates a new launch runtime exception.
	 * 
	 * @param msg the msg
	 */
	public LaunchRuntimeException(String msg) {
		super(msg);
	}
}
