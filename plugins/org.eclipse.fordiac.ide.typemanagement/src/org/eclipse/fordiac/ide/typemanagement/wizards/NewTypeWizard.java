/*******************************************************************************
 * Copyright (c) 2010 - 2021 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							 Johannes Kepler University Linz,
 *                           Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch
 *               - initial API and implementation and/or initial documentation
 *   Jose Cabral - Add preferences
 *   Alois Zoitl - moved openEditor helper function to EditorUtils
 *   Daniel Lindhuber, Bianca Wiesmayr, Ernst Blecha
 *               - extended for Data Type creation
 *   Alois Zoitl - reworked type loading to avoid race conditions with resource
 *                 change listener and type saving.
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.util.TypeFromTemplateCreator;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
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
	private TypeEntry entry;

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

		final File template = page1.getTemplate();
		if (!checkTemplateAvailable(template.getAbsolutePath())) {
			return false;
		}
		final IFile targetTypeFile = getTargetFile();
		final String packageName = page1.getPackageName();
		final TypeFromTemplateCreator creator = new TypeFromTemplateCreator(getTargetFile(), template, packageName);
		try {
			getContainer().run(false, true, creator::createTypeFromTemplate);
		} catch (final InvocationTargetException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		} catch (final InterruptedException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
		entry = creator.getTypeEntry();
		if (entry != null) {
			if (page1.getOpenType()) {
				openTypeEditor(targetTypeFile);
			}
			return true;
		}
		return false;
	}

	public static void openTypeEditor(final IFile file) {
		final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
		EditorUtils.openEditor(new FileEditorInput(file), desc.getId());
	}

	private IFile getTargetFile() {
		final String typeName = page1.getFileName();
		return ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(page1.getContainerFullPath() + File.separator + typeName));
	}

	private static boolean checkTemplateAvailable(final String templatePath) {
		if (!new File(templatePath).exists()) {
			templateNotAvailable(templatePath);
			return false;
		}
		return true;
	}

	private static void templateNotAvailable(final String templatePath) {
		final MessageBox mbx = new MessageBox(Display.getDefault().getActiveShell());
		mbx.setMessage(MessageFormat.format(Messages.NewFBTypeWizard_TemplateNotAvailable, templatePath));
		mbx.open();
	}

	public TypeEntry getTypeEntry() {
		return entry;
	}
}
