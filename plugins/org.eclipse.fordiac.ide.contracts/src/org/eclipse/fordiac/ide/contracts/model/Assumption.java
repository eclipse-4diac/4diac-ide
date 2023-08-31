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
package org.eclipse.fordiac.ide.contracts.model;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class Assumption extends ContractElement {
	private static final int POS_OCCURS = 2;
	private static final int POS_TIME = 4;
	private static final int POS_EVERY = 3;
	private static final int ASSUMPTION_LENGTH = 5;
	private static final int POSITION_NO = 4;

	Assumption() {

	}

	static Assumption createAssumption(final String line) {
		if (line.contains("offset")) { //$NON-NLS-1$
			return AssumptionWithOffset.createAssumptionWithOffset(line);
		}
		String[] parts = line.split(" "); //$NON-NLS-1$
		if (!isCorrectAssumtion(parts)) {
			throw new IllegalArgumentException("Error with Assumption: " + line); //$NON-NLS-1$
		}
		final Assumption assumption = new Assumption();
		assumption.setInputEvent(parts[1]);
		if (parts[POSITION_NO].contains(",")) { //$NON-NLS-1$
			parts = parts[POSITION_NO].split(","); //$NON-NLS-1$
			assumption.setMin(Integer.parseInt(parts[0].substring(1)));
			parts = parts[1].split("]"); //$NON-NLS-1$
			assumption.setMax(Integer.parseInt(parts[0]));
			return assumption;
		}
		assumption.setMax(-1);
		assumption.setMin(Integer.parseInt(parts[POSITION_NO].substring(0, parts[POSITION_NO].length() - 2)));
		return assumption;
	}

	private static boolean isCorrectAssumtion(final String[] parts) {
		if (parts.length != ASSUMPTION_LENGTH) {
			return false;
		}
		if (!"occurs".equals(parts[POS_OCCURS])) { //$NON-NLS-1$
			return false;
		}
		if (!"every".equals(parts[POS_EVERY])) { //$NON-NLS-1$
			return false;
		}
		return "ms".equals(parts[POS_TIME].substring(parts[POS_TIME].length() - 2, parts[POS_TIME].length())); //$NON-NLS-1$
	}

	@Override
	boolean isValid() {
		if (getOwner().getOwner() instanceof final SubApp subapp) {
			final EList<SubApp> subapps = new BasicEList<>();
			final EList<FB> fBs = new BasicEList<>();
			final EList<Event> inputEvents = subapp.getInterface().getEventInputs();
			final EList<FBNetworkElement> fBNetworkElements = subapp.getSubAppNetwork().getNetworkElements();
			for (final FBNetworkElement element : fBNetworkElements) {
				if ((element instanceof final SubApp containedSubapp
						&& containedSubapp.getName().startsWith("_CONTRACT"))) { //$NON-NLS-1$
					subapps.add(containedSubapp);
				} else if (element instanceof final FB fb) {
					fBs.add(fb);
				}
			}
			if (hasMatchingEvents(subapps, fBs, inputEvents)) {
				return true;
			}

		}
		return false;
	}

	private boolean hasMatchingEvents(final EList<SubApp> subapps, final EList<FB> fBs,
			final EList<Event> inputEvents) {
		if (!subapps.isEmpty()) {
			for (final Event inputE : inputEvents) {
				if (inputE.getName().equals(getInputEvent())) {
					return true;
				}
			}
		}
		if (fBs.size() == 1) {
			final EList<Event> inputFBEvents = fBs.get(0).getInterface().getEventInputs();
			for (final Event inputFBEs : inputFBEvents) {
				if (inputFBEs.getName().equals(getInputEvent())) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isCompatibleWith(final EList<Assumption> assumptions) {

		for (final Assumption assumption : assumptions) {
			if (assumption instanceof AssumptionWithOffset) {
				return AssumptionWithOffset.isCompatibleWith(assumptions);
			}

		}
		return ContractElement.isTimeConsistent(assumptions);
	}

	@Override
	public String createComment() {
		if (this instanceof final AssumptionWithOffset withOffset) {
			return withOffset.createComment();
		}
		final StringBuilder comment = new StringBuilder();
		comment.append("ASSUMPTION "); //$NON-NLS-1$
		comment.append(getInputEvent());
		comment.append(" occurs every "); //$NON-NLS-1$
		if (getMax() == -1 || getMax() == getMin()) {
			comment.append(getMin());
		} else {
			comment.append("["); //$NON-NLS-1$
			comment.append(getMin());
			comment.append(","); //$NON-NLS-1$
			comment.append(getMax());
			comment.append("]"); //$NON-NLS-1$
		}
		comment.append("ms \n"); //$NON-NLS-1$
		return comment.toString();
	}

}
