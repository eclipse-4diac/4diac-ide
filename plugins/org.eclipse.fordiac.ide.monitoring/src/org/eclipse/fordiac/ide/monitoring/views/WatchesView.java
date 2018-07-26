/*******************************************************************************
 * Copyright (c) 2012 - 2016 Profactor GbmH, fortiss GmbH
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
package org.eclipse.fordiac.ide.monitoring.views;

import org.eclipse.fordiac.ide.deployment.monitoringbase.IMonitoringListener;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
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

	
	private IMonitoringListener listener = new IMonitoringListener() {
		
		@Override
		public void notifyTriggerEvent(PortElement port) {	
			//currently nothing to do
		}
		
		@Override
		public void notifyRemovePort(PortElement port) {
			filteredTree.getViewer().refresh();
		}
			
		@Override
		public void notifyAddPort(PortElement port) {
			if(!filteredTree.isDisposed()){
				filteredTree.getViewer().refresh();
			}
		}
	};

	public WatchesView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite root = new Composite(parent, SWT.NONE);
		root.setLayout(new GridLayout());
		PatternFilter patternFilter = new PatternFilter();

		filteredTree = new FilteredTree(root, SWT.H_SCROLL | SWT.V_SCROLL,
				patternFilter, true);

		GridData treeGridData = new GridData();
		treeGridData.grabExcessHorizontalSpace = true;
		treeGridData.grabExcessVerticalSpace = true;
		treeGridData.horizontalAlignment = SWT.FILL;
		treeGridData.verticalAlignment = SWT.FILL;

		filteredTree.setLayoutData(treeGridData);

		TreeViewerColumn column1 = new TreeViewerColumn(filteredTree.getViewer(),
				SWT.None);
		column1.getColumn().setText("Watched Element");
		column1.getColumn().setWidth(340);
		TreeViewerColumn column2 = new TreeViewerColumn(filteredTree.getViewer(),
				SWT.None);
		column2.getColumn().setText("Value");
		column2.getColumn().setWidth(100);
		column2.setEditingSupport(new EditingSupport(column2.getViewer()) {

			@Override
			protected void setValue(Object element, Object value) {
				if ((element instanceof MonitoringElement) &&
						(((MonitoringElement) element).getPort().getInterfaceElement() instanceof VarDeclaration)){
					MonitoringManager.getInstance().writeValue((MonitoringElement)element, (String)value);
				}
			}

			@Override
			protected Object getValue(Object element) {
				if (element instanceof MonitoringElement) {
					return ((MonitoringElement) element).getCurrentValue();
				}
				return ""; //$NON-NLS-1$
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new TextCellEditor(filteredTree.getViewer().getTree());
			}

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});

		filteredTree.getViewer().getTree().setHeaderVisible(true);
		filteredTree.getViewer().getTree().setLinesVisible(true);

		filteredTree.getViewer().setContentProvider(new WatchesContentProvider());
		filteredTree.getViewer().setLabelProvider(new WatchesLabelProvider());
		filteredTree.getViewer().setInput(new Object());

		//MonitoringManager.getInstance().addWatchesAdapter(adapter);
		
		addWatchesAdapters();
	}

	private void addWatchesAdapters() {
		MonitoringManager.getInstance().addWatchesAdapter(listener);
	}

	@Override
	public void setFocus() {
		//currently nothing to do
	}

}
