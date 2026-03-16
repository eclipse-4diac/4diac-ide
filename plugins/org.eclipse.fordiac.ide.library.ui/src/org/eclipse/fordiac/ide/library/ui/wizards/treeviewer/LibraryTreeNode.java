/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.wizards.treeviewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.model.WorkbenchAdapter;

public final class LibraryTreeNode extends WorkbenchAdapter implements IAdaptable {

	private final Object value;
	private final String label;
	private final List<LibraryTreeNode> children = new ArrayList<>();
	private Object parent;

	public LibraryTreeNode(final Object value, final String label) {
		this.value = value;
		this.label = Objects.requireNonNullElse(label, ""); //$NON-NLS-1$
	}

	public Object getValue() {
		return value;
	}

	public void addChild(final LibraryTreeNode child) {
		if (child != null) {
			child.parent = this;
			children.add(child);
		}
	}

	public List<LibraryTreeNode> getChildren() {
		return List.copyOf(children);
	}

	@Override
	public Object[] getChildren(final Object o) {
		return children.toArray();
	}

	@Override
	public Object getParent(final Object o) {
		return parent;
	}

	@Override
	public String getLabel(final Object o) {
		return label;
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == IWorkbenchAdapter.class) {
			return adapter.cast(this);
		}
		return null;
	}

	public static Object unwrapNode(final Object element) {
		return element instanceof final LibraryTreeNode node ? node.getValue() : element;
	}
}
