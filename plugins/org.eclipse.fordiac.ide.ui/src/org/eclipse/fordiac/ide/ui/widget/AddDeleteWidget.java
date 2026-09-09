/*******************************************************************************
 * Copyright (c) 2019, 2023 Johannes Kepler University Linz
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Alois Zoitl - initial implementation
 * Bianca Wiesmayr - enhanced add functionality
 * Daniel Lindhuber - added separate delete listener
 * Martin Jobst - check editable when enabling buttons
 *              - extracted delete only base class
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.fordiac.ide.ui.providers.CommandProvider;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.fordiac.ide.ui.providers.CreationCommandProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class AddDeleteWidget extends DeleteWidget {
	private static final int EDIT_COLUMN = 0;
	private Button createButton;

	@Override
	protected int getButtonCount() {
		return 2;
	}

	@Override
	protected void createButtons(final FormToolkit widgetFactory, final Composite container) {
		createAddButton(widgetFactory, container);
		super.createButtons(widgetFactory, container);
	}

	protected void createAddButton(final FormToolkit widgetFactory, final Composite container) {
		createButton = widgetFactory.createButton(container, "", SWT.PUSH); //$NON-NLS-1$
		createButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		createButton.setToolTipText("Create element"); //$NON-NLS-1$
		createButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
	}

	public void setVisibleCreateButton(final boolean visible) {
		setVisible(visible, createButton);
	}

	protected void setCreateButtonEnablement(final boolean enable) {
		createButton.setEnabled(enable);
	}

	public void addCreateListener(final Listener createListener) {
		createButton.addListener(SWT.Selection, createListener);
	}

	public void bindToTableViewer(final TableViewer viewer, final CommandExecutor executor,
			final CreationCommandProvider addCommand, final CommandProvider deleteCommand) {

		final Listener createListener = getAddListener(viewer, executor, addCommand);

		final Listener deleteListener = getDeleteListener(viewer, executor, deleteCommand);

		bindToTableViewer(viewer, createListener, deleteListener);
	}

	public void bindToTableViewer(final NatTable table, final CommandExecutor executor,
			final CommandProvider addCommand, final CommandProvider deleteCommand) {

		final Listener createListener = getAddListener(table, executor, addCommand);

		final Listener deleteListener = getDeleteListener(table, executor, deleteCommand);

		bindToTableViewer(table, createListener, deleteListener);
	}

	public void bindToTableViewer(final TableViewer viewer, final Listener createListener,
			final Listener deleteListener) {

		addCreateListener(createListener);
		addDeleteListener(deleteListener);

		bindSelectionChanged(viewer);

		viewer.getTable().addKeyListener(createKeyListener(event -> {
			if (createButton.isEnabled()) {
				deleteListener.handleEvent(event);
			}
		}));
	}

	public void bindToTableViewer(final NatTable table, final Listener createListener, final Listener deleteListener) {
		addCreateListener(createListener);
		addDeleteListener(deleteListener);

		bindSelectionChanged(table);

		table.addKeyListener(createKeyListener(event -> {
			if (createButton.isEnabled()) {
				deleteListener.handleEvent(event);
			}
		}));
	}

	private static Listener getAddListener(final TableViewer viewer, final CommandExecutor executor,
			final CreationCommandProvider commandProvider) {
		return _ -> {
			final CreationCommand cmd = commandProvider.getCommand(getReferencedElement(viewer));
			if (cmd.canExecute()) {
				executor.executeCommand(cmd);
				viewer.refresh();
				final StructuredSelection selection = new StructuredSelection(cmd.getCreatedElement());
				viewer.setSelection(selection);
				viewer.getTable().forceFocus();
				viewer.editElement(cmd.getCreatedElement(), EDIT_COLUMN);
			}
		};
	}

	private static Listener getAddListener(final NatTable table, final CommandExecutor executor,
			final CommandProvider commandProvider) {
		return _ -> {
			Object refObject = null;
			int[] rows = null;
			final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(table);
			if (selectionLayer != null) {
				rows = selectionLayer.getFullySelectedRowPositions();
				if (rows.length > 0 && rows[rows.length - 1] >= 0) {
					final ListDataProvider<?> dataProvider = (ListDataProvider<?>) NatTableWidgetFactory
							.getDataLayer(table).getDataProvider();
					refObject = dataProvider.getRowObject(rows[rows.length - 1]);
				}
			}

			final Command cmd = commandProvider.getCommand(refObject);
			executor.executeCommand(cmd);
			table.refresh();
			if ((selectionLayer != null) && (rows != null) && (rows.length > 0)) {
				selectionLayer.selectRow(0, rows[rows.length - 1] + 1, false, false);
			}
		};
	}

	protected static Object getReferencedElement(final TableViewer viewer) {
		if (!viewer.getStructuredSelection().isEmpty()) {
			return viewer.getStructuredSelection().toList().get(viewer.getStructuredSelection().size() - 1);
		}
		return null;
	}

	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		setCreateButtonEnablement(enabled);
	}
}