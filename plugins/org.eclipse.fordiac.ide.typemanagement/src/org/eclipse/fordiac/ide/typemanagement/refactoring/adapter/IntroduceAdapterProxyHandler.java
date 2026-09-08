/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.adapter;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.core.refactoring.PerformChangeOperation;
import org.eclipse.ui.handlers.HandlerUtil;

public class IntroduceAdapterProxyHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection sel = HandlerUtil.getCurrentSelection(event);

		final AdapterTypeEntry atEntry = resolveAdapterTypeEntry(sel);
		if (atEntry == null) {
			return null;
		}

		final String adapterName = atEntry.getTypeName();
		final String proxyName = adapterName + "_Proxy";

		final IFile adpFile = atEntry.getFile();
		final IProject project = adpFile.getProject();

		// prefer same folder as the .adp
		final IFolder targetFolder = (adpFile.getParent() instanceof final IFolder folder) ? folder
				: project.getFolder("Type Library");

		final CreateAdapterProxyTypeChange change = new CreateAdapterProxyTypeChange(targetFolder, proxyName,
				adapterName);

		try {
			new PerformChangeOperation(change).run(null);

		} catch (final CoreException e) {
			throw new ExecutionException("Insert Adapter Proxy failed", e); //$NON-NLS-1$
		}

		return null;
	}

	private static AdapterTypeEntry resolveAdapterTypeEntry(final ISelection selection) {
		if (!(selection instanceof final IStructuredSelection iss) || iss.isEmpty()) {
			return null;
		}
		final Object first = iss.getFirstElement();

		if (first instanceof final AdapterTypeEntry f) {
			return f;
		}

		if (first instanceof final IFile f
				&& TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING.equalsIgnoreCase(f.getFileExtension())) {
			final TypeEntry te = TypeLibraryManager.INSTANCE.getTypeEntryForFile(f);
			if (te instanceof final AdapterTypeEntry at) {
				return at;
			}
		}
		return null;
	}

}
