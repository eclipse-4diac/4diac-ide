/*******************************************************************************
 * Copyright (c) 2025 Johannes Kepler Universität Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.swt.graphics.Color;

public class ContractScanner implements Iterable<ContractScanner.Token> {
	public record Token(TokenType type, String value) {
	}

	public static final Color NORMAL = new Color(0, 0, 0);
	public static final Color KEYWORD = new Color(127, 0, 85);
	public static final Color COMMENT = new Color(63, 127, 95);

	public enum TokenType {
		NORMAL, KEYWORD, COMMENT
	}

	@SuppressWarnings("nls")
	private static final Set<String> KEYWORDS = Set.of("Age", "Clock", "FIFO", "ID", "LIFO", "Reaction", "and", "clock",
			"drift", "every", "has", "jitter", "maxdiff", "ms", "ns", "occurred", "occurs", "of", "offset", "once",
			"out", "resolution", "s", "skew", "then", "times", "us", "using", "whenever", "with", "within");

	private final StringReader reader;
	private final StringBuilder sb;
	private int ch;
	private TokenType tt;
	private boolean hasNext = true;

	public ContractScanner(final String string) {
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
		tt = KEYWORDS.contains(v) ? TokenType.KEYWORD : TokenType.NORMAL;
	}

	private void readOther() {
		while (ch >= 0 && !Character.isLetter((char) ch) && ch != '/') {
			sb.append((char) ch);
			nextChar();
		}
		tt = TokenType.NORMAL;
	}

	private void readSingleLineComment() {
		while (ch >= 0 && ch != '\n') {
			sb.append((char) ch);
			nextChar();
		}
		tt = TokenType.COMMENT;
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
		tt = TokenType.COMMENT;
	}
}
