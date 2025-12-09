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
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace;
import org.eclipse.fordiac.ide.fb.interpreter.provider.OperationalSemanticsEditPlugin;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class TransitionTraceItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
		IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public TransitionTraceItemProvider(AdapterFactory adapterFactory) {
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

			addSourceStatePropertyDescriptor(object);
			addDestinationStatePropertyDescriptor(object);
			addCondEventPropertyDescriptor(object);
			addCondExpressionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Source State feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addSourceStatePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_TransitionTrace_sourceState_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_TransitionTrace_sourceState_feature", //$NON-NLS-1$ //$NON-NLS-2$
								"_UI_TransitionTrace_type"), //$NON-NLS-1$
						OperationalSemanticsPackage.Literals.TRANSITION_TRACE__SOURCE_STATE, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Destination State feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addDestinationStatePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_TransitionTrace_destinationState_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_TransitionTrace_destinationState_feature", //$NON-NLS-1$ //$NON-NLS-2$
								"_UI_TransitionTrace_type"), //$NON-NLS-1$
						OperationalSemanticsPackage.Literals.TRANSITION_TRACE__DESTINATION_STATE, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Cond Event feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addCondEventPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_TransitionTrace_condEvent_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_TransitionTrace_condEvent_feature", //$NON-NLS-1$ //$NON-NLS-2$
								"_UI_TransitionTrace_type"), //$NON-NLS-1$
						OperationalSemanticsPackage.Literals.TRANSITION_TRACE__COND_EVENT, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Cond Expression feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addCondExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_TransitionTrace_condExpression_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_TransitionTrace_condExpression_feature", //$NON-NLS-1$ //$NON-NLS-2$
								"_UI_TransitionTrace_type"), //$NON-NLS-1$
						OperationalSemanticsPackage.Literals.TRANSITION_TRACE__COND_EXPRESSION, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This returns TransitionTrace.gif. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/TransitionTrace")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((TransitionTrace) object).getSourceState();
		return label == null || label.length() == 0 ? getString("_UI_TransitionTrace_type") : //$NON-NLS-1$
				getString("_UI_TransitionTrace_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(TransitionTrace.class)) {
		case OperationalSemanticsPackage.TRANSITION_TRACE__SOURCE_STATE:
		case OperationalSemanticsPackage.TRANSITION_TRACE__DESTINATION_STATE:
		case OperationalSemanticsPackage.TRANSITION_TRACE__COND_EVENT:
		case OperationalSemanticsPackage.TRANSITION_TRACE__COND_EXPRESSION:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
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
