/*******************************************************************************
 * Copyright (c) 2021, 2022 Johannes Kepler University Linz and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Antonio Garmendia, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *  Paul Pavlicek - cleanup
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter.fbnetwork;

import static org.eclipse.fordiac.ide.fb.interpreter.mm.FBNetworkTestRunner.runFBNetworkTestManager;
import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.utils.EMFComparePrettyPrinter;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerComparisonUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.FBNetworkTestRunner.IllegalTraceException;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.test.fb.interpreter.infra.AbstractInterpreterTest;

/** small fb network consisting of e_sr, e_switch, and e_ctud */
public class SimpleEventConnectionTest extends AbstractInterpreterTest {

	private static final String INITAIL_FB = "E_SPLIT"; //$NON-NLS-1$
	private static final String INITIAL_PIN = "EI"; //$NON-NLS-1$

	@Override
	public void test() throws IllegalTraceException {

		final FBNetwork network = loadFbNetwork("ReferenceExamples", "ReferenceExamples", "EventConnections"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertNotNull(network);

		final SubApp subApp = network.getSubAppNamed("Ex1a"); //$NON-NLS-1$

		final EventManager eventManager = runFBNetworkTestManager(subApp.getSubAppNetwork(), INITAIL_FB,
				INITIAL_PIN);

		final Resource resRight = EventManagerUtils.addResourceToManager(eventManager,
				URI.createURI("platform:/resource/ReferenceExamples/SimpleEventConnectionTest.xmi"));

		final URI uri = URI.createPlatformResourceURI(
				subApp.getFbNetwork().getAutomationSystem().getTypeEntry().getFile().getProject()
				.getFile("network_traces/ReferenceExamples.EventConnections.Ex1a.E_SPLIT.EI.xmi").getFullPath()
				.toString(),
				true);

		final Resource resLeft = EventManagerUtils.loadResource(URI.createPlatformResourceURI(
				subApp.getFbNetwork().getAutomationSystem().getTypeEntry().getFile().getProject()
				.getFile("network_traces/ReferenceExamples.EventConnections.Ex1a.E_SPLIT.EI.xmi").getFullPath()
				.toString(),
				true));

		final Comparison eventComparison = EventManagerComparisonUtils.compareEventManager(resLeft, resRight);
		EMFComparePrettyPrinter.printComparison(eventComparison, System.out);
		System.out.println("Number of Differences: " + eventComparison.getDifferences().size());
		System.out.println("Finish Comparison");

		// final List<FBTransactionBuilder> expectedTs = new ArrayList<>();
		// expectedTs.add(
		// new FBTransactionBuilder("E_SPLIT.EI").addOutputEvent("E_SPLIT.EO1").addOutputEvent("E_SPLIT.EO2"));
		// //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
		// expectedTs.add(new FBTransactionBuilder("E_REND.EI1")); //$NON-NLS-1$
		// expectedTs.add(new FBTransactionBuilder("E_REND.EI2").addOutputEvent("E_REND.EO")); //$NON-NLS-1$
		// //$NON-NLS-2$

		// checkNetworkResults(returnedTransactions, expectedTs);

	}

}
