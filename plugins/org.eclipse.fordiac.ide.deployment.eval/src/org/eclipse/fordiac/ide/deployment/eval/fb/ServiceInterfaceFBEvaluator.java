/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.devResponse.Port;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.eval.DeploymentEvaluatorSharedState;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringFactory;
import org.eclipse.fordiac.ide.monitoring.MonitoringManagerUtils;

public class ServiceInterfaceFBEvaluator extends FBEvaluator<ServiceInterfaceFBType> {

	private DeploymentEvaluatorSharedState sharedState;
	private FBDeploymentData deploymentData;
	private Map<String, MonitoringElement> monitoringElements;
	private boolean outputEvent;

	public ServiceInterfaceFBEvaluator(final ServiceInterfaceFBType type, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		super(type, context, variables, parent);
	}

	@Override
	public void prepare() {
		if (sharedState == null) {
			sharedState = DeploymentEvaluatorSharedState.fromContext(getType().getTypeLibrary());
			deploymentData = createFBDeploymentData(sharedState.getResource());
			monitoringElements = createMonitoringElements(deploymentData.getFb());
			try {
				sharedState.prepare();
				sharedState.createFBInstance(deploymentData);
				sharedState.startFB(deploymentData);
				for (final MonitoringBaseElement element : monitoringElements.values()) {
					sharedState.getDeviceManagementInteractor().addWatch(element);
				}
				updateWatches(sharedState.readWatches());
			} catch (final DeploymentException e) {
				throw new RuntimeException(e);
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

	protected Map<String, MonitoringElement> createMonitoringElements(final FBNetworkElement fbne) {
		return fbne.getInterface().getAllInterfaceElements().stream()
				.collect(Collectors.toMap(INamedElement::getName, this::createMonitoringElement));
	}

	protected MonitoringElement createMonitoringElement(final IInterfaceElement interfaceElement) {
		final MonitoringElement monitoringElement = MonitoringFactory.eINSTANCE.createMonitoringElement();
		monitoringElement.setPort(MonitoringManagerUtils.createPortElement(interfaceElement));
		return monitoringElement;
	}

	@Override
	public void evaluate(final Event event) throws EvaluatorException {
		prepare();
		try {
			writeVariables(getType().getInterfaceList().getInputVars());
			writeVariables(getType().getInterfaceList().getInOutVars());
			sharedState.triggerEvent(monitoringElements.get(event.getName()));
			pollWatches();
		} catch (final DeploymentException e) {
			throw new EvaluatorException(e.getMessage(), e, this);
		}
	}

	protected void writeVariables(final Iterable<VarDeclaration> varDeclarations) throws DeploymentException {
		for (final VarDeclaration varDeclaration : varDeclarations) {
			final Variable<?> variable = getVariables().get(varDeclaration.getName());
			sharedState.writeFBParameter(variable.toString(), deploymentData, varDeclaration);
		}
	}

	protected void pollWatches() throws DeploymentException {
		do {
			updateWatches(sharedState.readWatches());
		} while (!outputEvent);
		outputEvent = false;
	}

	protected void updateWatches(final Response response) {
		response.getWatches().getResources().stream()
				.filter(resource -> sharedState.getResource().getName().equals(resource.getName()))
				.map(org.eclipse.fordiac.ide.deployment.devResponse.Resource::getFbs).flatMap(Collection::stream)
				.filter(fb -> deploymentData.getFb().getName().equals(fb.getName()))
				.map(org.eclipse.fordiac.ide.deployment.devResponse.FB::getPorts).flatMap(Collection::stream)
				.forEachOrdered(this::updateWatch);
	}

	protected void updateWatch(final Port port) {
		final MonitoringElement monitoringElement = monitoringElements.get(port.getName());
		if (monitoringElement != null) {
			final IInterfaceElement interfaceElement = monitoringElement.getPort().getInterfaceElement();
			if (interfaceElement instanceof final VarDeclaration varDeclaration) {
				final Variable<?> variable = getVariables().get(varDeclaration.getName());
				if (variable != null) {
					port.getDataValues().stream().map(Data::getValue).forEachOrdered(variable::setValue);
				}
			} else if (interfaceElement instanceof final Event event) {
				final String oldValue = monitoringElement.getCurrentValue();
				port.getDataValues().stream().map(Data::getValue).forEachOrdered(monitoringElement::setCurrentValue);
				if (!event.isIsInput() && !oldValue.equals(monitoringElement.getCurrentValue())) {
					outputEvent = true;
				}
			}
		}
	}

	@Override
	public Map<ICallable, Evaluator> getChildren() {
		return Collections.emptyMap();
	}
}
