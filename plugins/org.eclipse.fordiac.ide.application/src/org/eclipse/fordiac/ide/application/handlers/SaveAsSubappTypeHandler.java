/*******************************************************************************
 * Copyright (c) 2014, 2016 fortiss GmbH
 * 				 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   			 - migrated the save as subapp type action to a handler and added
 *                 question dialog if the origin fb should be replaced
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.wizards.SaveAsSubappWizard;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class SaveAsSubappTypeHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		FBNetworkEditor editor = (FBNetworkEditor) HandlerUtil.getActiveEditor(event);
		// we check in the enablement that it is a structured selection therefore we can
		// easily cast here
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);

		for (Object selected : selection.toList()) {
			EditPart ep = (EditPart) selected;
			SubApp subApp = (SubApp) ep.getModel();

			if (!checkContainedSubApps(subApp)) {
				showInformationDialog(editor.getSite().getShell());
			} else {
				invokeSaveWizard(subApp, editor);
			}
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(calculateEnabled(selection));
	}

	private static boolean calculateEnabled(ISelection selection) {
		if (!selection.isEmpty() && (selection instanceof IStructuredSelection)) {
			for (Object selected : ((IStructuredSelection) selection).toList()) {
				if (selected instanceof EditPart) {
					if (((EditPart) selected).getModel() instanceof SubApp) {
						if (null != ((SubApp) ((EditPart) selected).getModel()).getPaletteEntry()) {
							// a typed subapplication has been selected
							return false;
						}
					} else {
						// a non subapplication has been selected
						return false;
					}
				} else {
					return false;
				}
			}
			// we only have subapps selected
			return true;
		}
		return false;
	}

	private static void showInformationDialog(Shell shell) {
		MessageDialog.openError(shell, Messages.SaveAsSubApplicationTypeAction_UntypedSubappError,
				Messages.SaveAsSubApplicationTypeAction_UntypedSubappErrorDescription);
	}

	private static void invokeSaveWizard(SubApp subApp, FBNetworkEditor editor) {
		SaveAsSubappWizard wizard = new SaveAsSubappWizard(subApp);

		WizardDialog dialog = new WizardDialog(editor.getSite().getShell(), wizard);
		dialog.create();
		dialog.open();
	}

	private static boolean checkContainedSubApps(SubApp subApp) {
		for (FBNetworkElement element : subApp.getSubAppNetwork().getNetworkElements()) {
			if ((element instanceof SubApp) && (null == element.getPaletteEntry())) {
				// we have an untyped subapplication
				return false;
			}
		}
		return true;
	}

}
