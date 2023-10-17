/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.gef.commands.Command;

public class DeleteAttributeCommand extends Command {
	private ConfigurableObject configurableObject;
	private Attribute attribute;

	public DeleteAttributeCommand(ConfigurableObject configurableObject, Attribute attribute) {
		this.configurableObject = configurableObject;
		this.attribute = attribute;
	}

	@Override
	public boolean canExecute() {
		return null != configurableObject && null != attribute;
	}

	@Override
	public void execute() {
		redo();
	}

	@Override
	public void undo() {
		configurableObject.getAttributes().add(attribute);
	}

	@Override
	public void redo() {
		configurableObject.getAttributes().remove(attribute);
	}

}
