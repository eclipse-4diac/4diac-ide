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

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DeviceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class DeploymentEvaluatorConfigurationBuilder {

	public static final String RESOURCE_NAME_PREFIX = "EVAL_RES_"; //$NON-NLS-1$
	private static final String MGR_ID = "MGR_ID"; //$NON-NLS-1$

	private DeviceTypeEntry deviceType;
	private ResourceTypeEntry resourceType;
	private String deviceProfile;
	private String deviceMgrID;

	public DeploymentEvaluatorConfigurationBuilder(final DeviceTypeEntry deviceType,
			final ResourceTypeEntry resourceType) {
		this.deviceType = deviceType;
		this.resourceType = resourceType;
	}

	public static final DeploymentEvaluatorConfigurationBuilder withDefaults(final TypeLibrary typeLibrary) {
		return fromContext(Collections.emptyMap(), typeLibrary);
	}

	public static final DeploymentEvaluatorConfigurationBuilder fromContext(final Map<String, Object> context,
			final TypeLibrary typeLibrary) {
		return new DeploymentEvaluatorConfigurationBuilder(
				Objects.requireNonNull(DeploymentEvaluatorConfiguration.getDeviceType(context, typeLibrary),
						"No such device type"), //$NON-NLS-1$
				Objects.requireNonNull(DeploymentEvaluatorConfiguration.getResourceType(context, typeLibrary),
						"No such resource type")) //$NON-NLS-1$
				.setDeviceProfile(DeploymentEvaluatorConfiguration.getDeviceProfile(context))
				.setDeviceMgrID(DeploymentEvaluatorConfiguration.getMgrID(context));
	}

	public Resource build() {
		final Resource resource = LibraryElementFactory.eINSTANCE.createResource();
		resource.setName(RESOURCE_NAME_PREFIX + UUID.randomUUID().toString());
		resource.setTypeEntry(resourceType);
		if (resourceType != null) {
			resource.getVarDeclarations().addAll(EcoreUtil.copyAll(resourceType.getType().getVarDeclaration()));
		}
		resource.setFBNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());
		resource.setDevice(createDevice());
		return resource;
	}

	public Device createDevice() {
		final Device device = LibraryElementFactory.eINSTANCE.createDevice();
		device.setName(deviceType.getTypeName());
		device.setTypeEntry(deviceType);
		device.setProfile(deviceProfile);
		device.getVarDeclarations().addAll(EcoreUtil.copyAll(deviceType.getType().getVarDeclaration()));
		if (deviceMgrID != null) {
			setValue(device, MGR_ID, deviceMgrID);
		}
		return device;
	}

	protected static void setValue(final Device device, final String name, final String value) {
		final VarDeclaration varDeclaration = getOrCreateVariable(device, name);
		if (varDeclaration.getValue() == null) {
			varDeclaration.setValue(LibraryElementFactory.eINSTANCE.createValue());
		}
		varDeclaration.getValue().setValue(value);
	}

	protected static VarDeclaration getOrCreateVariable(final Device device, final String name) {
		return device.getVarDeclarations().stream().filter(varDeclaration -> name.equals(varDeclaration.getName()))
				.findAny().orElseGet(() -> {
					final VarDeclaration varDeclaration = LibraryElementFactory.eINSTANCE.createVarDeclaration();
					device.getVarDeclarations().add(varDeclaration);
					return varDeclaration;
				});
	}

	public DeviceTypeEntry getDeviceType() {
		return deviceType;
	}

	public DeploymentEvaluatorConfigurationBuilder setDeviceType(final DeviceTypeEntry deviceType) {
		this.deviceType = Objects.requireNonNull(deviceType);
		return this;
	}

	public ResourceTypeEntry getResourceType() {
		return resourceType;
	}

	public DeploymentEvaluatorConfigurationBuilder setResourceType(final ResourceTypeEntry resourceType) {
		this.resourceType = Objects.requireNonNull(resourceType);
		return this;
	}

	public String getDeviceProfile() {
		return deviceProfile;
	}

	public DeploymentEvaluatorConfigurationBuilder setDeviceProfile(final String deviceProfile) {
		this.deviceProfile = deviceProfile;
		return this;
	}

	public String getDeviceMgrID() {
		return deviceMgrID;
	}

	public DeploymentEvaluatorConfigurationBuilder setDeviceMgrID(final String deviceMgrID) {
		this.deviceMgrID = deviceMgrID;
		return this;
	}
}
