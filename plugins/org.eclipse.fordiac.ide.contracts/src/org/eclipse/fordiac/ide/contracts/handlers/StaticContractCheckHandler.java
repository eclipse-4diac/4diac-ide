/*******************************************************************************
 * Copyright (c) 2023, 2025 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    - initial API and implementation and/or initial documentation
 *  Paul Pavlicek
 *    - - initial API and implementation and/or initial documentation
 *  Felix Schmid
 *    - adapted to use new contract checking system
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.handlers;

import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.contracts.ContractSystem;
import org.eclipse.fordiac.ide.contracts.Messages;
import org.eclipse.fordiac.ide.contracts.dialogs.ContractCheckResultDialog;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class StaticContractCheckHandler extends ContractCheckHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final Shell parentShell = HandlerUtil.getActiveShell(event);

		final Set<SubApp> toCheck = getSubAppsToCheck(event);
		if (!toCheck.isEmpty()) {
			final ContractSystem sysContracts = new ContractSystem();
			sysContracts.gatherContracts(toCheck);
			sysContracts.performStaticCheck();

			final var dialog = new ContractCheckResultDialog(sysContracts, isNetworkCheck(), parentShell);
			dialog.open();
			return Status.OK_STATUS;
		}
		MessageDialog.openError(parentShell, Messages.EvaluateSelectionErrorDialog_Title,
				Messages.EvaluateSelectionErrorDialog_Info);
		return Status.CANCEL_STATUS;
	}
}
