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
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.Activator;
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

public final class OpenStructMenu {
	public static void addTo(final TableViewer viewer) {
		final Menu menu = new Menu(viewer.getControl());
		final MenuItem openItem = createMenuItem(viewer, menu);
		menu.addMenuListener(new MenuListener() {
			@Override
			public void menuShown(final MenuEvent e) {
				final Item[] selection = viewer.getTable().getSelection();
				if (!(selection[0].getData() instanceof Event)
						&& !(selection[0].getData() instanceof AdapterDeclaration)) {
					final StructuredType type = getSelectedStructuredType(selection);
					openItem.setEnabled((type != null) && (type != IecTypes.GenericTypes.ANY_STRUCT));
				} else {
					menu.setVisible(false);
				}
			}

			@Override
			public void menuHidden(final MenuEvent e) {
			}

		});
		viewer.getControl().setMenu(menu);
	}


	private static MenuItem createMenuItem(final TableViewer viewer, final Menu menu) {
		final MenuItem openItem = new MenuItem(menu, SWT.NONE);
		openItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final StructuredType sel = getSelectedStructuredType(viewer.getTable().getSelection());
				if (sel != null) {
					openStructEditor(sel.getPaletteEntry().getFile());
				}
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				widgetSelected(e);
			}
		});
		openItem.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);
		return openItem;
	}

	private static StructuredType getSelectedStructuredType(final Item[] selected) {
		if (selected[0].getData() instanceof VarDeclaration) {
			final VarDeclaration varDecl = (VarDeclaration) selected[0].getData();
			if (varDecl.getType() instanceof StructuredType) {
				return (StructuredType) varDecl.getType();
			}
		}
		return null;
	}

	public static void openStructEditor(final IFile file) {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (null != workbench) {
			final IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
			if (null != activeWorkbenchWindow) {

				final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
				final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
				try {
					activePage.openEditor(new FileEditorInput(file), desc.getId());
				} catch (final PartInitException e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
			}
		}
	}

	public static void addTo(final Control control, final MenuListener listener, final SelectionListener selectionListener) {
		final Menu menu = new Menu(control);
		final MenuItem openItem = new MenuItem(menu, SWT.NONE);
		openItem.addSelectionListener(selectionListener);
		openItem.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);
		menu.addMenuListener(listener);
		control.setMenu(menu);
	}

	private OpenStructMenu() {
		throw new UnsupportedOperationException();
	}

}
