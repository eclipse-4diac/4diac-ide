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

import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;

public enum AttributeInheritMode {
	IGNORE, COPY, INHERIT, COPY_INHERIT;

	public static void copyAttributes(final ConfigurableObject conf, final List<Attribute> attributes) {
		attributes.forEach(attribute -> {
			if (AttributeInheritMode.hasDeclarationWithInheritMode(attribute, COPY)) {
				conf.setAttribute(attribute.getAttributeDeclaration(), attribute.getValue(), attribute.getComment());
			}
		});
	}

	public static boolean hasDeclarationWithInheritMode(final Attribute attribute, final AttributeInheritMode mode) {
		return attribute.getAttributeDeclaration() != null
				&& attribute.getAttributeDeclaration()
						.getAttribute(InternalAttributeDeclarations.INHERIT
								.getName()) instanceof final Attribute declarationsInheritAttibute
				&& (AttributeInheritMode.valueOf(declarationsInheritAttibute.getValue()) == COPY_INHERIT
						|| AttributeInheritMode.valueOf(declarationsInheritAttibute.getValue()) == mode);
	}
}
