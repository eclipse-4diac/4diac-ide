/*******************************************************************************
 * Copyright (c) 2012 - 2017 Profactor GbmH, fortiss GmbH
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

import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.actions.BreakpointsViewContinueBreakpoint;
import org.eclipse.fordiac.ide.monitoring.provider.BreakpointsContentProvider;
import org.eclipse.fordiac.ide.monitoring.provider.BreakpointsLabelProvider;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;


public class BreakpointsView extends ViewPart {

	private FilteredTree filteredTree;

	private final EContentAdapter adapter = new EContentAdapter() {
		@Override
		public void notifyChanged(
				org.eclipse.emf.common.notify.Notification notification) {
			filteredTree.getViewer().refresh(true);
		}
	};

	private Action continueAction;

	public BreakpointsView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite root = new Composite(parent, SWT.NONE);
		root.setLayout(new GridLayout());
		PatternFilter patternFilter = new PatternFilter() {
			@Override
			public boolean isElementVisible(Viewer viewer, Object element) {
				return super.isElementVisible(viewer, element);
			}

			@Override
			protected boolean isParentMatch(Viewer viewer, Object element) {
				return super.isParentMatch(viewer, element);
			}
		};

		filteredTree = new FilteredTree(root, SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.CHECK, patternFilter, true);

		GridData treeGridData = new GridData();
		treeGridData.grabExcessHorizontalSpace = true;
		treeGridData.grabExcessVerticalSpace = true;
		treeGridData.horizontalAlignment = SWT.FILL;
		treeGridData.verticalAlignment = SWT.FILL;

		filteredTree.setLayoutData(treeGridData);

		TreeViewerColumn column1 = new TreeViewerColumn(filteredTree.getViewer(),
				SWT.None);
		column1.getColumn().setText("Breakpoint");
		column1.getColumn().setWidth(340);
		TreeViewerColumn column2 = new TreeViewerColumn(filteredTree.getViewer(),
				SWT.None);
		column2.getColumn().setText("Condition");
		column2.getColumn().setWidth(100);
		column2.setEditingSupport(new EditingSupport(column2.getViewer()) {

			@Override
			protected void setValue(Object element, Object value) {
				if (element instanceof MonitoringElement) {
					((MonitoringElement) element)
							.setBreakpointCondition(value.toString());
				}
			}

			@Override
			protected Object getValue(Object element) {
				if (element instanceof MonitoringElement) {
					return ((MonitoringElement) element).getBreakpointCondition();
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

		filteredTree.getViewer().setContentProvider(
				new BreakpointsContentProvider());
		filteredTree.getViewer().setLabelProvider(new BreakpointsLabelProvider());
		filteredTree.getViewer().setInput(new Object());
		filteredTree.getViewer().addSelectionChangedListener(
				new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						if (filteredTree.getViewer().getSelection() instanceof IStructuredSelection) {
							Object first = ((IStructuredSelection) filteredTree.getViewer()
									.getSelection()).getFirstElement();
							if (first instanceof MonitoringElement) {
								boolean active = ((MonitoringElement) first)
										.isBreakpointActive();
								continueAction.setEnabled(active);
								if (active) {
									// configure action
								}
							}
						}
					}
				});

		//MonitoringManager.getInstance().addBreakpointsAdapter(adapter);
		addBreakpointAdapters();
		createToolbarbuttons();

	}
	
	private void addBreakpointAdapters() {
		MonitoringManager.getInstance().addBreakpointsAdapter(adapter);
	}

	private void createToolbarbuttons() {
		IToolBarManager toolBarManager = getViewSite().getActionBars()
				.getToolBarManager();
		Action refresh = new Action() {
			@Override
			public void run() {
				filteredTree.getViewer().refresh();
			}
		};
		refresh.setText("Refresh");
		refresh.setToolTipText("Refresh");
		refresh.setImageDescriptor(FordiacImage.ICON_Refresh.getImageDescriptor());
		toolBarManager.add(refresh);
		continueAction = new Action() {
			@Override
			public void run() {
				BreakpointsViewContinueBreakpoint bla = new BreakpointsViewContinueBreakpoint();
				bla.selectionChanged(continueAction, filteredTree.getViewer()
						.getSelection());
				bla.run(continueAction);

			}
		};
		continueAction.setText("Continue");
		continueAction.setToolTipText("Continue");
		continueAction.setImageDescriptor(FordiacImage.ICON_Resume.getImageDescriptor());
		continueAction.setEnabled(false);
		toolBarManager.add(continueAction);
	}

	@Override
	public void setFocus() {
	}

}
