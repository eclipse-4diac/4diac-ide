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
import org.eclipse.fordiac.ide.model.dataexport.GlobalConstantsExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.GlobalConstantsImporter;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorGlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class GlobalConstTypeResourceImpl extends AbstractLibraryElementResource<GlobalConstants> {

	public GlobalConstTypeResourceImpl(final URI uri) {
		super(uri, GlobalConstants.class);
	}

	@Override
	protected CommonElementImporter getTypeImporter(final InputStream inputStream, final TypeLibrary typeLib) {
		return new GlobalConstantsImporter(inputStream, typeLib);
	}

	@Override
	protected AbstractTypeExporter getTypeExporter(final GlobalConstants contentToSave) {
		return new GlobalConstantsExporter(contentToSave);
	}

	@Override
	protected ErrorGlobalConstants createErrorLibraryElement() {
		return LibraryElementFactory.eINSTANCE.createErrorGlobalConstants();
	}
}
