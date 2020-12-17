/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alexander Lumplecker
 *     - created stub class
 *   Bianca Wiesmayr, Lukas Wais, Virendra Ashiwal
 *     - initial implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.InterfaceEditPartForFBNetwork;
import org.eclipse.fordiac.ide.application.wizards.SaveAsStructWizard;
import org.eclipse.fordiac.ide.model.commands.create.CreateStructFromInterfaceElementsCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConvertToStructHandler extends AbstractHandler {

	private SaveAsStructWizard wizard;
	private IProject project;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection sel = HandlerUtil.getCurrentStructuredSelection(event);
		final List<VarDeclaration> varDecls = collectSelectedVarDecls(sel);
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final CommandStack commandStack = HandlerUtil.getActiveEditor(event).getAdapter(CommandStack.class);
		final FBNetworkElement fb = getNetworkElement(sel);
		if ((null != fb) && (null != commandStack) && !varDecls.isEmpty()) {
			project = fb.getFbNetwork().getAutomationSystem().getSystemFile().getProject();


			// open wizard to save in struct
			invokeSaveWizard(varDecls, editor);

			if (wizard.replaceSource()) {
				final DataType datatype = TypeLibrary.getTypeLibrary(project).getDataTypeLibrary()
						.getType(wizard.getDatatypeName());
				// (potentially) replace old elements with new ones
				final Command cmd = new CreateStructFromInterfaceElementsCommand(varDecls, datatype);
				if (cmd.canExecute()) {
					commandStack.execute(cmd);
				} else {
					showInformationDialog(editor);
				}
			}
		}

		return Status.OK_STATUS;
	}

	private void invokeSaveWizard(final List<VarDeclaration> varDecls, final IEditorPart editor) {
		wizard = new SaveAsStructWizard(varDecls, project);

		final WizardDialog dialog = new WizardDialog(editor.getSite().getShell(), wizard);
		dialog.create();
		dialog.open();
	}

	private static void showInformationDialog(final IEditorPart editor) {
		MessageDialog.openError(editor.getSite().getShell(), Messages.ConvertToStructHandler_OperationNotPossible,
				Messages.ConvertToStructHandler_NotAllowedReasons);
	}

	@SuppressWarnings("unchecked")
	private static List<VarDeclaration> collectSelectedVarDecls(final IStructuredSelection sel) {
		return (List<VarDeclaration>) sel.toList().stream().filter(ep -> ep instanceof InterfaceEditPartForFBNetwork)
				.map(ep -> ((EditPart) ep).getModel()).filter(el -> el instanceof VarDeclaration)
				.collect(Collectors.toList());
	}

	private static FBNetworkElement getNetworkElement(final IStructuredSelection selection) {
		for (final Object o : selection) {
			if (o instanceof InterfaceEditPartForFBNetwork) {
				final InterfaceEditPartForFBNetwork ep = (InterfaceEditPartForFBNetwork) o;
				if (ep.getModel() instanceof VarDeclaration) {
					return (FBNetworkElement) ep.getParent().getModel();
				}
			}
		}
		return null;
	}

}
