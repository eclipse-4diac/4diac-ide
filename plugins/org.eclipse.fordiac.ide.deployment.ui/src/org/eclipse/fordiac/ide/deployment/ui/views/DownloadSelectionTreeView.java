/*******************************************************************************
 * Copyright (c) 2008- 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.ui.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.util.ISelectedElementsChangedListener;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

/**
 * The Class DownloadSelectionTreeView.
 */
public class DownloadSelectionTreeView extends ViewPart {

	/** The viewer. */
	private ContainerCheckedTreeViewer viewer;

	public ContainerCheckedTreeViewer getViewer() {
		return viewer;
	}

	/** The drilldownadapter. */
	private DrillDownAdapter drillDownAdapter;

	private final List<Device> initialized = new ArrayList<>();

	/**
	 * The constructor.
	 */
	public DownloadSelectionTreeView() {
		SystemManager.INSTANCE.addWorkspaceListener(() -> Display.getDefault().asyncExec(() -> {
				if (!viewer.getTree().isDisposed()) {
					viewer.refresh(true);
				}
			})
		);
	}

	private void initializeDeviceProperties() {
		for (AutomationSystem system : SystemManager.INSTANCE.getSystems()) {
			for (Device dev : system.getSystemConfiguration().getDevices()) {
				if (!initialized.contains(dev)) {
					DownloadSelectionTree.initSelectedProperties(dev);
					initialized.add(dev);
				}

			}
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 * 
	 * @param parent the parent
	 */
	@Override
	public void createPartControl(final Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());

		initializeDeviceProperties();

		viewer = new DownloadSelectionTree(composite, SWT.FULL_SELECTION
				| SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

		drillDownAdapter = new DrillDownAdapter(viewer);

		GridData fillBoth = new GridData();
		fillBoth.horizontalAlignment = GridData.FILL;
		fillBoth.grabExcessHorizontalSpace = true;
		fillBoth.verticalAlignment = GridData.FILL;
		fillBoth.grabExcessVerticalSpace = true;
		viewer.getTree().setLayoutData(fillBoth);

		
		viewer.setInput(getViewSite());
		
		viewer.addCheckStateListener(event -> notifyListeners());

		getSite().setSelectionProvider(viewer);

		getSite().getPage().addSelectionListener("org.eclipse.fordiac.ide.deployment.ui.views.DownloadSelectionTreeView", //$NON-NLS-1$
				(final IWorkbenchPart part, final ISelection selection) -> {
					if ((part instanceof DownloadSelectionTreeView) && (treeView != part)) {
						treeView = (DownloadSelectionTreeView) part;
						treeView.addSelectedElementsChangedListener(getChangeListener());
						getChangeListener().selectionChanged();
					}
				});

		downloadButton = new Button(composite, SWT.NONE);
		downloadButton.setText(Messages.Mode_DownloadButtonLabel);
		downloadButton.setImage(FordiacImage.ICON_Download.getImage());
		downloadButton.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false));
		downloadButton.setEnabled(false);
		downloadButton.addListener( SWT.Selection, e ->{
			Object[] selected = treeView.getSelectedElements();
			clearDownloadConsole();
			DeploymentCoordinator deployment = DeploymentCoordinator.getInstance();
			deployment.performDeployment(selected);
		});

		makeActions();
		createToolbarbuttons();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}


	private static void clearDownloadConsole() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewPart view = page.findView("org.eclipse.fordiac.ide.deployment.ui.views.Output"); //$NON-NLS-1$
		
		if (view instanceof Output){
			Output output = (Output)view;
			output.clearOutput();
		}				
	}

	/** The tree view. */
	private DownloadSelectionTreeView treeView = null;
	private ISelectedElementsChangedListener changeListener = null;
	

	private ISelectedElementsChangedListener getChangeListener() {
		if(null == changeListener){
			changeListener = () -> {
				if((null != treeView) && (!downloadButton.isDisposed())){						
					downloadButton.setEnabled((treeView.getSelectedElements().length > 0));						
				}
			};
		}
		return changeListener;
	}

	@Override
	public void dispose() {		
		super.dispose();
		if(null != treeView){
			treeView.removeSelectedElementsChangedListener(getChangeListener());
		}
	}
	
	
	/**
	 * Hook context menu.
	 */
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager(
				Messages.DownloadSelectionTreeView_LABEL_PopupMenu);
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(final IMenuManager manager) {
				DownloadSelectionTreeView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	/**
	 * Contribute to action bars.
	 */
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	/**
	 * Fill local pull down.
	 * 
	 * @param manager
	 *          the manager
	 */
	private void fillLocalPullDown(final IMenuManager manager) {
		// not used
	}

	/**
	 * Fill context menu.
	 * 
	 * @param manager
	 *          the manager
	 */
	private void fillContextMenu(final IMenuManager manager) {
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	/**
	 * Fill local tool bar.
	 * 
	 * @param manager
	 *          the manager
	 */
	private void fillLocalToolBar(final IToolBarManager manager) {
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	/**
	 * Creates the toolbarbuttons.
	 */
	private void createToolbarbuttons() {
		IToolBarManager toolBarManager = getViewSite().getActionBars()
				.getToolBarManager();
		Action collapseAllAction = new Action() {
			@Override
			public void run() {
				viewer.collapseAll();
			}
		};
		collapseAllAction
				.setText(org.eclipse.fordiac.ide.deployment.ui.Messages.DownloadSelectionTreeView_COLLAPSE_ALL);
		collapseAllAction
				.setToolTipText(Messages.DownloadSelectionTreeView_COLLAPSE_ALL);
		collapseAllAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_COLLAPSEALL));
		toolBarManager.add(collapseAllAction);

		Action expandAllAction = new Action() {
			@Override
			public void run() {
				viewer.expandAll();
			}
		};
		expandAllAction.setText(Messages.DownloadSelectionTreeView_EXPAND_ALL);
		expandAllAction
				.setToolTipText(Messages.DownloadSelectionTreeView_EXPAND_ALL);
		expandAllAction.setImageDescriptor(FordiacImage.ICON_ExpandAll.getImageDescriptor());
		toolBarManager.add(expandAllAction);

		Action refresh = new Action() {
			@Override
			public void run() {
				viewer.refresh(true);
			}
		};
		refresh.setText(Messages.DownloadSelectionTreeView_Refresh);
		refresh.setToolTipText(Messages.DownloadSelectionTreeView_Refresh);
		refresh.setImageDescriptor(FordiacImage.ICON_Refresh.getImageDescriptor());
		toolBarManager.add(refresh);
	}

	/**
	 * Make actions.
	 */
	private void makeActions() {
		// not used
	}

	/**
	 * Hook double click action.
	 */
	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(final DoubleClickEvent event) {
				// doubleClickAction.run();
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	/** The listeners. */
	private final List<ISelectedElementsChangedListener> listeners = new ArrayList<>();

	/** The download button. */
	private Button downloadButton;

	/**
	 * Adds the selected elements changed listener.
	 * 
	 * @param listener the listener
	 */
	public void addSelectedElementsChangedListener(
			final ISelectedElementsChangedListener listener) {
		if ((!listeners.contains(listener)) && (null != listener)) {
			listeners.add(listener);
		}
	}
	
	/**
	 * Adds the selected elements changed listener.
	 * 
	 * @param listener the listener
	 */
	public void removeSelectedElementsChangedListener(
			final ISelectedElementsChangedListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notify listeners.
	 */
	private void notifyListeners() {
		for (Iterator<ISelectedElementsChangedListener> iterator = listeners
				.iterator(); iterator.hasNext();) {
			ISelectedElementsChangedListener listener = iterator.next();
			listener.selectionChanged();
		}
	}

	/**
	 * Gets the selected elements.
	 * 
	 * @return the selected elements
	 */
	public Object[] getSelectedElements() {
		return viewer.getCheckedElements();
	}

}