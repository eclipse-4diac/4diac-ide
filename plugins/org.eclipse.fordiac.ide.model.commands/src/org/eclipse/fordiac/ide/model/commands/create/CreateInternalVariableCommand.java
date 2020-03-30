/*******************************************************************************
 * Copyright (c) 2012, 2015 TU Wien ACIN, fortiss GmbH
 *               2019 - 2020 Johannes Kepler University Linz
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
 *   Bianca Wiesmayr
 *     - command now returns newly created elements, improve insertion
 *     - extracted reusable code to CreateInternalVariablesCommand
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.internal.CreateVariableCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * The Class CreateInternalVariableCommand.
 *
 * This command is used to create an internal variable for a BasicFBType.
 */
public class CreateInternalVariableCommand extends CreateVariableCommand {

	/**
	 * Instantiates command that creates a new internal variable.
	 *
	 * @param dataType the data type
	 * @param fbType   the fb type
	 */
	public CreateInternalVariableCommand(final BasicFBType fbType) {
		super(fbType, fbType.getInternalVars().size() - 1, null, null);
	}

	public CreateInternalVariableCommand(final BasicFBType fbType, int index, String name, DataType dataType) {
		super(fbType, index, name, dataType);
	}

	@Override
	protected EList<VarDeclaration> getVariableList() {
		return ((BaseFBType) getType()).getInternalVars();
	}

	@Override
	public String getDefaultVarName() {
		return "INTERNALVAR1"; //$NON-NLS-1$
	}

}
