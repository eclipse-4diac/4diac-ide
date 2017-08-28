/********************************************************************************
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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fordiac.ide.model.libraryElement.CompilableType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;

public class PropertiesItemProvider extends TransientLibraryElementItemProvider{

	public PropertiesItemProvider(AdapterFactory adapterFactory,
			LibraryElement libraryElement) {
		super(adapterFactory, libraryElement);
	}
	
	@Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object){
      if (childrenFeatures == null)
      {
        super.getChildrenFeatures(object);
        childrenFeatures.add(LibraryElementPackage.Literals.LIBRARY_ELEMENT__IDENTIFICATION);
        childrenFeatures.add(LibraryElementPackage.Literals.LIBRARY_ELEMENT__VERSION_INFO);
        if(object instanceof CompilableType){
        	childrenFeatures.add(LibraryElementPackage.Literals.COMPILABLE_TYPE__COMPILER_INFO);
        }
      }
      return childrenFeatures;
    }

    @Override
    public String getText(Object object){
      return "Properties";
    }
    
    @Override
    public Object getImage(Object object) {
      return overlayImage(object, FordiacImage.ICON_Properties.getImage());      
    }

    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
    {
	  super.collectNewChildDescriptors(newChildDescriptors, object);
	  newChildDescriptors.add
		(createChildParameter
			(LibraryElementPackage.Literals.LIBRARY_ELEMENT__VERSION_INFO,
			 LibraryElementFactory.eINSTANCE.createVersionInfo()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.LIBRARY_ELEMENT__IDENTIFICATION,
				 LibraryElementFactory.eINSTANCE.createIdentification()));
		
		newChildDescriptors.add
		(createChildParameter
			(LibraryElementPackage.Literals.COMPILABLE_TYPE__COMPILER_INFO,
			 LibraryElementFactory.eINSTANCE.createCompilerInfo()));
    }
   
}
