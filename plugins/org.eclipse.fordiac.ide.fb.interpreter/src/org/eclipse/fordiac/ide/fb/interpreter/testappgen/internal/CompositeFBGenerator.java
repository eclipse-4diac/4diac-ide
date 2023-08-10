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
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class CompositeFBGenerator {
	private CompositeFBType compositeFB;
	private final TestSuite testSuite;
	private final FBType sourceType;
	List<FBType> blocksToAdd;

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
		final IFolder folder = project.getFolder("Type Library"); //$NON-NLS-1$
		final IFile destfile = folder.getFile(sourceType.getName() + "_COMPOSITE.fbt"); //$NON-NLS-1$

		final TypeEntry entry = sourceType.getTypeLibrary().createTypeEntry(destfile);
		entry.setType(compositeFB);

		final FBNetwork net = LibraryElementFactory.eINSTANCE.createFBNetwork();
		for (int i = 0; i < blocksToAdd.size(); i++) {
			final FBNetworkElement el = LibraryElementFactory.eINSTANCE.createFB();
			el.setTypeEntry(sourceType.getTypeLibrary().getFBTypeEntry(blocksToAdd.get(i).getName()));
			final Position p0 = LibraryElementFactory.eINSTANCE.createPosition();
			p0.setX(50);
			p0.setY(50 + 250 * i);
			el.setPosition(p0);
			el.setInterface(EcoreUtil.copy(el.getType().getInterfaceList()));
			net.getNetworkElements().add(el);
			// el.setName(NameRepository.createUniqueName(el, blocksToAdd.get(i).getName() +
			// "1"));
			el.setName(NameRepository.createUniqueName(el, "TESTAPPFB1")); //$NON-NLS-1$
		}

		compositeFB.setFBNetwork(net);

		setValuesForFBToTest();

		createEvents();
		createConnections();

		return compositeFB;
	}

	private void setValuesForFBToTest() {

		final FB toTestFB = compositeFB.getFBNetwork().getFBNamed("TESTAPPFB2"); //$NON-NLS-1$

		for (final VarDeclaration varD : toTestFB.getInterface().getInputVars()) {
			varD.setValue(LibraryElementFactory.eINSTANCE.createValue());
			varD.getValue().setValue(""); //$NON-NLS-1$
		}
		for (final VarDeclaration varD : toTestFB.getInterface().getOutputVars()) {
			varD.setValue(LibraryElementFactory.eINSTANCE.createValue());
			varD.getValue().setValue(""); //$NON-NLS-1$
		}

	}

	private void createConnections() {
		createConnectionToTestFB();
		createConnectionToBlockToTest();
		createConnectionToMatchFB();
	}

	private void createConnectionToMatchFB() {
		final FB testFB = compositeFB.getFBNetwork().getFBNamed("TESTAPPFB1"); //$NON-NLS-1$
		final FB toTestFB = compositeFB.getFBNetwork().getFBNamed("TESTAPPFB2"); //$NON-NLS-1$
		final FB matchFB = compositeFB.getFBNetwork().getFBNamed("TESTAPPFB3"); //$NON-NLS-1$

		// Input Events
		for (final Event testEv : testFB.getInterface().getEventOutputs()) {
			for (final Event matchEv : matchFB.getInterface().getEventInputs()) {
				if (testEv.getName().equals(matchEv.getName())) {
					final EventConnection evCon = LibraryElementFactory.eINSTANCE.createEventConnection();
					evCon.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());
					evCon.setSource(testEv);
					evCon.setDestination(matchEv);
					compositeFB.getFBNetwork().getEventConnections().add(evCon);
				}
			}
		}

		for (final Event toTestEv : toTestFB.getInterface().getEventOutputs()) {
			for (final Event matchEv : matchFB.getInterface().getEventInputs()) {
				if (toTestEv.getName().equals(matchEv.getName())) {
					final EventConnection evCon = LibraryElementFactory.eINSTANCE.createEventConnection();
					evCon.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());
					evCon.setSource(toTestEv);
					evCon.setDestination(matchEv);
					compositeFB.getFBNetwork().getEventConnections().add(evCon);
				}
			}
		}

		// Input Data
		for (final VarDeclaration testVa : testFB.getInterface().getOutputVars()) {
			for (final VarDeclaration matchVa : matchFB.getInterface().getInputVars()) {
				if ((testVa.getName() + "_expected").equals(matchVa.getName())) { //$NON-NLS-1$
					final DataConnection evCon = LibraryElementFactory.eINSTANCE.createDataConnection();
					evCon.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());
					evCon.setSource(testVa);
					evCon.setDestination(matchVa);
					compositeFB.getFBNetwork().getDataConnections().add(evCon);
				}
			}
		}

		for (final VarDeclaration toTestVa : toTestFB.getInterface().getOutputVars()) {
			for (final VarDeclaration matchVa : matchFB.getInterface().getInputVars()) {
				if ((toTestVa.getName() + "_received").equals(matchVa.getName())) { //$NON-NLS-1$
					final DataConnection evCon = LibraryElementFactory.eINSTANCE.createDataConnection();
					evCon.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());
					evCon.setSource(toTestVa);
					evCon.setDestination(matchVa);
					compositeFB.getFBNetwork().getDataConnections().add(evCon);
				}
			}
		}
//		final DataConnection evCon = LibraryElementFactory.eINSTANCE.createDataConnection();
//		evCon.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());
//		evCon.setSource(testFB.getInterface().getOutputVars().get(2));
//		evCon.setDestination(toTestFB.getInterface().getInputVars().get(1));
//		compositeFB.getFBNetwork().getDataConnections().add(evCon);

	}

	private void createConnectionToBlockToTest() {
		final FB testFB = compositeFB.getFBNetwork().getFBNamed("TESTAPPFB1"); //$NON-NLS-1$
		final FB toTestFB = compositeFB.getFBNetwork().getFBNamed("TESTAPPFB2"); //$NON-NLS-1$

		for (int i = 0; i < testFB.getInterface().getEventOutputs().size(); i++) {
			if (testFB.getInterface().getEventOutputs().get(i).getName().contains("_expected")) { //$NON-NLS-1$
				break;
			}
			final EventConnection evCon = LibraryElementFactory.eINSTANCE.createEventConnection();
			evCon.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());
			evCon.setSource(testFB.getInterface().getEventOutputs().get(i));
			evCon.setDestination(toTestFB.getInterface().getEventInputs().get(i));
			compositeFB.getFBNetwork().getEventConnections().add(evCon);
		}

		for (int i = 0; i < toTestFB.getInterface().getInputVars().size(); i++) {
			final DataConnection evCon = LibraryElementFactory.eINSTANCE.createDataConnection();
			evCon.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());

			evCon.setSource(testFB.getInterface().getOutputVars().get(i));
			evCon.setDestination(toTestFB.getInterface().getInputVars().get(i));
			compositeFB.getFBNetwork().getDataConnections().add(evCon);
		}

	}

	private void createConnectionToTestFB() {
		for (int i = 0; i < compositeFB.getInterfaceList().getEventInputs().size(); i++) {
			final EventConnection evCon = LibraryElementFactory.eINSTANCE.createEventConnection();
			evCon.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());
			evCon.setSource(compositeFB.getInterfaceList().getEventInputs().get(i));
			evCon.setDestination(
					compositeFB.getFBNetwork().getFBNamed("TESTAPPFB1").getInterface().getEventInputs().get(i)); //$NON-NLS-1$
			compositeFB.getFBNetwork().getEventConnections().add(evCon);
		}

		final EventConnection sucCon = LibraryElementFactory.eINSTANCE.createEventConnection();
		sucCon.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());
		sucCon.setSource(compositeFB.getFBNetwork().getFBNamed("TESTAPPFB3").getInterface().getEventOutputs().get(0)); //$NON-NLS-1$
		sucCon.setDestination(compositeFB.getFBNetwork().getFBNamed("TESTAPPFB1").getInterface().getEventInputs() //$NON-NLS-1$
				.get(compositeFB.getFBNetwork().getFBNamed("TESTAPPFB1").getInterface().getEventInputs().size() - 1)); //$NON-NLS-1$
		compositeFB.getFBNetwork().getEventConnections().add(sucCon);

		final EventConnection errCon = LibraryElementFactory.eINSTANCE.createEventConnection();
		errCon.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());
		errCon.setSource(compositeFB.getFBNetwork().getFBNamed("TESTAPPFB3").getInterface().getEventOutputs().get(1)); //$NON-NLS-1$
		errCon.setDestination(compositeFB.getFBNetwork().getFBNamed("TESTAPPFB1").getInterface().getEventInputs() //$NON-NLS-1$
				.get(compositeFB.getFBNetwork().getFBNamed("TESTAPPFB1").getInterface().getEventInputs().size() - 1)); //$NON-NLS-1$
		compositeFB.getFBNetwork().getEventConnections().add(errCon);
	}

	private void createEvents() {
		for (final TestCase testCase : testSuite.getTestCases()) {
			final Event ev = LibraryElementFactory.eINSTANCE.createEvent();
			ev.setName(testCase.getName() + "_TEST"); //$NON-NLS-1$
			ev.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
			ev.setIsInput(true);
			compositeFB.getInterfaceList().getEventInputs().add(EcoreUtil.copy(ev));
		}

	}
}
