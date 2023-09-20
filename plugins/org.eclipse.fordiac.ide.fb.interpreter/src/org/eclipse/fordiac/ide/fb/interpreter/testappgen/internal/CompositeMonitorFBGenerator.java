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

public class CompositeMonitorFBGenerator {
	private CompositeFBType compositeFB;
	private final FBType sourceType;
	FB addedSourceType;
	private final List<FBType> monitorFBs;
	List<FB> addedMonitorFBs = new ArrayList<>();

	public CompositeMonitorFBGenerator(final FBType type, final List<FBType> monitorFBs) {
		sourceType = type;
		this.monitorFBs = monitorFBs;
	}

	public CompositeFBType generateCompositeFB() {
		compositeFB = LibraryElementFactory.eINSTANCE.createCompositeFBType();
		final Identification id = LibraryElementFactory.eINSTANCE.createIdentification();
		compositeFB.setIdentification(id);
		id.setStandard("IEC 61499"); //$NON-NLS-1$

		compositeFB.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		compositeFB.setName(sourceType.getName() + "_MONITOR_COMPOSITE"); //$NON-NLS-1$

		compositeFB.setService(LibraryElementFactory.eINSTANCE.createService());

		final IProject project = sourceType.getTypeLibrary().getProject();
		final String s = sourceType.getTypeEntry().getFile().getFullPath().toString();
		final IFolder folder = project.getFolder(s.substring(project.getName().length() + 2, s.lastIndexOf('/')));
		final IFile destfile = folder.getFile(sourceType.getName() + "_COMPOSITE.fbt"); //$NON-NLS-1$

		final TypeEntry entry = sourceType.getTypeLibrary().createTypeEntry(destfile);
		entry.setType(compositeFB);

		createInterface();
		addFBsToNetwork();

		setValuesForFBs();
		createConnections();
//		createWiths();

		return compositeFB;
	}

	private void setValuesForFBs() {
		compositeFB.getFBNetwork().getNetworkElements().stream()
				.forEach(n -> setValue(n.getInterface().getInputVars()));
		compositeFB.getFBNetwork().getNetworkElements().stream()
				.forEach(n -> setValue(n.getInterface().getOutputVars()));
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
		// Connection SourceType
		for (int i = 0; i < sourceType.getInterfaceList().getEventInputs().size(); i++) {
			compositeFB.getFBNetwork().getEventConnections()
					.add(createEventConnection(compositeFB.getInterfaceList().getEventInputs().get(i),
							addedSourceType.getInterface().getEventInputs().get(i)));
		}
		for (int i = 0; i < sourceType.getInterfaceList().getEventOutputs().size(); i++) {
			compositeFB.getFBNetwork().getEventConnections()
					.add(createEventConnection(addedSourceType.getInterface().getEventOutputs().get(i),
							compositeFB.getInterfaceList().getEventOutputs().get(i)));
		}
		for (int i = 0; i < sourceType.getInterfaceList().getInputVars().size(); i++) {
			compositeFB.getFBNetwork().getDataConnections()
					.add(createDataConnection(compositeFB.getInterfaceList().getInputVars().get(i),
							addedSourceType.getInterface().getInputVars().get(i)));
		}
		for (int i = 0; i < sourceType.getInterfaceList().getOutputVars().size(); i++) {
			compositeFB.getFBNetwork().getDataConnections()
					.add(createDataConnection(addedSourceType.getInterface().getOutputVars().get(i),
							compositeFB.getInterfaceList().getOutputVars().get(i)));
		}

		// Connection monitorFBs
		for (final FB fb : addedMonitorFBs) {
			for (int i = 0; i < compositeFB.getInterfaceList().getEventInputs().size(); i++) {
				compositeFB.getFBNetwork().getEventConnections()
						.add(createEventConnection(compositeFB.getInterfaceList().getEventInputs().get(i),
								fb.getInterface().getEventInputs().get(i)));
			}
			for (int i = 0; i < sourceType.getInterfaceList().getEventOutputs().size(); i++) {
				compositeFB.getFBNetwork().getEventConnections().add(
						createEventConnection(addedSourceType.getInterface().getEventOutputs().get(i), fb.getInterface()
								.getEventInputs().get(i + sourceType.getInterfaceList().getEventInputs().size())));
			}
			for (int i = 0; i < compositeFB.getInterfaceList().getInputVars().size(); i++) {
				compositeFB.getFBNetwork().getDataConnections().add(createDataConnection(
						compositeFB.getInterfaceList().getInputVars().get(i), fb.getInterface().getInputVars().get(i)));
			}
			for (int i = 0; i < sourceType.getInterfaceList().getOutputVars().size(); i++) {
				compositeFB.getFBNetwork().getDataConnections().add(
						createDataConnection(addedSourceType.getInterface().getOutputVars().get(i), fb.getInterface()
								.getInputVars().get(i + compositeFB.getInterfaceList().getInputVars().size())));
			}
		}
		int fbCnt = 0;
		for (int i = sourceType.getInterfaceList().getEventOutputs().size(); i < compositeFB.getInterfaceList()
				.getEventOutputs().size(); i++) {

			compositeFB.getFBNetwork().getEventConnections()
					.add(createEventConnection(addedMonitorFBs.get(fbCnt).getInterface().getEventOutputs().get(0),
							compositeFB.getInterfaceList().getEventOutputs().get(i)));
			fbCnt++;
		}
	}

	private void createInterface() {
		// Event Interface
		compositeFB.getInterfaceList().getEventInputs().addAll(sourceType.getInterfaceList().getEventInputs().stream()
				.map(n -> (AbstractFBGenerator.createEvent(n.getName(), true))).toList());
		compositeFB.getInterfaceList().getEventOutputs().addAll(sourceType.getInterfaceList().getEventOutputs().stream()
				.map(n -> (AbstractFBGenerator.createEvent(n.getName(), false))).toList());
		compositeFB.getInterfaceList().getEventOutputs().addAll(monitorFBs.stream()
				.map(n -> (AbstractFBGenerator.createEvent(
						n.getName().substring(sourceType.getName().length() + 1, n.getName().length() - 8), false)))
				.toList());

		// Data Interface
		compositeFB.getInterfaceList().getInputVars().addAll(sourceType.getInterfaceList().getInputVars().stream()
				.map(n -> AbstractFBGenerator.createVarDeclaration(n, n.getName(), true)).toList());
		compositeFB.getInterfaceList().getOutputVars().addAll(sourceType.getInterfaceList().getOutputVars().stream()
				.map(n -> (AbstractFBGenerator.createVarDeclaration(n, n.getName(), false))).toList());

	}

	private void addFBsToNetwork() {
		final FBNetwork net = LibraryElementFactory.eINSTANCE.createFBNetwork();
		compositeFB.setFBNetwork(net);
		final FBNetworkElement el = LibraryElementFactory.eINSTANCE.createFB();
		el.setTypeEntry(sourceType.getTypeEntry());
		final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
		pos.setX(400);
		pos.setY(0);
		el.setPosition(pos);
		el.setInterface(EcoreUtil.copy(el.getType().getInterfaceList()));
		net.getNetworkElements().add(el);
		final String name = NameRepository.createUniqueName(el, "TESTAPPFB1"); //$NON-NLS-1$
		el.setName(name);
		addedSourceType = compositeFB.getFBNetwork().getFBNamed(name);

		for (int i = 0; i < monitorFBs.size(); i++) {
			final FBNetworkElement monFB = LibraryElementFactory.eINSTANCE.createFB();
			monFB.setTypeEntry(monitorFBs.get(i).getTypeEntry());
			final Position p = LibraryElementFactory.eINSTANCE.createPosition();
			p.setX(400);
			p.setY(300 * (i + 1));
			monFB.setPosition(p);
			monFB.setInterface(EcoreUtil.copy(monFB.getType().getInterfaceList()));
			net.getNetworkElements().add(monFB);
			final String monName = NameRepository.createUniqueName(monFB, "TESTAPPFB1"); //$NON-NLS-1$
			monFB.setName(monName);
			addedMonitorFBs.add(compositeFB.getFBNetwork().getFBNamed(monName));
		}
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
}
