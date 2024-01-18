/*******************************************************************************
 * Copyright (c) 2014, 2021 fortiss GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - updated bootfile exporting for new project layout
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.bootfile.wizard;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.fordiac.ide.deployment.bootfile.BootFileDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.bootfile.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.providers.DialogSettingsProvider;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

public class CreateBootfilesWizard extends Wizard implements IExportWizard {

	private static final String FORDIAC_CREATE_BOOTFILES_SECTION = "4DIAC_CREATE_BOOTFILES_SECTION"; //$NON-NLS-1$

	private IStructuredSelection selection;
	private CreateBootFilesWizardPage bootFilePage;

	public CreateBootfilesWizard() {
		// nothing to be done here
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		this.selection = new StructuredSelection(selection.toList());
		setWindowTitle(Messages.FordiacCreateBootfilesWizard_LABEL_Window_Title);
		final IDialogSettings settings = DialogSettingsProvider.getDialogSettings(getClass());

		if (null != settings.getSection(FORDIAC_CREATE_BOOTFILES_SECTION)) {
			// if section does not exist create it
			settings.addNewSection(FORDIAC_CREATE_BOOTFILES_SECTION);
		}
		setDialogSettings(settings);
	}

	@Override
	public void addPages() {
		super.addPages();
		bootFilePage = new CreateBootFilesWizardPage(selection);
		addPage(bootFilePage);
	}

	@Override
	public boolean performFinish() {
		bootFilePage.saveWidgetValues();

		final IRunnableWithProgress iop = monitor -> {
			final String outputDirectory = bootFilePage.getDirectory();
			final Map<Device, List<Object>> workLoad = prepareWorkload();
			monitor.beginTask(Messages.CreateBootfilesWizard_GeneratingBootFilesForTheSelectedResources,
					workLoad.size());
			for (final Entry<Device, List<Object>> entry : workLoad.entrySet()) {
				final String fileName = MessageFormat.format(Messages.CreateBootfilesWizard_IProgressMonitorMonitor,
						outputDirectory, Character.valueOf(File.separatorChar),
						entry.getKey().getAutomationSystem().getName(), entry.getKey().getName());

				BootFileDeviceManagementCommunicationHandler.createBootFile(entry.getValue(), fileName, getShell());
				monitor.worked(1);
			}
			monitor.done();
		};

		try {
			new ProgressMonitorDialog(getShell()).run(false, false, iop);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt(); // mark interruption
			showExceptionErrorDialog(e);
		} catch (final Exception e) {
			showExceptionErrorDialog(e);
		}

		return true;
	}

	protected void showExceptionErrorDialog(final Exception e) {
		final MessageBox msg = new MessageBox(getShell(), SWT.ERROR);
		msg.setMessage(Messages.CreateBootfilesWizard_BootFileCreationError + e.getMessage());
		msg.open();
		FordiacLogHelper.logError(msg.getMessage(), e);
	}

	private Map<Device, List<Object>> prepareWorkload() {
		final Object[] selectedElements = bootFilePage.getSelectedElements();
		final Map<Device, List<Object>> workLoad = new HashMap<>();

		for (final Object object : selectedElements) {
			if (object instanceof Resource) {
				insertResource(workLoad, (Resource) object);
			} else if (object instanceof Device) {
				// if the device is selected we need to add it so that its parameters are added
				// as well
				getWorkLoadEntryList(workLoad, (Device) object).add(object);
			}
		}
		return workLoad;
	}

	private static void insertResource(final Map<Device, List<Object>> workLoad, final Resource res) {
		final List<Object> resList = getWorkLoadEntryList(workLoad, res.getDevice());
		resList.add(res);
	}

	private static List<Object> getWorkLoadEntryList(final Map<Device, List<Object>> workLoad, final Device device) {
		return workLoad.computeIfAbsent(device, dev -> new ArrayList<>());
	}

}
