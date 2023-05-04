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
package org.eclipse.fordiac.ide.structuredtextcore.stcore.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.Subrange;

public final class STTypeDeclarationParser {

	private static final Pattern SIMPLE_SUBRANGE_PATTERN = Pattern
			.compile("([\\+\\-]?\\d+)\\s*\\.\\.\\s*([\\+\\-]?\\d+)"); //$NON-NLS-1$

	public static ArrayType parseLegacyTypeDeclaration(final DataType baseType, final String arraySize) {
		try {
			final int length = Integer.parseInt(arraySize.trim());
			if (length <= 0) {
				return null;
			}
			return STCoreUtil.newArrayType(baseType, STCoreUtil.newSubrange(0, length - 1));
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	public static ArrayType parseSimpleTypeDeclaration(final DataType baseType, final String arraySize) {
		final List<String> subrangeStrings = splitString(arraySize);
		if (subrangeStrings.isEmpty()) {
			return null; // not a proper array type declaration
		}
		final List<Subrange> subranges = subrangeStrings.stream().map(STTypeDeclarationParser::parseSimpleSubrange)
				.toList();
		return STCoreUtil.newArrayType(baseType, subranges);
	}

	private static Subrange parseSimpleSubrange(final String text) {
		final Matcher matcher = SIMPLE_SUBRANGE_PATTERN.matcher(text);
		if (matcher.matches()) {
			final String lowerBoundString = matcher.group(1);
			final String upperBoundString = matcher.group(2);
			try {
				return STCoreUtil.newSubrange(Integer.parseInt(lowerBoundString), Integer.parseInt(upperBoundString));
			} catch (final NumberFormatException e) {
				// fallthrough
			}
		}
		return DataFactory.eINSTANCE.createSubrange();
	}

	private static List<String> splitString(final String text) {
		final List<String> result = new ArrayList<>();
		int depth = 0;
		boolean string = false;
		int lastSplitIndex = -1;
		for (int i = 0, end = text.length(); i < end; ++i) {
			switch (text.charAt(i)) {
			case '"':
				string = !string;
				break;
			case '$':
				if (string) { // string escape (escapes *at least* one character, don't care about more)
					i++; // NOSONAR
				}
				break;
			case '(', '[', '{':
				if (!string) {
					depth++;
				}
			break;
			case ')', ']', '}':
				if (!string) {
					if (depth == 0) { // unbalanced
						return Collections.emptyList();
					}
					depth--;
				}
			break;
			case ',':
				if (!string && depth == 0) {
					result.add(text.substring(lastSplitIndex + 1, i).trim());
					lastSplitIndex = i;
				}
				break;
			default:
				break;
			}
		}
		if (depth != 0) { // unbalanced
			return Collections.emptyList();
		}
		result.add(text.substring(lastSplitIndex + 1).trim());
		return result;
	}

	private STTypeDeclarationParser() {
		throw new UnsupportedOperationException();
	}
}
