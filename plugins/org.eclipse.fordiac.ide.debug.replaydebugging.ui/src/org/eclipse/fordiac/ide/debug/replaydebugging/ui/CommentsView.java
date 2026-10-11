/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.ILog;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.EventChange;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.EventPosition;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.command.TimelineViewCommandStack;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.TimelineModel;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.ISources;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

/**
 * Shows all comments made on timelines and events as a table with the columns
 * Timeline (the number of the timeline as displayed in the timeline view),
 * Event (empty for comments on a timeline itself) and Comment.
 *
 * The view is a presentation of the state held by {@link CommentsHandler}: it
 * registers as a listener and mirrors the comments into a table. It never
 * modifies the comments itself. Go To, Edit and Delete execute platform
 * commands whose handlers run the corresponding GEF commands on the command
 * stack of the timeline view. The table is updated through the handler's
 * notifications.
 *
 * While this view is active, the Edit menu's Undo and Redo (and Ctrl+Z) act on
 * that same stack, so they also undo or redo commands made in the timeline
 * view. They are disabled while the timeline view is not open.
 *
 * Interaction (the buttons are disabled while no row is selected):
 * <ul>
 * <li>Edit button, Space, or double-click on the comment cell: edit the comment
 * text (only the text; timeline and event are read-only). The committed text is
 * sent as a command.</li>
 * <li>Delete button, Del or Backspace: delete the comment.</li>
 * <li>Go To button, Enter, or double-click on any other cell of the row besides
 * the text: go to the commented timeline/event.</li>
 * </ul>
 * Each command receives the affected {@link CommentRow} as the current
 * selection of its execution context.
 */
public class CommentsView extends ViewPart {

	/** The id under which this view is registered in the plugin.xml. */
	public static final String ID = "org.eclipse.fordiac.ide.debug.replaydebugging.ui.CommentsView"; //$NON-NLS-1$

	/** Index of the Comment column; the only editable one. */
	private static final int COMMENT_COLUMN = 2;

	/**
	 * A single row of the table, and the element type of the selection passed to
	 * the commands.
	 *
	 * A row is either a comment on a timeline as a whole or a comment on a single
	 * event of a timeline. The timeline number is deliberately not stored: it is
	 * derived live (see {@link TimelineNumbering}), because it changes when
	 * timelines are spawned or removed.
	 *
	 * @param timeline  the timeline the comment belongs to, never {@code null}
	 * @param position  the event the comment belongs to, or {@code null} for a
	 *                  timeline comment
	 * @param comment   the comment text, never {@code null}
	 * @param navigator the navigator the comment was made on, never {@code null}
	 */
	public record CommentRow(Timeline timeline, EventPosition position, String comment, ReplayNavigator navigator) {

		/** Whether this comment is on a whole timeline rather than on an event. */
		public boolean isTimelineComment() {
			return position == null;
		}

		/**
		 * The row held by a selection, as passed to the handlers of this view's
		 * commands.
		 *
		 * @param selection a selection, or any other object
		 * @return the first element if it is a row, otherwise {@code null}
		 */
		public static CommentRow of(final Object selection) {
			return selection instanceof final IStructuredSelection s
					&& s.getFirstElement() instanceof final CommentRow row ? row : null;
		}
	}

	private TableViewer viewer;

	private Button editButton;
	private Button deleteButton;
	private Button goToButton;

	private Action undoAction;
	private Action redoAction;

	/** The timeline view's stack that Undo/Redo currently act on and listen to. */
	private CommandStack observedStack;

	/**
	 * The rows currently shown. Keys are either {@link EventPosition} or
	 * {@link Timeline}, mirroring the keys used by {@link CommentsHandler}.
	 */
	private final Map<Object, CommentRow> rows = new LinkedHashMap<>();

	/**
	 * Timeline numbers for the timeline trees that contain a comment. Rebuilt on
	 * every refresh.
	 */
	private final Map<Timeline, Integer> numbers = new HashMap<>();

	/** The timelines this view currently has a structure listener registered on. */
	private final Set<Timeline> watched = new HashSet<>();

	/** Keeps the table in sync with the comments stored in the handler. */
	private final CommentsHandler.Listener commentListener = new CommentsHandler.Listener() {
		@Override
		public void eventCommentChanged(final EventPosition position, final String comment, final Event event) {
			final ReplayNavigator navigator = event == Event.REMOVED ? null
					: CommentsHandler.getInstance().getReplayNavigator(position);
			update(position, event,
					navigator == null ? null : new CommentRow(position.timeline(), position, comment, navigator));
		}

		@Override
		public void timelineCommentChanged(final Timeline timeline, final String comment, final Event event) {
			final ReplayNavigator navigator = event == Event.REMOVED ? null
					: CommentsHandler.getInstance().getReplayNavigator(timeline);
			update(timeline, event, navigator == null ? null : new CommentRow(timeline, null, comment, navigator));
		}

		/**
		 * Applies a change reported by the {@link CommentsHandler} and refreshes the
		 * table.
		 *
		 * @param key   the key of the comment in the handler (position or timeline)
		 * @param event whether the comment was added/edited or removed
		 * @param row   the row for the new state; {@code null} if the comment was
		 *              removed again before this notification ran (a removal
		 *              notification follows in that case)
		 */
		private void update(final Object key, final CommentsHandler.Listener.Event event, final CommentRow row) {
			if (viewer == null || viewer.getControl().isDisposed()) {
				// notifications are delivered asynchronously and may arrive after disposal
				return;
			}
			if (event == CommentsHandler.Listener.Event.REMOVED) {
				rows.remove(key);
			} else if (row != null) {
				rows.put(key, row);
			} else {
				return;
			}
			refresh();
		}
	};

	/**
	 * Keeps the timeline numbers (and the visibility of comments on removed events)
	 * correct when the structure of the timelines changes.
	 */
	private final Timeline.StructureListener structureListener = new Timeline.StructureListener() {
		@Override
		public void eventAdded(final Timeline timeline) {
			refreshAsync();
		}

		@Override
		public void eventsRemoved(final Timeline timeline, final int removedStartEventIndex,
				final List<EventChange> removedChanges) {
			refreshAsync();
		}

		@Override
		public void timelineSpawned(final Timeline spawnedTimeline) {
			watchTree(spawnedTimeline);
			refreshAsync();
		}

		@Override
		public void timelineRemoved(final Timeline parentTimeline, final Timeline removedTimeline,
				final int spawnedAtEventNumber) {
			unwatchTree(removedTimeline);
			// Notified before the timeline is detached, so refresh later when the tree is
			// consistent again.
			refreshAsync();
		}

		@Override
		public void timelineStateChanged(final Timeline timeline) {
			// numbering is unaffected
		}

		/** Schedules a {@link #refresh()} on the UI thread. */
		private void refreshAsync() {
			Display.getDefault().asyncExec(() -> {
				if (viewer != null && !viewer.getControl().isDisposed()) {
					refresh();
				}
			});
		}
	};

	/** Keeps the Undo/Redo menu items in sync with the observed command stack. */
	private final CommandStackEventListener stackListener = _ -> updateUndoRedo();

	@Override
	public void createPartControl(final Composite parent) {
		final Composite root = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(root);

		createButtonBar(root);

		final Composite tableContainer = new Composite(root, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tableContainer);
		final TableColumnLayout layout = new TableColumnLayout();
		tableContainer.setLayout(layout);

		viewer = new TableViewer(tableContainer, SWT.FULL_SELECTION | SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(final Viewer v, final Object e1, final Object e2) {
				final CommentRow a = (CommentRow) e1;
				final CommentRow b = (CommentRow) e2;
				int result = Integer.compare(numberOf(a.timeline()), numberOf(b.timeline()));
				if (result == 0) {
					// the timeline's own comment sorts before its event comments
					final int ea = a.isTimelineComment() ? -1 : a.position().eventNumber();
					final int eb = b.isTimelineComment() ? -1 : b.position().eventNumber();
					result = Integer.compare(ea, eb);
				}
				return result;
			}
		});

		createColumn(layout, Messages.CommentsView_Timeline, new ColumnPixelData(90),
				row -> String.valueOf(numberOf(row.timeline())));
		createColumn(layout, Messages.CommentsView_Event, new ColumnPixelData(90), row -> row.isTimelineComment() ? "" //$NON-NLS-1$
				: String.valueOf(row.position().eventNumber() + row.timeline().getGlobalIndexStart()));
		final TableViewerColumn commentColumn = createColumn(layout, Messages.CommentsView_Comment,
				new ColumnWeightData(1, 200, true), CommentRow::comment);
		commentColumn.setEditingSupport(new CommentEditingSupport(viewer));

		// Double-click on the comment cell starts editing. Editing started by the
		// Edit button or the Space key is programmatic.
		TableViewerEditor.create(viewer, new ColumnViewerEditorActivationStrategy(viewer) {
			@Override
			protected boolean isEditorActivationEvent(final ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC
						|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION;
			}
		}, ColumnViewerEditor.DEFAULT);

		viewer.getTable().addKeyListener(KeyListener.keyPressedAdapter(e -> {
			switch (e.keyCode) {
			case SWT.DEL, SWT.BS -> deleteSelected();
			case SWT.CR, SWT.KEYPAD_CR -> goToSelected();
			default -> {
				if (e.character == ' ') {
					editSelected();
				}
			}
			}
		}));

		// Double-click on any cell except the comment cell goes to the comment.
		viewer.getTable().addMouseListener(MouseListener.mouseDoubleClickAdapter(e -> {
			final ViewerCell cell = viewer.getCell(new Point(e.x, e.y));
			if (cell != null && cell.getColumnIndex() != COMMENT_COLUMN) {
				goToSelected();
			}
		}));

		viewer.addSelectionChangedListener(_ -> updateButtons());
		viewer.setInput(List.of());
		updateButtons();

		getSite().setSelectionProvider(viewer);
		createUndoRedo();

		// Replays all existing comments on registration, then follows changes.
		CommentsHandler.getInstance().addListener(commentListener);
	}

	/** Creates the icon buttons Edit,, Delete, and Go To. */
	private void createButtonBar(final Composite parent) {
		final Composite buttonBar = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(buttonBar);
		RowLayoutFactory.fillDefaults().margins(4, 4).spacing(4).applyTo(buttonBar);

		final ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();

		editButton = createButton(buttonBar, sharedImages.getImage(ISharedImages.IMG_OBJ_FILE),
				Messages.CommentsView_EditComment, this::editSelected);
		deleteButton = createButton(buttonBar, sharedImages.getImage(ISharedImages.IMG_ETOOL_DELETE),
				Messages.CommentsView_DeleteComment, this::deleteSelected);
		goToButton = createButton(buttonBar, sharedImages.getImage(ISharedImages.IMG_TOOL_FORWARD),
				Messages.CommentsView_GoToEvent, this::goToSelected);
	}

	private static Button createButton(final Composite parent, final Image image, final String toolTip,
			final Runnable action) {
		final Button button = new Button(parent, SWT.PUSH);
		button.setImage(image);
		button.setToolTipText(toolTip);
		button.addSelectionListener(SelectionListener.widgetSelectedAdapter(_ -> action.run()));
		return button;
	}

	/** Enables the buttons only while a row is selected. */
	private void updateButtons() {
		final boolean hasSelection = selectedRow() != null;
		editButton.setEnabled(hasSelection);
		deleteButton.setEnabled(hasSelection);
		goToButton.setEnabled(hasSelection);
	}

	/**
	 * Makes the Edit menu's Undo and Redo (and their key bindings) act on the
	 * timeline view's command stack while this view is active.
	 */
	private void createUndoRedo() {
		undoAction = new Action() {
			@Override
			public void run() {
				if (observedStack != null && observedStack.canUndo()) {
					observedStack.undo();
				}
			}
		};
		redoAction = new Action() {
			@Override
			public void run() {
				if (observedStack != null && observedStack.canRedo()) {
					observedStack.redo();
				}
			}
		};
		final IActionBars actionBars = getViewSite().getActionBars();
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), undoAction);
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), redoAction);
		updateUndoRedo();
	}

	/**
	 * Looks up the timeline view's stack (it changes when that view is opened or
	 * closed), moves the listener to it, and updates enablement and labels of Undo
	 * and Redo. Called when this view gets focus, after a command was executed from
	 * here, and on every change of the observed stack.
	 */
	private void updateUndoRedo() {
		final CommandStack stack = TimelineViewCommandStack.find(getSite().getWorkbenchWindow(), false);
		if (stack != observedStack) {
			if (observedStack != null) {
				observedStack.removeCommandStackEventListener(stackListener);
			}
			observedStack = stack;
			if (observedStack != null) {
				observedStack.addCommandStackEventListener(stackListener);
			}
		}
		final boolean canUndo = stack != null && stack.canUndo();
		final boolean canRedo = stack != null && stack.canRedo();
		undoAction.setEnabled(canUndo);
		undoAction.setText(actionLabel("Undo", canUndo ? stack.getUndoCommand() : null)); //$NON-NLS-1$
		redoAction.setEnabled(canRedo);
		redoAction.setText(actionLabel("Redo", canRedo ? stack.getRedoCommand() : null)); //$NON-NLS-1$
		getViewSite().getActionBars().updateActionBars();
	}

	private static String actionLabel(final String base, final Command command) {
		return command == null || command.getLabel() == null ? base : base + " " + command.getLabel(); //$NON-NLS-1$
	}

	/** The selected row, or {@code null} if no row is selected. */
	private CommentRow selectedRow() {
		return CommentRow.of(viewer.getStructuredSelection());
	}

	/** Adds a column whose cell text is computed from the row by {@code text}. */
	private TableViewerColumn createColumn(final TableColumnLayout layout, final String title,
			final ColumnLayoutData data, final Function<CommentRow, String> text) {
		final TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setText(title);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return text.apply((CommentRow) element);
			}
		});
		layout.setColumnData(column.getColumn(), data);
		return column;
	}

	/** Starts editing the comment text of the selected row, if any. */
	private void editSelected() {
		final CommentRow row = selectedRow();
		if (row != null) {
			viewer.editElement(row, COMMENT_COLUMN);
		}
	}

	/**
	 * Executes the delete command matching the kind of the selected row, if any.
	 */
	private void deleteSelected() {
		final CommentRow row = selectedRow();
		if (row != null) {
			executeCommand(row.isTimelineComment() ? CommonConstants.REMOVE_TIMELINE_COMMENT_COMMAND_ID
					: CommonConstants.REMOVE_EVENT_COMMENT_COMMAND_ID, row, Map.of());
			viewer.getControl().setFocus();
		}
	}

	/** Executes {@link #COMMAND_ID} for the selected row, if any. */
	private void goToSelected() {
		final CommentRow row = selectedRow();
		if (row != null) {
			executeCommand(CommonConstants.GO_TO_EVENT_COMMAND_ID, row, Map.of());
		}
	}

	/**
	 * Executes a platform command for the given row.
	 *
	 * The row is passed explicitly as the current selection of the execution
	 * context instead of relying on the view's selection: a cell editor can commit
	 * when focus moves to another part, at which point this view's selection is no
	 * longer the active one. The active part id is set to this view so that
	 * handlers declared with {@code activeWhen} for this view still apply.
	 *
	 * @param commandId  the command to execute
	 * @param row        the row the command operates on
	 * @param parameters the command parameters, by parameter id
	 */
	private void executeCommand(final String commandId, final CommentRow row, final Map<String, String> parameters) {
		final ICommandService commandService = getSite().getService(ICommandService.class);
		final IHandlerService handlerService = getSite().getService(IHandlerService.class);
		try {
			final ParameterizedCommand command = ParameterizedCommand
					.generateCommand(commandService.getCommand(commandId), parameters);
			if (command == null) {
				ILog.get().error("Invalid parameters for command " + commandId); //$NON-NLS-1$
				return;
			}
			final IEvaluationContext context = handlerService.createContextSnapshot(false);
			context.addVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME, new StructuredSelection(row));
			context.addVariable(ISources.ACTIVE_PART_ID_NAME, ID);
			handlerService.executeCommandInContext(command, null, context);
		} catch (final ExecutionException | NotDefinedException | NotEnabledException | NotHandledException e) {
			ILog.get().error("Could not execute command " + commandId, e); //$NON-NLS-1$
		} finally {
			// the command may have opened the timeline view, which makes Undo/Redo
			// available
			updateUndoRedo();
		}
	}

	/**
	 * Recomputes the timeline numbers and updates the table. Must be called on the
	 * UI thread.
	 */
	private void refresh() {
		final Set<Timeline> roots = new HashSet<>();
		rows.values().forEach(row -> roots.add(TimelineNumbering.rootOf(row.timeline())));
		for (final Timeline timeline : new HashSet<>(watched)) {
			if (!roots.contains(TimelineNumbering.rootOf(timeline))) {
				unwatchTree(timeline);
			}
		}
		numbers.clear();
		for (final CommentRow row : rows.values()) {
			final Timeline root = TimelineNumbering.rootOf(row.timeline());
			if (!numbers.containsKey(root)) {
				numbers.putAll(TimelineNumbering.numberTree(root));
				watchTree(root);
			}
		}
		viewer.setInput(rows.values().stream().filter(this::isStillValid).toList());
		updateButtons();
	}

	/** Hides comments whose event has been removed from its timeline meanwhile. */
	private boolean isStillValid(final CommentRow row) {
		return row.isTimelineComment() || row.position().eventNumber() <= row.timeline().getMaxEventNumber();
	}

	/**
	 * The displayed number of the timeline, or 0 if it is not (or no longer) known.
	 */
	private int numberOf(final Timeline timeline) {
		return numbers.getOrDefault(timeline, Integer.valueOf(0)).intValue();
	}

	/** Registers the structure listener on the timeline and all its descendants. */
	private void watchTree(final Timeline timeline) {
		if (watched.add(timeline)) {
			timeline.addStructureListener(structureListener);
			timeline.getSpawnedTimelines().forEach(this::watchTree);
		}
	}

	/** Removes the structure listener from the timeline and all its descendants. */
	private void unwatchTree(final Timeline timeline) {
		if (watched.remove(timeline)) {
			timeline.removeStructureListener(structureListener);
			timeline.getSpawnedTimelines().forEach(this::unwatchTree);
		}
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
		// the timeline view may have been opened or closed since the last time
		updateUndoRedo();
	}

	@Override
	public void dispose() {
		CommentsHandler.getInstance().removeListener(commentListener);
		if (observedStack != null) {
			observedStack.removeCommandStackEventListener(stackListener);
			observedStack = null;
		}
		watched.forEach(t -> t.removeStructureListener(structureListener));
		watched.clear();
		super.dispose();
	}

	/**
	 * Makes the comment text editable; the event and timeline stay read-only. A
	 * committed text is not written anywhere directly but executed as
	 * {@link CommentsView#EDIT_COMMAND_ID}.
	 */
	private final class CommentEditingSupport extends EditingSupport {

		private final TextCellEditor editor;

		CommentEditingSupport(final TableViewer viewer) {
			super(viewer);
			editor = new TextCellEditor(viewer.getTable());
		}

		@Override
		protected CellEditor getCellEditor(final Object element) {
			return editor;
		}

		@Override
		protected boolean canEdit(final Object element) {
			return true;
		}

		@Override
		protected Object getValue(final Object element) {
			return ((CommentRow) element).comment();
		}

		@Override
		protected void setValue(final Object element, final Object value) {
			final CommentRow row = (CommentRow) element;
			final String text = ((String) value).trim();
			if (text.isEmpty() || text.equals(row.comment())) {
				return;
			}
			executeCommand(
					row.isTimelineComment() ? CommonConstants.EDIT_TIMELINE_COMMENT_COMMAND_ID
							: CommonConstants.EDIT_EVENT_COMMENT_COMMAND_ID,
					row, Map.of(CommonConstants.COMMENT_TEXT_PARAMETER_ID, text));
		}
	}

	/**
	 * Derives the display number of a timeline: the root is 1, then pre-order in
	 * the order the timeline view shows the spawned timelines.
	 */
	private static final class TimelineNumbering {

		private TimelineNumbering() {
		}

		/** The root of the tree the timeline belongs to. */
		static Timeline rootOf(final Timeline timeline) {
			Timeline current = timeline;
			while (current.getParentTimeline() != null) {
				current = current.getParentTimeline();
			}
			return current;
		}

		/** Numbers every timeline of the tree below (and including) root. */
		static Map<Timeline, Integer> numberTree(final Timeline root) {
			final Map<Timeline, Integer> result = new HashMap<>();
			number(root, result);
			return result;
		}

		private static void number(final Timeline timeline, final Map<Timeline, Integer> result) {
			result.put(timeline, Integer.valueOf(result.size() + 1));
			final List<Timeline> children = new ArrayList<>(timeline.getSpawnedTimelines());
			// same order as TimelineModel#getSpawnedTimelineModels
			children.sort(TimelineModel.<Timeline>getTimelineComparator(timeline, Function.identity()));
			for (final Timeline child : children) {
				number(child, result);
			}
		}
	}
}
