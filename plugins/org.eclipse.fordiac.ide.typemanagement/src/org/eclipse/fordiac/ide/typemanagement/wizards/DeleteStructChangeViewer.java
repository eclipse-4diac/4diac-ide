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
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.wizards;

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
	private static final String DELETE_PIN = "Delete Pin ";
	private static final String CHANGE_TO_ANY_STRUCT = "Change to ANY_STRUCT";
	private Composite control;
	private DeleteFBTypeInterfaceChange change;

	@Override
	public void createControl(final Composite parent) {
		control = new Composite(parent, SWT.NONE);
		control.setLayout(new FillLayout());

		final Table table = new Table(control, SWT.CHECK | SWT.BORDER);

		final TableItem deletePinItem = new TableItem(table, SWT.NONE);
		deletePinItem.setText(DELETE_PIN);
		final TableItem changeItem = new TableItem(table, SWT.NONE);
		changeItem.setText(CHANGE_TO_ANY_STRUCT);

		table.setSize(100, 100);

		// Add a SelectionListener to the table to listen for checkbox changes
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (e.detail == SWT.CHECK) {
					final TableItem item = (TableItem) e.item;
					switch (item.getText()) {
					case DELETE_PIN:
						change.addState(ChangeState.DELETE_PIN);
						change.getState().remove(ChangeState.CHANGE_TO_ANY);
						changeItem.setChecked(false);
						break;
					case CHANGE_TO_ANY_STRUCT:
						change.addState(ChangeState.CHANGE_TO_ANY);
						change.getState().remove(ChangeState.DELETE_PIN);
						deletePinItem.setChecked(false);
						break;
					default:
						break;
					}
				}
			}
		});

	}

	@Override
	public Control getControl() {
		// TODO Auto-generated method stub
		return control;
	}

	@Override
	public void setInput(final ChangePreviewViewerInput input) {
		final Change change = input.getChange();
		if (change instanceof final DeleteFBTypeInterfaceChange deleteChange) {
			this.change = deleteChange;
			this.change.getState().clear();
		}

	}

}