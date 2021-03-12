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
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;

/** This is the item provider adapter for a {@link org.eclipse.fordiac.ide.model.libraryElement.Segment} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated */
public class SegmentItemProvider extends TypedConfigureableObjectItemProvider {
	/** This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public SegmentItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/** This returns the property descriptors for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addPositionPropertyDescriptor(object);
			addWidthPropertyDescriptor(object);
			addOutConnectionsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/** This adds a property descriptor for the Position feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	protected void addPositionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_PositionableElement_position_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_PositionableElement_position_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_PositionableElement_type"),  //$NON-NLS-1$
				LibraryElementPackage.Literals.POSITIONABLE_ELEMENT__POSITION, true, false, true, null, null, null));
	}

	/** This adds a property descriptor for the Width feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	protected void addWidthPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_Segment_width_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_Segment_width_feature", //$NON-NLS-1$ //$NON-NLS-2$
								"_UI_Segment_type"),  //$NON-NLS-1$
						LibraryElementPackage.Literals.SEGMENT__WIDTH, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/** This adds a property descriptor for the Out Connections feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected void addOutConnectionsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_Segment_outConnections_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_Segment_outConnections_feature", //$NON-NLS-1$ //$NON-NLS-2$
								"_UI_Segment_type"),  //$NON-NLS-1$
						LibraryElementPackage.Literals.SEGMENT__OUT_CONNECTIONS, true, false, true, null, null, null));
	}

	/** This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(LibraryElementPackage.Literals.SEGMENT__VAR_DECLARATIONS);
		}
		return childrenFeatures;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/** This returns Segment.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, FordiacImage.ICON_SEGMENT.getImage());
	}

	/** This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated not */
	@Override
	public String getText(Object object) {
		String label = ((Segment) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_Segment_type") : label; //$NON-NLS-1$
	}

	/** This handles model notifications by calling {@link #updateChildren} to update any cached children and by
	 * creating a viewer notification, which it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Segment.class)) {
		case LibraryElementPackage.SEGMENT__COLOR:
		case LibraryElementPackage.SEGMENT__WIDTH:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case LibraryElementPackage.SEGMENT__VAR_DECLARATIONS:
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
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(LibraryElementPackage.Literals.COLORIZABLE_ELEMENT__COLOR,
				LibraryElementFactory.eINSTANCE.createColor()));

		newChildDescriptors.add(createChildParameter(LibraryElementPackage.Literals.SEGMENT__VAR_DECLARATIONS,
				LibraryElementFactory.eINSTANCE.createVarDeclaration()));

		newChildDescriptors.add(createChildParameter(LibraryElementPackage.Literals.SEGMENT__VAR_DECLARATIONS,
				LibraryElementFactory.eINSTANCE.createAdapterDeclaration()));

		newChildDescriptors.add(createChildParameter(LibraryElementPackage.Literals.SEGMENT__VAR_DECLARATIONS,
				LibraryElementFactory.eINSTANCE.createLocalVariable()));
	}

}
