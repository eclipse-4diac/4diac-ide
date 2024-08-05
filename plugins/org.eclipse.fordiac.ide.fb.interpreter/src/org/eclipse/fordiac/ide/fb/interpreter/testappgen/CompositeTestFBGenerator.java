/*******************************************************************************
 * Copyright (c) 2023, 2024 Johannes Kepler University Linz
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

import static org.eclipse.fordiac.ide.fb.interpreter.testappgen.GeneratedNameConstants.EVENT_ERROR;
import static org.eclipse.fordiac.ide.fb.interpreter.testappgen.GeneratedNameConstants.EVENT_LASTCOMPLETE;
import static org.eclipse.fordiac.ide.fb.interpreter.testappgen.GeneratedNameConstants.EVENT_RUNALL;
import static org.eclipse.fordiac.ide.fb.interpreter.testappgen.GeneratedNameConstants.EVENT_SUCCESS;
import static org.eclipse.fordiac.ide.fb.interpreter.testappgen.GeneratedNameConstants.VARDECL_TESTCASENAME;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.AbstractCompositeFBGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestCase;
import org.eclipse.fordiac.ide.fb.interpreter.testcasemodel.TestSuite;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
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
		for (int i = 0; i < getTestCases().size(); i++) {
			for (int j = 0; j < blocksToAdd.size(); j++) {
				// last two blocks in blocksToAdd list are the muxFB and runAllFB which only
				// need to be added once so unless the outer loop is in it's last two run
				// throughs, we don't add the last two in the list
				if (i < getTestCases().size() - 1 && j == blocksToAdd.size() - 2) {
					break;
				}

				int x;
				int y;
				// different position for muxFB and runAllFB
				if (i == getTestCases().size() - 1 && (j == blocksToAdd.size() - 2 || j == blocksToAdd.size() - 1)) {
					x = 50 + 400 * j;
					y = net.getNetworkElements().get((getTestCases().size() * 3) / 2).getPosition().toScreenPoint().y;
				} else {
					x = 50 + 400 * j;
					y = 50 + 250 * i;
				}

				final FB addedBlock = addFBToNetwork(net, blocksToAdd.get(j), x, y);
				// the actual instance of the added blocks get saved to the according list for
				// easier access when creating connections
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
		} else if (i == getTestCases().size() - 1 && j == blocksToAdd.size() - 2) {
			muxFB = fb;
		} else if (i == getTestCases().size() - 1 && j == blocksToAdd.size() - 1) {
			runAllFB = fb;
		}
	}

	private void addTimeOutFB(final FBNetwork net, final int x, final int y) {
		final FBNetworkElement compEl = LibraryElementFactory.eINSTANCE.createCFBInstance();
		final TypeEntry compType = sourceType.getTypeLibrary().getFBTypeEntry(TestGenBlockNames.TIMEOUT_COMPOSITE_NAME);
		compEl.setTypeEntry(compType);
		addPosition(compEl, x + (double) 200, y + (double) 150);

		compEl.setInterface(compEl.getType().getInterfaceList().copy());

		net.getNetworkElements().add(compEl);
		final String name = NameRepository.createUniqueName(compEl, "TESTAPPFB1");//$NON-NLS-1$
		compEl.setName(name);

		// connection between matchFB and timeOut
		final AdapterConnection a = createAdapterConnection(matchFBs.getLast().getInterface().getPlugs().get(0),
				compositeFB.getFBNetwork().getFBNamed(name).getInterface().getSockets().get(0));
		compositeFB.getFBNetwork().getAdapterConnections().add(a);
	}

	// every event is connected to every datapin
	@Override
	protected void createWiths() {
		for (final Event output : getEventOutputs()) {
			for (final VarDeclaration varD : compositeFB.getInterfaceList().getOutputVars()) {
				output.getWith().add(createWith(varD));
			}
		}
	}

	// output datapin where the name of the current testcase will be written to
	@Override
	protected void createData() {
		compositeFB.getInterfaceList().getOutputVars().add(createOutputVarDecl(
				sourceType.getTypeLibrary().getDataTypeLibrary().getType(FordiacKeywords.STRING), "TestCaseName")); //$NON-NLS-1$
	}

	@Override
	protected void createConnections() {

		for (int i = 0; i < getTestCases().size(); i++) {
			createConnToTestsingalGenFB(i);
			createConnToBlockToTest(i);
			createConnToMatchFB(i);
			createConnToMuxFB(i);
		}

		// there is only one instance of them so they don't need to be in the loop
		createConnToRunAllFB();
		createConnToComposite();
	}

	private void createConnToRunAllFB() {

		// connection from the composite to the runallFB
		getEventConns().add(createEventConn(compositeFB.getInterfaceList().getEvent(EVENT_RUNALL),
				(Event) runAllFB.getInterfaceElement(EVENT_RUNALL)));

		// connect muxFB outputs to the runallFB, so the runallFB knows, when to start
		// the next test case
		getEventConns().add(createEventConn((Event) muxFB.getInterfaceElement(EVENT_SUCCESS),
				(Event) runAllFB.getInterfaceElement(EVENT_LASTCOMPLETE)));
		getEventConns().add(createEventConn((Event) muxFB.getInterfaceElement(EVENT_ERROR),
				(Event) runAllFB.getInterfaceElement(EVENT_LASTCOMPLETE)));
	}

	private void createConnToMuxFB(final int index) {
		// Event Connections
		getEventConns()
				.add(createEventConn(getEventInputs().get(index), muxFB.getInterface().getEventInputs().get(index)));

		getEventConns().add(createEventConn(matchFBs.get(index).getInterface().getEventOutputs().get(0),
				muxFB.getInterface().getEventInputs().get(muxFB.getInterface().getEventInputs().size() - 2)));

		final Event source = matchFBs.get(index).getInterface().getEventOutputs().get(1);
		final Event dest = muxFB.getInterface().getEventInputs().getLast();
		getEventConns().add(createEventConn(source, dest));
//		final Event dest = matchFBs.get(index).getInterface().getEventOutputs().get(1);
//		final Event source = muxFB.getInterface().getEventInputs().getLast();
//		getEventConns().add(createEventConn(dest, source));
		getEventConns().add(createEventConn(runAllFB.getInterface().getEventOutputs().get(index),
				muxFB.getInterface().getEventInputs().get(index)));
	}

	// create connections to the outputs of the composite
	private void createConnToComposite() {
		// Event Connections
		getEventConns().add(createEventConn((Event) muxFB.getInterfaceElement(EVENT_ERROR),
				(Event) compositeFB.getInterfaceList().getInterfaceElement(EVENT_ERROR)));
		getEventConns().add(createEventConn((Event) muxFB.getInterfaceElement(EVENT_SUCCESS),
				(Event) compositeFB.getInterfaceList().getInterfaceElement(EVENT_SUCCESS)));

		// Data Connection
		getDataConns()
				.add(createDataConn((VarDeclaration) muxFB.getInterface().getInterfaceElement(VARDECL_TESTCASENAME),
						compositeFB.getInterfaceList().getVariable(VARDECL_TESTCASENAME)));

	}

	private void createConnToMatchFB(final int index) {
		// Input Events
		// connections from testFB to matchFB
		connectMatchFBEventInputs(testFBs.get(index), matchFBs.get(index));
		connectMatchFBEventInputs(toTestFBs.get(index), matchFBs.get(index));

		// Input Data
		// connections from testFB to matchFB
		for (final VarDeclaration testVa : testFBs.get(index).getInterface().getOutputVars()) {
			for (final VarDeclaration matchVa : matchFBs.get(index).getInterface().getInputVars()) {
				if ((testVa.getName()).equals(matchVa.getName())) {
					getDataConns().add(createDataConn(testVa, matchVa));
					break;
				}
			}
		}

		// connections from toTestFB to matchFB
		for (final VarDeclaration toTestVa : toTestFBs.get(index).getInterface().getOutputVars()) {
			for (final VarDeclaration matchVa : matchFBs.get(index).getInterface().getInputVars()) {
				if ((toTestVa.getName() + "_received").equals(matchVa.getName())) { //$NON-NLS-1$
					getDataConns().add(createDataConn(toTestVa, matchVa));
					break;
				}
			}
		}
	}

	private void connectMatchFBEventInputs(final FB sourceFb, final FB destFb) {
		for (final Event sourceEv : sourceFb.getInterface().getEventOutputs()) {
			final IInterfaceElement el = destFb.getInterfaceElement(sourceEv.getName());
			if (el instanceof final Event destEv && destEv.isIsInput()) {
				getEventConns().add(createEventConn(sourceEv, destEv));
			}
		}
	}

	private void createConnToBlockToTest(final int index) {
		// event connections from the testsignal generator block
		for (int i = 0; i < toTestFBs.get(index).getInterface().getEventInputs().size(); i++) {
			getEventConns().add(createEventConn(testFBs.get(index).getInterface().getEventOutputs().get(i),
					toTestFBs.get(index).getInterface().getEventInputs().get(i)));
		}

		// data connections from the testsignal generator block
		for (int i = 0; i < toTestFBs.get(index).getInterface().getInputVars().size(); i++) {
			getDataConns().add(createDataConn(testFBs.get(index).getInterface().getOutputVars().get(i + 1),
					toTestFBs.get(index).getInterface().getInputVars().get(i)));
		}
	}

	private void createConnToTestsingalGenFB(final int index) {
		// connection from the composite input events to the testsignal gen fb
		getEventConns().add(createEventConn( //
				getEventInputs().get(index), //
				testFBs.get(index).getInterface().getEventInputs().get(index)));

		// connection from the runall fb to the testsignal gen fb
		getEventConns().add(createEventConn( //
				runAllFB.getInterface().getEventOutputs().get(index),
				testFBs.get(index).getInterface().getEventInputs().get(index)));

		// connect matchFB to testFB, so testFB knows when to send the next event to the
		// block to test
		getEventConns().add(createEventConn(matchFBs.get(index).getInterface().getEventOutputs().getLast(),
				testFBs.get(index).getInterface().getEventInputs().getLast()));
	}

	@Override
	protected void createEvents() {
		// each testCase needs an input event
		getTestCases().stream().forEach(n -> getEventInputs().add(createInputEvent(n.getName() + "_TEST"))); //$NON-NLS-1$

		// event input to run all tests
		getEventInputs().add(createInputEvent(EVENT_RUNALL)); // $NON-NLS-1$

		// success and error output event to notify others if the test was successful or
		// not
		getEventOutputs().add(createOutputEvent(EVENT_ERROR)); // $NON-NLS-1$
		getEventOutputs().add(createOutputEvent(EVENT_SUCCESS)); // $NON-NLS-1$
	}

	@Override
	protected String getTypeName() {
		return sourceType.getName() + "_TEST_COMPOSITE"; //$NON-NLS-1$
	}

	private EList<EventConnection> getEventConns() {
		return compositeFB.getFBNetwork().getEventConnections();
	}

	private EList<DataConnection> getDataConns() {
		return compositeFB.getFBNetwork().getDataConnections();
	}

	private EList<Event> getEventInputs() {
		return compositeFB.getInterfaceList().getEventInputs();
	}

	private EList<Event> getEventOutputs() {
		return compositeFB.getInterfaceList().getEventOutputs();
	}

	private List<TestCase> getTestCases() {
		return testSuite.getTestCases();
	}
}