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
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.ILog;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.export.LibraryExporter;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.IDE;
import org.osgi.framework.Version;

public class LibraryExportWizard extends Wizard implements IExportWizard {

	private Collection<Library> availableLibraries;

	private LibrarySelectionPage selectionPage;
	private LibraryExportPreviewPage previewPage;

	private IProject project;
	private String version;

	public LibraryExportWizard() {
		setNeedsProgressMonitor(true);
	}

	@Override
	public boolean performFinish() {
		if (!saveEditors()) {
			return false;
		}

		final Library library = selectionPage.getSelectedLibrary();
		final String outputDirectory = selectionPage.getOutputDirectory();
		final boolean exportAsSIFB = selectionPage.exportTypesAsSIFB();

		final LibraryExporter exporter = new LibraryExporter(outputDirectory, exportAsSIFB, library, version);

		final List<TypeEntry> exportTypes = previewPage.getTypes().included();

		try {
			getContainer().run(true, true, progress -> {
				try {
					exporter.export(exportTypes, progress);
				} catch (final IOException e) {
					throw new InvocationTargetException(e);
				}
			});
		} catch (final InvocationTargetException e) {
			final Throwable cause = e.getCause();
			ILog.get().error(e.getMessage(), e);

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

		previewPage = new LibraryExportPreviewPage();
		addPage(previewPage);
	}

	@Override
	public IWizardPage getNextPage(final IWizardPage page) {
		final var nextPage = super.getNextPage(page);
		if (nextPage instanceof final LibraryExportPreviewPage prPage) {
			prPage.setInput(LibraryExporter.getTypesSorted(project, selectionPage.getSelectedLibrary(),
					selectionPage.getTypeSelection()));
		}
		return nextPage;
	}

	private boolean saveEditors() {
		final IResource[] relevantResourceRoots = { project.getFolder(TypeLibraryTags.TYPE_LIB_FOLDER_NAME),
				project.getFile(LibraryManager.MANIFEST) };

		return IDE.saveAllEditors(relevantResourceRoots, true);
	}

	private static IProject getProject(final IStructuredSelection selection) {
		if (selection != null && selection.getFirstElement() instanceof final IResource resource) {
			return resource.getProject();
		}
		return null;
	}

}
