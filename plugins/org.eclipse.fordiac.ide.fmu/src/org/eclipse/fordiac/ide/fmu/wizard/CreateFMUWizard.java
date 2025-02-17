/*******************************************************************************
 * Copyright (c) 2017 - 2018 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fmu.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.fordiac.ide.fmu.Messages;
import org.eclipse.fordiac.ide.fmu.preferences.FMUPreferenceConstants;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.providers.DialogSettingsProvider;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

public class CreateFMUWizard extends Wizard implements IExportWizard {

	private static final String FORDIAC_CREATE_FMU_SECTION = "4DIAC_CREATE_FMU_SECTION"; //$NON-NLS-1$

	private IStructuredSelection selection;
	private CreateFMUWizardPage page;

	public CreateFMUWizard() {
		setWindowTitle(Messages.FordiacCreateFMUWizard_LABEL_Window_Title);

		final IDialogSettings settings = DialogSettingsProvider.getDialogSettings(getClass());

		final IDialogSettings dialogSettings = settings.getSection(FORDIAC_CREATE_FMU_SECTION);
		if (dialogSettings == null) {
			settings.addNewSection(FORDIAC_CREATE_FMU_SECTION);
		}
		setDialogSettings(settings);
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		this.selection = new StructuredSelection(selection.toList());
	}

	@Override
	public void addPages() {
		super.addPages();
		page = new CreateFMUWizardPage(selection);
		addPage(page);
	}

	@Override
	public boolean performFinish() {

		// Store the selection in the preferences
		if (page.getStoreSelectedLibaries().getSelection()) {
			final IPreferenceStore store = FMUPreferenceConstants.STORE;
			store.setValue(FMUPreferenceConstants.P_FMU_WIN32, page.getWin32Field().getSelection());
			store.setValue(FMUPreferenceConstants.P_FMU_WIN64, page.getWin64Field().getSelection());
			store.setValue(FMUPreferenceConstants.P_FMU_LIN32, page.getLinux32Field().getSelection());
			store.setValue(FMUPreferenceConstants.P_FMU_LIN64, page.getLinux64Field().getSelection());
		}

		final IRunnableWithProgress iop = monitor -> {

			final String outputDirectory = page.getDirectory();
			final Map<Device, List<Resource>> toDeploy = addResourcesAndDevices();
			// Store the selected libraries to include in the FMU
			final List<String> librariesToAdd = getLibraries();

			for (final Entry<Device, List<Resource>> entry : toDeploy.entrySet()) {
				FMUDeviceManagementCommunicationHandler.createFMU(entry.getKey(), entry.getValue(), librariesToAdd,
						outputDirectory, getShell(), monitor);

			}
			monitor.done();
		};

		try {
			new ProgressMonitorDialog(getShell()).run(false, false, iop);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt(); // mark interruption
			showCreationExceptionDialog(e);
		} catch (final Exception e) {
			showCreationExceptionDialog(e);
		}

		return true;
	}

	protected void showCreationExceptionDialog(final Exception e) {
		final MessageBox msg = new MessageBox(getShell(), SWT.ERROR);
		msg.setMessage(Messages.CreateFMUWizard_FMUCreationError + e.getMessage());
		msg.open();
		FordiacLogHelper.logError(msg.getMessage(), e);
	}

	private List<String> getLibraries() {

		final List<String> libs = new ArrayList<>();
		if (page.getWin32Field().isEnabled() && page.getWin32Field().getSelection()) {
			libs.add(FMUPreferenceConstants.P_FMU_WIN32);
		}
		if (page.getWin64Field().isEnabled() && page.getWin64Field().getSelection()) {
			libs.add(FMUPreferenceConstants.P_FMU_WIN64);
		}
		if (page.getLinux32Field().isEnabled() && page.getLinux32Field().getSelection()) {
			libs.add(FMUPreferenceConstants.P_FMU_LIN32);
		}
		if (page.getLinux64Field().isEnabled() && page.getLinux64Field().getSelection()) {
			libs.add(FMUPreferenceConstants.P_FMU_LIN64);
		}
		return libs;
	}

	private Map<Device, List<Resource>> addResourcesAndDevices() {
		final Object[] selectedElements = page.getSelectedElements();
		final HashMap<Device, List<Resource>> toDeploy = new HashMap<>();

		for (final Object object : selectedElements) {
			if (object instanceof Resource) {
				insertResource(toDeploy, (Resource) object);
			} else if (object instanceof Device) {
				// if the device is selected we need to add it so that its parameters are added
				// as well
				getWorkLoadEntryList(toDeploy, (Device) object);
			}
		}
		return toDeploy;
	}

	private static void insertResource(final Map<Device, List<Resource>> workLoad, final Resource res) {
		final List<Resource> resList = getWorkLoadEntryList(workLoad, res.getDevice());
		resList.add(res);
	}

	private static List<Resource> getWorkLoadEntryList(final Map<Device, List<Resource>> toDeploy,
			final Device device) {
		return toDeploy.computeIfAbsent(device, dev -> new ArrayList<>());
	}

}
