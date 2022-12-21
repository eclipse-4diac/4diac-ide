/********************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Austria
 * 				 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Antonio Garmendia,Bianca Wiesmayr
 *    - initial API and implementation and/or initial documentation
 *  Fabio Gandolfi
 *    - adapted for emf compare
 ********************************************************************************/

package org.eclipse.fordiac.ide.model.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.SystemImporter;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class SystemResource extends ResourceImpl {

	public SystemResource(final URI uri) {
		super(uri);
	}

	@Override
	protected void doLoad(final InputStream inputStream, final Map<?, ?> options) throws IOException {
		final IFile fbtFile = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(this.uri.toPlatformString(true)));

		final TypeLibrary typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(fbtFile.getProject());
		final SystemEntry sysEntry = typeLib.createSystemEntry(fbtFile);

		final SystemImporter importer = new SystemImporter(fbtFile);
		importer.loadElement();
		final AutomationSystem element = importer.getElement();
		element.setTypeEntry(sysEntry);
		getContents().add(element);
	}

	@Override
	protected void doSave(final OutputStream outputStream, final Map<?, ?> options) throws IOException {
		saveSystem((AutomationSystem) getContents().get(0), outputStream);
	}

	private static void saveSystem(final AutomationSystem system, final OutputStream outputStream) {
		final TypeEntry typeEntry = system.getTypeEntry();
		Assert.isNotNull(typeEntry); // there should be no system without type entry
		typeEntry.setLastModificationTimestamp(typeEntry.getFile().getModificationStamp());
		AbstractTypeExporter.saveType(typeEntry, outputStream);
	}

	@Override
	protected void doUnload() {
		getContents().clear();
	}

}

