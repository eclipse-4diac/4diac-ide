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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.typemanagement.refactoring.connection.ConnectionsToStructRefactoring;
import org.eclipse.fordiac.ide.typemanagement.wizards.ConnectionsToStructWizardPage;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This Handler activates the
 * {@link org.eclipse.fordiac.ide.typemanagement.refactoring.connection.ConnectionsToStructRefactoring
 * ConnectionsToStructRefactoring} by preprocessing the selection and opening
 * the according wizard.
 */
public class ConnectionsToStructHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		try {
			if (HandlerUtil.getCurrentSelection(event) instanceof final IStructuredSelection selection) {

				final List<Connection> connections = selection.stream().filter(EditPart.class::isInstance)
						.map(EditPart.class::cast).map(EditPart::getModel).filter(Connection.class::isInstance)
						.map(Connection.class::cast).toList();
				final FBType sourceType = connections.get(0).getSourceElement().getType();
				final FBType destinationType = connections.get(0).getDestinationElement().getType();
				final Map<String, String> replacableConMap = new HashMap<>();
				connections.stream().forEach(
						con -> replacableConMap.put(con.getSource().getName(), con.getDestination().getName()));

				final ConnectionsToStructRefactoring refactoring = new ConnectionsToStructRefactoring(sourceType,
						destinationType, replacableConMap);
				final RefactoringWizard wizard = new RefactoringWizard(refactoring,
						RefactoringWizard.DIALOG_BASED_USER_INTERFACE | RefactoringWizard.PREVIEW_EXPAND_FIRST_NODE) {
					@Override
					protected void addUserInputPages() {
						addPage(new ConnectionsToStructWizardPage());
					}
				};
				final RefactoringWizardOpenOperation openOperation = new RefactoringWizardOpenOperation(wizard);
				openOperation.run(HandlerUtil.getActiveShell(event), refactoring.getName());
			}
		} catch (final Exception e) {
			FordiacLogHelper.logError("Error during ReplaceStruct refactoring", e); //$NON-NLS-1$
		}
		return null;
	}
}
