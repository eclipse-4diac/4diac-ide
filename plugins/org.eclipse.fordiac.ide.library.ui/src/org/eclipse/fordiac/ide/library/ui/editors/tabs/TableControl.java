/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner
 *   	- initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.editors.tabs;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.fordiac.ide.library.ui.editors.ManifestEditorPage;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.forms.widgets.FormToolkit;

public final class TableControl {

	private final Group group;
	private final TableViewer viewer;
	private final TableColumnLayout columnLayout;

	private final Button addButton;
	private final Button removeButton;

	private boolean enabled = true;

	private Runnable addHandler = () -> {
		/* nothing */ };
	private Runnable removeHandler = () -> {
		/* nothing */ };

	public TableControl(final Composite parent, final FormToolkit toolkit, final String title) {
		group = new Group(parent, SWT.NONE);
		group.setText(title);
		group.setLayout(new GridLayout(2, false));

		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(group);

		toolkit.adapt(group);

		final Composite tableComposite = toolkit.createComposite(group);
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		columnLayout = new TableColumnLayout();
		tableComposite.setLayout(columnLayout);

		viewer = new TableViewer(tableComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE);

		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		viewer.setContentProvider(ArrayContentProvider.getInstance());

		final Composite buttons = toolkit.createComposite(group);
		buttons.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		GridLayoutFactory.fillDefaults().applyTo(buttons);

		addButton = toolkit.createButton(buttons, "Add", SWT.PUSH); //$NON-NLS-1$
		addButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		removeButton = toolkit.createButton(buttons, "Remove", SWT.PUSH); //$NON-NLS-1$
		removeButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		hookListeners();
		updateButtonEnablement();
	}

	private void hookListeners() {
		addButton.addListener(SWT.Selection, _ -> addHandler.run());

		removeButton.addListener(SWT.Selection, _ -> removeHandler.run());

		viewer.addSelectionChangedListener(_ -> updateButtonEnablement());

		viewer.getTable().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent event) {
				if (event.keyCode == SWT.DEL && removeButton.isEnabled()) {
					removeHandler.run();
				}
			}
		});
	}

	public Control getControl() {
		return group;
	}

	public TableViewer getViewer() {
		return viewer;
	}

	public TableViewerColumn createColumn(final String title, final int weight) {
		final TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText(title);
		columnLayout.setColumnData(column.getColumn(), new ColumnWeightData(weight));
		return column;
	}

	public void setInput(final Object input) {
		viewer.setInput(input);
		updateButtonEnablement();
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
		viewer.getControl().setEnabled(enabled);
		updateButtonEnablement();
	}

	public void setAddHandler(final Runnable handler) {
		addHandler = Objects.requireNonNull(handler);
	}

	public void setRemoveHandler(final Runnable handler) {
		removeHandler = Objects.requireNonNull(handler);
	}

	private void updateButtonEnablement() {
		addButton.setEnabled(enabled);
		removeButton.setEnabled(enabled && !viewer.getStructuredSelection().isEmpty());
	}

	public static <T> ColumnLabelProvider createLabelProvider(final Class<T> type, final Function<T, ?> getter) {

		return new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return type.isInstance(element) ? Objects.toString(getter.apply(type.cast(element)), "") //$NON-NLS-1$
						: ""; //$NON-NLS-1$
			}
		};
	}

	protected static <T> EditingSupport createEditingSupport(final TableViewer viewer, final ManifestEditorPage<?> page,
			final Class<T> type, final Function<T, String> getter,
			final BiFunction<T, String, IUndoableOperation> operationFactory, final CellEditor editor) {

		return new EditingSupport(viewer) {

			@Override
			protected boolean canEdit(final Object element) {
				return type.isInstance(element);
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return editor;
			}

			@Override
			protected Object getValue(final Object element) {
				return type.isInstance(element) ? Objects.toString(getter.apply(type.cast(element)), "") //$NON-NLS-1$
						: ""; //$NON-NLS-1$
			}

			@Override
			protected void setValue(final Object element, final Object value) {
				if (type.isInstance(element)) {
					final T typedElement = type.cast(element);
					page.execute(operationFactory.apply(typedElement, Objects.toString(value, ""))); //$NON-NLS-1$
					viewer.setSelection(StructuredSelection.EMPTY);
				}
			}
		};
	}
}
