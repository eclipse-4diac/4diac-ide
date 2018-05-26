/*******************************************************************************
 * Copyright (c) 2010 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.wizards;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.dataexport.CommonElementExporter;
import org.eclipse.fordiac.ide.model.dataimport.ImportUtils;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.Activator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class NewFBTypeWizard extends Wizard implements INewWizard {
	private IStructuredSelection selection;
	private NewFBTypeWizardPage page1;
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
	private PaletteEntry entry;

	public NewFBTypeWizard() {
		setWindowTitle("New Type");
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		this.selection = selection;
		setWindowTitle("New Type");
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
			return finishTypeCreation(targetTypeFile);
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
		mbx.setMessage("Template not available! (" + templatePath + ")");
		mbx.open();
	}

	private boolean finishTypeCreation(IFile targetTypeFile) {
		Palette palette = SystemManager.INSTANCE.getPalette(targetTypeFile.getProject());
		entry = TypeLibrary.getPaletteEntry(palette, targetTypeFile);
		if (null == entry) {
			// refresh the palette and retry to fetch the entry
			TypeLibrary.refreshPalette(palette);
			entry = TypeLibrary.getPaletteEntry(palette, targetTypeFile);
		}
		LibraryElement type = entry.getType();
		type.setName(TypeLibrary.getTypeNameFromFile(targetTypeFile));
		setupVersionInfo(type);
		CommonElementExporter.saveType(entry);
		entry.setType(type);
		if (page1.getOpenType()) {
			openTypeEditor(entry);
		}
		return true;
	}

	private void setupVersionInfo(LibraryElement type) {
		VersionInfo versionInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();
		versionInfo.setAuthor(System.getProperty("user.name")); //$NON-NLS-1$
		versionInfo.setDate(format.format(new Date(System.currentTimeMillis())));
		versionInfo.setVersion("1.0"); //$NON-NLS-1$
		if(type instanceof AdapterType) {
			type = ((AdapterType)type).getAdapterFBType();
		}		
		type.getVersionInfo().clear();
		type.getVersionInfo().add(versionInfo);
	}

	private static void openTypeEditor(PaletteEntry entry) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
				.getDefaultEditor(entry.getFile().getName());
		try {
			page.openEditor(new FileEditorInput(entry.getFile()), desc.getId());
		} catch (PartInitException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	public PaletteEntry getPaletteEntry() {
		return entry;
	}
}
