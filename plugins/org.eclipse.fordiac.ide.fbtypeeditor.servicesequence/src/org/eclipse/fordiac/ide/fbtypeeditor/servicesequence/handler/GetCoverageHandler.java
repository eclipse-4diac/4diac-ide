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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.BasicFbExecutionTrace;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.FBTestRunner;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.SequenceRootEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.helpers.ServiceSequenceSaveAndLoadHelper;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.view.GetCoverageDialog;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class GetCoverageHandler extends AbstractHandler {
	Map<String, Integer> visitedStates = new HashMap<>();
	Map<List<String>, Integer> visitedPaths = new HashMap<>();

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		Optional<String> eventManagerValidity = Optional.empty();
		if (!selection.isEmpty()) {
			clearLists();
			for (final Object sel : selection) {
				if (((EditPart) sel) instanceof final SequenceRootEditPart serviceSeqEP) {
					for (final Object service : serviceSeqEP.getChildren()) {
						if (service instanceof final ServiceSequenceEditPart serviceEP) {
							final ServiceSequence seq = serviceEP.getModel();
							eventManagerValidity = checkForExistingEventManager(seq, serviceSeqEP.getFBType());
							setCoverageData((EventManager) seq.getEventManager(), seq);
						}
					}
				} else if (((EditPart) sel) instanceof final ServiceSequenceEditPart serviceEP
						&& serviceEP.getParent() instanceof final SequenceRootEditPart serviceSeqEP) {
					final ServiceSequence seq = serviceEP.getModel();
					eventManagerValidity = checkForExistingEventManager(seq, serviceSeqEP.getFBType());
					setCoverageData((EventManager) seq.getEventManager(), serviceEP.getModel());
				}
			}
		}

		final MessageDialog dialog;

		if (eventManagerValidity.isEmpty()) {
			dialog = new GetCoverageDialog(HandlerUtil.getActiveShell(event), visitedStates, visitedPaths);
		} else {
			dialog = new MessageDialog(HandlerUtil.getActiveShell(event),
					Messages.RunServiceSequenceHandler_InconsistencyDetected, null,
					Messages.RunServiceSequenceHandler_SequenceDoesNotMatchECC + ":\n" + eventManagerValidity.get(), //$NON-NLS-1$
					MessageDialog.WARNING, new String[] { "OK" }, 0); //$NON-NLS-1$
		}

		dialog.open();

		return Status.OK_STATUS;
	}

	private void setCoverageData(final EventManager evntMngr, final ServiceSequence seq) {
		final BasicFbExecutionTrace eccTraceHelper = new BasicFbExecutionTrace(evntMngr.getTransactions(),
				((BasicFBType) seq.getService().getFBType()).getECC());
		if (visitedStates.isEmpty()) {
			eccTraceHelper.getAllPossibleStates().forEach(s -> visitedStates.put(s.getName(), Integer.valueOf(0)));
		}
		if (visitedPaths.isEmpty()) {
			eccTraceHelper.getAllPossiblePaths().forEach(p -> visitedPaths.put(p, Integer.valueOf(0)));
		}
		eccTraceHelper.getAllStatesOfSequence()
				.forEach(s -> visitedStates.merge(s.getName(), Integer.valueOf(1), Integer::sum));
		eccTraceHelper.getAllPathsOfSequence().forEach(p -> visitedPaths.merge(p, Integer.valueOf(1), Integer::sum));
	}

	private static Optional<String> checkForExistingEventManager(final ServiceSequence seq, final FBType fbType) {
		if (seq.getEventManager() == null) {
			seq.setEventManager(ServiceSequenceSaveAndLoadHelper.loadEventManagerFromServiceSequenceFile(fbType, seq));
		}
		if (seq.getEventManager() == null) {
			seq.setEventManager(executeServiceSequence(seq, fbType));
		}
		return FBTestRunner.checkResults(seq, (EventManager) seq.getEventManager());
	}

	private static EventManager executeServiceSequence(final ServiceSequence seq, final FBType fb) {
		if (seq.getServiceTransaction().isEmpty()) {
			return null;
		}
		final FBType copyFbType = EcoreUtil.copy(fb);
		final FBRuntimeAbstract rt = RuntimeFactory.createFrom(copyFbType);
		RuntimeFactory.setStartState(rt, seq.getStartState());
		final List<FBTransaction> transaction = TransactionFactory.createFrom(copyFbType, seq, rt);
		final EventManager eventManager = EventManagerFactory.createFrom(transaction);
		TransactionFactory.addTraceInfoTo(eventManager.getTransactions());
		EventManagerUtils.process(eventManager);
		return eventManager;
	}

	private void clearLists() {
		visitedStates.clear();
		visitedPaths.clear();
	}
}