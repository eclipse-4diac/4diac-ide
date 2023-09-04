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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class Guarantee extends ContractElement {
	private static final int POS_MS = 10;
	private static final int POS_WITHIN = 9;
	private static final int POS_OCCUR = 8;
	private static final int POS_EVENT_STRING = 6;
	private static final int POS_THEN = 5;
	private static final int POS_OCCURS = 4;
	private static final int POS_EVENT = 2;
	private static final int POS_WHENEVER = 1;
	private static final int GUARANTEE_LENGTH = 11;
	private static final int POS_OUTPUT_EVENT = 7;
	private static final int POS_INPUT_EVENT = 3;
	private static final int POSITION_NO = 10;

	private String outputEvent;

	Guarantee() {

	}

	public String getOutputEvent() {
		return outputEvent;
	}

	void setOutputEvent(final String outputEvent) {
		this.outputEvent = outputEvent;
	}

	public static Guarantee createGuarantee(final String line) {
		if (line.contains("Reaction")) { //$NON-NLS-1$
			return Reaction.createReaction(line);
		}
		if (line.contains("events")) { //$NON-NLS-1$
			return GuaranteeTwoEvents.createGuaranteeTwoEvents(line);
		}
		String[] parts = line.split(" "); //$NON-NLS-1$
		if (!isCorrectGuarantee(parts)) {
			throw new IllegalArgumentException("Error with Guarantee: " + line); //$NON-NLS-1$
		}
		final Guarantee guarantee = new Guarantee();
		guarantee.setInputEvent(parts[POS_INPUT_EVENT]);
		guarantee.setOutputEvent(parts[POS_OUTPUT_EVENT]);
		if (ContractUtils.isInterval(parts, POSITION_NO, ",")) { //$NON-NLS-1$
			parts = parts[POSITION_NO].split(","); //$NON-NLS-1$
			guarantee.setMin(Integer.parseInt(parts[0].substring(1)));
			parts = parts[1].split("]"); //$NON-NLS-1$
			guarantee.setMax(Integer.parseInt(parts[0]));
			return guarantee;
		}
		guarantee.setMax(Integer.parseInt(parts[POSITION_NO].substring(0, parts[POSITION_NO].length() - 2)));
		guarantee.setMin(0);
		return guarantee;
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
		if (!"event".equals(parts[POS_EVENT_STRING])) { //$NON-NLS-1$
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
	boolean isValid() {
		if (this instanceof final GuaranteeTwoEvents guaranteeTwoEvents) {
			return guaranteeTwoEvents.isValid();
		}
		if (getContract().getOwner() instanceof final SubApp subapp) {
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
			if (hasMatchingEvent(subapp, subapps, fBs)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasMatchingEvent(final SubApp subapp, final EList<SubApp> subapps, final EList<FB> fBs) {
		if (!subapps.isEmpty()) {
			final EList<Event> inputEvents = subapp.getInterface().getEventInputs();
			final EList<Event> outputEvents = subapp.getInterface().getEventOutputs();
			final List<Event> inputNames = inputEvents.parallelStream().filter(e -> e.getName().equals(getInputEvent()))
					.toList();
			final List<Event> outputNames = outputEvents.parallelStream().filter(e -> e.getName().equals(outputEvent))
					.toList();
			if (inputNames.size() == 1 && outputNames.size() == 1) {
				return true;
			}
		}
		if (fBs.size() == 1) {
			final EList<Event> inputFBEvents = fBs.get(0).getInterface().getEventInputs();
			final EList<Event> outputFBEvents = fBs.get(0).getInterface().getEventOutputs();
			final List<Event> inputNames = inputFBEvents.parallelStream()
					.filter(e -> e.getName().equals(getInputEvent())).toList();
			final List<Event> outputNames = outputFBEvents.parallelStream().filter(e -> e.getName().equals(outputEvent))
					.toList();
			if (inputNames.size() == 1 && outputNames.size() == 1) {
				return true;
			}
		}
		return false;
	}

	private static void sortHelper(final Map<String, EList<Guarantee>> mapGuarantees,
			final Map<String, EList<Reaction>> mapReactions,
			final Map<String, EList<GuaranteeTwoEvents>> mapGuaranteeTwoEvents, final Guarantee guarantee) {
		if (guarantee instanceof final GuaranteeTwoEvents guaranteeTwoEvents) {
			if (mapGuaranteeTwoEvents.containsKey(guaranteeTwoEvents.getOutputEvent())) {
				mapGuaranteeTwoEvents.get(guaranteeTwoEvents.getOutputEvent()).add(guaranteeTwoEvents);
			} else {
				final EList<GuaranteeTwoEvents> toAdd = new BasicEList<>();
				toAdd.add(guaranteeTwoEvents);
				mapGuaranteeTwoEvents.put(guaranteeTwoEvents.getOutputEvent(), toAdd);
			}
		} else if (guarantee instanceof final Reaction reaction) {
			if (mapReactions.containsKey(reaction.getOutputEvent())) {
				mapReactions.get(reaction.getOutputEvent()).add(reaction);
			} else {
				final EList<Reaction> toAdd = new BasicEList<>();
				toAdd.add(reaction);
				mapReactions.put(reaction.getOutputEvent(), toAdd);
			}
		} else if (mapGuarantees.containsKey(guarantee.getOutputEvent())) {
			mapGuarantees.get(guarantee.getOutputEvent()).add(guarantee);
		} else {
			final EList<Guarantee> toAdd = new BasicEList<>();
			toAdd.add(guarantee);
			mapGuarantees.put(guarantee.getOutputEvent(), toAdd);
		}
	}

	public static boolean isCompatibleWith(final EList<Guarantee> guarantees) {
		final Map<String, EList<Guarantee>> mapGuarantees = new HashMap<>();
		final Map<String, EList<Reaction>> mapReactions = new HashMap<>();
		final Map<String, EList<GuaranteeTwoEvents>> mapGuaranteeTwoEvents = new HashMap<>();
		final Consumer<Guarantee> sort = guarantee -> sortHelper(mapGuarantees, mapReactions, mapGuaranteeTwoEvents,
				guarantee);
		guarantees.parallelStream().forEach(sort);
		if (mapGuaranteeTwoEvents.size() > 0) {
			return GuaranteeTwoEvents.isCompatibleWith(mapGuarantees, mapReactions, mapGuaranteeTwoEvents);
		}
		if (mapReactions.size() > 0) {
			return Reaction.isCompatibleWith(mapGuarantees, mapReactions);
		}
		for (final Map.Entry<String, EList<Guarantee>> entry : mapGuarantees.entrySet()) {
			if (entry.getValue().size() != 1 && !ContractElement.isTimeConsistent(entry.getValue())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String createComment() {
		if (this instanceof final Reaction reaction) {
			return reaction.createComment();
		}
		if (this instanceof final GuaranteeTwoEvents twoEvents) {
			return twoEvents.createComment();
		}
		final StringBuilder comment = new StringBuilder();
		comment.append("GUARANTEE Whenever event "); //$NON-NLS-1$
		comment.append(getInputEvent());
		comment.append(" occurs, then event "); //$NON-NLS-1$
		comment.append(outputEvent);
		comment.append(" occur within "); //$NON-NLS-1$
		if (getMin() == 0 || getMin() == getMax()) {
			comment.append(getMax());
		} else {
			comment.append("["); //$NON-NLS-1$
			comment.append(getMin());
			comment.append(","); //$NON-NLS-1$
			comment.append(getMax());
			comment.append("]"); //$NON-NLS-1$
		}
		comment.append("ms"); //$NON-NLS-1$
		comment.append(System.lineSeparator());
		return comment.toString();
	}

}
