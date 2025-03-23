/*******************************************************************************
 * Copyright (c) 2023, 2025 Paul Pavlicek and others
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
 *   Felix Schmid
 *     - add additional building methods for new contract rules
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.model.helpers;

import java.util.List;

import org.eclipse.fordiac.ide.contracts.model.ContractKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.Event;

public class ContractElementBuilder {
	private final StringBuilder contractElement;

	public ContractElementBuilder() {
		contractElement = new StringBuilder();
	}

	public ContractElementBuilder add(final String s) {
		contractElement.append(s);
		return this;
	}

	public ContractElementBuilder addEventList(final List<Event> pins) {
		contractElement.append(String.join(", ", pins.stream().map(Event::getName).toList())); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addEventExpr(final List<Event> pins) {
		if (pins.size() == 1) {
			contractElement.append(pins.get(0).getName());
			contractElement.append(" "); //$NON-NLS-1$
			return this;
		}
		contractElement.append("("); //$NON-NLS-1$
		addEventList(pins);
		contractElement.append(") "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addAssumption() {
		contractElement.append(ContractKeywords.ASSUMPTION);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addComma() {
		contractElement.append(ContractKeywords.COMMA);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addEClose() {
		contractElement.append(ContractKeywords.EVENTS_CLOSE);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addEOpen() {
		contractElement.append(ContractKeywords.EVENTS_OPEN);
		return this;
	}

	public ContractElementBuilder addEvent() {
		contractElement.append(ContractKeywords.EVENT);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addEvent(final String event) {
		contractElement.append(event);
		return this;
	}

	public ContractElementBuilder addEvents() {
		contractElement.append(ContractKeywords.EVENTS);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addEventSpace(final String event) {
		contractElement.append(event);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addEvery() {
		contractElement.append(ContractKeywords.EVERY);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addGuarantee() {
		contractElement.append(ContractKeywords.GUARANTEE);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addOccur() {
		contractElement.append(ContractKeywords.OCCUR);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addOccurs() {
		contractElement.append(ContractKeywords.OCCURS);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addHasOccurred() {
		contractElement.append(ContractKeywords.HAS);
		contractElement.append(" "); //$NON-NLS-1$
		contractElement.append(ContractKeywords.OCCURRED);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addOccursComma() {
		contractElement.append(ContractKeywords.OCCURS);
		contractElement.append(ContractKeywords.COMMA);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addOffet() {
		contractElement.append(ContractKeywords.OFFSET);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addReaction() {
		contractElement.append(ContractKeywords.REACTION);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addAge() {
		contractElement.append(ContractKeywords.AGE);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addSpace() {
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addThen() {
		contractElement.append(ContractKeywords.THEN);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addTime(final String time) {
		contractElement.append(time);
		return this;
	}

	public ContractElementBuilder addTimeUnit() {
		contractElement.append(ContractKeywords.UNIT_OF_TIME);
		return this;
	}

	public ContractElementBuilder addMs() {
		contractElement.append(ContractKeywords.UNIT_OF_TIME);
		return this;
	}

	public ContractElementBuilder addWhenever() {
		contractElement.append(ContractKeywords.WHENEVER);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addWith() {
		contractElement.append(ContractKeywords.WITH);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addWithin() {
		contractElement.append(ContractKeywords.WITHIN);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public String getString() {
		return contractElement.toString();
	}
}
