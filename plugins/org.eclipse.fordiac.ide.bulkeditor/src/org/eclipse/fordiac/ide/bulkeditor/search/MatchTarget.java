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
package org.eclipse.fordiac.ide.bulkeditor.search;

import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;

public record MatchTarget(String name, String type, String comment, String value, List<Attribute> attributes) {

	private static final List<Attribute> NO_ATTRIBUTES = Collections.emptyList();

	public static MatchTarget of(final String name, final String type, final String comment, final String value,
			final ConfigurableObject owner) {
		return new MatchTarget(name, type, comment, value, owner != null ? owner.getAttributes() : NO_ATTRIBUTES);
	}

	public static MatchTarget ofAttribute(final Attribute attribute) {
		return new MatchTarget(attribute.getName(), attribute.getTypeName(), attribute.getComment(),
				InitialValueHelper.getInitialOrDefaultValue(attribute), NO_ATTRIBUTES);
	}
}
