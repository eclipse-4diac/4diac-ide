/*******************************************************************************
 * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH,
 * 				 2018 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.monitoring.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.HiddenElement;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.AdapterMonitoringEvent;
import org.eclipse.fordiac.ide.model.monitoring.AdapterMonitoringVarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.AdapterPortElement;
import org.eclipse.fordiac.ide.model.monitoring.InternalVarInstance;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage;
import org.eclipse.fordiac.ide.model.monitoring.SubAppPortElement;
import org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 *
 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage
 * @generated
 */
public class MonitoringAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static MonitoringPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public MonitoringAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = MonitoringPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object. <!--
	 * begin-user-doc --> This implementation returns <code>true</code> if the
	 * object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 *
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(final Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected MonitoringSwitch<Adapter> modelSwitch = new MonitoringSwitch<>() {
		@Override
		public Adapter caseMonitoringElement(final MonitoringElement object) {
			return createMonitoringElementAdapter();
		}

		@Override
		public Adapter caseMonitoringAdapterElement(final MonitoringAdapterElement object) {
			return createMonitoringAdapterElementAdapter();
		}

		@Override
		public Adapter caseAdapterPortElement(final AdapterPortElement object) {
			return createAdapterPortElementAdapter();
		}

		@Override
		public Adapter caseAdapterMonitoringEvent(final AdapterMonitoringEvent object) {
			return createAdapterMonitoringEventAdapter();
		}

		@Override
		public Adapter caseAdapterMonitoringVarDeclaration(final AdapterMonitoringVarDeclaration object) {
			return createAdapterMonitoringVarDeclarationAdapter();
		}

		@Override
		public Adapter caseIEditPartCreator(final IEditPartCreator object) {
			return createIEditPartCreatorAdapter();
		}

		@Override
		public Adapter caseSubAppPortElement(final SubAppPortElement object) {
			return createSubAppPortElementAdapter();
		}

		@Override
		public Adapter caseSubappMonitoringElement(final SubappMonitoringElement object) {
			return createSubappMonitoringElementAdapter();
		}

		@Override
		public Adapter caseInternalVarInstance(final InternalVarInstance object) {
			return createInternalVarInstanceAdapter();
		}

		@Override
		public Adapter caseMonitoringBase_IEditPartCreator(final IEditPartCreator object) {
			return createMonitoringBase_IEditPartCreatorAdapter();
		}

		@Override
		public Adapter caseMonitoringBaseElement(final MonitoringBaseElement object) {
			return createMonitoringBaseElementAdapter();
		}

		@Override
		public Adapter casePortElement(final PortElement object) {
			return createPortElementAdapter();
		}

		@Override
		public Adapter caseINamedElement(final INamedElement object) {
			return createINamedElementAdapter();
		}

		@Override
		public Adapter caseITypedElement(final ITypedElement object) {
			return createITypedElementAdapter();
		}

		@Override
		public Adapter caseConfigurableObject(final ConfigurableObject object) {
			return createConfigurableObjectAdapter();
		}

		@Override
		public Adapter caseHiddenElement(final HiddenElement object) {
			return createHiddenElementAdapter();
		}

		@Override
		public Adapter caseIInterfaceElement(final IInterfaceElement object) {
			return createIInterfaceElementAdapter();
		}

		@Override
		public Adapter caseICallable(final ICallable object) {
			return createICallableAdapter();
		}

		@Override
		public Adapter caseEvent(final Event object) {
			return createEventAdapter();
		}

		@Override
		public Adapter caseVarDeclaration(final VarDeclaration object) {
			return createVarDeclarationAdapter();
		}

		@Override
		public Adapter defaultCase(final EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(final Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement
	 * <em>Element</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement
	 * @generated
	 */
	public Adapter createMonitoringBaseElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement
	 * <em>Element</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringElement
	 * @generated
	 */
	public Adapter createMonitoringElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement
	 * <em>Adapter Element</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement
	 * @generated
	 */
	public Adapter createMonitoringAdapterElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator <em>IEdit Part
	 * Creator</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator
	 * @generated
	 */
	public Adapter createIEditPartCreatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.monitoring.SubAppPortElement <em>Sub
	 * App Port Element</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.monitoring.SubAppPortElement
	 * @generated
	 */
	public Adapter createSubAppPortElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement
	 * <em>Subapp Monitoring Element</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement
	 * @generated
	 */
	public Adapter createSubappMonitoringElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.monitoring.InternalVarInstance
	 * <em>Internal Var Instance</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.monitoring.InternalVarInstance
	 * @generated
	 */
	public Adapter createInternalVarInstanceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator <em>IEdit Part
	 * Creator</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator
	 * @generated
	 */
	public Adapter createMonitoringBase_IEditPartCreatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement
	 * <em>Port Element</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement
	 * @generated
	 */
	public Adapter createPortElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.monitoring.AdapterPortElement
	 * <em>Adapter Port Element</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.monitoring.AdapterPortElement
	 * @generated
	 */
	public Adapter createAdapterPortElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.monitoring.AdapterMonitoringEvent
	 * <em>Adapter Monitoring Event</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.monitoring.AdapterMonitoringEvent
	 * @generated
	 */
	public Adapter createAdapterMonitoringEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.monitoring.AdapterMonitoringVarDeclaration
	 * <em>Adapter Monitoring Var Declaration</em>}'. <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.monitoring.AdapterMonitoringVarDeclaration
	 * @generated
	 */
	public Adapter createAdapterMonitoringVarDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement <em>INamed
	 * Element</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.INamedElement
	 * @generated
	 */
	public Adapter createINamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ITypedElement <em>ITyped
	 * Element</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ITypedElement
	 * @generated
	 */
	public Adapter createITypedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject
	 * <em>Configurable Object</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject
	 * @generated
	 */
	public Adapter createConfigurableObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.HiddenElement <em>Hidden
	 * Element</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.HiddenElement
	 * @generated
	 */
	public Adapter createHiddenElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
	 * <em>IInterface Element</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
	 * @generated
	 */
	public Adapter createIInterfaceElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ICallable
	 * <em>ICallable</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ICallable
	 * @generated
	 */
	public Adapter createICallableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.Event <em>Event</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will
	 * catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Event
	 * @generated
	 */
	public Adapter createEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration <em>Var
	 * Declaration</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
	 * @generated
	 */
	public Adapter createVarDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This
	 * default implementation returns null. <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // MonitoringAdapterFactory
