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
import java.net.URI;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.LibraryRecord;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.LibGroupNode;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.SectionNode;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.WorkspaceRoot;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class WorkspaceLibrariesSource implements ILibrarySource {

	@Override
	public String id() {
		return "workspace"; //$NON-NLS-1$
	}

	@Override
	public String comboLabelText() {
		return "Workspace Libraries"; //$NON-NLS-1$
	}

	@Override
	public void createConfigUI(final Composite parent) {
		final Label l = new Label(parent, SWT.WRAP);
		l.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		l.setText("Shows libraries from your workspace." + System.lineSeparator() //$NON-NLS-1$
				+ "You can import libraries into the target project."); //$NON-NLS-1$
	}

	@Override
	public LibrarySourceUIComponents loadLibrarySource(final IProgressMonitor monitor) {
		final WorkspaceRoot root = new WorkspaceRoot();

		LibraryManager.INSTANCE.getExtractedLibraries().forEach((symbolicName, recs) -> {
			root.getExtractedLibs().add(new LibGroupNode(recs));
		});
		root.getExtractedLibs().sort(Comparator.comparing(LibGroupNode::getSymbolicName));

		LibraryManager.INSTANCE.getStandardLibraries().forEach((symbolicName, recs) -> {
			root.getStandardLibs().add(new LibGroupNode(recs));
		});

		root.getStandardLibs().sort(Comparator.comparing(LibGroupNode::getSymbolicName));

		final ITreeContentProvider cp = new WorkspaceLibrariesContentProvider();
		final ILabelProvider lp = new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof WorkspaceRoot) {
					return ""; //$NON-NLS-1$
				}
				if (element instanceof final SectionNode s) {
					return s.getLabelText();
				}
				if (element instanceof final LibGroupNode g) {
					return g.getLabelText();
				}
				if (element instanceof final LibraryRecord r) {
					return r.symbolicName() + " - " + r.version(); //$NON-NLS-1$
				}
				if (element instanceof final Path p) {
					return p.getFileName() != null ? p.getFileName().toString() : p.toString();
				}
				return super.getText(element);
			}
		};

		return new LibrarySourceUIComponents(cp, lp, root, null);
	}

	@Override
	public boolean isSelectableLeaf(final Object element) {
		return element instanceof LibraryRecord || element instanceof Path;
	}

	@Override
	public void install(final IProject targetProject, final Collection<?> selectedLeafElements,
			final IProgressMonitor monitor) throws Exception {

		final List<URI> urisToImport = new ArrayList<>();
		int importedFromFolder = 0;

		for (final Object o : selectedLeafElements) {
			if (monitor.isCanceled()) {
				return;
			}

			if (o instanceof final LibraryRecord rec) {
				// link/import existing extracted library
				urisToImport.add(rec.uri());
				continue;
			}

			if ((o instanceof final Path p) && Files.isDirectory(p)) {
				// treat as extracted library directory
				LibraryManager.INSTANCE.importLibrary(targetProject, p.toUri(), true, true);
				importedFromFolder++;
			}
		}

		if (!urisToImport.isEmpty()) {
			LibraryManager.INSTANCE.importLibraries(targetProject, urisToImport, true);
		}
		monitor.worked(urisToImport.size() + importedFromFolder);
	}

	private static final class WorkspaceLibrariesContentProvider implements ITreeContentProvider {
		private static final Object[] EMPTY = new Object[0];

		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof final WorkspaceRoot root) {
				return root.getChildren();
			}
			return EMPTY;
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			if ((parentElement instanceof final SectionNode s)
					&& (s.getLibGroupNodeChildren() instanceof final List<?> list)) {
				return list.toArray();
			}
			if (parentElement instanceof final LibGroupNode g) {
				return g.getLibraryRecords().toArray();
			}
			if (parentElement instanceof final Path p && Files.isDirectory(p)) {
				return listPathChildren(p);
			}
			return EMPTY;
		}

		@Override
		public Object getParent(final Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(final Object element) {
			if (element instanceof SectionNode) {
				return true;
			}
			if (element instanceof LibGroupNode) {
				return true;
			}
			if (element instanceof final Path p) {
				return Files.isDirectory(p);
			}
			return false;
		}

		private static Object[] listPathChildren(final Path dir) {
			final List<Path> children = new ArrayList<>();
			try (var stream = Files.newDirectoryStream(dir, (Filter<? super Path>) Files::isDirectory)) {
				stream.forEach(children::add);
			} catch (final IOException e) {
				// ignore
			}
			children.sort(
					Comparator.comparing(p -> p.getFileName() != null ? p.getFileName().toString() : p.toString()));
			return children.toArray(Path[]::new);
		}

	}

	@Override
	public String exclusiveVersinSelectionKey(final Object element) {
		if (element instanceof final LibraryRecord rec) {
			return rec.symbolicName();
		}
		return null;
	}
}
