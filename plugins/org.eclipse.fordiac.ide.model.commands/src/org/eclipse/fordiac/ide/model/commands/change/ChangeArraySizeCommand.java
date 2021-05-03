/*******************************************************************************
 * Copyright (c) 2012 - 2016 TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger 
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class ChangeArraySizeCommand extends Command {
	private VarDeclaration variable;
	private int oldArraySize;
	private int newArraySize;
	private String newArraySizeString;

	public ChangeArraySizeCommand(final VarDeclaration variable, final String newArraySizeString) {
		super();
		this.variable = variable;
		this.newArraySizeString = newArraySizeString;
	}

	@Override
	public boolean canExecute() {
		return (null != variable) && (null != newArraySizeString);
	}

	@Override
	public void execute() {
		if (variable.isArray()) {
			oldArraySize = variable.getArraySize();
		} else {
			oldArraySize = 0;
		}
		if (newArraySizeString.length() == 0) {
			newArraySize = 0;
		} else if (newArraySizeString.length() > 0) {
			try {
				newArraySize = Integer.parseInt(newArraySizeString);
			} catch (NumberFormatException nfe) {
				newArraySize = 0;
			}
		}
		if(newArraySize < 0) {
			newArraySize = 0;
		}
		setArraySize(newArraySize);
	}

	private void setArraySize(int arraySize) {
		variable.setArraySize(arraySize);
	}

	@Override
	public void undo() {
		setArraySize(oldArraySize);
	}

	@Override
	public void redo() {
		setArraySize(newArraySize);
	}
}
