/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import java.util.ArrayList;

import org.eclipse.fordiac.ide.deployment.ui.views.DownloadSelectionTreeView;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.navigator.CommonNavigator;

public class MonitoredSystems extends CompoundContributionItem {

	private static final class SystemMonitoringSelectionAdapter extends SelectionAdapter {
		private final AutomationSystem system;

		private SystemMonitoringSelectionAdapter(AutomationSystem system) {
			this.system = system;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			MenuItem menuItem = (MenuItem) e.getSource();
			MonitoringManager manager = MonitoringManager.getInstance();

			if (menuItem.getSelection()) {
				manager.enableSystem(system.getName());

				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				try {
					window.getWorkbench().showPerspective("org.eclipse.fordiac.ide.monitoring.MonitoringPerspective", window); //$NON-NLS-1$
				} catch (WorkbenchException e1) {
					Activator.getDefault().logError(e1.getMessage(), e1);
				}
			} else {
				manager.disableSystem(system.getName());
			}
			refreshSystemTree();
		}
	}

	private static class MonitorSystemContribution extends ContributionItem {		
		private final AutomationSystem system;

		MonitorSystemContribution(AutomationSystem system) {
			super();
			this.system = system;
		}

		
		@Override
		public void fill(Menu menu, int index) {
			createSystemMonitoringMenuEntry(system, menu, index);
		}
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		ArrayList<MonitorSystemContribution> menuList = new ArrayList<>();
		for (AutomationSystem system : SystemManager.INSTANCE.getSystems()) {
			menuList.add(new MonitorSystemContribution(system));
		}
		return menuList.toArray(new IContributionItem[menuList.size()]);
	}
	
	private static void createSystemMonitoringMenuEntry(AutomationSystem system, Menu menu, int index){
		MenuItem item = new MenuItem(menu, SWT.CHECK);
		item.setText(system.getName());
		MonitoringManager manager = MonitoringManager.getInstance();
		item.setSelection(manager.monitoringForSystemEnabled(system));
		item.addSelectionListener(new SystemMonitoringSelectionAdapter(system));
	}

	public static void createMenuEntriesForSystems(Menu menu) {
		for (AutomationSystem system : SystemManager.INSTANCE.getSystems()) {	
			createSystemMonitoringMenuEntry(system, menu, -1);
		}		
		
	}

	public static void refreshSystemTree() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewPart view = page.findView("org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer"); //$NON-NLS-1$
		
		if ((null != view) && (view instanceof CommonNavigator)){
			CommonNavigator treeView = (CommonNavigator)view;
			treeView.getCommonViewer().refresh();
		}
		
		view = page.findView("org.eclipse.fordiac.ide.deployment.ui.views.DownloadSelectionTreeView"); //$NON-NLS-1$
		
		if ((null != view) && (view instanceof DownloadSelectionTreeView)){
			DownloadSelectionTreeView treeView = (DownloadSelectionTreeView)view;
			treeView.getViewer().refresh();
		}
	}

}
