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
package org.eclipse.fordiac.ide.gitlab.preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.fordiac.ide.gitlab.Messages;
import org.eclipse.fordiac.ide.gitlab.management.GitLabDownloader;
import org.eclipse.fordiac.ide.library.download.DownloadResult.Status;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Preference page that replaces the old single URL/token "GitLab Package
 * Downloader" page.
 *
 * Stores multiple GitLab endpoints (name, URL, token) in preferences using
 * {@link GitLabEndpointsStore}.
 */
public class GitLabEndpointsPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private final List<GitLabEndpoint> endpoints = new ArrayList<>();

	private TableViewer viewer;
	private Button editButton;
	private Button removeButton;
	private Button renameButton;

	public GitLabEndpointsPreferencePage() {
		setTitle(Messages.GitLabEndpointsPreferencePage_enpoints);
		setDescription(Messages.GitLabEndpointsPreferencePage_configure);
	}

	@Override
	public void init(final IWorkbench workbench) {
		// no-op
	}

	@Override
	protected Control createContents(final Composite parent) {
		final Composite root = new Composite(parent, SWT.NONE);
		root.setLayout(new GridLayout(2, false));

		createTable(root);
		createButtons(root);

		endpoints.clear();
		endpoints.addAll(GitLabEndpointsStore.loadEndpoints());
		viewer.setInput(endpoints);
		updateButtons();

		return root;
	}

	private void createTable(final Composite parent) {
		viewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE);
		viewer.setContentProvider(ArrayContentProvider.getInstance());

		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final TableViewerColumn nameCol = new TableViewerColumn(viewer, SWT.NONE);
		nameCol.getColumn().setText(Messages.GitLabEndpointsPreferencePage_name);
		nameCol.getColumn().setWidth(220);
		nameCol.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return element instanceof final GitLabEndpoint ep ? ep.name() : ""; //$NON-NLS-1$
			}
		});

		final TableViewerColumn urlCol = new TableViewerColumn(viewer, SWT.NONE);
		urlCol.getColumn().setText(Messages.GitLabEndpointsPreferencePage_url);
		urlCol.getColumn().setWidth(420);
		urlCol.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return element instanceof final GitLabEndpoint ep ? ep.url() : ""; //$NON-NLS-1$
			}
		});

		viewer.addSelectionChangedListener(e -> updateButtons());
		viewer.getTable().addListener(SWT.MouseDoubleClick, ev -> onEdit());
	}

	private void createButtons(final Composite parent) {
		final Composite buttons = new Composite(parent, SWT.NONE);
		buttons.setLayout(new GridLayout(1, false));
		buttons.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));

		final Button addButton = new Button(buttons, SWT.PUSH);
		addButton.setText(Messages.GitLabEndpointsPreferencePage_add);
		addButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		addButton.addListener(SWT.Selection, ev -> onAdd());

		editButton = new Button(buttons, SWT.PUSH);
		editButton.setText(Messages.GitLabEndpointsPreferencePage_edit);
		editButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		editButton.addListener(SWT.Selection, ev -> onEdit());

		renameButton = new Button(buttons, SWT.PUSH);
		renameButton.setText(Messages.GitLabEndpointsPreferencePage_rename);
		renameButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		renameButton.addListener(SWT.Selection, ev -> onRename());

		removeButton = new Button(buttons, SWT.PUSH);
		removeButton.setText(Messages.GitLabEndpointsPreferencePage_remove);
		removeButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		removeButton.addListener(SWT.Selection, ev -> onRemove());

		final Button testButton = new Button(buttons, SWT.PUSH);
		testButton.setText(Messages.GitLabEndpointsPreferencePage_test_con);
		testButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		testButton.addListener(SWT.Selection, ev -> onTest());
	}

	private void onAdd() {
		final GitLabEndpointDialog dlg = new GitLabEndpointDialog(getShell(), null, usedNames());
		if (dlg.open() == org.eclipse.jface.window.Window.OK) {
			final GitLabEndpoint ep = dlg.getResult();
			endpoints.add(ep);
			viewer.refresh();
			setValid(true);
			updateButtons();
		}
	}

	private void onEdit() {
		final GitLabEndpoint selected = getSelected();
		if (selected == null) {
			return;
		}
		final GitLabEndpointDialog dlg = new GitLabEndpointDialog(getShell(), selected, usedNamesExcluding(selected));
		if (dlg.open() == org.eclipse.jface.window.Window.OK) {
			final GitLabEndpoint updated = dlg.getResult();
			final int idx = endpoints.indexOf(selected);
			if (idx >= 0) {
				endpoints.set(idx, updated);
				viewer.refresh();
			}
		}
	}

	private void onRename() {
		final GitLabEndpoint selected = getSelected();
		if (selected == null) {
			return;
		}
		final InputDialog dlg = new InputDialog(getShell(), Messages.GitLabEndpointsPreferencePage_rename_Ep,
				Messages.GitLabEndpointsPreferencePage_new_name, selected.name(), value -> {
					final String v = Objects.toString(value, "").trim(); //$NON-NLS-1$
					if (v.isBlank()) {
						return Messages.GitLabEndpointsPreferencePage_name_not_empty;
					}
					if (usedNamesExcluding(selected).contains(v)) {
						return Messages.GitLabEndpointsPreferencePage_name_exists;
					}
					return null;
				});
		if (dlg.open() == org.eclipse.jface.window.Window.OK) {
			final String newName = dlg.getValue().trim();
			final int idx = endpoints.indexOf(selected);
			if (idx >= 0) {
				endpoints.set(idx, new GitLabEndpoint(newName, selected.url(), selected.token()));
				viewer.refresh();
			}
		}
	}

	private void onRemove() {
		final GitLabEndpoint selected = getSelected();
		if (selected == null) {
			return;
		}
		final boolean ok = MessageDialog.openConfirm(getShell(), Messages.GitLabEndpointsPreferencePage_remove_ep,
				Messages.GitLabEndpointsPreferencePage_remove_gl_ep + selected.name());
		if (!ok) {
			return;
		}
		endpoints.remove(selected);
		viewer.refresh();
		updateButtons();
	}

	private void onTest() {
		final GitLabEndpoint selected = getSelected();
		if (selected == null) {
			MessageDialog.openInformation(getShell(), Messages.GitLabEndpointsPreferencePage_test_con,
					Messages.GitLabEndpointsPreferencePage_16);
			return;
		}
		testConnection(selected);
	}

	protected void testConnection(final GitLabEndpoint selected) {
		try {
			final GitLabDownloader dl = new GitLabDownloader(selected.token(), selected.url());
			final var res = dl.fetchProjectsAndPackages();
			if (res != null && res.status() == Status.OK) {
				MessageDialog.openInformation(getShell(), Messages.GitLabEndpointsPreferencePage_test_con,
						Messages.GitLabEndpointsPreferencePage_connnection_sucessful);
			} else {
				final String msg = res != null
						? Objects.toString(res.message(), Messages.GitLabEndpointsPreferencePage_connection_failed)
						: Messages.GitLabEndpointsPreferencePage_20;
				MessageDialog.openError(getShell(), Messages.GitLabEndpointsPreferencePage_test_con, msg);
			}
		} catch (final Exception ex) {
			MessageDialog.openError(getShell(), Messages.GitLabEndpointsPreferencePage_test_con, ex.getMessage());
		}
	}

	private void updateButtons() {
		final boolean hasSel = getSelected() != null;
		if (editButton != null && !editButton.isDisposed()) {
			editButton.setEnabled(hasSel);
		}
		if (renameButton != null && !renameButton.isDisposed()) {
			renameButton.setEnabled(hasSel);
		}
		if (removeButton != null && !removeButton.isDisposed()) {
			removeButton.setEnabled(hasSel);
		}
	}

	private GitLabEndpoint getSelected() {
		if (viewer == null || viewer.getControl().isDisposed()) {
			return null;
		}
		final IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
		final Object first = sel.getFirstElement();
		return first instanceof final GitLabEndpoint ep ? ep : null;
	}

	private List<String> usedNames() {
		return endpoints.stream().map(GitLabEndpoint::name).toList();
	}

	private List<String> usedNamesExcluding(final GitLabEndpoint exclude) {
		return endpoints.stream().filter(e -> e != exclude).map(GitLabEndpoint::name).toList();
	}

	@Override
	public boolean performOk() {
		return saveAndReturn(true);
	}

	@Override
	protected void performApply() {
		saveAndReturn(false);
	}

	private boolean saveAndReturn(final boolean returnValueOnSuccess) {
		// validate all
		for (final GitLabEndpoint ep : endpoints) {
			if (ep == null || !ep.isValid()) {
				setErrorMessage(Messages.GitLabEndpointsPreferencePage_all_endpoints);
				return false;
			}
		}
		setErrorMessage(null);
		GitLabEndpointsStore.saveEndpoints(endpoints);
		return returnValueOnSuccess;
	}

	@Override
	protected void performDefaults() {
		endpoints.clear();
		viewer.refresh();
		super.performDefaults();
	}
}
