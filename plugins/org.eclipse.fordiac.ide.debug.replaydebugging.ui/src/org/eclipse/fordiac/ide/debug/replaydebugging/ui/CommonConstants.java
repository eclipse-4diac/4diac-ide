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

	public static final String ADD_EDIT_EVENT_COMMENT_REQUEST = "AddEditEventComment"; //$NON-NLS-1$
	public static final String ADD_EDIT_EVENT_COMMENT_DATA = "AddEditEventCommentData"; //$NON-NLS-1$
	public static final String REMOVE_EVENT_COMMENT_REQUEST = "RemoveEventComment"; //$NON-NLS-1$

}
