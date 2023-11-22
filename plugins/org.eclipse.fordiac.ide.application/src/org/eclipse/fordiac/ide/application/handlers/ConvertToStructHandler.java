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
 *   Lukas Wais
 *     - Adaption for new SaveAsStructWizard
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.InterfaceEditPartForFBNetwork;
import org.eclipse.fordiac.ide.application.editparts.SubAppInternalInterfaceEditPart;
import org.eclipse.fordiac.ide.application.wizards.ExtractStructTypeWizard;
import org.eclipse.fordiac.ide.model.commands.create.CreateStructFromInterfaceElementsCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConvertToStructHandler extends AbstractHandler {

	private ExtractStructTypeWizard wizard;
	private IProject project;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection sel = HandlerUtil.getCurrentStructuredSelection(event);
		final List<VarDeclaration> varDecls = collectSelectedVarDecls(sel);
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final CommandStack commandStack = editor.getAdapter(CommandStack.class);
		final FBNetworkElement fb = getNetworkElementFromSelectedPins(sel);

		if ((null != fb) && (null != commandStack) && !varDecls.isEmpty()) {
			project = getProject(fb);
			// open wizard to save in struct
			invokeSaveWizard(varDecls, editor);

			if (wizard.replaceSource()) {
				final DataType datatype = TypeLibraryManager.INSTANCE.getTypeLibrary(project).getDataTypeLibrary()
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

	private static IProject getProject(final FBNetworkElement fb) {
		final EObject root = EcoreUtil.getRootContainer(fb);
		if (root instanceof final LibraryElement libEl) {
			return libEl.getTypeLibrary().getProject();
		}
		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection sel = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(!collectSelectedVarDecls(sel).isEmpty());
	}

	private void invokeSaveWizard(final List<VarDeclaration> varDecls, final IEditorPart editor) {
		wizard = new ExtractStructTypeWizard(varDecls, project, Messages.ConvertToStructHandler_Title);
		final WizardDialog dialog = new WizardDialog(editor.getSite().getShell(), wizard);
		dialog.create();
		dialog.open();
	}

	private static void showInformationDialog(final IEditorPart editor) {
		MessageDialog.openError(editor.getSite().getShell(), Messages.ConvertToStructHandler_OperationNotPossible,
				Messages.ConvertToStructHandler_NotAllowedReasons);
	}

	private static List<VarDeclaration> collectSelectedVarDecls(final ISelection sel) {
		if (sel instanceof final StructuredSelection structSel) {
			return ((List<?>) structSel.toList()).stream().filter(EditPart.class::isInstance).map(EditPart.class::cast)
					.map(EditPart::getModel).filter(VarDeclaration.class::isInstance).map(VarDeclaration.class::cast)
					.toList();
		}
		return Collections.emptyList();
	}

	private static FBNetworkElement getNetworkElementFromSelectedPins(final IStructuredSelection selectedPins) {
		for (final Object pin : selectedPins) {
			if (pin instanceof final SubAppInternalInterfaceEditPart ep && ep.getModel() instanceof VarDeclaration) {
				return ep.getModel().getFBNetworkElement();
			}
			if (pin instanceof final InterfaceEditPartForFBNetwork ep && ep.getModel() instanceof VarDeclaration) {
				return (FBNetworkElement) ep.getParent().getModel();
			}
		}
		return null;
	}

}
