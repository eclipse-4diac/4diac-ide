/**
 * ******************************************************************************
 * Copyright (c) 2012, 2013, 2018 Profactor GmbH, fortiss GmbH, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl
 *      - initial API and implementation and/or initial documentation
 *    Alois Zoitl - moved to deployment and reworked it to a device response model
 * ******************************************************************************
 *
 */
package org.eclipse.fordiac.ide.deployment.devResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>FB Status</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage#getFBStatus()
 * @model
 * @generated
 */
public enum FBStatus implements Enumerator {
	/**
	 * The '<em><b>UNKNOWN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNKNOWN_VALUE
	 * @generated
	 * @ordered
	 */
	UNKNOWN(0, "UNKNOWN", "UNKNOWN"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>IDLE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #IDLE_VALUE
	 * @generated
	 * @ordered
	 */
	IDLE(1, "IDLE", "IDLE"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>RUNNING</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RUNNING_VALUE
	 * @generated
	 * @ordered
	 */
	RUNNING(2, "RUNNING", "RUNNING"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>STOPPED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STOPPED_VALUE
	 * @generated
	 * @ordered
	 */
	STOPPED(3, "STOPPED", "STOPPED"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>KILLED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #KILLED_VALUE
	 * @generated
	 * @ordered
	 */
	KILLED(4, "KILLED", "KILLED"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>UNKNOWN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNKNOWN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int UNKNOWN_VALUE = 0;

	/**
	 * The '<em><b>IDLE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #IDLE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int IDLE_VALUE = 1;

	/**
	 * The '<em><b>RUNNING</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RUNNING
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int RUNNING_VALUE = 2;

	/**
	 * The '<em><b>STOPPED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STOPPED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int STOPPED_VALUE = 3;

	/**
	 * The '<em><b>KILLED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #KILLED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int KILLED_VALUE = 4;

	/**
	 * An array of all the '<em><b>FB Status</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final FBStatus[] VALUES_ARRAY =
		new FBStatus[] {
			UNKNOWN,
			IDLE,
			RUNNING,
			STOPPED,
			KILLED,
		};

	/**
	 * A public read-only list of all the '<em><b>FB Status</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<FBStatus> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>FB Status</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static FBStatus get(String literal) {
		for (FBStatus result : VALUES_ARRAY) {
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>FB Status</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static FBStatus getByName(String name) {
		for (FBStatus result : VALUES_ARRAY) {
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>FB Status</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static FBStatus get(int value) {
		switch (value) {
			case UNKNOWN_VALUE: return UNKNOWN;
			case IDLE_VALUE: return IDLE;
			case RUNNING_VALUE: return RUNNING;
			case STOPPED_VALUE: return STOPPED;
			case KILLED_VALUE: return KILLED;
			default: return null;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private FBStatus(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} //FBStatus
