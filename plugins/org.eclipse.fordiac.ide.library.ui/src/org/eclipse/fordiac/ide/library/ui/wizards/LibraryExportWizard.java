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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.export.LibraryExporter;
import org.eclipse.fordiac.ide.library.export.LibraryExporter.TypeSelection;
import org.eclipse.fordiac.ide.library.export.LibraryExporter.TypeSelectionResult;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.IDE;
import org.osgi.framework.Version;

public class LibraryExportWizard extends Wizard implements IExportWizard {
	private Collection<Library> availableLibraries = Collections.emptyList();

	private LibrarySelectionPage selectionPage;
	private LibraryExportPreviewPage previewPage;

	private IProject project;
	private String version;

	public LibraryExportWizard() {
		setNeedsProgressMonitor(true);
	}

	@Override
	public boolean performFinish() {
		final Library library = selectionPage.getSelectedLibrary();
		final String outputDirectory = selectionPage.getOutputDirectory();
		final boolean exportAsSIFB = selectionPage.exportTypesAsSIFB();

		final LibraryExporter exporter = new LibraryExporter(outputDirectory, exportAsSIFB, library, version);
		final var exportTypes = previewPage.getTypes().included();

		try {
			getContainer().run(true, true, progress -> {
				try {
					exporter.export(exportTypes, progress);
				} catch (final IOException e) {
					throw new InvocationTargetException(e);
				}
			});
		} catch (final InvocationTargetException e) {
			final String message = getErrorMessage(e);
			ILog.get().error(message, e);

			MessageDialog.openError(getShell(), Messages.LibraryExporter_ErrorTitle,
					MessageFormat.format(Messages.LibraryExporter_ErrorMessage, message));

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
			return;
		}

		if (!IDE.saveAllEditors(new IResource[] { project.getFile(LibraryManager.MANIFEST) }, true)) {
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
	public void createPageControls(final Composite pageContainer) {
		super.createPageControls(pageContainer);

		if (getContainer() instanceof final WizardDialog dialog) {
			dialog.addPageChangingListener(this::handlePageChanging);
		}
	}

	@Override
	public boolean canFinish() {
		return getContainer() != null && getContainer().getCurrentPage() == previewPage && previewPage.isPageComplete();
	}

	private boolean saveTypeEditors() {
		return IDE.saveAllEditors(new IResource[] { project.getFolder(TypeLibraryTags.TYPE_LIB_FOLDER_NAME) }, true);
	}

	private static IProject getProject(final IStructuredSelection selection) {
		if (selection != null && selection.getFirstElement() instanceof final IResource resource) {
			return resource.getProject();
		}
		return null;
	}

	private TypeSelectionResult collectTypes() throws InvocationTargetException, InterruptedException {
		final Library library = selectionPage.getSelectedLibrary();
		final TypeSelection selection = selectionPage.getTypeSelection();

		final AtomicReference<TypeSelectionResult> result = new AtomicReference<>();

		getContainer().run(true, false, monitor -> {
			monitor.beginTask(Messages.LibraryExporter_CollectingTypes, IProgressMonitor.UNKNOWN);
			try {
				result.set(LibraryExporter.getTypesSorted(project, library, selection));
			} finally {
				monitor.done();
			}
		});

		return result.get();
	}

	private void handlePageChanging(final PageChangingEvent event) {
		if (event.getCurrentPage() != selectionPage || event.getTargetPage() != previewPage) {
			return;
		}

		if (!saveTypeEditors()) {
			event.doit = false;
			return;
		}

		try {
			final TypeSelectionResult types = collectTypes();
			previewPage.setInput(types);
		} catch (final InvocationTargetException e) {
			event.doit = false;
			final String message = getErrorMessage(e);
			ILog.get().error(message, e);
			MessageDialog.openError(getShell(), Messages.LibraryExporter_ErrorTitle, message);
		} catch (final InterruptedException _) {
			event.doit = false;
			Thread.currentThread().interrupt();
		}
	}

	private static String getErrorMessage(final InvocationTargetException exception) {
		final Throwable cause = exception.getCause() != null ? exception.getCause() : exception;
		return Objects.toString(cause.getMessage(), cause.toString());
	}

}
