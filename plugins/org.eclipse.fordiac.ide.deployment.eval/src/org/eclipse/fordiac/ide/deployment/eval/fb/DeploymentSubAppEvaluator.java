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
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.deployment.data.ResourceDeploymentData;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchUtils;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchUtils.SubAppConnectionEndpoint;
import org.eclipse.fordiac.ide.deployment.devResponse.Watches;
import org.eclipse.fordiac.ide.deployment.eval.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class DeploymentSubAppEvaluator extends DeploymentFBNetworkElementEvaluator<SubAppType, TypedSubApp> {

	private final Map<IInterfaceElement, Set<SubAppConnectionEndpoint<?>>> connectionEndpoints;

	private ResourceDeploymentData deploymentData;

	public DeploymentSubAppEvaluator(final SubAppType type, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		super(type, LibraryElementFactory.eINSTANCE.createTypedSubApp(), context, variables, parent);
		connectionEndpoints = getInstance().getInterface().getAllInterfaceElements().collect(Collectors
				.toUnmodifiableMap(Function.identity(), DeploymentSubAppEvaluator::resolveSubappInterfaceEndpoints));
	}

	private static Set<SubAppConnectionEndpoint<?>> resolveSubappInterfaceEndpoints(final IInterfaceElement element) {
		return DeploymentDebugWatchUtils.resolveSubappInterfaceEndpoints(element)
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	@Override
	protected void deployInstance() throws DeploymentException {
		deploymentData = new ResourceDeploymentData(getSharedState().getResource());
		getSharedState().deploy(deploymentData);
	}

	@Override
	protected void deleteInstance() throws DeploymentException {
		getSharedState().delete(deploymentData);
		deploymentData = null;
	}

	@Override
	protected void addWatch(final IInterfaceElement element) throws EvaluatorException {
		for (final SubAppConnectionEndpoint<?> endpoint : connectionEndpoints.get(element)) {
			try {
				getSharedState().addWatch(getResourceRelativeName(endpoint.element()));
			} catch (final DeploymentException e) {
				throw new EvaluatorException(MessageFormat.format(Messages.DeploymentSubAppEvaluator_CannotAddWatch,
						endpoint.element().getQualifiedName(), element.getQualifiedName()), e, this);
			}
		}
	}

	@Override
	protected void removeWatch(final IInterfaceElement element) throws EvaluatorException {
		for (final SubAppConnectionEndpoint<?> endpoint : connectionEndpoints.get(element)) {
			try {
				getSharedState().removeWatch(getResourceRelativeName(endpoint.element()));
			} catch (final DeploymentException e) {
				throw new EvaluatorException(MessageFormat.format(Messages.DeploymentSubAppEvaluator_CannotRemoveWatch,
						endpoint.element().getQualifiedName(), element.getQualifiedName()), e, this);
			}
		}
	}

	@Override
	protected boolean triggerEvent(final Event event) throws EvaluatorException {
		final Set<SubAppConnectionEndpoint<?>> endpoints = connectionEndpoints.get(event);
		if (endpoints.isEmpty()) {
			return false;
		}
		for (final SubAppConnectionEndpoint<?> endpoint : endpoints) {
			try {
				getSharedState().triggerEvent(getResourceRelativeName(endpoint.element()));
			} catch (final DeploymentException e) {
				throw new EvaluatorException(MessageFormat.format(Messages.DeploymentSubAppEvaluator_CannotTriggerEvent,
						endpoint.element().getQualifiedName(), event.getQualifiedName()), e, this);
			}
		}
		return true;
	}

	@Override
	protected void writeVariable(final VarDeclaration varDeclaration) throws EvaluatorException {
		final Value value = getVariables().get(varDeclaration.getName()).getValue();
		for (final SubAppConnectionEndpoint<?> endpoint : connectionEndpoints.get(varDeclaration)) {
			final Value endpointValue = convertWatchValue(endpoint, value);
			final Variable<?> endpointVariable = VariableOperations.newVariable((VarDeclaration) endpoint.element(),
					endpointValue);
			try {
				getSharedState().writeFBParameter(getResourceRelativeName(endpoint.element()),
						endpointVariable.toString(false));
			} catch (final DeploymentException e) {
				throw new EvaluatorException(MessageFormat.format(Messages.DeploymentSubAppEvaluator_CannotWriteValue,
						endpoint.element().getQualifiedName(), varDeclaration.getQualifiedName()), e, this);
			}
		}
	}

	@Override
	protected void updateWatch(final Event event, final Watches watches) throws EvaluatorException {
		connectionEndpoints.get(event).stream().mapToInt(
				endpoint -> parseWatchValue((Event) endpoint.element(), getWatchValue(watches, endpoint.element())))
				.reduce(Integer::sum).ifPresent(value -> updateWatch(event, value));
	}

	@Override
	protected void updateWatch(final VarDeclaration varDeclaration, final Watches watches) throws EvaluatorException {
		connectionEndpoints.get(varDeclaration).stream().findAny()
				.map(endpoint -> convertWatchValue(endpoint,
						parseWatchValue((VarDeclaration) endpoint.element(),
								getWatchValue(watches, endpoint.element()))))
				.ifPresent(value -> updateWatch(varDeclaration, value));
	}

	private static Value convertWatchValue(final SubAppConnectionEndpoint<?> endpoint, final Value value)
			throws EvaluatorException {
		if (endpoint.negate()) {
			return ValueOperations.bitwiseNot(value);
		}
		return value;
	}

	protected ResourceDeploymentData getDeploymentData() {
		return deploymentData;
	}
}
