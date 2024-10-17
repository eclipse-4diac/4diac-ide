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
package org.eclipse.fordiac.ide.deployment.eval;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;

import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.eval.AbstractEvaluator;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class DeploymentEvaluatorSharedState implements Closeable {

	public static final String SHARED_STATE_NAME = DeploymentEvaluatorSharedState.class.getName();

	private final Resource resource;
	private final IDeviceManagementInteractor deviceManagementInteractor;

	protected DeploymentEvaluatorSharedState(final Resource resource) {
		this.resource = Objects.requireNonNull(resource, "Resource must not be null"); //$NON-NLS-1$
		deviceManagementInteractor = Objects.requireNonNull(
				DeviceManagementInteractorFactory.INSTANCE.getDeviceManagementInteractor(
						Objects.requireNonNull(resource.getDevice(), "Resource not in device")), //$NON-NLS-1$
				"No valid device management interactor for profile " + resource.getDevice().getProfile()); //$NON-NLS-1$
	}

	public static DeploymentEvaluatorSharedState fromContext(final TypeLibrary typeLibrary) {
		return (DeploymentEvaluatorSharedState) AbstractEvaluator.getSharedResources().computeIfAbsent(
				SHARED_STATE_NAME, unused -> new DeploymentEvaluatorSharedState(DeploymentEvaluatorConfigurationBuilder
						.fromContext(AbstractEvaluator.currentContext(), typeLibrary).build()));
	}

	public void prepare() throws DeploymentException {
		if (!deviceManagementInteractor.isConnected()) {
			deviceManagementInteractor.connect();
			deviceManagementInteractor.createResource(resource);
			deviceManagementInteractor.startResource(resource);
		}
	}

	@Override
	public void close() throws IOException {
		try {
			if (deviceManagementInteractor.isConnected()) {
				deviceManagementInteractor.deleteResource(resource.getName());
				deviceManagementInteractor.disconnect();
			}
		} catch (final DeploymentException e) {
			throw new IOException(e);
		}
	}

	public void createFBInstance(final FBDeploymentData deploymentData) throws DeploymentException {
		deviceManagementInteractor.createFBInstance(deploymentData, resource);
	}

	public void startFB(final FBDeploymentData deploymentData) throws DeploymentException {
		deviceManagementInteractor.startFB(resource, deploymentData);
	}

	public void deleteFB(final FBDeploymentData deploymentData) throws DeploymentException {
		deviceManagementInteractor.deleteFB(resource, deploymentData);
	}

	public void addWatch(final Resource resource, final String name) throws DeploymentException {
		deviceManagementInteractor.addWatch(resource, name);
	}

	public void removeWatch(final Resource resource, final String name) throws DeploymentException {
		deviceManagementInteractor.removeWatch(resource, name);
	}

	public Response readWatches() throws DeploymentException {
		return deviceManagementInteractor.readWatches();
	}

	public void triggerEvent(final Resource resource, final String name) throws DeploymentException {
		deviceManagementInteractor.triggerEvent(resource, name);
	}

	public void writeFBParameter(final String value, final FBDeploymentData deploymentData,
			final VarDeclaration varDeclaration) throws DeploymentException {
		deviceManagementInteractor.writeFBParameter(resource, value, deploymentData, varDeclaration);
	}

	public void writeDeviceParameter(final String name, final String value) throws DeploymentException {
		deviceManagementInteractor.writeDeviceParameter(resource.getDevice(), name, value);
	}

	public Resource getResource() {
		return resource;
	}

	public IDeviceManagementInteractor getDeviceManagementInteractor() {
		return deviceManagementInteractor;
	}
}
