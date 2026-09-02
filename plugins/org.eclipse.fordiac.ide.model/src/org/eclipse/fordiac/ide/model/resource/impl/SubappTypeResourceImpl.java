/********************************************************************************
 * Copyright (c) 2026 Johannes Kepler University Austria
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Alois Zoitl - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.resource.impl;

import java.io.InputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.SubApplicationTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.SubAppTImporter;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorSubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class SubappTypeResourceImpl extends AbstractLibraryElementResource<SubAppType> {

	public SubappTypeResourceImpl(final URI uri) {
		super(uri, SubAppType.class);
	}

	@Override
	protected CommonElementImporter getTypeImporter(final InputStream inputStream, final TypeLibrary typeLib) {
		return new SubAppTImporter(inputStream, typeLib);
	}

	@Override
	protected AbstractTypeExporter getTypeExporter(final SubAppType contentToSave) {
		return new SubApplicationTypeExporter(contentToSave);
	}

	@Override
	protected ErrorSubAppType createErrorLibraryElement() {
		final ErrorSubAppType type = LibraryElementFactory.eINSTANCE.createErrorSubAppType();
		type.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		type.setFBNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());
		return type;
	}
}
