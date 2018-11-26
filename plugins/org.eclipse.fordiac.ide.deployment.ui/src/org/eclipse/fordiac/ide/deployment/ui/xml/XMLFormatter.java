/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GbmH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.deployment.Activator;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

/**
 * The Class XMLFormatter.
 */
public class XMLFormatter {

	private static class CommentReader extends TagReader {

		private boolean complete = false;

		@Override
		protected void clear() {
			this.complete = false;
		}

		@Override
		public String getStartOfTag() {
			return "<!--"; //$NON-NLS-1$
		}

		@Override
		protected String readTag() throws IOException {
			int intChar;
			char c;
			StringBuilder node = new StringBuilder();

			while (!complete && (intChar = reader.read()) != -1) {
				c = (char) intChar;

				node.append(c);

				if (c == '>' && node.toString().endsWith("-->")) { //$NON-NLS-1$
					complete = true;
				}
			}
			return node.toString();
		}
	}

	private static class DoctypeDeclarationReader extends TagReader {

		private boolean complete = false;

		@Override
		protected void clear() {
			this.complete = false;
		}

		@Override
		public String getStartOfTag() {
			return "<!"; //$NON-NLS-1$
		}

		@Override
		protected String readTag() throws IOException {
			int intChar;
			char c;
			StringBuilder node = new StringBuilder();

			while (!complete && (intChar = reader.read()) != -1) {
				c = (char) intChar;

				node.append(c);

				if (c == '>') {
					complete = true;
				}
			}
			return node.toString();
		}

	}

	private static class ProcessingInstructionReader extends TagReader {

		private boolean complete = false;

		@Override
		protected void clear() {
			this.complete = false;
		}

		@Override
		public String getStartOfTag() {
			return "<?"; //$NON-NLS-1$
		}

		@Override
		protected String readTag() throws IOException {
			int intChar;
			char c;
			StringBuilder node = new StringBuilder();

			while (!complete && (intChar = reader.read()) != -1) {
				c = (char) intChar;

				node.append(c);

				if (c == '>' && node.toString().endsWith("?>")) { //$NON-NLS-1$
					complete = true;
				}
			}
			return node.toString();
		}
	}

	private abstract static class TagReader {

		protected Reader reader;

		private String tagText;

		protected abstract void clear();

		public int getPostTagDepthModifier() {
			return 0;
		}

		public int getPreTagDepthModifier() {
			return 0;
		}

		public abstract String getStartOfTag();

		public String getTagText() {
			return this.tagText;
		}

		public boolean isTextNode() {
			return false;
		}

		protected abstract String readTag() throws IOException;

		public boolean requiresInitialIndent() {
			return true;
		}

		public void setReader(final Reader reader) throws IOException {
			this.reader = reader;
			this.clear();
			this.tagText = readTag();
		}

		public boolean startsOnNewline() {
			return true;
		}
	}

	private static class TagReaderFactory {

		// Warning: the order of the Array is important!
		private static TagReader[] tagReaders = new TagReader[] {
				new CommentReader(), new DoctypeDeclarationReader(),
				new ProcessingInstructionReader(), new XmlElementReader() };

		private static TagReader textNodeReader = new TextReader();

		public static TagReader createTagReaderFor(final Reader reader)
				throws IOException {

			char[] buf = new char[10];
			reader.mark(10);
			reader.read(buf, 0, 10);
			reader.reset();

			String startOfTag = String.valueOf(buf);

			for (int i = 0; i < tagReaders.length; i++) {
				if (startOfTag.startsWith(tagReaders[i].getStartOfTag())) {
					tagReaders[i].setReader(reader);
					return tagReaders[i];
				}
			}
			// else
			textNodeReader.setReader(reader);
			return textNodeReader;
		}
	}

	private static class TextReader extends TagReader {

		private boolean complete;

		private boolean isTextNode;

		@Override
		protected void clear() {
			this.complete = false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ant.internal.ui.editor.formatter.XmlDocumentFormatter.TagReader#getStartOfTag()
		 */
		@Override
		public String getStartOfTag() {
			return ""; //$NON-NLS-1$
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ant.internal.ui.editor.formatter.XmlDocumentFormatter.TagReader#isTextNode()
		 */
		@Override
		public boolean isTextNode() {
			return this.isTextNode;
		}

		@Override
		protected String readTag() throws IOException {

			StringBuilder node = new StringBuilder();

			while (!complete) {

				reader.mark(1);
				int intChar = reader.read();
				if (intChar == -1) {
					break;
				}

				char c = (char) intChar;
				if (c == '<') {
					reader.reset();
					complete = true;
				} else {
					node.append(c);
				}
			}

			// if this text node is just whitespace
			// remove it, except for the newlines.
			if (node.length() < 1) {
				this.isTextNode = false;

			} else if (node.toString().trim().length() == 0) {
				String whitespace = node.toString();
				node = new StringBuilder();
				for (int i = 0; i < whitespace.length(); i++) {
					char whitespaceCharacter = whitespace.charAt(i);
					if (whitespaceCharacter == '\n'
							|| whitespaceCharacter == '\r') {
						node.append(whitespaceCharacter);
					}
				}
				this.isTextNode = false;

			} else {
				this.isTextNode = true;
			}
			return node.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ant.internal.ui.editor.formatter.XmlDocumentFormatter.TagReader#requiresInitialIndent()
		 */
		@Override
		public boolean requiresInitialIndent() {
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ant.internal.ui.editor.formatter.XmlDocumentFormatter.TagReader#startsOnNewline()
		 */
		@Override
		public boolean startsOnNewline() {
			return false;
		}
	}

	private static class XmlElementReader extends TagReader {

		private boolean complete = false;

		@Override
		protected void clear() {
			this.complete = false;
		}

		@Override
		public int getPostTagDepthModifier() {
			if (getTagText().endsWith("/>") || getTagText().endsWith("/ >")) { //$NON-NLS-1$ //$NON-NLS-2$
				return 0;
			} else if (getTagText().startsWith("</")) { //$NON-NLS-1$
				return 0;
			} else {
				return +1;
			}
		}

		@Override
		public int getPreTagDepthModifier() {
			if (getTagText().startsWith("</")) { //$NON-NLS-1$
				return -1;
			}
			return 0;
		}

		@Override
		public String getStartOfTag() {
			return "<"; //$NON-NLS-1$
		}

		@Override
		protected String readTag() throws IOException {

			StringBuilder node = new StringBuilder();

			boolean insideQuote = false;
			int intChar;

			while (!complete && (intChar = reader.read()) != -1) {
				char c = (char) intChar;

				node.append(c);
				// TODO logic incorrectly assumes that " is quote character
				// when it could also be '
				if (c == '"') {
					insideQuote = !insideQuote;
				}
				if (c == '>' && !insideQuote) {
					complete = true;
				}
			}
			return node.toString();
		}
	}

	private int depth;

	private StringBuilder formattedXml;

	private boolean lastNodeWasText;

	private String fDefaultLineDelimiter;

	/**
	 * Instantiates a new xML formatter.
	 */
	public XMLFormatter() {
		super();
		depth = -1;
	}

	private void copyNode(final Reader reader, final StringBuilder out)
			throws IOException {

		TagReader tag = TagReaderFactory.createTagReaderFor(reader);

		depth = depth + tag.getPreTagDepthModifier();

		if (!lastNodeWasText) {

			if (tag.startsOnNewline() && !hasNewlineAlready(out)) {
				out.append(fDefaultLineDelimiter);
			}

			if (tag.requiresInitialIndent()) {
				out.append(indent("\t")); //$NON-NLS-1$
			}
		}

		out.append(tag.getTagText());

		depth = depth + tag.getPostTagDepthModifier();

		lastNodeWasText = tag.isTextNode();

	}

	/**
	 * Returns the indent of the given string.
	 * 
	 * @param line the text line
	 * @param tabWidth the width of the '\t' character.
	 * 
	 * @return the int
	 */
	public static int computeIndent(final String line, final int tabWidth) {
		int result = 0;
		int blanks = 0;
		int size = line.length();
		for (int i = 0; i < size; i++) {
			char c = line.charAt(i);
			if (c == '\t') {
				result++;
				blanks = 0;
			} else if (isIndentChar(c)) {
				blanks++;
				if (blanks == tabWidth) {
					result++;
					blanks = 0;
				}
			} else {
				return result;
			}
		}
		return result;
	}

	/**
	 * Indent char is a space char but not a line delimiters.
	 * <code>== Character.isWhitespace(ch) && ch != '\n' && ch != '\r'</code>
	 * 
	 * @param ch the ch
	 * 
	 * @return true, if checks if is indent char
	 */
	public static boolean isIndentChar(final char ch) {
		return Character.isWhitespace(ch) && !isLineDelimiterChar(ch);
	}

	/**
	 * Line delimiter chars are '\n' and '\r'.
	 * 
	 * @param ch the ch
	 * 
	 * @return true, if checks if is line delimiter char
	 */
	public static boolean isLineDelimiterChar(final char ch) {
		return ch == '\n' || ch == '\r';
	}

	/**
	 * Format.
	 * 
	 * @param documentText the document text
	 * 
	 * @return the string
	 */
	public String format(final String documentText) {

		Assert.isNotNull(documentText);

		Reader reader = new StringReader(documentText);
		formattedXml = new StringBuilder();

		if (depth == -1) {
			depth = 0;
		}
		lastNodeWasText = false;
		try {
			while (true) {
				reader.mark(1);
				int intChar = reader.read();
				reader.reset();

				if (intChar != -1) {
					copyNode(reader, formattedXml);
				} else {
					break;
				}
			}
			reader.close();
		} catch (IOException e) {
			Activator.getDefault().logWarning("Eception during xml formating", e);
		}
		return formattedXml.toString();
	}

	private boolean hasNewlineAlready(final StringBuilder out) {
		return out.lastIndexOf("\n") == formattedXml.length() - 1 //$NON-NLS-1$
				|| out.lastIndexOf("\r") == formattedXml.length() - 1; //$NON-NLS-1$
	}

	private String indent(final String canonicalIndent) {
		StringBuilder indent = new StringBuilder(30);
		for (int i = 0; i < depth; i++) {
			indent.append(canonicalIndent);
		}
		return indent.toString();
	}

	/**
	 * Sets the initial indent.
	 * 
	 * @param indent the new initial indent
	 */
	public void setInitialIndent(final int indent) {
		depth = indent;
	}

	/**
	 * Returns the indentation of the line at <code>offset</code> as a
	 * <code>StringBuilder</code>. If the offset is not valid, the empty
	 * string is returned.
	 * 
	 * @param offset the offset in the document
	 * @param document the document
	 * 
	 * @return the indentation (leading whitespace) of the line in which
	 * <code>offset</code> is located
	 */
	public static StringBuilder getLeadingWhitespace(final int offset,
			final IDocument document) {
		StringBuilder indent = new StringBuilder();
		try {
			IRegion line = document.getLineInformationOfOffset(offset);
			int lineOffset = line.getOffset();
			int nonWS = findEndOfWhiteSpace(document, lineOffset, lineOffset
					+ line.getLength());
			indent.append(document.get(lineOffset, nonWS - lineOffset));
			return indent;
		} catch (BadLocationException e) {
			return indent;
		}
	}

	/**
	 * Returns the first offset greater than <code>offset</code> and smaller
	 * than <code>end</code> whose character is not a space or tab character.
	 * If no such offset is found, <code>end</code> is returned.
	 * 
	 * @param document the document to search in
	 * @param offset the offset at which searching start
	 * @param end the offset at which searching stops
	 * 
	 * @return the offset in the specifed range whose character is not a space
	 * or tab
	 * 
	 * @throws BadLocationException the bad location exception
	 * 
	 * @exception BadLocationException
	 * if position is an invalid range in the given document
	 */
	public static int findEndOfWhiteSpace(final IDocument document, int offset,
			final int end) throws BadLocationException {
		while (offset < end) {
			char c = document.getChar(offset);
			if (c != ' ' && c != '\t') {
				return offset;
			}
			offset++;
		}
		return end;
	}

	/**
	 * Creates a string that represents one indent (can be spaces or tabs..)
	 * 
	 * @return one indentation
	 */
	public static StringBuilder createIndent() {
		StringBuilder oneIndent = new StringBuilder();
		oneIndent.append('\t'); // default

		return oneIndent;
	}

	/**
	 * Sets the default line delimiter.
	 * 
	 * @param defaultLineDelimiter the new default line delimiter
	 */
	public void setDefaultLineDelimiter(final String defaultLineDelimiter) {
		fDefaultLineDelimiter = defaultLineDelimiter;

	}
}