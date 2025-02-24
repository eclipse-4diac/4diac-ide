/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.fordiac.ide.gef.figures.BorderedRoundedRectangle;
import org.eclipse.fordiac.ide.gef.figures.RoundedRectangleShadowBorder;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.swt.graphics.Color;

public class InstanceContractFigure extends Figure {

	private static final Color normal = new Color(0, 0, 0);
	private static final Color highlight = new Color(127, 0, 85);
	private static final Color comment = new Color(63, 127, 95);
	private final FlowPage flowPage;
	private String currentText;

	public InstanceContractFigure(final String initialText) {
		final var main = new BorderedRoundedRectangle();
		main.setOutline(false);
		main.setOpaque(false);
		main.setCornerDimensions(new Dimension(GefPreferenceConstants.CORNER_DIM, GefPreferenceConstants.CORNER_DIM));
		main.setBorder(new RoundedRectangleShadowBorder());
		final GridLayout topLayout = new GridLayout(3, false);
		topLayout.marginHeight = 0;
		topLayout.marginWidth = 0;
		topLayout.verticalSpacing = 0;
		topLayout.horizontalSpacing = 0;
		main.setLayoutManager(topLayout);

		final Figure contractContainer = new Figure();
		contractContainer.setLayoutManager(new ToolbarLayout());
		final GridData gridData2 = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		gridData2.horizontalSpan = 3;
		main.add(contractContainer, gridData2, 0);

		setCursor(Cursors.SIZEALL);

		flowPage = new FlowPage();
		contractContainer.add(flowPage);

		final int lineHeight = (int) CoordinateConverter.INSTANCE.getLineHeight();
		int top = lineHeight / 2;
		final int bottom = top;
		if (top + bottom != lineHeight) {
			// we have a rounding error
			top += lineHeight - (top + bottom);
		}
		contractContainer.setBorder(new MarginBorder(top, 5, bottom, 5));

		setLayoutManager(new StackLayout());
		final GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL | GridData.GRAB_VERTICAL);
		add(main, gridData);
		setText(initialText);
	}

	public void setText(final String text) {
		if (text != null && text.equals(currentText)) {
			return; // no need for expensive refresh
		}
		currentText = text;

		flowPage.removeAll();
		if (text != null) {
			final Scanner scan = new Scanner(text);

			for (final Scanner.Token t : scan) {
				final TextFlow tf = new TextFlow(t.value);

				switch (t.type) {
				case comment:
					tf.setForegroundColor(comment);
					break;
				case keyword:
					tf.setForegroundColor(highlight);
					break;
				default:
					tf.setForegroundColor(normal);
					break;
				}
				flowPage.add(tf);
			}
		} else {
			flowPage.add(new TextFlow("")); //$NON-NLS-1$
		}
	}

	@Override
	protected void layout() {
		final var parentBounds = getParent().getBounds();
		final var bounds = getBounds();
		final int minWidth = Math.min(bounds.width, parentBounds.width);
		bounds.width = parentBounds.width;
		setBounds(bounds);
		final var loc = getLocation();
		loc.x -= (bounds.width - minWidth) / 2;

		setLocation(loc);
		super.layout();
	}

	private static class Scanner implements Iterable<Scanner.Token> {
		static record Token(TokenType type, String value) {
		}

		public enum TokenType {
			normal, keyword, comment
		}

		@SuppressWarnings("nls")
		private static final Set<String> keywords = Set.of("Age", "Clock", "FIFO", "ID", "LIFO", "Reaction", "and",
				"clock", "drift", "every", "has", "jitter", "maxdiff", "ms", "ns", "occured", "occurs", "of", "offset",
				"once", "out", "resolution", "s", "skew", "then", "times", "us", "using", "whenever", "with", "within");

		StringReader reader;
		StringBuilder sb;
		int ch;
		TokenType tt;
		boolean hasNext = true;

		public Scanner(final String string) {
			reader = new StringReader(string);
			sb = new StringBuilder();
			nextChar();
		}

		@Override
		public Iterator<Token> iterator() {
			return new Iterator<>() {

				@Override
				public boolean hasNext() {
					return hasNext;
				}

				@Override
				public Token next() {
					if (ch == '/') {
						sb.append((char) ch);
						nextChar();

						if (ch == '/') {
							readSingleLineComment();
						} else if (ch == '*') {
							readMultiLineComment();
						} else {
							readOther();
						}
					} else if (Character.isLetter((char) ch)) {
						readWord();
					} else {
						readOther();
					}
					readWhitespace();

					final String tv = sb.toString();
					sb.setLength(0);
					return new Token(tt, tv);
				}
			};
		}

		private void nextChar() {
			try {
				ch = reader.read();
				if (ch < 0) {
					hasNext = false;
				}
			} catch (final IOException e) {
				ch = -1;
				hasNext = false;
			}
		}

		private void readWhitespace() {
			while (ch >= 0 && Character.isWhitespace((char) ch)) {
				sb.append((char) ch);
				nextChar();
			}
		}

		private void readWord() {
			while (ch >= 0 && Character.isLetterOrDigit((char) ch)) {
				sb.append((char) ch);
				nextChar();
			}
			final String v = sb.toString();
			tt = keywords.contains(v) ? TokenType.keyword : TokenType.normal;
		}

		private void readOther() {
			while (ch >= 0 && !Character.isLetter((char) ch) && ch != '/') {
				sb.append((char) ch);
				nextChar();
			}
			tt = TokenType.normal;
		}

		private void readSingleLineComment() {
			while (ch >= 0 && ch != '\n') {
				sb.append((char) ch);
				nextChar();
			}
			tt = TokenType.comment;
		}

		private void readMultiLineComment() {
			int prev = ch;

			while (prev >= 0 && ch >= 0 && !(prev == '*' && ch == '/')) {
				sb.append((char) ch);
				prev = ch;
				nextChar();
			}
			sb.append((char) ch);
			nextChar();
			tt = TokenType.comment;
		}
	}
}
