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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
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
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage
 * @generated
 */
public class MonitoringSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static MonitoringPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	public MonitoringSwitch() {
		if (modelPackage == null) {
			modelPackage = MonitoringPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(final EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a
	 * non null result; it yields that result. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(final int classifierID, final EObject theEObject) {
		switch (classifierID) {
		case MonitoringPackage.MONITORING_ELEMENT: {
			final MonitoringElement monitoringElement = (MonitoringElement) theEObject;
			T result = caseMonitoringElement(monitoringElement);
			if (result == null) {
				result = caseMonitoringBaseElement(monitoringElement);
			}
			if (result == null) {
				result = caseMonitoringBase_IEditPartCreator(monitoringElement);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case MonitoringPackage.MONITORING_ADAPTER_ELEMENT: {
			final MonitoringAdapterElement monitoringAdapterElement = (MonitoringAdapterElement) theEObject;
			T result = caseMonitoringAdapterElement(monitoringAdapterElement);
			if (result == null) {
				result = caseMonitoringBaseElement(monitoringAdapterElement);
			}
			if (result == null) {
				result = caseMonitoringBase_IEditPartCreator(monitoringAdapterElement);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case MonitoringPackage.ADAPTER_PORT_ELEMENT: {
			final AdapterPortElement adapterPortElement = (AdapterPortElement) theEObject;
			T result = caseAdapterPortElement(adapterPortElement);
			if (result == null) {
				result = casePortElement(adapterPortElement);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case MonitoringPackage.ADAPTER_MONITORING_EVENT: {
			final AdapterMonitoringEvent adapterMonitoringEvent = (AdapterMonitoringEvent) theEObject;
			T result = caseAdapterMonitoringEvent(adapterMonitoringEvent);
			if (result == null) {
				result = caseIEditPartCreator(adapterMonitoringEvent);
			}
			if (result == null) {
				result = caseEvent(adapterMonitoringEvent);
			}
			if (result == null) {
				result = caseIInterfaceElement(adapterMonitoringEvent);
			}
			if (result == null) {
				result = caseICallable(adapterMonitoringEvent);
			}
			if (result == null) {
				result = caseITypedElement(adapterMonitoringEvent);
			}
			if (result == null) {
				result = caseHiddenElement(adapterMonitoringEvent);
			}
			if (result == null) {
				result = caseINamedElement(adapterMonitoringEvent);
			}
			if (result == null) {
				result = caseConfigurableObject(adapterMonitoringEvent);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION: {
			final AdapterMonitoringVarDeclaration adapterMonitoringVarDeclaration = (AdapterMonitoringVarDeclaration) theEObject;
			T result = caseAdapterMonitoringVarDeclaration(adapterMonitoringVarDeclaration);
			if (result == null) {
				result = caseIEditPartCreator(adapterMonitoringVarDeclaration);
			}
			if (result == null) {
				result = caseVarDeclaration(adapterMonitoringVarDeclaration);
			}
			if (result == null) {
				result = caseIInterfaceElement(adapterMonitoringVarDeclaration);
			}
			if (result == null) {
				result = caseITypedElement(adapterMonitoringVarDeclaration);
			}
			if (result == null) {
				result = caseHiddenElement(adapterMonitoringVarDeclaration);
			}
			if (result == null) {
				result = caseINamedElement(adapterMonitoringVarDeclaration);
			}
			if (result == null) {
				result = caseConfigurableObject(adapterMonitoringVarDeclaration);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case MonitoringPackage.IEDIT_PART_CREATOR: {
			final IEditPartCreator iEditPartCreator = (IEditPartCreator) theEObject;
			T result = caseIEditPartCreator(iEditPartCreator);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case MonitoringPackage.SUB_APP_PORT_ELEMENT: {
			final SubAppPortElement subAppPortElement = (SubAppPortElement) theEObject;
			T result = caseSubAppPortElement(subAppPortElement);
			if (result == null) {
				result = casePortElement(subAppPortElement);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case MonitoringPackage.SUBAPP_MONITORING_ELEMENT: {
			final SubappMonitoringElement subappMonitoringElement = (SubappMonitoringElement) theEObject;
			T result = caseSubappMonitoringElement(subappMonitoringElement);
			if (result == null) {
				result = caseMonitoringElement(subappMonitoringElement);
			}
			if (result == null) {
				result = caseMonitoringBaseElement(subappMonitoringElement);
			}
			if (result == null) {
				result = caseMonitoringBase_IEditPartCreator(subappMonitoringElement);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case MonitoringPackage.INTERNAL_VAR_INSTANCE: {
			final InternalVarInstance internalVarInstance = (InternalVarInstance) theEObject;
			T result = caseInternalVarInstance(internalVarInstance);
			if (result == null) {
				result = caseVarDeclaration(internalVarInstance);
			}
			if (result == null) {
				result = caseIInterfaceElement(internalVarInstance);
			}
			if (result == null) {
				result = caseITypedElement(internalVarInstance);
			}
			if (result == null) {
				result = caseHiddenElement(internalVarInstance);
			}
			if (result == null) {
				result = caseINamedElement(internalVarInstance);
			}
			if (result == null) {
				result = caseConfigurableObject(internalVarInstance);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Element</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMonitoringElement(final MonitoringElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter
	 * Element</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter
	 *         Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMonitoringAdapterElement(final MonitoringAdapterElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEdit
	 * Part Creator</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEdit
	 *         Part Creator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIEditPartCreator(final IEditPartCreator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sub App
	 * Port Element</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sub App
	 *         Port Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubAppPortElement(final SubAppPortElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subapp
	 * Monitoring Element</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subapp
	 *         Monitoring Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubappMonitoringElement(final SubappMonitoringElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Internal
	 * Var Instance</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Internal
	 *         Var Instance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInternalVarInstance(final InternalVarInstance object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEdit
	 * Part Creator</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEdit
	 *         Part Creator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMonitoringBase_IEditPartCreator(final IEditPartCreator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Element</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMonitoringBaseElement(final MonitoringBaseElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Port
	 * Element</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Port
	 *         Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePortElement(final PortElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter
	 * Port Element</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter
	 *         Port Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapterPortElement(final AdapterPortElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter
	 * Monitoring Event</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter
	 *         Monitoring Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapterMonitoringEvent(final AdapterMonitoringEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter
	 * Monitoring Var Declaration</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter
	 *         Monitoring Var Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapterMonitoringVarDeclaration(final AdapterMonitoringVarDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INamed
	 * Element</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INamed
	 *         Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINamedElement(final INamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ITyped
	 * Element</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ITyped
	 *         Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseITypedElement(final ITypedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Configurable Object</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Configurable Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConfigurableObject(final ConfigurableObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Hidden
	 * Element</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Hidden
	 *         Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHiddenElement(final HiddenElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>IInterface Element</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>IInterface Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIInterfaceElement(final IInterfaceElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>ICallable</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>ICallable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseICallable(final ICallable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Event</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEvent(final Event object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Var
	 * Declaration</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Var
	 *         Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVarDeclaration(final VarDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>EObject</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last
	 * case anyway. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(final EObject object) {
		return null;
	}

} // MonitoringSwitch
