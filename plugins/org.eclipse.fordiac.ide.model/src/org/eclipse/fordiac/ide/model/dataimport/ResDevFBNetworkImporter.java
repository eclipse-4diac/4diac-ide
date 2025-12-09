/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   			 - Changed XML parsing to Staxx cursor interface for improved
 *  			   parsing performance
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.dataimport.ConnectionHelper.ConnectionBuilder;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

class ResDevFBNetworkImporter extends SubAppNetworkImporter {

	private final EList<VarDeclaration> varInputs;
	private final List<ConnectionBuilder<Connection>> brokenConnections = new ArrayList<>();

	ResDevFBNetworkImporter(final CommonElementImporter importer, final EList<VarDeclaration> varInputs) {
		super(importer);
		this.varInputs = varInputs;
	}

	ResDevFBNetworkImporter(final CommonElementImporter importer, final FBNetwork fbNetwork,
			final EList<VarDeclaration> varInputs, final Map<String, FBNetworkElement> fbNetworkElementMap) {
		super(importer, fbNetwork, fbNetworkElementMap);
		this.varInputs = varInputs;
	}

	public List<ConnectionBuilder<Connection>> getBrokenConnections() {
		return brokenConnections;
	}

	@Override
	IInterfaceElement getContainingInterfaceElement(final String interfaceElement, final EClass conType,
			final boolean isInput) {
		for (final VarDeclaration inVar : varInputs) {
			if (inVar.getName().equals(interfaceElement)) {
				return inVar;
			}
		}
		return null;
	}

	@Override
	protected void addFBNetworkElement(final FBNetworkElement element) {
		String name = element.getName();
		final var separator = name.lastIndexOf('.');
		if (separator != -1) {
			name = name.substring(separator + 1);
		}
		// insert element with long name
		super.addFBNetworkElement(element);
		// update name to the correct short one
		element.setName(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T extends Connection> T parseConnection(final EClass conType)
			throws XMLStreamException, TypeImportException {
		final ConnectionBuilder<T> builder = ConnectionHelper.createConnectionBuilder(conType, this);
		builder.validate();

		if (builder.isMissingConnectionDestination() || builder.isMissingConnectionSource()) {
			// potential connection to a mapped fb, store for retry after mapping
			// information is created
			brokenConnections.add((ConnectionBuilder<Connection>) builder);
			return null;
		}

		builder.handleErrorCases();
		return builder.getConnection();
	}

}
