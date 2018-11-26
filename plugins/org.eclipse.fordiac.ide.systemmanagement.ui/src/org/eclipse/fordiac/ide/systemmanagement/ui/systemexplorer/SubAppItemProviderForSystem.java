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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.provider.FBNetworkItemProvider;
import org.eclipse.fordiac.ide.model.libraryElement.provider.SubAppItemProvider;

/** a dedicated item provider that will ensure that in the system tree the subapplication will have the 
 * content of the subapp without the intermediate subappnetwork node shown.
 * 
 * @author alil
 *
 */
public class SubAppItemProviderForSystem extends SubAppItemProvider {
	FBNetworkItemProvider subAppNetworkItemProvider = null;

	public SubAppItemProviderForSystem(AdapterFactory adapterFactory) {
		super(adapterFactory);
		subAppNetworkItemProvider = new FBNetworkItemProvider(adapterFactory){

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
		return subAppNetworkItemProvider.getChildrenFeatures(getFBNetwork(object));
	}

	@Override
	public Collection<?> getChildren(Object object) {		
		return subAppNetworkItemProvider.getChildren(getFBNetwork(object));
	}

	@Override
	public boolean hasChildren(Object object) {
		FBNetwork fbNetwork = getFBNetwork(object);
		return (null != fbNetwork) ? subAppNetworkItemProvider.hasChildren(fbNetwork) : false;
	}

	@Override
	public Object getParent(Object object) {
		EObject cont = ((SubApp)object).eContainer();
		if(cont instanceof FBNetwork){
			return ((FBNetwork)cont).eContainer();
		}
		return super.getParent(object);
	}

	private FBNetwork getFBNetwork(Object object) {
		FBNetwork subAppNetwork = ((SubApp)object).getSubAppNetwork();
		if(null != subAppNetwork && !subAppNetwork.eAdapters().contains(subAppNetworkItemProvider)){
			//register to the subappnetwork changes so that the viewer is updated
			subAppNetwork.eAdapters().add(subAppNetworkItemProvider);
		}
		return subAppNetwork;
	}
}
