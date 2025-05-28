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
package org.eclipse.fordiac.ide.contracts;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.editparts.InstanceContract;
import org.eclipse.fordiac.ide.model.commands.change.ChangeContractCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class EvaluateContractHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		final Shell parentShell = HandlerUtil.getActiveShell(event);

		// using a set for "toEvaluate" avoids adding the same SubApp multiple times
		// e.g. by selecting the SubApp but also its contract or pin
		final Set<SubApp> toEvaluate = new HashSet<>();
		final FBNetwork network = selection.size() == 1
				&& selection.getFirstElement() instanceof final AbstractEditPart editPart
				&& editPart.getModel() instanceof final FBNetwork nw ? nw : null;

		if (network != null) {
			// selected network -> check all contracts of network
			addFromNetwork(network, toEvaluate);
		} else {
			// selection -> look for selected SubApps with contracts
			addFromSelection(selection, toEvaluate);
		}

		if (!toEvaluate.isEmpty()) {
			final ContractSystem sysContracts = new ContractSystem();
			sysContracts.gatherContracts(toEvaluate);
			sysContracts.checkSystem();

			final var dialog = new ContractCheckResultDialog(sysContracts, network != null, parentShell);
			dialog.open();
			return Status.OK_STATUS;
		}
		MessageDialog.openError(parentShell, Messages.EvaluateSelectionErrorDialog_Title,
				Messages.EvaluateSelectionErrorDialog_Info);
		return Status.CANCEL_STATUS;
	}

	private static void addFromNetwork(final FBNetwork network, final Set<SubApp> toEvaluate) {
		for (final FBNetworkElement element : network.getNetworkElements()) {
			if (element instanceof final SubApp subapp
					&& subapp.getAttribute(ChangeContractCommand.CONTRACT_ATTRIBUTE_NAME) != null) {
				toEvaluate.add(subapp);
			}
		}
	}

	private static void addFromSelection(final IStructuredSelection selection, final Set<SubApp> toEvaluate) {
		for (final Object selected : selection) {
			if (selected instanceof final EditPart selectedEP) {
				Object obj = selectedEP.getModel();

				if (obj instanceof final Event eventPin) {
					obj = eventPin.getFBNetworkElement(); // selecting pin of SubApp selects SubApp
				} else if (obj instanceof final FBNetworkElement fb && !(obj instanceof final SubApp)
						&& fb.isNestedInSubApp()) {
					obj = fb.eContainer().eContainer(); // selecting FB selects containing SubApp
				}

				if (obj instanceof final SubApp subapp
						&& subapp.getAttribute(ChangeContractCommand.CONTRACT_ATTRIBUTE_NAME) != null) {
					toEvaluate.add(subapp);
				} else if (obj instanceof final InstanceContract instCon) {
					toEvaluate.add(instCon.getSubApp());
				}
			}
		}
	}
}
