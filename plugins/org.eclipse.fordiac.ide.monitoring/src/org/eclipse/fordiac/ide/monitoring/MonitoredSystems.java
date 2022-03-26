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

import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
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

		private SystemMonitoringSelectionAdapter(final AutomationSystem system) {
			this.system = system;
		}

		@Override
		public void widgetSelected(final SelectionEvent e) {
			final MenuItem menuItem = (MenuItem) e.getSource();
			final MonitoringManager manager = MonitoringManager.getInstance();

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

		MonitorSystemContribution(final AutomationSystem system) {
			super();
			this.system = system;
		}

		@Override
		public void fill(final Menu menu, final int index) {
			createSystemMonitoringMenuEntry(system, menu, index);
		}
	}

	private static void createSystemMonitoringMenuEntry(final AutomationSystem system, final Menu menu,
			final int index) {
		final MenuItem item = (index == -1) ? new MenuItem(menu, SWT.CHECK) : new MenuItem(menu, SWT.CHECK, index);
		item.setText(system.getName());
		final MonitoringManager manager = MonitoringManager.getInstance();
		item.setSelection(manager.monitoringForSystemEnabled(system));
		item.addSelectionListener(new SystemMonitoringSelectionAdapter(system));
	}

	public static void createMenuEntriesForSystems(final Menu menu) {
		for (final AutomationSystem system : MonitoringManager.getInstance().getMonitoredSystems()) {
			createSystemMonitoringMenuEntry(system, menu, -1);
		}
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		final ArrayList<MonitorSystemContribution> menuList = new ArrayList<>();
		for (final AutomationSystem system : MonitoringManager.getInstance().getMonitoredSystems()) {
			menuList.add(new MonitorSystemContribution(system));
		}
		return menuList.toArray(new IContributionItem[menuList.size()]);
	}

	public static void refreshSystemTree() {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		final IViewPart view = page.findView("org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer"); //$NON-NLS-1$

		if (view instanceof CommonNavigator) {
			final CommonNavigator treeView = (CommonNavigator) view;
			treeView.getCommonViewer().refresh();
		}

	}

}
