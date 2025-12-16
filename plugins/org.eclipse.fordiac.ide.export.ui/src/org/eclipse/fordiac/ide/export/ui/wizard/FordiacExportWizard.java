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
import org.eclipse.fordiac.ide.export.Exporter;
import org.eclipse.fordiac.ide.export.IExportFilter;
import org.eclipse.fordiac.ide.export.ui.Messages;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.IDialogSettings;
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
	@SuppressWarnings("squid:S2142")
	public boolean performFinish() {
		page.saveWidgetValues();

		final List<IFile> exportees = collectExportees();

		if (!IDE.saveAllEditors(exportees.toArray(new IResource[exportees.size()]), true)) {
			return false;
		}

		final Exporter exporter = new Exporter(page.getSelectedExportFilter(), page.getDirectory(),
				page.overwriteWithoutWarning(), page.enableCMakeLists());
		try {
			setNeedsProgressMonitor(true);
			getContainer().run(true, true, monitor -> exporter.exportElements(monitor, exportees));
			Display.getDefault().asyncExec(() -> showErrorWarningSummary(exporter.getExportFilter()));
		} catch (final InterruptedException | InvocationTargetException e) {
			showExceptionErrorDialog(e);
		}

		return true;
	}

	protected static void showExceptionErrorDialog(final Exception e) {
		FordiacLogHelper.logError(e.getMessage(), e);
		final MessageBox msg = new MessageBox(Display.getDefault().getActiveShell());
		msg.setMessage(e.getMessage());
		msg.open();
	}

	protected static void showErrorWarningSummary(final IExportFilter filter) {
		if (filter != null && ((!filter.getErrors().isEmpty()) || (!filter.getWarnings().isEmpty()))) {
			new ExportStatusMessageDialog(Display.getDefault().getActiveShell(), filter.getWarnings(),
					filter.getErrors()).open();
		}
	}

	private final List<IFile> collectExportees() {
		final List<?> resources = page.getSelectedResources();
		return resources.parallelStream().filter(IFile.class::isInstance).map(IFile.class::cast).toList();
	}

}
