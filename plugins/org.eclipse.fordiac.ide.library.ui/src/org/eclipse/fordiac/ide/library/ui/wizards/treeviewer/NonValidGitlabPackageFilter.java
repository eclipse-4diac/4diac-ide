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

import java.util.List;

import org.eclipse.fordiac.ide.gitlab.Project;
import org.eclipse.fordiac.ide.gitlab.treeviewer.LeafNode;
import org.eclipse.jface.viewers.ViewerFilter;

public final class NonValidGitlabPackageFilter extends ViewerFilter {
	private boolean enabled;

	private java.util.Map<Project, List<org.eclipse.fordiac.ide.gitlab.Package>> projectsAndPackages;

	private java.util.Map<String, List<LeafNode>> packagesAndLeaves;

	private LatestOnlyFilter latestOnlyFilter;

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public void setContext(
			final java.util.Map<Project, List<org.eclipse.fordiac.ide.gitlab.Package>> projectsAndPackages,
			final java.util.Map<String, List<LeafNode>> packagesAndLeaves, final LatestOnlyFilter latestOnlyFilter) {
		this.projectsAndPackages = projectsAndPackages;
		this.packagesAndLeaves = packagesAndLeaves;
		this.latestOnlyFilter = latestOnlyFilter;
	}

	@Override
	public boolean select(final org.eclipse.jface.viewers.Viewer viewer, final Object parentElement,
			final Object element) {
		if (!enabled) {
			return true;
		}

		// Only apply to GitLab model nodes.
		if (element instanceof final org.eclipse.fordiac.ide.gitlab.Package pack) {
			return hasVisibleLeaves(pack);
		}

		if (element instanceof final org.eclipse.fordiac.ide.gitlab.Project project) {
			return hasVisiblePackages(project);
		}

		return true;
	}

	private boolean hasVisibleLeaves(final org.eclipse.fordiac.ide.gitlab.Package pack) {
		final var leavesMap = packagesAndLeaves;
		if (leavesMap == null) {
			return true;
		}

		if (latestOnlyFilter != null && latestOnlyFilter.isEnabled()) {
			return latestOnlyFilter.hasBestForPackage(pack.name());
		}

		final var leaves = leavesMap.get(pack.name());
		return leaves != null && !leaves.isEmpty();
	}

	private boolean hasVisiblePackages(final org.eclipse.fordiac.ide.gitlab.Project project) {
		final var pp = projectsAndPackages;
		if (pp == null) {
			return true;
		}
		final var packs = pp.get(project);
		if (packs == null || packs.isEmpty()) {
			return false;
		}
		for (final var p : packs) {
			if (p != null && hasVisibleLeaves(p)) {
				return true;
			}
		}
		return false;
	}
}