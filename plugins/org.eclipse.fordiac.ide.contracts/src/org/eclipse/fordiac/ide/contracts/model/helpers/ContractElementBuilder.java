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
package org.eclipse.fordiac.ide.contracts.model.helpers;

import org.eclipse.fordiac.ide.contracts.model.ContractKeywords;

public class ContractElementBuilder {
	private final StringBuilder contractElement;

	public ContractElementBuilder() {
		contractElement = new StringBuilder();
	}

	public ContractElementBuilder addAssumption() {
		contractElement.append(ContractKeywords.ASSUMPTION);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addComma() {
		contractElement.append(ContractKeywords.COMMA);
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

	public ContractElementBuilder addOccursComma() {
		contractElement.append(ContractKeywords.OCCURS);
		contractElement.append(ContractKeywords.COMMA);
		contractElement.append(" "); //$NON-NLS-1$
		return this;
	}

	public ContractElementBuilder addOffet() {
		contractElement.append(ContractKeywords.OFFSET);
		return this;
	}

	public ContractElementBuilder addReaction() {
		contractElement.append(ContractKeywords.REACTION);
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
