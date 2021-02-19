/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

public class TableCutAction extends Action {

	private final Object part;

	public TableCutAction(Object part) {
		this.part = part;
		setId(ActionFactory.CUT.getId());
		setText(FordiacMessages.TableCopyPaste_TEXT_Cut);
		final ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
	}

	@Override
	public void run() {
		final I4diacTableUtil editor = TableWidgetFactory.getTableEditor(part);
		if (editor != null) {
		for (final CellEditor cell : editor.getViewer().getCellEditors()) {
			if (cell.isActivated()) {
				cell.performCut();
				return;
			}
		}
		cutItems(editor);
	}
	}

	private static void cutItems(I4diacTableUtil editor) {
		final Table table = editor.getViewer().getTable();
		final int[] indices = table.getSelectionIndices();
		if (indices.length != 0) {
			final Object[] entries = new Object[indices.length];
			final CompoundCommand cmpCommand = new CompoundCommand();
			for (int i = 0; i < indices.length; i++) {
				entries[i] = editor.removeEntry(indices[i], cmpCommand);
			}
			editor.executeCompoundCommand(cmpCommand);
			Clipboard.getDefault().setContents(entries);
		}
	}

}
