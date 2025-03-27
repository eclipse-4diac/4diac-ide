/**
 * *******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *          - initial implementation and/or documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.fb.interpreter.OpSem.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class BasicFBTypeRuntimeItemProvider extends FBRuntimeAbstractItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public BasicFBTypeRuntimeItemProvider(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(final Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addBasicfbtypePropertyDescriptor(object);
			addActiveStatePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Basicfbtype feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addBasicfbtypePropertyDescriptor(final Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_BasicFBTypeRuntime_basicfbtype_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_BasicFBTypeRuntime_basicfbtype_feature", //$NON-NLS-1$ //$NON-NLS-2$
								"_UI_BasicFBTypeRuntime_type"), //$NON-NLS-1$
						OperationalSemanticsPackage.Literals.BASIC_FB_TYPE_RUNTIME__BASICFBTYPE, true, false, true,
						null, null, null));
	}

	/**
	 * This adds a property descriptor for the Active State feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addActiveStatePropertyDescriptor(final Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_BasicFBTypeRuntime_activeState_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_BasicFBTypeRuntime_activeState_feature", //$NON-NLS-1$ //$NON-NLS-2$
								"_UI_BasicFBTypeRuntime_type"), //$NON-NLS-1$
						OperationalSemanticsPackage.Literals.BASIC_FB_TYPE_RUNTIME__ACTIVE_STATE, true, false, true,
						null, null, null));
	}

	/**
	 * This returns BasicFBTypeRuntime.gif. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Object getImage(final Object object) {
		return overlayImage(object, FordiacImage.ICON_RESOURCE.getImage());
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public String getText(final Object object) {
		if (object instanceof final BasicFBTypeRuntime rt && (rt.getBasicfbtype() != null)) {
			if (rt.getActiveState() != null) {
				return super.getText(object) + "Runtime for type " + rt.getBasicfbtype().getName() + ", active state: " //$NON-NLS-1$ //$NON-NLS-2$
						+ rt.getActiveState();
			}
			return super.getText(object) + getString("_UI_BasicFBTypeRuntime_type") + " for type " //$NON-NLS-1$ //$NON-NLS-2$
					+ rt.getBasicfbtype().getName();
		}
		return getString("_UI_BasicFBTypeRuntime_type"); //$NON-NLS-1$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update
	 * any cached children and by creating a viewer notification, which it passes to
	 * {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void notifyChanged(final Notification notification) {
		updateChildren(notification);
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing
	 * the children that can be created under this object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(final Collection<Object> newChildDescriptors, final Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
