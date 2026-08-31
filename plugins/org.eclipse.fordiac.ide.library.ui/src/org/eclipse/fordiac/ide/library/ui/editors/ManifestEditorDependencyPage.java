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
 *   Mario Kastner
 *   	- initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.editors;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.LinkedLibrary;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.library.provider.ILibraryProvider;
import org.eclipse.fordiac.ide.library.provider.ILibraryProvider.LibraryDescriptor;
import org.eclipse.fordiac.ide.library.provider.OfflineLibraryProvider;
import org.eclipse.fordiac.ide.library.provider.OnlineLibraryProvider;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.library.ui.wizards.ManageLibraryWizard;
import org.eclipse.fordiac.ide.library.ui.wizards.UnifiedLibraryImportWizard;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

class ManifestEditorDependencyPage extends FormPage {

	private final ILibraryProvider offlineLibraryProvider = new OfflineLibraryProvider();
	private final ILibraryProvider onlineLibraryProvider = new OnlineLibraryProvider();
	private final List<ILibraryProvider> libraryProviders = List.of(offlineLibraryProvider, onlineLibraryProvider);
	private static final Comparator<LibraryDescriptor> VERSION_DESCENDING = Comparator
			.comparing(LibraryDescriptor::version).reversed();
	private TreeViewer treeViewer;

	/**
	 * Maps symbolic names of linked libraries to Version Strings
	 */
	private final Map<String, String> linkedLibVersions = new HashMap<>();

	public ManifestEditorDependencyPage(final FormEditor editor, final String id, final String title) {
		super(editor, id, title);
	}

	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		super.createFormContent(managedForm);
		final ScrolledForm form = managedForm.getForm();

		final Composite root = form.getBody();
		root.setLayout(new GridLayout(1, false));

		final Composite treeContainer = new Composite(root, SWT.NONE);
		treeContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final TreeColumnLayout columnLayout = new TreeColumnLayout();
		treeContainer.setLayout(columnLayout);

		treeViewer = new TreeViewer(treeContainer,
				SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		treeViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		configureColumns(columnLayout);

		// do not compute dependencies
		offlineLibraryProvider.refresh(new NullProgressMonitor(), false);

		collectLinkedLibraryVersions();

		treeViewer.getTree().setHeaderVisible(true);
		treeViewer.setContentProvider(new ITreeContentProvider() {

			@Override
			public boolean hasChildren(final Object element) {
				if (element instanceof final Collection<?> list) {
					return !list.isEmpty();
				}
				if (element instanceof final LibContainer container) {
					return !container.children().isEmpty();
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
				if (inputElement instanceof final LibContainer container) {
					return container.children().toArray();
				}
				return new Object[0];
			}

			@Override
			public Object[] getChildren(final Object parentElement) {
				if (parentElement instanceof final Collection<?> list) {
					return list.toArray();
				}
				if (parentElement instanceof final LibContainer desc) {
					return desc.children().toArray();
				}
				return new Object[0];
			}
		});

		treeViewer.setInput(createViewerInput());
		treeViewer.getTree().setLinesVisible(true);
		treeViewer.expandAll();

		createButtonBar(root, managedForm);

		form.reflow(true);

		refreshLibraries(managedForm);
	}

	public void refresh() {
		if (treeViewer == null || treeViewer.getControl().isDisposed()) {
			return;
		}
		collectLinkedLibraryVersions();
		treeViewer.setInput(createViewerInput());
		treeViewer.expandAll();
	}

	public void reveal(final Required required) {
		if (treeViewer == null || treeViewer.getTree().isDisposed()) {
			return;
		}

		treeViewer.setSelection(new StructuredSelection(required), true);
		treeViewer.getTree().setFocus();
	}

	private void createButtonBar(final Composite parent, final IManagedForm form) {
		final Composite buttonBar = new Composite(parent, SWT.NONE);
		buttonBar.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));

		final GridLayout layout = new GridLayout(3, false);
		buttonBar.setLayout(layout);

		final Button refreshButton = new Button(buttonBar, SWT.PUSH);
		refreshButton.setText(Messages.ManifestEditor_RefreshLibraries);
		refreshButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		refreshButton.addListener(SWT.Selection, _ -> refreshLibraries(form));

		final Button manageLibrariesButton = new Button(buttonBar, SWT.PUSH);
		manageLibrariesButton.setText(Messages.ManageLibraryWizard_Label);
		manageLibrariesButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		manageLibrariesButton.addListener(SWT.Selection, _ -> ManageLibraryWizard
				.openDialog(getManifestEditor().getProject(), getEditor().getSite().getShell()));

		final Button importLibrariesButton = new Button(buttonBar, SWT.PUSH);
		importLibrariesButton.setText(Messages.ManifestEditor_ImportLibraries);
		importLibrariesButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		importLibrariesButton.addListener(SWT.Selection, _ -> UnifiedLibraryImportWizard
				.openDialog(getManifestEditor().getProject(), getEditor().getSite().getShell()));
	}

	private List<LibContainer> createViewerInput() {
		final var manifest = getManifestEditor().getManifest();
		if (manifest == null || manifest.getDependencies() == null) {
			return Collections.emptyList();
		}
		final Map<Boolean, List<Required>> libraries = manifest.getDependencies().getRequired().stream()
				.collect(Collectors.partitioningBy(r -> isStandardLib(r.getSymbolicName())));

		return List.of(new LibContainer(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME, libraries.get(Boolean.TRUE)),
				new LibContainer(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME, libraries.get(Boolean.FALSE)));
	}

	private static boolean isStandardLib(final String symbolicName) {
		return LibraryManager.INSTANCE.getStandardLibraries().containsKey(symbolicName);
	}

	private void collectLinkedLibraryVersions() {
		linkedLibVersions.clear();
		final IProject project = getManifestEditor().getProject();
		if (project == null) {
			return;
		}
		try {
			LinkedLibrary.getAll(project, null)
					.forEach(lib -> linkedLibVersions.putIfAbsent(lib.getSymbolicName(), lib.getVersion().toString()));
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}

	}

	private ManifestEditor getManifestEditor() {
		return (ManifestEditor) getEditor();
	}

	private String getUsedVersion(final String symbolicName) {
		return linkedLibVersions.getOrDefault(symbolicName, ""); //$NON-NLS-1$
	}

	private void configureColumns(final TreeColumnLayout layout) {
		// symbolic name column
		final TreeViewerColumn symbolicNameColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		symbolicNameColumn.getColumn().setText(Messages.ManifestEditor_Column_SymbolicName);
		symbolicNameColumn.setLabelProvider(createLabelProvider(Required::getSymbolicName, cell -> {
			if (cell.getElement() instanceof LibContainer(final String name, final List<Required> children)
					&& !children.isEmpty()) {
				cell.setText(MessageFormat.format("{0} ({1})", name, Integer.valueOf(children.size()))); //$NON-NLS-1$
			}
		}, false));

		// version range column
		final TreeViewerColumn versionRangeColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		versionRangeColumn.getColumn().setText(Messages.ManifestEditor_Column_VersionRange);
		versionRangeColumn.setLabelProvider(createLabelProvider(Required::getVersion, null, true));
		versionRangeColumn.setEditingSupport(new EditingSupport(treeViewer) {
			private final CellEditor editor = new VersionRangeCellEditor(treeViewer.getTree());

			@Override
			protected void setValue(final Object element, final Object value) {
				if (element instanceof final Required required) {

					final String version = value.toString();
					if (version.equals(required.getVersion())) {
						return;
					}
					required.setVersion(version);
					getViewer().refresh(required);
					getViewer().setSelection(StructuredSelection.EMPTY);
					getManifestEditor().setDirty(true);
				}
			}

			@Override
			protected Object getValue(final Object element) {
				if (element instanceof final Required req) {
					return req.getVersion();
				}
				return ""; //$NON-NLS-1$
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return editor;
			}

			@Override
			protected boolean canEdit(final Object element) {
				return element instanceof Required;
			}
		});

		// active version column
		final TreeViewerColumn activeVersionColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		activeVersionColumn.getColumn().setText(Messages.ManifestEditor_Column_Used);
		activeVersionColumn
				.setLabelProvider(createLabelProvider(req -> getUsedVersion(req.getSymbolicName()), null, false));

		// latest version in range column
		final TreeViewerColumn latestInRangeColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		latestInRangeColumn.getColumn().setText(Messages.ManifestEditor_Column_LatestInRange);
		latestInRangeColumn.setLabelProvider(createLabelProvider(req -> {
			if (!VersionComparator.isValidRange(req.getVersion())) {
				return ""; //$NON-NLS-1$
			}
			final org.osgi.framework.VersionRange range = VersionComparator.parseVersionRange(req.getVersion());
			return libraryProviders.stream().map(provider -> provider.getLatest(req.getSymbolicName(), range))
					.flatMap(Optional::stream).sorted(VERSION_DESCENDING).map(lib -> lib.version().toString())
					.findFirst().orElse(""); //$NON-NLS-1$
		}, null, false));

		// latest overall version column
		final TreeViewerColumn latestColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		latestColumn.getColumn().setText(Messages.ManifestEditor_Column_Latest);
		latestColumn.setLabelProvider(createLabelProvider(req -> libraryProviders.stream()
				.map(provider -> provider.getLatest(req.getSymbolicName())).flatMap(Optional::stream)
				.sorted(VERSION_DESCENDING).map(lib -> lib.version().toString()).findFirst().orElse(""), null, false) //$NON-NLS-1$
		);

		// define column width ratios
		layout.setColumnData(symbolicNameColumn.getColumn(), new ColumnWeightData(40));
		layout.setColumnData(versionRangeColumn.getColumn(), new ColumnWeightData(30));
		layout.setColumnData(activeVersionColumn.getColumn(), new ColumnWeightData(20));
		layout.setColumnData(latestInRangeColumn.getColumn(), new ColumnWeightData(20));
		layout.setColumnData(latestColumn.getColumn(), new ColumnWeightData(20));
	}

	private void refreshLibraries(final IManagedForm form) {
		form.getForm().setMessage(Messages.ManifestEditor_RefreshLibraries + " ...", //$NON-NLS-1$
				IMessageProvider.INFORMATION);
		final Job job = new Job(Messages.ManageLibraryWizard_LoadRemoteVersions) {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				final IStatus status = onlineLibraryProvider.refresh(monitor, false);

				Display.getDefault().asyncExec(() -> {
					if (treeViewer == null || treeViewer.getTree().isDisposed()) {
						return;
					}

					if (status.isOK()) {
						form.getForm().setMessage("", IMessageProvider.NONE); //$NON-NLS-1$
					} else {
						final String combinedMessage = status.isMultiStatus() ? Arrays.stream(status.getChildren())
								.map(IStatus::getMessage).collect(Collectors.joining(System.lineSeparator()))
								: status.getMessage();
						form.getForm().setMessage(combinedMessage, IMessageProvider.ERROR);
					}

					treeViewer.refresh();
				});
				return Status.OK_STATUS;
			}
		};

		job.setUser(false);
		job.schedule();
	}

	private static CellLabelProvider createLabelProvider(final Function<Required, String> textProvider,
			final Consumer<ViewerCell> containerUpdater, final boolean editable) {

		return new StyledCellLabelProvider() {

			@Override
			public void update(final ViewerCell cell) {
				if (cell.getElement() instanceof final Required required) {
					updateRequiredCell(cell, required);
				} else {
					clearRequiredStyle(cell);
					cell.setText(""); //$NON-NLS-1$

					if (containerUpdater != null) {
						containerUpdater.accept(cell);
					}
				}

				super.update(cell);
			}

			private void updateRequiredCell(final ViewerCell cell, final Required required) {
				clearRequiredStyle(cell);

				final String text = textProvider.apply(required);
				if (!editable) {
					final StyledString styledText = new StyledString(text, StyledString.QUALIFIER_STYLER);
					cell.setText(styledText.getString());
					cell.setStyleRanges(styledText.getStyleRanges());
				} else {
					cell.setText(text);
				}

				if (!VersionComparator.isValidRange(required.getVersion())) {
					final Display display = cell.getControl().getDisplay();
					cell.setBackground(display.getSystemColor(SWT.COLOR_INFO_BACKGROUND));
					cell.setForeground(display.getSystemColor(SWT.COLOR_RED));
					cell.setStyleRanges(null);
				}
			}

			private static void clearRequiredStyle(final ViewerCell cell) {
				cell.setBackground(null);
				cell.setForeground(null);
				cell.setStyleRanges(null);
			}
		};
	}

	record LibContainer(String name, List<Required> children) {
		LibContainer(final String name) {
			this(name, new ArrayList<>());
		}
	}

}
