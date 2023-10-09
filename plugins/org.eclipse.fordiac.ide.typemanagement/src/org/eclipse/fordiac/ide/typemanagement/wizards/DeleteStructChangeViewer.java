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
 *   Michael Oberlehner
 *     - initial API and implementation and/or initial documentation
 *   Dario Romano
 *     - add correct preservation of selection and ui initialization from change state
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.wizards;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.fordiac.ide.typemanagement.refactoring.DeleteFBTypeInterfaceChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.DeleteFBTypeInterfaceChange.ChangeState;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.ui.refactoring.ChangePreviewViewerInput;
import org.eclipse.ltk.ui.refactoring.IChangePreviewViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class DeleteStructChangeViewer implements IChangePreviewViewer {
	private Composite control;
	private DeleteFBTypeInterfaceChange change;

	private Table table;
	private final Map<TableItem, ChangeState> choices = new HashMap<>();

	@Override
	public void createControl(final Composite parent) {
		control = new Composite(parent, SWT.NONE);
		control.setLayout(new FillLayout());

		table = new Table(control, SWT.CHECK | SWT.BORDER);
		for (final ChangeState s : ChangeState.values()) {
			final TableItem ti = new TableItem(table, SWT.NONE);
			ti.setText(s.toString());
			choices.put(ti, s);
		}

		table.setSize(100, 100);

		// Add a SelectionListener to the table to listen for checkbox changes
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (e.detail == SWT.CHECK) {
					final TableItem item = (TableItem) e.item;
					final ChangeState cs = choices.get(item);
					if (change.getState().contains(cs)) {
						change.getState().clear();
						item.setChecked(false);
						return;
					}
					change.addState(cs);
					change.getState().removeIf(state -> !state.equals(cs));
					choices.keySet().stream().filter(anyItem -> !anyItem.equals(item))
							.forEach(otherItem -> otherItem.setChecked(false));
				}
			}
		});

	}

	@Override
	public Control getControl() {
		return control;
	}

	@Override
	public void setInput(final ChangePreviewViewerInput input) {
		final Change change = input.getChange();
		if (change instanceof final DeleteFBTypeInterfaceChange deleteChange) {
			choices.keySet().stream().forEach(i -> i.setChecked(false));
			this.change = deleteChange;
			// initialize UI from change state
			choices.entrySet().stream().filter(entry -> deleteChange.getState().contains(entry.getValue()))
					.forEach(entry -> entry.getKey().setChecked(true));
		}

	}

}