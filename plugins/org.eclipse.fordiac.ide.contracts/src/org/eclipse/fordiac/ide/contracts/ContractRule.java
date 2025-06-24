/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import org.eclipse.fordiac.ide.Utils;
import org.eclipse.fordiac.ide.contractSpec.EventSpec;
import org.eclipse.fordiac.ide.contractSpec.Interval;
import org.eclipse.fordiac.ide.contractSpec.RepetitionOptions;

class ContractRule {
	enum Type {
		SINGLE_EVENT, REPETITION, REACTION, AGE, CAUSAL_REACTION, CAUSAL_AGE;

		@Override
		public String toString() {
			return switch (this) {
			case SINGLE_EVENT -> Messages.ContractRuleSingleEvent;
			case REPETITION -> Messages.ContractRuleRepetition;
			case REACTION -> Messages.ContractRuleReaction;
			case AGE -> Messages.ContractRuleAge;
			case CAUSAL_REACTION -> Messages.ContractRuleCausalReaction;
			case CAUSAL_AGE -> Messages.ContractRuleCausalAge;
			};
		}
	}

	private Type type;
	private boolean fulfilled;
	private ContractComponent owner;
	private String input;
	private String output;
	private CInterval interval;
	// only for REPETITION
	private CInterval offset;
	private double jitter;

	// TODO: the following aspects are ignored for now:
	// clock, "once", N out of M times, difference between () and {} in EventExpr

	ContractRule(final EventSpec event, final Interval interval) {
		setCommon(Type.SINGLE_EVENT, interval);
		setEvent(event);
	}

	ContractRule(final EventSpec event, final Interval interval, final RepetitionOptions options) {
		setCommon(Type.REPETITION, interval);
		setEvent(event);

		jitter = 0;
		if (options != null) {
			if (options.getJitter() != null) {
				jitter = Utils.timeExpr2Ns(options.getJitter().getTime()).orElse(0);
			}
			if (options.getOffset() != null && options.getOffset().getInterval() != null) {
				offset = new CInterval(options.getOffset().getInterval());
			}
		}
		if (offset == null) {
			offset = new CInterval('[', 0, 0, ']');
		}
	}

	ContractRule(final Type type, final EventSpec input, final EventSpec output, final Interval interval) {
		setCommon(type, interval);
		this.input = createEvent(input);
		this.output = createEvent(output);
	}

	private void setCommon(final Type type, final Interval interval) {
		this.type = type;
		this.interval = new CInterval(interval);
	}

	private void setEvent(final EventSpec event) {
		if (event.getPort().getIsInput() != 0) {
			this.input = createEvent(event);
		} else {
			this.output = createEvent(event);
		}
	}

	private static String createEvent(final EventSpec event) {
		return event.getPort().getName();
	}

	boolean isAssumption() {
		return (type == Type.SINGLE_EVENT || type == Type.REPETITION) && output == null;
	}

	String getPortName() {
		return isAssumption() ? getInput() : getOutput();
	}

	String getPortNameQualified() {
		return owner.getName() + "." + getPortName(); //$NON-NLS-1$
	}

	// === getters/setters
	Type getType() {
		return type;
	}

	void setType(final Type type) {
		this.type = type;
	}

	boolean isFulFilled() {
		return fulfilled;
	}

	void setFulFilled(final boolean fulfilled) {
		this.fulfilled = fulfilled;
	}

	ContractComponent getOwner() {
		return owner;
	}

	void setOwner(final ContractComponent owner) {
		this.owner = owner;
	}

	String getInput() {
		return input;
	}

	void setInput(final String input) {
		this.input = input;
	}

	String getOutput() {
		return output;
	}

	void setOutput(final String output) {
		this.output = output;
	}

	CInterval getInterval() {
		return interval;
	}

	void setInterval(final CInterval interval) {
		this.interval = interval;
	}

	double getJitter() {
		return jitter;
	}

	void setJitter(final double jitter) {
		this.jitter = jitter;
	}

	CInterval getOffset() {
		return offset;
	}

	void setOffset(final CInterval offset) {
		this.offset = offset;
	}
}
