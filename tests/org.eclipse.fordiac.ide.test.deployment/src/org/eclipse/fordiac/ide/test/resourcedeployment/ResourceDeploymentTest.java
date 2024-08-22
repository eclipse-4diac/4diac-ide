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
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.test.model.FordiacProjectLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Bundle;

class ResourceDeploymentTest {

	private static EList<Resource> resources = null;

	@BeforeAll
	static void setupResourcesToTest() throws CoreException, IOException {
		resources = loadResources();
		assertNotNull(resources);
	}

	@SuppressWarnings("static-method")
	@Test
	void testNumberOfSubappParams() throws DeploymentException {
		final ResourceDeploymentData data = generateDeploymentData("TestNumberOfParams"); //$NON-NLS-1$
		assertTrue(data.getParams().isEmpty());
	}

	@SuppressWarnings("static-method")
	@Test
	void testNumberOfParamsInStackedSubApp() throws DeploymentException {
		// use the same network for this test
		final ResourceDeploymentData data = generateDeploymentData("TestParamsIdentity"); //$NON-NLS-1$
		assertEquals(4, data.getParams().size());
	}

	/*
	 * Parameters should only be written once inside a bootfile
	 */
	@SuppressWarnings("static-method")
	@Test
	void testParamsIndentity() throws DeploymentException {
		final ResourceDeploymentData data = generateDeploymentData("TestParamsIdentity"); //$NON-NLS-1$
		assertFalse(hasDuplicateEntry(data.getParams()));
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
		final Bundle bundle = Platform.getBundle("org.eclipse.fordiac.ide.test.export"); //$NON-NLS-1$
		final Path projectPath = new Path("data/ResourceDeploymentTest"); //$NON-NLS-1$
		final FordiacProjectLoader loader = new FordiacProjectLoader(bundle, projectPath);
		return loader.getAutomationSystem("ResourceDeploymentTest").getDeviceNamed("FORTE_PC").getResource(); //$NON-NLS-1$
	}

	private static ResourceDeploymentData generateDeploymentData(final String testName) throws DeploymentException {
		final Optional<Resource> res = getResource(testName);
		return res.isPresent() ? new ResourceDeploymentData(res.get()) : null;
	}

	private static Optional<Resource> getResource(final String testName) {
		return resources.stream().filter(res -> res.getName().equalsIgnoreCase(testName)).findAny();
	}
}
