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
import org.eclipse.fordiac.ide.model.dataexport.AbstractBlockTypeExporter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class FunctionBlockResource extends ResourceImpl {
	
	public FunctionBlockResource(URI uri) {
		super(uri);
	}
	
	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		IFile fbtFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(this.uri.toPlatformString(true)));
		final var paletteEntryForFile = TypeLibrary.getPaletteEntryForFile(fbtFile);
		//Load the Type
		var lib = paletteEntryForFile.getType();
		//Do not modify any fordiac element
		getContents().add(lib);		
	}
	
	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		var fbtFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(this.uri.toPlatformString(true)));
		var paletteEntryForFile = TypeLibrary.getPaletteEntryForFile(fbtFile);
		if (paletteEntryForFile == null) {
			paletteEntryForFile = TypeLibrary.getTypeLibrary(fbtFile.getProject()).createPaletteEntry(fbtFile);
		} 			
		paletteEntryForFile.setType((LibraryElement)getContents().get(0));
		
		paletteEntryForFile.setLastModificationTimestamp(paletteEntryForFile.getFile().getModificationStamp());
		
		AbstractBlockTypeExporter.saveType(paletteEntryForFile, outputStream);		
	}
	
	@Override
	protected void doUnload() {
		getContents().clear();
	}
}
