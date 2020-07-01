/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.ui.widgets;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class OpenStructMenu {
	public static void addTo(TableViewer viewer) {
		Menu menu = new Menu(viewer.getControl());
		MenuItem openItem = createMenuItem(viewer, menu);
		menu.addMenuListener(new MenuListener() {
			@Override
			public void menuShown(MenuEvent e) {
				Item[] selection = viewer.getTable().getSelection();
				if (!(selection[0].getData() instanceof Event)
						&& !(selection[0].getData() instanceof AdapterDeclaration)) {
					openItem.setEnabled((getSelectedStructuredType(selection) != null)
							&& !getSelectedStructuredType(selection).getName().contentEquals("ANY_STRUCT")); //$NON-NLS-1$
				} else {
					menu.setVisible(false);
				}
			}

			@Override
			public void menuHidden(MenuEvent e) {
			}

		});
		viewer.getControl().setMenu(menu);
	}


	private static MenuItem createMenuItem(TableViewer viewer, Menu menu) {
		MenuItem openItem = new MenuItem(menu, SWT.NONE);
		openItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StructuredType sel = getSelectedStructuredType(viewer.getTable().getSelection());
				if (sel != null) {
					openStructEditor(sel.getPaletteEntry().getFile());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		openItem.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);
		return openItem;
	}

	private static StructuredType getSelectedStructuredType(Item[] selected) {
		if (selected[0].getData() instanceof VarDeclaration) {
			VarDeclaration varDecl = (VarDeclaration) selected[0].getData();
			if (varDecl.getType() instanceof StructuredType) {
				return (StructuredType) varDecl.getType();
			}
		}
		return null;
	}

	public static void openStructEditor(IFile file) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (null != workbench) {
			IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
			if (null != activeWorkbenchWindow) {

				IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
				IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
				try {
					activePage.openEditor(new FileEditorInput(file), desc.getId());
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static void addTo(Control control, MenuListener listener, SelectionListener selectionListener) {
		Menu menu = new Menu(control);
		MenuItem openItem = new MenuItem(menu, SWT.NONE);
		openItem.addSelectionListener(selectionListener);
		openItem.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);
		menu.addMenuListener(listener);
		control.setMenu(menu);
	}

	private OpenStructMenu() {
		throw new UnsupportedOperationException();
	}

}
