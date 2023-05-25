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

import java.util.stream.Collector;

public final class STCoreProposalStringCollectors {

	public static Collector<STCoreProposalString, ?, STCoreProposalString> joining() {
		return Collector.of(STCoreProposalString::new, STCoreProposalString::concat, STCoreProposalString::concat);
	}

	public static Collector<STCoreProposalString, ?, STCoreProposalString> joining(final CharSequence delimiter) {
		return Collector.of(() -> new STCoreProposalStringJoiner(delimiter), STCoreProposalStringJoiner::add,
				STCoreProposalStringJoiner::merge, STCoreProposalStringJoiner::toProposalString);
	}

	public static Collector<STCoreProposalString, ?, STCoreProposalString> joining(final CharSequence delimiter,
			final CharSequence prefix, final CharSequence suffix) {
		return Collector.of(() -> new STCoreProposalStringJoiner(delimiter, prefix, suffix),
				STCoreProposalStringJoiner::add, STCoreProposalStringJoiner::merge,
				STCoreProposalStringJoiner::toProposalString);
	}

	private STCoreProposalStringCollectors() {
		throw new UnsupportedOperationException();
	}
}
