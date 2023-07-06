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
package org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.TextRegion;

public class STCoreProposalString implements CharSequence {

	private final StringBuilder buffer;
	private final List<ITextRegion> regions = new ArrayList<>();

	public STCoreProposalString() {
		buffer = new StringBuilder();
	}

	public STCoreProposalString(final String str) {
		buffer = new StringBuilder(str);
	}

	public STCoreProposalString(final CharSequence str) {
		buffer = new StringBuilder(str);
	}

	public STCoreProposalString(final String str, final boolean region) {
		buffer = new StringBuilder(str);
		if (region) {
			regions.add(new TextRegion(0, str.length()));
		}
	}

	public STCoreProposalString(final CharSequence str, final boolean region) {
		buffer = new StringBuilder(str);
		if (region) {
			regions.add(new TextRegion(0, str.length()));
		}
	}

	public STCoreProposalString append(final char c) {
		buffer.append(c);
		return this;
	}

	public STCoreProposalString append(final CharSequence s) {
		buffer.append(s);
		return this;
	}

	public STCoreProposalString append(final char c, final boolean region) {
		if (region) {
			regions.add(new TextRegion(length(), 1));
		}
		buffer.append(c);
		return this;
	}

	public STCoreProposalString append(final CharSequence s, final boolean region) {
		if (region) {
			regions.add(new TextRegion(length(), s.length()));
		}
		buffer.append(s);
		return this;
	}

	public STCoreProposalString addRegion(final ITextRegion region) {
		if (region.getOffset() < 0 || region.getOffset() + region.getLength() > length()) {
			throw new IllegalArgumentException();
		}
		regions.add(region);
		return this;
	}

	public STCoreProposalString concat(final STCoreProposalString other) {
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

	protected List<ITextRegion> getRegions() {
		return regions;
	}

	protected List<ITextRegion> getRegions(final int offset) {
		if (offset == 0) {
			return getRegions();
		}
		return regions.stream()
				.<ITextRegion>map(region -> new TextRegion(offset + region.getOffset(), region.getLength())).toList();
	}

	@Override
	public String toString() {
		return buffer.toString();
	}
}
