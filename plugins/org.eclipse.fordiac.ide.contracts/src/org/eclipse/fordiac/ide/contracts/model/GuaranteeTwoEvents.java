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
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.contracts.exceptions.GuaranteeTwoEventsExeption;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class GuaranteeTwoEvents extends Guarantee {

	private static final int POS_WITHIN = 9;
	private static final int POS_OCCUR = 8;
	private static final int POS_EVENTS = 6;
	private static final int POS_THEN = 5;
	private static final int POS_OCCURS = 4;
	private static final int POS_EVENT = 2;
	private static final int POS_WHENEVER = 1;
	private static final int POS_MS = 10;
	private static final int GUARANTEE_LENGTH = 11;
	private static final int POSITION_OUTPUT_EVENT = 7;
	private static final int POSITION_INPUT_EVENT = 3;
	private static final int POSITION_NO = 10;

	private String secondOutputEvent;

	GuaranteeTwoEvents() {
		throw new ExceptionInInitializerError("GuaranteeTwoEvents not Implemented"); //$NON-NLS-1$
		// remove when class is correctly evaluated in contract
	}

	String getSecondOutputEvent() {
		return secondOutputEvent;
	}

	void setSecondOutputEvent(final String secondOutputEvent) {
		this.secondOutputEvent = secondOutputEvent;
	}

	static Guarantee createGuaranteeTwoEvents(final String line) {
		final String[] parts = line.split(" "); //$NON-NLS-1$
		if (!isCorrectGuarantee(parts)) {
			throw new GuaranteeTwoEventsExeption("Error with Guarantee: " + line); //$NON-NLS-1$

		}
		final GuaranteeTwoEvents guarantee = new GuaranteeTwoEvents();
		guarantee.setInputEvent(parts[POSITION_INPUT_EVENT]);
		final String[] events = parts[POSITION_OUTPUT_EVENT].split(ContractKeywords.INTERVAL_DIVIDER);
		guarantee.setSecondOutputEvent(events[1].substring(0, events[1].length() - 1));
		guarantee.setOutputEvent(events[0].substring(1, events[0].length()));
		if (ContractUtils.isInterval(parts, POSITION_NO, ContractKeywords.INTERVAL_DIVIDER)) {
			guarantee.setRangeFromInterval(parts, POSITION_NO);
			return guarantee;
		}
		guarantee.setMax(-1);
		guarantee.setMin(Integer.parseInt(
				parts[POSITION_NO].substring(0, parts[POSITION_NO].length() - ContractKeywords.UNIT_OF_TIME.length())));
		return guarantee;

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
		return hasMatchingEvents(((SubApp) getContract().getOwner()), containedSubapps, containedfBs);

	}

	private boolean hasMatchingEvents(final SubApp subapp, final List<SubApp> containedSubapps,
			final List<FB> containedfBs) {
		if (!containedSubapps.isEmpty()) {
			final EList<Event> inputEvents = subapp.getInterface().getEventInputs();
			final EList<Event> outputEvents = subapp.getInterface().getEventOutputs();
			final List<Event> inputNames = inputEvents.parallelStream()
					.filter(e -> e.getName().equals(this.getInputEvent())).toList();
			final List<Event> outputNames = outputEvents.parallelStream()
					.filter(e -> e.getName().equals(this.getOutputEvent()) || e.getName().equals(secondOutputEvent))
					.toList();
			if (inputNames.size() == 1 && outputNames.size() == 2) {
				return true;
			}
		}
		if (containedfBs.size() == 1) {
			final EList<Event> inputFBEvents = containedfBs.get(0).getInterface().getEventInputs();
			final EList<Event> outputFBEvents = containedfBs.get(0).getInterface().getEventOutputs();
			final List<Event> inputNames = inputFBEvents.parallelStream()
					.filter(e -> e.getName().equals(this.getInputEvent())).toList();
			final List<Event> outputNames = outputFBEvents.parallelStream()
					.filter(e -> e.getName().equals(this.getOutputEvent()) || e.getName().equals(secondOutputEvent))
					.toList();
			if (inputNames.size() == 1 && outputNames.size() == 2) {
				return true;
			}
		}
		return false;
	}

	private static boolean isCorrectGuarantee(final String[] parts) {
		if (parts.length != GUARANTEE_LENGTH) {
			return false;
		}
		if (!ContractKeywords.WHENEVER.equals(parts[POS_WHENEVER])) {
			return false;
		}
		if (!ContractKeywords.EVENT.equals(parts[POS_EVENT])) {
			return false;
		}
		if (!(ContractKeywords.OCCURS + ContractKeywords.COMMA).equals(parts[POS_OCCURS])) {
			return false;
		}
		if (!ContractKeywords.THEN.equals(parts[POS_THEN])) {
			return false;
		}
		if (!ContractKeywords.EVENTS.equals(parts[POS_EVENTS])) {
			return false;
		}
		if (!ContractKeywords.OCCUR.equals(parts[POS_OCCUR])) {
			return false;
		}
		if (!ContractKeywords.WITHIN.equals(parts[POS_WITHIN])) {
			return false;
		}
		return ContractKeywords.UNIT_OF_TIME.equals(
				parts[POS_MS].subSequence(ContractUtils.getStartPosition(parts, POS_MS), parts[POS_MS].length()));
	}

	@Override
	public String createComment() {
		final StringBuilder comment = new StringBuilder();
		comment.append(ContractKeywords.GUARANTEE);
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.WHENEVER);
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.EVENT);
		comment.append(" "); //$NON-NLS-1$
		comment.append(getInputEvent());
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.OCCURS);
		comment.append(ContractKeywords.COMMA);
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.THEN);
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.EVENTS);
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.EVENTS_OPEN);
		comment.append(getOutputEvent());
		comment.append(ContractKeywords.COMMA);
		comment.append(getSecondOutputEvent());
		comment.append(ContractKeywords.EVENTS_CLOSE);
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.OCCUR);
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.WITHIN);
		comment.append(" "); //$NON-NLS-1$
		if (getMin() == 0 || getMin() == getMax()) {
			comment.append(getMax());
		} else {
			comment.append(ContractUtils.createInterval(this));
		}
		comment.append(ContractKeywords.UNIT_OF_TIME);
		comment.append(System.lineSeparator());
		return comment.toString();

	}

	public static boolean isCompatibleWith(final Map<String, EList<Guarantee>> mapGuarantees,
			final Map<String, EList<Reaction>> mapReactions,
			final Map<String, EList<GuaranteeTwoEvents>> mapGuaranteeTwoEvents) {
		if (mapGuarantees.isEmpty() && mapReactions.isEmpty() && mapGuaranteeTwoEvents.size() == 1) {
			return true;
		}
		// TODO Implement the rest
		return false;
	}
}
