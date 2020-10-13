/*******************************************************************************
 * Copyright (c) 2008 - 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.ui.wizard;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.IExportFilter;
import org.eclipse.fordiac.ide.export.ui.Activator;
import org.eclipse.fordiac.ide.export.ui.Messages;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
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
import org.eclipse.ui.ide.IDE;

/**
 * The Class FordiacExportWizard.
 */
public class FordiacExportWizard extends Wizard implements IExportWizard {

	private static final String FORDIAC_EXPORT_SECTION = "4DIAC_EXPORT_SECTION"; //$NON-NLS-1$

	private IStructuredSelection selection;

	/**
	 * Instantiates a new fordiac export wizard.
	 */
	public FordiacExportWizard() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings();

		if (null == settings.getSection(FORDIAC_EXPORT_SECTION)) {
			// section does not exist create a section
			settings.addNewSection(FORDIAC_EXPORT_SECTION);
		}
		setDialogSettings(settings);
		setWindowTitle(Messages.FordiacExportWizard_LABEL_Window_Title);
	}

	private SelectFBTypesWizardPage page;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		super.addPages();

		page = new SelectFBTypesWizardPage(Messages.FordiacExportWizard_WizardPage, selection);
		page.setDescription(Messages.FordiacExportWizard_DESCRIPTION_WizardPage);
		page.setTitle(Messages.FordiacExportWizard_TITLE_WizardPage);
		addPage(page);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		page.saveWidgetValues();

		final IConfigurationElement conf;
		final IExportFilter filter;
		try {
			conf = page.getSelectedExportFilter();
			filter = (IExportFilter) conf.createExecutableExtension(Messages.FordiacExportWizard_Class);
		} catch (CoreException e) {
			MessageBox msg = new MessageBox(Display.getDefault().getActiveShell());
			msg.setMessage(Messages.FordiacExportWizard_ERROR + e.getMessage());
			msg.open();
			Activator.getDefault().logError(msg.getMessage(), e);
			return true;
		}

		IRunnableWithProgress op = new IRunnableWithProgress() {

			@SuppressWarnings("rawtypes")
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				List resources = page.getSelectedResources();
				String outputDirectory = page.getDirectory();

				monitor.beginTask(MessageFormat.format(Messages.FordiacExportWizard_ExportingSelectedTypesUsingExporter,
						conf.getAttribute("name")), resources.size()); //$NON-NLS-1$

				for (Object object : resources) {
					if (object instanceof IFile) {
						IFile file = (IFile) object;

						PaletteEntry entry = TypeLibrary.getPaletteEntryForFile(file);
						LibraryElement type = entry.getType();

						monitor.subTask(
								MessageFormat.format(Messages.FordiacExportWizard_ExportingType, entry.getLabel()));

						try {
							if (null != type) {
								filter.export(file, outputDirectory, page.overwriteWithoutWarning(), type);
							} else {
								filter.export(file, outputDirectory, page.overwriteWithoutWarning());
							}
						} catch (ExportException e) {
							MessageBox msg = new MessageBox(Display.getDefault().getActiveShell());
							msg.setMessage(Messages.FordiacExportWizard_ERROR + e.getMessage());
							msg.open();
						}

						monitor.worked(1);
					}
				}

				monitor.done();

			}
		};

		try {
			new ProgressMonitorDialog(getShell()).run(false, false, op);
		} catch (Exception e) {
			MessageBox msg = new MessageBox(Display.getDefault().getActiveShell());
			msg.setMessage(Messages.FordiacExportWizard_ERROR + e.getMessage());
			msg.open();
		}

		if ((!filter.getErrors().isEmpty()) || (!filter.getWarnings().isEmpty())) {
			new ExportStatusMessageDialog(getShell(), filter.getWarnings(), filter.getErrors()).open();
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection currentSelection) {
		List selectedResources = IDE.computeSelectedResources(currentSelection);
		this.selection = new StructuredSelection(selectedResources);
	}

}
