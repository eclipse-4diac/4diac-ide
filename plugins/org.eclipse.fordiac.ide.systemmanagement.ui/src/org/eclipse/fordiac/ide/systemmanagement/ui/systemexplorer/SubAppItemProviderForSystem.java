/*******************************************************************************
 * Copyright (c) 2015, 2016 fortiss GmbH
 *               2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - added defensive checks when the fbnetwork is null
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.provider.FBNetworkItemProvider;
import org.eclipse.fordiac.ide.model.libraryElement.provider.SubAppItemProvider;

/**
 * a dedicated item provider that will ensure that in the system tree the
 * subapplication will have the content of the subapp without the intermediate
 * subappnetwork node shown.
 *
 * @author alil
 *
 */
public class SubAppItemProviderForSystem extends SubAppItemProvider {

	private FBNetworkItemProvider subAppNetworkItemProvider = null;

	public SubAppItemProviderForSystem(AdapterFactory adapterFactory) {
		super(adapterFactory);
		subAppNetworkItemProvider = new FBNetworkItemProvider(adapterFactory) {

			@Override
			public void fireNotifyChanged(Notification notification) {
				FBNetwork network = (FBNetwork) notification.getNotifier();
				Notification wrappedNotification = ViewerNotification.wrapNotification(notification,
						network.eContainer());
				super.fireNotifyChanged(wrappedNotification);
			}

		};
	}

	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		FBNetwork fbNetwork = getFBNetwork(object);
		return (null != fbNetwork) ? subAppNetworkItemProvider.getChildrenFeatures(fbNetwork) : Collections.emptyList();
	}

	@Override
	public Collection<?> getChildren(Object object) {
		FBNetwork fbNetwork = getFBNetwork(object);
		return (null != fbNetwork) ? subAppNetworkItemProvider.getChildren(fbNetwork) : Collections.emptyList();
	}

	@Override
	public boolean hasChildren(Object object) {
		FBNetwork fbNetwork = getFBNetwork(object);
		return ((null != fbNetwork) && subAppNetworkItemProvider.hasChildren(fbNetwork));
	}

	@Override
	public Object getParent(Object object) {
		EObject cont = ((SubApp) object).eContainer();
		if (cont instanceof FBNetwork) {
			return ((FBNetwork) cont).eContainer();
		}
		return super.getParent(object);
	}

	private FBNetwork getFBNetwork(Object object) {
		FBNetwork subAppNetwork = ((SubApp) object).getSubAppNetwork();
		if (null != subAppNetwork && !subAppNetwork.eAdapters().contains(subAppNetworkItemProvider)) {
			// register to the subappnetwork changes so that the viewer is updated
			subAppNetwork.eAdapters().add(subAppNetworkItemProvider);
		}
		return subAppNetwork;
	}
}
