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
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.ErrorDataType;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.DataTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.DataTypeImporter;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class DataTypeResourceImpl extends AbstractLibraryElementResource<AnyDerivedType> {

	public DataTypeResourceImpl(final URI uri) {
		super(uri, AnyDerivedType.class);
	}

	@Override
	protected CommonElementImporter getTypeImporter(final InputStream inputStream, final TypeLibrary typeLib) {
		return new DataTypeImporter(inputStream, typeLib);
	}

	@Override
	protected AbstractTypeExporter getTypeExporter(final AnyDerivedType contentToSave) {
		return new DataTypeExporter(contentToSave);
	}

	@Override
	protected ErrorDataType createErrorLibraryElement() {
		return DataFactory.eINSTANCE.createErrorDataType();
	}
}
