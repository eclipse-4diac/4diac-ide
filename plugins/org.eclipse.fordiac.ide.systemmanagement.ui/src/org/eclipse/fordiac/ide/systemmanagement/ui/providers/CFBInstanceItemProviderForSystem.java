/*******************************************************************************
 * Copyright (c) 2015, 2021 fortiss GmbH, Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *                 (Based on SubAppItmeProviderForSystem
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.providers;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.provider.CFBInstanceItemProvider;
import org.eclipse.fordiac.ide.model.libraryElement.provider.FBNetworkItemProvider;

/**
 * a dedicated item provider that will ensure that in the system tree the CFB
 * instances will have the content of the fbnetwork without the intermediate
 * FBnetwork node shown.
 *
 * @author alil
 */
public class CFBInstanceItemProviderForSystem extends CFBInstanceItemProvider {

	private FBNetworkItemProvider networkItemProvider = null;

	public CFBInstanceItemProviderForSystem(final AdapterFactory adapterFactory) {
		super(adapterFactory);
		networkItemProvider = new FBNetworkItemProviderForSystem(adapterFactory);
	}

	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(final Object object) {
		final FBNetwork fbNetwork = getFBNetwork(object);
		return (null != fbNetwork) ? networkItemProvider.getChildrenFeatures(fbNetwork) : Collections.emptyList();
	}

	@Override
	public Collection<?> getChildren(final Object object) {
		final FBNetwork fbNetwork = getFBNetwork(object);
		return (null != fbNetwork) ? networkItemProvider.getChildren(fbNetwork) : Collections.emptyList();
	}

	@Override
	public boolean hasChildren(final Object object) {
		// for CFB instances we assume that we have children
		return true;
	}

	@Override
	public Object getParent(final Object object) {
		final EObject cont = ((EObject) object).eContainer();
		if (cont instanceof final FBNetwork fbn) {
			return fbn.eContainer();
		}
		return super.getParent(object);
	}

	protected FBNetwork getFBNetwork(final Object object) {
		final CFBInstance cfbInstance = ((CFBInstance) object);
		FBNetwork fbNetwork = cfbInstance.getCfbNetwork();
		if (null == fbNetwork) {
			// the fb network is not yet loaded load it now
			fbNetwork = cfbInstance.loadCFBNetwork();
		}
		if (null != fbNetwork && !fbNetwork.eAdapters().contains(networkItemProvider)) {
			// register to the subappnetwork changes so that the viewer is updated
			fbNetwork.eAdapters().add(networkItemProvider);
		}
		return fbNetwork;
	}
}
