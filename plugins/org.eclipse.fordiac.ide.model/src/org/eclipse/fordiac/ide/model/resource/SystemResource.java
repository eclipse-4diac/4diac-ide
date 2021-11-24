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
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.dataexport.SystemExporter;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;


public class SystemResource extends ResourceImpl {
	
	public SystemResource(URI uri) {
		super(uri);	
	}
	
	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		saveSystem((AutomationSystem) getContents().get(0));
	}
	
	public void saveSystem(final AutomationSystem system) {
		saveSystem(system, system.getSystemFile());
	}
	
	public void saveSystem(final AutomationSystem system, final IFile file) {
		Assert.isNotNull(system.getPaletteEntry()); // there should be no system without palette entry
		system.getPaletteEntry().setLastModificationTimestamp(file.getModificationStamp() + 1);
		final SystemExporter systemExporter = new SystemExporter(system);
		systemExporter.saveSystem(file);
	}

}
