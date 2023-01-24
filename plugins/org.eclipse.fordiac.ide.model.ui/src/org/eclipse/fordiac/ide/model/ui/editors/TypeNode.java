/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Germany GmbH,
 *                          Primetals Technologies Austria GmbH,
 *                          Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - extracted out of the DataTypeDropdown class
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.model.data.DataType;

public class TypeNode implements Comparable<TypeNode> {
	private final String name;
	final List<TypeNode> children;
	private TypeNode parent;
	private DataType type;

	TypeNode(final String name) {
		this.name = name;
		children = new ArrayList<>();
	}

	public boolean isDirectory() {
		return null == type;
	}

	TypeNode(final String name, final DataType type) {
		this.name = name;
		this.type = type;
		children = new ArrayList<>();
	}

	String getName() {
		return name;
	}

	List<TypeNode> getChildren() {
		return children;
	}

	// inserting in place
	void addChild(final TypeNode child) {
		final int index = Collections.binarySearch(children, child);
		if (index < 0) {
			children.add(-index - 1, child);
		}
	}

	TypeNode getParent() {
		return parent;
	}

	void setParent(final TypeNode parent) {
		this.parent = parent;
	}

	public DataType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (null == obj) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TypeNode other = (TypeNode) obj;
		if (null == name) {
			if (null != other.name) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(final TypeNode other) {
		// otherwise lower case would be after upper case names
		return this.name.toLowerCase().compareTo(other.getName().toLowerCase());
	}
}