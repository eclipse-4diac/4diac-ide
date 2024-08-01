/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.wizards;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.PlatformUI;

public abstract class AbstractMissingTypeWizard extends Wizard {

	public enum Choices {
		CREATE_MISSING_TYPE(), CHANGE_TYPE(), DELETE_AFFECTED_ELEMENTS();
	}

	private AbstractRepairOperationPage page;
	protected final EObject errorType;
	protected EObject result;

	protected AbstractMissingTypeWizard(final EObject errorType) {
		this.errorType = errorType;
	}

	protected abstract AbstractRepairOperationPage createPage();

	protected abstract void handleChangeType();

	protected abstract void handleCreateMissing();

	public void openWizard() {
		page = createPage();

		final WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor().getSite().getShell(), this);

		this.addPage(page);
		dialog.create();
		dialog.open();
	}

	public static TypeLibrary getTypeLibrary(final EObject eObj) {
		if (EcoreUtil.getRootContainer(eObj) instanceof final LibraryElement le) {
			return le.getTypeLibrary();
		}
		return null;
	}

	@Override
	public boolean performFinish() {
		final Choices choice = page.getSelection();
		switch (choice) {
		case CHANGE_TYPE:
			handleChangeType();
			break;
		case CREATE_MISSING_TYPE:
			handleCreateMissing();
			break;
		default:
			break;
		}
		return true;
	}

	protected abstract class AbstractRepairOperationPage extends WizardPage {
		protected Choices choice;

		protected AbstractRepairOperationPage(final String pageName) {
			super(pageName);
			this.choice = Choices.CHANGE_TYPE;
		}

		public Choices getSelection() {
			return choice;
		}
	}
}
