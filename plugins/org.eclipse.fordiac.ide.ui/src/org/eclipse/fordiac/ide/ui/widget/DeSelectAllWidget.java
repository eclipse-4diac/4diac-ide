/*******************************************************************************
 * Copyright (c) 2025 Monika Wenger
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import java.util.stream.Stream;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class DeSelectAllWidget {
	private Button deSelectAllButton;
	protected Composite container;
	private Listener listener = null;

	public void createControls(final Composite parent, final FormToolkit widgetFactory) {
		createControls(parent, widgetFactory, false);
	}

	private void createControls(final Composite parent, final FormToolkit widgetFactory, final boolean horizontal) {
		container = createContainer(widgetFactory, parent, horizontal ? 2 : 1);
		createDeSelectAllButton(widgetFactory, container);
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

	protected void createDeSelectAllButton(final FormToolkit widgetFactory, final Composite container) {
		deSelectAllButton = widgetFactory.createButton(container, "", SWT.CHECK); //$NON-NLS-1$
		deSelectAllButton.setToolTipText("(Un)Check all interface elements"); //$NON-NLS-1$
		deSelectAllButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
	}

	protected static Composite createContainer(final FormToolkit widgetFactory, final Composite parent,
			final int columns) {
		final Composite container = widgetFactory.createComposite(parent, SWT.NONE);
		container.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false));
		GridLayoutFactory.fillDefaults().numColumns(columns).equalWidth(true).margins(1, 0).spacing(1, 0)
				.applyTo(container);
		return container;
	}

	public void bindToTableViewer(final TableViewer viewer, final CommandExecutorForList<TableItem> executor) {
		if (null == listener) {
			listener = getDeSelectAllListener(deSelectAllButton, viewer, executor);
			deSelectAllButton.addListener(SWT.Selection, listener);
		}
	}

	private static Listener getDeSelectAllListener(final Button checkbox, final TableViewer viewer,
			final CommandExecutorForList<TableItem> executor) {
		return ev -> {
			final TableItem[] items = viewer.getTable().getItems();
			final boolean isCreate = checkbox.getSelection();
			if (isCreate) {
				executor.executeCommand(Stream.of(items).filter(item -> !item.getChecked()).toList(), isCreate);
			} else {
				executor.executeCommand(Stream.of(items).filter(TableItem::getChecked).toList(), isCreate);
			}
		};
	}

	public Composite getControl() {
		return container;
	}

	public boolean isEnabled() {
		return deSelectAllButton.getEnabled();
	}

	public void setEnabled(final boolean enabled) {
		deSelectAllButton.setEnabled(enabled);
	}

	public void setSelection(final boolean selected) {
		deSelectAllButton.removeListener(SWT.Selection, listener);
		deSelectAllButton.setSelection(selected);
		deSelectAllButton.addListener(SWT.Selection, listener);
	}
}
