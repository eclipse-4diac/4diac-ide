/**
 * *******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *          - initial implementation and/or documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.CommunicationPackage;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow;

/** <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.CommunicationPackage
 * @generated */
public class CommunicationAdapterFactory extends AdapterFactoryImpl {
	/** The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected static CommunicationPackage modelPackage;

	/** Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public CommunicationAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = CommunicationPackage.eINSTANCE;
		}
	}

	/** Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This
	 * implementation returns <code>true</code> if the object is either the model's package or is an instance object of
	 * the model. <!-- end-user-doc -->
	 *
	 * @return whether this factory is applicable for the type of the object.
	 * @generated */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/** The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected CommunicationSwitch<Adapter> modelSwitch = new CommunicationSwitch<>() {
		@Override
		public Adapter caseTsnConfiguration(TsnConfiguration object) {
			return createTsnConfigurationAdapter();
		}

		@Override
		public Adapter caseTsnWindow(TsnWindow object) {
			return createTsnWindowAdapter();
		}

		@Override
		public Adapter caseCommunicationConfiguration(CommunicationConfiguration object) {
			return createCommunicationConfigurationAdapter();
		}

		@Override
		public Adapter caseINamedElement(INamedElement object) {
			return createINamedElementAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/** Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration <em>Tsn
	 * Configuration</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration
	 * @generated */
	public Adapter createTsnConfigurationAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow <em>Tsn Window</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow
	 * @generated */
	public Adapter createTsnWindowAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration <em>Communication
	 * Configuration</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration
	 * @generated */
	public Adapter createCommunicationConfigurationAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement
	 * <em>INamed Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.INamedElement
	 * @generated */
	public Adapter createINamedElementAdapter() {
		return null;
	}

	/** Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns null.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @generated */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // CommunicationAdapterFactory
