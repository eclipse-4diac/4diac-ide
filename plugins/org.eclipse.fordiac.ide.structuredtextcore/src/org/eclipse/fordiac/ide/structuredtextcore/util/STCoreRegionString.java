/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.TextRegion;

public class STCoreRegionString implements CharSequence {

	private static final Pattern REGION_SPLIT = Pattern.compile(",\\s*"); //$NON-NLS-1$
	private static final Pattern REGION_PATTERN = Pattern.compile("\\[([-+]?\\d+):([-+]?\\d+)\\]"); //$NON-NLS-1$

	private final StringBuilder buffer;
	private final List<ITextRegion> regions = new ArrayList<>();

	public STCoreRegionString() {
		buffer = new StringBuilder();
	}

	public STCoreRegionString(final String str) {
		buffer = new StringBuilder(str);
	}

	public STCoreRegionString(final CharSequence str) {
		buffer = new StringBuilder(str);
	}

	public STCoreRegionString(final String str, final boolean region) {
		buffer = new StringBuilder(str);
		if (region) {
			regions.add(new TextRegion(0, str.length()));
		}
	}

	public STCoreRegionString(final CharSequence str, final boolean region) {
		buffer = new StringBuilder(str);
		if (region) {
			regions.add(new TextRegion(0, str.length()));
		}
	}

	public STCoreRegionString append(final char c) {
		buffer.append(c);
		return this;
	}

	public STCoreRegionString append(final CharSequence s) {
		buffer.append(s);
		return this;
	}

	public STCoreRegionString append(final char c, final boolean region) {
		if (region) {
			regions.add(new TextRegion(length(), 1));
		}
		buffer.append(c);
		return this;
	}

	public STCoreRegionString append(final CharSequence s, final boolean region) {
		if (region) {
			regions.add(new TextRegion(length(), s.length()));
		}
		buffer.append(s);
		return this;
	}

	public STCoreRegionString addRegion(final ITextRegion region) {
		if (region.getOffset() < 0 || region.getOffset() + region.getLength() > length()) {
			throw new IllegalArgumentException();
		}
		regions.add(region);
		return this;
	}

	public STCoreRegionString concat(final STCoreRegionString other) {
		regions.addAll(other.getRegions(length()));
		buffer.append(other.buffer);
		return this;
	}

	@Override
	public int length() {
		return buffer.length();
	}

	@Override
	public char charAt(final int index) {
		return buffer.charAt(index);
	}

	@Override
	public CharSequence subSequence(final int start, final int end) {
		return buffer.subSequence(start, end);
	}

	public List<ITextRegion> getRegions() {
		return regions;
	}

	public List<ITextRegion> getRegions(final int offset) {
		if (offset == 0) {
			return getRegions();
		}
		return regions.stream()
				.<ITextRegion>map(region -> new TextRegion(offset + region.getOffset(), region.getLength())).toList();
	}

	public static List<ITextRegion> parseRegions(final String string) {
		return parseRegions(string, 0);
	}

	public static List<ITextRegion> parseRegions(final String string, final int offset) {
		if (string.length() < 2 || string.charAt(0) != '[' || string.charAt(string.length() - 1) != ']') {
			throw new IllegalArgumentException("Invalid regions string: " + string); //$NON-NLS-1$
		}
		return REGION_SPLIT.splitAsStream(string.substring(1, string.length() - 1)).map(s -> parseRegion(s, offset))
				.toList();
	}

	public static ITextRegion parseRegion(final String string) {
		return parseRegion(string, 0);
	}

	public static ITextRegion parseRegion(final String string, final int offset) {
		final Matcher matcher = REGION_PATTERN.matcher(string);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("Invalid region string: " + string); //$NON-NLS-1$
		}
		return new TextRegion(offset + Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}

	@Override
	public String toString() {
		return buffer.toString();
	}
}
