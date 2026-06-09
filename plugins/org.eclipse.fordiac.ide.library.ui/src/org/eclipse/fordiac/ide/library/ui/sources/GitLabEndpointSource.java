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
package org.eclipse.fordiac.ide.library.ui.sources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.gitlab.Package;
import org.eclipse.fordiac.ide.gitlab.Project;
import org.eclipse.fordiac.ide.gitlab.management.GitLabDownloader;
import org.eclipse.fordiac.ide.gitlab.preferences.GitLabEndpoint;
import org.eclipse.fordiac.ide.gitlab.treeviewer.LeafNode;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.DownloadResult.Status;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.LibraryTreeNode;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.model.AdaptableList;
import org.eclipse.ui.services.IDisposable;

public final class GitLabEndpointSource implements ILibrarySource, IDisposable {

	private final GitLabEndpoint endpoint;
	private GitLabDownloader downloadManager;
	private String details = "Configured via Preferences → GitLab Endpoints.\n" + "Endpoint: "; //$NON-NLS-1$ //$NON-NLS-2$

	GitLabEndpointSource(final GitLabEndpoint endpoint) {
		this.endpoint = Objects.requireNonNull(endpoint);
	}

	@Override
	public String id() {
		return "gitlab:" + endpoint.name(); //$NON-NLS-1$
	}

	@Override
	public String comboLabelText() {
		return "GitLab – " + endpoint.name();
	}

	@Override
	public void createConfigUI(final Composite parent) {
		final Label l = new Label(parent, SWT.WRAP);
		l.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		l.setText(details + endpoint.name());
	}

	@Override
	public Object loadLibrarySource(final IProgressMonitor monitor) {
		final AdaptableList root = new AdaptableList();
		if (!endpoint.isValid()) {
			return root;
		}

		downloadManager = new GitLabDownloader(endpoint.token(), endpoint.url());
		final GitLabDownloader mgr = downloadManager;
		final DownloadResult<Void> fetchProjectsAndPackages = mgr.fetchProjectsAndPackages();
		if (fetchProjectsAndPackages.status() == Status.OK) {
			details = "Connection Sucessfull"; //$NON-NLS-1$
		}

		final Map<Project, List<Package>> projectsAndPackages = mgr.getProjectsAndPackages();
		final Map<String, List<LeafNode>> packagesAndLeaves = mgr.getPackagesAndLeaves();
		projectsAndPackages.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.comparing(Project::name)))
				.forEach(entry -> root.add(createProjectNode(entry.getKey(), entry.getValue(), packagesAndLeaves)));

		return root;
	}

	private static LibraryTreeNode createProjectNode(final Project project, final List<Package> packages,
			final Map<String, List<LeafNode>> packagesAndLeaves) {
		final LibraryTreeNode projectNode = new LibraryTreeNode(project, project.name());
		packages.stream().sorted(Comparator.comparing(Package::name))
				.collect(Collectors.toMap(Package::name, Function.identity(), (first, ignored) -> first,
						LinkedHashMap::new))
				.values().forEach(pack -> {
					final LibraryTreeNode packageNode = new LibraryTreeNode(pack, pack.name());
					packagesAndLeaves.getOrDefault(pack.name(), List.of()).stream()
							.sorted(Comparator.comparing(LeafNode::getVersion))
							.forEach(leaf -> packageNode.addChild(new LibraryTreeNode(leaf, leaf.getVersion())));
					projectNode.addChild(packageNode);
				});
		return projectNode;
	}

	@Override
	public boolean isSelectableLeaf(final Object element) {
		return LibraryTreeNode.unwrapNode(element) instanceof LeafNode;
	}

	@Override
	public void install(final IProject targetProject, final Collection<?> selectedLeafElements,
			final IProgressMonitor monitor) throws IOException {
		final GitLabDownloader mgr = downloadManager;
		if (mgr == null) {
			throw new IOException("GitLab endpoint not available."); //$NON-NLS-1$
		}

		int done = 0;
		for (final Object o : selectedLeafElements) {
			monitor.worked(0);
			if (monitor.isCanceled()) {
				return;
			}

			final Object value = LibraryTreeNode.unwrapNode(o);
			if (value instanceof final LeafNode leafNode) {
				final Path path = mgr.packageDownloader(leafNode.getProject(), leafNode.getPackage(),
						GitLabDownloader.FileFilter.ZIP);
				if (path != null) {
					LibraryManager.INSTANCE.extractLibrary(path, targetProject, true, true);
					deleteDirectoryRecursive(path.getParent());
				}
				done++;
			}
		}
		monitor.worked(done);
	}

	private static void deleteDirectoryRecursive(final Path dir) throws IOException {
		if (dir == null || !Files.exists(dir)) {
			return;
		}
		try (var pathStream = Files.walk(dir)) {
			pathStream.sorted(Comparator.reverseOrder()).forEach(p -> {
				try {
					Files.deleteIfExists(p);
				} catch (final Exception e) {
					FordiacLogHelper.logError("Deletion of downloaded zip failed", e); //$NON-NLS-1$
				}
			});
		}
	}

	@Override
	public void dispose() {
		downloadManager = null;
	}

	@Override
	public String exclusiveVersionSelectionKey(final Object element) {
		final Object value = LibraryTreeNode.unwrapNode(element);
		if (value instanceof final LeafNode leaf) {
			return leaf.getProject().name() + "/" + leaf.getPackage().name(); //$NON-NLS-1$
		}
		return null;
	}
}
