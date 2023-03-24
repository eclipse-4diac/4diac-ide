/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.segment.widget;

import org.eclipse.fordiac.ide.model.annotations.MappingAnnotations;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationChannel;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Messages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

public class MappedFbMenu {

	public static void addContextMenu(final TableViewer viewer) {
		final Menu menu = new Menu(viewer.getControl());
		final MenuItem openItem = createMenuItem(viewer, menu);
		menu.addMenuListener(new MenuListener() {
			@Override
			public void menuShown(final MenuEvent e) {
				final Item[] selection = viewer.getTable().getSelection();
				final boolean isWindowSelected = ((selection.length > 0)
						&& (selection[0].getData() instanceof CommunicationMappingTarget));
				openItem.setEnabled(isWindowSelected);
				menu.setVisible(isWindowSelected);
			}

			@Override
			public void menuHidden(final MenuEvent e) {
				menu.setVisible(false);
			}

		});
		viewer.getControl().setMenu(menu);
	}

	private static MenuItem createMenuItem(final TableViewer viewer, final Menu menu) {
		final MenuItem openItem = new MenuItem(menu, SWT.NONE);
		openItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final CommunicationMappingTarget sel = getSelectedWindow(viewer.getTable().getSelection());
				if (sel != null) {
					openDialog(sel);
				}
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				widgetSelected(e);
			}
		});
		openItem.setText(Messages.TsnDetails_ShowMappedCommunicationFBs);
		return openItem;
	}

	private static CommunicationMappingTarget getSelectedWindow(final Item[] selected) {
		if ((selected.length > 0) && (selected[0].getData() instanceof final CommunicationMappingTarget window)) {
			return window;
		}
		return null;
	}

	private static void openDialog(final CommunicationMappingTarget sel) {
		final ITreeContentProvider treeProvider = createTreeContentProvider();
		final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), createLabelProvider(), treeProvider);
		dialog.setTitle(Messages.TsnDetails_ShowMappedCommunicationFBs);
		dialog.setInput(sel);
		dialog.setHelpAvailable(false);
		dialog.setAllowMultiple(false);
		dialog.open();

	}

	private static ILabelProvider createLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final CommunicationChannel channel) {
					return MappingAnnotations.getHierarchicalName(channel) + "." + channel.getName(); //$NON-NLS-1$
				}
				return super.getText(element);
			}

			@Override
			public Image getImage(final Object element) {
				return FordiacImage.ICON_FB.getImage();
			}

		};
	}

	private static ITreeContentProvider createTreeContentProvider() {
		return new ITreeContentProvider() {

			@Override
			public Object[] getElements(final Object inputElement) {
				if (inputElement instanceof final CommunicationMappingTarget target) {
					return target.getMappedElements().toArray();
				}
				return new Object[0];
			}

			@Override
			public Object[] getChildren(final Object parentElement) {
				return new Object[0];
			}

			@Override
			public Object getParent(final Object element) {
				return null;
			}

			@Override
			public boolean hasChildren(final Object element) {
				return false;
			}

		};

	}

	private MappedFbMenu() {
		throw new UnsupportedOperationException();
	}

}
