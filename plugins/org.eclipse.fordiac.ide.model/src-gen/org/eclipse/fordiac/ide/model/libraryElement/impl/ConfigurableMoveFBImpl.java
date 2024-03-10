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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Configurable Move FB</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableMoveFBImpl#getDataType
 * <em>Data Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConfigurableMoveFBImpl extends ConfigurableFBImpl implements ConfigurableMoveFB {
	/**
	 * The cached value of the '{@link #getDataType() <em>Data Type</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected DataType dataType;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ConfigurableMoveFBImpl() {
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.CONFIGURABLE_MOVE_FB;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public DataType getDataType() {
		if (dataType != null && dataType.eIsProxy()) {
			final InternalEObject oldDataType = (InternalEObject) dataType;
			dataType = (DataType) eResolveProxy(oldDataType);
			if ((dataType != oldDataType) && eNotificationRequired()) {
				eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						LibraryElementPackage.CONFIGURABLE_MOVE_FB__DATA_TYPE, oldDataType, dataType));
			}
		}
		return dataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public DataType basicGetDataType() {
		return dataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setDataType(final DataType newDataType) {
		final DataType oldDataType = dataType;
		dataType = newDataType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CONFIGURABLE_MOVE_FB__DATA_TYPE,
					oldDataType, dataType));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void updateConfiguration() {
		// if data type exists, set it as the data type of the input/output data pin
		if (dataType != null && getInterface() != null) {
			for (final VarDeclaration input : getInterface().getInputVars()) {
				input.setType(dataType);
			}
			for (final VarDeclaration output : getInterface().getOutputVars()) {
				output.setType(dataType);
			} // FB_MOVE has no varinouts
		}
	}

	@Override
	public void loadConfiguration(final Attribute config) {
		// attribute holds the name of the desired data type of input and output data
		if ((config != null && LibraryElementTags.F_MOVE_CONFIG.equals(config.getName()))
				&& (config.getValue() != null)) {
			// get data type from library
			dataType = getTypeLibrary().getDataTypeLibrary().getTypeIfExists(config.getValue());
		}
		updateConfiguration();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Attribute> getConfigurationAsAttributes() {
		if (dataType == null) {
			return ECollections.emptyEList();
		}
		final Attribute attr = LibraryElementFactory.eINSTANCE.createAttribute();
		attr.setName(LibraryElementTags.F_MOVE_CONFIG);
		attr.setValue(dataType.getName());
		return ECollections.asEList(attr);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
		switch (featureID) {
		case LibraryElementPackage.CONFIGURABLE_MOVE_FB__DATA_TYPE:
			if (resolve) {
				return getDataType();
			}
			return basicGetDataType();
		default:
			return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(final int featureID, final Object newValue) {
		switch (featureID) {
		case LibraryElementPackage.CONFIGURABLE_MOVE_FB__DATA_TYPE:
			setDataType((DataType) newValue);
			return;
		default:
			super.eSet(featureID, newValue);
			return;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(final int featureID) {
		switch (featureID) {
		case LibraryElementPackage.CONFIGURABLE_MOVE_FB__DATA_TYPE:
			setDataType((DataType) null);
			return;
		default:
			super.eUnset(featureID);
			return;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(final int featureID) {
		return switch (featureID) {
		case LibraryElementPackage.CONFIGURABLE_MOVE_FB__DATA_TYPE -> dataType != null;
		default -> super.eIsSet(featureID);
		};
	}
}
// ConfigurableMoveFBImpl
