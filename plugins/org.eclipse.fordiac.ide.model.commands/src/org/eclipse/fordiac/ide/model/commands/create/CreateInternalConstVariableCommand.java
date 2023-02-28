/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.internal.CreateVariableCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/** The Class CreateInternalConstVariableCommand.
 *
 * This command is used to create an internal constant variable for a BasicFBType. */
public class CreateInternalConstVariableCommand extends CreateVariableCommand {

	/** Instantiates command that creates a new internal constant variable.
	 *
	 * @param dataType the data type
	 * @param fbType   the fb type */
	public CreateInternalConstVariableCommand(final BaseFBType fbType) {
		super(fbType, fbType.getInternalConstVars().size(), null, null);
	}

	public CreateInternalConstVariableCommand(final BaseFBType fbType, final int index, final String name, final DataType dataType) {
		super(fbType, index, name, dataType);
	}

	@Override
	protected EList<VarDeclaration> getVariableList() {
		return ((BaseFBType) getType()).getInternalConstVars();
	}

	@Override
	public String getDefaultVarName() {
		return "INTERNALCONSTVAR1"; //$NON-NLS-1$
	}

}
