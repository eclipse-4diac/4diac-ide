/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Melanie Winter
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts;

import org.eclipse.fordiac.ide.model.libraryElement.Primitive;

public class Parameter {
	private final Primitive primitive;

	public Parameter(final Primitive primitive) {
		this.primitive = primitive;
	}

	public String getParameter() {
		return primitive.getParameters();
	}

	public Primitive getPrimitive() {
		return primitive;
	}
}