/*******************************************************************************
 * Copyright (c) 2008, 2021 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Extract export process into own class for better code
 *                 readability and addressing several sonar issues
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.ui.wizard;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.IExportFilter;
import org.eclipse.fordiac.ide.export.ui.Messages;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.CMakeListsMarker;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * The Class FordiacExportWizard.
 */
public class FordiacExportWizard extends Wizard implements IExportWizard {

	private static final String FORDIAC_EXPORT_SECTION = "4DIAC_EXPORT_SECTION"; //$NON-NLS-1$

	private IStructuredSelection selection;
	private SelectFBTypesWizardPage page;

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection currentSelection) {
		final List<IResource> selectedResources = IDE.computeSelectedResources(currentSelection);
		this.selection = new StructuredSelection(selectedResources);
		final Bundle bundle = FrameworkUtil.getBundle(getClass());
		final IDialogSettings settings = PlatformUI.getDialogSettingsProvider(bundle).getDialogSettings();

		if (null == settings.getSection(FORDIAC_EXPORT_SECTION)) {
			// section does not exist create a section
			settings.addNewSection(FORDIAC_EXPORT_SECTION);
		}
		setDialogSettings(settings);
		setWindowTitle(Messages.FordiacExportWizard_LABEL_Window_Title);
	}

	@Override
	public void addPages() {
		super.addPages();

		page = new SelectFBTypesWizardPage(Messages.FordiacExportWizard_WizardPage, selection);
		page.setDescription(Messages.FordiacExportWizard_DESCRIPTION_WizardPage);
		page.setTitle(Messages.FordiacExportWizard_TITLE_WizardPage);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		page.saveWidgetValues();

		final Exporter exporter = new Exporter(page.getSelectedExportFilter(), collectExportees(), page.getDirectory(),
				page.overwriteWithoutWarning());
		try {
			new ProgressMonitorDialog(getShell()).run(false, false, exporter);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();  // mark interruption
			showExceptionErrorDialog(e);
		} catch (final Exception e) {
			showExceptionErrorDialog(e);
		}

		return true;
	}

	protected static void showExceptionErrorDialog(final Exception e) {
		FordiacLogHelper.logError(e.getMessage(), e);
		final MessageBox msg = new MessageBox(Display.getDefault().getActiveShell());
		msg.setMessage(Messages.FordiacExportWizard_ERROR + e.getMessage());
		msg.open();
	}

	private final List<LibraryElement> collectExportees() {
		final List<Object> resources = page.getSelectedResources();
		final List<LibraryElement> exportees = resources.parallelStream().filter(IFile.class::isInstance)
				.map(exportee -> TypeLibrary.getPaletteEntryForFile((IFile) exportee)).filter(Objects::nonNull)
				.map(PaletteEntry::getType)
				.collect(Collectors.toList());

		if (page.enableCMakeLists()) {
			exportees.add(new CMakeListsMarker());
		}
		return exportees;
	}

	private static class Exporter implements IRunnableWithProgress {

		private final List<LibraryElement> exportees;
		private final String outputDirectory;
		private final IConfigurationElement conf;
		private final boolean overwriteWithoutWarning;

		public Exporter(final IConfigurationElement conf, final List<LibraryElement> exportees,
				final String outputDirectory, final boolean overwriteWithoutWarning) {
			this.conf = conf;
			this.exportees = exportees;
			this.outputDirectory = outputDirectory;
			this.overwriteWithoutWarning = overwriteWithoutWarning;
		}

		@Override
		public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			monitor.beginTask(MessageFormat.format(Messages.FordiacExportWizard_ExportingSelectedTypesUsingExporter,
					conf.getAttribute("name")), exportees.size()); //$NON-NLS-1$

			final IExportFilter filter = createExportFilter();
			if (null != filter) {
				for (final LibraryElement type : exportees) {
					exportElement(monitor, filter, type);
					monitor.worked(1);
				}
				showErrorWarningSummary(filter);
			}
			monitor.done();
		}

		private void exportElement(final IProgressMonitor monitor, final IExportFilter filter,
				final LibraryElement type) {
			try {
				if (type instanceof CMakeListsMarker) {
					monitor.subTask(Messages.FordiacExportWizard_ExportingCMakeLists);
					filter.export(null, outputDirectory, overwriteWithoutWarning, type);
				} else {
					monitor.subTask(MessageFormat.format(Messages.FordiacExportWizard_ExportingType,
							type.getPaletteEntry().getLabel()));
					filter.export(type.getPaletteEntry().getFile(), outputDirectory, overwriteWithoutWarning,
							type);
				}

			} catch (final ExportException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
				final MessageBox msg = new MessageBox(Display.getDefault().getActiveShell());
				msg.setMessage(Messages.FordiacExportWizard_ERROR + e.getMessage());
				msg.open();
			}
		}

		private IExportFilter createExportFilter() {
			IExportFilter filter = null;
			try {
				filter = (IExportFilter) conf.createExecutableExtension("class"); //$NON-NLS-1$
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
				final MessageBox msg = new MessageBox(Display.getDefault().getActiveShell());
				msg.setMessage(Messages.FordiacExportWizard_ERROR + e.getMessage());
				msg.open();
			}
			return filter;
		}

		private static void showErrorWarningSummary(final IExportFilter filter) {
			if ((!filter.getErrors().isEmpty()) || (!filter.getWarnings().isEmpty())) {
				new ExportStatusMessageDialog(Display.getDefault().getActiveShell(), filter.getWarnings(),
						filter.getErrors()).open();
			}
		}

	}

}
