/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Primetals Technologies Austria GmbH
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
 *    Alois Zoitl  - turned the Palette model into POJOs
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Adapter Type Palette Entry</b></em>'. <!--
 * end-user-doc -->
 *
 * @generated */
public class AdapterTypePaletteEntryImpl extends AbstractTypeEntryImpl implements AdapterTypeEntry {
	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected AdapterTypePaletteEntryImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return PalettePackage.Literals.ADAPTER_TYPE_PALETTE_ENTRY;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public AdapterType getType() {
		final LibraryElement type = super.getType();
		if (type instanceof AdapterType) {
			return (AdapterType) type;
		}
		return null;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setType(final LibraryElement type) {
		if (type instanceof AdapterType) {
			super.setType(type);
		} else {
			super.setType(null);
			if (null != type) {
				FordiacLogHelper.logError("tried to set no AdapterType as type entry for AdapterTypePaletteEntry");  //$NON-NLS-1$
			}
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public CommonElementImporter getImporter() {
		return new org.eclipse.fordiac.ide.model.dataimport.ADPImporter(getFile());
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public AdapterType getTypeEditable() {
		final LibraryElement type = super.getTypeEditable();
		if (type instanceof AdapterType) {
			return (AdapterType) type;
		}
		return null;
	}

} // AdapterTypePaletteEntryImpl
