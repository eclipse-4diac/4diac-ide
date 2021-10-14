/*******************************************************************************
 * Copyright (c) 2014, 2020 fortiss GmbH, Johannes Kepler University,
 *                          Primetals Technologies Austria GmbH
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
 *  Lukas Wais   - Adaption to work with new SaveAsSubappWizard
 *  Lukas Wais	 - Removed old code and adaption for the improved SaveAsSubappWizard
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.wizards.SaveAsSubappWizard;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class SaveAsSubappTypeHandler extends AbstractHandler {
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		// we check in the enablement that it is a structured selection therefore we can
		// easily cast here
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);

		for (final Object selected : selection.toList()) {
			final EditPart ep = (EditPart) selected;
			final SubApp subApp = (SubApp) ep.getModel();
			invokeSaveWizard(subApp, editor);
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(calculateEnabled(selection));
	}

	private static boolean calculateEnabled(final ISelection selection) {
		if ((selection instanceof IStructuredSelection) && !selection.isEmpty()) {
			for (final Object selected : ((IStructuredSelection) selection).toList()) {
				if ((selected instanceof EditPart) && (((EditPart) selected).getModel() instanceof SubApp)) {
					if (null != ((SubApp) ((EditPart) selected).getModel()).getPaletteEntry()) {
						// a typed subapplication has been selected
						return false;
					}
				} else {
					// a non subapplication has been selected
					return false;
				}
			}
			// we only have subapps selected
			return true;
		}
		return false;
	}

	private static void invokeSaveWizard(final SubApp subApp, final IEditorPart editor) {
		final SaveAsSubappWizard wizard = new SaveAsSubappWizard(subApp,
				Messages.SaveAsSubApplicationTypeAction_WizardPageName);

		final WizardDialog dialog = new WizardDialog(editor.getSite().getShell(), wizard);
		dialog.create();
		dialog.open();
	}
}
