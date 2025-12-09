/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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

package org.eclipse.fordiac.ide.model;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IVarElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SegmentType;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public enum AttributeInheritMode {
	IGNORE, COPY, INHERIT, COPY_INHERIT;

	public static void copyAttributeValuesFromType(final TypedConfigureableObject newElement) {
		if (newElement.getTypeEntry() == null
				|| !(newElement.getTypeEntry().getType() instanceof final ConfigurableObject typeElement)) {
			return;
		}
		AttributeInheritMode.copyAttributes(newElement, typeElement.getAttributes());

		if (newElement instanceof final BlockFBNetworkElement fbne && typeElement instanceof final FBType fbType) {
			fbType.getInterfaceList().getAllInterfaceElements().forEach(typeInterfaceElement -> {
				final var newInterface = fbne.getInterfaceElement(typeInterfaceElement.getName());
				AttributeInheritMode.copyAttributes(newInterface, typeInterfaceElement.getAttributes());
			});
		} else if (newElement instanceof final Device device && typeElement instanceof final DeviceType deviceType) {
			copyChildrenAttributeValues(device, deviceType.getVarDeclaration());
		} else if (newElement instanceof final Segment segment
				&& typeElement instanceof final SegmentType segmentType) {
			copyChildrenAttributeValues(segment, segmentType.getVarDeclaration());
		} else if (newElement instanceof final Resource resource
				&& typeElement instanceof final ResourceType resourceType) {
			copyChildrenAttributeValues(resource, resourceType.getVarDeclaration());
		}
	}

	private static void copyChildrenAttributeValues(final IVarElement newElement,
			final List<VarDeclaration> typeDeclarations) {
		typeDeclarations.forEach(typeVarDecl -> newElement.getVarDeclarations().stream()
				.filter(instanceDecl -> instanceDecl.getName().equals(typeVarDecl.getName())).findFirst()
				.ifPresent(newDecl -> AttributeInheritMode.copyAttributes(newDecl, typeVarDecl.getAttributes())));
	}

	private static void copyAttributes(final ConfigurableObject conf, final List<Attribute> attributes) {
		attributes.forEach(attribute -> {
			if (AttributeInheritMode.hasDeclarationWithInheritMode(attribute, Set.of(COPY, COPY_INHERIT))) {
				conf.setAttribute(attribute.getAttributeDeclaration(), attribute.getValue(), attribute.getComment());
			}
		});
	}

	public static List<Attribute> getInheritAttributes(final ConfigurableObject instance,
			final List<Attribute> typeAttributes) {
		final var instanceDeclarationList = instance.getAttributes().stream().map(Attribute::getAttributeDeclaration)
				.filter(Objects::nonNull).toList();
		return typeAttributes.stream()
				.filter(attribute -> AttributeInheritMode.hasDeclarationWithInheritMode(attribute,
						Set.of(INHERIT, COPY_INHERIT)))
				.filter(attribute -> !instanceDeclarationList.contains(attribute.getAttributeDeclaration())).toList();
	}

	private static boolean hasDeclarationWithInheritMode(final Attribute attribute,
			final Set<AttributeInheritMode> modes) {
		return attribute.getAttributeDeclaration() != null
				&& attribute.getAttributeDeclaration()
						.getAttribute(InternalAttributeDeclarations.INHERIT
								.getName()) instanceof final Attribute declarationsInheritAttibute
				&& modes.contains(AttributeInheritMode.valueOf(declarationsInheritAttibute.getValue()));
	}
}
