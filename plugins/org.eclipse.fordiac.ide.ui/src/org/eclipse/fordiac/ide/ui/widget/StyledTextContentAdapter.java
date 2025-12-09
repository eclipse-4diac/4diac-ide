/*******************************************************************************
 * Copyright (c) 2005, 2023 IBM Corporation and others.
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Martin Erich Jobst - copied from TextContentAdapter and adapted for StyledText
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IControlContentAdapter;
import org.eclipse.jface.fieldassist.IControlContentAdapter2;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

/**
 * An {@link IControlContentAdapter} for SWT StyledText controls. This is a
 * convenience class for easily creating a {@link ContentProposalAdapter} for
 * text fields.
 */
public class StyledTextContentAdapter implements IControlContentAdapter, IControlContentAdapter2 {

	@Override
	public String getControlContents(final Control control) {
		return ((StyledText) control).getText();
	}

	@Override
	public void setControlContents(final Control control, final String text, final int cursorPosition) {
		((StyledText) control).setText(text);
		((StyledText) control).setSelection(cursorPosition, cursorPosition);
	}

	@Override
	public void insertControlContents(final Control control, final String text, final int cursorPosition) {
		final Point selection = ((StyledText) control).getSelection();
		((StyledText) control).insert(text);
		// Insert will leave the cursor at the end of the inserted text. If this
		// is not what we wanted, reset the selection.
		if (cursorPosition < text.length()) {
			((StyledText) control).setSelection(selection.x + cursorPosition, selection.x + cursorPosition);
		}
	}

	@Override
	public int getCursorPosition(final Control control) {
		return ((StyledText) control).getCaretOffset();
	}

	@Override
	public Rectangle getInsertionBounds(final Control control) {
		final StyledText text = (StyledText) control;
		final Point caretOrigin = text.getCaret().getLocation();
		// We fudge the y pixels due to problems with getCaretLocation
		// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=52520
		return new Rectangle(caretOrigin.x + text.getClientArea().x, caretOrigin.y + text.getClientArea().y + 3, 1,
				text.getLineHeight());
	}

	@Override
	public void setCursorPosition(final Control control, final int position) {
		((StyledText) control).setSelection(new Point(position, position));
	}

	@Override
	public Point getSelection(final Control control) {
		return ((StyledText) control).getSelection();
	}

	@Override
	public void setSelection(final Control control, final Point range) {
		((StyledText) control).setSelection(range);
	}
}
