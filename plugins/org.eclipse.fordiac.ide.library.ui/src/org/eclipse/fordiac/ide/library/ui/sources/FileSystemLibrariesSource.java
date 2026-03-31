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
import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.LibraryTreeNode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.model.AdaptableList;

public final class FileSystemLibrariesSource implements ILibrarySource {

	private Path rootFolder;

	@Override
	public String id() {
		return "filesystem"; //$NON-NLS-1$
	}

	@Override
	public String comboLabelText() {
		return "FileSystem Libraries"; //$NON-NLS-1$
	}

	@Override
	public void createConfigUI(final Composite parent) {
		final Composite grid = new Composite(parent, SWT.NONE);
		grid.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		grid.setLayout(new GridLayout(3, false));

		new Label(grid, SWT.NONE).setText("Root folder:"); //$NON-NLS-1$

		final Text rootText = new Text(grid, SWT.BORDER | SWT.SINGLE);
		rootText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		rootText.setMessage("Choose a folder containing ZIPs or extracted libraries..."); //$NON-NLS-1$

		final Button browse = new Button(grid, SWT.PUSH);
		browse.setText("Browse…"); //$NON-NLS-1$
		browse.addListener(SWT.Selection, ev -> {
			final DirectoryDialog dlg = new DirectoryDialog(parent.getShell());
			dlg.setText("Select library root folder"); //$NON-NLS-1$
			final String sel = dlg.open();
			if (sel != null && !sel.isBlank()) {
				rootText.setText(sel);
				rootFolder = Path.of(sel);
			}
		});
	}

	@Override
	public Object loadLibrarySource(final IProgressMonitor monitor) {
		final Path root = rootFolder;
		final AdaptableList list = new AdaptableList();
		if (root == null || !Files.isDirectory(root)) {
			return list;
		}

		for (final Path path : LibraryManager.INSTANCE.listArchiveFolders(root)) {
			list.add(createPathNode(path));
		}
		return list;
	}

	private static LibraryTreeNode createPathNode(final Path path) {
		final String label = path.getFileName() != null ? path.getFileName().toString() : path.toString();
		final LibraryTreeNode node = new LibraryTreeNode(path, label);
		if (Files.isDirectory(path)) {
			for (final Path child : LibraryManager.INSTANCE.listArchiveFolders(path)) {
				node.addChild(createPathNode(child));
			}
		}
		return node;
	}

	@Override
	public boolean isSelectableLeaf(final Object element) {
		return LibraryTreeNode.unwrapNode(element) instanceof Path;
	}

	@Override
	public void install(final IProject targetProject, final Collection<?> selectedLeafElements,
			final IProgressMonitor monitor) throws Exception {
		int done = 0;
		for (final Object o : selectedLeafElements) {
			if (monitor.isCanceled()) {
				return;
			}

			final Object value = LibraryTreeNode.unwrapNode(o);
			if (!(value instanceof final Path p)) {
				continue;
			}

			if (Files.isDirectory(p)) {
				LibraryManager.INSTANCE.importLibrary(targetProject, p.toUri(), true, true);
			} else {
				LibraryManager.INSTANCE.extractLibrary(p, targetProject, true, true);
			}
			done++;
		}
		monitor.worked(done);
	}
}
