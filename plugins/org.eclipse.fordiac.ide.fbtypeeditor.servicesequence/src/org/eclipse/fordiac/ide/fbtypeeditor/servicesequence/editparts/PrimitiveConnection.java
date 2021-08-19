/*******************************************************************************
 * Copyright (c) 2008, 2009, 2015 Profactor GmbH, fortiss GmbH
 *               2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr, Melanie Winter - cleanup
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

/** A connection between the primitive's anchor point and the primitive name */
public class PrimitiveConnection {

	private boolean isInputPrimitive;
	private boolean isLeft;

	public PrimitiveConnection(final boolean isInputPrimitive) {
		this.isInputPrimitive = isInputPrimitive;
		this.isLeft = true;
	}

	public void setPrimitiveType(final boolean isInputPrimitive) {
		this.isInputPrimitive = isInputPrimitive;
	}

	public void setInputDirection(final boolean isLeft) {
		this.isLeft = isLeft;
	}

	public boolean isInputPrimitive() {
		return isInputPrimitive;
	}

	public boolean isLeft() {
		return isLeft;
	}
}
