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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.edit.providers;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.provider.FBNetworkItemProvider;

public class FBNetworkItemProviderForSystem extends FBNetworkItemProvider {
	public FBNetworkItemProviderForSystem(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public void fireNotifyChanged(final Notification notification) {
		final FBNetwork network = (FBNetwork) notification.getNotifier();
		final Notification wrappedNotification = ViewerNotification.wrapNotification(notification,
				network.eContainer());
		super.fireNotifyChanged(wrappedNotification);
	}
}