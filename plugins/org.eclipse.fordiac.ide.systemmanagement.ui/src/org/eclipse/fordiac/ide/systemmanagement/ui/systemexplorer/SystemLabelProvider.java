/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.navigator.IDescriptionProvider;

public class SystemLabelProvider extends AdapterFactoryLabelProvider implements IDescriptionProvider{

	private static ComposedAdapterFactory systemAdapterFactory = new ComposedAdapterFactory(createFactoryList());
	
	private ILabelProvider workbenchLabelProvider = WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider();
	
	
	public SystemLabelProvider() {
		super(systemAdapterFactory);
	}

	@Override
	public void dispose() {
		workbenchLabelProvider.dispose();
		super.dispose();
	}

	@Override
	public String getText(Object object) {
		if(object instanceof IResource){
			return null;
		}
		return super.getText(object);
	}

	@Override
	public Image getImage(Object object) {
		if(object instanceof AutomationSystem){
			return FordiacImage.ICON_SystemPerspective.getImage();
		}
		if(object instanceof IResource){
			return null;
		}
		return super.getImage(object);
	}


	@Override
	public String getDescription(Object anElement) {
		// TODO provide descriptive tooltip text here
		return super.getText(anElement);
	}

	private static List<AdapterFactory> createFactoryList(){
		ArrayList<AdapterFactory> factories = new ArrayList<AdapterFactory>();
		factories.add(new LibraryElementItemProviderAdapterFactory());
		factories.add(new DataItemProviderAdapterFactory());
		return factories;
	}
}
