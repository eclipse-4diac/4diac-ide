/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *                    Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - extracted out of the DataTypeDropdown class
 *   Martin Erich Jobst - refactored type proposals
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.nat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class TypeNode {
	private final String name;
	private final String fullName;
	private final List<TypeNode> children = new ArrayList<>();
	private TypeNode parent;
	private final Object type;

	public TypeNode(final String name) {
		this(name, name, null);
	}

	public TypeNode(final LibraryElement libraryElement) {
		this(libraryElement.getName(), PackageNameHelper.getFullTypeName(libraryElement), libraryElement);
	}

	public TypeNode(final TypeEntry typeEntry) {
		this(typeEntry.getTypeName(), typeEntry.getFullTypeName(), typeEntry);
	}

	public TypeNode(final String name, final String fullName, final Object type) {
		this.name = name;
		this.fullName = fullName;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		return fullName;
	}

	public String getPackageName() {
		return PackageNameHelper.extractPackageName(fullName);
	}

	public List<TypeNode> getChildren() {
		return children;
	}

	public TypeNode getParent() {
		return parent;
	}

	public LibraryElement getType() {
		if (type instanceof final LibraryElement libraryElement) {
			return libraryElement;
		}
		if (type instanceof final TypeEntry typeEntry) {
			return typeEntry.getType();
		}
		return null;
	}

	public TypeEntry getTypeEntry() {
		if (type instanceof final TypeEntry typeEntry) {
			return typeEntry;
		}
		if (type instanceof final LibraryElement libraryElement) {
			return libraryElement.getTypeEntry();
		}

		return null;
	}

	public boolean isDirectory() {
		return null == type;
	}

	public void addChild(final TypeNode child) {
		children.add(child);
		child.parent = this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fullName);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TypeNode other = (TypeNode) obj;
		return Objects.equals(fullName, other.fullName);
	}

	public void sortChildren() {
		children.sort(Comparator.comparing(TypeNode::getName, String.CASE_INSENSITIVE_ORDER)
				.thenComparing(TypeNode::getFullName));
		children.forEach(TypeNode::sortChildren);
	}
}
