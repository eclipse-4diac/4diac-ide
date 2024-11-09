/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.provider;

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;

final class AttributeItemProviderAnnotation {

	static String getText(final Attribute attribute, final AttributeItemProvider provider) {
		String label = attribute.getName();

		if (label == null || label.isBlank()) {
			return provider.getString("_UI_Attribute_type"); //$NON-NLS-1$
		}

		if (attribute.getAttributeDeclaration() != null
				&& attribute.getAttributeDeclaration().getTypeEntry().getPackageName() != null
				&& !attribute.getAttributeDeclaration().getTypeEntry().getPackageName().isBlank()) {
			label += " [" + attribute.getAttributeDeclaration().getTypeEntry().getPackageName() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
		}

		if (attribute.getType() != null && attribute.getAttributeDeclaration() == null) {
			label += " : " + attribute.getType().getQualifiedName(); //$NON-NLS-1$
		}

		if (attribute.getValue() != null && !attribute.getValue().isBlank()) {
			label += " := " + attribute.getValue(); //$NON-NLS-1$
		}

		return label;
	}

	private AttributeItemProviderAnnotation() {
		throw new UnsupportedOperationException("Helper class shall not be instantiated!"); //$NON-NLS-1$
	}

}
