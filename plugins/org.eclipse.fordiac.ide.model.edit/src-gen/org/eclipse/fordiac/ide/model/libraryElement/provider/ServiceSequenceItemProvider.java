/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022 Martin Erich Jobst
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
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.fordiac.ide.model.buildpath.BuildpathFactory;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.provider.FordiacEditPlugin;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;

/**
 * This is the item provider adapter for a {@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ServiceSequenceItemProvider 
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
	public ServiceSequenceItemProvider(AdapterFactory adapterFactory) {
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

			addNamePropertyDescriptor(object);
			addCommentPropertyDescriptor(object);
			addServiceSequenceTypePropertyDescriptor(object);
			addStartStatePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_INamedElement_name_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_INamedElement_name_feature", "_UI_INamedElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 LibraryElementPackage.Literals.INAMED_ELEMENT__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Comment feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCommentPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_INamedElement_comment_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_INamedElement_comment_feature", "_UI_INamedElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 LibraryElementPackage.Literals.INAMED_ELEMENT__COMMENT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Service Sequence Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addServiceSequenceTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ServiceSequence_serviceSequenceType_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_ServiceSequence_serviceSequenceType_feature", "_UI_ServiceSequence_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 LibraryElementPackage.Literals.SERVICE_SEQUENCE__SERVICE_SEQUENCE_TYPE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Start State feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStartStatePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ServiceSequence_startState_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_ServiceSequence_startState_feature", "_UI_ServiceSequence_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 LibraryElementPackage.Literals.SERVICE_SEQUENCE__START_STATE,
				 true,
				 false,
				 true,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
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
			childrenFeatures.add(LibraryElementPackage.Literals.CONFIGURABLE_OBJECT__ATTRIBUTES);
			childrenFeatures.add(LibraryElementPackage.Literals.SERVICE_SEQUENCE__SERVICE_TRANSACTION);
			childrenFeatures.add(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER);
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
	 * This returns ServiceSequence.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, FordiacImage.ICON_SERVICE_SEQUENCE.getImage());
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public String getText(Object object) {
		final String label = ((ServiceSequence) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_ServiceSequence_type") : label; //$NON-NLS-1$
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

		switch (notification.getFeatureID(ServiceSequence.class)) {
			case LibraryElementPackage.SERVICE_SEQUENCE__NAME:
			case LibraryElementPackage.SERVICE_SEQUENCE__COMMENT:
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_SEQUENCE_TYPE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__ATTRIBUTES:
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
			case LibraryElementPackage.SERVICE_SEQUENCE__EVENT_MANAGER:
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
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__SERVICE_TRANSACTION,
				 LibraryElementFactory.eINSTANCE.createServiceTransaction()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createConfigurableObject()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createAdapterConnection()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createAdapterDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createTypedConfigureableObject()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createAdapterFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createLibraryElement()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createAdapterType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createApplication()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createArraySize()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createAttributeDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createFBType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createBaseFBType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createBasicFBType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createAutomationSystem()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createCFBInstance()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createColor()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createColorizableElement()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createComment()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createCommunicationChannel()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createMappingTarget()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createCommunicationMappingTarget()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createCompiler()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createCompilerInfo()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createCompositeFBType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createConfigurableMoveFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createConnectionRoutingData()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createDataConnection()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createStructManipulator()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createDemultiplexer()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createDevice()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createDeviceType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createECAction()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createECC()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createECState()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createPositionableElement()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createECTransition()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createErrorMarkerDataType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createErrorMarkerFBNElement()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createErrorMarkerInterface()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createEvent()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createEventConnection()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createFBNetwork()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createFunctionFBType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createGlobalConstants()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createGroup()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createHiddenElement()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createIdentification()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createImport()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createPrimitive()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createInputPrimitive()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createInterfaceList()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createLink()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createLocalVariable()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createMapping()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createMemberVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createMultiplexer()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createOriginalSource()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createOtherAlgorithm()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createOtherMethod()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createOutputPrimitive()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createPosition()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createResource()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createResourceTypeName()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createResourceType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createResourceTypeFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createSegment()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createSegmentType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createService()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createServiceSequence()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createServiceTransaction()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createServiceInterface()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createServiceInterfaceFBType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createSimpleECAction()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createSimpleECState()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createSimpleFBType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createSTAlgorithm()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createSTFunction()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createSTFunctionBody()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createSTMethod()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createSubAppType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createSystemConfiguration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createTypedSubApp()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createUntypedSubApp()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createValue()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createVersionInfo()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 LibraryElementFactory.eINSTANCE.createWith()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnyType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnyDerivedType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createArrayType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createDirectlyDerivedType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createValueType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createEnumeratedType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createEnumeratedValue()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createStructuredType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createSubrange()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createDerivedType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createSubrangeType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createEventType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnyElementaryType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnyMagnitudeType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnyNumType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnyRealType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createRealType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createLrealType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnyIntType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnyUnsignedType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createUsintType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createUintType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createUdintType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createUlintType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnySignedType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createSintType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createIntType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createDintType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createLintType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnyDurationType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createTimeType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createLtimeType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnyBitType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createBoolType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createByteType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createWordType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createDwordType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createLwordType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnyCharsType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnySCharsType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnyWCharsType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnyStringType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createStringType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createWstringType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnyCharType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createCharType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createWcharType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createAnyDateType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createDateAndTimeType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createLdtType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createDateType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createTimeOfDayType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createLtodType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createLdateType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 DataFactory.eINSTANCE.createInternalDataType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 BuildpathFactory.eINSTANCE.createAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 BuildpathFactory.eINSTANCE.createBuildpath()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 BuildpathFactory.eINSTANCE.createPattern()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER,
				 BuildpathFactory.eINSTANCE.createSourceFolder()));
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
			childFeature == LibraryElementPackage.Literals.SERVICE_SEQUENCE__SERVICE_TRANSACTION ||
			childFeature == LibraryElementPackage.Literals.SERVICE_SEQUENCE__EVENT_MANAGER;

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
