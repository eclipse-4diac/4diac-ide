/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.emf.compare.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.fordiac.ide.model.edit.providers.FBNetworkItemProviderForSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;

public class FBNetworkItemProviderEmfCompare extends FBNetworkItemProviderForSystem {

	public FBNetworkItemProviderEmfCompare(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public Object getParent(final Object object) {
		if (object instanceof FBNetwork) {
			return ((FBNetwork) object).eContainer();
		}
		return super.getParent(object);
	}

	@Override
	public Collection<?> getChildren(final Object object) {
		final Collection<Object> children = new ArrayList<>();
		children.addAll(((FBNetwork) object).getNetworkElements());
		children.addAll(((FBNetwork) object).getEventConnections());
		children.addAll(((FBNetwork) object).getDataConnections());
		children.addAll(((FBNetwork) object).getAdapterConnections());
		return children;
	}

}
