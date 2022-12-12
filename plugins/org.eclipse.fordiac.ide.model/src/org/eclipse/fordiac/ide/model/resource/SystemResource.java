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
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.dataimport.SystemImporter;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;


public class SystemResource extends ResourceImpl {

	public SystemResource(final URI uri) {
		super(uri);
	}

	@Override
	protected void doLoad(final InputStream inputStream, final Map<?, ?> options) throws IOException {
		final IFile fbtFile = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(this.uri.toPlatformString(true)));
		final SystemImporter importer = new SystemImporter(fbtFile);
		importer.loadElement();
		final AutomationSystem element = importer.getElement();
		getContents().add(element);
	}

	@Override
	protected void doSave(final OutputStream outputStream, final Map<?, ?> options) throws IOException {
		saveSystem((AutomationSystem) getContents().get(0));
	}

	private static void saveSystem(final AutomationSystem system) {
		Assert.isNotNull(system.getTypeEntry()); // there should be no system without type entry
		system.getTypeEntry().setLastModificationTimestamp(system.getSystemFile().getModificationStamp() + 1);
		system.getTypeEntry().save();
	}

	@Override
	protected void doUnload() {
		getContents().clear();
	}

}

