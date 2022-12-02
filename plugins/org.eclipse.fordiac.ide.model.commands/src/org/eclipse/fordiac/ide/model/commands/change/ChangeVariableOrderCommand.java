/*******************************************************************************
 * Copyright (c) 2020, 2022 Johannes Kepler University
 * 				 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *   Alexander Lumplecker - changed class and constructor name
 *   Bianca Wiesmayr - cleanup, remove duplicated code
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class ChangeVariableOrderCommand extends AbstractChangeListElementOrderCommand<VarDeclaration> {

	public ChangeVariableOrderCommand(final EList<VarDeclaration> type, final VarDeclaration variable,
			final int indexChanged) {
		super(variable, isMoveUp(indexChanged), type);
	}

	public ChangeVariableOrderCommand(final EList<VarDeclaration> type, final VarDeclaration variable,
			final boolean moveUp) {
		this(type, variable, moveUp ? -1 : 1);
	}

	private static boolean isMoveUp(final int indexChanged) {
		// move down : +1
		// move up : -1
		if (indexChanged == 1) {
			return false;
		}
		if (indexChanged == -1) {
			return true;
		}
		throw new UnsupportedOperationException();
	}
}
