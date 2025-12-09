/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.eval.fb;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.fordiac.ide.model.eval.AbstractEvaluator;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public abstract class FBEvaluator<T extends FBType> extends AbstractEvaluator {
	private final T type;
	private FBEvaluatorEventQueue eventQueue;

	protected FBEvaluator(final T type, final Variable<?> context, final Iterable<Variable<?>> variables,
			final Evaluator parent) {
		super(new FBVariable(Evaluator.CONTEXT_NAME, type, variables), parent);
		this.type = type;
	}

	@Override
	public Value evaluate() throws EvaluatorException, InterruptedException {
		if (eventQueue != null) {
			Event event;
			while ((event = eventQueue.receiveInputEvent()) != null) {
				if (!isApplicable(event)) {
					throw new UnsupportedOperationException(
							"The event " + event.getName() + " is not applicable for this evaluator"); //$NON-NLS-1$ //$NON-NLS-2$
				}
				evaluate(event);
			}
		}
		return null;
	}

	/**
	 * Evaluate a single event
	 */
	public abstract void evaluate(final Event event) throws EvaluatorException, InterruptedException;

	/**
	 * Determine if the event is applicable to the current evaluator
	 */
	public boolean isApplicable(final Event event) {
		return event.eContainer() != null && switch (event.eContainer().eContainer()) {
		case final FBType fbType when fbType == type -> event.isIsInput();
		case final AdapterFB adapterFB when adapterFB.getAdapterDecl() != null
				&& adapterFB.getAdapterDecl().eContainer() != null
				&& adapterFB.getAdapterDecl().eContainer().eContainer() == type ->
			!event.isIsInput();
		case null, default -> false;
		};
	}

	/**
	 * Send an output event to the event queue (if present)
	 *
	 * @param event The event to send
	 * @return {@code true} if the event was successfully added to this queue or the
	 *         queue was null, {@code false} otherwise
	 */
	protected boolean sendOutputEvent(final Event event) {
		return eventQueue == null || eventQueue.sendOutputEvent(event);
	}

	/**
	 * Send multiple output events to the event queue (if present)
	 *
	 * @param events The events to send
	 * @return {@code true} if the events were successfully added to the queue or
	 *         the queue was null, {@code false} otherwise
	 */
	protected boolean sendOutputEvents(final Iterable<Event> events) {
		if (eventQueue != null) {
			for (final Event event : events) {
				if (!sendOutputEvent(event)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String getName() {
		return type.getName();
	}

	@Override
	public FBVariable getContext() {
		return ((FBVariable) super.getContext());
	}

	@Override
	public Object getSourceElement() {
		return type;
	}

	@Override
	public Set<String> getDependencies() {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(type.eAllContents(), 0), false)
				.map(content -> switch (content) {
				case final Attribute attribute -> VariableOperations.getDependencies(attribute);
				case final VarDeclaration varDeclaration -> VariableOperations.getDependencies(varDeclaration);
				default -> Collections.<String>emptySet();
				}).flatMap(Collection::stream).collect(Collectors.toUnmodifiableSet());
	}

	@Override
	public Map<String, Variable<?>> getVariables() {
		return getContext().getValue().getMembers();
	}

	@Override
	public void reset(final Iterable<Variable<?>> variables) {
		getContext().setValue(new FBVariable(Evaluator.CONTEXT_NAME, type, variables).getValue());
		update(getContext().getValue().getMembers().values());
	}

	@Override
	public boolean isPersistent() {
		return true;
	}

	public T getType() {
		return type;
	}

	public FBEvaluatorEventQueue getEventQueue() {
		return eventQueue;
	}

	public void setEventQueue(final FBEvaluatorEventQueue eventQueue) {
		this.eventQueue = eventQueue;
	}
}
