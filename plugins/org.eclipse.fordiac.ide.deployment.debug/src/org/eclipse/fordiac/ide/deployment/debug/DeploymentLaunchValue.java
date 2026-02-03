/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug;

import static org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchUtils.getDevice;
import static org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchUtils.getResource;
import static org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchUtils.getResourceRelativeName;
import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.newVariable;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.fordiac.ide.deployment.data.DeviceDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.ResourceDeploymentData;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchUtils;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchUtils.SubAppConnectionEndpoint;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class DeploymentLaunchValue {
	private final String name;
	private final Optional<VarDeclaration> refElement;
	private String value;

	public DeploymentLaunchValue(final String name, final Optional<VarDeclaration> refElement, final String value) {
		this.name = Objects.requireNonNull(name);
		this.refElement = Objects.requireNonNull(refElement);
		this.value = Objects.requireNonNullElse(value, ""); //$NON-NLS-1$
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = Objects.requireNonNullElse(value, ""); //$NON-NLS-1$
	}

	public String getName() {
		return name;
	}

	public Optional<VarDeclaration> getRefElement() {
		return refElement;
	}

	public void applyTo(final Collection<DeviceDeploymentData> deploymentData) throws DeploymentException {
		if (refElement.isEmpty() || value.isEmpty()) {
			return;
		}

		final Optional<ResourceDeploymentData> resourceData = findDeviceData(deploymentData)
				.flatMap(this::findResourceData);
		if (resourceData.isEmpty()) {
			return;
		}

		try {
			final Value refValue = newVariable(refElement.get(), value).getValue();
			DeploymentDebugWatchUtils.resolveSubappInterfaceEndpoints(refElement.get()).forEachOrdered(endpoint -> {
				final Value paramValue = convertValue(endpoint, refValue);
				final String paramName = getResourceRelativeName(endpoint.element(), getResource(endpoint.element()));
				final Variable<?> paramVariable = newVariable(endpoint.element(), paramValue);
				resourceData.get().addParameter(paramName, paramVariable.toString(false));
			});
		} catch (final EvaluatorException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentLaunchValue_Error, name), e);
		}
	}

	private Optional<DeviceDeploymentData> findDeviceData(final Collection<DeviceDeploymentData> deploymentData) {
		final Device device = getDevice(refElement.get());
		return deploymentData.stream().filter(data -> data.getDevice() == device).findAny();
	}

	private Optional<ResourceDeploymentData> findResourceData(final DeviceDeploymentData deploymentData) {
		final Resource resource = getResource(refElement.get());
		return deploymentData.getResData().stream().filter(data -> data.getRes() == resource).findAny();
	}

	private static Value convertValue(final SubAppConnectionEndpoint<?> endpoint, final Value value)
			throws EvaluatorException {
		if (endpoint.negate()) {
			return ValueOperations.bitwiseNot(value);
		}
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DeploymentLaunchValue other = (DeploymentLaunchValue) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return String.format("%s [name=%s, value=%s]", getClass().getName(), name, value); //$NON-NLS-1$
	}
}