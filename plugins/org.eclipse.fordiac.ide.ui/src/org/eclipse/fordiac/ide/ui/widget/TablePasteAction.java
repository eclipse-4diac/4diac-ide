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
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

public class TablePasteAction extends Action {

	private final Object part;

	public TablePasteAction(final Object part) {
		this.part = part;
		setId(ActionFactory.PASTE.getId());
		setText(FordiacMessages.TableCopyPaste_TEXT_Paste);
		final ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
	}

	@Override
	public void run() {
		final I4diacTableUtil editor = TableWidgetFactory.getTableEditor(part);

		if (editor != null) {
			for (final CellEditor cell : editor.getViewer().getCellEditors()) {
				// cell can be null if column is not editable
				if (cell != null && cell.isActivated()) {
					cell.performPaste();
					return;
				}
			}
			// This IF will be removed once the new copy/paste handling has been approved.
			final String instanceClassName = "CommentPropertySection";
			if (instanceClassName.equals(editor.getClass().getSimpleName())) {
				handleInstancePropertySheet(editor);
			} else {
				pasteItems(editor);
			}
		}
	}

	private static void handleInstancePropertySheet(final I4diacTableUtil editor) {
		// cell selection does not support multi-selection so we do not have to
		// care about more cells being selected

		final TableViewer viewer = editor.getViewer();
		final ViewerCell focusCell = viewer.getColumnViewerEditor().getFocusCell();
		final ICellModifier modifier = viewer.getCellModifier();
		final Object content = FordiacClipboard.INSTANCE.getTableContents();
		final int index = viewer.getTable().getSelectionIndex();

		if (focusCell != null && index >= 0 && content instanceof String) {
			final TableItem item = viewer.getTable().getItem(index);
			final String property = (String) viewer.getColumnProperties()[focusCell.getColumnIndex()];
			if (modifier.canModify(item, property)) {
				modifier.modify(item, property, content);
			}
		}
	}

	private static void pasteItems(final I4diacTableUtil editor) {
		final TableViewer viewer = editor.getViewer();
		final Table table = viewer.getTable();
		final int[] pasteIndices = table.getSelectionIndices();
		final Object content = Clipboard.getDefault().getContents();
		final Object[] entries = createEntriesFromContent(content);

		// catch wrong clipboard contents
		if (entries == null) {
			return;
		}

		int index = getInsertionStartIndex(pasteIndices, table);

		// is filled by the editor with the according "add"-Commands
		final CompoundCommand cmpCommand = new CompoundCommand();
		final int[] selectionIndices = new int[entries.length];

		for (int i = 0; i < entries.length; i++) {
			selectionIndices[i] = index; // store the position of inserted objects for selection later on
			editor.addEntry(entries[i], index++, cmpCommand);
		}

		editor.executeCompoundCommand(cmpCommand);
		table.forceFocus();
		// the selection has to be set again via the table viewer for the widgets to
		// recognize it
		table.setSelection(selectionIndices);
		viewer.setSelection(viewer.getSelection());
	}

	private static Object[] createEntriesFromContent(final Object content) {
		return (content instanceof StructuredSelection) ? ((StructuredSelection) content).toArray()
				: (Object[]) content;
	}

	private static int getInsertionStartIndex(final int[] pasteIndices, final Table table) {
		int index;
		if (pasteIndices.length == 0) {
			// itemCount > 0 ==> no entry is selected and we insert at the bottom of the table
			// otherwise the table is empty and we start to insert at 0
			index = table.getItemCount() > 0 ? table.getItemCount() : 0;
		} else {
			// use the last index as insertion start for multi selections
			index = pasteIndices[pasteIndices.length - 1] + 1;
		}
		return index;
	}

}
