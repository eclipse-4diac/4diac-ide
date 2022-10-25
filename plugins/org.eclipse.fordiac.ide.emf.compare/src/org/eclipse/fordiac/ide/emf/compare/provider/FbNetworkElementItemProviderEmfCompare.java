/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.emf.compare.provider;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.provider.FBNetworkElementItemProvider;

public class FbNetworkElementItemProviderEmfCompare extends FBNetworkElementItemProvider {

	public FbNetworkElementItemProviderEmfCompare(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public boolean hasChildren(final Object object) {
		return true;
	}

	@Override
	public Collection<?> getChildren(final Object object) {
		final FBNetworkElement element = ((FBNetworkElement) object);
		return element.getInterface().getAllInterfaceElements();
	}

	@Override
	public Object getParent(final Object object) {
		if (object instanceof InterfaceList) {
			return ((InterfaceList) object).eContainer();
		}

		return super.getParent(object);
	}
}
