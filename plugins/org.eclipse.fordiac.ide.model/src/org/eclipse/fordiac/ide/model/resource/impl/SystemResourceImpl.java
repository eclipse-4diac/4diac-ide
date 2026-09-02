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
import org.eclipse.fordiac.ide.model.dataexport.SystemExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.SystemImporter;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorAutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class SystemResourceImpl extends AbstractLibraryElementResource<AutomationSystem> {

	public SystemResourceImpl(final URI uri) {
		super(uri, AutomationSystem.class);
	}

	@Override
	protected CommonElementImporter getTypeImporter(final InputStream inputStream, final TypeLibrary typeLib) {
		return new SystemImporter(inputStream, typeLib);
	}

	@Override
	protected AbstractTypeExporter getTypeExporter(final AutomationSystem contentToSave) {
		return new SystemExporter(contentToSave);
	}

	@Override
	protected ErrorAutomationSystem createErrorLibraryElement() {
		final ErrorAutomationSystem errorSystem = LibraryElementFactory.eINSTANCE.createErrorAutomationSystem();
		errorSystem.setSystemConfiguration(LibraryElementFactory.eINSTANCE.createSystemConfiguration());
		return errorSystem;
	}
}
