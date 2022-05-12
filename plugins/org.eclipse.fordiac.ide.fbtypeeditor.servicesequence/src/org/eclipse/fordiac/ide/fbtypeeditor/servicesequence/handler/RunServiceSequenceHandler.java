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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
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
				try {
					AbstractInterpreterTest.runFBTest((BasicFBType) seq.getService().getFBType(), seq);
					MessageDialog.openInformation(HandlerUtil.getActiveShell(event), Messages.RunServiceSequenceHandler_Success,
							Messages.RunServiceSequenceHandler_SequenceMatchesECC);

				} catch (final Exception e) {
					FordiacLogHelper.logError("Service Sequence was inconsistent", e); //$NON-NLS-1$
					MessageDialog.openError(HandlerUtil.getActiveShell(event), Messages.RunServiceSequenceHandler_InconsistencyDetected,
							Messages.RunServiceSequenceHandler_SequenceDoesNotMatchECC);
				}

			}
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof StructuredSelection) {
			Object selected = ((StructuredSelection) selection).getFirstElement();
			if (selected instanceof EditPart) {
				selected = ((EditPart) selected).getModel();
			}
			if (selected instanceof ServiceSequence) {
				setBaseEnabled(((ServiceSequence) selected).getService().getFBType() instanceof BasicFBType);
			}
		}
	}

	private static ServiceSequence getSequence(final Object selected) {
		if (selected instanceof ServiceSequenceEditPart) {
			return ((ServiceSequenceEditPart) selected).getModel();
		} else if (selected instanceof ServiceSequence) {
			return (ServiceSequence) selected;
		}
		return null;
	}
}
