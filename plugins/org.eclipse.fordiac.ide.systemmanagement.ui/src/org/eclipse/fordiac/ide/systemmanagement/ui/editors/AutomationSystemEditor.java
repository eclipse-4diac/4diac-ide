/*******************************************************************************
 * Copyright (c) 2020, 2024 Primetals Technologies Germany GmbH,
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
import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
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
import org.eclipse.fordiac.ide.model.edit.ITypeEntryEditor;
import org.eclipse.fordiac.ide.model.edit.TypeEntryAdapter;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
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
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.systemmanagement.ui.providers.AutomationSystemProviderAdapterFactory;
import org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer.StyledSystemLabelProvider;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.widget.SelectionTabbedPropertySheetPage;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;

public class AutomationSystemEditor extends AbstractBreadCrumbEditor implements ITypeEntryEditor {

	private AutomationSystem system;
	private DiagramOutlinePage outlinePage;
	private final EditorTabCommandStackListener subEditorCommandStackListener;
	private GraphicalAnnotationModel annotationModel;
	private ValidationJob validationJob;

	public AutomationSystemEditor() {
		subEditorCommandStackListener = new EditorTabCommandStackListener(this);
	}

	private final TypeEntryAdapter adapter = new TypeEntryAdapter(this);

	@Override
	public void createPartControl(final Composite parent) {
		if (system != null) {
			super.createPartControl(parent);
		} else {
			showLoadErrorMessage(parent);
		}
	}

	public void showLoadErrorMessage(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).applyTo(composite);

		final Image image = Display.getDefault().getSystemImage(SWT.ICON_ERROR);
		final Label imageLabel = new Label(composite, SWT.NULL);
		image.setBackground(imageLabel.getBackground());
		imageLabel.setImage(image);
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.BEGINNING).applyTo(imageLabel);

		final Label messageLabel = new Label(composite, SWT.NONE);
		messageLabel.setText(
				MessageFormat.format(Messages.AutomationSystemEditor_CouldNotLoadSystem, getEditorInput().getName()));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(messageLabel);
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
		if (model instanceof IFile) {
			return new SystemEditor();
		}

		if (model instanceof CFBInstance) {
			return new CompositeInstanceViewer();
		}

		if (model instanceof final SubApp subApp) {
			if (subApp.isTyped() || subApp.isContainedInTypedInstance()) {
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
			if ((subApp.isTyped()) || (subApp.isContainedInTypedInstance())) {
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
				final TypeEntry oldSystemEntry = system.getTypeEntry();

				system.setName(TypeEntry.getTypeNameFromFile(file));

				final TypeEntry newSystemEntry = TypeLibraryManager.INSTANCE.getTypeLibrary(file.getProject())
						.createTypeEntry(file);
				newSystemEntry.save(system, monitor);
				oldSystemEntry.eAdapters().remove(adapter);
				oldSystemEntry.setType(null);
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

	private IFile getFile() {
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
		}
	}

	@Override
	public void reloadType() {
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
		if (validationJob != null) {
			validationJob.dispose();
			validationJob = null;
		}
		if (annotationModel != null) {
			annotationModel.dispose();
			annotationModel = null;
		}

		if (input instanceof final FileEditorInput fileEI) {
			if (getEditorInput() == null) {
				system = SystemManager.INSTANCE.getSystem(fileEI.getFile());
				if (system != null) {
					getCommandStack().addCommandStackEventListener(this);
					getCommandStack().addCommandStackEventListener(subEditorCommandStackListener);
					system.getTypeEntry().eAdapters().add(adapter);
				}
			}
			setPartName(TypeEntry.getTypeNameFromFile(fileEI.getFile()));
			annotationModel = new FordiacMarkerGraphicalAnnotationModel(fileEI.getFile(), () -> system);
			validationJob = new ValidationJob(getPartName(), getCommandStack(), annotationModel);
			// inform child editors about the new file and that they should update the
			// annotation model. Currently we use for simplicity their existing editor input
			pages.stream().filter(IReusableEditor.class::isInstance).map(IReusableEditor.class::cast)
					.forEach(e -> e.setInput(e.getEditorInput()));
		}
		setInputWithNotify(input);
	}

	@Override
	public void setFocus() {
		super.setFocus();
		// we got focus check if the content needs reload
		adapter.checkFileReload();
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

}
