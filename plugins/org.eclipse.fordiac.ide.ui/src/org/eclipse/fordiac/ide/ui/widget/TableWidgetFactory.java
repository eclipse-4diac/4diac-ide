/*******************************************************************************
 * Copyright (c) 2019, 2022 Johannes Kepler University Linz,
 *                          Primetals Technologies Austria GmbH
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
 *     - added copy/paste/cut for tables
 *   Alois Zoitl - added single cell selection support
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.TabContents;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public final class TableWidgetFactory {



	private static List<IWorkbenchSite> handledSites = new ArrayList<>();

	public static TableViewer createTableViewer(final Composite parent) {
		return createTableViewer(parent, 0);
	}

	public static TableViewer createTableViewer(final Composite parent, final int style) {
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		final TableViewer tableViewer = createGenericTableViewer(gridData, parent, style);

		gridData.heightHint = 150;
		gridData.widthHint = 80;

		return tableViewer;
	}

	public static TableViewer createPropertyTableViewer(final Composite parent) {
		return createPropertyTableViewer(parent, 0);
	}

	public static TableViewer createPropertyTableViewer(final Composite parent, final int style) {
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		final TableViewer tableViewer = createGenericTableViewer(gridData, parent, style);

		gridData.minimumHeight = 80;
		gridData.heightHint = 4;
		gridData.widthHint = 400;

		return tableViewer;
	}

	private static TableViewer createGenericTableViewer(final GridData gridData, final Composite parent, final int style) {
		final TableViewer tableViewer = new TableViewer(parent,
				SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | style);

		final TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(tableViewer,
				new FocusCellOwnerDrawHighlighter(tableViewer));
		final ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(tableViewer) {
			@Override
			protected boolean isEditorActivationEvent(final ColumnViewerEditorActivationEvent event) {
				return (event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL)
						|| (event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION)
						|| ((event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED)
								&& (event.keyCode == SWT.CR || event.keyCode == SWT.F2))
						|| (event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC);
			}
		};

		TableViewerEditor.create(tableViewer, focusCellManager, actSupport,
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

	/** @param part can be of type:
	 *             <ul>
	 *             <li>TabbedPropertySheetPage</li>
	 *             <li>IWorkbenchPart</li>
	 *             </ul>
	 */
	public static void enableCopyPasteCut(final Object part) {
		final IWorkbenchSite site = getSite(part);
		if (site != null) {

			for (final IWorkbenchSite s : handledSites) {
				if (s == site) {
					return;
				}
			}
			final ActionRegistry registry = new ActionRegistry();
			registerActions(part, registry);
			setActionHandlers(site, registry);
			handledSites.add(site);
		}
	}

	private static void registerActions(final Object part, final ActionRegistry registry) {
		IAction action = new TableCopyAction(part);
		registry.registerAction(action);

		action = new TablePasteAction(part);
		registry.registerAction(action);

		action = new TableCutAction(part);
		registry.registerAction(action);
	}

	private static void setActionHandlers(final IWorkbenchSite site, final ActionRegistry registry) {
		final IActionBars bars = getActionBars(site);
		if (bars != null) {
			String id = ActionFactory.COPY.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.PASTE.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.CUT.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			bars.updateActionBars();
		}
	}

	private static IActionBars getActionBars(final IWorkbenchSite site) {
		if (site instanceof IEditorSite) {
			return ((IEditorSite) site).getActionBars();
		} else if (site instanceof IPageSite) {
			return ((IPageSite) site).getActionBars();
		}
		return null;
	}

	private static IWorkbenchSite getSite(final Object part) {
		if (part instanceof IWorkbenchPart) {
			return ((IWorkbenchPart) part).getSite();
		} else if (part instanceof IPageBookViewPage) {
			return ((TabbedPropertySheetPage) part).getSite();
		}
		return null;
	}

	public static I4diacTableUtil getTableEditor(final Object part) {
		if (part instanceof IWorkbenchPart) {
			return getTableEditorFromWorkbenchPart((IWorkbenchPart) part);
		} else if (part instanceof IPageBookViewPage) {
			return getTableEditorFromPropertySheet((TabbedPropertySheetPage) part);
		}
		return null;
	}

	private static I4diacTableUtil getTableEditorFromWorkbenchPart(final IWorkbenchPart part) {
		final IWorkbenchWindow window = part.getSite().getWorkbenchWindow();
		final ISelection sel = window.getSelectionService().getSelection();
		final Object editor = (sel instanceof StructuredSelection) ? ((StructuredSelection) sel).getFirstElement()
				: null;
		return (editor instanceof I4diacTableUtil) ? (I4diacTableUtil) editor : null;
	}

	private static I4diacTableUtil getTableEditorFromPropertySheet(final TabbedPropertySheetPage part) {
		final TabContents content = part.getCurrentTab();
		final ISection section = content.getSectionAtIndex(0);
		return (section instanceof I4diacTableUtil) ? (I4diacTableUtil) section : null;
	}

}
