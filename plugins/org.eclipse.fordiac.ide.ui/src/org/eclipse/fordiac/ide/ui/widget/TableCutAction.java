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

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
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
				// cell can be null if column is not editable
				if (cell != null && cell.isActivated()) {
					cell.performCut();
					return;
				}
			}
			// This IF will be removed once the new copy/paste handling has been approved.
			String instanceClassName = "CommentPropertySection"; //$NON-NLS-1$
			if (instanceClassName.equals(editor.getClass().getSimpleName())) {
				handleInstancePropertySheet(editor);
			} else {
				cutItems(editor);			
			}
		}
	}
	
	private static void handleInstancePropertySheet(I4diacTableUtil editor) {
		TableViewer viewer = editor.getViewer();
		ViewerCell focusCell = viewer.getColumnViewerEditor().getFocusCell();
		ICellModifier modifier = viewer.getCellModifier();
		
		if (focusCell != null) {
			TableItem item = viewer.getTable().getItem(viewer.getTable().getSelectionIndex());
			String property = (String) viewer.getColumnProperties()[focusCell.getColumnIndex()];
			if (modifier.canModify(item, property)) {
				Clipboard.getDefault().setContents(focusCell.getText());
				modifier.modify(item, property, ""); //$NON-NLS-1$
			}
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
