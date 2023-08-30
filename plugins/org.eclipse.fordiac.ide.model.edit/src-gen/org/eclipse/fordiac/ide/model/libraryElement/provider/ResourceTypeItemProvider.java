/**
 * *******************************************************************************
 *  Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;

/** This is the item provider adapter for a {@link org.eclipse.fordiac.ide.model.libraryElement.ResourceType} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated */
public class ResourceTypeItemProvider extends LibraryElementItemProvider {
	/** This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public ResourceTypeItemProvider(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/** This returns the property descriptors for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(final Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addSupportedFBTypesPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/** This adds a property descriptor for the Supported FB Types feature. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated */
	protected void addSupportedFBTypesPropertyDescriptor(final Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_ResourceType_supportedFBTypes_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ResourceType_supportedFBTypes_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_ResourceType_type"),  //$NON-NLS-1$
				LibraryElementPackage.Literals.RESOURCE_TYPE__SUPPORTED_FB_TYPES, true, false, true, null, null, null));
	}

	/** This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(final Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(LibraryElementPackage.Literals.RESOURCE_TYPE__VAR_DECLARATION);
			childrenFeatures.add(LibraryElementPackage.Literals.RESOURCE_TYPE__FB_NETWORK);
		}
		return childrenFeatures;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EStructuralFeature getChildFeature(final Object object, final Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/** This returns ResourceType.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Object getImage(final Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ResourceType")); //$NON-NLS-1$
	}

	/** This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public String getText(final Object object) {
		final String label = ((ResourceType) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_ResourceType_type") : //$NON-NLS-1$
				getString("_UI_ResourceType_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/** This handles model notifications by calling {@link #updateChildren} to update any cached children and by
	 * creating a viewer notification, which it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	@Override
	public void notifyChanged(final Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(ResourceType.class)) {
		case LibraryElementPackage.RESOURCE_TYPE__VAR_DECLARATION:
		case LibraryElementPackage.RESOURCE_TYPE__FB_NETWORK:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		default:
			super.notifyChanged(notification);
			return;
		}
	}

	/** This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be created
	 * under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected void collectNewChildDescriptors(final Collection<Object> newChildDescriptors, final Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(LibraryElementPackage.Literals.RESOURCE_TYPE__VAR_DECLARATION,
				LibraryElementFactory.eINSTANCE.createVarDeclaration()));

		newChildDescriptors.add(createChildParameter(LibraryElementPackage.Literals.RESOURCE_TYPE__VAR_DECLARATION,
				LibraryElementFactory.eINSTANCE.createAdapterDeclaration()));

		newChildDescriptors.add(createChildParameter(LibraryElementPackage.Literals.RESOURCE_TYPE__VAR_DECLARATION,
				LibraryElementFactory.eINSTANCE.createLocalVariable()));

		newChildDescriptors.add(createChildParameter(LibraryElementPackage.Literals.RESOURCE_TYPE__FB_NETWORK,
				LibraryElementFactory.eINSTANCE.createFBNetwork()));
	}

}
