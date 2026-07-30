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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.library.LibraryChange;
import org.eclipse.fordiac.ide.library.LibraryChange.ChangeType;
import org.eclipse.fordiac.ide.library.LibraryResolver;
import org.eclipse.fordiac.ide.library.LibraryResolver.ResolveResult;
import org.eclipse.fordiac.ide.library.LinkedLibrary;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.library.provider.ILibraryProvider;
import org.eclipse.fordiac.ide.library.provider.ILibraryProvider.LibraryDescriptor;
import org.eclipse.fordiac.ide.library.provider.OfflineLibraryProvider;
import org.eclipse.fordiac.ide.library.provider.OnlineLibraryProvider;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.IMessageProvider;
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
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

public class LibraryPlanningPage extends WizardPage {

	private TreeViewer treeViewer;

	private final ILibraryProvider offlineLibraryProvider;
	private final ILibraryProvider onlineLibraryProvider;

	private List<LibContainer> input;
	private final IProject project;
	private StyledText detailsText;
	private ResolveResult resolveResult;

	protected LibraryPlanningPage(final String pageName, final IProject project) {
		super(pageName);
		setTitle(Messages.ManageLibraryWizard_PlannigPage_Titel);
		this.offlineLibraryProvider = new OfflineLibraryProvider();
		this.onlineLibraryProvider = new OnlineLibraryProvider();
		this.project = project;
	}

	public List<LibraryChange> getChanges() {
		return getAllChanges().filter(change -> change.getType() != ChangeType.NOP).toList();
	}

	public ResolveResult getResolveResult() {
		return resolveResult;
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

		configureColumns(columnLayout);

		offlineLibraryProvider.refresh(new NullProgressMonitor(), true);

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

		createValidationInfo(root);

		input = createViewerInput();
		treeViewer.setInput(input);
		treeViewer.getTree().setLinesVisible(true);
		treeViewer.expandAll();

		setControl(root);
		setPageComplete(false);

		root.layout();

		startRemoteVersionLookupJob();

	}

	private void createValidationInfo(final Composite container) {
		final Composite detailsBox = new Composite(container, SWT.BORDER);
		detailsBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		detailsBox.setLayout(new GridLayout(1, false));

		final Label detailsLabel = new Label(detailsBox, SWT.NONE);
		detailsLabel.setText("Problems"); //$NON-NLS-1$

		detailsText = new StyledText(detailsBox, SWT.V_SCROLL | SWT.READ_ONLY | SWT.WRAP);
		final GridData detailsData = new GridData(SWT.FILL, SWT.FILL, true, true);
		detailsData.heightHint = 90;
		detailsText.setLayoutData(detailsData);
	}

	private IStatus validateChanges() {
		final Set<String> excluded = getAllChanges().filter(n -> n.getType() == ChangeType.REMOVE)
				.map(LibraryChange::getSymbolicName).collect(Collectors.toSet());
		final Set<LibraryDescriptor> included = getPlannedLinkedLibraries().stream().collect(Collectors.toSet());

		resolveResult = LibraryResolver.resolveDependencies(getProjectDependencies(), getAvailableLibraries(), included,
				excluded);
		detailsText.setText(resolveResult.getMessage());
		final GridData gd = (GridData) detailsText.getLayoutData();
		gd.heightHint = Math.min(90, detailsText.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		detailsText.getParent().layout();
		return resolveResult.status();
	}

	private Map<String, List<LibraryDescriptor>> getAvailableLibraries() {
		final Map<String, List<LibraryDescriptor>> availableLibraries = new HashMap<>();
		offlineLibraryProvider.getAll().forEach((symbolicName, libraries) -> availableLibraries
				.computeIfAbsent(symbolicName, k -> new ArrayList<>()).addAll(libraries));
		onlineLibraryProvider.getAll().forEach((symbolicName, libraries) -> availableLibraries
				.computeIfAbsent(symbolicName, k -> new ArrayList<>()).addAll(libraries));
		availableLibraries.replaceAll((k, v) -> v.stream().distinct().toList());
		return availableLibraries;
	}

	private Map<String, VersionRange> getProjectDependencies() {
		final Map<String, VersionRange> dependencies = new HashMap<>();
		final Manifest man = ManifestHelper.getOrCreateProjectManifest(project);

		if (man == null || man.getDependencies() == null) {
			return Map.of();
		}

		man.getDependencies().getRequired().forEach(
				req -> dependencies.put(req.getSymbolicName(), VersionComparator.parseVersionRange(req.getVersion())));

		return dependencies;
	}

	public List<LibraryDescriptor> getPlannedLinkedLibraries() {
		return getAllChanges().filter(n -> n.getType() != ChangeType.REMOVE).map(n -> {
			final Version version = switch (n.getType()) {
			case UPDATE, DOWNGRADE -> new Version(n.getTargetVersion());
			default -> new Version(n.getCurrentVersion());
			};

			return offlineLibraryProvider.getLibrary(n.getSymbolicName(), version)
					.orElse(onlineLibraryProvider.getLibrary(n.getSymbolicName(), version)
							.orElse(new LibraryDescriptor(n.getSymbolicName(), version, Map.of())));
		}).toList();
	}

	private List<LibContainer> createViewerInput() {
		final LibContainer stdLibNode = new LibContainer(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME);
		final LibContainer extLibNode = new LibContainer(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);

		try {
			LinkedLibrary.getExternal(project, new NullProgressMonitor()).forEach(folder -> extLibNode.children()
					.add(LibraryChange.createEmpty(folder.getSymbolicName(), folder.getVersion().toString())));

			LinkedLibrary.getStandard(project, new NullProgressMonitor()).forEach(folder -> stdLibNode.children()
					.add(LibraryChange.createEmpty(folder.getSymbolicName(), folder.getVersion().toString())));
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}

		return List.of(extLibNode, stdLibNode);
	}

	private void configureColumns(final TreeColumnLayout layout) {
		final TreeViewerColumn symbolicNameColumn = createColumn(Messages.ManageLibraryWizard_SymbolicName,
				new LibraryChangeLabelProvider(LibraryChange::getSymbolicName, false));
		layout.setColumnData(symbolicNameColumn.getColumn(), new ColumnWeightData(40));

		final TreeViewerColumn activeVersionColumn = createColumn(Messages.ManageLibraryWizard_CurrentVersion,
				new LibraryChangeLabelProvider(LibraryChange::getCurrentVersion, false));
		layout.setColumnData(activeVersionColumn.getColumn(), new ColumnWeightData(20));

		final TreeViewerColumn changeSelectionColumn = createColumn(Messages.ManageLibraryWizard_Change,
				new LibraryChangeLabelProvider(LibraryChange::getText, true));
		layout.setColumnData(changeSelectionColumn.getColumn(), new ColumnWeightData(20));

		changeSelectionColumn.setEditingSupport(new EditingSupport(treeViewer) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (element instanceof final LibraryChange currentChange && value instanceof final Integer i) {
					final List<LibraryChange> changes = getAvailableChanges(currentChange);
					if (i.intValue() >= 0 && i.intValue() < changes.size()) {
						final var newChange = changes.get(i.intValue());
						currentChange.setTargetVersion(newChange.getTargetVersion());
						currentChange.setType(newChange.getType());
						treeViewer.update(element, null);
						checkPageComplete();
					}
				}
			}

			@Override
			protected Object getValue(final Object element) {
				if (element instanceof final LibraryChange rec) {
					return Integer.valueOf(getAvailableChanges(rec).indexOf(rec));
				}
				return Integer.valueOf(0);
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				if (element instanceof final LibraryChange rec) {
					final String[] changes = getAvailableChanges(rec).stream().map(LibraryChange::getText).toList()
							.toArray(new String[0]);
					return new ComboBoxCellEditor(treeViewer.getTree(), changes, SWT.READ_ONLY);
				}
				return new ComboBoxCellEditor(treeViewer.getTree(), new String[0], SWT.READ_ONLY);
			}

			@Override
			protected boolean canEdit(final Object element) {
				return element instanceof LibraryChange;
			}

			private List<String> getAvailableVersions(final String symbolicName) {
				return Stream
						.concat(offlineLibraryProvider.getAll(symbolicName).stream(),
								onlineLibraryProvider.getAll(symbolicName).stream())
						.map(lib -> lib.version().toString()).distinct().toList();
			}

			private List<LibraryChange> getAvailableChanges(final LibraryChange change) {
				final LibraryChange select = LibraryChange.createEmpty(change.getSymbolicName(),
						change.getCurrentVersion());
				final LibraryChange remove = LibraryChange.createRemove(change.getSymbolicName(),
						change.getCurrentVersion());
				return Stream
						.concat(Stream.of(select, remove),
								getAvailableVersions(change.getSymbolicName()).stream()
										.filter(v -> !v.equals(change.getCurrentVersion())).map(v -> LibraryChange
												.createChange(change.getSymbolicName(), change.getCurrentVersion(), v)))
						.toList();
			}

		});

	}

	private void checkPageComplete() {
		final IStatus status = validateChanges();
		setPageComplete(getAllChanges().anyMatch(change -> change.getType() != ChangeType.NOP) && status.isOK());
	}

	private Stream<LibraryChange> getAllChanges() {
		return input.stream().flatMap(node -> node.children().stream());
	}

	private TreeViewerColumn createColumn(final String name, final CellLabelProvider labelProvider) {
		final TreeViewerColumn column = new TreeViewerColumn(treeViewer, SWT.NONE);
		column.getColumn().setText(name);
		column.setLabelProvider(labelProvider);
		return column;
	}

	private void startRemoteVersionLookupJob() {
		setMessage(Messages.ManageLibraryWizard_LoadRemoteVersions + " ...", IMessageProvider.INFORMATION); //$NON-NLS-1$
		final Job job = new Job(Messages.ManageLibraryWizard_LoadRemoteVersions) {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {

				final IStatus status = onlineLibraryProvider.refresh(monitor, true);

				Display.getDefault().asyncExec(() -> {
					if (treeViewer == null || treeViewer.getTree().isDisposed()) {
						return;
					}

					if (status.isOK()) {
						setMessage(null);
					} else {
						final String combinedMessage = Arrays.stream(status.getChildren()).map(IStatus::getMessage)
								.collect(Collectors.joining(System.lineSeparator()));
						setMessage(combinedMessage, toMessageProviderSeverity(status));
					}

					treeViewer.refresh();
				});

				return Status.OK_STATUS;
			}
		};

		job.setUser(false);
		job.schedule();
	}

	private static int toMessageProviderSeverity(final IStatus status) {
		return switch (status.getSeverity()) {
		case IStatus.ERROR -> IMessageProvider.ERROR;
		case IStatus.WARNING -> IMessageProvider.WARNING;
		case IStatus.INFO -> IMessageProvider.INFORMATION;
		default -> IMessageProvider.NONE;
		};
	}

	record LibContainer(String name, List<LibraryChange> children) {
		LibContainer(final String name) {
			this(name, new ArrayList<>());
		}
	}

}
