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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;

import org.eclipse.gef.commands.CommandStack;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Automation System</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AutomationSystemImpl#getApplication <em>Application</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AutomationSystemImpl#getMapping <em>Mapping</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AutomationSystemImpl#getSystemConfiguration <em>System Configuration</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AutomationSystemImpl#getCommandStack <em>Command Stack</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AutomationSystemImpl extends LibraryElementImpl implements AutomationSystem {
	/**
	 * The cached value of the '{@link #getApplication() <em>Application</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getApplication()
	 * @generated
	 * @ordered
	 */
	protected EList<Application> application;

	/**
	 * The cached value of the '{@link #getMapping() <em>Mapping</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapping()
	 * @generated
	 * @ordered
	 */
	protected EList<Mapping> mapping;

	/**
	 * The cached value of the '{@link #getSystemConfiguration() <em>System Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystemConfiguration()
	 * @generated
	 * @ordered
	 */
	protected SystemConfiguration systemConfiguration;

	/**
	 * The default value of the '{@link #getCommandStack() <em>Command Stack</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommandStack()
	 * @generated
	 * @ordered
	 */
	protected static final CommandStack COMMAND_STACK_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCommandStack() <em>Command Stack</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommandStack()
	 * @generated
	 * @ordered
	 */
	protected CommandStack commandStack = COMMAND_STACK_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AutomationSystemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.AUTOMATION_SYSTEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Application> getApplication() {
		if (application == null) {
			application = new EObjectContainmentEList<Application>(Application.class, this, LibraryElementPackage.AUTOMATION_SYSTEM__APPLICATION);
		}
		return application;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Mapping> getMapping() {
		if (mapping == null) {
			mapping = new EObjectContainmentEList<Mapping>(Mapping.class, this, LibraryElementPackage.AUTOMATION_SYSTEM__MAPPING);
		}
		return mapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SystemConfiguration getSystemConfiguration() {
		if (systemConfiguration != null && systemConfiguration.eIsProxy()) {
			InternalEObject oldSystemConfiguration = (InternalEObject)systemConfiguration;
			systemConfiguration = (SystemConfiguration)eResolveProxy(oldSystemConfiguration);
			if (systemConfiguration != oldSystemConfiguration) {
				InternalEObject newSystemConfiguration = (InternalEObject)systemConfiguration;
				NotificationChain msgs = oldSystemConfiguration.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION, null, null);
				if (newSystemConfiguration.eInternalContainer() == null) {
					msgs = newSystemConfiguration.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION, oldSystemConfiguration, systemConfiguration));
			}
		}
		return systemConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SystemConfiguration basicGetSystemConfiguration() {
		return systemConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSystemConfiguration(SystemConfiguration newSystemConfiguration, NotificationChain msgs) {
		SystemConfiguration oldSystemConfiguration = systemConfiguration;
		systemConfiguration = newSystemConfiguration;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION, oldSystemConfiguration, newSystemConfiguration);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSystemConfiguration(SystemConfiguration newSystemConfiguration) {
		if (newSystemConfiguration != systemConfiguration) {
			NotificationChain msgs = null;
			if (systemConfiguration != null)
				msgs = ((InternalEObject)systemConfiguration).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION, null, msgs);
			if (newSystemConfiguration != null)
				msgs = ((InternalEObject)newSystemConfiguration).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION, null, msgs);
			msgs = basicSetSystemConfiguration(newSystemConfiguration, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION, newSystemConfiguration, newSystemConfiguration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CommandStack getCommandStack() {
		return commandStack;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCommandStack(CommandStack newCommandStack) {
		CommandStack oldCommandStack = commandStack;
		commandStack = newCommandStack;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.AUTOMATION_SYSTEM__COMMAND_STACK, oldCommandStack, commandStack));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Device getDeviceNamed(final String name) {
		return org.eclipse.fordiac.ide.model.Annotations.getDeviceNamed(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Application getApplicationNamed(final String name) {
		return org.eclipse.fordiac.ide.model.Annotations.getApplicationNamed(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.AUTOMATION_SYSTEM__APPLICATION:
				return ((InternalEList<?>)getApplication()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.AUTOMATION_SYSTEM__MAPPING:
				return ((InternalEList<?>)getMapping()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION:
				return basicSetSystemConfiguration(null, msgs);
			default:
				return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.AUTOMATION_SYSTEM__APPLICATION:
				return getApplication();
			case LibraryElementPackage.AUTOMATION_SYSTEM__MAPPING:
				return getMapping();
			case LibraryElementPackage.AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION:
				if (resolve) return getSystemConfiguration();
				return basicGetSystemConfiguration();
			case LibraryElementPackage.AUTOMATION_SYSTEM__COMMAND_STACK:
				return getCommandStack();
			default:
				return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LibraryElementPackage.AUTOMATION_SYSTEM__APPLICATION:
				getApplication().clear();
				getApplication().addAll((Collection<? extends Application>)newValue);
				return;
			case LibraryElementPackage.AUTOMATION_SYSTEM__MAPPING:
				getMapping().clear();
				getMapping().addAll((Collection<? extends Mapping>)newValue);
				return;
			case LibraryElementPackage.AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION:
				setSystemConfiguration((SystemConfiguration)newValue);
				return;
			case LibraryElementPackage.AUTOMATION_SYSTEM__COMMAND_STACK:
				setCommandStack((CommandStack)newValue);
				return;
			default:
				super.eSet(featureID, newValue);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.AUTOMATION_SYSTEM__APPLICATION:
				getApplication().clear();
				return;
			case LibraryElementPackage.AUTOMATION_SYSTEM__MAPPING:
				getMapping().clear();
				return;
			case LibraryElementPackage.AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION:
				setSystemConfiguration((SystemConfiguration)null);
				return;
			case LibraryElementPackage.AUTOMATION_SYSTEM__COMMAND_STACK:
				setCommandStack(COMMAND_STACK_EDEFAULT);
				return;
			default:
				super.eUnset(featureID);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.AUTOMATION_SYSTEM__APPLICATION:
				return application != null && !application.isEmpty();
			case LibraryElementPackage.AUTOMATION_SYSTEM__MAPPING:
				return mapping != null && !mapping.isEmpty();
			case LibraryElementPackage.AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION:
				return systemConfiguration != null;
			case LibraryElementPackage.AUTOMATION_SYSTEM__COMMAND_STACK:
				return COMMAND_STACK_EDEFAULT == null ? commandStack != null : !COMMAND_STACK_EDEFAULT.equals(commandStack);
			default:
				return super.eIsSet(featureID);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (commandStack: "); //$NON-NLS-1$
		result.append(commandStack);
		result.append(')');
		return result.toString();
	}

} //AutomationSystemImpl
