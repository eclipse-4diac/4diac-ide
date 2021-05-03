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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;

/** This is the item provider adapter for a {@link org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated */
public class OutputPrimitiveItemProvider extends PrimitiveItemProvider {
	/** This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public OutputPrimitiveItemProvider(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/** This returns the property descriptors for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(final Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

		}
		return itemPropertyDescriptors;
	}

	/** This returns OutputPrimitive.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated not */
	@Override
	public Object getImage(final Object object) {

		if (((Service) ((OutputPrimitive) object).eContainer().eContainer().eContainer()).getLeftInterface()
				.equals(((OutputPrimitive) object).getInterface())) {
			return overlayImage(object, FordiacImage.ICON_LEFT_OUTPUT_PRIMITIVE.getImage());
		}
		return overlayImage(object, FordiacImage.ICON_RIGHT_OUTPUT_PRIMITIVE.getImage());
	}

	/** This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated not */
	@Override
	public String getText(final Object object) {
		final String label = ((OutputPrimitive) object).getEvent();
		String retVal = ((label == null) || (label.length() == 0)) ? getString("_UI_OutputPrimitive_type") : label; //$NON-NLS-1$

		final String parameters = ((OutputPrimitive) object).getParameters();
		if ((parameters != null) && (parameters.length() != 0)) {
			retVal += " (" + parameters + ")"; //$NON-NLS-1$ //$NON-NLS-2$
		}

		return retVal;
	}

	/** This handles model notifications by calling {@link #updateChildren} to update any cached children and by
	 * creating a viewer notification, which it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	@Override
	public void notifyChanged(final Notification notification) {
		updateChildren(notification);
		super.notifyChanged(notification);
	}

	/** This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be created
	 * under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected void collectNewChildDescriptors(final Collection<Object> newChildDescriptors, final Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
