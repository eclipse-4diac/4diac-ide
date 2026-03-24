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
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.wizards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.gitlab.management.GitLabDownloader;
import org.eclipse.fordiac.ide.gitlab.treeviewer.LeafNode;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.library.ui.wizards.LibraryDescriptorNode.LibraryDescriptorLabelProvider;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Version;

public class LibraryPlanningPage extends WizardPage {

	private TreeViewer treeViewer;
	private final IProject project;
	private final Map<String, List<String>> localVersionLookup;
	private final Map<String, List<String>> remoteVersionLookup;

	protected LibraryPlanningPage(final String pageName, final IProject project) {
		super(pageName);
		this.project = project;
		this.localVersionLookup = new HashMap<>();
		this.remoteVersionLookup = new HashMap<>();
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite root = new Composite(parent, SWT.NONE);
		root.setLayout(new GridLayout(1, false));

		final Composite treeContainer = new Composite(root, SWT.NONE);
		treeContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final TreeColumnLayout columnLayout = new TreeColumnLayout();
		treeContainer.setLayout(columnLayout);

		treeViewer = new TreeViewer(treeContainer,
				SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		treeViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		initVersionLookup();

		configureColumns(columnLayout);

		treeViewer.getTree().setHeaderVisible(true);
		treeViewer.setContentProvider(new ITreeContentProvider() {

			@Override
			public boolean hasChildren(final Object element) {
				if (element instanceof final Collection<?> list) {
					return !list.isEmpty();
				}
				if (element instanceof final LibraryDescriptorNode desc) {
					return !desc.getChildren().isEmpty();
				}
				return false;
			}

			@Override
			public Object getParent(final Object element) {
				return null;
			}

			@Override
			public Object[] getElements(final Object inputElement) {
				if (inputElement instanceof final Collection<?> list) {
					return list.toArray();
				}
				return new Object[0];
			}

			@Override
			public Object[] getChildren(final Object parentElement) {
				if (parentElement instanceof final Collection<?> list) {
					return list.toArray();
				}
				if (parentElement instanceof final LibraryDescriptorNode desc) {
					return desc.getChildren().toArray();
				}
				return new Object[0];
			}
		});

		treeViewer.setInput(getViewerInput());
		treeViewer.getTree().setLinesVisible(true);
		setControl(root);

		setPageComplete(false);

		treeViewer.expandAll();

		treeViewer.getTree().getDisplay().asyncExec(() -> {
			treeViewer.getTree().pack();
			treeViewer.getTree().layout();
		});

	}

	private List<LibraryDescriptorNode> getViewerInput() {
		final LibraryDescriptorNode stdLib = new LibraryDescriptorNode(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME, "");
		LibraryManager.getLinkedLibraries(project.getFolder(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME)).stream().forEach(
				lib -> stdLib.addChild(new LibraryDescriptorNode(lib.symbolicName(), lib.version().toString())));

		final LibraryDescriptorNode extLib = new LibraryDescriptorNode(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME, "");
		LibraryManager.getLinkedLibraries(project.getFolder(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME)).stream().forEach(
				lib -> extLib.addChild(new LibraryDescriptorNode(lib.symbolicName(), lib.version().toString())));
		return List.of(extLib, stdLib);
	}

	private void configureColumns(final TreeColumnLayout layout) {
		final TreeViewerColumn symbolicNameColumn = createColumn(Messages.LibraryPlanningPage_SymbolicName,
				new LibraryDescriptorLabelProvider(LibraryDescriptorNode::getName));
		layout.setColumnData(symbolicNameColumn.getColumn(), new ColumnWeightData(40));

		final TreeViewerColumn activeVersionColumn = createColumn(Messages.LibraryPlanningPage_ActiveVersion,
				new LibraryDescriptorLabelProvider(LibraryDescriptorNode::getActiveVersion));
		layout.setColumnData(activeVersionColumn.getColumn(), new ColumnWeightData(20));

		final TreeViewerColumn actionColumn = createColumn(Messages.LibraryPlanningPage_Action,
				new LibraryDescriptorNode.ActionLabelProvider());
		layout.setColumnData(actionColumn.getColumn(), new ColumnWeightData(20));

		actionColumn.setEditingSupport(new EditingSupport(treeViewer) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (element instanceof final LibraryDescriptorNode rec && value instanceof final Integer i) {
					final List<LibraryChangeAction> actions = getAvailableActions(rec);

					if (i.intValue() >= 0 && i.intValue() < actions.size()) {
						rec.setAction(actions.get(i.intValue()));
						treeViewer.update(element, null);
					}
				}
			}

			@Override
			protected Object getValue(final Object element) {
				if (element instanceof final LibraryDescriptorNode rec) {
					return Integer.valueOf(getAvailableActions(rec).indexOf(rec.getActionType()));
				}
				return Integer.valueOf(0);
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				if (element instanceof final LibraryDescriptorNode rec) {
					return new ComboBoxCellEditor(treeViewer.getTree(),
							getAvailableActionStrings(rec).toArray(new String[0]), SWT.READ_ONLY);
				}
				return new ComboBoxCellEditor(treeViewer.getTree(), new String[0], SWT.READ_ONLY);
			}

			@Override
			protected boolean canEdit(final Object element) {
				if (element instanceof final LibraryDescriptorNode rec) {
					return rec.getChildren().isEmpty();
				}
				return false;
			}

			private List<String> getAvailableVersions(final LibraryDescriptorNode node) {
				return Stream
						.concat(localVersionLookup.getOrDefault(node.getName(), Collections.emptyList()).stream(),
								remoteVersionLookup.getOrDefault(node.getName(), Collections.emptyList()).stream())
						.distinct().toList();
			}

			private List<String> getAvailableActionStrings(final LibraryDescriptorNode node) {
				return getAvailableActions(node).stream().map(LibraryChangeAction::getActionText).toList();
			}

			private List<LibraryChangeAction> getAvailableActions(final LibraryDescriptorNode node) {
				return Stream
						.concat(Stream.of(LibraryChangeAction.emptyAction(), LibraryChangeAction.removeAction()),
								getAvailableVersions(node).stream().map(v -> LibraryChangeAction.createAction(node, v)))
						.distinct().toList();
			}

		});

	}

	private void initVersionLookup() {
		// Get available standard libs
		LibraryManager.getLinkedLibraries(project.getFolder(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME))
				.forEach(i -> localVersionLookup.computeIfAbsent(i.symbolicName(), s -> new ArrayList<>())
						.addAll(LibraryManager.INSTANCE.getAllAvailableVersions(i.symbolicName()).map(Version::toString)
								.toList()));

		// Get available external libs
		LibraryManager.getLinkedLibraries(project.getFolder(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME))
				.forEach(i -> localVersionLookup.computeIfAbsent(i.symbolicName(), s -> new ArrayList<>())
						.addAll(LibraryManager.INSTANCE.getAllAvailableVersions(i.symbolicName()).map(Version::toString)
								.toList()));

		// Get available remote Versions
		initRemoteVersionLookup();

	}

	private void initRemoteVersionLookup() {
		final GitLabDownloader downloader = new GitLabDownloader();
		downloader.convertEndpointsToDownloader().stream().forEach(d -> {
			if (d.isActive()) {
				final DownloadResult<Void> fetch = downloader.fetchProjectsAndPackages();
				if (fetch.status() == DownloadResult.Status.OK) {
					final Map<String, List<LeafNode>> packagesAndLeaves = downloader.getPackagesAndLeaves();

					for (final String symbolicName : localVersionLookup.keySet()) {
						packagesAndLeaves.getOrDefault(symbolicName, Collections.emptyList()).stream()
								.map(LeafNode::getVersion).forEach(v -> remoteVersionLookup
										.computeIfAbsent(symbolicName, s -> new ArrayList<>()).add(v));
					}

				}
			}
		});
	}

	private TreeViewerColumn createColumn(final String name, final CellLabelProvider labelProvider) {
		final TreeViewerColumn column = new TreeViewerColumn(treeViewer, SWT.NONE);
		column.getColumn().setText(name);
		column.setLabelProvider(labelProvider);
		return column;
	}

}
