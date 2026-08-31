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

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.LibraryRecord;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.LibGroupNode;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.LibraryTreeNode;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.model.AdaptableList;

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
	public Object loadLibrarySource(final IProgressMonitor monitor) {
		final AdaptableList root = new AdaptableList();

		final List<LibGroupNode> standardGroups = new ArrayList<>();
		LibraryManager.INSTANCE.getStandardLibraries()
				.forEach((_, recs) -> standardGroups.add(new LibGroupNode(recs)));
		standardGroups.sort(Comparator.comparing(LibGroupNode::getSymbolicName));
		root.add(createSectionNode(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME, standardGroups));

		final List<LibGroupNode> extractedGroups = new ArrayList<>();
		LibraryManager.INSTANCE.getExtractedLibraries()
				.forEach((_, recs) -> extractedGroups.add(new LibGroupNode(recs)));
		extractedGroups.sort(Comparator.comparing(LibGroupNode::getSymbolicName));
		root.add(createSectionNode(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME, extractedGroups));

		return root;
	}

	private static LibraryTreeNode createSectionNode(final String label, final List<LibGroupNode> groups) {
		final LibraryTreeNode section = new LibraryTreeNode(label, label);
		for (final LibGroupNode group : groups) {
			final LibraryTreeNode groupNode = new LibraryTreeNode(group, group.getLabelText());
			for (final LibraryRecord rec : group.getLibraryRecords()) {
				groupNode.addChild(new LibraryTreeNode(rec, rec.symbolicName() + " - " + rec.version())); //$NON-NLS-1$
			}
			section.addChild(groupNode);
		}
		return section;
	}

	@Override
	public boolean isSelectableLeaf(final Object element) {
		final Object value = LibraryTreeNode.unwrapNode(element);
		return value instanceof LibraryRecord;
	}

	@Override
	public void install(final IProject targetProject, final Collection<?> selectedLeafElements,
			final IProgressMonitor monitor) throws Exception {

		final List<URI> urisToImport = new ArrayList<>();

		for (final Object o : selectedLeafElements) {
			if (monitor.isCanceled()) {
				return;
			}

			final Object value = LibraryTreeNode.unwrapNode(o);
			if (value instanceof final LibraryRecord rec) {
				urisToImport.add(rec.uri());
			}
		}

		if (!urisToImport.isEmpty()) {
			LibraryManager.INSTANCE.importLibraries(targetProject, urisToImport, true);
		}
		monitor.worked(urisToImport.size());
	}

	@Override
	public String exclusiveVersionSelectionKey(final Object element) {
		final Object value = LibraryTreeNode.unwrapNode(element);
		if (value instanceof final LibraryRecord rec) {
			return rec.symbolicName();
		}
		return null;
	}
}
