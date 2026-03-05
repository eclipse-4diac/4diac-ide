package org.eclipse.fordiac.ide.library.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
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
import org.eclipse.fordiac.ide.library.ui.sources.LibrarySourceBuilder.EmptyTreeContentProvider;
import org.eclipse.fordiac.ide.library.ui.sources.LibrarySourceUIComponents;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.FilteredCheckedTree;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.LatestOnlyFilter;
import org.eclipse.fordiac.ide.library.ui.wizards.treeviewer.NonValidGitlabPackageFilter;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
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
import org.eclipse.ui.services.IDisposable;

public class UnifiedLibraryImportWizardPage extends WizardPage {

	private IProject targetProject;
	private final List<ILibrarySource> sources;

	private ComboViewer sourceCombo;
	private Button refreshButton;
	private Button manageButton;

	private Composite configHost;
	private StackLayout configLayout;

	private FilteredCheckedTree filteredTree;
	private ContainerCheckedTreeViewer viewer;

	private Text detailsText;

	private ILibrarySource activeSource;

	private ITreeContentProvider currentContentProvider;
	private ILabelProvider currentLabelProvider;

	private Object[] lastExpandedElements = new Object[0];
	private Object lastSelectedElement = null;

	private boolean showLatestOnly;
	private boolean hideEmptyProjects;
	private boolean internalCheckUpdate;

	private final LatestOnlyFilter latestOnlyFilter = new LatestOnlyFilter();
	private final NonValidGitlabPackageFilter gitlabPackageFilter = new NonValidGitlabPackageFilter();

	public UnifiedLibraryImportWizardPage(final IProject targetProject) {
		super(Messages.UnifiedLibraryImportWizardPage_Available_Libraries);
		setTitle(Messages.UnifiedLibraryImportWizardPage_LibraryImport);
		setDescription(Messages.UnifiedLibraryImportWizardPage_brows);
		this.targetProject = targetProject;
		this.showLatestOnly = true;
		this.hideEmptyProjects = true;
		this.sources = new ArrayList<>(LibrarySourceBuilder.getAllSources());
	}

	public boolean performImport(final IWizardContainer container) {
		if (activeSource == null) {
			setErrorMessage(Messages.UnifiedLibraryImportWizardPage_no_src);
			return false;
		}
		if (targetProject == null) {
			setErrorMessage(Messages.UnifiedLibraryImportWizardPage_no_target);
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
			setErrorMessage(cause.getMessage());
			return false;
		} catch (final InterruptedException ie) {
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

		refreshButton = new Button(top, SWT.PUSH);
		refreshButton.setText(Messages.UnifiedLibraryImportWizardPage_refresh);

		manageButton = new Button(top, SWT.PUSH);
		manageButton.setText(Messages.UnifiedLibraryImportWizardPage_manage);
		manageButton.setToolTipText(Messages.UnifiedLibraryImportWizardPage_config);

		manageButton.addListener(SWT.Selection, e -> {
			openGitLabPreferences();
			// After closing preferences, rebuild the sources list from preferences
			rebuildSourcesKeepingSelection();
		});

		sourceCombo.addSelectionChangedListener(e -> {
			activeSource = (ILibrarySource) ((IStructuredSelection) e.getSelection()).getFirstElement();
			switchConfigComposite(activeSource);
			scheduleRefresh();
		});

		refreshButton.addListener(SWT.Selection, ev -> scheduleRefresh());
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

		// Create one config composite per source, then switch using StackLayout
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
		// give the wizard page a larger preferred size so the tree can show more
		// rows/columns
		sashGD.heightHint = 650;
		sashGD.widthHint = 950;
		sash.setLayoutData(sashGD);

		final Composite treeArea = new Composite(sash, SWT.NONE);
		treeArea.setLayout(new FillLayout());

		filteredTree = new FilteredCheckedTree(treeArea, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL, new PatternFilter());
		viewer = filteredTree.getCheckedViewer();
		viewer.setContentProvider(new EmptyTreeContentProvider());
		viewer.setLabelProvider(new LabelProvider());
		viewer.setInput(new Object[0]);

		viewer.addFilter(latestOnlyFilter);
		viewer.addFilter(gitlabPackageFilter);
		applyViewerFilters();

		final ICheckStateListener checkListener = event -> {
			handleTreeCheckBoxes(event);
		};
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
			final String key = activeSource.exclusiveVersinSelectionKey(event.getElement());
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
						final String otherKey = activeSource.exclusiveVersinSelectionKey(other);
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

		// dispose old sources if needed
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
		if (activeSource == null || viewer == null || viewer.getControl().isDisposed()) {
			return;
		}

		// snapshot expansion + selection BEFORE anything changes
		try {
			lastExpandedElements = viewer.getExpandedElements();
			final Object sel = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
			lastSelectedElement = sel;
		} catch (final Exception ignore) {
			// viewer might not be initialized yet
		}

		// Ensure a content provider is set before any setInput() call (JFace
		// assertion).
		if (viewer.getContentProvider() == null) {
			viewer.setContentProvider(new EmptyTreeContentProvider());
			viewer.setLabelProvider(new LabelProvider());
		}

		setErrorMessage(null);
		setPageComplete(false);

		detailsText.setText(Messages.UnifiedLibraryImportWizardPage_loading);
		viewer.setInput(new Object[0]);

		final ILibrarySource sourceSnapshot = activeSource;

		final Job job = new Job(
				Messages.UnifiedLibraryImportWizardPage_loading_from + sourceSnapshot.comboLabelText()) {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				try {
					final LibrarySourceUIComponents model = sourceSnapshot.loadLibrarySource(monitor);
					if (monitor.isCanceled()) {
						return Status.CANCEL_STATUS;
					}

					Display.getDefault().asyncExec(() -> {
						if (getControl() == null || getControl().isDisposed()) {
							disposeCurrentProviders();
							return;
						}
						try {
							applyModelToViewer(model);
							latestOnlyFilter.rebuildIndex(viewer, currentContentProvider, model.input());
							applyViewerFilters();
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
					return new Status(IStatus.ERROR, "org.eclipse.fordiac.ide.library.ui", ex.getMessage(), ex);
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

		// 1) Restore expansion
		if (lastExpandedElements != null && lastExpandedElements.length > 0) {
			try {
				viewer.setExpandedElements(lastExpandedElements);
			} catch (final Exception ignore) {
				// elements may not exist anymore after filtering "latest only"
			}
		}
		if (viewer.getExpandedElements().length == 0) {

			viewer.expandToLevel(2);

		} else {
			// keep existing behavior: expand a bit for first load
			viewer.expandToLevel(2);
		}

		// 2) Restore selection if still present
		if (lastSelectedElement != null) {
			try {
				viewer.setSelection(new StructuredSelection(lastSelectedElement), true);
			} catch (final Exception ignore) {
				// selection may no longer exist
			}
		}
	}

	private void applyModelToViewer(final LibrarySourceUIComponents model) {
		disposeCurrentProviders();

		currentContentProvider = model.contentProvider();
		currentLabelProvider = model.labelProvider();

		viewer.setContentProvider(currentContentProvider);
		viewer.setLabelProvider(currentLabelProvider);
		viewer.setInput(model.input());

		updateGitLabContext(model);

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

	private void updateGitLabContext(final LibrarySourceUIComponents model) {

		try {
			final Object in = model != null ? model.input() : null;
			final Object ctx = model != null ? model.context() : null;
			if (in instanceof final java.util.Map<?, ?> inMap && ctx instanceof final java.util.Map<?, ?> ctxMap) {
				@SuppressWarnings("unchecked")
				final var pp = (java.util.Map<org.eclipse.fordiac.ide.gitlab.Project, java.util.List<org.eclipse.fordiac.ide.gitlab.Package>>) inMap;
				@SuppressWarnings("unchecked")
				final var leaves = (java.util.Map<String, java.util.List<org.eclipse.fordiac.ide.gitlab.treeviewer.LeafNode>>) ctxMap;
				gitlabPackageFilter.setContext(pp, leaves, latestOnlyFilter);
				return;
			}
		} catch (final Exception ignore) {
			// ignore (non-GitLab input/context)
		}
		gitlabPackageFilter.setContext(null, null, latestOnlyFilter);
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

	private void disposeCurrentProviders() {
		try {
			if (currentLabelProvider != null) {
				currentLabelProvider.dispose();
			}
		} finally {
			currentLabelProvider = null;
		}

		if (currentContentProvider instanceof final IDisposable d) {
			d.dispose();
		}
		currentContentProvider = null;
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
		disposeCurrentProviders();
		for (final ILibrarySource s : sources) {
			s.dispose();
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

		filteredTree.getCheckedViewer().getCheckedElements();

		Stream.of(filteredTree.getCheckedViewer().getCheckedElements()).filter(LibraryRecord.class::isInstance)
				.map(LibraryRecord.class::cast).forEach(lib -> libs
						.put(ManifestHelper.createRequired(lib.symbolicName(), lib.version().toString()), lib.uri()));
		return libs;
	}

}
