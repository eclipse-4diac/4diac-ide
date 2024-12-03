/*******************************************************************************
 * Copyright (c) 2020, 2024 Johannes Kepler University, Linz,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Daniel Lindhuber, Bianca Wiesmayr - initial API and
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
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModelListener;
import org.eclipse.fordiac.ide.gef.widgets.PackageInfoWidget;
import org.eclipse.fordiac.ide.model.commands.change.ChangeApplicationOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateApplicationCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteApplicationCommand;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.emf.SingleRecursiveContentAdapter;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.systemmanagement.ui.providers.SystemElementItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
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
	private TableViewer appTableViewer;
	private TreeViewer sysConfTreeViewer;

	private ActionRegistry actionRegistry;
	private final List<String> selectionActions = new ArrayList<>();
	private final List<String> stackActions = new ArrayList<>();
	private final List<String> propertyActions = new ArrayList<>();

	private final Adapter appListener = new SingleRecursiveContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			Display.getDefault().asyncExec(() -> {
				if ((null != appTableViewer) && (!appTableViewer.getControl().isDisposed())) {
					appTableViewer.refresh();
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
		GridLayoutFactory.fillDefaults().applyTo(form.getBody());

		final Composite parentComposite = toolkit.createComposite(form.getBody());
		toolkit.adapt(parentComposite);
		parentComposite.setLayout(new GridLayout(1, false));
		parentComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		createInfoSection(toolkit, parentComposite);

		final Composite bottomComp = toolkit.createComposite(parentComposite);
		bottomComp.setLayout(new GridLayout(2, true));
		bottomComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createApplicationsSection(toolkit, bottomComp);

		createSysconfSection(toolkit, bottomComp);

		getSite().setSelectionProvider(this);

		if (null != system) {
			typeInfo.initialize(system, this::executeCommand);
			typeInfo.refresh();
			appTableViewer.setInput(system.getApplication());
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

	private void createInfoSection(final FormToolkit toolkit, final Composite parent) {
		final Section infoSection = createExpandableSection(toolkit, parent, Messages.SystemEditor_SystemInformation);

		typeInfo = new PackageInfoWidget(toolkit, () -> annotationModel);
		final Composite composite = toolkit.createComposite(infoSection);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).applyTo(composite);
		typeInfo.createControls(composite);
		infoSection.setClient(composite);
	}

	private static Section createExpandableSection(final FormToolkit toolkit, final Composite parent,
			final String text) {
		final Section section = toolkit.createSection(parent,
				ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		section.setText(text);
		return section;
	}

	private void createApplicationsSection(final FormToolkit toolkit, final Composite bottomComp) {
		final Section appSection = createExpandableSection(toolkit, bottomComp, Messages.SystemEditor_Applications);

		final Composite appSecComposite = toolkit.createComposite(appSection);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(appSecComposite);
		appSection.setClient(appSecComposite);

		final AddDeleteReorderListWidget actionMgmButtons = new AddDeleteReorderListWidget();
		actionMgmButtons.createControls(appSecComposite, toolkit);

		appTableViewer = TableWidgetFactory.createTableViewer(appSecComposite);
		configureActionTableLayout(appTableViewer);
		appTableViewer.setContentProvider(ArrayContentProvider.getInstance());

		actionMgmButtons.bindToTableViewer(appTableViewer, cmd -> getCommandStack().execute(cmd),
				ref -> new CreateApplicationCommand(system, getAppName((Application) ref)),
				ref -> new DeleteApplicationCommand((Application) ref),
				ref -> new ChangeApplicationOrderCommand((Application) ref, true),
				ref -> new ChangeApplicationOrderCommand((Application) ref, false));
	}

	private static String getAppName(final Application ref) {
		return (ref != null) ? ref.getName() : null;
	}

	private void configureActionTableLayout(final TableViewer appTableViewer) {
		final Table table = appTableViewer.getTable();
		final TableViewerColumn nameCol = new TableViewerColumn(appTableViewer, SWT.LEFT);
		nameCol.getColumn().setText(FordiacMessages.Name);
		nameCol.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public Image getImage(final Object element) {
				return FordiacImage.ICON_APPLICATION.getImage();
			}

			@Override
			public String getText(final Object element) {
				if (element instanceof final Application app) {
					return app.getName();
				}
				return element.toString();
			}

		});

		final TableViewerColumn commentCol = new TableViewerColumn(appTableViewer, SWT.LEFT);
		commentCol.getColumn().setText(FordiacMessages.Comment);
		commentCol.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final Application app) {
					return app.getComment();
				}
				return element.toString();
			}
		});

		final TableLayout tabLayout = new TableLayout();
		tabLayout.addColumnData(new ColumnWeightData(1, 50));
		tabLayout.addColumnData(new ColumnWeightData(2, 50));

		table.setLayout(tabLayout);
		appTableViewer.setColumnProperties(
				new String[] { ApplicationViewerCellModifier.APP_NAME, ApplicationViewerCellModifier.APP_COMMENT });
		appTableViewer.setCellModifier(new ApplicationViewerCellModifier(getCommandStack()));
		appTableViewer.setCellEditors(new CellEditor[] { new TextCellEditor(table), new TextCellEditor(table) });
	}

	private void createSysconfSection(final FormToolkit toolkit, final Composite bottomComp) {
		final Section sysConfSection = createExpandableSection(toolkit, bottomComp,
				Messages.SystemEditor_SystemConfiguration);
		sysConfSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite sysConfSecComposite = toolkit.createComposite(sysConfSection);
		GridLayoutFactory.fillDefaults().applyTo(sysConfSecComposite);
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
