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
package org.eclipse.fordiac.ide.library.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.library.LibraryRecord;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.library.ui.sources.ILibrarySource;
import org.eclipse.fordiac.ide.library.ui.sources.LibrarySourceBuilder;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.FilteredCheckedTree;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.LatestOnlyFilter;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.LibraryTreeNode;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.NonValidGitlabPackageFilter;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.model.AdaptableList;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class UnifiedLibraryImportWizardPage extends WizardPage {

	private IProject targetProject;
	private final List<ILibrarySource> sources;

	private ComboViewer sourceCombo;

	private Composite configHost;
	private StackLayout configLayout;

	private FilteredCheckedTree filteredTree;
	private ContainerCheckedTreeViewer viewer;
	private WorkbenchContentProvider contentProvider;
	private WorkbenchLabelProvider labelProvider;

	private Text detailsText;

	private ILibrarySource activeSource;

	private Object[] lastExpandedElements = new Object[0];
	private Object lastSelectedElement = null;

	private boolean showLatestOnly;
	private boolean hideEmptyProjects;
	private boolean internalCheckUpdate;
	private final Set<String> defaultSelectedLibraries;

	private final LatestOnlyFilter latestOnlyFilter = new LatestOnlyFilter();
	private final NonValidGitlabPackageFilter gitlabPackageFilter = new NonValidGitlabPackageFilter();

	public UnifiedLibraryImportWizardPage(final IProject targetProject) {
		this(targetProject, new String[0]);
	}

	public UnifiedLibraryImportWizardPage(final IProject targetProject, final String[] defaultSelectedLibraries) {
		super(Messages.UnifiedLibraryImportWizardPage_Available_Libraries);
		setTitle(Messages.UnifiedLibraryImportWizardPage_LibraryImport);
		setDescription(Messages.UnifiedLibraryImportWizardPage_brows);
		this.targetProject = targetProject;
		this.showLatestOnly = true;
		this.hideEmptyProjects = true;
		this.sources = new ArrayList<>(LibrarySourceBuilder.getAllSources());
		this.defaultSelectedLibraries = Set.copyOf(
				Arrays.asList(defaultSelectedLibraries != null ? defaultSelectedLibraries.clone() : new String[0]));
	}

	public boolean performImport(final IWizardContainer container) {
		if (activeSource == null) {
			setErrorMessage(Messages.UnifiedLibraryImportWizardPage_no_src);
			return false;
		}
		if (targetProject == null) {
			setErrorMessage(Messages.UnifiedLibraryImportWizardPage_no_project);
			return false;
		}

		final List<Object> selectedLeafs = getCheckedLeafElements();
		if (selectedLeafs.isEmpty()) {
			setErrorMessage(Messages.UnifiedLibraryImportWizardPage_no_lib);
			return false;
		}

		final IRunnableWithProgress op = monitor -> {
			try {
				activeSource.install(targetProject, selectedLeafs, monitor);
			} catch (final Exception e) {
				throw new InvocationTargetException(e);
			}
		};

		try {
			container.run(true, true, op);
			return true;
		} catch (final InvocationTargetException ite) {
			final Throwable cause = ite.getCause() != null ? ite.getCause() : ite;
			setErrorMessage(cause.getMessage() != null ? cause.getMessage() : cause.toString());
			return false;
		} catch (final InterruptedException ie) {
			Thread.currentThread().interrupt();
			setErrorMessage(Messages.UnifiedLibraryImportWizardPage_op_cancled);
			return false;
		}
	}

	@Override
	public void createControl(final Composite parent) {
		showLatestOnly = true;
		final Composite root = new Composite(parent, SWT.NONE);
		root.setLayout(new GridLayout(1, false));
		root.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createTopRow(root);
		createProjectSelectionRow(root);
		createConfigArea(root);
		createMainArea(root);

		final Button showLatestVersionsOnlyButton = new Button(root, SWT.CHECK);
		showLatestVersionsOnlyButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		showLatestVersionsOnlyButton.setText(Messages.UnifiedLibraryImportWizardPage_show_latest);
		showLatestVersionsOnlyButton.setSelection(showLatestOnly);
		showLatestVersionsOnlyButton.addListener(SWT.Selection, e -> {
			showLatestOnly = showLatestVersionsOnlyButton.getSelection();
			applyViewerFilters();
		});

		final Button hideEmptyProjectsButton = new Button(root, SWT.CHECK);
		hideEmptyProjectsButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		hideEmptyProjectsButton.setText(Messages.UnifiedLibraryImportWizardPage_hide_non_valid);
		hideEmptyProjectsButton.setSelection(hideEmptyProjects);
		hideEmptyProjectsButton.addListener(SWT.Selection, e -> {
			hideEmptyProjects = hideEmptyProjectsButton.getSelection();
			applyViewerFilters();
		});

		setControl(root);
		Dialog.applyDialogFont(root);

		if (!sources.isEmpty()) {
			sourceCombo.setSelection(new StructuredSelection(sources.getFirst()));
		} else {
			setErrorMessage(Messages.UnifiedLibraryImportWizardPage_no_lib_Available);
			setPageComplete(false);
		}
	}

	private void createTopRow(final Composite parent) {
		final Composite top = new Composite(parent, SWT.NONE);
		top.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		top.setLayout(new GridLayout(4, false));

		new Label(top, SWT.NONE).setText(Messages.UnifiedLibraryImportWizardPage_work_with);

		sourceCombo = new ComboViewer(top, SWT.READ_ONLY);
		sourceCombo.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		sourceCombo.setContentProvider(ArrayContentProvider.getInstance());
		sourceCombo.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return element instanceof final ILibrarySource s ? s.comboLabelText() : super.getText(element);
			}
		});
		sourceCombo.setInput(sources);

		final Button refreshButton = new Button(top, SWT.PUSH);
		refreshButton.setText(Messages.UnifiedLibraryImportWizardPage_refresh);

		final Button manageButton = new Button(top, SWT.PUSH);
		manageButton.setText(Messages.UnifiedLibraryImportWizardPage_manage);
		manageButton.setToolTipText(Messages.UnifiedLibraryImportWizardPage_config);
		manageButton.addListener(SWT.Selection, e -> {
			openGitLabPreferences();
			rebuildSourcesKeepingSelection();
		});

		sourceCombo.addSelectionChangedListener(e -> {
			activeSource = (ILibrarySource) ((IStructuredSelection) e.getSelection()).getFirstElement();
			switchConfigComposite(activeSource);
			scheduleRefresh();
		});

		refreshButton.addListener(SWT.Selection, ev -> scheduleRefresh());
	}

	private void createProjectSelectionRow(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		container.setLayout(new GridLayout(2, false));

		final Label label = new Label(container, SWT.NONE);
		label.setText(Messages.UnifiedLibraryImportWizardPage_ImportIntoProject);

		final ComboViewer projectCombo = new ComboViewer(container, SWT.READ_ONLY);
		projectCombo.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		projectCombo.setContentProvider(ArrayContentProvider.getInstance());
		projectCombo.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return element instanceof final IProject project ? project.getName() : super.getText(element);
			}
		});

		final var accessibleProjects = getAccessibleFordiacProjects();

		projectCombo.setInput(accessibleProjects);

		if (targetProject == null) {
			if (accessibleProjects.isEmpty()) {
				setErrorMessage(Messages.UnifiedLibraryImportWizardPage_no_project);
				setPageComplete(false);
				return;
			}
			targetProject = accessibleProjects.getFirst();
		}

		projectCombo.setSelection(new StructuredSelection(targetProject));

		projectCombo.addSelectionChangedListener(
				e -> targetProject = (IProject) ((IStructuredSelection) e.getSelection()).getFirstElement());

	}

	private static List<IProject> getAccessibleFordiacProjects() {
		return Arrays.stream(ResourcesPlugin.getWorkspace().getRoot().getProjects())
				.filter(SystemManager::hasFordiacProjectNature).toList();
	}

	private void createConfigArea(final Composite parent) {
		configHost = new Composite(parent, SWT.NONE);
		configHost.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		configLayout = new StackLayout();
		configHost.setLayout(configLayout);
		rebuildConfigComposites();
	}

	private void rebuildConfigComposites() {
		for (final Control c : configHost.getChildren()) {
			c.dispose();
		}

		for (final ILibrarySource s : sources) {
			final Composite cfg = new Composite(configHost, SWT.NONE);
			cfg.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
			cfg.setLayout(new GridLayout(1, false));
			cfg.setData(Messages.UnifiedLibraryImportWizardPage_source, s);
			s.createConfigUI(cfg);
		}

		configHost.layout(true, true);
	}

	private void switchConfigComposite(final ILibrarySource source) {
		if (source == null) {
			return;
		}
		for (final Control c : configHost.getChildren()) {
			if (c.getData(Messages.UnifiedLibraryImportWizardPage_source) == source) {
				configLayout.topControl = c;
				break;
			}
		}
		configHost.layout(true, true);
	}

	private void createMainArea(final Composite parent) {
		final SashForm sash = new SashForm(parent, SWT.VERTICAL);
		final GridData sashGD = new GridData(SWT.FILL, SWT.FILL, true, true);
		sashGD.heightHint = 650;
		sashGD.widthHint = 950;
		sash.setLayoutData(sashGD);

		final Composite treeArea = new Composite(sash, SWT.NONE);
		treeArea.setLayout(new FillLayout());

		filteredTree = new FilteredCheckedTree(treeArea, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL, new PatternFilter());
		viewer = filteredTree.getCheckedViewer();
		contentProvider = new WorkbenchContentProvider();
		labelProvider = new WorkbenchLabelProvider();
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(labelProvider);
		viewer.setInput(new AdaptableList());
		viewer.addFilter(latestOnlyFilter);
		viewer.addFilter(gitlabPackageFilter);
		updateGitlabContext();
		applyViewerFilters();

		final ICheckStateListener checkListener = this::handleTreeCheckBoxes;
		viewer.addCheckStateListener(checkListener);
		viewer.addSelectionChangedListener(e -> updateDetails());

		detailsText = new Text(sash, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		detailsText.setText(Messages.UnifiedLibraryImportWizardPage_sel_lib);

		sash.setWeights(85, 15);
		setPageComplete(false);
	}

	protected void handleTreeCheckBoxes(final CheckStateChangedEvent event) {
		if (internalCheckUpdate || viewer == null || activeSource == null) {
			return;
		}
		if (event.getChecked() && activeSource.isSelectableLeaf(event.getElement())) {
			final String key = activeSource.exclusiveVersionSelectionKey(event.getElement());
			if (key != null && !key.isBlank()) {
				internalCheckUpdate = true;
				try {
					final Object newlyChecked = event.getElement();
					for (final Object other : viewer.getCheckedElements()) {
						if (other == newlyChecked) {
							continue;
						}
						if (!activeSource.isSelectableLeaf(other)) {
							continue;
						}
						final String otherKey = activeSource.exclusiveVersionSelectionKey(other);
						if (key.equals(otherKey)) {
							viewer.setChecked(other, false);
						}
					}
				} finally {
					internalCheckUpdate = false;
				}
			}
		}

		updateDetails();
		setPageComplete(hasSelectableCheckedLeafs());
	}

	private void rebuildSourcesKeepingSelection() {
		final String previousId = activeSource != null ? activeSource.id() : null;

		for (final ILibrarySource s : sources) {
			s.dispose();
		}
		sources.clear();
		sources.addAll(LibrarySourceBuilder.getAllSources());
		sourceCombo.setInput(sources);
		rebuildConfigComposites();

		ILibrarySource toSelect = null;
		if (previousId != null) {
			toSelect = sources.stream().filter(s -> Objects.equals(previousId, s.id())).findFirst().orElse(null);
		}
		if (toSelect == null && !sources.isEmpty()) {
			toSelect = sources.getFirst();
		}

		if (toSelect != null) {
			sourceCombo.setSelection(new StructuredSelection(toSelect));
			activeSource = toSelect;
			switchConfigComposite(activeSource);
			scheduleRefresh();
		} else {
			activeSource = null;
			setErrorMessage(Messages.UnifiedLibraryImportWizardPage_no_lib_src);
			setPageComplete(false);
		}
	}

	private void scheduleRefresh() {
		if (activeSource == null || viewer == null || viewer.getControl().isDisposed() || targetProject == null) {
			return;
		}

		try {
			lastExpandedElements = viewer.getExpandedElements();
			lastSelectedElement = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
		} catch (final Exception ignore) {
			// viewer might not be initialized yet
		}

		setErrorMessage(null);
		setPageComplete(false);
		detailsText.setText(Messages.UnifiedLibraryImportWizardPage_loading);
		viewer.setInput(new AdaptableList());

		final ILibrarySource sourceSnapshot = activeSource;
		final Job job = new Job(
				Messages.UnifiedLibraryImportWizardPage_loading_from + sourceSnapshot.comboLabelText()) {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				try {
					final Object model = sourceSnapshot.loadLibrarySource(monitor);
					if (monitor.isCanceled()) {
						return Status.CANCEL_STATUS;
					}

					Display.getDefault().asyncExec(() -> {
						if (getControl() == null || getControl().isDisposed()) {
							return;
						}
						try {
							applyModelToViewer(model);
							updateGitlabContext();
							latestOnlyFilter.rebuildIndex(viewer, contentProvider, model);
							applyViewerFilters();
							applyDefaultSelectionToVisibleLibraries();
							restoreTreeStateAfterRefresh();
							detailsText.setText(Messages.UnifiedLibraryImportWizardPage_select_to_see_details);
							setPageComplete(hasSelectableCheckedLeafs());
						} catch (final Exception uiEx) {
							setErrorMessage(uiEx.getMessage());
							detailsText.setText(Messages.UnifiedLibraryImportWizardPage_failed);
							setPageComplete(false);
						}
					});
					return Status.OK_STATUS;
				} catch (final Exception ex) {
					Display.getDefault().asyncExec(() -> {
						if (getControl() == null || getControl().isDisposed()) {
							return;
						}
						setErrorMessage(ex.getMessage());
						detailsText.setText(Messages.UnifiedLibraryImportWizardPage_feiled_load);
						setPageComplete(false);
					});
					return new Status(IStatus.ERROR, "org.eclipse.fordiac.ide.library.ui", ex.getMessage(), ex); //$NON-NLS-1$
				}
			}
		};

		job.setUser(true);
		job.schedule();
	}

	private void restoreTreeStateAfterRefresh() {
		if (viewer == null || viewer.getControl().isDisposed()) {
			return;
		}

		if (lastExpandedElements != null && lastExpandedElements.length > 0) {
			try {
				viewer.setExpandedElements(lastExpandedElements);
			} catch (final Exception ignore) {
				// elements may not exist anymore after filtering
			}
		}
		viewer.expandToLevel(2);

		if (lastSelectedElement != null) {
			try {
				viewer.setSelection(new StructuredSelection(lastSelectedElement), true);
			} catch (final Exception ignore) {
				// selection may no longer exist
			}
		}
	}

	private void applyModelToViewer(final Object model) {
		viewer.setInput(model);
		viewer.refresh();
		viewer.expandAll();
		uncheckAll();
	}

	private void uncheckAll() {
		if (viewer == null || viewer.getTree() == null || viewer.getTree().isDisposed()) {
			return;
		}
		for (final TreeItem item : viewer.getTree().getItems()) {
			final Object data = item.getData();
			if (data != null) {
				viewer.setSubtreeChecked(data, false);
			}
		}
	}

	private void updateGitlabContext() {
		gitlabPackageFilter.setLatestOnlyFilter(latestOnlyFilter);
	}

	private void applyDefaultSelectionToVisibleLibraries() {
		if (viewer == null || viewer.getControl().isDisposed() || activeSource == null
				|| defaultSelectedLibraries.isEmpty()) {
			return;
		}

		internalCheckUpdate = true;
		try {
			checkDefaultLibraries(viewer.getTree().getItems(), new HashSet<>());
		} finally {
			internalCheckUpdate = false;
		}
	}

	private void checkDefaultLibraries(final TreeItem[] items, final Set<String> alreadySelected) {
		for (final TreeItem item : items) {
			final Object element = item.getData();
			final Object value = LibraryTreeNode.unwrapNode(element);
			if (activeSource.isSelectableLeaf(element) && value instanceof final LibraryRecord rec
					&& defaultSelectedLibraries.contains(rec.symbolicName())
					&& alreadySelected.add(rec.symbolicName())) {
				viewer.setChecked(element, true);
			}
			checkDefaultLibraries(item.getItems(), alreadySelected);
		}
	}

	private void applyViewerFilters() {
		latestOnlyFilter.setEnabled(showLatestOnly);
		gitlabPackageFilter.setEnabled(hideEmptyProjects);
		if (viewer == null || viewer.getControl().isDisposed()) {
			return;
		}

		final Object[] expandedBefore;
		final Object selectedBefore;
		try {
			expandedBefore = viewer.getExpandedElements();
			selectedBefore = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
		} catch (final Exception ex) {
			viewer.refresh();
			return;
		}

		viewer.getTree().setRedraw(false);
		try {
			viewer.refresh();
			if (expandedBefore != null && expandedBefore.length > 0) {
				try {
					viewer.setExpandedElements(expandedBefore);
				} catch (final Exception ignore) {
					// elements may have been filtered out
				}
			}
			if (selectedBefore != null) {
				try {
					viewer.setSelection(new StructuredSelection(selectedBefore), true);
				} catch (final Exception ignore) {
					// selection may have been filtered out
				}
			}
			if (expandedBefore != null && expandedBefore.length > 0 && viewer.getExpandedElements().length == 0) {
				viewer.expandToLevel(2);
			}
		} finally {
			viewer.getTree().setRedraw(true);
		}
	}

	private boolean hasSelectableCheckedLeafs() {
		if (activeSource == null || viewer == null) {
			return false;
		}
		return Arrays.stream(viewer.getCheckedElements()).anyMatch(activeSource::isSelectableLeaf);
	}

	private List<Object> getCheckedLeafElements() {
		if (activeSource == null || viewer == null) {
			return List.of();
		}
		final List<Object> res = new ArrayList<>();
		for (final Object el : viewer.getCheckedElements()) {
			if (activeSource.isSelectableLeaf(el)) {
				res.add(el);
			}
		}
		return res;
	}

	private void updateDetails() {
		if (detailsText == null || detailsText.isDisposed() || viewer == null) {
			return;
		}
		final Object sel = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
		detailsText.setText(LibrarySourceBuilder.buildDetails(sel));
	}

	@Override
	public void dispose() {
		for (final ILibrarySource s : sources) {
			s.dispose();
		}
		if (labelProvider != null) {
			labelProvider.dispose();
		}
		super.dispose();
	}

	private void openGitLabPreferences() {
		final String pageId = org.eclipse.fordiac.ide.gitlab.preferences.PreferenceConstants.GITLAB_ENDPOINTS_PREF_PAGE_ID;
		org.eclipse.ui.dialogs.PreferencesUtil
				.createPreferenceDialogOn(getShell(), pageId, new String[] { pageId }, null).open();
	}

	public void setTargetProject(final IProject project) {
		this.targetProject = project;
	}

	public Map<Required, URI> getChosenLibraries() {
		final Map<Required, URI> libs = new HashMap<>();
		Stream.of(filteredTree.getCheckedViewer().getCheckedElements()).map(LibraryTreeNode::unwrapNode)
				.filter(LibraryRecord.class::isInstance).map(LibraryRecord.class::cast).forEach(lib -> libs
						.put(ManifestHelper.createRequired(lib.symbolicName(), lib.version().toString()), lib.uri()));
		return libs;
	}
}
