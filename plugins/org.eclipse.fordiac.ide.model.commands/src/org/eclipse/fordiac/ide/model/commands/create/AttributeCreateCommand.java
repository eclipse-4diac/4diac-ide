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
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;

public class AttributeCreateCommand extends Command {
	private ConfigurableObject configurableObject;
	private Attribute attribute;

	public AttributeCreateCommand(ConfigurableObject configurableObject) {
		this.configurableObject = configurableObject;
	}

	@Override
	public boolean canExecute() {
		return null != configurableObject;
	}

	@Override
	public void execute() {
		attribute = LibraryElementFactory.eINSTANCE.createAttribute();
		attribute.setName("name"); //$NON-NLS-1$
		attribute.setComment("comment"); //$NON-NLS-1$
		attribute.setValue("value"); //$NON-NLS-1$
		redo();
	}

	@Override
	public void undo() {
		configurableObject.getAttributes().remove(attribute);
	}

	@Override
	public void redo() {
		configurableObject.getAttributes().add(attribute);
	}

}
