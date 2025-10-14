/*******************************************************************************
 * Copyright (c) 2023, 2025 Martin Erich Jobst
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

import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.ParameterDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.ResourceDeploymentData;
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

	protected DeploymentEvaluatorSharedState(final DeploymentEvaluatorConfigurationBuilder builder) {
		resource = builder.createResource();
		deviceManagementInteractor = Objects.requireNonNull(
				DeviceManagementInteractorFactory.INSTANCE.getDeviceManagementInteractor(
						Objects.requireNonNull(resource.getDevice(), "Resource not in device")), //$NON-NLS-1$
				"No valid device management interactor for profile " + resource.getDevice().getProfile()); //$NON-NLS-1$
		if (builder.isTrace()) {
			deviceManagementInteractor.addDeploymentListener(new DeploymentEvaluatorTraceProxy());
		}
	}

	public static DeploymentEvaluatorSharedState fromContext(final TypeLibrary typeLibrary) {
		return (DeploymentEvaluatorSharedState) AbstractEvaluator.getSharedResources().computeIfAbsent(
				SHARED_STATE_NAME, unused -> new DeploymentEvaluatorSharedState(DeploymentEvaluatorConfigurationBuilder
						.fromContext(AbstractEvaluator.currentContext(), typeLibrary)));
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

	public void deploy(final FBDeploymentData deploymentData) throws DeploymentException {
		deviceManagementInteractor.createFBInstance(deploymentData, resource);
		deviceManagementInteractor.startFB(resource, deploymentData);
	}

	public void deploy(final ResourceDeploymentData deploymentData) throws DeploymentException {
		for (final FBDeploymentData fb : deploymentData.getFbs()) {
			deviceManagementInteractor.createFBInstance(fb, deploymentData.getRes());
		}
		for (final ParameterDeploymentData param : deploymentData.getParams()) {
			deviceManagementInteractor.writeFBParameter(deploymentData.getRes(), param.value(),
					new FBDeploymentData(param.prefix(), param.variable().getBlockFBNetworkElement()),
					param.variable());
		}
		for (final ConnectionDeploymentData connection : deploymentData.getConnections()) {
			deviceManagementInteractor.createConnection(deploymentData.getRes(), connection);
		}
		for (final FBDeploymentData fb : deploymentData.getFbs()) {
			deviceManagementInteractor.startFB(deploymentData.getRes(), fb);
		}
	}

	public void delete(final ResourceDeploymentData deploymentData) throws DeploymentException {
		for (final ConnectionDeploymentData connection : deploymentData.getConnections()) {
			deviceManagementInteractor.deleteConnection(deploymentData.getRes(), connection);
		}
		for (final FBDeploymentData fb : deploymentData.getFbs()) {
			deviceManagementInteractor.deleteFB(deploymentData.getRes(), fb);
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

	public void addWatch(final String name) throws DeploymentException {
		deviceManagementInteractor.addWatch(resource, name);
	}

	public void removeWatch(final String name) throws DeploymentException {
		deviceManagementInteractor.removeWatch(resource, name);
	}

	public Response readWatches() throws DeploymentException {
		return deviceManagementInteractor.readWatches();
	}

	public void triggerEvent(final String name) throws DeploymentException {
		deviceManagementInteractor.triggerEvent(resource, name);
	}

	public void writeFBParameter(final String value, final FBDeploymentData deploymentData,
			final VarDeclaration varDeclaration) throws DeploymentException {
		deviceManagementInteractor.writeFBParameter(resource, value, deploymentData, varDeclaration);
	}

	public void writeFBParameter(final String name, final String value) throws DeploymentException {
		deviceManagementInteractor.writeFBParameter(resource, name, value);
	}

	public void writeDeviceParameter(final String name, final String value) throws DeploymentException {
		deviceManagementInteractor.writeDeviceParameter(resource.getDevice(), name, value);
	}

	public void createConnection(final ConnectionDeploymentData connection) throws DeploymentException {
		deviceManagementInteractor.createConnection(resource, connection);
	}

	public void deleteConnection(final ConnectionDeploymentData connection) throws DeploymentException {
		deviceManagementInteractor.deleteConnection(resource, connection);
	}

	public Resource getResource() {
		return resource;
	}

	public IDeviceManagementInteractor getDeviceManagementInteractor() {
		return deviceManagementInteractor;
	}
}
