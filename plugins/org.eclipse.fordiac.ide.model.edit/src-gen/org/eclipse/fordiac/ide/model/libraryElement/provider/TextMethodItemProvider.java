/**
 * *******************************************************************************
 * Copyright (c) 2008, 2026 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                                                       Martin Erich Jobst, Primetals Technologies Austria GmbH
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
import org.eclipse.fordiac.ide.model.libraryElement.TextMethod;

/**
 * This is the item provider adapter for a {@link org.eclipse.fordiac.ide.model.libraryElement.TextMethod} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TextMethodItemProvider extends MethodItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextMethodItemProvider(AdapterFactory adapterFactory) {
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

			addTextPropertyDescriptor(object);
			addReturnTypePropertyDescriptor(object);
			addVarargsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Text feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTextPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TextSourceElement_text_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_TextSourceElement_text_feature", "_UI_TextSourceElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 LibraryElementPackage.Literals.TEXT_SOURCE_ELEMENT__TEXT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Return Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addReturnTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TextMethod_returnType_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_TextMethod_returnType_feature", "_UI_TextMethod_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 LibraryElementPackage.Literals.TEXT_METHOD__RETURN_TYPE,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Varargs feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addVarargsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TextMethod_varargs_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_TextMethod_varargs_feature", "_UI_TextMethod_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 LibraryElementPackage.Literals.TEXT_METHOD__VARARGS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
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
			childrenFeatures.add(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS);
			childrenFeatures.add(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS);
			childrenFeatures.add(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS);
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
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((TextMethod)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_TextMethod_type") : //$NON-NLS-1$
			getString("_UI_TextMethod_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(TextMethod.class)) {
			case LibraryElementPackage.TEXT_METHOD__TEXT:
			case LibraryElementPackage.TEXT_METHOD__VARARGS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case LibraryElementPackage.TEXT_METHOD__INPUT_PARAMETERS:
			case LibraryElementPackage.TEXT_METHOD__OUTPUT_PARAMETERS:
			case LibraryElementPackage.TEXT_METHOD__IN_OUT_PARAMETERS:
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
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createAdapterDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createTypedConfigureableObject()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createAdapterFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createAttributeDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createCFBInstance()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createComment()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createCommunicationChannel()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createConfigurableMoveFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createContainerVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createStructManipulator()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createDemultiplexer()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createDevice()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createErrorAttributeDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createErrorMarkerFBNElement()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createErrorMarkerInterface()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createEvent()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createGroup()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createLocalVariable()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createMultiplexer()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createResource()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createResourceTypeFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createSegment()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createTypedSubApp()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createUntypedSubApp()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createVarConfigInstance()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createAdapterDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createTypedConfigureableObject()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createAdapterFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createAttributeDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createCFBInstance()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createComment()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createCommunicationChannel()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createConfigurableMoveFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createContainerVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createStructManipulator()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createDemultiplexer()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createDevice()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createErrorAttributeDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createErrorMarkerFBNElement()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createErrorMarkerInterface()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createEvent()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createGroup()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createLocalVariable()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createMultiplexer()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createResource()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createResourceTypeFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createSegment()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createTypedSubApp()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createUntypedSubApp()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createVarConfigInstance()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createAdapterDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createTypedConfigureableObject()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createAdapterFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createAttributeDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createCFBInstance()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createComment()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createCommunicationChannel()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createConfigurableMoveFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createContainerVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createStructManipulator()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createDemultiplexer()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createDevice()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createErrorAttributeDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createErrorMarkerFBNElement()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createErrorMarkerInterface()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createEvent()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createGroup()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createLocalVariable()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createMultiplexer()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createResource()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createResourceTypeFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createSegment()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createTypedSubApp()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createUntypedSubApp()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS,
				 LibraryElementFactory.eINSTANCE.createVarConfigInstance()));
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
			childFeature == LibraryElementPackage.Literals.TEXT_METHOD__INPUT_PARAMETERS ||
			childFeature == LibraryElementPackage.Literals.TEXT_METHOD__OUTPUT_PARAMETERS ||
			childFeature == LibraryElementPackage.Literals.TEXT_METHOD__IN_OUT_PARAMETERS;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2", //$NON-NLS-1$
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
