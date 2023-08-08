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
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class CompositeFBGenerator  {
	private CompositeFBType compositeFB;
	private TestSuite testSuite;
	private FBType sourceType;
	List<FBType> blocksToAdd;

	public CompositeFBGenerator(FBType type, TestSuite testSuite, List<FBType> blocksToAdd) {
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
		final IFile destfile = folder.getFile(sourceType.getName() + "_COMPOSITE.fbt");  //$NON-NLS-1$
		
		TypeEntry entry  = sourceType.getTypeLibrary().createTypeEntry(destfile);
		entry.setType(compositeFB);

		
		
		FBNetwork net = LibraryElementFactory.eINSTANCE.createFBNetwork();
		for(int i = 0; i < blocksToAdd.size(); i++) {
			FBNetworkElement el = LibraryElementFactory.eINSTANCE.createFB();
			el.setTypeEntry(sourceType.getTypeLibrary().getFBTypeEntry(blocksToAdd.get(i).getName()));
			final Position p0 = LibraryElementFactory.eINSTANCE.createPosition();
			p0.setX(50+50*i);
			p0.setY(50+50*i);
			el.setPosition(p0);
			el.setInterface(EcoreUtil.copy(el.getType().getInterfaceList()));
			net.getNetworkElements().add(el);
			el.setName(NameRepository.createUniqueName(el, "TESTAPPFB1"));
			
		}
		
		compositeFB.setFBNetwork(net);
		
		createEvents();
		createSomeConnections();
		
		return compositeFB;
	}
	
	private void createSomeConnections() {
		EventConnection evCon = LibraryElementFactory.eINSTANCE.createEventConnection();
		evCon.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());
		
		evCon.setSource(compositeFB.getFBNetwork().getFBNamed(sourceType.getName() + "_MATCH"). // Instanznamen
				getInterface().getEventInputs().get(0));
		evCon.setDestination(compositeFB.getFBNetwork().getFBNamed(sourceType.getName() + "_TEST").
				getInterface().getEventInputs().get(0));
		compositeFB.getFBNetwork().getEventConnections().add(evCon);
		
	}

	private void createEvents() {
		for (TestCase testCase : testSuite.getTestCases()) {
			Event ev = LibraryElementFactory.eINSTANCE.createEvent();
			ev.setName(testCase.getName() + "_TEST");
			ev.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
			ev.setIsInput(true);
			compositeFB.getInterfaceList().getEventInputs().add(EcoreUtil.copy(ev));
		}
		
	}
}
