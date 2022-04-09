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
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>FB Type Palette Entry</b></em>'. <!--
 * end-user-doc -->
 *
 * @generated */
public class FBTypePaletteEntryImpl extends AbstractTypeEntryImpl implements FBTypeEntry {
	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected FBTypePaletteEntryImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return PalettePackage.Literals.FB_TYPE_PALETTE_ENTRY;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public FBType getType() {
		final LibraryElement type = super.getType();
		if (type instanceof FBType) {
			return (FBType) type;
		}
		return null;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setType(final LibraryElement type) {
		if (type instanceof FBType) {
			super.setType(type);
		} else {
			super.setType(null);
			if (null != type) {
				FordiacLogHelper.logError("tried to set no FBType as type entry for FBTypePaletteEntry"); //$NON-NLS-1$
			}
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public CommonElementImporter getImporter() {
		return new org.eclipse.fordiac.ide.model.dataimport.FBTImporter(getFile());
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public FBType getTypeEditable() {
		final LibraryElement type = super.getTypeEditable();
		if (type instanceof FBType) {
			return (FBType) type;
		}
		return null;
	}

} // FBTypePaletteEntryImpl
