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
 *   Alexander Lumplecker - extracted from ChangeVariableCommand
 *   					  - changed variable types to Algorithm
 *   Bianca Wiesmayr - use abstract command
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;

public class ChangeAlgorithmOrderCommand extends AbstractChangeListElementOrderCommand<ICallable> {

	public ChangeAlgorithmOrderCommand(final EList<ICallable> type, final ICallable alg, final int indexChanged) {
		super(alg, isMoveUp(indexChanged), type);
	}

	public ChangeAlgorithmOrderCommand(final EList<ICallable> type, final ICallable alg, final boolean moveUp) {
		this(type, alg, moveUp ? -1 : 1);
	}

	private static boolean isMoveUp(final int indexChanged) {
		if (indexChanged == 1) {
			return false;
		}
		if (indexChanged == -1) {
			return true;
		}
		throw new UnsupportedOperationException();
	}
}
