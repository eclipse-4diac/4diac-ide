/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.views.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.graph.FBNetworkEventLaneGraph;
import org.eclipse.fordiac.ide.model.graph.FBNetworkGraph;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.progress.UIJob;

public class EventSequencePage extends Page implements IAdaptable {

	private static final long REFRESH_DELAY = 500; // in milliseconds

	private static final Transfer[] DND_TRANSFER_TYPES = new Transfer[] { LocalSelectionTransfer.getTransfer() };

	private final IWorkbenchPart part;
	private final CommandStack commandStack;
	private FBNetworkLaneGraphTreeViewer viewer;
	private FBNetwork network;

	private final Map<FBNetwork, FBNetworkScopedContentAdapter> observedNetworks = new HashMap<>();
	private final UIJob refreshJob = UIJob.create(Messages.EventSequencePage_RefreshJobName,
			(ICoreRunnable) monitor -> refreshViewer());

	public EventSequencePage(final IWorkbenchPart part) {
		this.part = part;
		this.commandStack = Adapters.adapt(part, CommandStack.class);
	}

	@Override
	public void init(final IPageSite pageSite) {
		super.init(pageSite);
		setupActions();
	}

	private void setupActions() {
		final IActionBars partActionBars = switch (part) {
		case final IEditorPart editorPart -> editorPart.getEditorSite().getActionBars();
		case final IViewPart viewPart -> viewPart.getViewSite().getActionBars();
		case null, default -> null;
		};

		if (partActionBars != null) {
			final IActionBars actionBars = getSite().getActionBars();
			actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
					partActionBars.getGlobalActionHandler(ActionFactory.UNDO.getId()));
			actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),
					partActionBars.getGlobalActionHandler(ActionFactory.REDO.getId()));
			actionBars.updateActionBars();
		}
	}

	@Override
	public void createControl(final Composite parent) {
		viewer = new FBNetworkLaneGraphTreeViewer(parent,
				SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		final Tree tree = viewer.getTree();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		createColumns();

		viewer.setContentProvider(new FBNetworkGraphContentProvider());
		viewer.addDoubleClickListener(EventSequencePage::handleDoubleClick);
		viewer.addDragSupport(DND.DROP_MOVE, DND_TRANSFER_TYPES, new EventSequenceDragSource(viewer));
		viewer.addDropSupport(DND.DROP_MOVE, DND_TRANSFER_TYPES, new EventSequenceDropAdapter(viewer, commandStack));
		viewer.addTreeListener(new ITreeViewerListener() {
			@Override
			public void treeExpanded(final TreeExpansionEvent event) {
				if (event.getElement() instanceof final FBNetworkGraph<?>.Node node
						&& node.getElement() instanceof final UntypedSubApp subApp) {
					addNetworkAdapter(subApp.getSubAppNetwork());
				}
				Display.getCurrent().asyncExec(EventSequencePage.this::updateColumnWidths);
			}

			@Override
			public void treeCollapsed(final TreeExpansionEvent event) {
				// do nothing
			}
		});

		if (network != null) {
			setNetwork(network);
		}
	}

	private void createColumns() {
		final TreeViewerColumn elementColumn = new TreeViewerColumn(viewer, SWT.LEFT);
		elementColumn.getColumn().setText(Messages.EventSequencePage_NameColumn);
		elementColumn.getColumn().pack();
		elementColumn.setLabelProvider(new FBNetworkGraphNameLabelProvider());

		final TreeViewerColumn sequenceNumberColumn = new TreeViewerColumn(viewer, SWT.RIGHT);
		sequenceNumberColumn.getColumn().setText(Messages.EventSequencePage_SequenceColumn);
		sequenceNumberColumn.getColumn().pack();
		sequenceNumberColumn.setLabelProvider(new FBNetworkGraphSequenceNumberLabelProvider());

		final TreeViewerColumn graphColumn = new TreeViewerColumn(viewer, SWT.LEFT);
		graphColumn.getColumn().setText(Messages.EventSequencePage_ConnectionsColumn);
		graphColumn.getColumn().pack();
		graphColumn.setLabelProvider(new ColumnLabelProvider());

		viewer.setGraphIndex(2);
	}

	private static void handleDoubleClick(final DoubleClickEvent event) {
		if (event.getSelection() instanceof final IStructuredSelection selection
				&& selection.getFirstElement() instanceof final FBNetworkGraph<?>.Node node) {
			final var element = node.getElement();
			final var editor = HandlerHelper.openEditor(element);
			HandlerHelper.selectElement(element, editor);
		}
	}

	@Override
	public Control getControl() {
		return viewer != null ? viewer.getControl() : null;
	}

	@Override
	public void setFocus() {
		if (viewer != null && !viewer.getControl().isDisposed()) {
			viewer.getControl().setFocus();
		}
	}

	public void setNetwork(final FBNetwork network) {
		if (this.network == network) {
			return;
		}

		removeNetworkAdapters();
		this.network = network;
		addNetworkAdapter(this.network);

		refreshViewer();
	}

	private void refreshViewer() {
		if (viewer != null && !viewer.getControl().isDisposed()) {
			final Object[] expandedElements = viewer.getExpandedElements();
			viewer.setInput(network != null ? new FBNetworkEventLaneGraph(network) : null);
			viewer.setExpandedElements(expandedElements);
			Display.getCurrent().asyncExec(this::updateColumnWidths);
		}
	}

	private void updateColumnWidths() {
		if (viewer != null && !viewer.getControl().isDisposed()) {
			for (final TreeColumn column : viewer.getTree().getColumns()) {
				final int oldWidth = column.getWidth();
				column.pack();
				column.setWidth(Math.max(oldWidth, column.getWidth()));
			}
		}
	}

	private void addNetworkAdapter(final FBNetwork network) {
		if (network != null) {
			observedNetworks.computeIfAbsent(network, this::createAdapter);
		}
	}

	private void removeNetworkAdapter(final FBNetwork network) {
		if (network != null) {
			final FBNetworkScopedContentAdapter adapter = observedNetworks.remove(network);
			if (adapter != null) {
				adapter.dispose();
			}
		}
	}

	private void removeNetworkAdapters() {
		observedNetworks.values().forEach(FBNetworkScopedContentAdapter::dispose);
		observedNetworks.clear();
	}

	private FBNetworkScopedContentAdapter createAdapter(final FBNetwork network) {
		return new FBNetworkScopedContentAdapter(network) {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				if (notification.getFeature() == LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS) {
					handleNetworkElementsChanged(notification);
				}
				if (!notification.isTouch() && shouldRefresh(notification)) {
					refreshJob.cancel();
					refreshJob.schedule(REFRESH_DELAY);
				}
			}

			private boolean shouldRefresh(final Notification notification) {
				return notification.getFeature() == LibraryElementPackage.Literals.INAMED_ELEMENT__NAME
						|| notification.getFeature() == LibraryElementPackage.Literals.FB_NETWORK__NETWORK_ELEMENTS
						|| notification.getFeature() == LibraryElementPackage.Literals.FB_NETWORK__EVENT_CONNECTIONS;
			}

			private void handleNetworkElementsChanged(final Notification notification) {
				switch (notification.getEventType()) {
				case Notification.REMOVE -> elementRemoved(notification.getOldValue());
				case Notification.REMOVE_MANY ->
					((Collection<?>) notification.getOldValue()).forEach(this::elementRemoved);
				default -> {
					// do nothing
				}
				}
			}

			private void elementRemoved(final Object element) {
				if (element instanceof final UntypedSubApp untypedSubApp) {
					removeNetworkAdapter(untypedSubApp.getSubAppNetwork());
					untypedSubApp.getSubAppNetwork().getNetworkElements().forEach(this::elementRemoved);
				}
			}
		};
	}

	@Override
	public void dispose() {
		removeNetworkAdapters();
		refreshJob.cancel();
		super.dispose();
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == CommandStack.class) {
			return adapter.cast(commandStack);
		}
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}
}
