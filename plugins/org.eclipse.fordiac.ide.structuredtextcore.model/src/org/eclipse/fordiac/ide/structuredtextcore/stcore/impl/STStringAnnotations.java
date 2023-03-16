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
package org.eclipse.fordiac.ide.structuredtextcore.stcore.impl;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STString;

final class STStringAnnotations {

	private static final byte UTF_16_BOM_LOW = (byte) 0xFF;
	private static final byte UTF_16_BOM_HIGH = (byte) 0xFE;

	private static final byte[] UTF_16_BOM = new byte[] { UTF_16_BOM_HIGH, UTF_16_BOM_LOW };
	private static final String UTF_16_BOM_BASE64 = Base64.getEncoder().encodeToString(UTF_16_BOM);

	static STString createSTString(final String it) {
		final byte[] data = Base64.getDecoder().decode(it);
		final boolean wide = isWide(data);
		final Charset charset = wide ? StandardCharsets.UTF_16 : StandardCharsets.UTF_8;
		final String value = new String(data, charset);
		return new STString(value, wide);
	}

	private static boolean isWide(final byte[] data) {
		return data.length >= 2 // must be at least 2 bytes long for BOM
				&& ((data[0] == UTF_16_BOM_HIGH && data[1] == UTF_16_BOM_LOW) // detect UTF-16 BOM BE
						|| (data[0] == UTF_16_BOM_LOW && data[1] == UTF_16_BOM_HIGH)); // detect UTF-16 BOM LE
	}

	static String convertSTString(final STString it) {
		final Charset charset = it.isWide() ? StandardCharsets.UTF_16 : StandardCharsets.UTF_8;
		final byte[] data = it.toString().getBytes(charset);
		if (data.length == 0 && it.isWide()) { // ensure UTF-16 BOM for empty wide strings
			return UTF_16_BOM_BASE64;
		}
		return Base64.getEncoder().encodeToString(data);
	}

	private STStringAnnotations() {
		throw new UnsupportedOperationException();
	}
}
