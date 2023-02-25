/*******************************************************************************
 * Copyright (c) 2011, 2015, 2016 TU Wien ACIN, fortiss GmbH
 *               2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr
 *    - consistent dropdown menu edit
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import static org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider.ONE_CONDITION;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.gef.commands.Command;

public class ChangeConditionEventCommand extends Command {

	private final ECTransition transition;
	private final List<Event> eventList = new ArrayList<>();
	private final String conditionEvent;
	private String oldConditionEvent;

	// if the string is 1 we need to set capture the condition expression
	private String oldConditionExpression;

	/**
	 * @param transition
	 * @param conditonEvent name of the new event for the transition condition, in
	 *                      addition to an event name the values may also be an
	 *                      empty string for setting the no event on the transition
	 *                      condition or 1 for setting the transition condition to
	 *                      always true.
	 */
	public ChangeConditionEventCommand(final ECTransition transition, final String conditionEvent) {
		super();
		this.transition = transition;
		this.conditionEvent = conditionEvent;

		final BasicFBType fb = (null != transition) ? transition.getECC().getBasicFBType() : null;
		eventList.addAll(ECCContentAndLabelProvider.getInputEvents(fb));
	}

	@Override
	public boolean canExecute() {
		return conditionEvent.equals(ECCContentAndLabelProvider.EMPTY_FIELD)
				|| conditionEvent.contentEquals(ONE_CONDITION)
				|| !eventList.isEmpty();
	}

	@Override
	public void execute() {
		oldConditionEvent = transition.getConditionEvent() != null ? transition.getConditionEvent().getName()
				: ""; //$NON-NLS-1$
		if (ONE_CONDITION.equals(conditionEvent)) {
			oldConditionExpression = transition.getConditionExpression();
		}
		if (ONE_CONDITION.equals(transition.getConditionExpression())) {
			oldConditionExpression = transition.getConditionExpression();
			transition.setConditionExpression(""); //$NON-NLS-1$
		}
		redo();
	}

	@Override
	public void undo() {
		if (null != oldConditionExpression) {
			transition.setConditionExpression(oldConditionExpression);
		}
		transition.setConditionEvent(getEvent(oldConditionEvent));
	}

	@Override
	public void redo() {
		if (conditionEvent.equals(ONE_CONDITION)) {
			// one has been selected
			transition.setConditionExpression(ONE_CONDITION);
			transition.setConditionEvent(null);
		} else {
			transition.setConditionEvent(getEvent(conditionEvent));
		}
	}

	private Event getEvent(final String eventName) {
		for (final Event e : eventList) {
			if (ECCContentAndLabelProvider.getEventName(e).equals(eventName)) {
				return e;
			}
		}
		return null;
	}
}
