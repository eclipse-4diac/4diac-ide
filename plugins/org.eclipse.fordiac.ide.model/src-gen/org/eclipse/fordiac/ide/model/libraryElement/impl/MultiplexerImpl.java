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

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Multiplexer</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.MultiplexerImpl#getStructType
 * <em>Struct Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MultiplexerImpl extends FBImpl implements Multiplexer {
	/**
	 * The cached value of the '{@link #getStructType() <em>Struct Type</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStructType()
	 * @generated
	 * @ordered
	 */
	protected StructuredType structType;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MultiplexerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.MULTIPLEXER;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public StructuredType getStructType() {
		if ((structType != null) && structType.eIsProxy()) {
			InternalEObject oldStructType = (InternalEObject) structType;
			structType = (StructuredType) eResolveProxy(oldStructType);
			if (structType != oldStructType) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LibraryElementPackage.MULTIPLEXER__STRUCT_TYPE, oldStructType, structType));
				}
			}
		}
		return structType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StructuredType basicGetStructType() {
		return structType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setStructTypeGen(StructuredType newStructType) {
		StructuredType oldStructType = structType;
		structType = newStructType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.MULTIPLEXER__STRUCT_TYPE,
					oldStructType, structType));
		}
	}

	@Override
	public void setStructType(StructuredType newStructType) {
		setStructTypeGen(newStructType);

		// create member variables of struct as data input ports
		getInterface().getInputVars().clear();
		Collection<VarDeclaration> list = EcoreUtil.copyAll(structType.getMemberVariables());
		list.forEach(varDecl -> varDecl.setIsInput(true));
		Event ev = getInterface().getEventInputs().get(0);

		// create with constructs
		list.forEach(varDecl -> {
			With with = LibraryElementFactory.eINSTANCE.createWith();
			with.setVariables(varDecl);
			ev.getWith().add(with);
		});

		// add data input ports to the interface
		getInterface().getInputVars().addAll(list);
		getInterface().getOutputVars().get(0).setType(structType); // there should be only one output
		setAttribute("StructuredType", "STRING", getStructType().getName(), COMMENT_EDEFAULT);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LibraryElementPackage.MULTIPLEXER__STRUCT_TYPE:
			if (resolve) {
				return getStructType();
			}
			return basicGetStructType();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case LibraryElementPackage.MULTIPLEXER__STRUCT_TYPE:
			setStructType((StructuredType) newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
		case LibraryElementPackage.MULTIPLEXER__STRUCT_TYPE:
			setStructType((StructuredType) null);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case LibraryElementPackage.MULTIPLEXER__STRUCT_TYPE:
			return structType != null;
		default:
			return super.eIsSet(featureID);
		}
	}

} // MultiplexerImpl
