/*******************************************************************************
 * Copyright (c) 2015 Profactor GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class CreationPopupDialog extends PopupDialog {

	private final Object[] elements;
	private final LabelProvider labelProvider;

	private final ICreationExecutor executor;

	public CreationPopupDialog(final Shell parent, final int shellStyle, final boolean takeFocusOnOpen,
			final boolean persistSize, final boolean persistLocation, final boolean showDialogMenu,
			final boolean showPersistActions, final String titleText, final String infoText, final Object[] elements,
			final LabelProvider labelProvider, final ICreationExecutor executor) {
		super(parent, shellStyle, takeFocusOnOpen, persistSize, persistLocation, showDialogMenu, showPersistActions,
				titleText, infoText);
		this.elements = elements.clone();
		this.labelProvider = labelProvider;
		this.executor = executor;
	}

	@Override
	protected Point getInitialLocation(final Point initialSize) {
		final Display display = getShell().getDisplay();
		return display.getCursorLocation();
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Table proposalTable = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL);

		proposalTable.setRedraw(false);
		proposalTable.setItemCount(elements.length);
		final TableItem[] items = proposalTable.getItems();
		for (int i = 0; i < items.length; i++) {
			final TableItem item = items[i];
			final Object proposal = elements[i];
			item.setText(labelProvider.getText(proposal));
			item.setImage(labelProvider.getImage(proposal));
			item.setData(proposal);
		}
		proposalTable.setRedraw(true);

		proposalTable.setHeaderVisible(false);
		proposalTable.addListener(SWT.Selection, e -> {
			if (e.item instanceof final TableItem selected) {
				executor.execute(selected.getData());
			}
			close();
		});

		return proposalTable;
	}
}
