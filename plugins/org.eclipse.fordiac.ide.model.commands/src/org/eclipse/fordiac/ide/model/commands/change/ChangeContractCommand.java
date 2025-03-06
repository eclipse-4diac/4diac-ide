/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;

import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.HelperTypes;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public class ChangeContractCommand extends Command {

	public static final String CONTRACT_ATTRIBUTE_NAME = "_SUBAPP_CONTRACT"; //$NON-NLS-1$

	private final SubApp subApp;
	private final String contract;
	private String oldContract;

	/**
	 * Instantiates a new change contract command.
	 *
	 * @param subAp    the subApp for which the contract should be changed
	 * @param contract the new contract, if null then contract will be deleted
	 */
	public ChangeContractCommand(final SubApp subApp, final String contract) {
		this.subApp = Objects.requireNonNull(subApp);
		this.contract = contract;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldContract = subApp.getAttributeValue(CONTRACT_ATTRIBUTE_NAME);

		// delete and add again to trigger refresh
		subApp.deleteAttribute(CONTRACT_ATTRIBUTE_NAME);
		if (contract != null) {
			subApp.setAttribute(CONTRACT_ATTRIBUTE_NAME, HelperTypes.CDATA, contract, ""); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		subApp.deleteAttribute(CONTRACT_ATTRIBUTE_NAME);
		if (oldContract != null) {
			subApp.setAttribute(CONTRACT_ATTRIBUTE_NAME, HelperTypes.CDATA, oldContract, ""); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		subApp.deleteAttribute(CONTRACT_ATTRIBUTE_NAME);
		if (contract != null) {
			subApp.setAttribute(CONTRACT_ATTRIBUTE_NAME, HelperTypes.CDATA, contract, ""); //$NON-NLS-1$
		}
	}
}
