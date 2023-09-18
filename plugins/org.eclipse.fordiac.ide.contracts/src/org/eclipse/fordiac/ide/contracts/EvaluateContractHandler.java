/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.contracts.model.AssumptionWithOffset;
import org.eclipse.fordiac.ide.contracts.model.Contract;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class EvaluateContractHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		for (final Object selected : selection.toList()) {
			Object obj = selected;
			if (selected instanceof final EditPart selectedEP) {
				obj = selectedEP.getModel();
			}
			if (obj instanceof final SubApp subapp) {
				final Contract test = Contract.getContractFromComment(subapp.getComment());
				test.setOwner(subapp);
				if (test.isValid()) {
					System.out.println("contract valid");
				}
				if (test.getAssumptions().get(0).getMin() == 1) {
					System.out.println("first assumtion works");
				}
				if (((AssumptionWithOffset) test.getAssumptions().get(1)).getMaxOffset() == -1) {
					System.out.println("second max assumtion works");
				}
				if (((AssumptionWithOffset) test.getAssumptions().get(1)).getMinOffset() == 2) {
					System.out.println("second min assumtion works");
				}
				final EvaluateContractCommand eccmd = new EvaluateContractCommand(subapp);
				if (eccmd.canExecute()) {
					eccmd.execute();
				}
			}
		}
		return Status.OK_STATUS;

	}

}