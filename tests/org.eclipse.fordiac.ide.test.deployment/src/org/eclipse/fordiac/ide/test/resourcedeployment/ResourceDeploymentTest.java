/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.resourcedeployment;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.deployment.data.ResourceDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.ResourceDeploymentData.ParameterData;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.export.forte_ng.algorithm.OtherAlgorithmSupportFactory;
import org.eclipse.fordiac.ide.export.forte_ng.st.StructuredTextSupportFactory;
import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsStandaloneSetup;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup;
import org.eclipse.fordiac.ide.test.model.FordiacProjectLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.osgi.framework.Bundle;

class ResourceDeploymentTest {

	private static final String DeviceName = "FORTE_PC"; //$NON-NLS-1$
	private static final String TestNetwork_NumberOfParams = "TestNumberOfParams"; //$NON-NLS-1$
	private static final String TestNetwork_UniqueParameterDestination = "TestParamsIdentity"; //$NON-NLS-1$
	private static final String TestNetwork_UntypedSubApp = "TestUntypedSubApp"; //$NON-NLS-1$
	private static final String[] resNames = { TestNetwork_NumberOfParams, TestNetwork_UniqueParameterDestination,
			TestNetwork_UntypedSubApp };

	private static EList<Resource> resources = null;

	@BeforeAll
	static void setupResourcesToTest() throws CoreException, IOException {
		setup();
		resources = loadResources();
		assertNotNull(resources);
	}

	@SuppressWarnings("static-method")
	@Test
	void testNumberOfSubappParams() throws DeploymentException {
		final ResourceDeploymentData data = generateDeploymentData(TestNetwork_NumberOfParams);
		assertTrue(data.getParams().isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	void testNumberOfParamsInStackedSubApp() throws DeploymentException {
		final ResourceDeploymentData data = generateDeploymentData(TestNetwork_UniqueParameterDestination);
		assertEquals(4, data.getParams().size());
	}

	/*
	 * Parameters should only be written once inside a bootfile
	 */

	@SuppressWarnings("static-method")
	@ParameterizedTest
	@ValueSource(strings = { TestNetwork_NumberOfParams, TestNetwork_UniqueParameterDestination,
			TestNetwork_UntypedSubApp })
	void testUniqueParameterDestinations(final String networkName) throws DeploymentException {
		final ResourceDeploymentData data = generateDeploymentData(networkName);
		assertFalse(hasDuplicateEntry(data.getParams()));
	}

	@SuppressWarnings("static-method")
	@Test
	void testNumberOfParamsInUntypedSubApp() throws DeploymentException {
		final ResourceDeploymentData data = generateDeploymentData(TestNetwork_UntypedSubApp);
		assertEquals(2, data.getParams().size());
	}

	private static boolean hasDuplicateEntry(final List<ParameterData> parameters) {
		final HashSet<String> set = new HashSet<>();
		for (final ParameterData param : parameters) {
			if (!set.add(getParameterDestination(param))) {
				return true;
			}
		}
		return false;
	}

	private static String getParameterDestination(final ParameterData param) {
		return param.getPrefix() + param.getVar().getQualifiedName();
	}

	private static EList<Resource> loadResources() throws CoreException, IOException {
		final Bundle bundle = Platform.getBundle("org.eclipse.fordiac.ide.test.deployment"); //$NON-NLS-1$
		final Path projectPath = new Path("data/ResourceDeploymentTest"); //$NON-NLS-1$
		final FordiacProjectLoader loader = new FordiacProjectLoader(bundle, projectPath);
		return loader.getAutomationSystem("ResourceDeploymentTest").getDeviceNamed(DeviceName).getResource(); //$NON-NLS-1$
	}

	private static ResourceDeploymentData generateDeploymentData(final String testName) throws DeploymentException {
		final Optional<Resource> res = getResource(testName);
		return res.isPresent() ? new ResourceDeploymentData(res.get()) : null;
	}

	private static Optional<Resource> getResource(final String testName) {
		return resources.stream().filter(res -> res.getName().equalsIgnoreCase(testName)).findAny();
	}

	@SuppressWarnings("unused")
	public static void setup() {
		new DataTypeLibrary();
		GlobalConstantsStandaloneSetup.doSetup();
		STFunctionStandaloneSetup.doSetup();
		STAlgorithmStandaloneSetup.doSetup();
		OtherAlgorithmSupportFactory.register();
		StructuredTextSupportFactory.register();
	}

}
