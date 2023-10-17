/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;

public class ChangeAttributeValueCommand extends AbstractChangeAttributeCommand {
	private final String newValue;
	private String oldValue;

	public ChangeAttributeValueCommand(final Attribute attribute, final String value) {
		super(attribute);
		newValue = (null == value) ? "" : value; //$NON-NLS-1$ //always ensure a valid value
	}

	@Override
	protected void doExecute() {
		oldValue = getAttribute().getValue();
		getAttribute().setValue(newValue);
	}

	@Override
	protected void doUndo() {
		getAttribute().setValue(oldValue);
	}

	@Override
	protected void doRedo() {
		getAttribute().setValue(newValue);
	}
}
