/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University
 *               2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Martin Jobst - refactored additional commands
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class ChangeSubAppIETypeCommand extends ChangeDataTypeCommand {
	public ChangeSubAppIETypeCommand(final VarDeclaration interfaceElement, final DataType dataType) {
		super(interfaceElement, dataType);

		if (interfaceElement.getFBNetworkElement().isMapped()) {
			getAdditionalCommands().add(new ChangeDataTypeCommand(interfaceElement.getFBNetworkElement().getOpposite()
					.getInterfaceElement(interfaceElement.getName()), dataType));
		}
	}
}
