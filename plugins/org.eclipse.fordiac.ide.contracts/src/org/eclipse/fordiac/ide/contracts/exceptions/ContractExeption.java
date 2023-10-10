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

import org.eclipse.fordiac.ide.contracts.model.Contract;

public class ContractExeption extends Exception {

	private static final long serialVersionUID = 2453711497586948029L;
	private final Contract contract;

	ContractExeption(final Contract contract, final String errorMessage) {
		super(errorMessage);
		this.contract = contract;
	}

	ContractExeption(final String errorMessage) {
		this(null, errorMessage);
	}

	public Contract getContract() {
		return contract;
	}
}