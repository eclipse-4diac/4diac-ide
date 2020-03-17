/********************************************************************************
 * Copyright (c) 2008, 2009, 2013 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport.exceptions;

public class TypeImportException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4762764457661312258L;

	public TypeImportException(final String msg) {
		super(msg);
	}

	public TypeImportException(final String msg, Throwable exception) {
		super(msg, exception);
	}

}
