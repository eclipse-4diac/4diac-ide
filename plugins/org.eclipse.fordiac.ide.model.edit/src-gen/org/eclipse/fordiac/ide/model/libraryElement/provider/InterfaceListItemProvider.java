/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022-2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.fordiac.ide.model.data.provider.FordiacEditPlugin;

import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;

/**
 * This is the item provider adapter for a {@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class InterfaceListItemProvider 
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InterfaceListItemProvider(AdapterFactory adapterFactory) {
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

			addErrorMarkerPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Error Marker feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addErrorMarkerPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_InterfaceList_errorMarker_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_InterfaceList_errorMarker_feature", "_UI_InterfaceList_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 LibraryElementPackage.Literals.INTERFACE_LIST__ERROR_MARKER,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
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
			childrenFeatures.add(LibraryElementPackage.Literals.INTERFACE_LIST__EVENT_INPUTS);
			childrenFeatures.add(LibraryElementPackage.Literals.INTERFACE_LIST__EVENT_OUTPUTS);
			childrenFeatures.add(LibraryElementPackage.Literals.INTERFACE_LIST__INPUT_VARS);
			childrenFeatures.add(LibraryElementPackage.Literals.INTERFACE_LIST__OUTPUT_VARS);
			childrenFeatures.add(LibraryElementPackage.Literals.INTERFACE_LIST__IN_OUT_VARS);
			childrenFeatures.add(LibraryElementPackage.Literals.INTERFACE_LIST__PLUGS);
			childrenFeatures.add(LibraryElementPackage.Literals.INTERFACE_LIST__SOCKETS);
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
	 * This returns InterfaceList.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, FordiacImage.ICON_INTERFACE_LIST.getImage());
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_InterfaceList_type"); //$NON-NLS-1$
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

		switch (notification.getFeatureID(InterfaceList.class)) {
			case LibraryElementPackage.INTERFACE_LIST__OUT_MAPPED_IN_OUT_VARS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case LibraryElementPackage.INTERFACE_LIST__EVENT_INPUTS:
			case LibraryElementPackage.INTERFACE_LIST__EVENT_OUTPUTS:
			case LibraryElementPackage.INTERFACE_LIST__INPUT_VARS:
			case LibraryElementPackage.INTERFACE_LIST__OUTPUT_VARS:
			case LibraryElementPackage.INTERFACE_LIST__IN_OUT_VARS:
			case LibraryElementPackage.INTERFACE_LIST__PLUGS:
			case LibraryElementPackage.INTERFACE_LIST__SOCKETS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.INTERFACE_LIST__EVENT_INPUTS,
				 LibraryElementFactory.eINSTANCE.createEvent()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.INTERFACE_LIST__EVENT_OUTPUTS,
				 LibraryElementFactory.eINSTANCE.createEvent()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.INTERFACE_LIST__INPUT_VARS,
				 LibraryElementFactory.eINSTANCE.createVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.INTERFACE_LIST__INPUT_VARS,
				 LibraryElementFactory.eINSTANCE.createLocalVariable()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.INTERFACE_LIST__INPUT_VARS,
				 LibraryElementFactory.eINSTANCE.createMemberVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.INTERFACE_LIST__OUTPUT_VARS,
				 LibraryElementFactory.eINSTANCE.createVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.INTERFACE_LIST__OUTPUT_VARS,
				 LibraryElementFactory.eINSTANCE.createLocalVariable()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.INTERFACE_LIST__OUTPUT_VARS,
				 LibraryElementFactory.eINSTANCE.createMemberVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.INTERFACE_LIST__IN_OUT_VARS,
				 LibraryElementFactory.eINSTANCE.createVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.INTERFACE_LIST__IN_OUT_VARS,
				 LibraryElementFactory.eINSTANCE.createLocalVariable()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.INTERFACE_LIST__IN_OUT_VARS,
				 LibraryElementFactory.eINSTANCE.createMemberVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.INTERFACE_LIST__PLUGS,
				 LibraryElementFactory.eINSTANCE.createAdapterDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.INTERFACE_LIST__SOCKETS,
				 LibraryElementFactory.eINSTANCE.createAdapterDeclaration()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == LibraryElementPackage.Literals.INTERFACE_LIST__EVENT_INPUTS ||
			childFeature == LibraryElementPackage.Literals.INTERFACE_LIST__EVENT_OUTPUTS ||
			childFeature == LibraryElementPackage.Literals.INTERFACE_LIST__INPUT_VARS ||
			childFeature == LibraryElementPackage.Literals.INTERFACE_LIST__OUTPUT_VARS ||
			childFeature == LibraryElementPackage.Literals.INTERFACE_LIST__IN_OUT_VARS ||
			childFeature == LibraryElementPackage.Literals.INTERFACE_LIST__PLUGS ||
			childFeature == LibraryElementPackage.Literals.INTERFACE_LIST__SOCKETS;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2", //$NON-NLS-1$
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return FordiacEditPlugin.INSTANCE;
	}

}
