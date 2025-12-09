/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model;

import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class StructTreeContentProvider implements ITreeContentProvider {

	private AbstractStructTreeNode root = null;

	public void setRoot(final AbstractStructTreeNode root) {
		this.root = root;
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof StructManipulator) {
			return getMemberVariableNodes();
		}
		return new Object[] {};
	}

	private Object[] getMemberVariableNodes() {
		return root != null ? root.getChildrenAsArray() : new Object[] {};
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof CheckableStructTreeNode) {
			return ((AbstractStructTreeNode) parentElement).getChildrenAsArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof CheckableStructTreeNode) {
			return ((AbstractStructTreeNode) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof final AbstractStructTreeNode abstractstructtreenode) {
			return abstractstructtreenode.hasChildren();
		}
		return false;
	}
}
