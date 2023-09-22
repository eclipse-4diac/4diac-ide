/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Germany GmbH,
 *                          Primetals Technologies Austria GmbH,
 *                          Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - extracted out of the DataTypeDropdown class
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.model.ui.nat.TypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.LabelProvider;
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
	public DataTypeTreeSelectionDialog(final Shell parent) {
		super(parent, createTreeLabelProvider(), new TypeSelectionTreeContentProvider());
	}

	public DataTypeTreeSelectionDialog(final Shell parent,
			final TypeSelectionTreeContentProvider typeSelectionTreeContentProvider) {
		super(parent, createTreeLabelProvider(), typeSelectionTreeContentProvider);
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
		final Object selected = ((TreeSelection) getTreeViewer().getSelection()).getFirstElement();
		if (selected instanceof TypeNode) {
			final EObject dtp = ((TypeNode) selected).getType();
			if (dtp instanceof StructuredType) {
				return (StructuredType) dtp;
			}
		}
		return null;
	}

	protected static LabelProvider createTreeLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof TypeNode) {
					return ((TypeNode) element).getName();
				}
				return element.toString();
			}

			@Override
			public Image getImage(final Object element) {
				if (element instanceof final TypeNode node) {
					if (node.isDirectory()) {
						return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
					}
					return FordiacImage.ICON_DATA_TYPE.getImage();
				}
				return super.getImage(element);
			}
		};
	}

}