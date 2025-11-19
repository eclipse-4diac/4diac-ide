/*******************************************************************************
 * Copyright (c) 2024, 2025  Primetals Technologies Austria GmbH
 *                           Monika Wenger
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.LibraryRecord;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

public class LibrarySelectionPage extends WizardPage {
	private final List<LibraryRecord> libraries = new ArrayList<>();
	private CheckboxTreeViewer treeViewer;
	private Button libraryNameSort;
	private final boolean showStandard;
	private final boolean showWorkspace;
	private VersionRange range;
	private SelectionAdapter listener;

	public LibrarySelectionPage(final String pageName, final boolean alwaysComplete, final boolean showStandard,
			final boolean showWorkspace) {
		super(pageName);
		setPageComplete(alwaysComplete);
		this.showStandard = showStandard;
		this.showWorkspace = showWorkspace;
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(1, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createTreeViewer(composite);

		final Composite compositeConfiguring = new Composite(composite, SWT.NULL);
		compositeConfiguring.setLayout(new GridLayout(2, true));
		compositeConfiguring.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createTreeGroupConfiguring(compositeConfiguring);
		createColumnConfiguring(compositeConfiguring);

		findLibs();
		fillViewer(true);
		selectRange();

		// Show description on opening
		setErrorMessage(null);
		setMessage(null);
		setControl(composite);
		Dialog.applyDialogFont(composite);
	}

	private void createColumnConfiguring(final Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setText(Messages.LibraryPage_Columns);
		group.setLayout(new RowLayout(SWT.HORIZONTAL));

		final Button showPath = new Button(group, SWT.CHECK);
		showPath.setText(Messages.LibraryPage_Path);
		showPath.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final Button button = (Button) e.widget;
				final TreeColumn[] columns = treeViewer.getTree().getColumns();
				if (button.getSelection()) {
					createPathColumn();
					treeViewer.refresh();
				} else {
					columns[3].dispose();
				}
			}
		});
	}

	private void createTreeViewer(final Composite parent) {
		final Composite tableComposite = new Composite(parent, SWT.NONE);
		tableComposite.setLayout(new GridLayout(1, true));
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		treeViewer = new CheckboxTreeViewer(tableComposite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		final Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tree.setHeaderVisible(true);

		tableComposite.layout();

		treeViewer.setContentProvider(new ITreeContentProvider() {
			@Override
			public Object[] getElements(final Object inputElement) {
				if (inputElement instanceof Collection) {
					return ((Collection<?>) inputElement).toArray();
				}
				return new Object[0];
			}

			@Override
			public Object[] getChildren(final Object parentElement) {
				if (libraryNameSort.getSelection()) {
					if (parentElement instanceof final String name) {
						return libraries.stream().filter(lib -> lib.name().equals(name))
								.sorted(Comparator.comparing(LibraryRecord::version).reversed()).toArray();
					}
				} else if (parentElement instanceof final String version) {
					return libraries.stream().filter(lib -> lib.version().toString().equals(version))
							.sorted(Comparator.comparing(LibraryRecord::name)).toArray();
				}
				return new Object[0];
			}

			@Override
			public Object getParent(final Object element) {
				if (element instanceof final LibraryRecord lib) {
					if (libraryNameSort.getSelection()) {
						return lib.name();
					}
					return lib.version().toString();
				}
				return treeViewer.getTree();
			}

			@Override
			public boolean hasChildren(final Object element) {
				return element instanceof String;
			}
		});
	}

	private void createTreeGroupConfiguring(final Composite parent) {
		final Group group = new Group(parent, SWT.NONE);
		group.setText(Messages.LibraryPage_Sorting);
		group.setLayout(new RowLayout(SWT.HORIZONTAL));

		libraryNameSort = new Button(group, SWT.RADIO);
		libraryNameSort.setText(Messages.LibraryPage_Name);
		libraryNameSort.setSelection(true);

		final Button versionSort = new Button(group, SWT.RADIO);
		versionSort.setText(Messages.LibraryPage_Version);

		final SelectionListener sortListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final Button button = (Button) e.widget;
				if (button.getSelection()) {
					fillViewer(button == libraryNameSort);
				}
			}
		};
		libraryNameSort.addSelectionListener(sortListener);
		versionSort.addSelectionListener(sortListener);
	}

	@Override
	public boolean isPageComplete() {
		return super.isPageComplete() || treeViewer.getCheckedElements().length > 0;
	}

	private void findLibs() {
		libraries.clear();
		if (showStandard) {
			LibraryManager.INSTANCE.getStandardLibraries()
					.forEach((symbolicName, reclist) -> reclist.forEach(libraries::add));
		}
		if (showWorkspace) {
			LibraryManager.INSTANCE.getExtractedLibraries()
					.forEach((symbolicName, reclist) -> reclist.forEach(libraries::add));
		}
	}

	public Map<Required, URI> getChosenLibraries() {
		final Map<Required, URI> libs = new HashMap<>();
		Stream.of(treeViewer.getCheckedElements()).filter(LibraryRecord.class::isInstance)
				.map(LibraryRecord.class::cast).forEach(lib -> libs
						.put(ManifestHelper.createRequired(lib.symbolicName(), lib.version().toString()), lib.uri()));
		return libs;
	}

	public void setStandardLibRange(final VersionRange range) {
		this.range = range;
		selectRange();
	}

	private void selectRange() {
		if (range == null || treeViewer == null || libraries.isEmpty()) {
			return;
		}
		treeViewer.setCheckedElements(
				libraries.stream().filter(l -> range.includes(l.version())).toArray(LibraryRecord[]::new));
		treeViewer.refresh();
	}

	private void fillViewer(final boolean isLibrary) {
		final Object[] tempSelection = treeViewer.getCheckedElements();
		createColumns(isLibrary);
		if (null != listener) {
			treeViewer.getTree().removeSelectionListener(listener);
		}
		if (isLibrary) {
			treeViewer.setInput(libraries.stream().map(LibraryRecord::name).distinct().sorted().toList());
			treeViewer.setCheckedElements(tempSelection);
			treeViewer.setCheckStateProvider(getLibraryNameCheckStateProvider());
			treeViewer.getTree().addSelectionListener(getLibraryNameListener());
		} else {
			treeViewer.setInput(libraries.stream().map(LibraryRecord::version).distinct()
					.sorted(Comparator.reverseOrder()).map(Version::toString).toList());
			treeViewer.setCheckedElements(tempSelection);
			if (range != null) {
				final String version = range.getLeft().toString();
				final Object[] children = ((ITreeContentProvider) treeViewer.getContentProvider()).getChildren(version);
				if (Arrays.stream(children).allMatch(child -> treeViewer.getChecked(child))) {
					treeViewer.setChecked(version, true);
				}
			}
			treeViewer.setCheckStateProvider(null);
			treeViewer.getTree().addSelectionListener(getVersionListener());
		}
		treeViewer.refresh();
	}

	private SelectionAdapter getLibraryNameListener() {
		listener = new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				if (event.detail == SWT.CHECK && event.item instanceof final TreeItem item
						&& item.getParentItem() instanceof final TreeItem parent) {
					treeViewer.getTree().removeSelectionListener(listener);
					final boolean checked = item.getChecked();
					if (checked) {
						Stream.of(parent.getItems()).filter(i -> i != item).forEach(c -> c.setChecked(false));
					}
					treeViewer.getTree().addSelectionListener(listener);
				}
			}
		};
		return listener;
	}

	private ICheckStateProvider getLibraryNameCheckStateProvider() {
		return new ICheckStateProvider() {
			@Override
			public boolean isChecked(final Object element) {
				if (element instanceof String) {
					return false;
				}
				return treeViewer.getChecked(element);
			}

			@Override
			public boolean isGrayed(final Object element) {
				return element instanceof String;
			}
		};
	}

	private SelectionAdapter getVersionListener() {
		listener = new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				if (event.detail == SWT.CHECK && event.item instanceof final TreeItem item) {
					treeViewer.getTree().removeSelectionListener(listener);
					final boolean checked = item.getChecked();
					final TreeItem[] children = item.getItems();
					if (children.length > 0) {
						checkParentForVersionListener(item, checked, children);
					} else {
						checkChildForVersionListener(item);
					}
					treeViewer.refresh();
					setPageComplete(isPageComplete());
					treeViewer.getTree().addSelectionListener(listener);
				}
			}

			private void checkChildForVersionListener(final TreeItem item) {
				final TreeItem parent = item.getParentItem();
				parent.setChecked(Stream.of(parent.getItems()).allMatch(TreeItem::getChecked));
				Stream.of(item.getParent().getItems()).filter(i -> i != parent)
						.flatMap(e -> Arrays.stream(e.getItems()))
						.filter(c -> c.getData() != null
								&& ((LibraryRecord) c.getData()).name().equals(((LibraryRecord) item.getData()).name()))
						.forEach(d -> d.setChecked(false));
			}

			private void checkParentForVersionListener(final TreeItem item, final boolean checked,
					final TreeItem[] children) {
				Stream.of(children).forEach(c -> c.setChecked(checked));
				final Set<String> childrenNames = Stream.of(children).map(TreeItem::getData)
						.map(LibraryRecord.class::cast).filter(Objects::nonNull).map(LibraryRecord::name)
						.collect(Collectors.toSet());
				Stream.of(item.getParent().getItems()).filter(i -> i != item && i.getChecked()).forEach(p -> {
					for (final TreeItem child : p.getItems()) {
						final LibraryRecord childItem = (LibraryRecord) child.getData();
						if (childrenNames.contains(childItem.name())) {
							child.setChecked(false);
						}
					}
					p.setChecked(false);
				});
			}
		};
		return listener;
	}

	private void createColumns(final boolean isLibrary) {
		Stream.of(treeViewer.getTree().getColumns()).forEach(TreeColumn::dispose);

		final TreeViewerColumn col1 = new TreeViewerColumn(treeViewer, SWT.NONE);
		final TreeViewerColumn col2 = new TreeViewerColumn(treeViewer, SWT.NONE);
		final TreeViewerColumn col3 = new TreeViewerColumn(treeViewer, SWT.NONE);

		if (isLibrary) {
			createLibraryFirstColumn(col1, col2);
		} else {
			createVersionFirstColumn(col1, col2);
		}

		col3.getColumn().setText(Messages.LibraryPage_Comment);
		col3.getColumn().setWidth(200);
		col3.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final LibraryRecord libdisplay) {
					return libdisplay.comment();
				}
				return ""; //$NON-NLS-1$
			}
		});
	}

	private void createPathColumn() {
		final TreeViewerColumn col4 = new TreeViewerColumn(treeViewer, SWT.NONE);
		col4.getColumn().setText(Messages.LibraryPage_Path);
		col4.getColumn().setWidth(200);
		col4.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final LibraryRecord libdisplay) {
					return libdisplay.path().toString();
				}
				return ""; //$NON-NLS-1$
			}
		});
	}

	private static void createVersionFirstColumn(final TreeViewerColumn col1, final TreeViewerColumn col2) {
		col1.getColumn().setText(Messages.LibraryPage_Version);
		col1.getColumn().setWidth(100);
		col1.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final String elem) {
					return elem;
				}
				return ""; //$NON-NLS-1$
			}
		});

		col2.getColumn().setText(Messages.LibraryPage_Name);
		col2.getColumn().setWidth(150);
		col2.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final LibraryRecord libdisplay) {
					return libdisplay.name();
				}
				return ""; //$NON-NLS-1$
			}
		});
	}

	private static void createLibraryFirstColumn(final TreeViewerColumn col1, final TreeViewerColumn col2) {
		col1.getColumn().setText(Messages.LibraryPage_Name);
		col1.getColumn().setWidth(150);
		col1.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final String elem) {
					return elem;
				}
				return ""; //$NON-NLS-1$
			}

			@Override
			public void update(final ViewerCell cell) {
				final Object element = cell.getElement();
				if (element instanceof final String item) {
					cell.setImage(null);
					cell.setText(item);
				}
			}
		});

		col2.getColumn().setText(Messages.LibraryPage_Version);
		col2.getColumn().setWidth(100);
		col2.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final LibraryRecord libdisplay) {
					return libdisplay.version().toString();
				}
				return ""; //$NON-NLS-1$
			}
		});
	}
}
