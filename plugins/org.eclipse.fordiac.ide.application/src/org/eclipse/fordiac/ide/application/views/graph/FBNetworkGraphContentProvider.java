/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.views.graph;

import org.eclipse.fordiac.ide.model.graph.FBNetworkGraph;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class FBNetworkGraphContentProvider implements ITreeContentProvider {
	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof final FBNetworkGraph<?> graph) {
			return graph.getSortedNodes().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof final FBNetworkGraph<?>.Node node) {
			final FBNetworkGraph<?> subgraph = node.loadSubgraph();
			if (subgraph != null) {
				return subgraph.getSortedNodes().toArray();
			}
		}
		return new Object[0];
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof final FBNetworkGraph<?>.Node node) {
			return node.getGraph().getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof final FBNetworkGraph<?>.Node node) {
			return node.hasSubElements();
		}
		return false;
	}
}