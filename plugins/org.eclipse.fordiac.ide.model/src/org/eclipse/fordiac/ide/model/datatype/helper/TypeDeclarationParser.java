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
package org.eclipse.fordiac.ide.model.datatype.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.Subrange;

public final class TypeDeclarationParser {

	private static final Pattern SIMPLE_SUBRANGE_PATTERN = Pattern
			.compile("([\\+\\-]?+\\d++)\\s*+\\.\\.\\s*+([\\+\\-]?+\\d++)"); //$NON-NLS-1$
	private static final Pattern SIMPLE_ARRAY_SIZE_PATTERN = Pattern
			.compile(SIMPLE_SUBRANGE_PATTERN + "(?:\\s*+,\\s*+" + SIMPLE_SUBRANGE_PATTERN + ")*+"); //$NON-NLS-1$ //$NON-NLS-2$

	public static DataType parseTypeDeclaration(final DataType baseType, final String arraySize) {
		final DataType result = parseLegacyTypeDeclaration(baseType, arraySize);
		if (result != null) {
			return result;
		}
		return parseSimpleTypeDeclaration(baseType, arraySize);
	}

	public static DataType parseLegacyTypeDeclaration(final DataType baseType, final String arraySize) {
		try {
			final int length = Integer.parseInt(arraySize.trim());
			if (length <= 0) {
				return null;
			}
			return newArrayType(baseType, newSubrange(0, length - 1));
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	public static DataType parseSimpleTypeDeclaration(final DataType baseType, final String arraySize) {
		final List<String> subrangeStrings = splitString(arraySize);
		if (subrangeStrings.isEmpty()) {
			return null; // not a proper array type declaration
		}
		final List<Subrange> subranges = subrangeStrings.stream().map(TypeDeclarationParser::parseSimpleSubrange)
				.toList();
		return newArrayType(baseType, subranges);
	}

	public static boolean isSimpleTypeDeclaration(final String arraySize) {
		return arraySize == null || arraySize.isEmpty()
				|| SIMPLE_ARRAY_SIZE_PATTERN.matcher(arraySize.trim()).matches();
	}

	public static boolean isVariableArrayBounds(final String arraySize) {
		return arraySize != null && !arraySize.isBlank()
				&& splitString(arraySize).stream().map(String::strip).anyMatch("*"::equals); //$NON-NLS-1$
	}

	private static Subrange parseSimpleSubrange(final String text) {
		final Matcher matcher = SIMPLE_SUBRANGE_PATTERN.matcher(text);
		if (matcher.matches()) {
			final String lowerBoundString = matcher.group(1);
			final String upperBoundString = matcher.group(2);
			try {
				return newSubrange(Integer.parseInt(lowerBoundString), Integer.parseInt(upperBoundString));
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

	private static ArrayType newArrayType(final DataType arrayBaseType, final Subrange... arraySubranges) {
		return newArrayType(arrayBaseType, Arrays.asList(arraySubranges));
	}

	private static ArrayType newArrayType(final DataType arrayBaseType, final List<Subrange> arraySubranges) {
		if (arrayBaseType == null) {
			return null;
		}
		final ArrayType result = DataFactory.eINSTANCE.createArrayType();
		result.setName("ARRAY [" + arraySubranges.stream().map(TypeDeclarationParser::getSubrangeString) //$NON-NLS-1$
				.collect(Collectors.joining(", ")) + "] OF " + arrayBaseType.getName()); //$NON-NLS-1$ //$NON-NLS-2$
		result.setBaseType(arrayBaseType);
		result.getSubranges().addAll(arraySubranges);
		return result;
	}

	private static Subrange newSubrange(final int lower, final int upper) {
		final Subrange result = DataFactory.eINSTANCE.createSubrange();
		result.setLowerLimit(lower);
		result.setUpperLimit(upper);
		return result;
	}

	private static String getSubrangeString(final Subrange subrange) {
		if (subrange.isSetLowerLimit() && subrange.isSetUpperLimit()) {
			return subrange.getLowerLimit() + ".." + subrange.getUpperLimit(); //$NON-NLS-1$
		}
		return "*"; //$NON-NLS-1$
	}

	private TypeDeclarationParser() {
		throw new UnsupportedOperationException();
	}
}
