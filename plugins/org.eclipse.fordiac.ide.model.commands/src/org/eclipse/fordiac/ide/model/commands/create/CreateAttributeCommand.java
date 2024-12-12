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

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

public class CreateAttributeCommand extends CreationCommand implements ScopedCommand {
	private final ConfigurableObject configurableObject;
	private Attribute attribute;

	private final String name;
	private final String comment;
	private final DataType dataType;
	private final AttributeDeclaration attributeDecl;
	private final String value;
	private final int index;

	static final String DEFAULT_ATTRIBUTE_NAME = "Attribute1"; //$NON-NLS-1$

	private CreateAttributeCommand(final ConfigurableObject configurableObject, final String name, final String comment,
			final DataType dataType, final AttributeDeclaration attributeDecl, final String value, final int index) {
		this.configurableObject = Objects.requireNonNull(configurableObject);
		this.name = name;
		this.comment = comment;
		this.dataType = dataType;
		this.attributeDecl = attributeDecl;
		this.value = value;
		this.index = index;
	}

	public static CreateAttributeCommand withDefaults(final ConfigurableObject configurableObject) {
		return CreateAttributeCommand.forTemplate(configurableObject, null, -1);
	}

	public static CreateAttributeCommand forTemplate(final ConfigurableObject configurableObject,
			final Attribute template, final int index) {
		if (template != null) {
			return CreateAttributeCommand.forValues(configurableObject, template.getName(), template.getComment(),
					template.getType(), template.getAttributeDeclaration(), template.getValue(), index);
		}
		return CreateAttributeCommand.forValues(configurableObject, DEFAULT_ATTRIBUTE_NAME, "", ElementaryTypes.STRING, //$NON-NLS-1$
				null, "", index);
	}

	public static CreateAttributeCommand forValues(final ConfigurableObject configurableObject, final String name,
			final String comment, final DataType dataType, final AttributeDeclaration attributeDecl, final String value,
			final int index) {
		return new CreateAttributeCommand(configurableObject, name, comment, dataType, attributeDecl, value,
				index >= 0 && index <= configurableObject.getAttributes().size() ? index
						: configurableObject.getAttributes().size());
	}

	@Override
	public void execute() {
		attribute = LibraryElementFactory.eINSTANCE.createAttribute();
		attribute.setName(name);
		attribute.setComment(comment);
		attribute.setType(dataType);
		attribute.setAttributeDeclaration(attributeDecl);
		attribute.setValue(value);
		configurableObject.getAttributes().add(index, attribute);
		if (attributeDecl == null) {
			attribute.setName(NameRepository.createUniqueName(attribute, name));
		}
	}

	@Override
	public void undo() {
		configurableObject.getAttributes().remove(attribute);
	}

	@Override
	public void redo() {
		configurableObject.getAttributes().add(index, attribute);
	}

	@Override
	public Object getCreatedElement() {
		return attribute;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(configurableObject);
	}
}
