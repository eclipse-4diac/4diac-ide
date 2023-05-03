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
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;

/** This is the item provider adapter for a {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EccTrace} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated */
public class EccTraceItemProvider extends TraceItemProvider {
	/** This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public EccTraceItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/** This returns the property descriptors for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addTransitionsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/** This adds a property descriptor for the Transitions feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected void addTransitionsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_EccTrace_transitions_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_EccTrace_transitions_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_EccTrace_type"),  //$NON-NLS-1$
				OperationalSemanticsPackage.Literals.ECC_TRACE__TRANSITIONS, true, false, true, null, null, null));
	}

	/** This returns EccTrace.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/EccTrace")); //$NON-NLS-1$
	}

	/** This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public String getText(Object object) {
		return getString("_UI_EccTrace_type"); //$NON-NLS-1$
	}

	/** This handles model notifications by calling {@link #updateChildren} to update any cached children and by
	 * creating a viewer notification, which it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);
		super.notifyChanged(notification);
	}

	/** This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be created
	 * under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
