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
 *   Mathias Garstenauer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.typemanagement.refactoring.connection.commands.RepairBrokenConnectionCommand;
import org.eclipse.fordiac.ide.typemanagement.wizards.RepairBrokenConnectionWizardPage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * A Handler for reconnecting existing
 * {@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface
 * ErrorMarkerInterface} via a StructuredType Connection by using the
 * {@link org.eclipse.fordiac.ide.typemanagement.refactoring.connection.commands.RepairBrokenConnectionCommand
 * RepairBrokenConnectionCommand} It launches as Wizard to query the necessary
 * information from the user and executes the Command on the
 * {@link org.eclipse.gef.commands.CommandStack CommandStack}.
 */
public class RepairBrokenConnectionHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection sel = HandlerUtil.getCurrentSelection(event);
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final CommandStack commandStack = editor.getAdapter(CommandStack.class);
		if (sel instanceof final IStructuredSelection ssel && ssel.getFirstElement() instanceof final EditPart part
				&& part.getModel() instanceof final ErrorMarkerInterface errormarker) {
			final RepairBrokenConnectionWizardPage repairPage = new RepairBrokenConnectionWizardPage(
					errormarker.getFBNetworkElement().getTypeLibrary(), errormarker.getType());
			final Wizard wiz = new Wizard() {

				@Override
				public boolean performFinish() {
					return repairPage.getType() != null && repairPage.getVar() != null;
				}
			};
			wiz.addPage(repairPage);
			final WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().getActiveEditor().getSite().getShell(), wiz);
			dialog.create();

			if (dialog.open() == Window.OK) {
				final CompoundCommand repairCommands = new CompoundCommand();
				(errormarker.isIsInput() ? errormarker.getInputConnections() : errormarker.getOutputConnections())
						.forEach(con -> repairCommands.add(new RepairBrokenConnectionCommand(con,
								!errormarker.isIsInput(), repairPage.getType(), repairPage.getVar())));
				commandStack.execute(repairCommands);
			}

		}
		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		setBaseEnabled(isRepairable());
	}

	private static boolean isRepairable() {
		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			final IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
			return selection.size() == 1 && selection.getFirstElement() instanceof final EditPart part
					&& part.getModel() instanceof ErrorMarkerInterface;
		}
		return false;
	}
}
