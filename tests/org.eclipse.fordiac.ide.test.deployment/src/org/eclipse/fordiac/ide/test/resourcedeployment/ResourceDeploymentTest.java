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
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

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
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.osgi.framework.Bundle;

class ResourceDeploymentTest {

	record TestNetwork(String name, int expectedNumParameters, int expectedNumConnections) {
	}

	private static final String DEVICE_NAME = "FORTE_PC"; //$NON-NLS-1$

	//@formatter:off
	private final List<TestNetwork> networks = List.of(
			new TestNetwork("TestNetwork_Empty", 0, 0), //$NON-NLS-1$
			new TestNetwork("TestNetwork_StackedTypedSubapp", 4, 1), //$NON-NLS-1$
			new TestNetwork("TestNetwork_StackedUntypedSubapp", 2, 0)); //$NON-NLS-1$
	//@formatter:on

	private static EList<Resource> resources = null;

	@BeforeAll
	static void setupResourcesToTest() throws CoreException, IOException {
		setup();
		resources = loadResources();
		assertNotNull(resources);
	}

	@TestFactory
	Stream<DynamicTest> dynamicUniqueParamTest() {
		return generateDynamicTests("Test unique Destinations in: ", //$NON-NLS-1$
				(nw, dep) -> assertFalse(hasDuplicateEntry(dep.getParams())));
	}

	@TestFactory
	Stream<DynamicTest> dynamicNumberOfParametersTest() {
		return generateDynamicTests("Test Number of Params in: ", //$NON-NLS-1$
				(nw, dep) -> assertEquals(nw.expectedNumParameters(), dep.getParams().size()));
	}

	@TestFactory
	Stream<DynamicTest> dynamicNumberOfConnectionsTest() {
		return generateDynamicTests("Test Connection Count in: ", //$NON-NLS-1$
				(nw, dep) -> assertEquals(nw.expectedNumConnections(), dep.getConnections().size()));
	}

	private Stream<DynamicTest> generateDynamicTests(final String testNamePrefix,
			final BiConsumer<TestNetwork, ResourceDeploymentData> assertion) {
		return networks.stream().map(netw -> dynamicTest(testNamePrefix + netw.name(), () -> {
			assertion.accept(netw, generateDeploymentData(netw.name()));
		}));
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
		return loader.getAutomationSystem("ResourceDeploymentTest").getDeviceNamed(DEVICE_NAME).getResource(); //$NON-NLS-1$
	}

	private static ResourceDeploymentData generateDeploymentData(final String testName) throws DeploymentException {
		final Optional<Resource> res = getResource(testName);
		return res.isPresent() ? new ResourceDeploymentData(res.get()) : null;
	}

	private static Optional<Resource> getResource(final String testName) {
		return resources.stream().filter(res -> res.getName().equalsIgnoreCase(testName)).findAny();
	}

	@SuppressWarnings("unused")
	private static void setup() {
		new DataTypeLibrary();
		GlobalConstantsStandaloneSetup.doSetup();
		STFunctionStandaloneSetup.doSetup();
		STAlgorithmStandaloneSetup.doSetup();
		OtherAlgorithmSupportFactory.register();
		StructuredTextSupportFactory.register();
	}

}
