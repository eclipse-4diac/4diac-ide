/*******************************************************************************
 * Copyright (c) 2015, 2016 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.provider.ApplicationItemProvider;
import org.eclipse.fordiac.ide.model.libraryElement.provider.FBNetworkItemProvider;

/** a dedicated item provider that will ensure that in the system tree the application will have the content of the application
 * without the intermediate fbnetwork node shown.
 * 
 * @author alil
 *
 */
public class ApplicationItemProviderForSystem extends
		ApplicationItemProvider implements INotifyChangedListener {
	
	private FBNetworkItemProvider fbNetworkItemProvider = null;

	public ApplicationItemProviderForSystem(AdapterFactory adapterFactory) {
		super(adapterFactory);
		fbNetworkItemProvider = new FBNetworkItemProvider(adapterFactory){

			@Override
			public void fireNotifyChanged(Notification notification) {
				FBNetwork network = (FBNetwork)notification.getNotifier();
				Notification wrappedNotification = ViewerNotification.wrapNotification(notification, network.eContainer()); 
				super.fireNotifyChanged(wrappedNotification);
			}
			
		};
	}

	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(
			Object object) {
		return fbNetworkItemProvider.getChildrenFeatures(getFBNetwork(object));
	}

	@Override
	public Collection<?> getChildren(Object object) {		
		return fbNetworkItemProvider.getChildren(getFBNetwork(object));
	}

	@Override
	public boolean hasChildren(Object object) {
		return fbNetworkItemProvider.hasChildren(getFBNetwork(object));
	}

	private FBNetwork getFBNetwork(Object object) {
		FBNetwork fbNetwork = ((Application)object).getFBNetwork();
		if(!fbNetwork.eAdapters().contains(fbNetworkItemProvider)){
			//register to the fbnetwork changes so that the viewer is updated
			fbNetwork.eAdapters().add(fbNetworkItemProvider);
		}
		return fbNetwork;
	}
	
}
