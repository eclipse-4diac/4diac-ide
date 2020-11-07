/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl  - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.editors;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.application.editors.ApplicationEditor;
import org.eclipse.fordiac.ide.application.editors.ApplicationEditorInput;
import org.eclipse.fordiac.ide.application.editors.SubAppNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.SubApplicationEditorInput;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceDiagramEditor;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceEditorInput;
import org.eclipse.fordiac.ide.systemconfiguration.editor.SystemConfigurationEditor;
import org.eclipse.fordiac.ide.systemconfiguration.editor.SystemConfigurationEditorInput;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.Activator;
import org.eclipse.fordiac.ide.systemmanagement.ui.breadcrumb.BreadcrumbWidget;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AutomationSystemEditor extends MultiPageEditorPart
		implements CommandStackEventListener, ITabbedPropertySheetPageContributor {

	private AutomationSystem system;

	private Map<Object, Integer> modelToEditorNum = new HashMap<>();

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		loadSystem();
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(GridLayoutFactory.fillDefaults().equalWidth(true).spacing(0, 0).create());
		BreadcrumbWidget breadcrumb = new BreadcrumbWidget(parent);
		breadcrumb.setInput(system);
		breadcrumb.addSelectionChangedListener(
				event -> handleBreadCrumbSelection(((StructuredSelection) event.getSelection()).getFirstElement()));

		super.createPartControl(parent);

		((CTabFolder) getContainer()).setTabHeight(0); // we don't want the tabs to be seen
	}

	@Override
	protected Composite createPageContainer(Composite parent) {
		Composite pageContainer = new Composite(parent, SWT.NONE);
		pageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		return pageContainer;
	}

	private void loadSystem() {
		if (getEditorInput() instanceof FileEditorInput) {
			system = SystemManager.INSTANCE.getSystem(((FileEditorInput) getEditorInput()).getFile());
			if (null != system) {
				getCommandStack().addCommandStackEventListener(this);
				setPartName(system.getName());
			}
		}
	}

	@Override
	protected void createPages() {
		try {
			int pagenum = addPage(new SystemEditor(), getEditorInput());
			modelToEditorNum.put(system.getSystemFile(), pagenum); // need to use the file as reference as this is
																	// provided by the content providers
		} catch (PartInitException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	private void handleBreadCrumbSelection(Object element) {
		int pagenum = modelToEditorNum.computeIfAbsent(element, this::createEditor);
		if (-1 != pagenum) {
			setActivePage(pagenum);
		}
	}

	private int createEditor(Object model) {
		EditorPart part = createEditorPart(model);
		if (null != part) {
			IEditorInput input = createEditorInput(model);
			try {
				return addPage(part, input);
			} catch (PartInitException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
		return -1;
	}

	private static EditorPart createEditorPart(Object model) {
		if (model instanceof SubApp) {
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

	private static IEditorInput createEditorInput(Object model) {
		if (model instanceof SubApp) {
			return new SubApplicationEditorInput((SubApp) model);
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
	public void doSave(IProgressMonitor monitor) {
		if (null != system) {
			SystemManager.saveSystem(system);
			getCommandStack().markSaveLocation();
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}

	}

	@Override
	public void doSaveAs() {
		// currently not allowed
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public boolean isDirty() {
		return ((null != system) && getCommandStack().isDirty());
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (adapter == CommandStack.class) {
			return adapter.cast(getCommandStack());
		}
		if (adapter == IPropertySheetPage.class) {
			return adapter.cast(new TabbedPropertySheetPage(this));
		}
		return super.getAdapter(adapter);
	}

	@Override
	public void stackChanged(CommandStackEvent event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public void dispose() {
		if (null != system) {
			getCommandStack().removeCommandStackEventListener(this);
		}
		super.dispose();
	}

	public CommandStack getCommandStack() {
		return (null != system) ? system.getCommandStack() : null;
	}

	@Override
	public String getContributorId() {
		return DiagramEditorWithFlyoutPalette.PROPERTY_CONTRIBUTOR_ID;
	}

}
