/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Germany GmbH, Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.editors;

import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.application.editors.ApplicationEditor;
import org.eclipse.fordiac.ide.application.editors.ApplicationEditorInput;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.SubAppNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.SubApplicationEditorInput;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeAndSubAppInstanceViewerInput;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeInstanceViewer;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.gef.DiagramOutlinePage;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.model.ui.editors.AbstractBreadCrumbEditor;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceDiagramEditor;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceEditorInput;
import org.eclipse.fordiac.ide.subapptypeeditor.viewer.SubappInstanceViewer;
import org.eclipse.fordiac.ide.systemconfiguration.editor.SystemConfigurationEditor;
import org.eclipse.fordiac.ide.systemconfiguration.editor.SystemConfigurationEditorInput;
import org.eclipse.fordiac.ide.systemmanagement.AutomationSystemListener;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.Activator;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.systemmanagement.ui.providers.AutomationSystemProviderAdapterFactory;
import org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer.SystemLabelProvider;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AutomationSystemEditor extends AbstractBreadCrumbEditor implements AutomationSystemListener {

	private static final int OVERWRITE_CHANGES = 0;
	private static final int SAVE_CHANGES = 1;
	private static final int DISCARD_CHANGES = 2;
	private AutomationSystem system;
	private DiagramOutlinePage outlinePage;

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, input);
		loadSystem();
		SystemManager.INSTANCE.addAutomationSystemListener(this);
	}

	@Override
	protected Composite createPageContainer(final Composite parent) {
		final Composite pageContainer = new Composite(parent, SWT.NONE);
		pageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		return pageContainer;
	}

	@Override
	protected void createPages() {
		try {
			final int pagenum = addPage(new SystemEditor(), getEditorInput());
			getModelToEditorNumMapping().put(system, Integer.valueOf(pagenum));
		} catch (final PartInitException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	private void loadSystem() {

		if (getEditorInput() instanceof FileEditorInput) {
			system = SystemManager.INSTANCE.getSystem(((FileEditorInput) getEditorInput()).getFile()); // register as
			// listener and
			// call this
			// method
			if (null != system) {
				getCommandStack().addCommandStackEventListener(this);
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

		if (model instanceof SubApp) {
			if ((((SubApp) model).isTyped()) || (((SubApp) model).isContainedInTypedInstance())) {
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
		if (model instanceof SubApp) {
			final SubApp subApp = (SubApp) model;
			if ((subApp.isTyped()) || (subApp.isContainedInTypedInstance())) {
				return new CompositeAndSubAppInstanceViewerInput(subApp);
			}
			return new SubApplicationEditorInput(subApp);
		}

		if (model instanceof CFBInstance) {
			return new CompositeAndSubAppInstanceViewerInput((FB) model);
		}

		if (model instanceof Application) {
			return new ApplicationEditorInput((Application) model);
		}
		if (model instanceof SystemConfiguration) {
			return new SystemConfigurationEditorInput((SystemConfiguration) model);
		}
		if (model instanceof Device) {
			return new SystemConfigurationEditorInput(((Device) model).getSystemConfiguration());
		}
		if (model instanceof Resource) {
			return new ResourceEditorInput((Resource) model);
		}
		return null;
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		if (null != system) {
			SystemManager.INSTANCE.removeAutomationSystemListener(this);
			SystemManager.saveSystem(system);
			getCommandStack().markSaveLocation();
			firePropertyChange(IEditorPart.PROP_DIRTY);
			SystemManager.INSTANCE.addAutomationSystemListener(this);
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
				final EObject targetmodel = getTargetModel(Arrays.copyOfRange(nameList, 1, nameList.length));
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
		saveAsDialog.setOriginalName(system.getSystemFile().getName());
		saveAsDialog.open();
		final IPath path = saveAsDialog.getResult();
		if (path == null) {
			return;
		}

		final IPath fullPath = system.getSystemFile().getFullPath();
		if (fullPath.equals(path)) {
			doSave(null);
			return;
		}
		SystemManager.INSTANCE.removeAutomationSystemListener(this);
		final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		SystemManager.saveSystem(system, file);
		SystemManager.INSTANCE.addAutomationSystemListener(this);

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
	protected void gotoFBNetworkElement(final Object object) {
		final String[] split = ((String) object).split("\\."); //$NON-NLS-1$
		if (split.length >= 2) {
			final EObject targetmodel = getTargetModel(Arrays.copyOf(split, split.length - 1));
			if (null != targetmodel) {
				getBreadcrumb().setInput(targetmodel);
				final FBNetworkEditor fbEditor = getAdapter(FBNetworkEditor.class);
				if (null != fbEditor) {
					final FBNetworkElement elementToSelect = fbEditor.getModel()
							.getElementNamed(split[split.length - 1]);
					fbEditor.selectElement(elementToSelect);
				}
			}
		}
	}

	private EObject getTargetModel(final String[] path) {
		EObject retVal = system.getApplicationNamed(path[0]);
		if (null != retVal) {
			if (path.length > 1) {
				// we are within a subapplication in the application
				retVal = parseSubappPath(((Application) retVal).getFBNetwork(),
						Arrays.copyOfRange(path, 1, path.length));
			}
		} else if (path.length > 2) {
			// we need to have at least a device and a resource in the path
			retVal = system.getDeviceNamed(path[0]);
			if (null != retVal) {
				retVal = ((Device) retVal).getResourceNamed(path[1]);
				if ((null != retVal) && (path.length > 2)) {
					// we are within a subapplication in the resource
					retVal = parseSubappPath(((Resource) retVal).getFBNetwork(),
							Arrays.copyOfRange(path, 2, path.length));
				}
			}
		}
		return retVal;
	}

	private static EObject parseSubappPath(FBNetwork network, final String[] path) {
		EObject retVal = null;
		for (final String element : path) {
			retVal = network.getElementNamed(element);
			if (retVal instanceof SubApp) {
				network = getSubAppNetwork((SubApp) retVal);
			} else if (retVal instanceof CFBInstance) {
				network = getCFBNetwork((CFBInstance) retVal);
			} else {
				return null;
			}
			if (null == network) {
				// we couldn't load the network, memento seems to be broken
				return null;
			}
		}
		return retVal;
	}

	private static FBNetwork getSubAppNetwork(final SubApp subApp) {
		FBNetwork network = subApp.getSubAppNetwork();
		if (null == network) {
			network = subApp.loadSubAppNetwork();
		}
		return network;
	}

	private static FBNetwork getCFBNetwork(final CFBInstance cfb) {
		FBNetwork network = cfb.getCfbNetwork();
		if (null == network) {
			network = cfb.loadCFBNetwork();
		}
		return network;
	}

	@Override
	public void dispose() {
		SystemManager.INSTANCE.removeAutomationSystemListener(this);
		super.dispose();
	}

	@Override
	public void automationSystemChanged(final IFile file) {
		if (file.equals(system.getSystemFile())) {
			Display.getDefault().asyncExec(() -> openFileChangedDialog(file));
		}
	}

	private void openFileChangedDialog(final IFile file) {
		final String info = Messages.AutomationSystemEditor_Info.replace("{placeholder}", //$NON-NLS-1$
				file.getFullPath().toOSString());
		final MessageDialog dialog = new MessageDialog(getSite().getShell(), Messages.AutomationSystemEditor_Title,
				null, info, MessageDialog.INFORMATION,
				new String[] { Messages.AutomationSystemEditor_Overwrite_Changes,
						Messages.AutomationSystemEditor_Save_Changes, Messages.AutomationSystemEditor_Discard_Changes },
				0);
		final int returnCode = dialog.open();
		handleReturnCode(returnCode, file);
	}

	private void handleReturnCode(final int returnCode, final IFile file) {
		switch (returnCode) {
		case OVERWRITE_CHANGES:
			doSave(new NullProgressMonitor());
			break;
		case SAVE_CHANGES:
			doSaveAs();
			replaceSystemFile(file);
			break;
		case DISCARD_CHANGES:
			replaceSystemFile(file);
			break;
		default:
			break;
		}

	}



	private void replaceSystemFile(final IFile file) {
		system = SystemManager.INSTANCE.replaceSystemFromFile(system, file);
		if (!system.getApplication().isEmpty()) {
			OpenListenerManager.openEditor(system.getApplication().get(0));
		} else {
			EditorUtils.CloseEditor.run(this);
		}
	}

}
