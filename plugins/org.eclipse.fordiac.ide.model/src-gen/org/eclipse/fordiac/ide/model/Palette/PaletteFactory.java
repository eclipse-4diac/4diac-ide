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
package org.eclipse.fordiac.ide.model.Palette;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage
 * @generated
 */
public interface PaletteFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PaletteFactory eINSTANCE = org.eclipse.fordiac.ide.model.Palette.impl.PaletteFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Palette</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Palette</em>'.
	 * @generated
	 */
	Palette createPalette();

	/**
	 * Returns a new object of class '<em>Adapter Type Palette Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Adapter Type Palette Entry</em>'.
	 * @generated
	 */
	AdapterTypePaletteEntry createAdapterTypePaletteEntry();

	/**
	 * Returns a new object of class '<em>Data Type Palette Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Data Type Palette Entry</em>'.
	 * @generated
	 */
	DataTypePaletteEntry createDataTypePaletteEntry();

	/**
	 * Returns a new object of class '<em>Device Type Palette Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Device Type Palette Entry</em>'.
	 * @generated
	 */
	DeviceTypePaletteEntry createDeviceTypePaletteEntry();

	/**
	 * Returns a new object of class '<em>FB Type Palette Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>FB Type Palette Entry</em>'.
	 * @generated
	 */
	FBTypePaletteEntry createFBTypePaletteEntry();

	/**
	 * Returns a new object of class '<em>Resource Type Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource Type Entry</em>'.
	 * @generated
	 */
	ResourceTypeEntry createResourceTypeEntry();

	/**
	 * Returns a new object of class '<em>Segment Type Palette Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Segment Type Palette Entry</em>'.
	 * @generated
	 */
	SegmentTypePaletteEntry createSegmentTypePaletteEntry();

	/**
	 * Returns a new object of class '<em>Sub Application Type Palette Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sub Application Type Palette Entry</em>'.
	 * @generated
	 */
	SubApplicationTypePaletteEntry createSubApplicationTypePaletteEntry();

	/**
	 * Returns a new object of class '<em>System Palette Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>System Palette Entry</em>'.
	 * @generated
	 */
	SystemPaletteEntry createSystemPaletteEntry();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PalettePackage getPalettePackage();

} //PaletteFactory
