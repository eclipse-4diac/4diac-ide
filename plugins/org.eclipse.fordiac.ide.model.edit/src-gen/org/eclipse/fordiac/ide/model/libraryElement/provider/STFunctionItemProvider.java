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
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.STFunction;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * This is the item provider adapter for a {@link org.eclipse.fordiac.ide.model.libraryElement.STFunction} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class STFunctionItemProvider extends TextFunctionItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public STFunctionItemProvider(AdapterFactory adapterFactory) {
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

		}
		return itemPropertyDescriptors;
	}

	/**
	 * This returns STFunction.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/STFunction")); //$NON-NLS-1$
	}

	/** This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated not */
	@Override
	public String getText(final Object object) {
		final STFunction function = (STFunction) object;
		return getString("_UI_STFunction_type", //$NON-NLS-1$
				new Object[] { function.getName(),
						Stream.concat(
								Stream.concat(
										function.getInputParameters().stream().filter(VarDeclaration.class::isInstance)
												.map(VarDeclaration.class::cast).map(VarDeclaration::getType)
												.filter(Predicate.not(Objects::isNull)).map(DataType::getName),
										function.getInOutParameters().stream().filter(VarDeclaration.class::isInstance)
												.map(VarDeclaration.class::cast).map(VarDeclaration::getType)
												.filter(Predicate.not(Objects::isNull)).map(DataType::getName)
												.filter(Predicate.not(Objects::isNull)).map(name -> "&" + name)),
								function.getOutputParameters().stream().filter(VarDeclaration.class::isInstance)
										.map(VarDeclaration.class::cast).map(VarDeclaration::getType)
										.filter(Predicate.not(Objects::isNull)).map(DataType::getName)
										.filter(Predicate.not(Objects::isNull)).map(name -> "&" + name))
								.collect(Collectors.joining(", ")),
						function.getReturnType() != null ? function.getReturnType().getName() : "VOID" });
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
	}

}
