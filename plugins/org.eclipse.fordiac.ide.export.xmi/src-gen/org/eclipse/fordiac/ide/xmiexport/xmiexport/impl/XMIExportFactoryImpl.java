/**
 * *******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.xmiexport.xmiexport.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.fordiac.ide.xmiexport.xmiexport.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class XMIExportFactoryImpl extends EFactoryImpl implements XMIExportFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XMIExportFactory init() {
		try {
			XMIExportFactory theXMIExportFactory = (XMIExportFactory)EPackage.Registry.INSTANCE.getEFactory(XMIExportPackage.eNS_URI);
			if (theXMIExportFactory != null) {
				return theXMIExportFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new XMIExportFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XMIExportFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case XMIExportPackage.XMI_EXPORT_ATTRIBUTE_VALUES: return createXMIExportAttributeValues();
			case XMIExportPackage.XMI_EXPORT_ATTRIBUTE_VALUE: return createXMIExportAttributeValue();
			case XMIExportPackage.XMI_EXPORT_INITIAL_VALUES: return createXMIExportInitialValues();
			case XMIExportPackage.XMI_EXPORT_INITIAL_VALUE: return createXMIExportInitialValue();
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATIONS: return createXMIExportTypeDeclarations();
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATION: return createXMIExportTypeDeclaration();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMIExportAttributeValues createXMIExportAttributeValues() {
		XMIExportAttributeValuesImpl xmiExportAttributeValues = new XMIExportAttributeValuesImpl();
		return xmiExportAttributeValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMIExportAttributeValue createXMIExportAttributeValue() {
		XMIExportAttributeValueImpl xmiExportAttributeValue = new XMIExportAttributeValueImpl();
		return xmiExportAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMIExportInitialValues createXMIExportInitialValues() {
		XMIExportInitialValuesImpl xmiExportInitialValues = new XMIExportInitialValuesImpl();
		return xmiExportInitialValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMIExportInitialValue createXMIExportInitialValue() {
		XMIExportInitialValueImpl xmiExportInitialValue = new XMIExportInitialValueImpl();
		return xmiExportInitialValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMIExportTypeDeclarations createXMIExportTypeDeclarations() {
		XMIExportTypeDeclarationsImpl xmiExportTypeDeclarations = new XMIExportTypeDeclarationsImpl();
		return xmiExportTypeDeclarations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMIExportTypeDeclaration createXMIExportTypeDeclaration() {
		XMIExportTypeDeclarationImpl xmiExportTypeDeclaration = new XMIExportTypeDeclarationImpl();
		return xmiExportTypeDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMIExportPackage getXMIExportPackage() {
		return (XMIExportPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static XMIExportPackage getPackage() {
		return XMIExportPackage.eINSTANCE;
	}

} //XMIExportFactoryImpl
