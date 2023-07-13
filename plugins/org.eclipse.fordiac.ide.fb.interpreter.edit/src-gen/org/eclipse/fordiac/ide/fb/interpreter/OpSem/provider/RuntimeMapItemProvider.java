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
import java.util.Map;

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
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.provider.OperationalSemanticsEditPlugin;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;

/** This is the item provider adapter for a {@link java.util.Map.Entry} object. <!-- begin-user-doc --> <!--
 * end-user-doc -->
 *
 * @generated */
public class RuntimeMapItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
		IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/** This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public RuntimeMapItemProvider(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/** This returns the property descriptors for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(final Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addKeyPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/** This adds a property descriptor for the Key feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected void addKeyPropertyDescriptor(final Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_RuntimeMap_key_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_RuntimeMap_key_feature", //$NON-NLS-1$ //$NON-NLS-2$
								"_UI_RuntimeMap_type"),  //$NON-NLS-1$
						OperationalSemanticsPackage.Literals.RUNTIME_MAP__KEY, true, false, true, null, null, null));
	}

	/** This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(final Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(OperationalSemanticsPackage.Literals.RUNTIME_MAP__VALUE);
		}
		return childrenFeatures;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EStructuralFeature getChildFeature(final Object object, final Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/** This returns RuntimeMap.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated NOT */
	@Override
	public Object getImage(final Object object) {
		return overlayImage(object, FordiacImage.ICON_DEVICE);
	}

	/** This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated NOT */
	@Override
	public String getText(final Object object) {
		final Map.Entry<?, ?> runtimeMap = (Map.Entry<?, ?>) object;
		if (runtimeMap.getKey() instanceof final FBNetworkElement fb
				&& runtimeMap.getValue() instanceof final FBRuntimeAbstract rt) {
			return "Contained Runtime for FB " + fb.getQualifiedName(); //$NON-NLS-1$
		}
		return "" + runtimeMap.getKey() + " -> " + runtimeMap.getValue(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/** This handles model notifications by calling {@link #updateChildren} to update any cached children and by
	 * creating a viewer notification, which it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated */
	@Override
	public void notifyChanged(final Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Map.Entry.class)) {
		case OperationalSemanticsPackage.RUNTIME_MAP__VALUE:
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
	protected void collectNewChildDescriptors(final Collection<Object> newChildDescriptors, final Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(OperationalSemanticsPackage.Literals.RUNTIME_MAP__VALUE,
				OperationalSemanticsFactory.eINSTANCE.createBasicFBTypeRuntime()));

		newChildDescriptors.add(createChildParameter(OperationalSemanticsPackage.Literals.RUNTIME_MAP__VALUE,
				OperationalSemanticsFactory.eINSTANCE.createSimpleFBTypeRuntime()));

		newChildDescriptors.add(createChildParameter(OperationalSemanticsPackage.Literals.RUNTIME_MAP__VALUE,
				OperationalSemanticsFactory.eINSTANCE.createFBNetworkRuntime()));
	}

	/** Return the resource locator for this item provider's resources. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public ResourceLocator getResourceLocator() {
		return OperationalSemanticsEditPlugin.INSTANCE;
	}

}
