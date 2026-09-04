/*******************************************************************************
 * Copyright (c) 2026 Franz Höpfinger
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Franz Höpfinger - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.model.dataimport;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import org.eclipse.fordiac.ide.model.dataexport.FbtExporter;
import org.eclipse.fordiac.ide.model.dataimport.FBTImporter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.junit.jupiter.api.Test;

@SuppressWarnings("nls")
class ServiceCommentImportExportTest {

	@Test
	void reimportedServiceKeepsItsComment() throws Exception {
		final ServiceInterfaceFBType type = LibraryElementFactory.eINSTANCE.createServiceInterfaceFBType();
		type.setName("Test");
		type.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());

		final Service service = LibraryElementFactory.eINSTANCE.createService();
		service.setRightInterface(createServiceInterface("RESOURCE"));
		service.setLeftInterface(createServiceInterface("APPLICATION"));
		service.setComment("Subscribe to a PUBLISH_10 Block");
		type.setService(service);

		final TypeLibrary typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(null);
		final ServiceInterfaceFBType reimported;
		try (InputStream exported = new FbtExporter(type).getFileContent()) {
			final FBTImporter importer = new FBTImporter(exported, typeLib);
			importer.loadElement();
			reimported = (ServiceInterfaceFBType) importer.getElement();
		}

		assertEquals(service.getComment(), reimported.getService().getComment());
	}

	private static ServiceInterface createServiceInterface(final String name) {
		final ServiceInterface result = LibraryElementFactory.eINSTANCE.createServiceInterface();
		result.setName(name);
		return result;
	}
}
