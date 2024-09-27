/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.stcore;

import java.util.Objects;

public final class STString implements java.io.Serializable, Comparable<STString>, CharSequence {
	private static final long serialVersionUID = 5942393260733637000L;

	private final String value;
	private final boolean wide;

	public STString(final String value, final boolean wide) {
		this.value = value;
		this.wide = wide;
	}

	@Override
	public int length() {
		return value.length();
	}

	@Override
	public char charAt(final int index) {
		return value.charAt(index);
	}

	@Override
	public CharSequence subSequence(final int start, final int end) {
		return value.subSequence(start, end);
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value, Boolean.valueOf(wide));
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final STString other = (STString) obj;
		return Objects.equals(value, other.value) && wide == other.wide;
	}

	@Override
	public int compareTo(final STString o) {
		return value.compareTo(o.value);
	}

	public boolean isWide() {
		return wide;
	}
}
