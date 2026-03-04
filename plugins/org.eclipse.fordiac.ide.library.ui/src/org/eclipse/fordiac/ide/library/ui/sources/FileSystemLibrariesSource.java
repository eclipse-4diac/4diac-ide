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
import org.eclipse.fordiac.ide.library.ui.sources.LibrarySourceBuilder.EmptyTreeContentProvider;
import org.eclipse.fordiac.ide.library.ui.wizards.ArchivedLibraryImportContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public final class FileSystemLibrariesSource implements ILibrarySource {

	private Path rootFolder; // chosen via UI

	private Text rootText;

	@Override
	public String id() {
		return "filesystem"; //$NON-NLS-1$
	}

	@Override
	public String comboLabelText() {
		return "FileSystem Libraries";
	}

	@Override
	public void createConfigUI(final Composite parent) {
		final Composite grid = new Composite(parent, SWT.NONE);
		grid.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		grid.setLayout(new GridLayout(3, false));

		new Label(grid, SWT.NONE).setText("Root folder:");

		rootText = new Text(grid, SWT.BORDER | SWT.SINGLE);
		rootText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		rootText.setMessage("Choose a folder containing ZIPs or extracted libraries...");

		final Button browse = new Button(grid, SWT.PUSH);
		browse.setText("Browse…");

		browse.addListener(SWT.Selection, ev -> {
			final DirectoryDialog dlg = new DirectoryDialog(parent.getShell());
			dlg.setText("Select library root folder");
			final String sel = dlg.open();
			if (sel != null && !sel.isBlank()) {
				rootText.setText(sel);
				rootFolder = Path.of(sel);
			}
		});
	}

	@Override
	public LibrarySourceUIComponents loadLibrarySource(final IProgressMonitor monitor) {
		final Path root = rootFolder;
		if (root == null || !Files.isDirectory(root)) {
			return new LibrarySourceUIComponents(new EmptyTreeContentProvider(), new LabelProvider(), new Object[0],
					null);
		}

		// show only top-level directories and archives (same logic as LibraryManager
		// uses for its archive folder)
		final Path[] content = LibraryManager.INSTANCE.listArchiveFolders(root);

		final ITreeContentProvider cp = new ArchivedLibraryImportContentProvider();
		final ILabelProvider lp = new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final Path p) {
					return p.getFileName() != null ? p.getFileName().toString() : p.toString();
				}
				return ""; //$NON-NLS-1$
			}
		};

		return new LibrarySourceUIComponents(cp, lp, content, null);
	}

	@Override
	public boolean isSelectableLeaf(final Object element) {
		return element instanceof Path;
	}

	@Override
	public void install(final IProject targetProject, final Collection<?> selectedLeafElements,
			final IProgressMonitor monitor) throws Exception {

		int done = 0;
		for (final Object o : selectedLeafElements) {
			if (monitor.isCanceled()) {
				return;
			}
			if (!(o instanceof final Path p)) {
				continue;
			}

			if (Files.isDirectory(p)) {
				// Import extracted library directory (link)
				LibraryManager.INSTANCE.importLibrary(targetProject, p.toUri(), true, true);
				done++;
				continue;
			}

			// ZIP archive -> extract
			LibraryManager.INSTANCE.extractLibrary(p, targetProject, true, true);
			done++;
		}
		monitor.worked(done);
	}
}