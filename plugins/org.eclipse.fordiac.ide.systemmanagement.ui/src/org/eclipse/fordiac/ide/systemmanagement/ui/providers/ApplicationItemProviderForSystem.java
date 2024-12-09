/*******************************************************************************
 * Copyright (c) 2015, 2016 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.providers;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.fordiac.ide.model.edit.providers.FBNetworkItemProviderForSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.provider.ApplicationItemProvider;
import org.eclipse.fordiac.ide.model.libraryElement.provider.FBNetworkItemProvider;

/**
 * a dedicated item provider that will ensure that in the system tree the
 * application will have the content of the application without the intermediate
 * fbnetwork node shown.
 *
 * @author alil
 *
 */
public class ApplicationItemProviderForSystem extends ApplicationItemProvider implements INotifyChangedListener {

	private FBNetworkItemProvider fbNetworkItemProvider = null;

	public ApplicationItemProviderForSystem(final AdapterFactory adapterFactory) {
		super(adapterFactory);
		fbNetworkItemProvider = new FBNetworkItemProviderForSystem(adapterFactory);
	}

	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(final Object object) {
		return fbNetworkItemProvider.getChildrenFeatures(getFBNetwork(object));
	}

	@Override
	public Collection<?> getChildren(final Object object) {
		return fbNetworkItemProvider.getChildren(getFBNetwork(object));
	}

	@Override
	public boolean hasChildren(final Object object) {
		return fbNetworkItemProvider.hasChildren(getFBNetwork(object));
	}

	private FBNetwork getFBNetwork(final Object object) {
		final FBNetwork fbNetwork = ((Application) object).getFBNetwork();
		if (!fbNetwork.eAdapters().contains(fbNetworkItemProvider)) {
			// register to the fbnetwork changes so that the viewer is updated
			fbNetwork.eAdapters().add(fbNetworkItemProvider);
		}
		return fbNetwork;
	}

}
