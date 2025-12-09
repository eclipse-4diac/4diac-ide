/*******************************************************************************
 * Copyright (c) 2014, 2023 fortiss GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *     - introduce common superclass
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.actions;

import java.io.FileFilter;

import org.eclipse.fordiac.ide.fbtypeeditor.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typemanagement.wizards.NewFBTypeWizardPage;
import org.eclipse.fordiac.ide.typemanagement.wizards.NewTypeWizard;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchPart;

public abstract class CreateFromNewAdapterAction extends CreateInterfaceElementAction {
	private TypeEntry typeEntry;

	CreateFromNewAdapterAction(final IWorkbenchPart part, final FBType fbType) {
		super(part, fbType);
		setText(Messages.CreateFromNewAdapterAction_NewAdapter);
	}

	@Override
	public void run() {
		final NewTypeWizard wizard = new NewTypeWizard() {
			@Override
			protected NewFBTypeWizardPage createNewFBTypeWizardPage() {
				return new NewFBTypeWizardPage(getSelection()) {
					@Override
					protected FileFilter createTemplatesFileFilter() {
						// only show adapter templates in template selection box
						return pathname -> pathname.getName().toUpperCase().endsWith(".ADP"); //$NON-NLS-1$
					}
				};
			}
		};

		final WizardDialog dialog = new WizardDialog(getWorkbenchPart().getSite().getShell(), wizard);
		dialog.create();

		if (Window.OK == dialog.open()) {
			// the type could be created new execute the command
			final TypeEntry entry = wizard.getTypeEntry();
			if (entry instanceof AdapterTypeEntry) {
				execute(getCreationCommand((AdapterTypeEntry) entry));
			}
		}
	}

	private IStructuredSelection getSelection() {
		if (null != typeEntry) {
			// if we have a TypeEntry we will use this to mark the place in the
			// typelibrary to indicate the user where he might add the adapter
			return new StructuredSelection(typeEntry.getFile().getParent());
		}
		return new StructuredSelection();
	}

	protected abstract Command getCreationCommand(AdapterTypeEntry adpEntry);

	public void setTypeEntry(final TypeEntry typeEntry) {
		this.typeEntry = typeEntry;
	}

}
