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
package org.eclipse.fordiac.ide.model.virtualDNS;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSFactory
 * @model kind="package"
 * @generated
 */
public interface VirtualDNSPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "virtualDNS";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.fordiac.ide.virtualDNS";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "virtualDNS";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	VirtualDNSPackage eINSTANCE = org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSEntryImpl <em>Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSEntryImpl
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSPackageImpl#getVirtualDNSEntry()
	 * @generated
	 */
	int VIRTUAL_DNS_ENTRY = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_DNS_ENTRY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_DNS_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_DNS_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSCollectionImpl <em>Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSCollectionImpl
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSPackageImpl#getVirtualDNSCollection()
	 * @generated
	 */
	int VIRTUAL_DNS_COLLECTION = 1;

	/**
	 * The feature id for the '<em><b>Virtual DNS Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_DNS_COLLECTION__VIRTUAL_DNS_ENTRIES = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_DNS_COLLECTION__NAME = 1;

	/**
	 * The number of structural features of the '<em>Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_DNS_COLLECTION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSManagementImpl <em>Management</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSManagementImpl
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSPackageImpl#getVirtualDNSManagement()
	 * @generated
	 */
	int VIRTUAL_DNS_MANAGEMENT = 2;

	/**
	 * The feature id for the '<em><b>Available DNS Collections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_DNS_MANAGEMENT__AVAILABLE_DNS_COLLECTIONS = 0;

	/**
	 * The feature id for the '<em><b>Active Virtual DNS</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_DNS_MANAGEMENT__ACTIVE_VIRTUAL_DNS = 1;

	/**
	 * The number of structural features of the '<em>Management</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_DNS_MANAGEMENT_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSEntry
	 * @generated
	 */
	EClass getVirtualDNSEntry();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSEntry#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSEntry#getName()
	 * @see #getVirtualDNSEntry()
	 * @generated
	 */
	EAttribute getVirtualDNSEntry_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSEntry#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSEntry#getValue()
	 * @see #getVirtualDNSEntry()
	 * @generated
	 */
	EAttribute getVirtualDNSEntry_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSCollection <em>Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Collection</em>'.
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSCollection
	 * @generated
	 */
	EClass getVirtualDNSCollection();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSCollection#getVirtualDNSEntries <em>Virtual DNS Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Virtual DNS Entries</em>'.
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSCollection#getVirtualDNSEntries()
	 * @see #getVirtualDNSCollection()
	 * @generated
	 */
	EReference getVirtualDNSCollection_VirtualDNSEntries();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSCollection#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSCollection#getName()
	 * @see #getVirtualDNSCollection()
	 * @generated
	 */
	EAttribute getVirtualDNSCollection_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSManagement <em>Management</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Management</em>'.
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSManagement
	 * @generated
	 */
	EClass getVirtualDNSManagement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSManagement#getAvailableDNSCollections <em>Available DNS Collections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Available DNS Collections</em>'.
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSManagement#getAvailableDNSCollections()
	 * @see #getVirtualDNSManagement()
	 * @generated
	 */
	EReference getVirtualDNSManagement_AvailableDNSCollections();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSManagement#getActiveVirtualDNS <em>Active Virtual DNS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Active Virtual DNS</em>'.
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSManagement#getActiveVirtualDNS()
	 * @see #getVirtualDNSManagement()
	 * @generated
	 */
	EReference getVirtualDNSManagement_ActiveVirtualDNS();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VirtualDNSFactory getVirtualDNSFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSEntryImpl <em>Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSEntryImpl
		 * @see org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSPackageImpl#getVirtualDNSEntry()
		 * @generated
		 */
		EClass VIRTUAL_DNS_ENTRY = eINSTANCE.getVirtualDNSEntry();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VIRTUAL_DNS_ENTRY__NAME = eINSTANCE.getVirtualDNSEntry_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VIRTUAL_DNS_ENTRY__VALUE = eINSTANCE.getVirtualDNSEntry_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSCollectionImpl <em>Collection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSCollectionImpl
		 * @see org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSPackageImpl#getVirtualDNSCollection()
		 * @generated
		 */
		EClass VIRTUAL_DNS_COLLECTION = eINSTANCE.getVirtualDNSCollection();

		/**
		 * The meta object literal for the '<em><b>Virtual DNS Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_DNS_COLLECTION__VIRTUAL_DNS_ENTRIES = eINSTANCE.getVirtualDNSCollection_VirtualDNSEntries();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VIRTUAL_DNS_COLLECTION__NAME = eINSTANCE.getVirtualDNSCollection_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSManagementImpl <em>Management</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSManagementImpl
		 * @see org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSPackageImpl#getVirtualDNSManagement()
		 * @generated
		 */
		EClass VIRTUAL_DNS_MANAGEMENT = eINSTANCE.getVirtualDNSManagement();

		/**
		 * The meta object literal for the '<em><b>Available DNS Collections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_DNS_MANAGEMENT__AVAILABLE_DNS_COLLECTIONS = eINSTANCE.getVirtualDNSManagement_AvailableDNSCollections();

		/**
		 * The meta object literal for the '<em><b>Active Virtual DNS</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_DNS_MANAGEMENT__ACTIVE_VIRTUAL_DNS = eINSTANCE.getVirtualDNSManagement_ActiveVirtualDNS();

	}

} //VirtualDNSPackage
