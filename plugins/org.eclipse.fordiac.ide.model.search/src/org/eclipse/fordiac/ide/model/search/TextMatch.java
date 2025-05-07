/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.search;

import java.text.MessageFormat;

import org.eclipse.emf.common.util.URI;

/**
 * A text match
 */
public class TextMatch extends Match {

	private final int line;
	private final int offset;
	private final int length;

	/**
	 * Create a new text match
	 *
	 * @param uri    The uri of the match (must not be null)
	 * @param line   The line of the match (may be -1 if unknown)
	 * @param offset The offset of the match (may be -1 if unknown)
	 * @param length The length of the match (may be -1 if unknown)
	 */
	public TextMatch(final URI uri, final int line, final int offset, final int length) {
		super(uri, MessageFormat.format(Messages.TextMatch_Location, Integer.valueOf(line), Integer.valueOf(offset),
				Integer.valueOf(length)));
		this.line = line;
		this.offset = offset;
		this.length = length;
	}

	/**
	 * Get the line of the match
	 *
	 * @return The line (may be -1 if unknown)
	 */
	public int getLine() {
		return line;
	}

	/**
	 * Get the offset of the match
	 *
	 * @return The offset (may be -1 if unknown)
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Get the length of the match
	 *
	 * @return The length (may be -1 if unknown)
	 */
	public int getLength() {
		return length;
	}

	@Override
	public int hashCode() {
		return super.hashCode() + offset + length + line;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TextMatch other = (TextMatch) obj;
		return length == other.length && line == other.line && offset == other.offset;
	}

	@Override
	public String toString() {
		return String.format("%s [uri=%s, location=%s, line=%s, offset=%s, length=%s]", getClass().getName(), getUri(), //$NON-NLS-1$
				getLocation(), Integer.valueOf(line), Integer.valueOf(offset), Integer.valueOf(length));
	}

}
