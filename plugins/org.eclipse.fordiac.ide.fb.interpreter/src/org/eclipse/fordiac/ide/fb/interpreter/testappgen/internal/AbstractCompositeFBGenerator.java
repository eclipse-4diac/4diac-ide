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

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public abstract class AbstractCompositeFBGenerator extends AbstractBlockGenerator {
	protected CompositeFBType compositeFB;

	protected AbstractCompositeFBGenerator(final FBType sourceType) {
		super(sourceType);
	}

	protected void createCompositeFB() {
		compositeFB = LibraryElementFactory.eINSTANCE.createCompositeFBType();
		configureBlock(compositeFB);
		createFile(compositeFB);

		addFBsToNetwork();
		setValuesForFBs();
		createEvents();
		createData();
		createConnections();
		createWiths();
	}

	protected static void setValue(final EList<VarDeclaration> vars) {
		for (final VarDeclaration varD : vars) {
			if (varD.getValue() == null) {
				varD.setValue(LibraryElementFactory.eINSTANCE.createValue());
				varD.getValue().setValue(""); //$NON-NLS-1$
			}
		}
	}

	protected static EventConnection createEventConnection(final Event source, final Event dest) {
		final EventConnection con = LibraryElementFactory.eINSTANCE.createEventConnection();
		con.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());
		con.setSource(source);
		con.setDestination(dest);
		return con;
	}

	protected static DataConnection createDataConnection(final VarDeclaration source, final VarDeclaration dest) {
		final DataConnection con = LibraryElementFactory.eINSTANCE.createDataConnection();
		con.setRoutingData(LibraryElementFactory.eINSTANCE.createConnectionRoutingData());
		con.setSource(source);
		con.setDestination(dest);
		return con;
	}

	protected static FB addFBToNetwork(final FBNetwork net, final FBType blockToAdd, final int x, final int y) {

		final FBNetworkElement el = LibraryElementFactory.eINSTANCE.createFB();
		el.setTypeEntry(blockToAdd.getTypeEntry());
		addPosition(el, x, y);
		el.setInterface(blockToAdd.getInterfaceList().copy());

		net.getNetworkElements().add(el);
		final String name = NameRepository.createUniqueName(el, "TESTAPPFB1"); //$NON-NLS-1$
		el.setName(name);
		return net.getFBNamed(name);

	}

	// some values might be null, so to be sure every value gets set again
	protected void setValuesForFBs() {
		compositeFB.getFBNetwork().getNetworkElements().stream()
				.forEach(n -> setValue(n.getInterface().getInputVars()));
		compositeFB.getFBNetwork().getNetworkElements().stream()
				.forEach(n -> setValue(n.getInterface().getOutputVars()));
	}

	protected abstract void createWiths();

	protected abstract void createConnections();

	protected abstract void createData();

	protected abstract void createEvents();

	protected abstract void addFBsToNetwork();

}
