/*******************************************************************************
 * Copyright (c) 2021, 2024 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst - add helper to find children by simple name
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.annotations;

import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.impl.NamedElementAnnotations;

public final class SubAppAnnotations {

	public static FBNetwork loadSubAppNetwork(final TypedSubApp subApp) {
		FBNetwork subAppNetwork = subApp.getSubAppNetwork();
		if (null == subAppNetwork) {
			subAppNetwork = FBNetworkHelper.copyFBNetWork(subApp.getType().getFBNetwork(), subApp.getInterface());
			subApp.setSubAppNetwork(subAppNetwork);
			subAppNetwork.getEventConnections().forEach(EventConnection::checkIfConnectionBroken);
			subAppNetwork.getDataConnections().forEach(DataConnection::checkIfConnectionBroken);
			subAppNetwork.getAdapterConnections().forEach(AdapterConnection::checkIfConnectionBroken);
		}
		return subAppNetwork;
	}

	public static Stream<INamedElement> findBySimpleName(final TypedSubApp root, final String name) {
		loadSubAppNetwork(root); // ensure network is loaded
		return NamedElementAnnotations.findBySimpleName(root, name);
	}

	private SubAppAnnotations() {
		throw new UnsupportedOperationException("The utility class SubAppAnnotations should not be instatiated"); //$NON-NLS-1$
	}

}
