/********************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport.exceptions;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;

/**
 * The Class ReferencedTypeNotFoundException.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ReferencedTypeNotFoundException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9062674318458939898L;

	/** The type. */
	private FBType type;

	/** The missing type name. */
	private String missingTypeName;

	/**
	 * Instantiates a new referenced type not found exception.
	 * 
	 * @param arg0 the arg0
	 * @param type the type
	 * @param missingTypeName the missing type name
	 */
	public ReferencedTypeNotFoundException(final String arg0,
			final FBType type, final String missingTypeName) {
		super(arg0);
		this.type = type;
		this.missingTypeName = missingTypeName;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public FBType getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type the new type
	 */
	public void setType(final FBType type) {
		this.type = type;
	}

	/**
	 * Gets the missing type name.
	 * 
	 * @return the missing type name
	 */
	public String getMissingTypeName() {
		return missingTypeName;
	}

	/**
	 * Sets the missing type name.
	 * 
	 * @param missingTypeName the new missing type name
	 */
	public void setMissingTypeName(final String missingTypeName) {
		this.missingTypeName = missingTypeName;
	}

}
