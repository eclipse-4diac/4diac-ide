/*******************************************************************************
 * Copyright (c) 2011, 2017 TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.typemanagement.navigator;

import java.util.ArrayList;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;

class FBTypeComposedAdapterFactory {
	
	private static ComposedAdapterFactory fbTypeCompAdapterFactory;
	
	final static ComposedAdapterFactory getAdapterFactory(){
		if (fbTypeCompAdapterFactory == null){
			fbTypeCompAdapterFactory = new ComposedAdapterFactory(createFactoryList());
		}
		return fbTypeCompAdapterFactory;
	}
	
	private final static ArrayList<AdapterFactory> createFactoryList(){
		ArrayList<AdapterFactory> factories = new ArrayList<AdapterFactory>();
		factories.add(new LibraryElementItemProviderAdapterFactory());
		factories.add(new DataItemProviderAdapterFactory());
		return factories;
	}
}
