/*******************************************************************************
 * Copyright (c) 2025, 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.ui.editors;

import org.eclipse.ui.IEditorInput;

/**
 * A listener to notify UI components about changes in library elements.
 */
public interface LibraryElementStateListener {
	/**
	 * Notifies that a new input was connected.
	 *
	 * @param input the input
	 */
	default void elementConnected(final IEditorInput input) {
		// do nothing
	}

	/**
	 * Notifies that an input was disconnected.
	 *
	 * @param input the input
	 */
	default void elementDisconnected(final IEditorInput input) {
		// do nothing
	}

	/**
	 * Notifies that the dirty state of the given input has changed.
	 *
	 * @param input   the input
	 * @param isDirty the new dirty state
	 */
	default void elementDirtyStateChanged(final IEditorInput input, final boolean isDirty) {
		// do nothing
	}

	/**
	 * Notifies that the content of the library element is about to be replaced.
	 *
	 * @param input the input
	 */
	default void elementContentAboutToBeReplaced(final IEditorInput input) {
		// do nothing
	}

	/**
	 * Notifies that the content of the library element has been replaced.
	 *
	 * @param input the input
	 */
	default void elementContentReplaced(final IEditorInput input) {
		// do nothing
	}

	/**
	 * Notifies that the library element has been deleted.
	 *
	 * @param input the input
	 */
	default void elementDeleted(final IEditorInput input) {
		// do nothing
	}

	/**
	 * Notifies that the library element has moved.
	 *
	 * @param originalInput the input before the move
	 * @param movedInput    the input after the move
	 */
	default void elementMoved(final IEditorInput originalInput, final IEditorInput movedInput) {
		// do nothing
	}
}
