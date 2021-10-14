/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Marker Ref</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerRefImpl#getFileMarkerId <em>File Marker
 * Id</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerRefImpl#getErrorMessage <em>Error
 * Message</em>}</li>
 * </ul>
 *
 * @generated */
public class ErrorMarkerRefImpl extends EObjectImpl implements ErrorMarkerRef {
	/** The default value of the '{@link #getFileMarkerId() <em>File Marker Id</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFileMarkerId()
	 * @generated
	 * @ordered */
	protected static final long FILE_MARKER_ID_EDEFAULT = 0L;

	/** The cached value of the '{@link #getFileMarkerId() <em>File Marker Id</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFileMarkerId()
	 * @generated
	 * @ordered */
	protected long fileMarkerId = FILE_MARKER_ID_EDEFAULT;

	/** The default value of the '{@link #getErrorMessage() <em>Error Message</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getErrorMessage()
	 * @generated
	 * @ordered */
	protected static final String ERROR_MESSAGE_EDEFAULT = null;

	/** The cached value of the '{@link #getErrorMessage() <em>Error Message</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getErrorMessage()
	 * @generated
	 * @ordered */
	protected String errorMessage = ERROR_MESSAGE_EDEFAULT;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected ErrorMarkerRefImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.ERROR_MARKER_REF;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public long getFileMarkerId() {
		return fileMarkerId;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setFileMarkerId(long newFileMarkerId) {
		long oldFileMarkerId = fileMarkerId;
		fileMarkerId = newFileMarkerId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LibraryElementPackage.ERROR_MARKER_REF__FILE_MARKER_ID, oldFileMarkerId, fileMarkerId));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setErrorMessage(String newErrorMessage) {
		String oldErrorMessage = errorMessage;
		errorMessage = newErrorMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ERROR_MARKER_REF__ERROR_MESSAGE,
					oldErrorMessage, errorMessage));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public boolean hasError() {
		return getFileMarkerId() != 0;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LibraryElementPackage.ERROR_MARKER_REF__FILE_MARKER_ID:
			return getFileMarkerId();
		case LibraryElementPackage.ERROR_MARKER_REF__ERROR_MESSAGE:
			return getErrorMessage();
		default:
			return super.eGet(featureID, resolve, coreType);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case LibraryElementPackage.ERROR_MARKER_REF__FILE_MARKER_ID:
			setFileMarkerId((Long) newValue);
			return;
		case LibraryElementPackage.ERROR_MARKER_REF__ERROR_MESSAGE:
			setErrorMessage((String) newValue);
			return;
		default:
			super.eSet(featureID, newValue);
			return;
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case LibraryElementPackage.ERROR_MARKER_REF__FILE_MARKER_ID:
			setFileMarkerId(FILE_MARKER_ID_EDEFAULT);
			return;
		case LibraryElementPackage.ERROR_MARKER_REF__ERROR_MESSAGE:
			setErrorMessage(ERROR_MESSAGE_EDEFAULT);
			return;
		default:
			super.eUnset(featureID);
			return;
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case LibraryElementPackage.ERROR_MARKER_REF__FILE_MARKER_ID:
			return fileMarkerId != FILE_MARKER_ID_EDEFAULT;
		case LibraryElementPackage.ERROR_MARKER_REF__ERROR_MESSAGE:
			return ERROR_MESSAGE_EDEFAULT == null ? errorMessage != null : !ERROR_MESSAGE_EDEFAULT.equals(errorMessage);
		default:
			return super.eIsSet(featureID);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (fileMarkerId: "); //$NON-NLS-1$
		result.append(fileMarkerId);
		result.append(", errorMessage: "); //$NON-NLS-1$
		result.append(errorMessage);
		result.append(')');
		return result.toString();
	}

} // ErrorMarkerRefImpl
