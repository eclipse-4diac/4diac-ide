/*******************************************************************************
 * Copyright (c) 2015 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class CreationPopupDialog extends PopupDialog {

	private Object[] elements;
	private LabelProvider labelProvider;

	private ICreationExecutor executor;

	public CreationPopupDialog(Shell parent, int shellStyle,
			boolean takeFocusOnOpen, boolean persistSize,
			boolean persistLocation, boolean showDialogMenu,
			boolean showPersistActions, String titleText, String infoText,
			Object[] elements, LabelProvider labelProvider,
			ICreationExecutor executor) {
		super(parent, shellStyle, takeFocusOnOpen, persistSize,
				persistLocation, showDialogMenu, showPersistActions, titleText,
				infoText);
		this.elements = elements.clone();
		this.labelProvider = labelProvider;
		this.executor = executor;
	}

	@Override
	protected Point getInitialLocation(Point initialSize) {
		Display display = getShell().getDisplay();
		return display.getCursorLocation();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Table proposalTable = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL);

		proposalTable.setRedraw(false);
		proposalTable.setItemCount(elements.length);
		TableItem[] items = proposalTable.getItems();
		for (int i = 0; i < items.length; i++) {
			TableItem item = items[i];
			Object proposal = elements[i];
			item.setText(labelProvider.getText(proposal));
			item.setImage(labelProvider.getImage(proposal));
			item.setData(proposal);
		}
		proposalTable.setRedraw(true);

		proposalTable.setHeaderVisible(false);
		proposalTable.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.item instanceof TableItem) {
					TableItem selected = (TableItem) e.item;
					executor.execute(selected.getData());
				}
				close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		return proposalTable;
	}
}
