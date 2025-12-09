/*******************************************************************************
 * Copyright (c) 2021, 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber - refactored class structure
 *******************************************************************************/
package org.eclipse.fordiac.ide.model;

import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.jface.viewers.TreeViewer;

public abstract class AbstractStructTree<T extends AbstractStructTreeNode> {

	T root;
	protected TreeViewer viewer;

	protected AbstractStructTree(final StructManipulator manipulator, final StructuredType struct,
			final TreeViewer viewer) {
		this.viewer = viewer;
		initTree(manipulator, struct);
	}

	protected AbstractStructTree(final StructManipulator manipulator, final StructuredType struct) {
		this.viewer = null;
		initTree(manipulator, struct);
	}

	private final void initTree(final StructManipulator manipulator, final StructuredType struct) {
		root = createRoot();
		buildTree(manipulator, struct, root);
	}

	public T getRoot() {
		return root;
	}

	public TreeViewer getViewer() {
		return viewer;
	}

	public void setViewer(final TreeViewer viewer) {
		this.viewer = viewer;
	}

	public abstract void buildTree(StructManipulator struct, StructuredType structType, T parent);

	public abstract T createRoot();

}
