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
package org.eclipse.fordiac.ide.contracts;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.fordiac.ide.application.editparts.InstanceContract;
import org.eclipse.fordiac.ide.model.commands.change.ChangeContractCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class ContractCheckHandler extends AbstractHandler {

	private boolean isNetwork;

	protected Set<SubApp> getSubAppsToCheck(final ExecutionEvent event) {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);

		// using a set for "toCheck" avoids adding the same SubApp multiple times
		// e.g. by selecting the SubApp but also its contract or pin
		final Set<SubApp> toCheck = new HashSet<>();
		final FBNetwork network = selection.size() == 1
				&& selection.getFirstElement() instanceof final AbstractEditPart editPart
				&& editPart.getModel() instanceof final FBNetwork nw ? nw : null;

		isNetwork = network != null;
		if (isNetwork) {
			// selected network -> check all contracts of network
			addFromNetwork(network, toCheck);
		} else {
			// selection -> look for selected SubApps with contracts
			addFromSelection(selection, toCheck);
		}
		return toCheck;
	}

	protected boolean isNetworkCheck() {
		return isNetwork;
	}

	private static void addFromNetwork(final FBNetwork network, final Set<SubApp> toCheck) {
		for (final FBNetworkElement element : network.getNetworkElements()) {
			if (element instanceof final SubApp subapp
					&& subapp.getAttribute(ChangeContractCommand.CONTRACT_ATTRIBUTE_NAME) != null) {
				toCheck.add(subapp);
			}
		}
	}

	private static void addFromSelection(final IStructuredSelection selection, final Set<SubApp> toCheck) {
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
					toCheck.add(subapp);
				} else if (obj instanceof final InstanceContract instCon) {
					toCheck.add(instCon.getSubApp());
				}
			}
		}
	}
}
