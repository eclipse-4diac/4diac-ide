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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.NameRepository;
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
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class CompositeFBGenerator {
	private CompositeFBType compositeFB;
	private final TestSuite testSuite;
	private final FBType sourceType;
	List<FBType> blocksToAdd;
	private FB toTestFB;
	private FB testFB;
	private FB matchFB;

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
		final IFolder folder = project.getFolder("Type Library/blocksForTestingTheTestblock/generatedBlocks"); //$NON-NLS-1$
		final IFile destfile = folder.getFile(sourceType.getName() + "_COMPOSITE.fbt"); //$NON-NLS-1$

		final TypeEntry entry = sourceType.getTypeLibrary().createTypeEntry(destfile);
		entry.setType(compositeFB);

		final FBNetwork net = LibraryElementFactory.eINSTANCE.createFBNetwork();
		for (int i = 0; i < blocksToAdd.size(); i++) {
			final FBNetworkElement el = LibraryElementFactory.eINSTANCE.createFB();
			el.setTypeEntry(sourceType.getTypeLibrary().getFBTypeEntry(blocksToAdd.get(i).getName()));
			final Position p0 = LibraryElementFactory.eINSTANCE.createPosition();
			p0.setX(50 + 250 * i);
			p0.setY(50);
			el.setPosition(p0);
			el.setInterface(EcoreUtil.copy(el.getType().getInterfaceList()));
			net.getNetworkElements().add(el);
			el.setName(NameRepository.createUniqueName(el, "TESTAPPFB1")); //$NON-NLS-1$
		}

		compositeFB.setFBNetwork(net);

		testFB = compositeFB.getFBNetwork().getFBNamed("TESTAPPFB1"); //$NON-NLS-1$
		toTestFB = compositeFB.getFBNetwork().getFBNamed("TESTAPPFB2"); //$NON-NLS-1$
		matchFB = compositeFB.getFBNetwork().getFBNamed("TESTAPPFB3"); //$NON-NLS-1$

		setValuesForFBs();
		createEvents();
		createConnections();

		return compositeFB;
	}

	private void setValuesForFBs() {
		// toTestFB
		for (final VarDeclaration varD : toTestFB.getInterface().getInputVars()) {
			varD.setValue(LibraryElementFactory.eINSTANCE.createValue());
			varD.getValue().setValue(""); //$NON-NLS-1$
		}
		for (final VarDeclaration varD : toTestFB.getInterface().getOutputVars()) {
			varD.setValue(LibraryElementFactory.eINSTANCE.createValue());
			varD.getValue().setValue(""); //$NON-NLS-1$
		}

		// testFB
		for (final VarDeclaration varD : testFB.getInterface().getInputVars()) {
			varD.setValue(LibraryElementFactory.eINSTANCE.createValue());
			varD.getValue().setValue(""); //$NON-NLS-1$
		}
		for (final VarDeclaration varD : testFB.getInterface().getOutputVars()) {
			varD.setValue(LibraryElementFactory.eINSTANCE.createValue());
			varD.getValue().setValue(""); //$NON-NLS-1$
		}

		// matchFB
		for (final VarDeclaration varD : matchFB.getInterface().getInputVars()) {
			varD.setValue(LibraryElementFactory.eINSTANCE.createValue());
			varD.getValue().setValue(""); //$NON-NLS-1$
		}
		for (final VarDeclaration varD : matchFB.getInterface().getOutputVars()) {
			varD.setValue(LibraryElementFactory.eINSTANCE.createValue());
			varD.getValue().setValue(""); //$NON-NLS-1$
		}
	}

	private void createConnections() {
		createConnectionToTestFB();
		createConnectionToBlockToTest();
		createConnectionToMatchFB();
		createConnectionToComposite();
	}

	private void createConnectionToComposite() {
		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(matchFB.getInterface().getEventOutputs().get(0),
						compositeFB.getInterfaceList().getEventOutputs().get(0)));
		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(matchFB.getInterface().getEventOutputs().get(1),
						compositeFB.getInterfaceList().getEventOutputs().get(1)));
	}

	private void createConnectionToMatchFB() {
		// Input Events
		for (final Event testEv : testFB.getInterface().getEventOutputs()) {
			for (final Event matchEv : matchFB.getInterface().getEventInputs()) {
				if (testEv.getName().equals(matchEv.getName())) {
					compositeFB.getFBNetwork().getEventConnections().add(createEventConnection(testEv, matchEv));
				}
			}
		}

		for (final Event toTestEv : toTestFB.getInterface().getEventOutputs()) {
			for (final Event matchEv : matchFB.getInterface().getEventInputs()) {
				if (toTestEv.getName().equals(matchEv.getName())) {
					compositeFB.getFBNetwork().getEventConnections().add(createEventConnection(toTestEv, matchEv));
				}
			}
		}

		// Input Data
		for (final VarDeclaration testVa : testFB.getInterface().getOutputVars()) {
			for (final VarDeclaration matchVa : matchFB.getInterface().getInputVars()) {
				if ((testVa.getName() + "_expected").equals(matchVa.getName())) { //$NON-NLS-1$
					compositeFB.getFBNetwork().getDataConnections().add(createDataConnection(testVa, matchVa));
				}
			}
		}

		for (final VarDeclaration toTestVa : toTestFB.getInterface().getOutputVars()) {
			for (final VarDeclaration matchVa : matchFB.getInterface().getInputVars()) {
				if ((toTestVa.getName() + "_received").equals(matchVa.getName())) { //$NON-NLS-1$
					compositeFB.getFBNetwork().getDataConnections().add(createDataConnection(toTestVa, matchVa));
				}
			}
		}
	}

	private void createConnectionToBlockToTest() {
		for (int i = 0; i < testFB.getInterface().getEventOutputs().size(); i++) {
			if (testFB.getInterface().getEventOutputs().get(i).getName().contains("expected")) { //$NON-NLS-1$
				break;
			}
			compositeFB.getFBNetwork().getEventConnections().add(createEventConnection(
					testFB.getInterface().getEventOutputs().get(i), toTestFB.getInterface().getEventInputs().get(i)));
		}

		for (int i = 0; i < toTestFB.getInterface().getInputVars().size(); i++) {
			compositeFB.getFBNetwork().getDataConnections().add(createDataConnection(
					testFB.getInterface().getOutputVars().get(i), toTestFB.getInterface().getInputVars().get(i)));
		}

	}

	private void createConnectionToTestFB() {
		for (int i = 0; i < compositeFB.getInterfaceList().getEventInputs().size(); i++) {
			compositeFB.getFBNetwork().getEventConnections()
					.add(createEventConnection(compositeFB.getInterfaceList().getEventInputs().get(i),
							testFB.getInterface().getEventInputs().get(i)));
		}
		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(matchFB.getInterface().getEventOutputs().get(0),
						testFB.getInterface().getEventInputs().get(testFB.getInterface().getEventInputs().size() - 1)));

		compositeFB.getFBNetwork().getEventConnections()
				.add(createEventConnection(matchFB.getInterface().getEventOutputs().get(1),
						testFB.getInterface().getEventInputs().get(testFB.getInterface().getEventInputs().size() - 1)));
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
		compositeFB.getInterfaceList().getEventOutputs().add(AbstractFBGenerator.createEvent("ERROR", false)); //$NON-NLS-1$
		compositeFB.getInterfaceList().getEventOutputs().add(AbstractFBGenerator.createEvent("SUCCESS", false)); //$NON-NLS-1$
	}
}