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
		if (subapp.getName().contains("_CONTRACT")) { //$NON-NLS-1$
			final String oldComment = subapp.getComment();
			int pos = oldComment.indexOf("ConstractState");//$NON-NLS-1$
			if (pos == -1) {
				pos = oldComment.length();
			}
			final StringBuilder comment = new StringBuilder();
			comment.append(oldComment.subSequence(0, pos));
			comment.append(" ConstractState"); //$NON-NLS-1$
			if (evaluate(subapp)) {
				comment.append(" TRUE"); //$NON-NLS-1$
			} else {
				comment.append(" FALSE"); //$NON-NLS-1$
			}
			cccmd = new ChangeCommentCommand(subapp, comment.toString());
			if (cccmd.canExecute()) {
				cccmd.execute();
			}
		}
	}

	private boolean evaluate(final SubApp subappToEvaluate) {
		final FBNetwork fBNetwork = subappToEvaluate.getSubAppNetwork();
		final EList<FBNetworkElement> fBNetworkElemnents = fBNetwork.getNetworkElements();
		final EList<SubApp> subApps = new BasicEList<>();
		for (final FBNetworkElement element : fBNetworkElemnents) {
			if ((element instanceof final SubApp containedSubapp)) {
				final EvaluateContractCommand eccmd = new EvaluateContractCommand(containedSubapp);
				if (eccmd.canExecute()) {
					eccmd.execute();
				}
				if (!hasValideContract(containedSubapp)) {
					return false;
				}
				subApps.add(containedSubapp);
			}
			if (subApps.isEmpty()) {
				return hasValideContract(subapp);
			}

		}
		return evaluteContaindSubapps(subApps);
	}

	private boolean evaluteContaindSubapps(final EList<SubApp> subApps) {
		final int[] times = new int[2];
		extractAssumtionNumbers(times, subapp);
		for (final SubApp containdSubApp : subApps) {
			final int[] timesContaind = new int[2];
			extractAssumtionNumbers(timesContaind, containdSubApp);
			if ((times[0] < timesContaind[0]) || (times[1] > timesContaind[1])) {
				return false;
			}
			times[0] = timesContaind[0];
			times[1] = timesContaind[1];
			extractGuaranteeNumbers(timesContaind, containdSubApp);
			times[0] += timesContaind[0];
			times[1] += timesContaind[1];
		}
		final int[] timesGuarantee = new int[2];
		extractGuaranteeNumbers(timesGuarantee, subapp);
		return (times[0] >= timesGuarantee[0] && times[1] <= timesGuarantee[1]);
	}

	private static void extractGuaranteeNumbers(final int[] times, final SubApp source) {
		String[] parts = source.getComment().split("ms"); //$NON-NLS-1$
		parts = parts[1].split(" "); //$NON-NLS-1$
		parts = parts[parts.length - 1].split(","); //$NON-NLS-1$
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

	private static void extractAssumtionNumbers(final int[] times, final SubApp source) {
		String[] parts = source.getComment().split("ms"); //$NON-NLS-1$
		parts = parts[0].split(" "); //$NON-NLS-1$
		parts = parts[parts.length - 1].split(","); //$NON-NLS-1$
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

	private static boolean hasValideContract(final SubApp contract) {
		return contract.getComment().startsWith("TRUE") //$NON-NLS-1$
				|| contract.getComment().startsWith("ASSUMPTION") && contract.getComment().contains("GUARANTEE");  //$NON-NLS-1$ //$NON-NLS-2$

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
