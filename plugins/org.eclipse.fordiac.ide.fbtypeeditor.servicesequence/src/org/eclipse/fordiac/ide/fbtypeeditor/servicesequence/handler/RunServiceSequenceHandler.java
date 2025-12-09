/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Bianca Wiesmayr, Melanie Winter
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.handler;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.mm.FBTestRunner;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class RunServiceSequenceHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		for (final Object selected : selection.toList()) {
			final ServiceSequence seq = getSequence(selected);
			if (seq != null) {
				final FBType fbType = EcoreUtil.copy(seq.getService().getFBType());
				Optional<String> result;
				if ((seq.getStartState() != null) && !seq.getStartState().isBlank()) { // $NON-NLS-1$
					result = FBTestRunner.runFBTest(fbType, seq, seq.getStartState());
				} else {
					result = FBTestRunner.runFBTest(fbType, seq);
				}

				if (result.isEmpty()) {
					MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
							Messages.RunServiceSequenceHandler_Success,
							Messages.RunServiceSequenceHandler_SequenceMatchesECC);
				} else {
					MessageDialog.openError(HandlerUtil.getActiveShell(event),
							Messages.RunServiceSequenceHandler_InconsistencyDetected,
							Messages.RunServiceSequenceHandler_SequenceDoesNotMatchECC + ":\n" + result.get()); //$NON-NLS-1$
				}
			}
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(!getSelectedSequences(selection).isEmpty());
	}

	private static List<ServiceSequence> getSelectedSequences(final ISelection selection) {
		if (selection instanceof final StructuredSelection structSel) {
			return structSel.toList().stream().map(RunServiceSequenceHandler::getSequence).filter(Objects::nonNull)
					.toList();
		}
		return Collections.emptyList();
	}

	private static ServiceSequence getSequence(Object selected) {
		if (selected instanceof final EditPart ep) {
			selected = ep.getModel();
		}
		if (selected instanceof final ServiceSequence serviceSeq) {
			return serviceSeq;
		}
		return null;
	}
}
