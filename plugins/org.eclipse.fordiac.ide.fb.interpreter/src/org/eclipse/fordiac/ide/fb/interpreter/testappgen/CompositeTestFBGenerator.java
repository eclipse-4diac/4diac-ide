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
 * Melanie Winter - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.testappgen;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.AbstractCompositeFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestSuite;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class CompositeTestFBGenerator extends AbstractCompositeFBGenerator {
	private final TestSuite testSuite;

	private FB muxFB;
	private FB runAllFB;
	private final List<FBType> blocksToAdd;
	private final List<FB> toTestFBs = new ArrayList<>();
	private final List<FB> testFBs = new ArrayList<>();
	private final List<FB> matchFBs = new ArrayList<>();

	public CompositeTestFBGenerator(final FBType type, final TestSuite testSuite, final List<FBType> blocksToAdd) {
		super(type);
		this.testSuite = testSuite;
		this.blocksToAdd = blocksToAdd;
	}

	public CompositeFBType generateCompositeFB() {
		createCompositeFB();
		return compositeFB;
	}

	@Override
	protected void addFBsToNetwork() {
		final FBNetwork net = LibraryElementFactory.eINSTANCE.createFBNetwork();
		compositeFB.setFBNetwork(net);
		// each testCase needs a matchFB, testFB and toTestFB
		for (int i = 0; i < testSuite.getTestCases().size(); i++) {
			for (int j = 0; j < blocksToAdd.size(); j++) {
				// last two blocks in blocksToAdd list are the muxFB and runAllFB which only
				// needs to be added once so unless the outer loop is in it's last two run
				// throughs, we don't add the last two in the list
				if (i < testSuite.getTestCases().size() - 1 && j == blocksToAdd.size() - 2) {
					break;
				}

				int x;
				int y;
				// different position for muxFB and runAllFB
				if (i == testSuite.getTestCases().size() - 1
						&& (j == blocksToAdd.size() - 2 || j == blocksToAdd.size() - 1)) {
					x = 50 + 400 * j;
					y = net.getNetworkElements().get((testSuite.getTestCases().size() * 3) / 2).getPosition().getY();
				} else {
					x = 50 + 400 * j;
					y = 50 + 250 * i;
				}

				final FB addedBlock = addFBToNetwork(net, blocksToAdd.get(j), x, y);
				// the actual instance of the added blocks get saved to a list for easier access
				// when creating connections
				addBlockToAccordingList(addedBlock, i, j);

				// add timeOut block to matchFb
				if (j == 2) {
					addTimeOutFB(net, x, y);
				}
			}
		}
	}

	private void addBlockToAccordingList(final FB fb, final int i, final int j) {
		if (j == 0) {
			testFBs.add(fb);
		} else if (j == 1) {
			toTestFBs.add(fb);
		} else if (j == 2) {
			matchFBs.add(fb);
		} else if (i == testSuite.getTestCases().size() - 1 && j == blocksToAdd.size() - 2) {
			muxFB = fb;
		} else if (i == testSuite.getTestCases().size() - 1 && j == blocksToAdd.size() - 1) {
			runAllFB = fb;
		}
	}

	private void addTimeOutFB(final FBNetwork net, final int x, final int y) {
		final FBNetworkElement compEl = LibraryElementFactory.eINSTANCE.createCFBInstance();

		final TypeEntry compType = sourceType.getTypeLibrary().getFBTypeEntry("E_TimeOut"); //$NON-NLS-1$
		compEl.setTypeEntry(compType);
		addPosition(compEl, x + 200, y + 150);

		compEl.setInterface(compEl.getType().getInterfaceList().copy());

		net.getNetworkElements().add(compEl);
		final String name = NameRepository.createUniqueName(compEl, "TESTAPPFB1");//$NON-NLS-1$
		compEl.setName(name);

		// connection between matchFB and timeOut

		final AdapterConnection a = createAdapterConnection(
				matchFBs.get(matchFBs.size() - 1).getInterface().getPlugs().get(0),
				compositeFB.getFBNetwork().getFBNamed(name).getInterface().getSockets().get(0));
		compositeFB.getFBNetwork().getAdapterConnections().add(a);
}

	private static AdapterConnection createAdapterConnection(final AdapterDeclaration source,
			final AdapterDeclaration dest) {
		final AdapterConnection con = LibraryElementFactory.eINSTANCE.createAdapterConnection();
		con.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());
		con.setSource(source);
		con.setDestination(dest);
		return con;
	}

	// every event is connected to every datapin
	@Override
	protected void createWiths() {
		for (final Event output : compositeFB.getInterfaceList().getEventOutputs()) {
			for (final VarDeclaration varD : compositeFB.getInterfaceList().getOutputVars()) {
				output.getWith().add(createWith(varD));
			}
		}
	}

	// datapin containing the name of the testCase
	@Override
	protected void createData() {
		compositeFB.getInterfaceList().getOutputVars().add(createVarDeclaration(
				sourceType.getTypeLibrary().getDataTypeLibrary().getType(FordiacKeywords.STRING), "TestCaseName", //$NON-NLS-1$
				false));
	}

	@Override
	protected void createConnections() {
		for (int i = 0; i < testSuite.getTestCases().size(); i++) {
			createConnToTestFB(i);
			createConnToBlockToTest(i);
			createConnToMatchFB(i);
			createConnToMuxFB(i);
		}
		createConnToRunAllFB();
		createConnToComposite();
	}

	private void createConnToRunAllFB() {
		// event connections
		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(
						compositeFB.getInterfaceList().getEventInputs()
								.get(compositeFB.getInterfaceList().getEventInputs().size() - 1),
						runAllFB.getInterface().getEventInputs().get(0)));
		compositeFB.getFBNetwork().getEventConnections().add(createEventConnection(
				muxFB.getInterface().getEventOutputs().get(0), runAllFB.getInterface().getEventInputs().get(1)));
		compositeFB.getFBNetwork().getEventConnections().add(createEventConnection(
				muxFB.getInterface().getEventOutputs().get(1), runAllFB.getInterface().getEventInputs().get(1)));

	}

	private void createConnToMuxFB(final int index) {
		// Event Connections
		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(compositeFB.getInterfaceList().getEventInputs().get(index),
						muxFB.getInterface().getEventInputs().get(index)));

		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(matchFBs.get(index).getInterface().getEventOutputs().get(0),
						muxFB.getInterface().getEventInputs().get(muxFB.getInterface().getEventInputs().size() - 2)));

		final Event dest = matchFBs.get(index).getInterface().getEventOutputs().get(1);
		final Event source = muxFB.getInterface().getEventInputs()
				.get(muxFB.getInterface().getEventInputs().size() - 1);
		compositeFB.getFBNetwork().getEventConnections().add(createEventConnection(dest, source));
		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(runAllFB.getInterface().getEventOutputs().get(index),
						muxFB.getInterface().getEventInputs().get(index)));
	}

	// create connections to the outputs of the composite
	private void createConnToComposite() {
		// Event Connections
		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(muxFB.getInterface().getEventOutputs().get(0),
						compositeFB.getInterfaceList().getEventOutputs().get(0)));
		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(muxFB.getInterface().getEventOutputs().get(1),
						compositeFB.getInterfaceList().getEventOutputs().get(1)));
		// Data Connection
		compositeFB.getFBNetwork().getDataConnections().add(createDataConnection(
				muxFB.getInterface().getOutputVars().get(0), compositeFB.getInterfaceList().getOutputVars().get(0)));

	}

	private void createConnToMatchFB(final int index) {
		// Input Events
		// connections from testFB to matchFB
		for (final Event testEv : testFBs.get(index).getInterface().getEventOutputs()) {
			for (final Event matchEv : matchFBs.get(index).getInterface().getEventInputs()) {
				if (testEv.getName().equals(matchEv.getName())) {
					compositeFB.getFBNetwork().getEventConnections().add(createEventConnection(testEv, matchEv));
				}
			}
		}

		// connections from toTestFB to matchFB
		for (final Event toTestEv : toTestFBs.get(index).getInterface().getEventOutputs()) {
			for (final Event matchEv : matchFBs.get(index).getInterface().getEventInputs()) {
				if (toTestEv.getName().equals(matchEv.getName())) {
					compositeFB.getFBNetwork().getEventConnections().add(createEventConnection(toTestEv, matchEv));
				}
			}
		}

		// Input Data
		// connections from testFB to matchFB
		for (final VarDeclaration testVa : testFBs.get(index).getInterface().getOutputVars()) {
			for (final VarDeclaration matchVa : matchFBs.get(index).getInterface().getInputVars()) {
				if ((testVa.getName()).equals(matchVa.getName())) {
					compositeFB.getFBNetwork().getDataConnections().add(createDataConnection(testVa, matchVa));
				}
			}
		}

		// connections from toTestFB to matchFB
		for (final VarDeclaration toTestVa : toTestFBs.get(index).getInterface().getOutputVars()) {
			for (final VarDeclaration matchVa : matchFBs.get(index).getInterface().getInputVars()) {
				if ((toTestVa.getName() + "_received").equals(matchVa.getName())) { //$NON-NLS-1$
					compositeFB.getFBNetwork().getDataConnections().add(createDataConnection(toTestVa, matchVa));
				}
			}
		}

	}

	private void createConnToBlockToTest(final int index) {
		// event
		for (int i = 0; i < toTestFBs.get(index).getInterface().getEventInputs().size(); i++) {
			compositeFB.getFBNetwork().getEventConnections()
					.add(createEventConnection(testFBs.get(index).getInterface().getEventOutputs().get(i),
							toTestFBs.get(index).getInterface().getEventInputs().get(i)));
		}

		// data
		for (int i = 0; i < toTestFBs.get(index).getInterface().getInputVars().size(); i++) {
			compositeFB.getFBNetwork().getDataConnections()
					.add(createDataConnection(testFBs.get(index).getInterface().getOutputVars().get(i),
							toTestFBs.get(index).getInterface().getInputVars().get(i)));
		}

	}

	private void createConnToTestFB(final int index) {
		// Event connection
		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(compositeFB.getInterfaceList().getEventInputs().get(index),
						testFBs.get(index).getInterface().getEventInputs().get(index)));
		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(runAllFB.getInterface().getEventOutputs().get(index),
						testFBs.get(index).getInterface().getEventInputs().get(index)));
	}

	@Override
	protected void createEvents() {
		// each testCase needs an input event
		testSuite.getTestCases().stream().forEach(
				n -> compositeFB.getInterfaceList().getEventInputs().add(createEvent(n.getName() + "_TEST", true))); //$NON-NLS-1$

		compositeFB.getInterfaceList().getEventInputs().add(createEvent("run_all_TEST", true)); //$NON-NLS-1$
		compositeFB.getInterfaceList().getEventOutputs().add(createEvent("ERROR", false)); //$NON-NLS-1$
		compositeFB.getInterfaceList().getEventOutputs().add(createEvent("SUCCESS", false)); //$NON-NLS-1$
	}

	@Override
	protected String getTypeName() {
		return sourceType.getName() + "_TEST_COMPOSITE"; //$NON-NLS-1$
	}

}