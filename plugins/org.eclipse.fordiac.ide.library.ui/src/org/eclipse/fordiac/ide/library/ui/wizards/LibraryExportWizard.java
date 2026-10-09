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
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.wizards;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.export.LibraryExporter;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.IDE;
import org.osgi.framework.Version;

public class LibraryExportWizard extends Wizard implements IExportWizard {

	private Collection<Library> availableLibraries;
	private LibrarySelectionPage selectionPage;
	private IProject project;
	private String version;

	@Override
	public boolean performFinish() {
		final IResource[] relevantResourceRoots = { project.getFolder(TypeLibraryTags.TYPE_LIB_FOLDER_NAME),
				project.getFile(LibraryManager.MANIFEST) };

		if (!IDE.saveAllEditors(relevantResourceRoots, true)) {
			return false;
		}

		final LibraryExporter exporter = new LibraryExporter(selectionPage.getOutputDirectory(),
				selectionPage.getTypeSelection(), selectionPage.getSelectedLibrary(), version, project);

		try {
			getContainer().run(true, true, progress -> {
				try {
					exporter.export(progress);
				} catch (final IOException e) {
					throw new InvocationTargetException(e);
				}
			});
		} catch (final InvocationTargetException e) {
			final Throwable cause = e.getCause();
			FordiacLogHelper.logError(e.getMessage(), e);

			MessageDialog.openError(getShell(), Messages.LibraryExporter_ErrorTitle,
					MessageFormat.format(Messages.LibraryExporter_ErrorMessage, cause.getMessage()));

			return false;
		} catch (final InterruptedException _) {
			Thread.currentThread().interrupt();
			return false;
		}
		return true;
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		project = getProject(selection);

		if (project == null) {
			availableLibraries = Collections.emptyList();
			return;
		}

		final var manifest = ManifestHelper.getOrCreateProjectManifest(project);
		availableLibraries = manifest != null && manifest.getExports() != null ? manifest.getExports().getLibrary()
				: Collections.emptyList();

		version = ManifestHelper.getVersion(manifest, new Version("1.0.0")).toString(); //$NON-NLS-1$
	}

	@Override
	public void addPages() {
		selectionPage = new LibrarySelectionPage(availableLibraries);
		addPage(selectionPage);
	}

	private static IProject getProject(final IStructuredSelection selection) {
		if (selection != null && selection.getFirstElement() instanceof final IResource resource) {
			return resource.getProject();
		}
		return null;
	}

}
