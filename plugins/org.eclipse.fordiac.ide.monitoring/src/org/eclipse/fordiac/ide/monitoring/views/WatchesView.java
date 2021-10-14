/*******************************************************************************
 * Copyright (c) 2012 - 2016 Profactor GbmH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added removing the watch listener on dispose
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.views;

import org.eclipse.fordiac.ide.deployment.monitoringbase.IMonitoringListener;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.provider.WatchesContentProvider;
import org.eclipse.fordiac.ide.monitoring.provider.WatchesLabelProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;

public class WatchesView extends ViewPart {

	private FilteredTree filteredTree;
	private final WatchesContentProvider provider = new WatchesContentProvider();

	private final IMonitoringListener listener = new IMonitoringListener() {

		@Override
		public void notifyTriggerEvent(final PortElement port) {
			// currently nothing to do
		}

		@Override
		public void notifyRemovePort(final PortElement port) {
			filteredTree.getViewer().refresh();
		}

		@Override
		public void notifyAddPort(final PortElement port) {
			if (!filteredTree.isDisposed()) {
				filteredTree.getViewer().refresh();
			}
		}

		@Override
		public void notifyWatchesChanged() {
			provider.update();
			if (!filteredTree.isDisposed()) {
				filteredTree.getViewer().refresh();
			}
		}
	};

	@Override
	public void createPartControl(final Composite parent) {
		final Composite root = new Composite(parent, SWT.NONE);
		root.setLayout(new GridLayout());
		final PatternFilter patternFilter = new PatternFilter();

		filteredTree = new FilteredTree(root, SWT.H_SCROLL | SWT.V_SCROLL, patternFilter, true, true);

		final GridData treeGridData = new GridData();
		treeGridData.grabExcessHorizontalSpace = true;
		treeGridData.grabExcessVerticalSpace = true;
		treeGridData.horizontalAlignment = SWT.FILL;
		treeGridData.verticalAlignment = SWT.FILL;

		filteredTree.setLayoutData(treeGridData);

		final TreeViewerColumn column1 = new TreeViewerColumn(filteredTree.getViewer(), SWT.None);
		column1.getColumn().setText("Watched Element");
		column1.getColumn().setWidth(340);
		final TreeViewerColumn column2 = new TreeViewerColumn(filteredTree.getViewer(), SWT.None);
		column2.getColumn().setText("Value");
		column2.getColumn().setWidth(100);
		column2.setEditingSupport(new EditingSupport(column2.getViewer()) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (element instanceof WatchValueTreeNode) {
					final MonitoringBaseElement monitoringBaseElement = ((WatchValueTreeNode) element)
							.getMonitoringBaseElement();
					MonitoringManager.getInstance().writeValue((MonitoringElement) monitoringBaseElement,
							(String) value);
				}

			}

			@Override
			protected Object getValue(final Object element) {
				if (element instanceof WatchValueTreeNode) {
					return ((WatchValueTreeNode) element).getValue();
				}
				return ""; //$NON-NLS-1$
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new TextCellEditor(filteredTree.getViewer().getTree());
			}

			@Override
			protected boolean canEdit(final Object element) {
				// TODO REMOVE that check after forcing is possible
				if (element instanceof WatchValueTreeNode && ((WatchValueTreeNode) element).isStructNode()) {
					return ((WatchValueTreeNode) element).isStructRootNode();
				}
				return true;
			}
		});

		filteredTree.getViewer().getTree().setHeaderVisible(true);
		filteredTree.getViewer().getTree().setLinesVisible(true);

		filteredTree.getViewer().setContentProvider(provider);

		filteredTree.getViewer().setLabelProvider(new WatchesLabelProvider());
		filteredTree.getViewer().setInput(new Object());

		addWatchesAdapters();
	}

	private void addWatchesAdapters() {
		MonitoringManager.getInstance().addMonitoringListener(listener);
	}

	@Override
	public void dispose() {
		MonitoringManager.getInstance().removeMonitoringListener(listener);
		super.dispose();
	}

	@Override
	public void setFocus() {
		// currently nothing to do
	}

}
