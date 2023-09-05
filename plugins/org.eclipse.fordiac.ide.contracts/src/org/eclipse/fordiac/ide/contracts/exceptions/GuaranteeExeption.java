/*******************************************************************************
   * Copyright (c) 2023 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.exceptions;

public class GuaranteeExeption extends ContractExeption {

	private static final long serialVersionUID = -942643004471027923L;

	public GuaranteeExeption(final String errorMessage) {
		super(errorMessage);
	}

}
