/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Daniel Lundhuber, Bianca Wiesmayr- initial API and
 *   			implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.systemmanagement.ui.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.gef.widgets.TypeInfoWidget;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer.SystemElementItemProviderAdapterFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.gef.ui.properties.UndoablePropertySheetPage;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

public class SystemEditor extends EditorPart
		implements CommandStackEventListener, ITabbedPropertySheetPageContributor, ISelectionListener {

	private static final ComposedAdapterFactory systemAdapterFactory = new ComposedAdapterFactory(createFactoryList());

	private AutomationSystem system;

	private Form form;

	private TypeInfoWidget typeInfo;
	private TreeViewer appTreeViewer;
	private TreeViewer sysConfTreeViewer;

	private ActionRegistry actionRegistry;
	private List<String> selectionActions = new ArrayList<>();
	private List<String> stackActions = new ArrayList<>();
	private List<String> propertyActions = new ArrayList<>();

	private Adapter appListener = new AdapterImpl() {
		@Override
		public void notifyChanged(Notification notification) {
			if ((null != appTreeViewer) && (!appTreeViewer.getControl().isDisposed())) {
				appTreeViewer.refresh();
			}
		}
	};

	private Adapter sysConfListener = new AdapterImpl() {
		@Override
		public void notifyChanged(Notification notification) {
			if ((null != sysConfTreeViewer) && (!sysConfTreeViewer.getControl().isDisposed())) {
				sysConfTreeViewer.refresh();
			}
		}
	};

	@Override
	public void stackChanged(CommandStackEvent event) {
		updateActions(stackActions);
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public String getContributorId() {
		return "org.eclipse.fordiac.ide.datatypeeditor.editors.DataTypeEditor"; //$NON-NLS-1$
	}

	@Override
	public void dispose() {
		if (null != system) {
			getCommandStack().removeCommandStackEventListener(this);
			system.eAdapters().remove(appListener);
			system.getSystemConfiguration().eAdapters().remove(sysConfListener);
		}
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
		getActionRegistry().dispose();
		super.dispose();
	}

	@Override
	protected void firePropertyChange(int property) {
		super.firePropertyChange(property);
		updateActions(propertyActions);
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
		// TODO implement save as new data type method, update isSaveAsAllowed()
		// accordingly
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setInput(input);
		setSite(site);
		site.getWorkbenchWindow().getSelectionService().addSelectionListener(this);
		loadSystem();
		initializeActionRegistry();
		setActionHandlers(site);
	}

	private void loadSystem() {
		if (getEditorInput() instanceof FileEditorInput) {
			system = SystemManager.INSTANCE.getSystem(((FileEditorInput) getEditorInput()).getFile());
			if (null != system) {
				getCommandStack().addCommandStackEventListener(this);
				setPartName(system.getName());
				system.eAdapters().add(appListener);
				system.getSystemConfiguration().eAdapters().add(sysConfListener);
			}
		}
	}

	private void setActionHandlers(IEditorSite site) {
		ActionRegistry registry = getActionRegistry();
		IActionBars bars = site.getActionBars();
		String id = ActionFactory.UNDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.REDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.DELETE.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		bars.updateActionBars();
	}

	@Override
	public boolean isDirty() {
		return ((null != system) && getCommandStack().isDirty());
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public void executeCommand(Command cmd) {
		CommandStack commandStack = getCommandStack();
		if (null != commandStack && cmd.canExecute()) {
			commandStack.execute(cmd);
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());

		form = toolkit.createForm(parent);
		form.getBody().setLayout(new GridLayout(1, true));

		SashForm sash = new SashForm(form.getBody(), SWT.VERTICAL);
		toolkit.adapt(sash);
		sash.setLayoutData(new GridData(GridData.FILL_BOTH));

		createInfoSection(toolkit, sash);

		Composite bottomComp = toolkit.createComposite(sash);
		bottomComp.setLayout(new GridLayout(2, true));
		bottomComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createApplicationsSection(toolkit, bottomComp);

		createSysconfSection(toolkit, bottomComp);

		if (null != system) {
			typeInfo.initialize(system, this::executeCommand);
			typeInfo.refresh();
			appTreeViewer.setInput(system.getApplication());
			sysConfTreeViewer.setInput(system.getSystemConfiguration());
		}
	}

	private void createInfoSection(FormToolkit toolkit, SashForm sash) {
		Section infoSection = toolkit.createSection(sash, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED);
		infoSection.setText("System Information:");
		infoSection.setLayout(new GridLayout(1, true));
		infoSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		typeInfo = new TypeInfoWidget(toolkit);
		Composite typeInfoRoot = typeInfo.createControls(infoSection);
		infoSection.setClient(typeInfoRoot);
	}

	private void createApplicationsSection(FormToolkit toolkit, Composite bottomComp) {
		Section appSection = toolkit.createSection(bottomComp, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED);
		appSection.setText("Applications:");
		appSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite appSecComposite = toolkit.createComposite(appSection);
		appSecComposite.setLayout(new GridLayout(2, false));
		appSection.setClient(appSecComposite);

		Tree tree = toolkit.createTree(appSecComposite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		appTreeViewer = new TreeViewer(tree);
		appTreeViewer.setContentProvider(new AdapterFactoryContentProvider(systemAdapterFactory) {
			@Override
			public boolean hasChildren(Object element) {
				return (element instanceof EList<?>) || super.hasChildren(element);
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return getChildren(inputElement);
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof EList<?>) {
					return ((EList<?>) parentElement).toArray();
				}
				return super.getChildren(parentElement);
			}
		});

		appTreeViewer.setLabelProvider(new AdapterFactoryLabelProvider(systemAdapterFactory));
	}

	private void createSysconfSection(FormToolkit toolkit, Composite bottomComp) {
		Section sysConfSection = toolkit.createSection(bottomComp,
				Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED);
		sysConfSection.setText("System Configuration:");
		sysConfSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite sysConfSecComposite = toolkit.createComposite(sysConfSection);
		sysConfSecComposite.setLayout(new GridLayout(2, false));
		sysConfSection.setClient(sysConfSecComposite);

		Tree tree = toolkit.createTree(sysConfSecComposite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		sysConfTreeViewer = new TreeViewer(tree);
		sysConfTreeViewer.setContentProvider(new AdapterFactoryContentProvider(systemAdapterFactory));
		sysConfTreeViewer.setLabelProvider(new AdapterFactoryLabelProvider(systemAdapterFactory));
	}

	@Override
	public void setFocus() {
		form.setFocus();
	}

	public CommandStack getCommandStack() {
		return (null != system) ? system.getCommandStack() : null;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (this.equals(getSite().getPage().getActiveEditor())) {
			updateActions(selectionActions);
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	private void createActions() {
		ActionRegistry registry = getActionRegistry();
		IAction action;

		action = new UndoAction(this);
		registry.registerAction(action);
		getStackActions().add(action.getId());

		action = new RedoAction(this);
		registry.registerAction(action);
		getStackActions().add(action.getId());
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (adapter == org.eclipse.ui.views.properties.IPropertySheetPage.class) {
			return adapter.cast(new UndoablePropertySheetPage(getCommandStack(),
					getActionRegistry().getAction(ActionFactory.UNDO.getId()),
					getActionRegistry().getAction(ActionFactory.REDO.getId())));
		}
		if (adapter == CommandStack.class) {
			return adapter.cast(getCommandStack());
		}
		if (adapter == ActionRegistry.class) {
			return adapter.cast(getActionRegistry());
		}
		return super.getAdapter(adapter);
	}

	private List<String> getStackActions() {
		return stackActions;
	}

	private void initializeActionRegistry() {
		createActions();
		updateActions(propertyActions);
		updateActions(stackActions);
	}

	private void updateActions(List<String> actionIds) {
		ActionRegistry registry = getActionRegistry();
		actionIds.forEach(id -> {
			IAction action = registry.getAction(id);
			if (action instanceof UpdateAction) {
				((UpdateAction) action).update();
			}
		});
	}

	private ActionRegistry getActionRegistry() {
		if (null == actionRegistry) {
			actionRegistry = new ActionRegistry();
		}
		return actionRegistry;
	}

	private static List<AdapterFactory> createFactoryList() {
		ArrayList<AdapterFactory> factories = new ArrayList<>(2);
		factories.add(new SystemElementItemProviderAdapterFactory());
		factories.add(new DataItemProviderAdapterFactory());
		return factories;
	}
}
