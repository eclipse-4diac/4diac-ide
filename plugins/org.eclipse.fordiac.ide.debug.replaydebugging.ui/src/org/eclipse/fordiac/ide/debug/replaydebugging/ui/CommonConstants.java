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

public class CommonConstants {
	private CommonConstants() {
		// this class should not be instantiated
	}

	public static final String NAVIGATION_POLICY = "Navigation"; //$NON-NLS-1$
	public static final String COMPARISON_POLICY = "Comparison"; //$NON-NLS-1$

	public static final String DELETE_TIMELINE_REQUEST = "DeleteTimeline"; //$NON-NLS-1$

	public static final String ADD_TO_COMPARISON_REQUEST = "AddToComparison"; //$NON-NLS-1$
	public static final String REMOVE_FROM_COMPARISON_REQUEST = "RemoveFromComparison"; //$NON-NLS-1$
	public static final String TOGGLE_COMPARISON_REQUEST = "ToggleComparison"; //$NON-NLS-1$

	public static final String SELECTED_EVENT_POSITION = "SelectedEventPosition"; //$NON-NLS-1$
	public static final String EVENT_ALREADY_COMPARED = "EventAlreadyCompared"; //$NON-NLS-1$

	public static final String ADD_EDIT_EVENT_COMMENT_REQUEST = "AddEditEventComment"; //$NON-NLS-1$
	public static final String ADD_EDIT_EVENT_COMMENT_DATA = "AddEditEventCommentData"; //$NON-NLS-1$
	public static final String REMOVE_EVENT_COMMENT_REQUEST = "RemoveEventComment"; //$NON-NLS-1$

	/** The id of the command that goes to the comment's timeline/event. */
	public static final String GO_TO_EVENT_COMMAND_ID = "org.eclipse.fordiac.ide.debug.replaydebugging.ui.commands.goToEvent"; //$NON-NLS-1$

	public static final String REMOVE_EVENT_COMMENT_COMMAND_ID = "org.eclipse.fordiac.ide.debug.replaydebugging.ui.removeEventComment"; //$NON-NLS-1$
	public static final String REMOVE_TIMELINE_COMMENT_COMMAND_ID = "org.eclipse.fordiac.ide.debug.replaydebugging.ui.removeTimelineComment"; //$NON-NLS-1$

	public static final String EDIT_EVENT_COMMENT_COMMAND_ID = "org.eclipse.fordiac.ide.debug.replaydebugging.ui.addEventComment"; //$NON-NLS-1$
	public static final String EDIT_TIMELINE_COMMENT_COMMAND_ID = "org.eclipse.fordiac.ide.debug.replaydebugging.ui.addTimelineComment"; //$NON-NLS-1$
	public static final String COMMENT_TEXT_PARAMETER_ID = "org.eclipse.fordiac.ide.debug.replaydebugging.ui.commentText"; //$NON-NLS-1$

}
