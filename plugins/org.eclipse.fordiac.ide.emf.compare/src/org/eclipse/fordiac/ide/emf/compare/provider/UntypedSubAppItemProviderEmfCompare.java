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
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.emf.compare.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.edit.providers.UntypedSubAppItemProviderForSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class UntypedSubAppItemProviderEmfCompare extends UntypedSubAppItemProviderForSystem {

	public UntypedSubAppItemProviderEmfCompare(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	protected FBNetwork getFBNetwork(final Object object) {
		final SubApp subapp = ((SubApp) object);
		if (subapp.isTyped()) {
			return null;
		}
		final FBNetwork subAppNetwork = subapp.getSubAppNetwork();
		if (null != subAppNetwork && !subAppNetwork.eAdapters().contains(getSubAppNetworkItemProvider())) {
			// register to the subappnetwork changes so that updates are propagated
			subAppNetwork.eAdapters().add(getSubAppNetworkItemProvider());
		}
		return subAppNetwork;
	}

	@Override
	public Object getParent(final Object object) {
		final EObject cont = ((SubApp) object).eContainer();
		if (cont != null) {
			return cont;
		}
		return super.getParent(object);
	}

	@Override
	public Collection<?> getChildren(final Object object) {
		final Collection<Object> children = new ArrayList<>();
		children.add(((SubApp) object).getSubAppNetwork());
		if (!((SubApp) object).getInterface().getAllInterfaceElements().isEmpty()) {
			children.add(((SubApp) object).getInterface());
		}
		return children;
	}

}
