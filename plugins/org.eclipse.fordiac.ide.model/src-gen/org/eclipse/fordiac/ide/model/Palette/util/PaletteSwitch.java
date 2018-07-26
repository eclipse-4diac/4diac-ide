/********************************************************************************
 * Copyright (c) 2008, 2010 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.Palette.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage
 * @generated
 */
public class PaletteSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PalettePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PaletteSwitch() {
		if (modelPackage == null) {
			modelPackage = PalettePackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case PalettePackage.PALETTE: {
				Palette palette = (Palette)theEObject;
				T result = casePalette(palette);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PalettePackage.PALETTE_GROUP: {
				PaletteGroup paletteGroup = (PaletteGroup)theEObject;
				T result = casePaletteGroup(paletteGroup);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PalettePackage.FB_TYPE_PALETTE_ENTRY: {
				FBTypePaletteEntry fbTypePaletteEntry = (FBTypePaletteEntry)theEObject;
				T result = caseFBTypePaletteEntry(fbTypePaletteEntry);
				if (result == null) result = casePaletteEntry(fbTypePaletteEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PalettePackage.PALETTE_ENTRY: {
				PaletteEntry paletteEntry = (PaletteEntry)theEObject;
				T result = casePaletteEntry(paletteEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PalettePackage.DEVICE_TYPE_PALETTE_ENTRY: {
				DeviceTypePaletteEntry deviceTypePaletteEntry = (DeviceTypePaletteEntry)theEObject;
				T result = caseDeviceTypePaletteEntry(deviceTypePaletteEntry);
				if (result == null) result = casePaletteEntry(deviceTypePaletteEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PalettePackage.RESOURCE_TYPE_ENTRY: {
				ResourceTypeEntry resourceTypeEntry = (ResourceTypeEntry)theEObject;
				T result = caseResourceTypeEntry(resourceTypeEntry);
				if (result == null) result = casePaletteEntry(resourceTypeEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PalettePackage.SEGMENT_TYPE_PALETTE_ENTRY: {
				SegmentTypePaletteEntry segmentTypePaletteEntry = (SegmentTypePaletteEntry)theEObject;
				T result = caseSegmentTypePaletteEntry(segmentTypePaletteEntry);
				if (result == null) result = casePaletteEntry(segmentTypePaletteEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PalettePackage.ADAPTER_TYPE_PALETTE_ENTRY: {
				AdapterTypePaletteEntry adapterTypePaletteEntry = (AdapterTypePaletteEntry)theEObject;
				T result = caseAdapterTypePaletteEntry(adapterTypePaletteEntry);
				if (result == null) result = casePaletteEntry(adapterTypePaletteEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PalettePackage.SUB_APPLICATION_TYPE_PALETTE_ENTRY: {
				SubApplicationTypePaletteEntry subApplicationTypePaletteEntry = (SubApplicationTypePaletteEntry)theEObject;
				T result = caseSubApplicationTypePaletteEntry(subApplicationTypePaletteEntry);
				if (result == null) result = casePaletteEntry(subApplicationTypePaletteEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Palette</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Palette</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePalette(Palette object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePaletteGroup(PaletteGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>FB Type Palette Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>FB Type Palette Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFBTypePaletteEntry(FBTypePaletteEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePaletteEntry(PaletteEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Device Type Palette Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Device Type Palette Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeviceTypePaletteEntry(DeviceTypePaletteEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource Type Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource Type Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourceTypeEntry(ResourceTypeEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Segment Type Palette Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Segment Type Palette Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSegmentTypePaletteEntry(SegmentTypePaletteEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter Type Palette Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter Type Palette Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapterTypePaletteEntry(AdapterTypePaletteEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sub Application Type Palette Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sub Application Type Palette Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubApplicationTypePaletteEntry(SubApplicationTypePaletteEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //PaletteSwitch
