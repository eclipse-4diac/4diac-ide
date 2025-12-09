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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;

public class ChangeDirectlyDerivedInitialValueCommand extends AbstractChangeDirectlyDerivedTypeCommand {
	private final String newValue;
	private String oldValue;

	private ChangeDirectlyDerivedInitialValueCommand(final DirectlyDerivedType directlyDerivedType,
			final String newInitalValue) {
		super(directlyDerivedType);
		newValue = (null == newInitalValue) ? "" : newInitalValue; //$NON-NLS-1$ //always ensure a valid value
	}

	public static ChangeDirectlyDerivedInitialValueCommand forName(final DirectlyDerivedType directlyDerivedType,
			final String newInitalValue) {
		return new ChangeDirectlyDerivedInitialValueCommand(directlyDerivedType, newInitalValue);
	}

	@Override
	public void doExecute() {
		oldValue = getDirectlyDerivedType().getInitialValue();
		getDirectlyDerivedType().setInitialValue(newValue);
	}

	@Override
	public void doUndo() {
		getDirectlyDerivedType().setInitialValue(oldValue);
	}

	@Override
	public void doRedo() {
		getDirectlyDerivedType().setInitialValue(newValue);
	}
}
