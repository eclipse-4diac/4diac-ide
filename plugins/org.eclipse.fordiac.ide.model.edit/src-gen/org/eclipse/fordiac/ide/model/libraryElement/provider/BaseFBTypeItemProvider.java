/**
 * *******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Martin Erich Jobst
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
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.fordiac.ide.model.libraryElement.BaseFBType} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class BaseFBTypeItemProvider extends FBTypeItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public BaseFBTypeItemProvider(AdapterFactory adapterFactory) {
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
	 * This specifies how to implement {@link #getChildren} and is used to deduce an
	 * appropriate feature for an {@link org.eclipse.emf.edit.command.AddCommand},
	 * {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated not
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(final Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_VARS);
			childrenFeatures.add(LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS);
			childrenFeatures.add(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES);
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
	 * This returns BaseFBType.gif.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/BaseFBType")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((BaseFBType)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_BaseFBType_type") : //$NON-NLS-1$
			getString("_UI_BaseFBType_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(BaseFBType.class)) {
			case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_VARS:
			case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_CONST_VARS:
			case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_FBS:
			case LibraryElementPackage.BASE_FB_TYPE__ALGORITHM:
			case LibraryElementPackage.BASE_FB_TYPE__METHODS:
			case LibraryElementPackage.BASE_FB_TYPE__CALLABLES:
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
				(LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_VARS,
				 LibraryElementFactory.eINSTANCE.createVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_VARS,
				 LibraryElementFactory.eINSTANCE.createLocalVariable()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_CONST_VARS,
				 LibraryElementFactory.eINSTANCE.createVarDeclaration()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_CONST_VARS,
				 LibraryElementFactory.eINSTANCE.createLocalVariable()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS,
				 LibraryElementFactory.eINSTANCE.createFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS,
				 LibraryElementFactory.eINSTANCE.createAdapterFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS,
				 LibraryElementFactory.eINSTANCE.createCFBInstance()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS,
				 LibraryElementFactory.eINSTANCE.createCommunicationChannel()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS,
				 LibraryElementFactory.eINSTANCE.createConfigurableMoveFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS,
				 LibraryElementFactory.eINSTANCE.createStructManipulator()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS,
				 LibraryElementFactory.eINSTANCE.createDemultiplexer()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS,
				 LibraryElementFactory.eINSTANCE.createMultiplexer()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS,
				 LibraryElementFactory.eINSTANCE.createResourceTypeFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__ALGORITHM,
				 LibraryElementFactory.eINSTANCE.createOtherAlgorithm()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__ALGORITHM,
				 LibraryElementFactory.eINSTANCE.createSTAlgorithm()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__METHODS,
				 LibraryElementFactory.eINSTANCE.createOtherMethod()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__METHODS,
				 LibraryElementFactory.eINSTANCE.createSTMethod()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createAdapterFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createAdapterType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createFBType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createBaseFBType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createBasicFBType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createCFBInstance()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createCommunicationChannel()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createCompositeFBType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createConfigurableMoveFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createStructManipulator()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createDemultiplexer()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createEvent()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createFunctionFBType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createMultiplexer()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createOtherAlgorithm()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createOtherMethod()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createResourceTypeFB()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createServiceInterfaceFBType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createSimpleFBType()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createSTAlgorithm()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createSTFunction()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createSTMethod()));

		newChildDescriptors.add
			(createChildParameter
				(LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES,
				 LibraryElementFactory.eINSTANCE.createSubAppType()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_VARS ||
			childFeature == LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_CONST_VARS ||
			childFeature == LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_FBS ||
			childFeature == LibraryElementPackage.Literals.BASE_FB_TYPE__CALLABLES ||
			childFeature == LibraryElementPackage.Literals.BASE_FB_TYPE__ALGORITHM ||
			childFeature == LibraryElementPackage.Literals.BASE_FB_TYPE__METHODS;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2", //$NON-NLS-1$
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
