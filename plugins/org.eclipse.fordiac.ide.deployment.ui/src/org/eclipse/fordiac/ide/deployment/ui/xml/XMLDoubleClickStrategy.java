/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GbmH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.xml;

import org.eclipse.fordiac.ide.deployment.ui.Activator;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextViewer;

/**
 * The Class XMLDoubleClickStrategy.
 */
public class XMLDoubleClickStrategy implements ITextDoubleClickStrategy {
	private ITextViewer fText;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.text.ITextDoubleClickStrategy#doubleClicked(org.eclipse.
	 * jface.text.ITextViewer)
	 */
	@Override
	public void doubleClicked(final ITextViewer part) {
		final int pos = part.getSelectedRange().x;

		if (pos < 0) {
			return;
		}

		fText = part;

		if (!selectComment(pos)) {
			selectWord(pos);
		}
	}

	protected boolean selectComment(final int caretPos) {
		final IDocument doc = fText.getDocument();
		int startPos;
		int endPos;

		try {
			int pos = caretPos;
			char c = ' ';

			while (pos >= 0) {
				c = doc.getChar(pos);
				if (c == '\\') {
					pos -= 2;
				} else if (c == Character.LINE_SEPARATOR || c == '\"') {
					break;
				} else {
					--pos;
				}
			}

			if (c != '\"') {
				return false;
			}

			startPos = pos;

			pos = caretPos;
			final int length = doc.getLength();
			c = ' ';

			while (pos < length) {
				c = doc.getChar(pos);
				if (c == Character.LINE_SEPARATOR || c == '\"') {
					break;
				}
				++pos;
			}
			if (c != '\"') {
				return false;
			}

			endPos = pos;

			final int offset = startPos + 1;
			final int len = endPos - offset;
			fText.setSelectedRange(offset, len);
			return true;
		} catch (final BadLocationException x) {
			Activator.getDefault().logError(x.getMessage(), x);
		}

		return false;
	}

	protected boolean selectWord(final int caretPos) {

		final IDocument doc = fText.getDocument();
		int startPos;
		int endPos;

		try {

			int pos = caretPos;
			char c;

			while (pos >= 0) {
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c)) {
					break;
				}
				--pos;
			}

			startPos = pos;

			pos = caretPos;
			final int length = doc.getLength();

			while (pos < length) {
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c)) {
					break;
				}
				++pos;
			}

			endPos = pos;
			selectRange(startPos, endPos);
			return true;

		} catch (final BadLocationException x) {
			Activator.getDefault().logError(x.getMessage(), x);
		}

		return false;
	}

	private void selectRange(final int startPos, final int stopPos) {
		final int offset = startPos + 1;
		final int length = stopPos - offset;
		fText.setSelectedRange(offset, length);
	}
}