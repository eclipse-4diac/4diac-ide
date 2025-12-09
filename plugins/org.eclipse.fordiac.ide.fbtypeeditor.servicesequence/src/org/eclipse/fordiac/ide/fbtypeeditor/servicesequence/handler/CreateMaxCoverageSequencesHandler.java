/*******************************************************************************
 * Copyright (c) 2023 Fabio Gandolfi, Johannes Kepler University Linz
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
 *   Bianca Wiesmayr - cleanup
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.fb.interpreter.api.BasicFbExecutionTrace;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateRecordedServiceSequenceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class CreateMaxCoverageSequencesHandler extends AbstractHandler {

	List<ECState> notVisitedStates;
	List<List<String>> notVisitedPaths;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		final FBType fbtype = ServiceHandlerUtils.getSelectedFbType(null, selection);
		final CompoundCommand compound = CreateAllServiceSequencesHandler.createAllSequences(fbtype);
		compound.getCommands().forEach(cmd -> ((CreateRecordedServiceSequenceCommand) cmd).enableTraceInfo(true));
		for (final Command cmd : compound.getCommands()) {
			if (cmd.canExecute() && cmd instanceof final CreateRecordedServiceSequenceCommand servCmd
					&& fbtype instanceof BasicFBType) {
				final BasicFbExecutionTrace eccTraceHelper = (BasicFbExecutionTrace) servCmd.getTraceInfo();
				if ((notVisitedPaths == null && notVisitedStates == null)
						|| (notVisitedPaths.isEmpty() && notVisitedStates.isEmpty())) {
					notVisitedPaths = new ArrayList<>(eccTraceHelper.getAllPossiblePaths());
					notVisitedStates = new ArrayList<>(eccTraceHelper.getAllPossibleStates());
				}

				final List<ECState> commonStates = new ArrayList<>(notVisitedStates);
				commonStates.retainAll(eccTraceHelper.getAllStatesOfSequenceUnique());
				final List<List<String>> commonPaths = new ArrayList<>(notVisitedPaths);
				commonPaths.retainAll(eccTraceHelper.getAllPathsOfSequence());

				if (!commonPaths.isEmpty() || !commonStates.isEmpty()) {

					notVisitedStates.removeAll(commonStates);
					notVisitedPaths.removeAll(commonPaths);

					if (notVisitedPaths.isEmpty() && notVisitedStates.isEmpty()) {
						break;
					}
					cmd.execute();
				}
			}
		}
		return Status.OK_STATUS;
	}

}
