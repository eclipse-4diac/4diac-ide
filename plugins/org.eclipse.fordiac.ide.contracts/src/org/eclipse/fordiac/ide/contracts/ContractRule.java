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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.Utils;
import org.eclipse.fordiac.ide.contractSpec.CausalRelation;
import org.eclipse.fordiac.ide.contractSpec.EventExpr;
import org.eclipse.fordiac.ide.contractSpec.EventSpec;
import org.eclipse.fordiac.ide.contractSpec.Interval;
import org.eclipse.fordiac.ide.contractSpec.RepetitionOptions;
import org.eclipse.fordiac.ide.contracts.helpers.ContractUtils;

/**
 * Represents a single contract rule of any type for further processing by the
 * static/dynamic checker. Some fields are only relevant for certain types (see
 * further comments).
 */
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

	record SlidingWindow(int n, int outOf) {
	}

	private Type type;
	private boolean fulfilled;
	private ContractComponent owner;
	private List<String> inputs;
	private List<String> outputs;
	private CInterval interval;
	// only for REPETITION
	private CInterval offset;
	private double jitter;
	// only for REACTION/AGE
	private boolean inputIsSequence;
	private boolean outputIsSequence;
	private boolean once;
	private SlidingWindow nOutOfM;
	// only for CAUSAL REACTION/CAUSAL AGE
	private CausalRelation causalRelation;
	// TODO: clock

	ContractRule(final String event, final CInterval interval) {
		this.type = Type.SINGLE_EVENT;
		this.interval = interval;
		this.outputs = List.of(event);
	}

	ContractRule(final EventSpec event, final Interval interval) {
		this(Type.SINGLE_EVENT, interval);
		setEventSingle(event);
	}

	ContractRule(final String event, final CInterval interval, final CInterval offset, final double jitter) {
		this.type = Type.REPETITION;
		this.interval = interval;
		this.outputs = List.of(event);
		this.offset = offset;
		this.jitter = jitter;
	}

	ContractRule(final EventSpec event, final Interval interval, final RepetitionOptions options) {
		this(Type.REPETITION, interval);
		setEventSingle(event);

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

	ContractRule(final Type type, final EventExpr inputs, final EventExpr outputs, final Interval interval) {
		this(type, interval);
		setEventExpr(inputs, true);
		this.inputIsSequence = inputs.isSequence();
		setEventExpr(outputs, false);
		this.outputIsSequence = outputs.isSequence();
	}

	ContractRule(final Type type, final EventSpec input, final EventSpec output, final Interval interval) {
		this(type, interval);
		this.inputs = List.of(input.getPort().getName());
		this.outputs = List.of(output.getPort().getName());
	}

	private ContractRule(final Type type, final Interval interval) {
		this.type = type;
		this.interval = new CInterval(interval);
	}

	private void setEventSingle(final EventSpec event) {
		if (event.getPort().getIsInput() != 0) {
			this.inputs = List.of(getEventName(event));
		} else {
			this.outputs = List.of(getEventName(event));
		}
	}

	private void setEventExpr(final EventExpr expr, final boolean isInput) {
		if (expr.getEvent() != null) {
			setEventSingle(expr.getEvent());
			return;
		}

		final List<String> names = new ArrayList<>();
		for (final EventSpec eSpec : expr.getEvents().getEvents()) {
			names.add(eSpec.getPort().getName());
		}
		if (isInput) {
			inputs = names;
		} else {
			outputs = names;
		}
	}

	private static String getEventName(final EventSpec event) {
		return event.getPort().getName();
	}

	boolean isAssumption() {
		return (type == Type.SINGLE_EVENT || type == Type.REPETITION) && outputs == null;
	}

	private List<String> getPortNames() {
		return isAssumption() ? getInputs() : getOutputs();
	}

	String getSinglePort() {
		return isAssumption() ? getInputs().getFirst() : getOutputs().getFirst();
	}

	@Override
	public String toString() {
		return switch (type) {
		case SINGLE_EVENT -> ContractUtils.createSingleEvent(getPortNames(), interval.toString());
		case REPETITION -> {
			final String o = offset.getLowerBound() == 0 && offset.getUpperBound() == 0 ? null : offset.toString();
			final String j = jitter == 0 ? null : Utils.nsToString(jitter);
			yield ContractUtils.createRepetition(getPortNames(), interval.toString(), o, j);
		}
		case REACTION -> ContractUtils.createReaction(inputs, outputs, inputIsSequence, outputIsSequence,
				interval.toString(), once, nOutOfM.n(), nOutOfM.outOf());
		case AGE -> ContractUtils.createAge(inputs, outputs, inputIsSequence, outputIsSequence, interval.toString(),
				once, nOutOfM.n(), nOutOfM.outOf());
		case CAUSAL_REACTION -> ContractUtils.createCausalReaction(inputs.get(0), outputs.get(0), interval.toString());
		case CAUSAL_AGE -> ContractUtils.createCausalAge(inputs.get(0), outputs.get(0), interval.toString());
		};
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

	List<String> getInputs() {
		return inputs;
	}

	void setInputs(final List<String> inputs) {
		this.inputs = inputs;
	}

	List<String> getOutputs() {
		return outputs;
	}

	void setOutputs(final List<String> outputs) {
		this.outputs = outputs;
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

	boolean inputIsSequence() {
		return inputIsSequence;
	}

	boolean outputIsSequence() {
		return outputIsSequence;
	}

	boolean isOnce() {
		return once;
	}

	void setOnce(final boolean once) {
		this.once = once;
	}

	SlidingWindow getNOutOfM() {
		return nOutOfM;
	}

	void setNOutOfM(final SlidingWindow nOutOfM) {
		this.nOutOfM = nOutOfM;
	}

	CausalRelation getCausalRelation() {
		return causalRelation;
	}

	void setCausalRelation(final CausalRelation causalRelation) {
		this.causalRelation = causalRelation;
	}
}
