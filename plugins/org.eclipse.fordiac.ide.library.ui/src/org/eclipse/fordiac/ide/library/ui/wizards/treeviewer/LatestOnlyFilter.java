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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.wizards.treeviewer;

import org.eclipse.fordiac.ide.gitlab.treeviewer.LeafNode;
import org.eclipse.fordiac.ide.library.LibraryRecord;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.osgi.framework.Version;

public final class LatestOnlyFilter extends ViewerFilter {

	private volatile boolean enabled;
	private final java.util.IdentityHashMap<Object, Object> bestLeafByParent = new java.util.IdentityHashMap<>();
	private final java.util.HashMap<String, Object> bestByPackageName = new java.util.HashMap<>();

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public void rebuildIndex(final TreeViewer viewer, final ITreeContentProvider contentProvider, final Object input) {
		bestLeafByParent.clear();
		bestByPackageName.clear();
		if (viewer == null || contentProvider == null || input == null) {
			return;
		}
		final Object[] roots = safeArray(contentProvider.getElements(input));
		for (final Object root : roots) {
			indexRecursively(contentProvider, root);
		}
	}

	private void indexRecursively(final ITreeContentProvider cp, final Object parent) {
		final Object[] children = safeArray(cp.getChildren(parent));
		if (children.length == 0) {
			return;
		}

		final Object parentValue = LibraryTreeNode.unwrapNode(parent);
		final Object firstChildValue = LibraryTreeNode.unwrapNode(children[0]);
		if (parentValue instanceof final org.eclipse.fordiac.ide.gitlab.Package pack && firstChildValue instanceof LeafNode) {
			Object best = children[0];
			for (final Object c : children) {
				if (compareGitLabLeaf(c, best) > 0) {
					best = c;
				}
			}
			bestLeafByParent.put(parent, best);
			bestByPackageName.put(pack.name(), best);
			return;
		}
		if (parentValue instanceof LibGroupNode && firstChildValue instanceof LibraryRecord) {
			bestLeafByParent.put(parent, children[0]);
			return;
		}

		for (final Object c : children) {
			indexRecursively(cp, c);
		}
	}

	@Override
	public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
		if (!enabled) {
			return true;
		}
		if (parentElement != null) {
			final Object best = bestLeafByParent.get(parentElement);
			if (best != null) {
				final Object value = LibraryTreeNode.unwrapNode(element);
				if (value instanceof LeafNode || value instanceof LibraryRecord) {
					return element == best;
				}
			}
		}
		return true;
	}

	private static int compareGitLabLeaf(final Object a, final Object b) {
		final String va = LibraryTreeNode.unwrapNode(a) instanceof final LeafNode l ? l.getVersion() : null;
		final String vb = LibraryTreeNode.unwrapNode(b) instanceof final LeafNode l ? l.getVersion() : null;
		return compareVersions(va, vb);
	}

	private static int compareVersions(final String a, final String b) {
		try {
			final var va = Version.parseVersion(String.valueOf(a));
			final var vb = Version.parseVersion(String.valueOf(b));
			return va.compareTo(vb);
		} catch (final Exception ex) {
			return String.valueOf(a).compareTo(String.valueOf(b));
		}
	}

	private static Object[] safeArray(final Object[] in) {
		return in != null ? in : new Object[0];
	}

	boolean isEnabled() {
		return enabled;
	}

	boolean hasBestForPackage(final String packageName) {
		return bestByPackageName.containsKey(packageName);
	}
}
