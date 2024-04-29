/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * This is the item provider adapter for a {@link org.eclipse.fordiac.ide.model.libraryElement.Group} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class GroupItemProvider extends FBNetworkElementItemProvider {

	private static final String TRUNCATION_STRING = "\u2026"; //$NON-NLS-1$

	private static int maxWidth = -1;

	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroupItemProvider(AdapterFactory adapterFactory) {
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

			addGroupElementsPropertyDescriptor(object);
			addWidthPropertyDescriptor(object);
			addHeightPropertyDescriptor(object);
			addLockedPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Group Elements feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addGroupElementsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Group_groupElements_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Group_groupElements_feature", "_UI_Group_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 LibraryElementPackage.Literals.GROUP__GROUP_ELEMENTS,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Width feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addWidthPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Group_width_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Group_width_feature", "_UI_Group_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 LibraryElementPackage.Literals.GROUP__WIDTH,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.REAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Height feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addHeightPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Group_height_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Group_height_feature", "_UI_Group_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 LibraryElementPackage.Literals.GROUP__HEIGHT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.REAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Locked feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addLockedPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Group_locked_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Group_locked_feature", "_UI_Group_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 LibraryElementPackage.Literals.GROUP__LOCKED,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns Group.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Object getImage(final Object object) {
		return overlayImage(object, FordiacImage.ICON_GROUP.getImage());
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(final Object object) {
		final String name = ((Group) object).getName();
		if (name != null && !name.isBlank()) {
			return name;
		}
		return "[unnamed group]";//$NON-NLS-1$
	}

	private static int getMaxWidth() {
		if (-1 == maxWidth) {
			final IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE,
					"org.eclipse.fordiac.ide.gef"); //$NON-NLS-1$
			return store.getInt("MaxTypeLabelSize");
		}
		return maxWidth;
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

		switch (notification.getFeatureID(Group.class)) {
			case LibraryElementPackage.GROUP__WIDTH:
			case LibraryElementPackage.GROUP__HEIGHT:
			case LibraryElementPackage.GROUP__LOCKED:
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
