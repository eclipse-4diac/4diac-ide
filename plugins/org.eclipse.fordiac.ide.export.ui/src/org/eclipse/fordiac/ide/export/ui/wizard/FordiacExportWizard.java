/*******************************************************************************
 * Copyright (c) 2008 - 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                           Johannes Kepler University Linz
 *                           Primetals Technologies Austria GmbH
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
 *   Ernst Blecha - Add "Overwrite All" and "Cancel All"
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.ui.wizard;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.IExportFilter;
import org.eclipse.fordiac.ide.export.ui.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.CMakeListsMarker;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.IDialogSettings;
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

/** The Class FordiacExportWizard. */
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

		final List<IFile> exportees = collectExportees();

		if (!IDE.saveAllEditors(exportees.toArray(new IResource[exportees.size()]), true)) {
			return false;
		}

		final Exporter exporter = new Exporter(page.getSelectedExportFilter(), exportees, page.getDirectory(),
				page.overwriteWithoutWarning(), page.enableCMakeLists());
		try {
			setNeedsProgressMonitor(true);
			getContainer().run(true, true, exporter);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt(); // mark interruption
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

	private final List<IFile> collectExportees() {
		final List<?> resources = page.getSelectedResources();
		return resources.parallelStream().filter(IFile.class::isInstance).map(IFile.class::cast).toList();
	}

	private static class Exporter implements IRunnableWithProgress {

		private final List<IFile> exportees;
		private final String outputDirectory;
		private final IConfigurationElement conf;
		private boolean overwriteWithoutWarning;
		private boolean enableCMakeLists;

		public Exporter(final IConfigurationElement conf, final List<IFile> exportees, final String outputDirectory,
				final boolean overwriteWithoutWarning, final boolean enableCMakeLists) {
			this.conf = conf;
			this.exportees = exportees;
			this.outputDirectory = outputDirectory;
			this.overwriteWithoutWarning = overwriteWithoutWarning;
			this.enableCMakeLists = enableCMakeLists;
		}

		@Override
		public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			monitor.beginTask(MessageFormat.format(Messages.FordiacExportWizard_ExportingSelectedTypesUsingExporter,
					conf.getAttribute("name")), exportees.size() + 1); //$NON-NLS-1$

			final IExportFilter filter = createExportFilter();
			if (null != filter) {
				for (final IFile file : exportees) {

					if (monitor.isCanceled()) {
						break;
					}
					try {
						exportElement(monitor, filter, file, null);
					} catch (final ExportException.OverwriteAll e) {
						overwriteWithoutWarning = true;
					} catch (final ExportException.CancelAll e) {
						enableCMakeLists = false;
						filter.getWarnings().add(Messages.FordiacExportWizard_EXPORT_CANCELED);
						break;
					} catch (final ExportException.UserInteraction e) {
						// noop
					}
					monitor.worked(1);
				}
				if (enableCMakeLists && !monitor.isCanceled()) {
					try {
						exportElement(monitor, filter, null, new CMakeListsMarker());
					} catch (final ExportException.UserInteraction e) {
						// noop
					}
					monitor.worked(1);
				}
				monitor.worked(1);
				if (monitor.isCanceled()) {
					filter.getErrors().add(Messages.FordiacExportWizard_EXPORT_CANCELED);
				}
				Display.getDefault().asyncExec(() -> showErrorWarningSummary(filter));
			}
			monitor.done();
		}

		private void exportElement(final IProgressMonitor monitor, final IExportFilter filter, final IFile file,
				final EObject source) throws ExportException.UserInteraction {
			try {
				if (source instanceof CMakeListsMarker) {
					monitor.subTask(Messages.FordiacExportWizard_ExportingCMakeLists);
					filter.export(null, outputDirectory, overwriteWithoutWarning, source);
				} else {
					String name = "anonymous"; //$NON-NLS-1$
					if (source instanceof final INamedElement ne) {
						name = ne.getName();
					} else if (file != null) {
						name = file.getFullPath().removeFileExtension().lastSegment();
					}

					monitor.subTask(MessageFormat.format(Messages.FordiacExportWizard_ExportingType, name));
					filter.export(file, outputDirectory, overwriteWithoutWarning, source);
				}
			} catch (final ExportException.UserInteraction e) {
				throw (e);
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
