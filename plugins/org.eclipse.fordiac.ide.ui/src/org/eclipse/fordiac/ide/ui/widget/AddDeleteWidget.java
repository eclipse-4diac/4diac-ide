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
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.ui.providers.CommandProvider;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.fordiac.ide.ui.providers.CreationCommandProvider;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class AddDeleteWidget {
	private static final int EDIT_COLUMN = 0;
	private Button createButton;
	private Button deleteButton;
	private boolean enabled = true;
	protected Composite container;

	public void createControls(final Composite parent, final FormToolkit widgetFactory) {
		container = createContainer(widgetFactory, parent);

		createAddButton(widgetFactory, container);

		createDeleteButton(widgetFactory, container);

		// initially nothing should be selected therefore deactivate the buttons
		setButtonEnablement(false);
	}

	public void setVisible(final boolean visible) {
		setVisible(visible, container);
	}

	private static void setVisible(final boolean visible, final Control widget) {
		widget.setVisible(visible);
		if (null != widget.getLayoutData()) {
			((GridData) widget.getLayoutData()).exclude = !visible;
		} else {
			widget.setLayoutData(GridDataFactory.fillDefaults().exclude(!visible).create());
		}
		widget.getParent().pack();
	}

	public void setVisibleCreateButton(final boolean visible) {
		setVisible(visible, createButton);
	}

	public void setVisibleDeleteButton(final boolean visible) {
		setVisible(visible, deleteButton);
	}

	protected void createDeleteButton(final FormToolkit widgetFactory, final Composite container) {
		deleteButton = widgetFactory.createButton(container, "", SWT.PUSH); //$NON-NLS-1$
		deleteButton.setToolTipText("Delete selected interface element"); //$NON-NLS-1$
		deleteButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		deleteButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
	}

	protected void createAddButton(final FormToolkit widgetFactory, final Composite container) {
		createButton = widgetFactory.createButton(container, "", SWT.PUSH); //$NON-NLS-1$
		createButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		createButton.setToolTipText("Create element"); //$NON-NLS-1$
		createButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
	}

	protected static Composite createContainer(final FormToolkit widgetFactory, final Composite parent) {
		final Composite container = widgetFactory.createComposite(parent, SWT.NONE);
		container.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false));
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(true).margins(1, 0).spacing(1, 0).applyTo(container);
		return container;
	}

	protected void setButtonEnablement(final boolean enable) {
		deleteButton.setEnabled(enable);
		deleteButton
				.setImage((enable) ? PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE)
						: PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE_DISABLED));
	}

	protected void setCreateButtonEnablement(final boolean enable) {
		createButton.setEnabled(enable);
	}

	public void addCreateListener(final Listener createListener) {
		createButton.addListener(SWT.Selection, createListener);
	}

	public void addDeleteListener(final Listener deleteListener) {
		deleteButton.addListener(SWT.Selection, deleteListener);
	}

	public void bindToTableViewer(final TableViewer viewer, final CommandExecutor executor,
			final CreationCommandProvider addCommand, final CommandProvider deleteCommand) {

		final Listener createListener = getAddListener(viewer, executor, addCommand);

		final Listener deleteListener = getDeleteListener(viewer, executor, deleteCommand);

		bindToTableViewer(viewer, createListener, deleteListener);
	}

	public void bindToTableViewer(final NatTable table, final CommandExecutor executor,
			final CreationCommandProvider addCommand, final CommandProvider deleteCommand) {

		final Listener createListener = getAddListener(table, executor, addCommand);

		final Listener deleteListener = getDeleteListener(table, executor, deleteCommand);

		bindToTableViewer(table, createListener, deleteListener);
	}

	public void bindToTableViewer(final TableViewer viewer, final Listener createListener,
			final Listener deleteListener) {

		addCreateListener(createListener);
		addDeleteListener(deleteListener);

		viewer.addSelectionChangedListener(ev -> setButtonEnablement(!viewer.getSelection().isEmpty() && enabled));

		viewer.getTable().addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(final KeyEvent e) {
				// Nothing to do here
			}

			@Override
			public void keyPressed(final KeyEvent e) {
				if ((e.keyCode == SWT.INSERT) && (e.stateMask == 0)) {
					createListener.handleEvent(null);
				} else if ((e.character == SWT.DEL) && (e.stateMask == 0)) {
					deleteListener.handleEvent(null);
				}
			}
		});
	}

	public void bindToTableViewer(final NatTable table, final Listener createListener, final Listener deleteListener) {
		addCreateListener(createListener);
		addDeleteListener(deleteListener);
		table.addListener(SWT.Selection, event -> {
			final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(table);
			final int[] rows = selectionLayer.getFullySelectedRowPositions();
			setButtonEnablement(rows.length > 0 && enabled);
		});

		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				if ((e.keyCode == SWT.INSERT) && (e.stateMask == 0)) {
					createListener.handleEvent(null);
				} else if ((e.character == SWT.DEL) && (e.stateMask == 0)) {
					deleteListener.handleEvent(null);
				}
			}
		});
	}

	public static Listener getSelectionListener(final TableViewer viewer, final CommandExecutor executor,
			final CommandProvider commandProvider) {
		return ev -> {
			if (!viewer.getStructuredSelection().isEmpty()) {
				executeCompoundCommandForList(viewer, viewer.getStructuredSelection().toList(), executor,
						commandProvider);
			}
		};
	}

	public static Listener getSelectionListener(final NatTable table, final CommandExecutor executor,
			final CommandProvider commandProvider) {
		return ev -> {
			final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(table);
			final ListDataProvider<?> dataProvider = (ListDataProvider<?>) NatTableWidgetFactory.getDataLayer(table)
					.getDataProvider();
			if (!selectionLayer.hasRowSelection() || selectionLayer.isRowPositionSelected(0)) {
				return;
			}

			final int[] rows = selectionLayer.getFullySelectedRowPositions();
			final List<Object> rowObjects = new ArrayList<>();
			for (final int row : rows) {
				if (row >= 0) {
					rowObjects.add(dataProvider.getRowObject(row));
				}
			}
			if (!rowObjects.isEmpty()) {
				executeCompoundCommandForList(table, rowObjects, executor, commandProvider);
				for (final int row : rows) {
					if (row == 0) {
						selectionLayer.selectRow(0, row, true, true);
					} else {
						selectionLayer.selectRow(0, row - 1, true, true);
					}
				}
			}
		};
	}

	protected static void executeCompoundCommandForList(final TableViewer viewer, final List<?> selection,
			final CommandExecutor executor, final CommandProvider commandProvider) {
		final CompoundCommand cmd = new CompoundCommand();
		selection.stream().forEach(elem -> cmd.add(commandProvider.getCommand(elem)));
		executor.executeCommand(cmd);
		viewer.refresh();
	}

	protected static void executeCompoundCommandForList(final NatTable table, final List<Object> selection,
			final CommandExecutor executor, final CommandProvider commandProvider) {
		final CompoundCommand cmd = new CompoundCommand();
		selection.forEach(elem -> cmd.add(commandProvider.getCommand(elem)));
		executor.executeCommand(cmd);
		table.refresh();
	}

	public static Listener getDeleteListener(final TableViewer viewer, final CommandExecutor executor,
			final CommandProvider commandProvider) {
		return ev -> {
			if (!viewer.getStructuredSelection().isEmpty()) {
				int pos = viewer.getTable().getSelectionIndices()[0];
				executeCompoundCommandForList(viewer, viewer.getStructuredSelection().toList(), executor,
						commandProvider);
				final int itemCnt = viewer.getTable().getItemCount();
				if (pos <= itemCnt) {
					if (pos == itemCnt) {
						pos--;
					}
					viewer.getTable().forceFocus();
					// the selection has to be set again via the table viewer for the widgets to
					// recognize it
					viewer.getTable().setSelection(pos);
				}
			}
		};
	}

	public static Listener getDeleteListener(final NatTable table, final CommandExecutor executor,
			final CommandProvider commandProvider) {
		return ev -> {
			final int[] rows = NatTableWidgetFactory.getSelectionLayer(table).getFullySelectedRowPositions();
			final ListDataProvider<?> dataProvider = (ListDataProvider<?>) NatTableWidgetFactory.getDataLayer(table)
					.getDataProvider();
			final List<Object> rowObjects = new ArrayList<>();
			for (final int row : rows) {
				if (row >= 0) {
					rowObjects.add(dataProvider.getRowObject(row));
				}
			}
			if (!rowObjects.isEmpty()) {
				executeCompoundCommandForList(table, rowObjects, executor, commandProvider);
			}
		};
	}

	private static Listener getAddListener(final TableViewer viewer, final CommandExecutor executor,
			final CreationCommandProvider commandProvider) {
		return ev -> {
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
			final CreationCommandProvider commandProvider) {
		return ev -> {
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

			final CreationCommand cmd = commandProvider.getCommand(refObject);
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

	public Composite getControl() {
		return container;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
		setButtonEnablement(false); // initially nothing should be selected therefore deactivate the buttons
		createButton.setEnabled(enabled);
	}
}
