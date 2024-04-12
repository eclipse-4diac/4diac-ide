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
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.fordiac.ide.model.data.provider.FordiacEditPlugin;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class FBNetworkItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
		IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public FBNetworkItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns FBNetwork.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated not
	 */
	@Override
	public Object getImage(final Object object) {
		return overlayImage(object, FordiacImage.ICON_FB_NETWORK.getImage());
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_FBNetwork_type"); //$NON-NLS-1$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(FBNetwork.class)) {
			case LibraryElementPackage.FB_NETWORK__DATA_CONNECTIONS:
			case LibraryElementPackage.FB_NETWORK__EVENT_CONNECTIONS:
			case LibraryElementPackage.FB_NETWORK__ADAPTER_CONNECTIONS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case LibraryElementPackage.FB_NETWORK__NETWORK_ELEMENTS:
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
				(LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS,
				 LibraryElementFactory.eINSTANCE.createFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS,
				 LibraryElementFactory.eINSTANCE.createAdapterFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS,
				 LibraryElementFactory.eINSTANCE.createCFBInstance()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS,
				 LibraryElementFactory.eINSTANCE.createComment()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS,
				 LibraryElementFactory.eINSTANCE.createCommunicationChannel()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS,
				 LibraryElementFactory.eINSTANCE.createConfigurableMoveFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS,
				 LibraryElementFactory.eINSTANCE.createStructManipulator()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS,
				 LibraryElementFactory.eINSTANCE.createDemultiplexer()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS,
				 LibraryElementFactory.eINSTANCE.createErrorMarkerFBNElement()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS,
				 LibraryElementFactory.eINSTANCE.createGroup()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS,
				 LibraryElementFactory.eINSTANCE.createMultiplexer()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS,
				 LibraryElementFactory.eINSTANCE.createResourceTypeFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS,
				 LibraryElementFactory.eINSTANCE.createTypedSubApp()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS,
				 LibraryElementFactory.eINSTANCE.createUntypedSubApp()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__DATA_CONNECTIONS,
				 LibraryElementFactory.eINSTANCE.createDataConnection()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__EVENT_CONNECTIONS,
				 LibraryElementFactory.eINSTANCE.createEventConnection()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.FB_NETWORK__ADAPTER_CONNECTIONS,
				 LibraryElementFactory.eINSTANCE.createAdapterConnection()));
	}

	/**
	 * Return the resource locator for this item provider's resources. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return FordiacEditPlugin.INSTANCE;
	}

}
