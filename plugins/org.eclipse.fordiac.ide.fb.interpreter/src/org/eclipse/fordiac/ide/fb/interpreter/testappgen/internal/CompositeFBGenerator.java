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

package org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class CompositeFBGenerator {
	private CompositeFBType compositeFB;
	private final TestSuite testSuite;
	private final FBType sourceType;

	private FB muxFB;
	private FB demuxFB;
	private final List<FBType> blocksToAdd;
	private final List<FB> toTestFBs = new ArrayList<>();
	private final List<FB> testFBs = new ArrayList<>();
	private final List<FB> matchFBs = new ArrayList<>();

	public CompositeFBGenerator(final FBType type, final TestSuite testSuite, final List<FBType> blocksToAdd) {
		sourceType = type;
		this.testSuite = testSuite;
		this.blocksToAdd = blocksToAdd;
	}

	public CompositeFBType generateCompositeFB() {
		compositeFB = LibraryElementFactory.eINSTANCE.createCompositeFBType();
		final Identification id = LibraryElementFactory.eINSTANCE.createIdentification();
		compositeFB.setIdentification(id);
		id.setStandard("IEC 61499"); //$NON-NLS-1$

		compositeFB.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		compositeFB.setName(sourceType.getName() + "_COMPOSITE"); //$NON-NLS-1$

		compositeFB.setService(LibraryElementFactory.eINSTANCE.createService());

		final IProject project = sourceType.getTypeLibrary().getProject();
		final String s = sourceType.getTypeEntry().getFile().getFullPath().toString();
		final IFolder folder = project.getFolder(s.substring(project.getName().length() + 2, s.lastIndexOf('/')));
		final IFile destfile = folder.getFile(sourceType.getName() + "_COMPOSITE.fbt"); //$NON-NLS-1$

		final TypeEntry entry = sourceType.getTypeLibrary().createTypeEntry(destfile);
		entry.setType(compositeFB);

		addFBsToNetwork();

		setValuesForFBs();
		createEvents();
		createDataOutputList();
		createConnections();
		createWiths();

		return compositeFB;
	}

	private void addFBsToNetwork() {
		final FBNetwork net = LibraryElementFactory.eINSTANCE.createFBNetwork();
		compositeFB.setFBNetwork(net);
		// each testCase needs a matchFB, testFB and toTestFB
		for (int i = 0; i < testSuite.getTestCases().size(); i++) {
			for (int j = 0; j < blocksToAdd.size(); j++) {
				// last block in blocksToAdd list is the muxFB which only needs to be added once
				// so unless the outer loop is in it's last run through, we skip to add the
				// muxFB
				if (i < testSuite.getTestCases().size() - 1 && j == blocksToAdd.size() - 2) {
					break;
				}
				final FBNetworkElement el = LibraryElementFactory.eINSTANCE.createFB();
				el.setTypeEntry(sourceType.getTypeLibrary().getFBTypeEntry(blocksToAdd.get(j).getName()));

				final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
				if (i == testSuite.getTestCases().size() - 1
						&& (j == blocksToAdd.size() - 2 || j == blocksToAdd.size() - 1)) {
					pos.setX(50 + 300 * j);
					pos.setY(net.getNetworkElements().get((testSuite.getTestCases().size() * 3) / 2).getPosition()
							.getY());
				} else {
					pos.setX(50 + 300 * j);
					pos.setY(50 + 250 * i);
				}
				el.setPosition(pos);
				el.setInterface(EcoreUtil.copy(el.getType().getInterfaceList()));

				net.getNetworkElements().add(el);
				final String name = NameRepository.createUniqueName(el, "TESTAPPFB1"); //$NON-NLS-1$
				el.setName(name);

				addBlockToAccordingList(name, i, j);
				// add timeOut block to matchFb
				if (j == 2) {
					addTimeOutFB(net, pos);
				}
			}
		}

	}

	private void addBlockToAccordingList(final String name, final int i, final int j) {
		if (j == 0) {
			testFBs.add(compositeFB.getFBNetwork().getFBNamed(name));
		} else if (j == 1) {
			toTestFBs.add(compositeFB.getFBNetwork().getFBNamed(name));
		} else if (j == 2) {
			matchFBs.add(compositeFB.getFBNetwork().getFBNamed(name));

		} else if (i == testSuite.getTestCases().size() - 1 && j == blocksToAdd.size() - 2) {
			muxFB = compositeFB.getFBNetwork().getFBNamed(name);
		} else if (i == testSuite.getTestCases().size() - 1 && j == blocksToAdd.size() - 1) {
			demuxFB = compositeFB.getFBNetwork().getFBNamed(name);
		}

	}

	private void addTimeOutFB(final FBNetwork net, final Position p0) {
		final FBNetworkElement compEl = LibraryElementFactory.eINSTANCE.createCFBInstance();

		final TypeEntry compType = sourceType.getTypeLibrary().getFBTypeEntry("E_TimeOut"); //$NON-NLS-1$
		compEl.setTypeEntry(compType);
		final Position p = LibraryElementFactory.eINSTANCE.createPosition();
		p.setX(p0.getX() + 100);
		p.setY(p0.getY() + 100);
		compEl.setPosition(p);
		compEl.setInterface(EcoreUtil.copy(compEl.getType().getInterfaceList()));

		net.getNetworkElements().add(compEl);
		final String name = NameRepository.createUniqueName(compEl, "TESTAPPFB1");//$NON-NLS-1$
		compEl.setName(name);

		// connection
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

	private void createWiths() {
		for (final Event output : compositeFB.getInterfaceList().getEventOutputs()) {
			for (final VarDeclaration varD : compositeFB.getInterfaceList().getOutputVars()) {
				final With w = LibraryElementFactory.eINSTANCE.createWith();
				w.setVariables(varD);
				output.getWith().add(w);
			}
		}
	}

	private void createDataOutputList() {
		final VarDeclaration varDecl = AbstractFBGenerator.createVarDeclaration("TestCaseName", false); //$NON-NLS-1$
		varDecl.setType(sourceType.getTypeLibrary().getDataTypeLibrary().getType(FordiacKeywords.STRING));
		varDecl.setComment(""); //$NON-NLS-1$
		compositeFB.getInterfaceList().getOutputVars().add(varDecl);
	}

	private void setValuesForFBs() {
		// some values might be null, so to be sure every value gets set again
		for (int i = 0; i < testSuite.getTestCases().size(); i++) {
			// toTestFB
			setValue(toTestFBs.get(i).getInterface().getInputVars());
			setValue(toTestFBs.get(i).getInterface().getOutputVars());

			// testFB
			setValue(testFBs.get(i).getInterface().getInputVars());
			setValue(testFBs.get(i).getInterface().getOutputVars());

			// matchFB
			setValue(matchFBs.get(i).getInterface().getInputVars());
			setValue(matchFBs.get(i).getInterface().getOutputVars());
		}
		// muxFB
		setValue(muxFB.getInterface().getInputVars());
		setValue(muxFB.getInterface().getOutputVars());
	}

	private static void setValue(final EList<VarDeclaration> vars) {
		for (final VarDeclaration varD : vars) {
			if (varD.getValue() == null) {
				varD.setValue(LibraryElementFactory.eINSTANCE.createValue());
				varD.getValue().setValue(""); //$NON-NLS-1$
			}
		}
	}

	private void createConnections() {
		for (int i = 0; i < testSuite.getTestCases().size(); i++) {
			createConnToTestFB(i);
			createConnToBlockToTest(i);
			createConnToMatchFB(i);
			createConnToMuxFB(i);
		}
		createConnToDemuxFB();
		createConnToComposite();
	}

	private void createConnToDemuxFB() {
		// event connections
		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(
						compositeFB.getInterfaceList().getEventInputs()
								.get(compositeFB.getInterfaceList().getEventInputs().size() - 1),
						demuxFB.getInterface().getEventInputs().get(0)));
		compositeFB.getFBNetwork().getEventConnections().add(createEventConnection(
				muxFB.getInterface().getEventOutputs().get(0), demuxFB.getInterface().getEventInputs().get(1)));
		compositeFB.getFBNetwork().getEventConnections().add(createEventConnection(
				muxFB.getInterface().getEventOutputs().get(1), demuxFB.getInterface().getEventInputs().get(1)));

	}

	private void createConnToMuxFB(final int index) {

		// Event Connections
		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(compositeFB.getInterfaceList().getEventInputs().get(index),
						muxFB.getInterface().getEventInputs().get(index)));

		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(matchFBs.get(index).getInterface().getEventOutputs().get(0),
						muxFB.getInterface().getEventInputs().get(muxFB.getInterface().getEventInputs().size() - 2)));
		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(matchFBs.get(index).getInterface().getEventOutputs().get(1),
						muxFB.getInterface().getEventInputs().get(muxFB.getInterface().getEventInputs().size() - 1)));
		compositeFB.getFBNetwork().getEventConnections().add(createEventConnection(
				demuxFB.getInterface().getEventOutputs().get(index), muxFB.getInterface().getEventInputs().get(index)));
		// Data Connections
//		if (!sourceType.getInterfaceList().getOutputVars().isEmpty()) {
//			compositeFB.getFBNetwork().getDataConnections()
//					.add(createDataConnection(matchFBs.get(index).getInterface().getOutputVars().get(0),
//							muxFB.getInterface().getInputVars().get(index)));
//		}
	}

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
				.add(createEventConnection(demuxFB.getInterface().getEventOutputs().get(index),
						testFBs.get(index).getInterface().getEventInputs().get(index)));
	}

	private static EventConnection createEventConnection(final Event source, final Event dest) {
		final EventConnection con = LibraryElementFactory.eINSTANCE.createEventConnection();
		con.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());
		con.setSource(source);
		con.setDestination(dest);
		return con;
	}

	private static DataConnection createDataConnection(final VarDeclaration source, final VarDeclaration dest) {
		final DataConnection con = LibraryElementFactory.eINSTANCE.createDataConnection();
		con.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());
		con.setSource(source);
		con.setDestination(dest);
		return con;
	}

	private void createEvents() {
		for (final TestCase testCase : testSuite.getTestCases()) {
			compositeFB.getInterfaceList().getEventInputs()
					.add(AbstractFBGenerator.createEvent(testCase.getName() + "_TEST", true)); //$NON-NLS-1$
		}
		compositeFB.getInterfaceList().getEventInputs().add(AbstractFBGenerator.createEvent("run_all_TEST", true)); //$NON-NLS-1$

		compositeFB.getInterfaceList().getEventOutputs().add(AbstractFBGenerator.createEvent("ERROR", false)); //$NON-NLS-1$
		compositeFB.getInterfaceList().getEventOutputs().add(AbstractFBGenerator.createEvent("SUCCESS", false)); //$NON-NLS-1$
	}

}