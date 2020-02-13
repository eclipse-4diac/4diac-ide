/*******************************************************************************
 * Copyright (c) 2008, 2009, 2013, 2016, 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Waldemar Eisenmenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemimport;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.dataimport.SystemImporter;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.Activator;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

/**
 * The Class SystemImport.
 *
 * @author gebenh, eisenmenger
 */
public class SystemImport extends Wizard implements IImportWizard {
	private static final String FORDIAC_SYSTEM_IMPORT_SECTION = "4DIAC_SYSTEM_IMPORT_SECTION"; //$NON-NLS-1$

	/**
	 * Instantiates a new system import.
	 */
	public SystemImport() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings();

		IDialogSettings dialogSettings = settings.getSection(FORDIAC_SYSTEM_IMPORT_SECTION);
		if (dialogSettings == null) {
			dialogSettings = settings.addNewSection(FORDIAC_SYSTEM_IMPORT_SECTION);
		}
		setDialogSettings(dialogSettings);
	}

	/** The page */
	private IEC61499_2ImportWizardPage page;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		super.addPages();
		page = new IEC61499_2ImportWizardPage("Import System");
		page.setDescription("Importing a system configuration to 4diac IDE");
		page.setTitle("System Import");
		addPage(page);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		IRunnableWithProgress op = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				AutomationSystem system = SystemManager.INSTANCE.createLocalProject(page.getProjectName());
				try (InputStream stream = new FileInputStream(page.getSelectedSystemFile());) {
					SystemImporter sysImporter = new SystemImporter(stream, system);
					sysImporter.importSystem();
					SystemManager.INSTANCE.saveSystem(system);
				} catch (IOException | XMLStreamException | TypeImportException e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
			}
		};
		try {
			new ProgressMonitorDialog(getShell()).run(false, false, op);
		} catch (Exception e) {
			MessageBox msg = new MessageBox(Display.getDefault().getActiveShell());
			msg.setMessage("Import Error:\n" + e.getMessage());
			msg.open();

			Activator.getDefault().logError(msg.getMessage(), e);

		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

}
