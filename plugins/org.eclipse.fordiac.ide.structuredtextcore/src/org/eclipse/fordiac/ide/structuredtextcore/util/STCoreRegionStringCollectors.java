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

import java.util.stream.Collector;

public final class STCoreRegionStringCollectors {

	public static Collector<STCoreRegionString, ?, STCoreRegionString> joining() {
		return Collector.of(STCoreRegionString::new, STCoreRegionString::concat, STCoreRegionString::concat);
	}

	public static Collector<STCoreRegionString, ?, STCoreRegionString> joining(final CharSequence delimiter) {
		return Collector.of(() -> new STCoreRegionStringJoiner(delimiter), STCoreRegionStringJoiner::add,
				STCoreRegionStringJoiner::merge, STCoreRegionStringJoiner::toProposalString);
	}

	public static Collector<STCoreRegionString, ?, STCoreRegionString> joining(final CharSequence delimiter,
			final CharSequence prefix, final CharSequence suffix) {
		return Collector.of(() -> new STCoreRegionStringJoiner(delimiter, prefix, suffix),
				STCoreRegionStringJoiner::add, STCoreRegionStringJoiner::merge,
				STCoreRegionStringJoiner::toProposalString);
	}

	private STCoreRegionStringCollectors() {
		throw new UnsupportedOperationException();
	}
}
