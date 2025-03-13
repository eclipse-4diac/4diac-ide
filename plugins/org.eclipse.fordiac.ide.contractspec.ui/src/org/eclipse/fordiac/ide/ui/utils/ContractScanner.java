package org.eclipse.fordiac.ide.ui.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.swt.graphics.Color;

public class ContractScanner implements Iterable<ContractScanner.Token> {
	public static record Token(TokenType type, String value) {
	}

	public static final Color NORMAL = new Color(0, 0, 0);
	public static final Color HIGHLIGHT = new Color(127, 0, 85);
	public static final Color COMMENT = new Color(63, 127, 95);

	public enum TokenType {
		normal, keyword, comment
	}

	@SuppressWarnings("nls")
	private static final Set<String> keywords = Set.of("Age", "Clock", "FIFO", "ID", "LIFO", "Reaction", "and", "clock",
			"drift", "every", "has", "jitter", "maxdiff", "ms", "ns", "occurred", "occurs", "of", "offset", "once",
			"out", "resolution", "s", "skew", "then", "times", "us", "using", "whenever", "with", "within");

	StringReader reader;
	StringBuilder sb;
	int ch;
	TokenType tt;
	boolean hasNext = true;

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
