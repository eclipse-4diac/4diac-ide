/**
 * *******************************************************************************
 *  * Copyright (c) 2007 - 2011 4DIAC - consortium.
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Eclipse Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement.provider;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.fordiac.ide.model.Palette.provider.fordiacEditPlugin;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

public class TransientLibraryElementItemProvider extends ItemProviderAdapter implements
			IEditingDomainItemProvider,
			IStructuredItemContentProvider,
			ITreeItemContentProvider,
			IItemLabelProvider,
			IItemPropertySource{
	
	public TransientLibraryElementItemProvider(AdapterFactory adapterFactory, LibraryElement libraryElement) {
		super(adapterFactory);
		libraryElement.eAdapters().add(this);
	}
		
	@Override
	public Collection<?> getChildren(Object object)
	{
	  return super.getChildren(target);
	}
	
	@Override
	public Collection<?> getNewChildDescriptors(Object object, EditingDomain editingDomain, Object sibling)
	{
	  return super.getNewChildDescriptors(target, editingDomain, sibling);
	}
	
	@Override
	public Object getParent(Object object) {
	  return target;
	}
	
	@Override
	public Object getImage(Object object) {
	  return overlayImage(object, getResourceLocator().getImage("full/obj16/LibraryElement"));
	}
	
	@Override
	public ResourceLocator getResourceLocator(){
	  return fordiacEditPlugin.INSTANCE;
	}

}
