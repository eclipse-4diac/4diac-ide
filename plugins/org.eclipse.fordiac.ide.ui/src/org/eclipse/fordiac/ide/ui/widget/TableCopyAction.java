/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH
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

import org.eclipse.fordiac.ide.ui.FordiacClipboard;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

public class TableCopyAction extends Action {

	private final Object part;

	public TableCopyAction(final Object part) {
		this.part = part;
		setId(ActionFactory.COPY.getId());
		setText(FordiacMessages.TableCopyPaste_TEXT_Cut);
		final ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
	}

	@Override
	public void run() {
		final I4diacTableUtil editor = TableWidgetFactory.getTableEditor(part);

		if (editor != null) {
			for (final CellEditor cell : editor.getViewer().getCellEditors()) {
				// cell can be null if column is not editable
				if (cell != null && cell.isActivated()) {
					cell.performCopy();
					return;
				}
			}
			// This IF will be removed once the new copy/paste handling has been approved.
			final String instanceClassName = "CommentPropertySection";
			if (instanceClassName.equals(editor.getClass().getSimpleName())) {
				handleInstancePropertySheet(editor);
			} else {
				Clipboard.getDefault().setContents(editor.getViewer().getStructuredSelection());
			}
		}
	}

	private static void handleInstancePropertySheet(final I4diacTableUtil editor) {
		final TableViewer viewer = editor.getViewer();
		final ViewerCell focusCell = viewer.getColumnViewerEditor().getFocusCell();
		final ICellModifier modifier = viewer.getCellModifier();
		final int index = viewer.getTable().getSelectionIndex();

		if (focusCell != null && index >= 0) {
			final TableItem item = viewer.getTable().getItem(index);
			final String property = (String) viewer.getColumnProperties()[focusCell.getColumnIndex()];
			if (modifier.canModify(item, property)) {
				FordiacClipboard.INSTANCE.setTableContents(focusCell.getText());
			}
		}
	}

}
