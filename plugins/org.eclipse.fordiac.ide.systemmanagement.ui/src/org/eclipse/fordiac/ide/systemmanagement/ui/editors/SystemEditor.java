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
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModelListener;
import org.eclipse.fordiac.ide.gef.widgets.PackageInfoWidget;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.providers.SystemElementItemProviderAdapterFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorSite;

public class SystemEditor extends EditorPart
		implements CommandStackEventListener, ISelectionListener, ISelectionProvider, IReusableEditor {

	private static final ComposedAdapterFactory systemAdapterFactory = new ComposedAdapterFactory(createFactoryList());

	private AutomationSystem system;

	private GraphicalAnnotationModel annotationModel;

	private Form form;

	private PackageInfoWidget typeInfo;
	private TreeViewer appTreeViewer;
	private TreeViewer sysConfTreeViewer;

	private ActionRegistry actionRegistry;
	private final List<String> selectionActions = new ArrayList<>();
	private final List<String> stackActions = new ArrayList<>();
	private final List<String> propertyActions = new ArrayList<>();

	private final Adapter appListener = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			Display.getDefault().asyncExec(() -> {
				if ((null != appTreeViewer) && (!appTreeViewer.getControl().isDisposed())) {
					appTreeViewer.refresh();
				}
			});
		}
	};

	private final Adapter sysConfListener = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			if ((null != sysConfTreeViewer) && (!sysConfTreeViewer.getControl().isDisposed())) {
				sysConfTreeViewer.refresh();
			}
		}
	};

	private final GraphicalAnnotationModelListener annotationModelListener = event -> {
		if (typeInfo != null && !form.isDisposed()) {
			form.getDisplay().asyncExec(typeInfo::refreshAnnotations);
		}
	};

	@Override
	public void stackChanged(final CommandStackEvent event) {
		updateActions(stackActions);
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	public void dispose() {
		if (null != system) {
			getCommandStack().removeCommandStackEventListener(this);
			system.eAdapters().remove(appListener);
			system.getSystemConfiguration().eAdapters().remove(sysConfListener);
		}
		removeAnnotationModelListener();
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
		getActionRegistry().dispose();
		super.dispose();
	}

	@Override
	protected void firePropertyChange(final int property) {
		super.firePropertyChange(property);
		updateActions(propertyActions);
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// with the breadcrumb based automation system editor this editor should not
		// support a save method
	}

	@Override
	public void doSaveAs() {
		// with the breadcrumb based automation system editor this editor should not
		// support a save as method
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		site.getWorkbenchWindow().getSelectionService().addSelectionListener(this);
		initializeActionRegistry();
		setActionHandlers(site);
	}

	@Override
	public void setInput(final IEditorInput input) {
		if (input instanceof final FileEditorInput fileEditorInput) {
			system = SystemManager.INSTANCE.getSystem(fileEditorInput.getFile());
			if (system != null) {
				getCommandStack().addCommandStackEventListener(this);
				setPartName(system.getName());
				system.eAdapters().add(appListener);
				system.getSystemConfiguration().eAdapters().add(sysConfListener);
			}
		}
		if (getSite() instanceof final MultiPageEditorSite multiPageEditorSite) {
			removeAnnotationModelListener();
			annotationModel = multiPageEditorSite.getMultiPageEditor().getAdapter(GraphicalAnnotationModel.class);
			addAnnotationModelListener();
		}
		super.setInputWithNotify(input);
	}

	private void setActionHandlers(final IEditorSite site) {
		final ActionRegistry registry = getActionRegistry();
		final IActionBars bars = site.getActionBars();
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

	public void executeCommand(final Command cmd) {
		final CommandStack commandStack = getCommandStack();
		if (null != commandStack && cmd.canExecute()) {
			commandStack.execute(cmd);
		}
	}

	@Override
	public void createPartControl(final Composite parent) {
		final FormToolkit toolkit = new FormToolkit(parent.getDisplay());

		form = toolkit.createForm(parent);
		form.getBody().setLayout(new GridLayout(1, true));

		final SashForm sash = new SashForm(form.getBody(), SWT.VERTICAL);
		toolkit.adapt(sash);
		sash.setLayoutData(new GridData(GridData.FILL_BOTH));

		createInfoSection(toolkit, sash);

		final Composite bottomComp = toolkit.createComposite(sash);
		bottomComp.setLayout(new GridLayout(2, true));
		bottomComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createApplicationsSection(toolkit, bottomComp);

		createSysconfSection(toolkit, bottomComp);

		getSite().setSelectionProvider(this);

		if (null != system) {
			typeInfo.initialize(system, this::executeCommand);
			typeInfo.refresh();
			appTreeViewer.setInput(system.getApplication());
			sysConfTreeViewer.setInput(system.getSystemConfiguration());
			addAnnotationModelListener();
		}
	}

	protected void addAnnotationModelListener() {
		if (annotationModel != null) {
			annotationModel.addAnnotationModelListener(annotationModelListener);
		}
	}

	protected void removeAnnotationModelListener() {
		if (annotationModel != null) {
			annotationModel.removeAnnotationModelListener(annotationModelListener);
		}
	}

	private void createInfoSection(final FormToolkit toolkit, final SashForm sash) {
		final Section infoSection = createExpandableSection(toolkit, sash, "System Information:");
		infoSection.setLayout(new GridLayout());

		typeInfo = new PackageInfoWidget(toolkit, () -> annotationModel);
		final Composite composite = toolkit.createComposite(infoSection);
		composite.setLayout(new GridLayout(2, true));
		typeInfo.createControls(composite);
		infoSection.setClient(composite);
	}

	private static Section createExpandableSection(final FormToolkit toolkit, final Composite parent,
			final String text) {
		final Section section = toolkit.createSection(parent,
				ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		section.setText(text);
		return section;
	}

	private void createApplicationsSection(final FormToolkit toolkit, final Composite bottomComp) {
		final Section appSection = createExpandableSection(toolkit, bottomComp, "Applications:");

		final Composite appSecComposite = toolkit.createComposite(appSection);
		appSecComposite.setLayout(new GridLayout(2, false));
		appSection.setClient(appSecComposite);

		final Tree tree = toolkit.createTree(appSecComposite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		appTreeViewer = new TreeViewer(tree);
		appTreeViewer.setContentProvider(new AdapterFactoryContentProvider(systemAdapterFactory) {
			@Override
			public boolean hasChildren(final Object element) {
				return (element instanceof EList<?>) || super.hasChildren(element);
			}

			@Override
			public Object[] getElements(final Object inputElement) {
				return getChildren(inputElement);
			}

			@Override
			public Object[] getChildren(final Object parentElement) {
				if (parentElement instanceof EList<?>) {
					return ((EList<?>) parentElement).toArray();
				}
				return super.getChildren(parentElement);
			}
		});

		appTreeViewer.setLabelProvider(new AdapterFactoryLabelProvider(systemAdapterFactory));
	}

	private void createSysconfSection(final FormToolkit toolkit, final Composite bottomComp) {
		final Section sysConfSection = createExpandableSection(toolkit, bottomComp, "System Configuration:");
		sysConfSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite sysConfSecComposite = toolkit.createComposite(sysConfSection);
		sysConfSecComposite.setLayout(new GridLayout(2, false));
		sysConfSection.setClient(sysConfSecComposite);

		final Tree tree = toolkit.createTree(sysConfSecComposite,
				SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
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
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		if (this.equals(getSite().getPage().getActiveEditor())) {
			updateActions(selectionActions);
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	private void createActions() {
		final ActionRegistry registry = getActionRegistry();
		IAction action;

		action = new UndoAction(this);
		registry.registerAction(action);
		getStackActions().add(action.getId());

		action = new RedoAction(this);
		registry.registerAction(action);
		getStackActions().add(action.getId());
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == CommandStack.class) {
			return adapter.cast(getCommandStack());
		}
		if (adapter == ActionRegistry.class) {
			return adapter.cast(getActionRegistry());
		}
		if (adapter == GraphicalAnnotationModel.class) {
			return adapter.cast(annotationModel);
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

	private void updateActions(final List<String> actionIds) {
		final ActionRegistry registry = getActionRegistry();
		actionIds.forEach(id -> {
			final IAction action = registry.getAction(id);
			if (action instanceof final UpdateAction ua) {
				ua.update();
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
		final ArrayList<AdapterFactory> factories = new ArrayList<>(2);
		factories.add(new SystemElementItemProviderAdapterFactory());
		factories.add(new DataItemProviderAdapterFactory());
		return factories;
	}

	@Override
	public ISelection getSelection() {
		return (system != null) ? new StructuredSelection(system) : StructuredSelection.EMPTY;
	}

	@Override
	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		// nothing to do here
	}

	@Override
	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		// nothing to do here
	}

	@Override
	public void setSelection(final ISelection selection) {
		// nothing to do here
	}

}
