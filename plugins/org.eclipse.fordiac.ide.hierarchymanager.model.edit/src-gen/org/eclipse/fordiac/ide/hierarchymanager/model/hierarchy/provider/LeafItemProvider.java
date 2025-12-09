/**
 * *******************************************************************************
 *  Copyright (c) 2023 Primetals Technologies Austria GmbH
 *  
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0 which is available at
 *  http://www.eclipse.org/legal/epl-2.0.
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *    Michael Oberlehner , Bianca Wiesmayr- initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyPackage;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;

/**
 * This is the item provider adapter for a {@link org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class LeafItemProvider extends NodeItemProvider {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "*******************************************************************************\n Copyright (c) 2023 Primetals Technologies Austria GmbH\n \n This program and the accompanying materials are made available under the\n terms of the Eclipse Public License 2.0 which is available at\n http://www.eclipse.org/legal/epl-2.0.\n\n SPDX-License-Identifier: EPL-2.0\n\n Contributors:\n   Michael Oberlehner , Bianca Wiesmayr- initial API and implementation and/or initial documentation\n*******************************************************************************";

	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LeafItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
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

			addContainerFileNamePropertyDescriptor(object);
			addRefPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Container File Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addContainerFileNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Leaf_containerFileName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Leaf_containerFileName_feature", "_UI_Leaf_type"),
				 HierarchyPackage.Literals.LEAF__CONTAINER_FILE_NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Ref feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRefPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Leaf_ref_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Leaf_ref_feature", "_UI_Leaf_type"),
				 HierarchyPackage.Literals.LEAF__REF,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns Leaf.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, FordiacImage.ICON_SUB_APP.getImage());
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		return HierarchyAnnotations.getLeafText((Leaf) object, this);
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

		switch (notification.getFeatureID(Leaf.class)) {
			case HierarchyPackage.LEAF__CONTAINER_FILE_NAME:
			case HierarchyPackage.LEAF__REF:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			default:
				super.notifyChanged(notification);
				return;
			}
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
	}

}
