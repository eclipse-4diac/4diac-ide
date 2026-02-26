/*******************************************************************************
 * Copyright (c) 2020, 2026 Primetals Technologies Germany GmbH,
 *                          Johannes Kepler University Linz,
 *                          Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *               - implemented first version of gotoMarker for FB markers
 *               - extracted breadcrumb based editor to model.ui
 *   Michael Oberlehner, Alois Zoitl
 *               - implemented save and restore state
 *   Daniel Lindhuber - auto reload remembers editor location
 *   Martin Erich Jobst - use library element provider
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.editors;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.eclipse.core.commands.operations.UndoContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.application.editors.ApplicationEditor;
import org.eclipse.fordiac.ide.application.editors.SubAppNetworkEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeInstanceViewer;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.DiagramOutlinePage;
import org.eclipse.fordiac.ide.gef.commands.OperationHistoryCommandStack;
import org.eclipse.fordiac.ide.model.commands.QualNameChangeListenerManager;
import org.eclipse.fordiac.ide.model.edit.ITypeEntryEditor;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.model.ui.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.model.ui.editors.AbstractBreadCrumbEditor;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementActivationListener;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementStateListener;
import org.eclipse.fordiac.ide.model.ui.editors.SubEditorInput;
import org.eclipse.fordiac.ide.model.ui.listeners.EditorTabCommandStackListener;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceDiagramEditor;
import org.eclipse.fordiac.ide.subapptypeeditor.viewer.SubappInstanceViewer;
import org.eclipse.fordiac.ide.systemconfiguration.editor.SystemConfigurationEditor;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.systemmanagement.ui.providers.AutomationSystemProviderAdapterFactory;
import org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer.StyledSystemLabelProvider;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.widget.SelectionTabbedPropertySheetPage;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;

public class AutomationSystemEditor extends AbstractBreadCrumbEditor implements ITypeEntryEditor {

	private AutomationSystem system;
	private final OperationHistoryCommandStack commandStack = new OperationHistoryCommandStack();
	private final LibraryElementStateListener elementStateListener = new EditorStateListener();
	private LibraryElementActivationListener activationListener;
	private DiagramOutlinePage outlinePage;
	private Composite mainComposite;

	public AutomationSystemEditor() {
		getCommandStack().addCommandStackEventListener(this);
		getCommandStack().addCommandStackEventListener(new EditorTabCommandStackListener(this));
		QualNameChangeListenerManager.addCommandStackEventListener(getCommandStack());
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, input);
		LibraryElementProvider.INSTANCE.addLibraryElementStateListener(elementStateListener);
		activationListener = new LibraryElementActivationListener(this);
	}

	@Override
	public void createPartControl(final Composite parent) {
		mainComposite = parent;
		createEditorContent();
	}

	private void createEditorContent() {
		if (system != null) {
			super.createPartControl(mainComposite);
		} else {
			showLoadErrorMessage(mainComposite);
		}
	}

	private void clearEditorContent() {
		getModelToEditorNumMapping().clear();
		for (int i = getPageCount() - 1; i >= 0; i--) {
			removePage(i);
		}
		pages.clear();
		for (final Control child : mainComposite.getChildren()) {
			child.dispose();
		}
		mainComposite.layout(true, true);
	}

	public void showLoadErrorMessage(final Composite parent) {
		final TypeEntry entry = getTypeEntry();
		final boolean fileExists = entry != null && entry.getFile() != null && entry.getFile().exists();

		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(fileExists ? 3 : 2).applyTo(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).applyTo(composite);

		final Image image = Display.getDefault().getSystemImage(SWT.ICON_ERROR);
		final Label imageLabel = new Label(composite, SWT.NULL);
		image.setBackground(imageLabel.getBackground());
		imageLabel.setImage(image);
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.BEGINNING).applyTo(imageLabel);

		final Label messageLabel = new Label(composite, SWT.NONE);
		messageLabel.setText(
				MessageFormat.format(Messages.AutomationSystemEditor_CouldNotLoadSystem, getEditorInput().getName()));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(false, false).applyTo(messageLabel);

		if (fileExists) {
			final Button textEditorButton = new Button(composite, SWT.NONE);
			textEditorButton.setText(Messages.AutomationSystemEditor_OpenTextEditor);
			textEditorButton.addListener(SWT.Selection, e -> EditorUtils.openTextEditor(getEditorInput()));
		}
	}

	@Override
	public String getTitleToolTip() {
		final String tooltip = (system != null) ? system.getTypeEntry().getFullTypeName() + "\n" : ""; //$NON-NLS-1$ //$NON-NLS-2$
		return tooltip + super.getTitleToolTip();
	}

	@Override
	protected Composite createPageContainer(final Composite parent) {
		final Composite pageContainer = new Composite(parent, SWT.NONE);
		pageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		return pageContainer;
	}

	@Override
	protected void addPages() {
		try {
			final int pagenum = addPage(new SystemEditor(), getEditorInput());
			getModelToEditorNumMapping().put(system, Integer.valueOf(pagenum));
		} catch (final PartInitException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	@Override
	protected void pageChange(final int newPageIndex) {
		super.pageChange(newPageIndex);
		if ((-1 != newPageIndex) && (null != outlinePage)) {
			final GraphicalViewer viewer = getActiveEditor().getAdapter(GraphicalViewer.class);
			outlinePage.viewerChanged(viewer);
		}
	}

	@Override
	protected EditorPart createEditorPart(final Object model) {
		return switch (model) {
		case final IFile file -> new SystemEditor();
		case final CFBInstance cfb -> new CompositeInstanceViewer();
		case final TypedSubApp subApp -> new SubappInstanceViewer();
		case final UntypedSubApp subApp when subApp.isContainedInTypedInstance() -> new SubappInstanceViewer();
		case final UntypedSubApp subApp -> new SubAppNetworkEditor();
		case final Application application -> new ApplicationEditor();
		case final SystemConfiguration systemConfiguration -> new SystemConfigurationEditor();
		case final Device device -> new SystemConfigurationEditor();
		case final Resource resource -> new ResourceDiagramEditor();
		case null, default -> null;
		};
	}

	@Override
	protected IEditorInput createEditorInput(final Object model) {
		return switch (model) {
		case final Device device -> new SubEditorInput(getEditorInput(), device.getSystemConfiguration());
		case null, default -> super.createEditorInput(model);
		};
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		if (null != system) {
			final WorkspaceModifyOperation operation = new WorkspaceModifyOperation(getFile().getParent()) {

				@Override
				protected void execute(final IProgressMonitor monitor)
						throws CoreException, InvocationTargetException, InterruptedException {
					LibraryElementProvider.INSTANCE.saveLibraryElement(getEditorInput(), monitor);
				}
			};
			try {
				operation.run(monitor);
			} catch (final InvocationTargetException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			} catch (final InterruptedException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
				Thread.currentThread().interrupt();
			}
			getCommandStack().markSaveLocation();
		}
	}

	@Override
	protected AdapterFactoryContentProvider createBreadcrumbContentProvider() {
		return new AdapterFactoryContentProvider(new AutomationSystemProviderAdapterFactory());
	}

	@Override
	protected AdapterFactoryLabelProvider createBreadcrumbLabelProvider() {
		return new StyledSystemLabelProvider();
	}

	@Override
	protected Object getInitialModel(final String itemPath) {
		if (null != itemPath) {
			final String[] nameList = itemPath.split("\\."); //$NON-NLS-1$
			if (nameList.length > 1) {
				// we have a child of the system
				final String searchPath = itemPath.substring(itemPath.indexOf('.') + 1);
				final EObject targetmodel = FBNetworkHelper.getModelFromHierarchicalName(searchPath, system);
				if (null != targetmodel) {
					return targetmodel;
				}
			}
		}
		return system;
	}

	@Override
	public void doSaveAs() {
		if (system == null) {
			return;
		}
		final SaveAsDialog saveAsDialog = new SaveAsDialog(getSite().getShell());
		saveAsDialog.setOriginalName(getFile().getName());
		saveAsDialog.open();
		final IPath path = saveAsDialog.getResult();
		if (path == null) {
			return;
		}

		final IPath fullPath = getFile().getFullPath();
		if (fullPath.equals(path)) {
			doSave(null);
			return;
		}
		final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation(file.getParent()) {

			@Override
			protected void execute(final IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				system.setName(TypeEntry.getTypeNameFromFile(file));

				final TypeEntry newSystemEntry = TypeLibraryManager.INSTANCE.getTypeLibrary(file.getProject())
						.createTypeEntry(file);
				newSystemEntry.save(system, monitor);
				setInput(new FileEditorInput(file));
			}
		};
		try {
			getSite().getWorkbenchWindow().run(true, true, operation);
		} catch (final InvocationTargetException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		} catch (final InterruptedException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == IPropertySheetPage.class) {
			return adapter.cast(new SelectionTabbedPropertySheetPage(this));
		}
		if (adapter == IContentOutlinePage.class) {
			if (outlinePage == null && system != null) {
				outlinePage = new DiagramOutlinePage(getActiveEditor().getAdapter(GraphicalViewer.class));
			}
			return adapter.cast(outlinePage);
		}
		if (adapter == AutomationSystem.class || adapter == LibraryElement.class) {
			return adapter.cast(system);
		}
		if (adapter == GraphicalAnnotationModel.class) {
			return adapter.cast(LibraryElementProvider.INSTANCE.getAnnotationModel(getEditorInput()));
		}
		return super.getAdapter(adapter);
	}

	@Override
	public OperationHistoryCommandStack getCommandStack() {
		return commandStack;
	}

	@Override
	public String getContributorId() {
		return DiagramEditorWithFlyoutPalette.PROPERTY_CONTRIBUTOR_ID;
	}

	private IFile getFile() {
		return system.getTypeEntry().getFile();
	}

	@Override
	public void dispose() {
		super.dispose();
		if (null != getCommandStack()) {
			commandStack.dispose();
		}
		if (activationListener != null) {
			activationListener.dispose();
			activationListener = null;
		}
		LibraryElementProvider.INSTANCE.disconnect(getEditorInput());
		LibraryElementProvider.INSTANCE.removeLibraryElementStateListener(elementStateListener);
	}

	private SystemEntry getTypeEntry() {
		if (system != null) {
			return (SystemEntry) system.getTypeEntry();
		}

		if (getEditorInput() instanceof final FileEditorInput fileInput) {
			return (SystemEntry) TypeLibraryManager.INSTANCE.getTypeEntryForFile(fileInput.getFile());
		}

		return null;
	}

	@Override
	public void reloadType() {
		try {
			LibraryElementProvider.INSTANCE.resetLibraryElement(getEditorInput(), null);
			system = LibraryElementProvider.INSTANCE.getElement(getEditorInput(), AutomationSystem.class);
			commandStack.setUndoContext(LibraryElementProvider.INSTANCE.getUndoContext(getEditorInput()));
		} catch (ClassCastException | CoreException e) {
			system = null;
			commandStack.setUndoContext(new UndoContext());
		}

		clearEditorContent();
		createEditorContent();

		if (system == null) {
			return;
		}

		setPartName(system.getName());
		restoreOpenEditor();
	}

	protected void restoreOpenEditor() {
		final String path = getBreadcrumb().serializePath();
		final boolean opened = getBreadcrumb().openPath(path, system);

		if (!opened) {
			if (!system.getApplication().isEmpty()) {
				OpenListenerManager.openEditor(system.getApplication().get(0));
				showReloadErrorMessage(path, Messages.AutomationSystemEditor_ShowingFirstApplication);
			} else {
				OpenListenerManager.openEditor(system);
				showReloadErrorMessage(path, Messages.AutomationSystemEditor_ShowingSystem);
			}
		}
		selectRootModelOfEditor();
	}

	@Override
	public void setInput(final IEditorInput input) {
		try {
			LibraryElementProvider.INSTANCE.disconnect(getEditorInput());
			LibraryElementProvider.INSTANCE.connect(input);
			system = LibraryElementProvider.INSTANCE.getElement(input, AutomationSystem.class);
			commandStack.setUndoContext(LibraryElementProvider.INSTANCE.getUndoContext(input));
		} catch (ClassCastException | CoreException e) {
			system = null;
			commandStack.setUndoContext(new UndoContext());
		}
		setPartName(TypeEntry.getTypeNameFromFileName(input.getName()));
		super.setInput(input);
	}

	private void selectRootModelOfEditor() {
		Display.getDefault().asyncExec(() -> {
			final GraphicalViewer viewer = getAdapter(GraphicalViewer.class);
			if (viewer != null) {
				final Object selection = getSelection(viewer);
				EditorUtils.refreshPropertySheetWithSelection(this, viewer, selection);
			}
		});
	}

	private Object getSelection(final GraphicalViewer viewer) {
		Object selection = null;
		final IEditorPart activeEditor = getActiveEditor();
		if (activeEditor instanceof final DiagramEditorWithFlyoutPalette diagramEditor) {
			selection = viewer.getEditPartForModel(diagramEditor.getModel());
		}
		if (selection == null) {
			selection = viewer.getRootEditPart();
		}
		return selection;
	}

	protected class EditorStateListener implements LibraryElementStateListener {

		@Override
		public void elementDirtyStateChanged(final IEditorInput input, final boolean isDirty) {
			if (input.equals(getEditorInput())) {
				firePropertyChange(PROP_DIRTY);
			}
		}

		@Override
		public void elementContentReplaced(final IEditorInput input) {
			if (!input.equals(getEditorInput())) {
				return;
			}
			final var newType = LibraryElementProvider.INSTANCE.getLibraryElement(getEditorInput());
			commandStack.setUndoContext(LibraryElementProvider.INSTANCE.getUndoContext(getEditorInput()));
			clearEditorContent();
			createEditorContent();
			setPartName(newType.getName());
			restoreOpenEditor();
		}

		@Override
		public void elementDeleted(final IEditorInput input) {
			if (input.equals(getEditorInput())) {
				close(false);
			}
		}

		@Override
		public void elementMoved(final IEditorInput originalInput, final IEditorInput movedInput) {
			if (originalInput.equals(getEditorInput())) {
				setInput(movedInput);
			}
		}
	}
}
