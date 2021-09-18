/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2021 Primetals Technologies Austria GmbH
 *               2021 Johannes Kepler University Linz
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
 *   Melanie Winter - addAttribute inserts after selection
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.gef.commands.Command;

public class AttributeCreateCommand extends Command implements CreationCommand {
	private final ConfigurableObject configurableObject;
	private Attribute attribute;
	private Attribute refElement;

	private final String name;
	private final String comment;
	private final String value;


	public AttributeCreateCommand(final ConfigurableObject configurableObject) {
		this(configurableObject, "name", "comment", "value"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public AttributeCreateCommand(final ConfigurableObject configurableObject, final Attribute refElement) {
		this(configurableObject, "name", "comment", "value"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		this.refElement = refElement;
	}

	public AttributeCreateCommand(final ConfigurableObject configurableObject, final String name, final String comment,
			final String value) {
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
		addAttribute();
	}

	@Override
	public void undo() {
		configurableObject.getAttributes().remove(attribute);
	}

	@Override
	public void redo() {
		addAttribute();
	}

	private void addAttribute() {
		if (null == refElement) {
			configurableObject.getAttributes().add(attribute);
		} else {
			final int index = configurableObject.getAttributes().indexOf(refElement) + 1;
			configurableObject.getAttributes().add(index, attribute);
		}
	}

	@Override
	public Object getCreatedElement() {
		return attribute;
	}
}
