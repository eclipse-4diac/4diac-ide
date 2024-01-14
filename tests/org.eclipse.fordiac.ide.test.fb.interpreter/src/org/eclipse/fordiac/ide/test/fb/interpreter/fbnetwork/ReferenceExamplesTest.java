/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter.fbnetwork;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.utils.EMFComparePrettyPrinter;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.compare.EventManagerComparisonUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.FBNetworkTestRunner;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.test.model.FordiacProjectLoader;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.osgi.framework.Bundle;

public class ReferenceExamplesTest {

	private static final String FILE_EXTENSION = "opsem"; //$NON-NLS-1$
	private static Bundle bundle = Platform.getBundle("org.eclipse.fordiac.ide.test.fb.interpreter");  //$NON-NLS-1$
	private static FordiacProjectLoader loader;
	private static IResource[] resources;

	public static IResource[] loadProject() throws CoreException {
		loader = getProject();
		final IProject project = loader.getEclipseProject();
		final IFolder folder = project.getFolder("network_traces"); //$NON-NLS-1$
		if (!folder.exists()) {
			throw new UnsupportedOperationException("could not find traces in project"); //$NON-NLS-1$
		}
		resources = folder.members(false);
		return resources;
	}

	public static IResource[] getTraceNames() throws CoreException {
		loadProject();
		return Stream.of(resources)
				.filter(res -> FILE_EXTENSION.equals(res.getFileExtension()))
				.toArray(IResource[]::new);
	}

	@SuppressWarnings("static-method")
	@ParameterizedTest
	@MethodSource("getTraceNames")
	public void runTest(final IResource res) {
		// print what test it is
		System.out.println("TEST CASE " + res.getName()); //$NON-NLS-1$
		System.out.println("*****************************"); //$NON-NLS-1$
		System.out.println();

		// load test
		final String[] request = res.getName().split("\\."); //$NON-NLS-1$
		// first element: system
		final AutomationSystem system = loader.getAutomationSystem(request[0]);
		// second element: application
		final Application app = system.getApplicationNamed(request[1]);
		// third element: subapplication name
		final SubApp testCase = (SubApp) app.getFBNetwork().getElementNamed(request[2]);
		// fourth element: FB to trigger
		final FBNetworkElement initialFb = testCase.getSubAppNetwork().getElementNamed(request[3]);
		// fifth element: pin to trigger
		final Event eventPin = (Event) initialFb.getInterfaceElement(request[4]);
		// sixth element must be opsem
		assertTrue(request[5].equals(FILE_EXTENSION)); // $NON-NLS-1$

		// create and execute event manager
		final EventManager eventManager = FBNetworkTestRunner.runFBNetworkTestManager(testCase.getSubAppNetwork(),
				eventPin);

		// prepare for emf compare
		final Resource resRight = EventManagerUtils.addResourceToManager(eventManager,
				URI.createURI("platform:/resource/ReferenceExamples/" + res.getName())); //$NON-NLS-1$ //$NON-NLS-2$

		final Resource resLeft = EventManagerUtils
				.loadResourceNotOnDemand (URI.createPlatformResourceURI(res.getFullPath().toString(), true));

		final Comparison eventComparison = EventManagerComparisonUtils.compareEventManager(resLeft, resRight);
		EMFComparePrettyPrinter.printComparison(eventComparison, System.out);
		assertTrue(eventComparison.getDifferences().isEmpty());

	}

	static FordiacProjectLoader getProject() {
		final Path projectPath = new Path("data/ReferenceExamples"); //$NON-NLS-1$
		FordiacProjectLoader loader;
		try {
			loader = new FordiacProjectLoader(bundle, projectPath);
		} catch (CoreException | IOException e) {
			throw new UnsupportedOperationException("could not find project"); //$NON-NLS-1$
		}
		return loader;
	}

}
