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
package org.eclipse.fordiac.ide.contracts;

import org.eclipse.fordiac.ide.contracts.model.Contract;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.commands.Command;

public class EvaluateContractOnlyCommand extends Command {
	private ChangeCommentCommand cccmd;
	private final Contract contract;

	EvaluateContractOnlyCommand(final Contract contract) {
		if (contract == null) {
			throw new IllegalArgumentException();
		}
		this.contract = contract;
	}

	@Override
	public void execute() {
		final FBNetworkElement owner = contract.getOwner();
		final boolean isValid = contract.isValid();
		final StringBuilder comment = new StringBuilder();
		if (isValid) {
			comment.append(contract.getAsString());

		} else {
			final String oldComment = owner.getComment();
			int pos = oldComment.indexOf("ConstractState");//$NON-NLS-1$
			if (pos == -1) {
				pos = oldComment.length();
			}
			comment.append(oldComment.subSequence(0, pos));
		}
		comment.append("\nConstractState(This contract only):"); //$NON-NLS-1$
		if (isValid) {
			comment.append(" Contract is Valid"); //$NON-NLS-1$
		} else {
			comment.append(" Contract is not Valid \n"); //$NON-NLS-1$
			comment.append(contract.getError());
		}
		cccmd = new ChangeCommentCommand(owner, comment.toString());
		if (cccmd.canExecute()) {
			cccmd.execute();
		}
	}

	@Override
	public void undo() {
		cccmd.undo();
	}

	@Override
	public void redo() {
		cccmd.redo();
	}
}
