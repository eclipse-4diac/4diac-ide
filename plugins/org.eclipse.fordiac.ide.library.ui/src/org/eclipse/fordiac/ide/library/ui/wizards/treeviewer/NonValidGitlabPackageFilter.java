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

import org.eclipse.fordiac.ide.gitlab.Project;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public final class NonValidGitlabPackageFilter extends ViewerFilter {

	private boolean enabled;
	private LatestOnlyFilter latestOnlyFilter;

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public void setLatestOnlyFilter(final LatestOnlyFilter latestOnlyFilter) {
		this.latestOnlyFilter = latestOnlyFilter;
	}

	@Override
	public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
		if (!enabled) {
			return true;
		}

		final Object value = LibraryTreeNode.unwrapNode(element);

		if (value instanceof final org.eclipse.fordiac.ide.gitlab.Package pack) {
			return hasVisibleLeaves(element, pack);
		}

		if (value instanceof final Project project) {
			return hasVisiblePackages(element, project);
		}

		return true;
	}

	private boolean hasVisibleLeaves(final Object element, final org.eclipse.fordiac.ide.gitlab.Package pack) {
		if (!(element instanceof final LibraryTreeNode node)) {
			return true;
		}
		if (node.getChildren().isEmpty()) {
			return false;
		}

		if (latestOnlyFilter != null && latestOnlyFilter.isEnabled()) {
			return latestOnlyFilter.hasBestForPackage(pack.name());
		}
		return true;
	}

	private boolean hasVisiblePackages(final Object element, final Project project) {
		if (!(element instanceof final LibraryTreeNode node)) {
			return true;
		}
		for (final LibraryTreeNode child : node.getChildren()) {
			final Object childValue = LibraryTreeNode.unwrapNode(child);
			if (childValue instanceof final org.eclipse.fordiac.ide.gitlab.Package pack
					&& hasVisibleLeaves(child, pack)) {
				return true;
			}
		}
		return false;
	}
}
