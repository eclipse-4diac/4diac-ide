/*******************************************************************************
 * Copyright (c) 2016- 2018 fortiss GmbH, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring, removed automatic
 *   			   switching to deployment perspective
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import java.util.ArrayList;
import java.util.Set;

import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
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
				manager.enableSystem(system);
			} else {
				manager.disableSystem(system);
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

		private static void createSystemMonitoringMenuEntry(AutomationSystem system, Menu menu, int index) {
			MenuItem item = (index == -1) ? new MenuItem(menu, SWT.CHECK) : new MenuItem(menu, SWT.CHECK, index);
			item.setText(system.getName());
			item.setSelection(true);
			item.addSelectionListener(new SystemMonitoringSelectionAdapter(system));
		}

	}

	private static class EmptyMonitoringContribution extends ContributionItem {

		@Override
		public void fill(Menu menu, int index) {
			MenuItem item = (index == -1) ? new MenuItem(menu, SWT.None) : new MenuItem(menu, SWT.None, index);
			item.setText(FordiacMessages.EmptyField);
			item.setEnabled(false); // indicate that this item is not clickable
		}
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		ArrayList<ContributionItem> menuList = new ArrayList<>();
		Set<AutomationSystem> systems = MonitoringManager.getInstance().getMonitoredSystems();
		if (systems.isEmpty()) {
			menuList.add(new EmptyMonitoringContribution());
		} else {
			for (AutomationSystem system : MonitoringManager.getInstance().getMonitoredSystems()) {
				menuList.add(new MonitorSystemContribution(system));
			}
		}
		return menuList.toArray(new IContributionItem[menuList.size()]);
	}

	public static void refreshSystemTree() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewPart view = page.findView("org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer"); //$NON-NLS-1$

		if ((null != view) && (view instanceof CommonNavigator)) {
			CommonNavigator treeView = (CommonNavigator) view;
			treeView.getCommonViewer().refresh();
		}

	}

}
