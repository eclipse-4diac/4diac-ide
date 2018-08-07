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


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;

/**
 * This is the item provider adapter for a {@link org.eclipse.fordiac.ide.model.libraryElement.BasicFBType} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class BasicFBTypeItemProvider
	extends FBTypeItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasicFBTypeItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	/**
	   * The cached list of non-modeled children for the target object.
	   */
	  protected List<Object> basicFBTypeChildren = null; 
	
	  /**
	   * Returns the two non-modeled children.
	   */
	  @Override
	  public Collection<?> getChildren(Object object) {
	    if (basicFBTypeChildren == null) {
	      BasicFBType basicFBType = (BasicFBType)object;
	      basicFBTypeChildren = new ArrayList<Object>();
	      for (Object runner : super.getChildren(object)) {
			if((!(runner instanceof VarDeclaration)) && (!(runner instanceof Algorithm))) {
				basicFBTypeChildren.add(runner);
			}
		  }
	      basicFBTypeChildren.add(new AlgorithmsItemProvider(adapterFactory, basicFBType));
	      basicFBTypeChildren.add(new InternalVarsItemProvider(adapterFactory, basicFBType));
	    }
	    return basicFBTypeChildren;
	  }
	  
	  /**
	   * Disposes the non-modeled children.
	   */
	  @Override
	  public void dispose() {
	    super.dispose();
	    if (basicFBTypeChildren != null){
	    	for (Object runner : basicFBTypeChildren) {
	    		((IDisposable)runner).dispose();
			}
	    }
	  }

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

		}
		return itemPropertyDescriptors;
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(LibraryElementPackage.Literals.BASIC_FB_TYPE__ECC);
			childrenFeatures.add(LibraryElementPackage.Literals.BASIC_FB_TYPE__ALGORITHM);
			childrenFeatures.add(LibraryElementPackage.Literals.BASIC_FB_TYPE__INTERNAL_VARS);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns BasicFBType.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, FordiacImage.ICON_BasicFB.getImage());
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((BasicFBType)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_BasicFBType_type") : //$NON-NLS-1$
			getString("_UI_BasicFBType_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(BasicFBType.class)) {
			case LibraryElementPackage.BASIC_FB_TYPE__ECC:
			case LibraryElementPackage.BASIC_FB_TYPE__ALGORITHM:
			case LibraryElementPackage.BASIC_FB_TYPE__INTERNAL_VARS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASIC_FB_TYPE__ECC,
				 LibraryElementFactory.eINSTANCE.createECC()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASIC_FB_TYPE__ALGORITHM,
				 LibraryElementFactory.eINSTANCE.createOtherAlgorithm()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASIC_FB_TYPE__ALGORITHM,
				 LibraryElementFactory.eINSTANCE.createSTAlgorithm()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASIC_FB_TYPE__INTERNAL_VARS,
				 LibraryElementFactory.eINSTANCE.createVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASIC_FB_TYPE__INTERNAL_VARS,
				 LibraryElementFactory.eINSTANCE.createAdapterDeclaration()));
	}

}
