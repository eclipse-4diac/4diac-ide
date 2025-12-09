/*******************************************************************************
 * Copyright (c) 2020, 2023 Primetals Technologies Germany GmbH,
 *                          Primetals Technologies Austria GmbH,
 *                          Johannes Kepler University Linz
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - extracted out of the DataTypeDropdown class
 *   Martin Erich Jobst - show FQN in type node labels
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import org.eclipse.emf.edit.ui.provider.DelegatingStyledCellLabelProvider;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

public class DataTypeTreeSelectionDialog extends ElementTreeSelectionDialog {

	public DataTypeTreeSelectionDialog(final Shell parent, final ITreeContentProvider contentProvider) {
		this(parent, contentProvider, new TreeNodeLabelProvider());
	}

	public DataTypeTreeSelectionDialog(final Shell parent, final ITreeContentProvider contentProvider,
			final IStyledLabelProvider labelProvider) {
		super(parent, new DelegatingStyledCellLabelProvider(labelProvider), contentProvider);
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Control control = super.createDialogArea(parent);
		createContextMenu(getTreeViewer().getTree());
		return control;
	}

	private void createContextMenu(final Control control) {
		final Menu openEditorMenu = new Menu(control);
		final MenuItem openItem = new MenuItem(openEditorMenu, SWT.NONE);
		openItem.addListener(SWT.Selection, e -> {
			final StructuredType sel = getSelectedStructuredType();
			if (sel != null) {
				handleShellCloseEvent();
				setResult(null); // discard selection, do not update type
				OpenStructMenu.openStructEditor(sel.getTypeEntry().getFile());
			}
		});
		openItem.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);

		openEditorMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuShown(final MenuEvent e) {
				final StructuredType type = getSelectedStructuredType();
				openItem.setEnabled((type != null) && (type != IecTypes.GenericTypes.ANY_STRUCT));
			}

			@Override
			public void menuHidden(final MenuEvent e) {
				// nothing to be done here
			}
		});
		control.setMenu(openEditorMenu);
	}

	private StructuredType getSelectedStructuredType() {
		return ((TreeSelection) getTreeViewer().getSelection()).getFirstElement() instanceof final TypeNode typeNode
				&& typeNode.getType() instanceof final StructuredType structType ? structType : null;
	}

	public static class TreeNodeLabelProvider extends LabelProvider implements IStyledLabelProvider {

		@Override
		public String getText(final Object element) {
			if (element instanceof final TypeNode typeNode) {
				return typeNode.getName();
			}
			return element.toString();
		}

		@Override
		public StyledString getStyledText(final Object element) {
			if (element instanceof final TypeNode typeNode && !typeNode.isDirectory()
					&& !typeNode.getPackageName().isEmpty()) {
				return new StyledString(typeNode.getName()).append(" - " + typeNode.getPackageName(), //$NON-NLS-1$
						StyledString.QUALIFIER_STYLER);
			}
			return new StyledString(getText(element));
		}

		@Override
		public Image getImage(final Object element) {
			if (element instanceof final TypeNode node) {
				if (node.isDirectory()) {
					return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
				}
				return FordiacImage.ICON_DATA_TYPE.getImage();
			}
			return super.getImage(element);
		}
	}
}
