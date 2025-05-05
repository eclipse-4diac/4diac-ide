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
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.export.AbstractExporter;
import org.eclipse.fordiac.ide.export.IExportFilter;
import org.eclipse.fordiac.ide.export.ui.Messages;
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

		final ExporterRunnable exporter = new ExporterRunnable(page.getSelectedExportFilter(), exportees,
				page.getDirectory(), page.overwriteWithoutWarning(), page.enableCMakeLists());
		try {
			setNeedsProgressMonitor(true);
			getContainer().run(true, true, exporter);
		} catch (final Exception e) {
			showExceptionErrorDialog(e);
		}

		return true;
	}

	protected static void showExceptionErrorDialog(final Exception e) {
		FordiacLogHelper.logError(e.getMessage(), e);
		final MessageBox msg = new MessageBox(Display.getDefault().getActiveShell());
		msg.setMessage(e.getMessage()); // TODO add Messages.FordiacExport_ERROR + e.getMessage()
		msg.open();
	}

	private final List<IFile> collectExportees() {
		final List<?> resources = page.getSelectedResources();
		return resources.parallelStream().filter(IFile.class::isInstance).map(IFile.class::cast).toList();
	}

	private static class ExporterRunnable extends AbstractExporter implements IRunnableWithProgress {

		private final List<IFile> exportees;

		protected ExporterRunnable(final IConfigurationElement filterConfig, final List<IFile> exportees,
				final String outputDirectory, final boolean overwriteWithoutWarning, final boolean enableCMakeLists) {
			super(filterConfig, outputDirectory, overwriteWithoutWarning, enableCMakeLists);
			this.exportees = exportees;
		}

		@Override
		public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			exportElements(monitor, exportees);
		}

		@Override
		protected void showErrorWarningSummary(final IExportFilter filter) {
			if ((!filter.getErrors().isEmpty()) || (!filter.getWarnings().isEmpty())) {
				new ExportStatusMessageDialog(Display.getDefault().getActiveShell(), filter.getWarnings(),
						filter.getErrors()).open();
			}
		}

		@Override
		protected void processError(final String errorMessage) {
			FordiacLogHelper.logError(errorMessage);
			final MessageBox msg = new MessageBox(Display.getDefault().getActiveShell());
			msg.setMessage(errorMessage);
			msg.open();
		}

	}

}
