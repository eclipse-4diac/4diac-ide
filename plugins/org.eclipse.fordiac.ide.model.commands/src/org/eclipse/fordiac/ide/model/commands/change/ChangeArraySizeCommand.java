/*******************************************************************************
 * Copyright (c) 2012 - 2016 TU Wien ACIN, fortiss GmbH
 *               2023 Martin Erich Jobst
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
 *   Martin Jobst - add value validation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class ChangeArraySizeCommand extends AbstractChangeInterfaceElementCommand {
	private final String newArraySizeString;
	private int oldArraySize;
	private int newArraySize;

	public ChangeArraySizeCommand(final VarDeclaration variable, final String newArraySizeString) {
		super(variable);
		this.newArraySizeString = newArraySizeString;
	}

	@Override
	public boolean canExecute() {
		return (null != getInterfaceElement()) && (null != newArraySizeString);
	}

	@Override
	protected void doExecute() {
		final VarDeclaration variable = getInterfaceElement();
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
			} catch (final NumberFormatException nfe) {
				newArraySize = 0;
			}
		}
		if(newArraySize < 0) {
			newArraySize = 0;
		}
		setArraySize(newArraySize);
	}

	private void setArraySize(final int arraySize) {
		getInterfaceElement().setArraySize(arraySize);
	}

	@Override
	protected void doUndo() {
		setArraySize(oldArraySize);
	}

	@Override
	protected void doRedo() {
		setArraySize(newArraySize);
	}

	@Override
	public VarDeclaration getInterfaceElement() {
		return (VarDeclaration) super.getInterfaceElement();
	}
}
