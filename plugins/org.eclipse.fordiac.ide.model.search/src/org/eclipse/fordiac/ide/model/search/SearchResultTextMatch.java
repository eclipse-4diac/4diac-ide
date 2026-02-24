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
package org.eclipse.fordiac.ide.model.search;

import java.util.Objects;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class SearchResultTextMatch extends TextMatch {
	private final String elementKind;
	private final String elementName;
	private final String elementComment;

	public SearchResultTextMatch(final INamedElement obj, final int line, final int offset, final int length,
			final String type) {
		super(EcoreUtil.getURI(obj), line, offset, length, type);
		elementKind = obj.getClass().getSimpleName();
		elementName = obj.getName();
		elementComment = obj.getComment();
	}

	public String getElementKind() {
		return elementKind;
	}

	public String getElementName() {
		return elementName;
	}

	public String getElementComment() {
		return elementComment;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		final SearchResultTextMatch other = (SearchResultTextMatch) obj;
		return elementKind == other.elementKind && elementName == other.elementName
				&& elementComment == other.elementComment;
	}

	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hash(elementKind, elementName, elementComment);
	}
}
