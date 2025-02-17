/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.converter;

import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;

public class STEnumValueConverter extends AbstractLexerBasedConverter<String> {

	public static final char ENUM_NAME_DELIMITER = '#';

	@Override
	public String toValue(final String string, final INode node) throws ValueConverterException {
		final int lastIndex = string.lastIndexOf(ENUM_NAME_DELIMITER);
		if (lastIndex != -1) {
			return string.substring(0, lastIndex) + PackageNameHelper.PACKAGE_NAME_DELIMITER
					+ string.substring(lastIndex + 1);
		}
		return string;
	}

	@Override
	protected String toEscapedString(final String value) {
		final int lastIndex = value.lastIndexOf(PackageNameHelper.PACKAGE_NAME_DELIMITER);
		if (lastIndex != -1) {
			return value.substring(0, lastIndex) + ENUM_NAME_DELIMITER
					+ value.substring(lastIndex + PackageNameHelper.PACKAGE_NAME_DELIMITER.length());
		}
		return value;
	}
}
