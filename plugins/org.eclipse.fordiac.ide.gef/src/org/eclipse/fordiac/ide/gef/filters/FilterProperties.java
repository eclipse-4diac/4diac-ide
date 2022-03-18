/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.filters;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class FilterProperties {

	public static boolean isSubapp(Object o) {
		return o instanceof SubApp;
	}

	public static boolean isSubappPin(Object o) {
		if (o instanceof IInterfaceElement) {
			final IInterfaceElement pin = (IInterfaceElement) o;
			final FBNetworkElement fbEl = pin.getFBNetworkElement();
			return isSubapp(fbEl);
		}
		return false;
	}

	public static boolean isAdapter(Object o) {
		return o instanceof AdapterDeclaration;
	}

	public static boolean isDataPin(Object o) {
		if (o instanceof Value) {
			return isDataPin(((Value) o).getVarDeclaration());
		}
		return o instanceof VarDeclaration && !(o instanceof AdapterDeclaration);
	}

	public static boolean isEventPin(Object o) {
		return o instanceof Event;
	}

	public static boolean isTypedSubappPin(Object o) {
		if (isSubappPin(o)) {
			final FBNetworkElement fbNetworkElement = ((IInterfaceElement) o).getFBNetworkElement();
			return isTyped(fbNetworkElement);
		}
		return false;
	}

	private static boolean isTyped(final FBNetworkElement fbNetworkElement) {
		return fbNetworkElement.getType() != null || fbNetworkElement.isContainedInTypedInstance();
	}

	public static boolean isUntypedSubappPin(Object o) {
		if (isSubappPin(o)) {
			final FBNetworkElement fbNetworkElement = ((IInterfaceElement) o).getFBNetworkElement();
			return !isTyped(fbNetworkElement);

		}
		return false;
	}

	private FilterProperties() {
		throw new UnsupportedOperationException();
	}
}
