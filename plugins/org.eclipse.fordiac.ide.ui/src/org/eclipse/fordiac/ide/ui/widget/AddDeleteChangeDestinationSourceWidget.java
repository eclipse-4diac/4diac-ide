/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Fabio Gandolfi - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.fordiac.ide.ui.providers.CommandProvider;
import org.eclipse.fordiac.ide.ui.providers.CreationCommandProvider;
import org.eclipse.fordiac.ide.ui.providers.MessageDialogProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.command.EditUtils;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class AddDeleteChangeDestinationSourceWidget extends AddDeleteWidget {
	private Button changeDestinationSourceButton;

	@Override
	public void createControls(final Composite parent, final FormToolkit widgetFactory) {
		container = createContainer(widgetFactory, parent);

		createAddButton(widgetFactory, container);

		createDeleteButton(widgetFactory, container);

		createChangeButton(widgetFactory, container);

		// initially nothing should be selected therefore deactivate the buttons
		setButtonEnablement(false);
	}

	protected void createChangeButton(final FormToolkit widgetFactory, final Composite container) {
		changeDestinationSourceButton = widgetFactory.createButton(container, "", SWT.PUSH); //$NON-NLS-1$
		changeDestinationSourceButton.setToolTipText("change Destination or Source of selected interface element"); //$NON-NLS-1$
		changeDestinationSourceButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		changeDestinationSourceButton
				.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_SYNCED));
	}

	@Override
	protected void setButtonEnablement(final boolean enable) {
		changeDestinationSourceButton.setEnabled(enable);
		super.setButtonEnablement(enable);
	}

	public void addChangePinListener(final Listener changeListener) {
		changeDestinationSourceButton.addListener(SWT.Selection, changeListener);
	}

	public void bindToTableViewer(final TableViewer viewer, final CommandExecutor executor,
			final CreationCommandProvider addCommand, final CommandProvider deleteCommand,
			final MessageDialogProvider changeSourceDestinationMessageBox) {
		super.bindToTableViewer(viewer, executor, addCommand, deleteCommand);
		final Listener createListener = getChangeListener(viewer, executor, changeSourceDestinationMessageBox);
		bindToTableViewer(viewer, createListener);
	}

	public void bindToTableViewer(final NatTable table, final CommandExecutor executor,
			final CreationCommandProvider addCommand, final CommandProvider deleteCommand,
			final MessageDialogProvider changeSourceDestinationMessageBox) {
		super.bindToTableViewer(table, executor, addCommand, deleteCommand);
		final Listener createListener = getChangeListener(table, executor, changeSourceDestinationMessageBox);
		bindToTableViewer(table, createListener);
	}

	private static Listener getChangeListener(final TableViewer viewer, final CommandExecutor executor,
			final MessageDialogProvider changeSourceDestinationMessageBox) {
		return ev -> {
			final MessageDialog msgDialog = changeSourceDestinationMessageBox
					.getMessageDialog(getReferencedElement(viewer));
			msgDialog.open();
			viewer.refresh();
		};
	}

	private static Listener getChangeListener(final NatTable table, final CommandExecutor executor,
			final MessageDialogProvider changeSourceDestinationMessageBox) {
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

			final MessageDialog msgDialog = changeSourceDestinationMessageBox.getMessageDialog(refObject);
			msgDialog.open();
			table.refresh();
		};
	}

	public void bindToTableViewer(final TableViewer viewer, final Listener changeListener) {

		changeDestinationSourceButton.addListener(SWT.Selection, changeListener);

		viewer.addSelectionChangedListener(ev -> setButtonEnablement(!viewer.getSelection().isEmpty()));
	}

	public void bindToTableViewer(final NatTable table, final Listener changeListener) {
		changeDestinationSourceButton.addListener(SWT.Selection, changeListener);

		table.addListener(SWT.Selection, event -> {
			final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(table);
			final int[] rows = selectionLayer.getFullySelectedRowPositions();
			setButtonEnablement(
					rows.length > 0 && EditUtils.allCellsEditable(selectionLayer, table.getConfigRegistry()));
		});
	}

}
