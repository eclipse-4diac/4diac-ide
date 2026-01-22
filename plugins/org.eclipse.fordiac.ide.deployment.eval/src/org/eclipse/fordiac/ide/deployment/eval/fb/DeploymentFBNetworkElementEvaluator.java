/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.eval.fb;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchUtils;
import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.devResponse.Port;
import org.eclipse.fordiac.ide.deployment.devResponse.Watches;
import org.eclipse.fordiac.ide.deployment.eval.DeploymentEvaluatorSharedState;
import org.eclipse.fordiac.ide.deployment.eval.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorCountingEventQueue;
import org.eclipse.fordiac.ide.model.eval.function.StandardFunctions;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public abstract class DeploymentFBNetworkElementEvaluator<T extends FBType, I extends BlockFBNetworkElement>
		extends FBEvaluator<T> {

	private static final String FAKE_TIME_DEV_PARAM_NAME = "FakeTime"; //$NON-NLS-1$

	private final I instance;
	private final Map<Event, AtomicInteger> eventCounters;

	private DeploymentEvaluatorSharedState sharedState;
	private boolean outputEvent;

	protected DeploymentFBNetworkElementEvaluator(final T type, final I instance, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		super(type, context, variables, parent);
		instance.setName(getName() + "_" + UUID.randomUUID().toString()); //$NON-NLS-1$
		instance.setTypeEntry(getType().getTypeEntry());
		instance.setInterface(getType().getInterfaceList().instanceCopy());
		this.instance = instance;
		this.eventCounters = instance.getInterface().getEventOutputs().stream()
				.collect(Collectors.toUnmodifiableMap(Function.identity(), unused -> new AtomicInteger()));
	}

	@Override
	public void prepare() {
		if (sharedState != null) {
			return;
		}
		try {
			sharedState = DeploymentEvaluatorSharedState.fromContext(getType().getTypeLibrary());
			sharedState.getResource().getFBNetwork().getNetworkElements().add(instance);
			sharedState.prepare();
			deployInstance();
			writeVariables();
			addWatches();
			updateWatches(sharedState.readWatches().getWatches());
			outputEvent = false; // make sure output event is set to false after update
		} catch (final DeploymentException e) {
			throw new EvaluatorException(e.getMessage(), e, this);
		}
	}

	@Override
	public void cleanup() {
		if (sharedState == null) {
			return;
		}
		try {
			removeWatches();
			deleteInstance();
			sharedState.getResource().getFBNetwork().getNetworkElements().remove(instance);
		} catch (final DeploymentException e) {
			throw new EvaluatorException(e.getMessage(), e, this);
		} finally {
			sharedState = null;
			outputEvent = false;
		}
	}

	@Override
	public void evaluate(final Event event) throws EvaluatorException, InterruptedException {
		final Event instanceEvent = getInstanceEvent(event);
		prepare();
		try {
			writeVariables();
			sharedState.writeDeviceParameter(FAKE_TIME_DEV_PARAM_NAME, StandardFunctions.NOW_MONOTONIC().toString());
			if (triggerEvent(instanceEvent)) {
				pollWatches();
			}
			update(getVariables().values());
		} catch (final DeploymentException e) {
			throw new EvaluatorException(e.getMessage(), e, this);
		}
	}

	protected void pollWatches() throws DeploymentException, InterruptedException {
		do {
			if (Thread.interrupted()) {
				throw new InterruptedException();
			}
			updateWatches(sharedState.readWatches().getWatches());
		} while (!outputEvent);
		outputEvent = false;
	}

	protected void addWatches() throws EvaluatorException {
		instance.getInterface().getEventOutputs().forEach(this::addWatch);
		instance.getInterface().getOutputVars().forEach(this::addWatch);
		instance.getInterface().getOutMappedInOutVars().forEach(this::addWatch);
	}

	protected void removeWatches() throws EvaluatorException {
		instance.getInterface().getEventOutputs().forEach(this::removeWatch);
		instance.getInterface().getOutputVars().forEach(this::removeWatch);
		instance.getInterface().getOutMappedInOutVars().forEach(this::removeWatch);
	}

	protected void updateWatches(final Watches watches) throws EvaluatorException {
		if (watches == null) {
			return;
		}
		instance.getInterface().getEventOutputs().forEach(e -> updateWatch(e, watches));
		instance.getInterface().getOutputVars().forEach(v -> updateWatch(v, watches));
		instance.getInterface().getOutMappedInOutVars().forEach(v -> updateWatch(v, watches));
	}

	protected void writeVariables() {
		instance.getInterface().getInputVars().forEach(this::writeVariable);
		instance.getInterface().getInOutVars().forEach(this::writeVariable);
	}

	protected abstract void deployInstance() throws DeploymentException;

	protected abstract void deleteInstance() throws DeploymentException;

	protected abstract void addWatch(IInterfaceElement element) throws EvaluatorException;

	protected abstract void removeWatch(IInterfaceElement element) throws EvaluatorException;

	protected abstract void updateWatch(final Event event, final Watches watches) throws EvaluatorException;

	protected abstract void updateWatch(final VarDeclaration varDeclaration, final Watches watches)
			throws EvaluatorException;

	protected abstract boolean triggerEvent(Event event) throws EvaluatorException;

	protected abstract void writeVariable(VarDeclaration varDeclaration) throws EvaluatorException;

	protected void updateWatch(final Event event, final int eventCount) throws EvaluatorException {
		final Event typeEvent = getTypeEvent(event);
		// has changed?
		if (eventCount == eventCounters.get(event).getAndSet(eventCount)) {
			return;
		}
		// update queue
		if (getEventQueue() instanceof final FBEvaluatorCountingEventQueue countingEventQueue) {
			countingEventQueue.getCount(typeEvent).set(eventCount);
		}
		// is output event?
		if (!event.isIsInput()) {
			outputEvent = true;
		}
	}

	protected void updateWatch(final VarDeclaration varDeclaration, final Value newValue) throws EvaluatorException {
		final Variable<?> variable = getVariables().get(varDeclaration.getName());
		if (variable != null) {
			variable.setValue(newValue);
		}
	}

	protected int parseWatchValue(final Event event, final String value) throws EvaluatorException {
		try {
			return Integer.parseInt(value);
		} catch (final Exception e) {
			throw new EvaluatorException(MessageFormat.format(
					Messages.DeploymentFBNetworkElementEvaluator_InvalidWatchValue, event.getQualifiedName()), e, this);
		}
	}

	protected Value parseWatchValue(final VarDeclaration varDeclaration, final String value) throws EvaluatorException {
		try {
			return VariableOperations.newVariable(varDeclaration, value).getValue();
		} catch (final Exception e) {
			throw new EvaluatorException(
					MessageFormat.format(Messages.DeploymentFBNetworkElementEvaluator_InvalidWatchValue,
							varDeclaration.getQualifiedName()),
					e, this);
		}
	}

	protected String getWatchValue(final Watches watches, final IInterfaceElement element) throws EvaluatorException {
		return findWatchValue(watches, element).orElseThrow(() -> new EvaluatorException(MessageFormat
				.format(Messages.DeploymentFBNetworkElementEvaluator_NoWatchValue, element.getQualifiedName()), this));
	}

	protected Optional<String> findWatchValue(final Watches watches, final IInterfaceElement element) {
		return findPort(watches, element).map(Port::getDataValues).stream().flatMap(Collection::stream)
				.map(Data::getValue).findFirst();
	}

	protected Optional<Port> findPort(final Watches watches, final IInterfaceElement element) {
		return findFB(watches, element.getBlockFBNetworkElement())
				.map(org.eclipse.fordiac.ide.deployment.devResponse.FB::getPorts).stream().flatMap(Collection::stream)
				.filter(port -> element.getName().equals(port.getName())).findAny();
	}

	protected Optional<org.eclipse.fordiac.ide.deployment.devResponse.FB> findFB(final Watches watches,
			final FBNetworkElement element) {
		return findResource(watches).map(org.eclipse.fordiac.ide.deployment.devResponse.Resource::getFbs).stream()
				.flatMap(Collection::stream).filter(fb -> getResourceRelativeName(element).equals(fb.getName()))
				.findAny();
	}

	protected Optional<org.eclipse.fordiac.ide.deployment.devResponse.Resource> findResource(final Watches watches) {
		return watches.getResources().stream()
				.filter(resource -> sharedState.getResource().getName().equals(resource.getName())).findAny();
	}

	protected Event getTypeEvent(final Event instanceEvent) {
		final Event typeEvent = getType().getInterfaceList().getEvent(instanceEvent.getName());
		if (typeEvent == null) {
			throw new EvaluatorException(
					MessageFormat.format(Messages.DeploymentFBNetworkElementEvaluator_NoSuchTypeEvent,
							instanceEvent.getQualifiedName()),
					this);
		}
		return typeEvent;
	}

	protected Event getInstanceEvent(final Event typeEvent) {
		final Event instanceEvent = instance.getInterface().getEvent(typeEvent.getName());
		if (instanceEvent == null) {
			throw new EvaluatorException(
					MessageFormat.format(Messages.DeploymentFBNetworkElementEvaluator_NoSuchInstanceEvent,
							typeEvent.getQualifiedName()),
					this);
		}
		return instanceEvent;
	}

	protected String getResourceRelativeName(final INamedElement element) {
		return DeploymentDebugWatchUtils.getResourceRelativeName(element, sharedState.getResource());
	}

	protected DeploymentEvaluatorSharedState getSharedState() {
		return sharedState;
	}

	@Override
	public Map<ICallable, Evaluator> getChildren() {
		return Collections.emptyMap();
	}

	public I getInstance() {
		return instance;
	}
}