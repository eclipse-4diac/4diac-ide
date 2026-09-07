/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.model.commands.create;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OverrideAttribute;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

public class CreateOverrideAttributeCommand extends CreationCommand implements ScopedCommand {
	private final TypedSubApp typedSubApp;
	private OverrideAttribute attribute;

	private final String location;
	private final String name;
	private final String comment;
	private final DataType dataType;
	private final AttributeDeclaration attributeDecl;
	private final String value;

	private static final String DEFAULT_ATTRIBUTE_NAME = "Attribute1"; //$NON-NLS-1$

	public CreateOverrideAttributeCommand(final ConfigurableObject configurableObject, final TypedSubApp typedSubApp,
			final Attribute ref) {
		this.typedSubApp = Objects.requireNonNull(typedSubApp);
		this.location = ((INamedElement) configurableObject).getRelativeName(typedSubApp);

		final var existingNames = typedSubApp.getOverrideAttributes().stream()
				.filter(oa -> oa.getLocation().equals(this.location)).map(Attribute::getName)
				.collect(Collectors.toSet());

		if (ref == null) {
			this.name = NameRepository.getUniqueName(existingNames, DEFAULT_ATTRIBUTE_NAME);
			this.comment = ""; //$NON-NLS-1$
			this.dataType = ElementaryTypes.STRING;
			this.attributeDecl = null;
			this.value = ""; //$NON-NLS-1$
		} else {
			this.name = NameRepository.getUniqueName(existingNames, ref.getName());
			this.comment = ref.getComment();
			this.dataType = ref.getType();
			this.attributeDecl = ref.getAttributeDeclaration();
			this.value = ref.getValue();
		}
	}

	@Override
	public void execute() {
		attribute = LibraryElementFactory.eINSTANCE.createOverrideAttribute();
		attribute.setLocation(location);
		attribute.setName(name);
		attribute.setAttributeDeclaration(attributeDecl);
		attribute.setType(dataType);
		attribute.setValue(value);
		attribute.setComment(comment);
		typedSubApp.getOverrideAttributes().add(attribute);
	}

	@Override
	public void undo() {
		typedSubApp.getOverrideAttributes().remove(attribute);
	}

	@Override
	public void redo() {
		typedSubApp.getOverrideAttributes().add(attribute);
	}

	@Override
	public OverrideAttribute getCreatedElement() {
		return attribute;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(typedSubApp);
	}
}
