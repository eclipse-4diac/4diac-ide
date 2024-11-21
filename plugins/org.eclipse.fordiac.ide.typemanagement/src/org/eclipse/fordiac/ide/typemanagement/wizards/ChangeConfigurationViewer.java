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

import java.util.LinkedHashMap;

import org.eclipse.fordiac.ide.typemanagement.refactoring.IFordiacPreviewChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.IFordiacPreviewChange.ChangeState;
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

public class ChangeConfigurationViewer implements IChangePreviewViewer {
	private Composite control;
	private IFordiacPreviewChange change;

	private Table table;
	private final LinkedHashMap<TableItem, ChangeState> choices = new LinkedHashMap<>();

	@Override
	public void createControl(final Composite parent) {
		control = new Composite(parent, SWT.NONE);
		control.setLayout(new FillLayout());

		table = new Table(control, SWT.CHECK | SWT.BORDER);

		table.setSize(100, 100);

		// Add a SelectionListener to the table to listen for checkbox changes
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (e.detail == SWT.CHECK) {
					final TableItem item = (TableItem) e.item;
					final ChangeState cs = choices.get(item);
					// do not allow de-selection of currently selected choice.
					if (change.getState().contains(cs)) {
						item.setChecked(true);
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
		if (choices.isEmpty()) {
			initializeChoices(input);
		}
		if (input.getChange() instanceof final IFordiacPreviewChange deleteChange) {
			setSelection(deleteChange);
		}
	}

	private void setSelection(final IFordiacPreviewChange delChange) {
		choices.keySet().stream().forEach(i -> i.setChecked(false));
		change = delChange;

		// initialize UI from change state
		choices.entrySet().stream().filter(entry -> delChange.getState().contains(entry.getValue()))
				.forEach(entry -> entry.getKey().setChecked(true));

		if (choices.keySet().stream().filter(TableItem::getChecked).toList().isEmpty()) {
			choices.firstEntry().getKey().setChecked(true);
		}

	}

	private void initializeChoices(final ChangePreviewViewerInput input) {
		if (input.getChange() instanceof final IFordiacPreviewChange previewChange) {
			previewChange.getAllowedChoices().forEach(s -> {
				if (!s.equals(ChangeState.NO_CHANGE)) { // no change should not be selectable by the User
					final TableItem ti = new TableItem(table, SWT.NONE);
					ti.setText(s.toString());
					choices.put(ti, s);
				}
			});
		}

	}

}