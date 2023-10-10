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

import org.eclipse.fordiac.ide.contracts.model.Reaction;

public class ReactionExeption extends ContractExeption {

	private static final long serialVersionUID = -7391480053584045054L;
	private final Reaction reaction;

	public ReactionExeption(final Reaction reaction, final String errorMessage) {
		super(errorMessage);
		this.reaction = reaction;
	}

	public ReactionExeption(final String errorMessage) {
		this(null, errorMessage);
	}

	public Reaction getReaction() {
		return reaction;
	}

}
