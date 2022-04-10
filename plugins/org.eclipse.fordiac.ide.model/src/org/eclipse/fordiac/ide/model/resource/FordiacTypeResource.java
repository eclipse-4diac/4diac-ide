/********************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Austria
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
 ********************************************************************************/

package org.eclipse.fordiac.ide.model.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class FordiacTypeResource extends ResourceImpl {

	public FordiacTypeResource(final URI uri) {
		super(uri);
	}

	@Override
	protected void doLoad(final InputStream inputStream, final Map<?, ?> options) throws IOException {
		final IFile fbtFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(this.uri.toPlatformString(true)));
		final var typeEntryForFile = TypeLibrary.getTypeEntryForFile(fbtFile);
		//Load the Type
		final var lib = EcoreUtil.copy(typeEntryForFile.getType());
		//Do not modify any fordiac element
		getContents().add(lib);
	}

	@Override
	protected void doSave(final OutputStream outputStream, final Map<?, ?> options) throws IOException {
		final var fbtFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(this.uri.toPlatformString(true)));
		var typeEntryForFile = TypeLibrary.getTypeEntryForFile(fbtFile);
		if (typeEntryForFile == null) {
			typeEntryForFile = TypeLibrary.getTypeLibrary(fbtFile.getProject()).createTypeEntry(fbtFile);
		}
		typeEntryForFile.setTypeEditable((LibraryElement) getContents().get(0));
		typeEntryForFile.setLastModificationTimestamp(typeEntryForFile.getFile().getModificationStamp());
		AbstractTypeExporter.saveType(typeEntryForFile, outputStream);
	}

	@Override
	protected void doUnload() {
		getContents().clear();
	}
}