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
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
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
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.provider.OperationalSemanticsEditPlugin;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class EventOccurrenceItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
		IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EventOccurrenceItemProvider(AdapterFactory adapterFactory) {
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

			addEventPropertyDescriptor(object);
			addActivePropertyDescriptor(object);
			addIgnoredPropertyDescriptor(object);
			addFbRuntimePropertyDescriptor(object);
			addCreatedTransactionsPropertyDescriptor(object);
			addParentFBPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Event feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addEventPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_EventOccurrence_event_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_EventOccurrence_event_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_EventOccurrence_type"), //$NON-NLS-1$
				OperationalSemanticsPackage.Literals.EVENT_OCCURRENCE__EVENT, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Active feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addActivePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_EventOccurrence_active_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_EventOccurrence_active_feature", //$NON-NLS-1$ //$NON-NLS-2$
								"_UI_EventOccurrence_type"), //$NON-NLS-1$
						OperationalSemanticsPackage.Literals.EVENT_OCCURRENCE__ACTIVE, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Ignored feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addIgnoredPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_EventOccurrence_ignored_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_EventOccurrence_ignored_feature", //$NON-NLS-1$ //$NON-NLS-2$
								"_UI_EventOccurrence_type"), //$NON-NLS-1$
						OperationalSemanticsPackage.Literals.EVENT_OCCURRENCE__IGNORED, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Fb Runtime feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addFbRuntimePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_EventOccurrence_fbRuntime_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_EventOccurrence_fbRuntime_feature", //$NON-NLS-1$ //$NON-NLS-2$
								"_UI_EventOccurrence_type"), //$NON-NLS-1$
						OperationalSemanticsPackage.Literals.EVENT_OCCURRENCE__FB_RUNTIME, true, false, true, null,
						null, null));
	}

	/**
	 * This adds a property descriptor for the Created Transactions feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addCreatedTransactionsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_EventOccurrence_createdTransactions_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
								"_UI_EventOccurrence_createdTransactions_feature", "_UI_EventOccurrence_type"), //$NON-NLS-1$ //$NON-NLS-2$
						OperationalSemanticsPackage.Literals.EVENT_OCCURRENCE__CREATED_TRANSACTIONS, true, false, true,
						null, null, null));
	}

	/**
	 * This adds a property descriptor for the Parent FB feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addParentFBPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_EventOccurrence_parentFB_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_EventOccurrence_parentFB_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_EventOccurrence_type"), //$NON-NLS-1$
				OperationalSemanticsPackage.Literals.EVENT_OCCURRENCE__PARENT_FB, true, false, true, null, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an
	 * appropriate feature for an {@link org.eclipse.emf.edit.command.AddCommand},
	 * {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(final Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(OperationalSemanticsPackage.Literals.EVENT_OCCURRENCE__RESULT_FB_RUNTIME);
			childrenFeatures.add(OperationalSemanticsPackage.Literals.EVENT_OCCURRENCE__FB_RUNTIME);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to
		// use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns EventOccurrence.gif. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated NOT
	 */
	@Override
	public Object getImage(final Object object) {
		if (object instanceof final EObject eObj) {
			if (eObj.eContainingFeature()
					.getFeatureID() == OperationalSemanticsPackage.FB_TRANSACTION__INPUT_EVENT_OCCURRENCE) {
				return overlayImage(object, FordiacImage.ICON_EVENT_INPUT.getImage());
			}
			return overlayImage(object, FordiacImage.ICON_EVENT_OUTPUT.getImage());
		}
		return overlayImage(object, getResourceLocator().getImage("full/obj16/EventOccurrence")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public String getText(final Object object) {
		final EventOccurrence eventOccurrence = (EventOccurrence) object;
		if ((eventOccurrence.getEvent() != null) && (eventOccurrence.getParentFB() != null)) {
			return getString("_UI_EventOccurrence_type") + " " + eventOccurrence.getParentFB().getName() + "." //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ eventOccurrence.getEvent().getName();
		}
		return getString("_UI_EventOccurrence_type"); //$NON-NLS-1$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update
	 * any cached children and by creating a viewer notification, which it passes to
	 * {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(EventOccurrence.class)) {
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__ACTIVE:
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__IGNORED:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__RESULT_FB_RUNTIME:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		default:
			super.notifyChanged(notification);
			return;
		}
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing
	 * the children that can be created under this object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors
				.add(createChildParameter(OperationalSemanticsPackage.Literals.EVENT_OCCURRENCE__RESULT_FB_RUNTIME,
						OperationalSemanticsFactory.eINSTANCE.createBasicFBTypeRuntime()));

		newChildDescriptors
				.add(createChildParameter(OperationalSemanticsPackage.Literals.EVENT_OCCURRENCE__RESULT_FB_RUNTIME,
						OperationalSemanticsFactory.eINSTANCE.createSimpleFBTypeRuntime()));

		newChildDescriptors
				.add(createChildParameter(OperationalSemanticsPackage.Literals.EVENT_OCCURRENCE__RESULT_FB_RUNTIME,
						OperationalSemanticsFactory.eINSTANCE.createFBNetworkRuntime()));
	}

	/**
	 * Return the resource locator for this item provider's resources. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return OperationalSemanticsEditPlugin.INSTANCE;
	}

}
