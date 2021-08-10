/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Melanie Winter
 *      - Initial implementation and/or documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

public class ChangeTransactionOrderCommand extends Command {
	private final ServiceTransaction selection;
	private EList<ServiceTransaction> serviceTransactions;
	private int oldIndex;
	private int newIndex;

	private ChangeTransactionOrderCommand(final ServiceTransaction selection) {
		this.selection = selection;
		if (null != selection) {
			serviceTransactions = selection.getServiceSequence().getServiceTransaction();
			oldIndex = serviceTransactions.indexOf(selection);
		}
	}

	public ChangeTransactionOrderCommand(final ServiceTransaction selection, final boolean moveUp) {
		this(selection);
		this.newIndex = moveUp ? oldIndex - 1 : oldIndex + 1;

		if (newIndex < 0) {
			newIndex = 0;
		}
		if (newIndex >= serviceTransactions.size()) {
			newIndex = serviceTransactions.size() - 1;
		}
	}

	public ChangeTransactionOrderCommand(final ServiceTransaction selection, final int newIndex) {
		this(selection);
		this.newIndex = newIndex;
	}

	@Override
	public boolean canExecute() {
		return (null != selection) && (serviceTransactions.size() > 1) && (serviceTransactions.size() > newIndex);
	}

	@Override
	public void execute() {
		moveTo(newIndex);
	}

	@Override
	public void redo() {
		moveTo(newIndex);
	}

	@Override
	public void undo() {
		moveTo(oldIndex);
	}

	private void moveTo(final int index) {
		serviceTransactions.move(index, selection);
	}
}
