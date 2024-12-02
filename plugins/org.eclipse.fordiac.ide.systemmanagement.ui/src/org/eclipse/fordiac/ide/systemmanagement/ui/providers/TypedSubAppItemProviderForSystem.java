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
package org.eclipse.fordiac.ide.systemmanagement.ui.providers;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.provider.FBNetworkItemProvider;
import org.eclipse.fordiac.ide.model.libraryElement.provider.TypedSubAppItemProvider;

/**
 * a dedicated item provider that will ensure that in the system tree the
 * subapplication will have the content of the subapp without the intermediate
 * subappnetwork node shown.
 *
 * @author alil
 *
 */
public class TypedSubAppItemProviderForSystem extends TypedSubAppItemProvider {

	private FBNetworkItemProvider subAppNetworkItemProvider = null;

	public TypedSubAppItemProviderForSystem(final AdapterFactory adapterFactory) {
		super(adapterFactory);
		subAppNetworkItemProvider = new FBNetworkItemProviderForSystem(adapterFactory);
	}

	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(final Object object) {
		final FBNetwork fbNetwork = getFBNetwork(object);
		return (null != fbNetwork) ? subAppNetworkItemProvider.getChildrenFeatures(fbNetwork) : Collections.emptyList();
	}

	@Override
	public Collection<?> getChildren(final Object object) {
		final FBNetwork fbNetwork = getFBNetwork(object);
		return (null != fbNetwork) ? subAppNetworkItemProvider.getChildren(fbNetwork) : Collections.emptyList();
	}

	@Override
	public boolean hasChildren(final Object object) {
		if (((SubApp) object).isTyped()) {
			// if we are typed subapp we always say we have children, avoids early copying
			// of type network
			return true;
		}
		final FBNetwork fbNetwork = getFBNetwork(object);
		return ((null != fbNetwork) && subAppNetworkItemProvider.hasChildren(fbNetwork));
	}

	@Override
	public Object getParent(final Object object) {
		final EObject cont = ((SubApp) object).eContainer();
		if (cont instanceof final FBNetwork fbn) {
			return fbn.eContainer();
		}
		return super.getParent(object);
	}

	protected FBNetwork getFBNetwork(final Object object) {
		final SubApp subapp = ((SubApp) object);
		FBNetwork subAppNetwork = subapp.getSubAppNetwork();
		if ((null == subAppNetwork) && subapp.isTyped()) {
			// the subapp currently does not have a subappnetwork and it is typed, copy the
			// subapp network from the type
			// to the instance
			subAppNetwork = subapp.loadSubAppNetwork();
		}
		if (null != subAppNetwork && !subAppNetwork.eAdapters().contains(subAppNetworkItemProvider)) {
			// register to the subappnetwork changes so that the viewer is updated
			subAppNetwork.eAdapters().add(subAppNetworkItemProvider);
		}
		return subAppNetwork;
	}

	protected FBNetworkItemProvider getSubAppNetworkItemProvider() {
		return subAppNetworkItemProvider;
	}

	@Override
	public void notifyChanged(final Notification notification) {
		if (LibraryElementPackage.TYPED_CONFIGUREABLE_OBJECT__TYPE_ENTRY == notification
				.getFeatureID(TypedConfigureableObject.class)) {
			// if the type entry get change inform viewer to update the parent's children,
			// this ensures that any
			// filters that may be enabled are applied
			final SubApp subapp = (SubApp) notification.getNotifier();
			final Notification wrappedNotification = ViewerNotification.wrapNotification(notification,
					subapp.eContainer().eContainer());
			fireNotifyChanged(wrappedNotification);
		} else {
			super.notifyChanged(notification);
		}
	}
}
