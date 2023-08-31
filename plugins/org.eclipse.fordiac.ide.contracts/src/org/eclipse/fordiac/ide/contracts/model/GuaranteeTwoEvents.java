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

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
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
		// throw new ExceptionInInitializerError("GuaranteeTwoEvents not Implemented"); //$NON-NLS-1$
		// remove when class is correctly evaluated in contract
	}

	String getSecondOutputEvent() {
		return secondOutputEvent;
	}

	void setSecondOutputEvent(final String secondOutputEvent) {
		this.secondOutputEvent = secondOutputEvent;
	}

	static Guarantee createGuaranteeTwoEvents(final String line) {
		String[] parts = line.split(" "); //$NON-NLS-1$
		if (!isCorrectGuarantee(parts)) {
			throw new IllegalArgumentException("Error with Guarantee: " + line); //$NON-NLS-1$
		}
		final GuaranteeTwoEvents guarantee = new GuaranteeTwoEvents();
		guarantee.setInputEvent(parts[POSITION_INPUT_EVENT]);
		final String[] events = parts[POSITION_OUTPUT_EVENT].split(","); //$NON-NLS-1$
		guarantee.setSecondOutputEvent(events[1].substring(0, events[1].length() - 1));
		guarantee.setOutputEvent(events[0].substring(1, events[0].length()));
		if (parts[POSITION_NO].contains(",")) { //$NON-NLS-1$
			parts = parts[POSITION_NO].split(","); //$NON-NLS-1$
			guarantee.setMin(Integer.parseInt(parts[0].substring(1)));
			parts = parts[1].split("]"); //$NON-NLS-1$
			guarantee.setMax(Integer.parseInt(parts[0]));
			return guarantee;
		}
		guarantee.setMax(-1);
		guarantee.setMin(Integer.parseInt(parts[POSITION_NO].substring(0, parts[POSITION_NO].length() - 2)));
		return guarantee;

	}

	@Override
	boolean isValid() {
		if (getOwner().getOwner() instanceof final SubApp subapp) {
			final EList<SubApp> subapps = new BasicEList<>();
			final EList<FB> fBs = new BasicEList<>();
			final EList<FBNetworkElement> fBNetworkElements = subapp.getSubAppNetwork().getNetworkElements();
			for (final FBNetworkElement element : fBNetworkElements) {
				if ((element instanceof final SubApp containedSubapp
						&& containedSubapp.getName().startsWith("_CONTRACT"))) { //$NON-NLS-1$
					subapps.add(containedSubapp);
				} else if (element instanceof final FB fb) {
					fBs.add(fb);
				}
			}
			if (hasMatchingEvents(subapp, subapps, fBs)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasMatchingEvents(final SubApp subapp, final EList<SubApp> subapps, final EList<FB> fBs) {
		if (!subapps.isEmpty()) {
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
		if (fBs.size() == 1) {
			final EList<Event> inputFBEvents = fBs.get(0).getInterface().getEventInputs();
			final EList<Event> outputFBEvents = fBs.get(0).getInterface().getEventOutputs();
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
		if (!"Whenever".equals(parts[POS_WHENEVER])) { //$NON-NLS-1$
			return false;
		}
		if (!"event".equals(parts[POS_EVENT])) { //$NON-NLS-1$
			return false;
		}
		if (!"occurs,".equals(parts[POS_OCCURS])) { //$NON-NLS-1$
			return false;
		}
		if (!"then".equals(parts[POS_THEN])) { //$NON-NLS-1$
			return false;
		}
		if (!"events".equals(parts[POS_EVENTS])) { //$NON-NLS-1$
			return false;
		}
		if (!"occur".equals(parts[POS_OCCUR])) { //$NON-NLS-1$
			return false;
		}
		if (!"within".equals(parts[POS_WITHIN])) { //$NON-NLS-1$
			return false;
		}
		return "ms".equals(parts[POS_MS].subSequence(parts[POS_MS].length() - 2, parts[POS_MS].length())); //$NON-NLS-1$
	}

	@Override
	public String createComment() {
		final StringBuilder comment = new StringBuilder();
		comment.append("GUARANTEE Whenever event "); //$NON-NLS-1$
		comment.append(getInputEvent());
		comment.append(" occurs, then events ("); //$NON-NLS-1$
		comment.append(getOutputEvent());
		comment.append(","); //$NON-NLS-1$
		comment.append(getSecondOutputEvent());
		comment.append(") occur within "); //$NON-NLS-1$
		if (getMin() == 0 || getMin() == getMax()) {
			comment.append(getMax());
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
