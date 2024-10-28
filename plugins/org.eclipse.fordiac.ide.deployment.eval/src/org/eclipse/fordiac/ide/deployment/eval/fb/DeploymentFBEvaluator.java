/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Port;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.devResponse.Watches;
import org.eclipse.fordiac.ide.deployment.eval.DeploymentEvaluatorSharedState;
import org.eclipse.fordiac.ide.deployment.eval.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorCountingEventQueue;
import org.eclipse.fordiac.ide.model.eval.function.StandardFunctions;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class DeploymentFBEvaluator<T extends FBType> extends FBEvaluator<T> {

	private static final String FAKE_TIME_DEV_PARAM_NAME = "FakeTime"; //$NON-NLS-1$
	private DeploymentEvaluatorSharedState sharedState;
	private FBDeploymentData deploymentData;
	private boolean outputEvent;
	private final Map<Event, Integer> eventCounters = new HashMap<>();

	public DeploymentFBEvaluator(final T type, final Variable<?> context, final Iterable<Variable<?>> variables,
			final Evaluator parent) {
		super(type, context, variables, parent);
	}

	@Override
	public void prepare() {
		if (sharedState == null) {
			sharedState = DeploymentEvaluatorSharedState.fromContext(getType().getTypeLibrary());
			deploymentData = createFBDeploymentData(sharedState.getResource());
			try {
				sharedState.prepare();
				sharedState.createFBInstance(deploymentData);
				sharedState.startFB(deploymentData);
				for (final IInterfaceElement element : deploymentData.getFb().getInterface()
						.getAllInterfaceElements()) {
					sharedState.addWatch(sharedState.getResource(), getResourceRelativeName(element));
				}
				writeVariables(getType().getInterfaceList().getInputVars());
				writeVariables(getType().getInterfaceList().getInOutVars());
				updateWatches(sharedState.readWatches());
				outputEvent = false; // make sure output event is set to false after update
			} catch (final DeploymentException e) {
				throw new EvaluatorException(e.getMessage(), e, this);
			}
		}
	}

	public void cleanup() {
		if (sharedState != null) {
			try {
				for (final IInterfaceElement element : deploymentData.getFb().getInterface()
						.getAllInterfaceElements()) {
					sharedState.removeWatch(sharedState.getResource(), getResourceRelativeName(element));
				}
				sharedState.deleteFB(deploymentData);
				sharedState.getResource().getFBNetwork().getNetworkElements().remove(deploymentData.getFb());
			} catch (final DeploymentException e) {
				throw new EvaluatorException(e.getMessage(), e, this);
			} finally {
				sharedState = null;
			}
		}
	}

	protected FBDeploymentData createFBDeploymentData(final Resource resource) {
		final FB fb = LibraryElementFactory.eINSTANCE.createFB();
		fb.setName(getName() + "_" + UUID.randomUUID().toString()); //$NON-NLS-1$
		fb.setTypeEntry(getType().getTypeEntry());
		fb.setInterface(getType().getInterfaceList().copy());
		resource.getFBNetwork().getNetworkElements().add(fb);
		return new FBDeploymentData("", fb); //$NON-NLS-1$
	}

	@Override
	public void evaluate(final Event event) throws EvaluatorException, InterruptedException {
		final Event instanceEvent = deploymentData.getFb().getInterface().getEvent(event.getName());
		if (instanceEvent == null) {
			throw new EvaluatorException(
					MessageFormat.format(Messages.DeploymentFBEvaluator_NoSuchInstanceEvent, event.getQualifiedName()),
					this);
		}
		prepare();
		try {
			writeVariables(getType().getInterfaceList().getInputVars());
			writeVariables(getType().getInterfaceList().getInOutVars());
			sharedState.writeDeviceParameter(FAKE_TIME_DEV_PARAM_NAME, StandardFunctions.NOW_MONOTONIC().toString());
			sharedState.triggerEvent(sharedState.getResource(), getResourceRelativeName(instanceEvent));
			pollWatches();
			update(getVariables().values());
		} catch (final DeploymentException e) {
			throw new EvaluatorException(e.getMessage(), e, this);
		}
	}

	protected void writeVariables(final Iterable<VarDeclaration> varDeclarations) throws DeploymentException {
		for (final VarDeclaration varDeclaration : varDeclarations) {
			final Variable<?> variable = getVariables().get(varDeclaration.getName());
			sharedState.writeFBParameter(variable.toString(false), deploymentData, varDeclaration);
		}
	}

	protected void pollWatches() throws DeploymentException, InterruptedException {
		do {
			if (Thread.interrupted()) {
				throw new InterruptedException();
			}
			updateWatches(sharedState.readWatches());
		} while (!outputEvent);
		outputEvent = false;
	}

	protected void updateWatches(final Response response) {
		Optional.ofNullable(response.getWatches()).map(Watches::getResources).stream().flatMap(Collection::stream)
				.filter(resource -> sharedState.getResource().getName().equals(resource.getName()))
				.map(org.eclipse.fordiac.ide.deployment.devResponse.Resource::getFbs).flatMap(Collection::stream)
				.filter(fb -> deploymentData.getFb().getName().equals(fb.getName()))
				.map(org.eclipse.fordiac.ide.deployment.devResponse.FB::getPorts).flatMap(Collection::stream)
				.forEachOrdered(this::updateWatch);
	}

	protected void updateWatch(final Port port) {
		if (port.getDataValues().isEmpty()) {
			return;
		}
		final String newValue = port.getDataValues().getLast().getValue();
		final IInterfaceElement element = getType().getInterfaceList().getInterfaceElement(port.getName());
		switch (element) {
		case final VarDeclaration varDeclaration -> updateDataWatch(varDeclaration, newValue);
		case final Event event -> updateEventWatch(event, newValue);
		case null, default -> {
			// do nothing
		}
		}
	}

	protected void updateDataWatch(final VarDeclaration varDeclaration, final String newValue)
			throws IllegalArgumentException {
		final Variable<?> variable = getVariables().get(varDeclaration.getName());
		if (variable != null) {
			variable.setValue(newValue, getType().getTypeLibrary());
		}
	}

	protected void updateEventWatch(final Event event, final String newValue) {
		// parse event count
		final Integer eventCount;
		try {
			eventCount = Integer.valueOf(newValue);
		} catch (final NumberFormatException e) {
			error(Messages.DeploymentFBEvaluator_InvalidEventCounter, e);
			return;
		}
		// has changed?
		if (eventCount.equals(eventCounters.put(event, eventCount))) {
			return;
		}
		// update queue
		if (getEventQueue() instanceof final FBEvaluatorCountingEventQueue countingEventQueue) {
			try {
				countingEventQueue.getCount(event).set(eventCount.intValue());
			} catch (final NumberFormatException e) {
				error(Messages.DeploymentFBEvaluator_InvalidEventCounter, e);
			}
		}
		// is output event?
		if (!event.isIsInput()) {
			outputEvent = true;
		}
	}

	protected String getResourceRelativeName(final IInterfaceElement element) {
		final String qualifiedName = element.getQualifiedName();
		final String resourceName = sharedState.getResource().getName();
		if (qualifiedName.startsWith(resourceName)) {
			return qualifiedName.substring(resourceName.length() + 1);
		}
		return qualifiedName;
	}

	protected DeploymentEvaluatorSharedState getSharedState() {
		return sharedState;
	}

	protected FBDeploymentData getDeploymentData() {
		return deploymentData;
	}

	@Override
	public Map<ICallable, Evaluator> getChildren() {
		return Collections.emptyMap();
	}
}
