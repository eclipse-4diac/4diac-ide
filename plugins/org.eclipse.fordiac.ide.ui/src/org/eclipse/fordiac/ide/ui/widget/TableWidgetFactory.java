/*******************************************************************************
 * Copyright (c) 2019, 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Bianca Wiesmayr, Virendra Ashiwal - initial implementation
 *   Alois Zoitl, Bianca Wiesmayr, Virendra Ashiwal
 *     - added style parameter to the createtableViewer method
 *   Alois Zoitl
 *     - changed editor activation behavior to double click and added tab through
 *       cells behavior
 *     - extracted helper for ComboCellEditors that unfold on activation
 *   Daniel Lindhuber
 *     - added enableCopyPasteCut method
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.views.properties.tabbed.TabContents;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public final class TableWidgetFactory {

	private static List<IWorkbenchSite> handledSites = new ArrayList<>();

	public static TableViewer createTableViewer(final Composite parent) {
		return createTableViewer(parent, 0);
	}

	public static TableViewer createTableViewer(final Composite parent, int style) {
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		TableViewer tableViewer = createGenericTableViewer(gridData, parent, style);

		gridData.heightHint = 150;
		gridData.widthHint = 80;

		return tableViewer;
	}

	public static TableViewer createPropertyTableViewer(final Composite parent) {
		return createPropertyTableViewer(parent, 0);
	}

	public static TableViewer createPropertyTableViewer(final Composite parent, int style) {
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		TableViewer tableViewer = createGenericTableViewer(gridData, parent, style);

		gridData.minimumHeight = 80;
		gridData.heightHint = 4;
		gridData.widthHint = 400;

		return tableViewer;
	}

	private static TableViewer createGenericTableViewer(GridData gridData, final Composite parent, int style) {
		TableViewer tableViewer = new TableViewer(parent,
				SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | style);

		ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(tableViewer) {
			@Override
			protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				return (event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL)
						|| (event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION)
						|| ((event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED)
								&& (event.keyCode == SWT.CR))
						|| (event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC);
			}
		};

		TableViewerEditor.create(tableViewer, actSupport,
				ColumnViewerEditor.TABBING_HORIZONTAL | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
						| ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION);

		tableViewer.getControl().setLayoutData(gridData);
		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		return tableViewer;
	}

	private TableWidgetFactory() {
		throw new UnsupportedOperationException("Widget Factory should not be instantiated"); //$NON-NLS-1$
	}

	public static void enableCopyPasteCut(final Object part) {
		if (part instanceof IWorkbenchPart) {
			IWorkbenchSite site = ((IWorkbenchPart) part).getSite();
			if (checkHandlers(site)) {
				activateWorkbenchHandlers(part, false);
				handledSites.add(site);
			}
		} else if (part instanceof IPageBookViewPage) {
			IWorkbenchSite site = ((IPageBookViewPage) part).getSite();
			if (checkHandlers(site)) {
				activateWorkbenchHandlers(part, true);
				handledSites.add(site);
			}
		}
	}

	private static boolean checkHandlers(IWorkbenchSite site) {
		for (IWorkbenchSite v : handledSites) {
			if (v == site) {
				if (site.hasService(IHandlerService.class)) {
					return false;
				}
				handledSites.remove(site);
				return true;
			}
		}
		return true;
	}

	private static I4diacTableUtil getView(Object obj) {
		if (obj instanceof I4diacTableUtil) {
			return (I4diacTableUtil) obj;
		}
		return null;
	}

	private static I4diacTableUtil getTab(Object part) {
		TabContents content = (((TabbedPropertySheetPage) part).getCurrentTab());
		return getView(content.getSectionAtIndex(0));
	}

	public static void activateWorkbenchHandlers(Object part, boolean isTabbed) {
		IWorkbenchSite site;
		if (isTabbed) {
			site = ((IPageBookViewPage) part).getSite();
		} else {
			site = ((IWorkbenchPart) part).getSite();
		}
		IHandlerService serv = site.getService(IHandlerService.class);
		Clipboard cb = Clipboard.getDefault();

		serv.activateHandler(org.eclipse.ui.IWorkbenchCommandConstants.EDIT_COPY, new AbstractHandler() {
			@Override
			public Object execute(ExecutionEvent event) throws ExecutionException {
				I4diacTableUtil view = isTabbed ? getTab(part) : getView(site.getSelectionProvider());
				if (view == null) {
					return Status.CANCEL_STATUS;
				}
				Object[] selection = ((StructuredSelection) view.getViewer().getSelection()).toArray();
				cb.setContents(selection);
				return Status.OK_STATUS;
			}
		});

		serv.activateHandler(org.eclipse.ui.IWorkbenchCommandConstants.EDIT_PASTE, new AbstractHandler() {
			@Override
			public Object execute(ExecutionEvent event) throws ExecutionException {
				I4diacTableUtil view = isTabbed ? getTab(part) : getView(site.getSelectionProvider());
				if (view == null) {
					return Status.CANCEL_STATUS;
				}
				Table table = view.getViewer().getTable();
				Object[] entries;
				try {
					entries = (Object[]) cb.getContents();
				} catch (Exception e) {
					return Status.CANCEL_STATUS;
				}
				if (cb.getContents() == null) {
					return Status.CANCEL_STATUS;
				}
				int[] pasteIndices = table.getSelectionIndices();
				int index;
				if (pasteIndices.length == 0) {
					if (table.getItemCount() > 0) {
						// no entry is selected -> insert at the bottom of the table
						index = table.getItemCount();
					} else {
						// no entries in the table
						index = 0;
					}
				} else {
					// use the last entry for multi selections
					index = pasteIndices[pasteIndices.length - 1] + 1;
				}
				CompoundCommand cmpCommand = new CompoundCommand();
				int[] selectionIndices = new int[entries.length];
				for (int i = 0; i < entries.length; i++) {
					selectionIndices[i] = index;
					view.addEntry(entries[i], index++, cmpCommand);
				}
				view.executeCompoundCommand(cmpCommand);
				table.forceFocus();
				// the selection has to be set again via the table viewer for the widgets to
				// recognize it
				table.setSelection(selectionIndices);
				TableViewer tableViewer = view.getViewer();
				tableViewer.setSelection(tableViewer.getSelection());
				return Status.OK_STATUS;
			}
		});

		serv.activateHandler(org.eclipse.ui.IWorkbenchCommandConstants.EDIT_CUT, new AbstractHandler() {
			@Override
			public Object execute(ExecutionEvent event) throws ExecutionException {
				I4diacTableUtil view = isTabbed ? getTab(part) : getView(site.getSelectionProvider());
				if (view == null) {
					return Status.CANCEL_STATUS;
				}
				Table table = view.getViewer().getTable();
				int[] indices = table.getSelectionIndices();
				if (indices.length == 0) {
					return Status.CANCEL_STATUS;
				}
				Object[] entries = new Object[indices.length];
				CompoundCommand cmpCommand = new CompoundCommand();
				for (int i = 0; i < indices.length; i++) {
					entries[i] = view.removeEntry(indices[i], cmpCommand);
				}
				view.executeCompoundCommand(cmpCommand);
				cb.setContents(entries);
				return Status.OK_STATUS;
			}
		});
	}
}
