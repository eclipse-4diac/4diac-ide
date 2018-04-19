/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.gef.commands.Command;

public class AttributeDeleteCommand extends Command {
	private ConfigurableObject configurableObject;
	private Attribute attribute;
	
	public AttributeDeleteCommand(ConfigurableObject configurableObject, Attribute attribute) {
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
