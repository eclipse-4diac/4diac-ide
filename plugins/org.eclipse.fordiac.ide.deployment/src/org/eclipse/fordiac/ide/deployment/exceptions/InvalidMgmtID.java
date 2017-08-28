/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.exceptions;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.deployment.Messages;

/**
 * The Class InvalidMgmtID.
 */
public class InvalidMgmtID extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The invalid mgmt id. */
	private String invalidMgmtID;

	/**
	 * Instantiates a new invalid mgmt id.
	 * 
	 * @param invalidMgmtID the invalid mgmt id
	 */
	public InvalidMgmtID(final String invalidMgmtID) {
		super(MessageFormat.format(Messages.InvalidMgmtID_LABEL_InvalidMgmtID,
				new Object[] { invalidMgmtID }));
		this.invalidMgmtID = invalidMgmtID;
	}

	/**
	 * Gets the invalid mgmt id.
	 * 
	 * @return the invalid mgmt id
	 */
	public String getInvalidMgmtID() {
		return invalidMgmtID;
	}

	/**
	 * Sets the invalid mgmt id.
	 * 
	 * @param invalidMgmtID the new invalid mgmt id
	 */
	public void setInvalidMgmtID(final String invalidMgmtID) {
		this.invalidMgmtID = invalidMgmtID;
	}

}
