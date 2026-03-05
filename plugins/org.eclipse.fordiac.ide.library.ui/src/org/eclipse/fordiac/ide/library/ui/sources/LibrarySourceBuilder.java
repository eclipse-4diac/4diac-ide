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

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.gitlab.Package;
import org.eclipse.fordiac.ide.gitlab.Project;
import org.eclipse.fordiac.ide.gitlab.preferences.GitLabEndpoint;
import org.eclipse.fordiac.ide.gitlab.preferences.GitLabEndpointsStore;
import org.eclipse.fordiac.ide.gitlab.treeviewer.LeafNode;
import org.eclipse.fordiac.ide.library.LibraryRecord;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.LibGroupNode;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.SectionNode;
import org.eclipse.jface.viewers.ITreeContentProvider;

public final class LibrarySourceBuilder {

	private LibrarySourceBuilder() {
		// util
	}

	public static List<ILibrarySource> getAllSources() {
		final List<ILibrarySource> res = new ArrayList<>();
		res.add(new WorkspaceLibrariesSource());
		res.add(new FileSystemLibrariesSource());

		final List<GitLabEndpoint> endpoints = GitLabEndpointsStore.loadEndpoints();
		endpoints.stream().forEach(ep -> res.add(new GitLabEndpointSource(ep)));
		return res;
	}

	public static final class ExistingLibTreeContentProvider implements ITreeContentProvider {
		private static final Object[] EMPTY = new Object[0];

		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof final Object[] arr) {
				return arr;
			}
			return EMPTY;
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			if (parentElement instanceof final LibGroupNode node) {
				return node.getLibraryRecords().toArray();
			}
			return EMPTY;
		}

		@Override
		public Object getParent(final Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(final Object element) {
			return element instanceof LibGroupNode;
		}
	}

	public static final class EmptyTreeContentProvider implements ITreeContentProvider {
		@Override
		public Object[] getElements(final Object inputElement) {
			return new Object[0];
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			return new Object[0];
		}

		@Override
		public Object getParent(final Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(final Object element) {
			return false;
		}
	}

	public static String buildDetails(final Object sel) {
		return switch (sel) {
		case null -> ""; //$NON-NLS-1$
		case final LeafNode leaf -> Messages.LibrarySourceBuilder_0 + System.lineSeparator() + "" + Messages.LibrarySourceBuilder_2
				+ leaf.getProject().name() + System.lineSeparator() + Messages.LibrarySourceBuilder_3 + leaf.getPackage().name()
				+ System.lineSeparator() + Messages.LibrarySourceBuilder_4 + leaf.getVersion() + System.lineSeparator();
		case final Project p ->
			Messages.LibrarySourceBuilder_5 + System.lineSeparator() + Messages.LibrarySourceBuilder_6 + p.name() + System.lineSeparator();
		case final Package p ->
			Messages.LibrarySourceBuilder_gitlab_package + System.lineSeparator() + Messages.LibrarySourceBuilder_name + p.name() + System.lineSeparator();
		case final Path path -> Messages.LibrarySourceBuilder_file_system + System.lineSeparator() + Messages.LibrarySourceBuilder_path + path + System.lineSeparator()
				+ (Files.isDirectory(path) ? Messages.LibrarySourceBuilder_dir : Messages.LibrarySourceBuilder_file) + System.lineSeparator();
		case final LibraryRecord rec -> Messages.LibrarySourceBuilder_lib + System.lineSeparator() + Messages.LibrarySourceBuilder_name + rec.name()
				+ System.lineSeparator() + Messages.LibrarySourceBuilder_sym_name + rec.symbolicName() + System.lineSeparator() + Messages.LibrarySourceBuilder_version
				+ rec.version() + System.lineSeparator() + Messages.LibrarySourceBuilder_comment + rec.comment() + System.lineSeparator()
				+ Messages.LibrarySourceBuilder_uri + rec.uri() + System.lineSeparator();
		case final LibGroupNode n -> n.getLabelText();
		case final SectionNode n -> n.getLabelText();
		default -> sel.toString();
		};

	}
}