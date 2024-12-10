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

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.contracts.exceptions.AssumptionExeption;
import org.eclipse.fordiac.ide.contracts.exceptions.ContractExeption;
import org.eclipse.fordiac.ide.contracts.model.helpers.ContractUtils;
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
		// reduced visibility
	}

	Assumption(final String inputEvent, final Interval interval) {
		super(inputEvent, interval);
	}

	static Assumption createAssumption(final String line) throws ContractExeption {
		if (line.contains(ContractKeywords.OFFSET)) {
			return AssumptionWithOffset.createAssumptionWithOffset(line);
		}
		final String[] parts = line.split(" "); //$NON-NLS-1$
		if (!isCorrectAssumption(parts)) {
			throw new AssumptionExeption("Error with Assumption: " + line); //$NON-NLS-1$
		}
		return createAssumptionFrom(parts);
	}

	private static Assumption createAssumptionFrom(final String[] parts) {
		final Assumption assumption = new Assumption();
		assumption.setInputEvent(parts[1]);
		if (ContractUtils.isInterval(parts, POSITION_NO, ContractKeywords.INTERVAL_DIVIDER)) {
			assumption.setRangeFromInterval(parts, POSITION_NO);
			return assumption;
		}
		assumption.setTime(new Instant(Integer.parseInt(parts[POSITION_NO].substring(0,
				parts[POSITION_NO].length() - ContractKeywords.UNIT_OF_TIME.length()))));
		return assumption;
	}

	private static boolean isCorrectAssumption(final String[] parts) {
		if (parts.length != ASSUMPTION_LENGTH) {
			return false;
		}
		if (!ContractKeywords.OCCURS.equals(parts[POS_OCCURS])) {
			return false;
		}
		if (!ContractKeywords.EVERY.equals(parts[POS_EVERY])) {
			return false;
		}
		return ContractKeywords.UNIT_OF_TIME.equals(
				parts[POS_TIME].substring(ContractUtils.getStartPosition(parts, POS_TIME), parts[POS_TIME].length()));
	}

	@Override
	boolean isValid() {
		if (!hasValidOwner()) {
			return false;
		}
		final EList<FBNetworkElement> fBNetworkElements = ((SubApp) getContract().getOwner()).getSubAppNetwork()
				.getNetworkElements();
		final List<SubApp> containedSubapps = fBNetworkElements.parallelStream().filter(ContractUtils::isContractSubapp)
				.map(el -> (SubApp) el).toList();
		final List<FB> containedfBs = fBNetworkElements.parallelStream().filter(FB.class::isInstance)
				.map(FB.class::cast).toList();
		final EList<Event> inputEvents = getContract().getOwner().getInterface().getEventInputs();
		return hasMatchingEvents(containedSubapps, containedfBs, inputEvents);
	}

	private boolean hasMatchingEvents(final List<SubApp> containedSubapps, final List<FB> containedfBs,
			EList<Event> inputEvents) {
		if (containedfBs.size() == 1) {
			inputEvents = containedfBs.get(0).getInterface().getEventInputs();
		}
		if (!containedSubapps.isEmpty() || containedfBs.size() == 1) {
			for (final Event inputE : inputEvents) {
				if (inputE.getName().equals(getInputEvent())) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isCompatibleWith(final EList<Assumption> assumptions) {

		for (final Assumption assumption : assumptions) {
			if (assumption instanceof AssumptionWithOffset) {
				return AssumptionWithOffset.isCompatibleWithOffset(assumptions);
			}

		}
		return Contract.isTimeConsistent(assumptions);
	}

	@Override
	public String asString() {
		if (this instanceof final AssumptionWithOffset withOffset) {
			return withOffset.asString();
		}
		final StringBuilder comment = new StringBuilder();
		if (getMax() == -1 || getMax() == getMin()) {
			comment.append(ContractUtils.createAssumptionString(getInputEvent(), String.valueOf(getMin())));

		} else {
			comment.append(ContractUtils.createAssumptionString(getInputEvent(), ContractUtils.createInterval(this)));
		}
		comment.append(System.lineSeparator());
		return comment.toString();

	}

}
