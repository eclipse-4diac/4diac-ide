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

import org.eclipse.fordiac.ide.contracts.model.AssumptionWithOffset;

public class AssumptionWithOffsetExeption extends ContractExeption {

	private static final long serialVersionUID = 7450659197700485935L;
	public final AssumptionWithOffset assumption;

	public AssumptionWithOffsetExeption(final AssumptionWithOffset assumption, final String errorMessage) {
		super(errorMessage);
		this.assumption = assumption;
	}

	public AssumptionWithOffsetExeption(final String errorMessage) {
		this(null, errorMessage);
	}

	AssumptionWithOffset getAssumption() {
		return assumption;
	}
}
