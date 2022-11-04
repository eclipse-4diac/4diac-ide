/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.dialogs;

import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.monitoring.Messages;
import org.eclipse.fordiac.ide.monitoring.provider.StructForceEditingSupport;
import org.eclipse.fordiac.ide.monitoring.provider.StructForceTreeContentProvider;
import org.eclipse.fordiac.ide.monitoring.provider.WatchesNameLabelProvider;
import org.eclipse.fordiac.ide.monitoring.provider.WatchesTypeLabelProvider;
import org.eclipse.fordiac.ide.monitoring.provider.WatchesValueLabelProvider;
import org.eclipse.fordiac.ide.monitoring.views.WatchValueTreeNode;
import org.eclipse.fordiac.ide.monitoring.views.WatchValueTreeNodeUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeColumn;

public class StructForceDialog extends Dialog {

	private static final int WIDTH = 500;
	private static final int HEIGHT = 300;

	private static final int COLUMN_WIDTH = 100;

	private static final String NAME = "NAME"; //$NON-NLS-1$
	private static final String TYPE = "DATATYPE"; //$NON-NLS-1$
	private static final String VALUE = "VALUE"; //$NON-NLS-1$

	private final StructuredType struct;
	private TreeViewer viewer;
	private final MonitoringBaseElement monitoringElement;
	private WatchValueTreeNode structNode;

	public StructForceDialog(final Shell parentShell, final StructuredType type, final MonitoringBaseElement monitoringElement) {
		super(parentShell);
		this.struct = type;
		this.monitoringElement = monitoringElement;
	}

	public String getValue() {
		return WatchValueTreeNodeUtils.buildTreeString(structNode);
	}

	@Override
	protected Control createContents(final Composite parent) {
		final Control control = super.createContents(parent);
		getButton(IDialogConstants.OK_ID).setText(Messages.StructForceDialog_Force_Button_Text);
		return control;
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite container = (Composite) super.createDialogArea(parent);
		createTree(container);
		return container;
	}

	private void createTree(final Composite parent) {
		viewer = createViewer(parent);
		createColumns();

		viewer.setContentProvider(new StructForceTreeContentProvider());
		final WatchValueTreeNode root = new WatchValueTreeNode(monitoringElement);
		structNode = WatchValueTreeNode.createStructNode(monitoringElement, struct, root);
		viewer.setInput(structNode);
	}

	public static TreeViewer createViewer(final Composite parent) {
		final TreeViewer treeViewer = new TreeViewer(parent,
				SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);

		treeViewer.getTree().setLinesVisible(true);
		treeViewer.getTree().setHeaderVisible(true);

		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.minimumHeight = 80;
		gridData.heightHint = 4;
		gridData.widthHint = 400;
		treeViewer.getControl().setLayoutData(gridData);

		return treeViewer;
	}

	private void createColumns() {

		final TreeViewerColumn nameCol = createTreeViewerColumn(NAME, COLUMN_WIDTH);
		nameCol.setLabelProvider(new WatchesNameLabelProvider());

		final TreeViewerColumn typeCol = createTreeViewerColumn(TYPE, COLUMN_WIDTH);
		typeCol.setLabelProvider(new WatchesTypeLabelProvider());

		final TreeViewerColumn valueCol = createTreeViewerColumn(VALUE, COLUMN_WIDTH);
		valueCol.setLabelProvider(new WatchesValueLabelProvider());
		valueCol.setEditingSupport(new StructForceEditingSupport(viewer, viewer.getTree()));
	}

	private TreeViewerColumn createTreeViewerColumn(final String title, final int bound) {
		final TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
		final TreeColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.StructForceDialog_Title);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(WIDTH, HEIGHT);
	}
}
