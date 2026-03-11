/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
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
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.SubApplicationTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.BlockTypeImporter;
import org.eclipse.fordiac.ide.model.dataimport.SubAppTImporter;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorSubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public class SubAppTypeEntryImpl extends AbstractInterfaceTypeEntryImpl<SubAppType> implements SubAppTypeEntry {

	public SubAppTypeEntryImpl() {
		super(SubAppType.class);
	}

	@Override
	protected BlockTypeImporter getImporter() {
		return new SubAppTImporter(getFile());
	}

	@Override
	protected ErrorSubAppType createErrorLibraryElement() {
		final ErrorSubAppType type = LibraryElementFactory.eINSTANCE.createErrorSubAppType();
		type.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		return type;
	}

	@Override
	protected AbstractTypeExporter getTypeExporter(final SubAppType type) {
		return new SubApplicationTypeExporter(type);
	}

	@Override
	public EClass getTypeEClass() {
		return LibraryElementPackage.Literals.SUB_APP_TYPE;
	}

	@Override
	public String getFileExtension() {
		return TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING;
	}
}
