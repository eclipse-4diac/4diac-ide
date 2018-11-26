/*******************************************************************************
 * Copyright (c) 2009 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.virtualDNS.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSCollection;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSEntry;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSFactory;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSManagement;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class VirtualDNSFactoryImpl extends EFactoryImpl implements VirtualDNSFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static VirtualDNSFactory init() {
		try {
			VirtualDNSFactory theVirtualDNSFactory = (VirtualDNSFactory)EPackage.Registry.INSTANCE.getEFactory(VirtualDNSPackage.eNS_URI);
			if (theVirtualDNSFactory != null) {
				return theVirtualDNSFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VirtualDNSFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VirtualDNSFactoryImpl() {
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
			case VirtualDNSPackage.VIRTUAL_DNS_ENTRY: return createVirtualDNSEntry();
			case VirtualDNSPackage.VIRTUAL_DNS_COLLECTION: return createVirtualDNSCollection();
			case VirtualDNSPackage.VIRTUAL_DNS_MANAGEMENT: return createVirtualDNSManagement();
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
	public VirtualDNSEntry createVirtualDNSEntry() {
		VirtualDNSEntryImpl virtualDNSEntry = new VirtualDNSEntryImpl();
		return virtualDNSEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VirtualDNSCollection createVirtualDNSCollection() {
		VirtualDNSCollectionImpl virtualDNSCollection = new VirtualDNSCollectionImpl();
		return virtualDNSCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VirtualDNSManagement createVirtualDNSManagement() {
		VirtualDNSManagementImpl virtualDNSManagement = new VirtualDNSManagementImpl();
		return virtualDNSManagement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VirtualDNSPackage getVirtualDNSPackage() {
		return (VirtualDNSPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VirtualDNSPackage getPackage() {
		return VirtualDNSPackage.eINSTANCE;
	}

} //VirtualDNSFactoryImpl
