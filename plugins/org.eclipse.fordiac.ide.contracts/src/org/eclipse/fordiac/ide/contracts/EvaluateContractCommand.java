/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.contracts.model.ContractKeywords;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public class EvaluateContractCommand extends Command {

	private final SubApp subapp;
	private ChangeCommentCommand cccmd;

	EvaluateContractCommand(final SubApp subapp) {
		if (subapp == null) {
			throw new IllegalArgumentException();
		}
		this.subapp = subapp;
	}

	@Override
	public void execute() {
		if (subapp.getName().startsWith(ContractKeywords.CONTRACT)) {
			final String oldComment = subapp.getComment();
			int pos = oldComment.indexOf(ContractKeywords.CONSTRACT_STATE);
			if (pos == -1) {
				pos = oldComment.length();
			}
			final StringBuilder comment = new StringBuilder();
			comment.append(oldComment.subSequence(0, pos));
			comment.append(ContractKeywords.CONSTRACT_STATE);
			if (evaluate(subapp)) {
				comment.append(ContractKeywords.TRUE);
			} else {
				comment.append(ContractKeywords.FALSE);
			}
			cccmd = new ChangeCommentCommand(subapp, comment.toString());
			if (cccmd.canExecute()) {
				cccmd.execute();
			}
		}
	}

	private boolean evaluate(final SubApp subappToEvaluate) {
		final FBNetwork fBNetwork = subappToEvaluate.getSubAppNetwork();
		final EList<FBNetworkElement> fBNetworkElements = fBNetwork.getNetworkElements();
		final EList<SubApp> subApps = new BasicEList<>();
		for (final FBNetworkElement element : fBNetworkElements) {
			if ((element instanceof final SubApp containedSubapp)) {
				final EvaluateContractCommand eccmd = new EvaluateContractCommand(containedSubapp);
				if (eccmd.canExecute()) {
					eccmd.execute();
				}
				if (!hasValidContract(containedSubapp)) {
					return false;
				}
				subApps.add(containedSubapp);
			}
			if (subApps.isEmpty()) {
				return hasValidContract(subapp);
			}

		}
		return evaluteContainedSubapps(subApps);
	}

	private boolean evaluteContainedSubapps(final EList<SubApp> subApps) {
		final int[] times = new int[2];
		extractAssumptionNumbers(times, subapp);
		for (final SubApp containdSubApp : subApps) {
			final int[] timesContained = new int[2];
			extractAssumptionNumbers(timesContained, containdSubApp);
			if ((times[0] < timesContained[0]) || (times[1] > timesContained[1])) {
				return false;
			}
			times[0] = timesContained[0];
			times[1] = timesContained[1];
			extractGuaranteeNumbers(timesContained, containdSubApp);
			times[0] += timesContained[0];
			times[1] += timesContained[1];
		}
		final int[] timesGuarantee = new int[2];
		extractGuaranteeNumbers(timesGuarantee, subapp);
		return (times[0] >= timesGuarantee[0] && times[1] <= timesGuarantee[1]);
	}

	private static void extractGuaranteeNumbers(final int[] times, final SubApp source) {
		String[] parts = source.getComment().split(ContractKeywords.UNIT_OF_TIME);
		parts = parts[1].split(" "); //$NON-NLS-1$
		parts = parts[parts.length - 1].split(ContractKeywords.UNIT_OF_TIME);
		if (parts.length == 1) {
			times[1] = Integer.parseInt(parts[0]);
			times[0] = 0;
		}
		if (parts.length == 2) {
			final String first = parts[0].substring(1);
			final String second = parts[1].substring(0, parts[1].length() - 1);
			times[0] = Integer.parseInt(first);
			times[1] = Integer.parseInt(second);
		}
	}

	private static void extractAssumptionNumbers(final int[] times, final SubApp source) {
		String[] parts = source.getComment().split(ContractKeywords.UNIT_OF_TIME);
		parts = parts[0].split(" "); //$NON-NLS-1$
		parts = parts[parts.length - 1].split(ContractKeywords.INTERVAL_DIVIDER);
		if (parts.length == 1) {
			times[1] = Integer.parseInt(parts[0]);
			times[0] = 0;
		}
		if (parts.length == 2) {
			final String first = parts[0].substring(1);
			final String second = parts[1].substring(0, parts[1].length() - 1);
			times[0] = Integer.parseInt(first);
			times[1] = Integer.parseInt(second);
		}
	}

	private static boolean hasValidContract(final SubApp contract) {
		return contract.getComment().startsWith(ContractKeywords.TRUE)
				|| contract.getComment().startsWith(ContractKeywords.ASSUMPTION)
						&& contract.getComment().contains(ContractKeywords.GUARANTEE);

	}

	@Override
	public void undo() {
		cccmd.undo();
	}

	@Override
	public void redo() {
		cccmd.redo();
	}

}
