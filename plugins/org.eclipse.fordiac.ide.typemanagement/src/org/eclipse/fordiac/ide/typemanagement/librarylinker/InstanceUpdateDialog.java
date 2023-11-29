/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.librarylinker;

import java.util.Set;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.search.dialog.FBUpdateDialog;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.widgets.LabelFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class InstanceUpdateDialog extends FBUpdateDialog {

	private final Set<FBNetworkElement> updatedInstances;

	public InstanceUpdateDialog(final Shell parentShell, final String dialogTitle, final Image dialogTitleImage,
			final String dialogMessage, final int dialogImageType, final String[] dialogButtonLabels,
			final int defaultIndex, final DataTypeEntry dataTypeEntry, final Set<FBNetworkElement> updatedInstances) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage, dialogImageType, dialogButtonLabels,
				defaultIndex, dataTypeEntry);
		this.updatedInstances = updatedInstances;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		parent.setLayout(new GridLayout(1, true)); // change ti num of columns const
		final Composite searchResArea = WidgetFactory.composite(NONE).create(parent);
		searchResArea.setLayout(new GridLayout(1, true));
		searchResArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		if (updatedInstances.isEmpty()) {
			// No update has been performed aka no instances changed
			final Label warningLabel = LabelFactory.newLabel(NONE).create(searchResArea);
			warningLabel.setText("No instances have been updated!"); //$NON-NLS-1$
		} else {
			createfilterButtons(parent);

			final TreeViewer treeViewer = new TreeViewer(searchResArea);
			configureTableViewer(treeViewer);
			treeViewer.setInput(updatedInstances.toArray());
			GridLayoutFactory.fillDefaults().generateLayout(searchResArea);
		}
		return parent;
	}

	@Override
	protected void configureTableViewer(final TreeViewer viewer) {
		viewer.setContentProvider(new InstanceUpdateContentProvider());
		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(true);
		viewer.getTree().setLayout(createTableLayout());

		final TreeViewerColumn colType = new TreeViewerColumn(viewer, SWT.LEAD);
		colType.getColumn().setText(FordiacMessages.InstanceName);
		colType.setLabelProvider(createLabelProvider());
	}

	@Override
	protected TableLayout createTableLayout() {
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH + TABLE_COL_WIDTH));
		return layout;
	}

	private static ColumnLabelProvider createLabelProvider() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final FBNetworkElement fbnElem) {
					return fbnElem.getName();
				}
				return super.getText(element);
			}
		};
	}

	private class InstanceUpdateContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof Object[]) {
				return (Object[]) inputElement;
			}
			return new Object[0];
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			return new Object[0];
		}

		@Override
		public Object getParent(final Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(final Object element) {
			return false;
		}

	}
}
