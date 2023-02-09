/********************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH
 *               2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - filling in the implementation,
 *                             fixes handling for ANY types,
 *                             fixes handling of Arrays
 *   Alois Zoitl - migrated to value converters
 *   Martin Jobst - migrated to typed value converter
 *   Daniel Lindhuber - prefix validation and ANY type handling
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.validation;

import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.value.ArrayValueConverter;
import org.eclipse.fordiac.ide.model.value.TypedValueConverter;

public final class ValueValidator {

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private static final String REGEX_VIRTUAL_DNS_ENTRY = "(%)(.)*(%)";  //$NON-NLS-1$
	private static final Pattern VIRTUAL_DNS_PATTERN = Pattern.compile(REGEX_VIRTUAL_DNS_ENTRY);

	public static String validateValue(final VarDeclaration varDeclaration) {
		return validateValue(varDeclaration, varDeclaration.getValue().getValue());
	}

	public static String validateValue(final VarDeclaration varDeclaration, final String valueString) {
		if (varDeclaration.getType() instanceof StructuredType) {
			// results in structs getting sent to the forte for validation
			// should be removed once there is a struct validator
			return EMPTY_STRING;
		}
		if (varDeclaration.isArray()) {
			return validateArray(varDeclaration, valueString);
		}
		return validateValue(varDeclaration.getType(), valueString);
	}

	private static String validateArray(final VarDeclaration varDeclaration, final String valueString) {
		try {
			new ArrayValueConverter<>(new TypedValueConverter(varDeclaration.getType())).toValue(valueString);
		} catch (final IllegalArgumentException e) {
			return e.getMessage();
		}
		return EMPTY_STRING;
	}

	/** Check the given value if it is valid literal for the data type
	 *
	 * @param type  data type for which the literal should be checked
	 * @param value the literal value to check
	 * @return empty string if the literal is valid, otherwise an error message what is wrong with the literal */
	public static String validateValue(final DataType type, final String value) {
		try {
			if (!checkVirtualDNS(value)) {
				new TypedValueConverter(type).toValue(value);
			}
		} catch (final IllegalArgumentException e) {
			return e.getMessage();
		}
		return EMPTY_STRING;
	}

	private static boolean checkVirtualDNS(final String value) throws IllegalArgumentException {
		final var virtualDNSmatcher = VIRTUAL_DNS_PATTERN.matcher(value);
		// check if literal is a virtual DNS entry
		if (virtualDNSmatcher.find()) {
			final boolean outbound = !(value.charAt(0) == '%' && value.charAt(value.length() - 1) == '%');
			boolean inbound = false;

			int counter = 0;
			// true if % is followed by %
			boolean followUp = false;

			for (int i = 0; i < value.length(); ++i) {
				if (value.charAt(i) == '%') {
					counter++;
					if (i < value.length() - 1 && counter % 2 == 0) {
						followUp = value.charAt(i + 1) == '%';
					}
				}
			}

			// true if literal is a single virtual DNS entry

			inbound = counter % 2 == 1;

			if (outbound && inbound) {
				throw new IllegalArgumentException(Messages.VALIDATOR_VIRTUAL_DNS_ILLEGAL_FORMAT);
			}

			if (inbound) {
				throw new IllegalArgumentException(Messages.VALIDATOR_VIRTUAL_DNS_MULTIPLE_BOUNDING_CHARACTERS);
			}

			final boolean single = (counter == 2);
			if (outbound || (!followUp && !single)) {
				throw new IllegalArgumentException(Messages.VALIDATOR_VIRTUAL_DNS_CHARACTERS_OUT_OF_BOUNDS);
			}
			return true;
		}
		return false;
	}

	private ValueValidator() {
		throw new UnsupportedOperationException("helper class ValueValidator should not be instantiated"); //$NON-NLS-1$
	}
}
