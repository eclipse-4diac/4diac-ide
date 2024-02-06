/*******************************************************************************
 * Copyright (c) 2020, 2023 Primetals Technologies Germany GmbH,
 *                          Johannes Kepler University Linz,
 *                          Primetals Technologies Austria GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.editors;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.application.editors.ApplicationEditor;
import org.eclipse.fordiac.ide.application.editors.ApplicationEditorInput;
import org.eclipse.fordiac.ide.application.editors.SubAppNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.SubApplicationEditorInput;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeAndSubAppInstanceViewerInput;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeInstanceViewer;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.DiagramOutlinePage;
import org.eclipse.fordiac.ide.gef.annotation.FordiacMarkerGraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.validation.ValidationJob;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.model.ui.editors.AbstractBreadCrumbEditor;
import org.eclipse.fordiac.ide.model.ui.listeners.EditorTabCommandStackListener;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceDiagramEditor;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceEditorInput;
import org.eclipse.fordiac.ide.subapptypeeditor.viewer.SubappInstanceViewer;
import org.eclipse.fordiac.ide.systemconfiguration.editor.SystemConfigurationEditor;
import org.eclipse.fordiac.ide.systemconfiguration.editor.SystemConfigurationEditorInput;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.changelistener.IEditorFileChangeListener;
import org.eclipse.fordiac.ide.systemmanagement.ui.providers.AutomationSystemProviderAdapterFactory;
import org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer.SystemLabelProvider;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AutomationSystemEditor extends AbstractBreadCrumbEditor implements IEditorFileChangeListener {

	private AutomationSystem system;
	private DiagramOutlinePage outlinePage;
	private final EditorTabCommandStackListener subEditorCommandStackListener;
	private GraphicalAnnotationModel annotationModel;
	private ValidationJob validationJob;

	public AutomationSystemEditor() {
		subEditorCommandStackListener = new EditorTabCommandStackListener(this);
	}

	private final Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (TypeEntry.TYPE_ENTRY_FILE_FEATURE.equals(notification.getFeature())) {
				Display.getDefault().asyncExec(() -> {
					if (null != system) {
						// the input should be set before the title is updated
						setInput(new FileEditorInput(system.getTypeEntry().getFile()));
						setPartName(system.getName());
					}
				});
			}
		}
	};

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, input);
		loadSystem();
		if (system != null) {
			hookSystemEntry(system.getTypeEntry());

		}
	}

	private void hookSystemEntry(final TypeEntry typeEntry) {
		annotationModel = new FordiacMarkerGraphicalAnnotationModel(typeEntry.getFile());
		validationJob = new ValidationJob(getPartName(), getCommandStack(), annotationModel);
		typeEntry.eAdapters().add(adapter);
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

	private void loadSystem() {
		if (getEditorInput() instanceof final FileEditorInput fileEI) {
			system = SystemManager.INSTANCE.getSystem(fileEI.getFile());
			// register as listener and call this method
			if (null != system) {
				getCommandStack().addCommandStackEventListener(this);
				getCommandStack().addCommandStackEventListener(subEditorCommandStackListener);
				setPartName(system.getName());
			}
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
		if (model instanceof IFile) {
			return new SystemEditor();
		}

		if (model instanceof CFBInstance) {
			return new CompositeInstanceViewer();
		}

		if (model instanceof final SubApp subApp) {
			if (subApp.isTyped() || subApp.isContainedInTypedInstance() || subApp.isUnfolded()) {
				return new SubappInstanceViewer();
			}
			return new SubAppNetworkEditor();
		}

		if (model instanceof Application) {
			return new ApplicationEditor();
		}
		if (model instanceof SystemConfiguration) {
			return new SystemConfigurationEditor();
		}
		if (model instanceof Device) {
			return new SystemConfigurationEditor();
		}
		if (model instanceof Resource) {
			return new ResourceDiagramEditor();
		}

		return null;
	}

	@Override
	protected IEditorInput createEditorInput(final Object model) {
		if (model instanceof IFile) {
			return getEditorInput();
		}
		if (model instanceof final SubApp subApp) {
			if ((subApp.isTyped()) || (subApp.isContainedInTypedInstance()) || subApp.isUnfolded()) {
				return new CompositeAndSubAppInstanceViewerInput(subApp);
			}
			return new SubApplicationEditorInput(subApp);
		}

		if (model instanceof CFBInstance) {
			return new CompositeAndSubAppInstanceViewerInput((FB) model);
		}

		if (model instanceof final Application app) {
			return new ApplicationEditorInput(app);
		}
		if (model instanceof final SystemConfiguration sysConf) {
			return new SystemConfigurationEditorInput(sysConf);
		}
		if (model instanceof final Device dev) {
			return new SystemConfigurationEditorInput(dev.getSystemConfiguration());
		}
		if (model instanceof final Resource res) {
			return new ResourceEditorInput(res);
		}
		return null;
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		if (null != system) {
			final WorkspaceModifyOperation operation = new WorkspaceModifyOperation(getFile().getParent()) {

				@Override
				protected void execute(final IProgressMonitor monitor)
						throws CoreException, InvocationTargetException, InterruptedException {
					system.getTypeEntry().save(system, monitor);
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
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}

	}

	@Override
	protected AdapterFactoryContentProvider createBreadcrumbContentProvider() {
		return new AdapterFactoryContentProvider(new AutomationSystemProviderAdapterFactory());
	}

	@Override
	protected AdapterFactoryLabelProvider createBreadcrumbLabelProvider() {
		return new SystemLabelProvider();
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
				final TypeEntry oldSystemEntry = system.getTypeEntry();

				system.setName(TypeEntry.getTypeNameFromFile(file));

				final TypeEntry newSystemEntry = TypeLibraryManager.INSTANCE.getTypeLibrary(file.getProject())
						.createTypeEntry(file);
				newSystemEntry.save(system, monitor);
				oldSystemEntry.eAdapters().remove(adapter);
				oldSystemEntry.setType(null);
				hookSystemEntry(newSystemEntry);
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
			return adapter.cast(new TabbedPropertySheetPage(this));
		}
		if (adapter == IContentOutlinePage.class) {
			if (null == outlinePage) {
				outlinePage = new DiagramOutlinePage(getActiveEditor().getAdapter(GraphicalViewer.class));
			}
			return adapter.cast(outlinePage);
		}
		if (adapter == AutomationSystem.class) {
			return adapter.cast(system);
		}
		if (adapter == GraphicalAnnotationModel.class) {
			return adapter.cast(annotationModel);
		}
		return super.getAdapter(adapter);
	}

	@Override
	public CommandStack getCommandStack() {
		return (null != system) ? system.getCommandStack() : null;
	}

	@Override
	public String getContributorId() {
		return DiagramEditorWithFlyoutPalette.PROPERTY_CONTRIBUTOR_ID;
	}

	@Override
	public IFile getFile() {
		return system.getTypeEntry().getFile();
	}

	@Override
	public void dispose() {
		if (system != null && system.getTypeEntry() != null && system.getTypeEntry().eAdapters().contains(adapter)) {
			system.getTypeEntry().eAdapters().remove(adapter);
		}

		// get these values here before calling super dispose
		final boolean dirty = isDirty();
		if (null != getCommandStack()) {
			getCommandStack().removeCommandStackEventListener(subEditorCommandStackListener);
		}
		if (validationJob != null) {
			validationJob.dispose();
		}
		if (annotationModel != null) {
			annotationModel.dispose();
		}

		super.dispose();

		if (dirty && system != null) {
			((SystemEntry) system.getTypeEntry()).setSystem(null);
			system = null;
			SystemManager.INSTANCE.notifyListeners();
		}
	}

	@Override
	public void reloadFile() {
		final CommandStack commandStack = system.getCommandStack();

		final String path = getBreadcrumb().serializePath();

		final SystemEntry typeEntry = (SystemEntry) system.getTypeEntry();

		if (typeEntry.eAdapters().contains(adapter)) {
			typeEntry.eAdapters().remove(adapter);
		}

		typeEntry.setSystem(null);
		system = typeEntry.getSystem();
		system.setCommandStack(commandStack);
		getCommandStack().flush();
		typeEntry.eAdapters().add(adapter);
		setPartName(system.getName());

		if (!getBreadcrumb().openPath(path, system)) {
			if (!system.getApplication().isEmpty()) {
				OpenListenerManager.openEditor(system.getApplication().get(0));
				showReloadErrorMessage(path, "Showing first application.");
			} else {
				OpenListenerManager.openEditor(system);
				showReloadErrorMessage(path, "Showing system.");
			}
		}
		selectRootModelOfEditor();
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
			selection = viewer.getEditPartRegistry().get(diagramEditor.getModel());
		}
		if (selection == null) {
			selection = viewer.getRootEditPart();
		}
		return selection;
	}

	@Override
	public void updateEditorInput(final FileEditorInput newInput) {
		setInput(newInput);
		firePropertyChange(IWorkbenchPart.PROP_TITLE);
	}

}
