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
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.gitlab.Package;
import org.eclipse.fordiac.ide.gitlab.Project;
import org.eclipse.fordiac.ide.gitlab.management.GitLabDownloader;
import org.eclipse.fordiac.ide.gitlab.preferences.GitLabEndpoint;
import org.eclipse.fordiac.ide.gitlab.treeviewer.GLTreeContentProvider;
import org.eclipse.fordiac.ide.gitlab.treeviewer.LeafNode;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.DownloadResult.Status;
import org.eclipse.fordiac.ide.library.ui.sources.LibrarySourceBuilder.EmptyTreeContentProvider;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.services.IDisposable;

public final class GitLabEndpointSource implements ILibrarySource, IDisposable {

	private final GitLabEndpoint endpoint;

	private GitLabDownloader downloadManager;

	private String details = "Configured via Preferences → GitLab Endpoints.\n" + "Endpoint: ";

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

	private Label l = null;

	@Override
	public void createConfigUI(final Composite parent) {
		l = new Label(parent, SWT.WRAP);
		l.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		l.setText(details);
	}

	@Override
	public LibrarySourceUIComponents loadLibrarySource(final IProgressMonitor monitor) {
		if (!endpoint.isValid()) {
			return new LibrarySourceUIComponents(new EmptyTreeContentProvider(), new LabelProvider(), new Object[0],
					null);
		}

		downloadManager = new GitLabDownloader(endpoint.token(), endpoint.url());
		final GitLabDownloader mgr = downloadManager;

		final DownloadResult<Void> fetchProjectsAndPackages = mgr.fetchProjectsAndPackages();

		if (fetchProjectsAndPackages.status() == Status.OK) {
			details = "Connection Sucessfull";
		}

		final Map<Project, List<Package>> projectsAndPackages = mgr.getProjectsAndPackages();
		final Map<String, List<LeafNode>> packagesAndLeavesRaw = mgr.getPackagesAndLeaves();

		final ITreeContentProvider cp = new GLTreeContentProvider(packagesAndLeavesRaw);

		final ILabelProvider lp = new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final Project project) {
					return project.name();
				}
				if (element instanceof final Package pack) {
					return pack.name();
				}
				if (element instanceof final LeafNode leafNode) {
					return leafNode.getVersion();
				}
				return "";
			}
		};

		return new LibrarySourceUIComponents(cp, lp, projectsAndPackages, packagesAndLeavesRaw);
	}

	@Override
	public boolean isSelectableLeaf(final Object element) {
		return element instanceof LeafNode;
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
			if (o instanceof final LeafNode leafNode) {
				final Path path = mgr.packageDownloader(leafNode.getProject(), leafNode.getPackage(),
						GitLabDownloader.FileFilter.ZIP);
				if (path != null) {
					LibraryManager.INSTANCE.extractLibrary(path, targetProject, true, true);
					// cleanup downloaded zipfile
					deleteDirectoryRecursive(path.getParent());
				}
				done++;
			}
		}
		monitor.worked(done);
	}

	private static void deleteDirectoryRecursive(final Path dir) throws java.io.IOException {
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
	public String exclusiveVersinSelectionKey(final Object element) {
		if (element instanceof final LeafNode leaf) {
			return leaf.getProject().name() + "/" + leaf.getPackage().name(); //$NON-NLS-1$
		}
		return null;
	}
}