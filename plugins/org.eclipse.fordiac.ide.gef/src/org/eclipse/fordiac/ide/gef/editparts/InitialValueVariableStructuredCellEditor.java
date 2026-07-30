/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import java.util.Optional;

import org.eclipse.fordiac.ide.gef.dialogs.InitialValueVariableDialog;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class InitialValueVariableStructuredCellEditor extends InitialValueStructuredCellEditor {

	public InitialValueVariableStructuredCellEditor(final Composite parent, final VarDeclaration varDeclaration) {
		super(parent, varDeclaration);
	}

	public InitialValueVariableStructuredCellEditor(final Composite parent, final VarDeclaration varDeclaration,
			final int style) {
		super(parent, varDeclaration, style);
	}

	@Override
	protected Optional<String> openVariableDialog(final Shell shell, final String initialValue) {
		return InitialValueVariableDialog.open(shell, getVarDeclaration(), initialValue);
	}

}
