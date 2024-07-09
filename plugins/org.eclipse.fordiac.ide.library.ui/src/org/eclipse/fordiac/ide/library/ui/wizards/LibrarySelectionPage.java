/*******************************************************************************
 * Copyright (c) 2024  Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.wizards;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.library.LibraryUtil;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class LibrarySelectionPage extends WizardPage {
	private List<LibDisplay> libraries;
	private Map<String, List<LibDisplay>> libGroupings;
	private CheckboxTableViewer tableViewer;
	private Button selectAllButton;
	private Button deselectButton;
	private boolean showStandard;
	private boolean showWorkspace;
	private final boolean selectAll;
	private final VersionComparator versionComparator;

	public LibrarySelectionPage(final String pageName, final boolean alwaysComplete, final boolean selectAll,
			final boolean showStandard, final boolean showWorkspace) {
		super(pageName);
		setPageComplete(alwaysComplete);
		this.showStandard = showStandard;
		this.showWorkspace = showWorkspace;
		this.selectAll = selectAll;
		versionComparator = new VersionComparator();
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NULL);

		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		findLibs();
		if (selectAll) {
			selectAll();
		}

		final Composite tableComposite = new Composite(composite, SWT.NONE);
		tableComposite.setLayout(new GridLayout(1, true));
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		tableViewer = CheckboxTableViewer.newCheckList(tableComposite, SWT.NONE);
		configureTableViewer(tableViewer);
		tableViewer.setInput(libraries);

		configureSelectionListener();

		final Composite buttonComposite = new Composite(composite, SWT.NONE);
		buttonComposite.setFont(parent.getFont());
		buttonComposite.setLayout(new GridLayout(1, false));
		buttonComposite.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));

		createButtons(buttonComposite);

		buttonComposite.layout();
		tableComposite.layout();

		// Show description on opening
		setErrorMessage(null);
		setMessage(null);
		setControl(composite);
		Dialog.applyDialogFont(composite);
	}

	@Override
	public boolean isPageComplete() {
		return super.isPageComplete() || libraries.stream().anyMatch(LibDisplay::isSelected);
	}

	private void configureSelectionListener() {
		tableViewer.getTable().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				setPageComplete(isPageComplete());
			}
		});
	}

	private void findLibs() {
		libraries = new ArrayList<>();
		if (showStandard) {
			libraries
					.addAll(LibraryUtil.getStandardLibraries(TypeLibraryManager.getToolLibProject()).entrySet().stream()
							.map(entry -> new LibDisplay(false, entry.getKey().getProduct().getName(),
									entry.getKey().getProduct().getSymbolicName(),
									entry.getKey().getProduct().getVersionInfo().getVersion(),
									entry.getKey().getProduct().getComment(), entry.getValue()))
							.toList());
		}
		if (showWorkspace) {
			libraries.addAll(
					LibraryUtil.getWorkspaceLibraries(TypeLibraryManager.getToolLibProject()).entrySet().stream()
							.map(entry -> new LibDisplay(false, entry.getKey().getProduct().getName(),
									entry.getKey().getProduct().getSymbolicName(),
									entry.getKey().getProduct().getVersionInfo().getVersion(),
									entry.getKey().getProduct().getComment(), entry.getValue()))
							.toList());
		}
		libGroupings = libraries.stream().collect(Collectors.groupingBy(lib -> lib.symbolicName));
	}

	public Map<Required, URI> getChosenLibraries() {
		final Map<Required, URI> libs = new HashMap<>();
		libraries.stream().filter(LibDisplay::isSelected).forEach(
				lib -> libs.put(ManifestHelper.createRequired(lib.getSymbolicName(), lib.getVersion()), lib.getUri()));
		return libs;
	}

	private void selectAll() {
		deselectAll();
		final VersionComparator comparator = new VersionComparator();
		libGroupings.forEach((symb, list) -> {
			list.stream().max((l1, l2) -> comparator.compare(l1.getVersion(), l2.getVersion()))
					.ifPresent(lib -> lib.setSelected(true));
		});
	}

	private void deselectAll() {
		libraries.forEach(lib -> lib.setSelected(false));
	}

	private void selectLib(final LibDisplay lib, final boolean selected) {
		if (!lib.isSelected()) {
			libGroupings.get(lib.getSymbolicName()).forEach(l -> l.setSelected(false));
		}
		lib.setSelected(selected);
	}

	private void configureTableViewer(final CheckboxTableViewer viewer) {
		viewer.setContentProvider(new ArrayContentProvider());

		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayout(createTableLayout());
		table.setSortDirection(SWT.DOWN);

		final TableViewerColumn nameColumn = new TableViewerColumn(viewer, SWT.LEAD);
		nameColumn.getColumn().setText(Messages.LibraryPage_Name);
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((LibDisplay) element).getName();
			}
		});

		final TableViewerColumn versionColumn = new TableViewerColumn(viewer, SWT.LEAD);
		versionColumn.getColumn().setText(Messages.LibraryPage_Version);
		versionColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((LibDisplay) element).getVersion();
			}
		});

		final TableViewerColumn commentColumn = new TableViewerColumn(viewer, SWT.LEAD);
		commentColumn.getColumn().setText(Messages.LibraryPage_Comment);
		commentColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((LibDisplay) element).getComment();
			}
		});

		final TableViewerColumn symNameColumn = new TableViewerColumn(viewer, SWT.LEAD);
		symNameColumn.getColumn().setText(Messages.LibraryPage_SymbolicName);
		symNameColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((LibDisplay) element).getSymbolicName();
			}
		});

		viewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(final Viewer viewer, final Object e1, final Object e2) {
				final LibDisplay lib1 = (LibDisplay) e1;
				final LibDisplay lib2 = (LibDisplay) e2;
				int comp = lib1.getSymbolicName().compareTo(lib2.getSymbolicName());
				if (comp == 0) {
					comp = versionComparator.compare(lib1.getVersion(), lib2.getVersion());
				}
				return comp;
			}
		});

		viewer.setCheckStateProvider(new ICheckStateProvider() {

			@Override
			public boolean isGrayed(final Object element) {
				return false;
			}

			@Override
			public boolean isChecked(final Object element) {
				return ((LibDisplay) element).isSelected();
			}
		});

		viewer.addCheckStateListener(event -> {
			final LibDisplay lib = (LibDisplay) event.getElement();
			selectLib(lib, event.getChecked());
			viewer.update(libGroupings.get(lib.getSymbolicName()).toArray(), null);
		});
	}

	@SuppressWarnings("static-method")
	private TableLayout createTableLayout() {
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(100));
		layout.addColumnData(new ColumnPixelData(50));
		layout.addColumnData(new ColumnPixelData(200));
		layout.addColumnData(new ColumnPixelData(100));
		return layout;
	}

	private void createButtons(final Composite buttonComposite) {

		selectAllButton = new Button(buttonComposite, SWT.PUSH);
		selectAllButton.setFont(buttonComposite.getFont());
		selectAllButton.setText(Messages.LibraryPage_SelectAll);
		setButtonLayoutData(selectAllButton);

		deselectButton = new Button(buttonComposite, SWT.PUSH);
		deselectButton.setFont(buttonComposite.getFont());
		deselectButton.setText(Messages.LibraryPage_DeselectAll);
		setButtonLayoutData(deselectButton);

		selectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				selectAll();
				tableViewer.refresh();
			}
		});

		deselectButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				deselectAll();
				tableViewer.refresh();
			}
		});
	}

	private class LibDisplay {
		private boolean selected;
		private final String name;
		private final String symbolicName;
		private final String version;
		private final String comment;
		private final URI uri;

		public LibDisplay(final boolean selected, final String name, final String symbolicName, final String version,
				final String comment, final URI uri) {
			this.selected = selected;
			this.name = name;
			this.symbolicName = symbolicName;
			this.version = version;
			this.comment = comment;
			this.uri = uri;
		}

		public void setSelected(final boolean selected) {
			this.selected = selected;
		}

		public boolean isSelected() {
			return selected;
		}

		public String getName() {
			return name;
		}

		public String getSymbolicName() {
			return symbolicName;
		}

		public String getVersion() {
			return version;
		}

		public String getComment() {
			return comment;
		}

		public URI getUri() {
			return uri;
		}
	}

	public boolean isShowStandard() {
		return showStandard;
	}

	public void setShowStandard(final boolean showStandard) {
		this.showStandard = showStandard;
		findLibs();
		tableViewer.setInput(libraries);
	}

	public boolean isShowWorkspace() {
		return showWorkspace;
	}

	public void setShowWorkspace(final boolean showWorkspace) {
		this.showWorkspace = showWorkspace;
		findLibs();
		tableViewer.setInput(libraries);
	}

}
