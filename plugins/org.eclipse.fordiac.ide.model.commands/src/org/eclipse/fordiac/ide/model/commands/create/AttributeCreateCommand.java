/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2021 Primetals Technologies Austria GmbH
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
 *   Daniel Lindhuber, Bianca Wiesmayr
 *     - extended to create specific attribute
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;

public class AttributeCreateCommand extends Command {
	private final ConfigurableObject configurableObject;
	private Attribute attribute;

	private final String name;
	private final String comment;
	private final String value;

	public AttributeCreateCommand(ConfigurableObject configurableObject) {
		this(configurableObject, "name", "comment", "value"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public AttributeCreateCommand(ConfigurableObject configurableObject, String name, String comment, String value) {
		this.configurableObject = configurableObject;
		this.name = name;
		this.comment = comment;
		this.value = value;
	}

	@Override
	public boolean canExecute() {
		return null != configurableObject;
	}

	@Override
	public void execute() {
		attribute = LibraryElementFactory.eINSTANCE.createAttribute();
		attribute.setName(name);
		attribute.setComment(comment);
		attribute.setValue(value);
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
