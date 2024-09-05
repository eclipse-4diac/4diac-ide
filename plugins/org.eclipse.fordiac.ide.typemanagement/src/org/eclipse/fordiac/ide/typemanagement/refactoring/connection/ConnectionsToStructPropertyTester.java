/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mathias Garstenauer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.connection;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * A PropertyTester for the
 * {@link org.eclipse.fordiac.ide.typemanagement.refactoring.connection.ConnectionsToStructRefactoring
 * ConnectionsToStructRefactoring}. It checks if the Refactoring is applicable
 * on the current selection. The Property is only set if:
 * <ul>
 * <li>there are only
 * {@link org.eclipse.fordiac.ide.model.libraryElement.DataConnection
 * DataConnections} are selected</li>
 * <li>the Connections all connect the same two
 * {@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement
 * FBNetworkElements}</li>
 * <li>the Connections do not connect InOutVars</li>
 * <li>the FBNetworkElements are neither a SubApp or a
 * ServiceInterfaceFBType</li>
 * </ul>
 */
public class ConnectionsToStructPropertyTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		FBNetworkElement dest = null;
		FBNetworkElement src = null;

		if (!(receiver instanceof final IStructuredSelection selection)) {
			return false;
		}

		final DataConnection firstCon = checkCon(selection.getFirstElement());
		if (firstCon != null) {
			dest = firstCon.getDestinationElement();
			src = firstCon.getSourceElement();
		}

		for (final Object sel : selection) {
			final DataConnection con = checkCon(sel);
			if (con == null || !checkVar(con.getSource(), src) || !checkVar(con.getDestination(), dest)) {
				return false;
			}
		}
		return src != null && !(src instanceof SubApp) && !(src.getType() instanceof ServiceInterfaceFBType)
				&& dest != null && !(dest instanceof SubApp) && !(dest.getType() instanceof ServiceInterfaceFBType);
	}

	private static DataConnection checkCon(final Object sel) {
		if (sel instanceof final EditPart part && part.getModel() instanceof final DataConnection con) {
			return con;
		}
		return null;
	}

	private static boolean checkVar(final IInterfaceElement element, final FBNetworkElement src) {
		return element instanceof final VarDeclaration vars && !vars.isInOutVar()
				&& element.getFBNetworkElement() == src;
	}

}
