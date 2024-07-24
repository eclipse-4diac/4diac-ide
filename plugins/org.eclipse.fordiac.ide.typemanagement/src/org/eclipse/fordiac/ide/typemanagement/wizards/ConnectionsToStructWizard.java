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
package org.eclipse.fordiac.ide.typemanagement.wizards;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.typemanagement.refactoring.connection.ConnectionsToStructRefactoring;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

public class ConnectionsToStructWizard extends RefactoringWizard {
	private ConnectionsToStructWizardPage choosePage;
	private DataType createdType;
	private String sourceName;
	private String destinationName;
	private boolean conflictResolution;
	private final IStructuredSelection selection;

	public ConnectionsToStructWizard(final IStructuredSelection selection,
			final ConnectionsToStructRefactoring refactoring) {
		super(refactoring, DIALOG_BASED_USER_INTERFACE | PREVIEW_EXPAND_FIRST_NODE);
		this.selection = selection;
		setWindowTitle("Connections to Struct");
	}

	@Override
	protected void addUserInputPages() {
		choosePage = new ConnectionsToStructWizardPage("Connections to Struct", selection);
		addPage(choosePage);
	}
}