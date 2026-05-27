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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.phrase.FbtMacroProcessor;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.browser.LocationAdapter;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

final class ModelLocationListener extends LocationAdapter {

	@Override
	public void changing(final LocationEvent event) {
		if (event.location != null && event.location.startsWith(FbtMacroProcessor.FBT_TYPE_ENTRY_URI)) {
			openFbtEntry(event.location.substring(FbtMacroProcessor.FBT_TYPE_ENTRY_URI.length()));
			event.doit = false;
		}
	}

	private static void openFbtEntry(final String substring) {
		final IFile targetFile = getFileFromEmfString(substring);
		if (targetFile == null) {
			return;
		}

		final IEditorPart editor = openEditor(targetFile);
		if (editor instanceof final ISelectionListener selListener) {
			selListener.selectionChanged(editor, new StructuredSelection(URI.createURI(substring)));
		}
	}

	private static IEditorPart openEditor(final IFile targetFile) {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
				.getDefaultEditor(targetFile.getName());
		try {
			return page.openEditor(new FileEditorInput(targetFile), desc.getId());
		} catch (final PartInitException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
		return null;
	}

	public static IFile getFileFromEmfString(String uriString) {
		if (uriString == null || uriString.isEmpty()) {
			return null;
		}

		if (uriString.startsWith("platform/")) { //$NON-NLS-1$
			uriString = "platform:/" + uriString.substring("platform/".length()); //$NON-NLS-1$ //$NON-NLS-2$
		}

		final URI uri = URI.createURI(uriString).trimFragment();

		if (uri.isPlatformResource()) {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true)));
		}

		return null;
	}
}