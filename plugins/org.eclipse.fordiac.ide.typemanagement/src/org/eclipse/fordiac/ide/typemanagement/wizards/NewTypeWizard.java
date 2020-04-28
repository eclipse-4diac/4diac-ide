/*******************************************************************************
 * Copyright (c) 2010 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch
 *     - initial API and implementation and/or initial documentation
 *   Jose Cabral - Add preferences
 *   Alois Zoitl - moved openEditor helper function to EditorUtils
 *   Daniel Lindhuber, Bianca Wiesmayr, Ernst Blecha
 *     - extended for Data Type creation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.wizards;

import java.io.File;
import java.text.MessageFormat;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.dataexport.AbstractBlockTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.DataTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.ImportUtils;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.Activator;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.preferences.TypeManagementPreferencesHelper;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class NewTypeWizard extends Wizard implements INewWizard {
	private IStructuredSelection selection;
	private NewFBTypeWizardPage page1;
	private PaletteEntry entry;

	public NewTypeWizard() {
		setWindowTitle(FordiacMessages.NewType);
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		this.selection = selection;
		setWindowTitle(FordiacMessages.NewType);
	}

	@Override
	public void addPages() {
		page1 = createNewFBTypeWizardPage();
		addPage(page1);
	}

	protected NewFBTypeWizardPage createNewFBTypeWizardPage() {
		return new NewFBTypeWizardPage(selection);
	}

	@Override
	public boolean performFinish() {
		String typeName = page1.getFileName();
		File template = page1.getTemplate();
		if (!checkTemplateAvailable(template.getAbsolutePath())) {
			return false;
		}
		IFile targetTypeFile = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(page1.getContainerFullPath() + File.separator + typeName));
		try {
			ImportUtils.copyFile(template, targetTypeFile);
			String fileEnding = typeName.substring(typeName.lastIndexOf('.'), typeName.length());
			return (fileEnding.equals(".dtp")) ? finishDataTypeCreation(targetTypeFile)
					: finishFBTypeCreation(targetTypeFile);
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		return false;
	}

	private static boolean checkTemplateAvailable(String templatePath) {
		if (!new File(templatePath).exists()) {
			templateNotAvailable(templatePath);
			return false;
		}
		return true;
	}

	private static void templateNotAvailable(String templatePath) {
		MessageBox mbx = new MessageBox(Display.getDefault().getActiveShell());
		mbx.setMessage(MessageFormat.format(Messages.NewFBTypeWizard_TemplateNotAvailable, templatePath));
		mbx.open();
	}

	private boolean finishDataTypeCreation(IFile targetTypeFile) {
		StructuredType type = DataFactory.eINSTANCE.createStructuredType();
		TypeManagementPreferencesHelper.setupVersionInfo(type);
		type.setName(TypeLibrary.getTypeNameFromFile(targetTypeFile));
		DataTypeExporter exporter = new DataTypeExporter(type);
		try {
			exporter.saveType(targetTypeFile);
		} catch (XMLStreamException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		if (page1.getOpenType()) {
			openTypeEditor(targetTypeFile);
		}
		return true;
	}

	private boolean finishFBTypeCreation(IFile targetTypeFile) {
		entry = TypeLibrary.getPaletteEntryForFile(targetTypeFile);
		if (null == entry) {
			// refresh the palette and retry to fetch the entry
			TypeLibrary.refreshTypeLib(targetTypeFile);
			entry = TypeLibrary.getPaletteEntryForFile(targetTypeFile);
		}
		LibraryElement type = entry.getType();
		type.setName(TypeLibrary.getTypeNameFromFile(targetTypeFile));
		TypeManagementPreferencesHelper.setupIdentification(type);
		TypeManagementPreferencesHelper.setupVersionInfo(type);
		AbstractBlockTypeExporter.saveType(entry);
		entry.setType(type);
		if (page1.getOpenType()) {
			openTypeEditor(entry.getFile());
		}
		return true;
	}

	private static void openTypeEditor(IFile file) {
		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
		EditorUtils.openEditor(new FileEditorInput(file), desc.getId());
	}

	public PaletteEntry getPaletteEntry() {
		return entry;
	}
}
