/*******************************************************************************
 * Copyright (c) 2023 Fabio Gandolfi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.handler;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.SequenceRootEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.helpers.EccTraceHelper;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.view.GetCoverageDialog;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class GetCoverageHandler extends AbstractHandler {

	HashMap<String, Integer> visitedStates = new HashMap<>();

	HashMap<ArrayList<String>, Integer> visitedPaths = new HashMap<>();


	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);

		if (!selection.isEmpty()){
			clearLists();

			for (final Object sel : selection) {
				if (((EditPart) sel) instanceof final SequenceRootEditPart serviceSeqEP) {
					for (final Object service : serviceSeqEP.getChildren()) {
						if (service instanceof final ServiceSequenceEditPart serviceEP
								&& serviceEP.getModel()
								.getEventManager() instanceof final EventManager evntMngr) {
							setCoverageData(evntMngr);
						}
					}
				}
				else if (((EditPart) sel) instanceof final ServiceSequenceEditPart serviceSeqEP
						&& serviceSeqEP.getModel().getEventManager() instanceof final EventManager evntMngr) {
					setCoverageData(evntMngr);
				}
			}
		}
		final GetCoverageDialog dialog = new GetCoverageDialog(HandlerUtil.getActiveShell(event), visitedStates,
				visitedPaths);
		dialog.open();

		return null;
	}

	private void setCoverageData(final EventManager evntMngr) {
		final EccTraceHelper eccTraceHelper = new EccTraceHelper(evntMngr.getTransactions());

		if (visitedStates.isEmpty()) {
			eccTraceHelper.getAllPossibleStates().forEach(s -> {
				visitedStates.put(s.getName(), 0);
			});
		}

		if (visitedPaths.isEmpty()) {
			eccTraceHelper.getAllPossiblePaths().forEach(p -> {
				visitedPaths.put(p, 0);
			});
		}

		eccTraceHelper.getAllStatesOfSequence().forEach(s -> visitedStates.merge(s.getName(), 1, Integer::sum));

		eccTraceHelper.getAllPathsOfSequence().forEach(p -> visitedPaths.merge(p, 1, Integer::sum));
	}

	private void clearLists() {
		visitedStates.clear();
		visitedPaths.clear();
	}
}
