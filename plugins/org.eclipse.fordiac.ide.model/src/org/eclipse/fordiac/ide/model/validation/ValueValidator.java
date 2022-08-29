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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.AnyNumType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.ByteType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DintType;
import org.eclipse.fordiac.ide.model.data.DwordType;
import org.eclipse.fordiac.ide.model.data.IntType;
import org.eclipse.fordiac.ide.model.data.LintType;
import org.eclipse.fordiac.ide.model.data.LrealType;
import org.eclipse.fordiac.ide.model.data.LwordType;
import org.eclipse.fordiac.ide.model.data.RealType;
import org.eclipse.fordiac.ide.model.data.SintType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.data.UdintType;
import org.eclipse.fordiac.ide.model.data.UintType;
import org.eclipse.fordiac.ide.model.data.UlintType;
import org.eclipse.fordiac.ide.model.data.UsintType;
import org.eclipse.fordiac.ide.model.data.WordType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
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
			final ArrayValueConverter<?> arrayVC = new ArrayValueConverter<>(
					new TypedValueConverter(varDeclaration.getType()));
			arrayVC.toValue(valueString).forEach(value -> checkValue(varDeclaration.getType(), value));
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
				final Object convertedValue = new TypedValueConverter(type).toValue(value);
				final DataType prefixType = getValidatedPrefixType(type, value);
				if (prefixType != null) {
					checkValue(prefixType, convertedValue);
				} else {
					checkValue(type, convertedValue);
				}
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

	private static DataType getValidatedPrefixType(final DataType type, final String value) {
		final String[] tokens = value.split("#"); //$NON-NLS-1$
		if (tokens.length > 1) {
			final DataType prefixType = ElementaryTypes.getTypeByName(tokens[0]);
			if (prefixType != null && !type.isAssignableFrom(prefixType)) {
				throw new IllegalArgumentException(Messages.VALIDATOR_INVALID_NUMBER_LITERAL);
			}
			return prefixType;
		}
		return null;
	}

	private static void checkValue(final DataType type, final Object value) {
		if ((type instanceof AnyNumType || type instanceof AnyBitType) && !isNumericValueValid(type, value)) {
			throw new IllegalArgumentException(Messages.VALIDATOR_INVALID_NUMBER_LITERAL);
		}
	}

	private static boolean isNumericValueValid(final DataType type, final Object value) {
		if (value instanceof Boolean) {
			return type instanceof BoolType;
		} else if (value instanceof BigDecimal) {
			if (type instanceof RealType) {
				return Float.isFinite(((BigDecimal) value).floatValue());
			} else if (type instanceof LrealType) {
				return Double.isFinite(((BigDecimal) value).doubleValue());
			}
		} else if (value instanceof BigInteger) {
			if (type instanceof RealType) {
				if (type instanceof LrealType) {
					return Double.isFinite(((BigInteger) value).doubleValue());
				}
				return Float.isFinite(((BigInteger) value).floatValue());
			} else if (type instanceof SintType) {
				return checkRange((BigInteger) value, Byte.MIN_VALUE, Byte.MAX_VALUE);
			} else if (type instanceof IntType) {
				return checkRange((BigInteger) value, Short.MIN_VALUE, Short.MAX_VALUE);
			} else if (type instanceof DintType) {
				return checkRange((BigInteger) value, Integer.MIN_VALUE, Integer.MAX_VALUE);
			} else if (type instanceof LintType) {
				return checkRange((BigInteger) value, Long.MIN_VALUE, Long.MAX_VALUE);
			} else if (type instanceof UsintType) {
				return checkRangeUnsigned((BigInteger) value, BigInteger.valueOf(0xffL));
			} else if (type instanceof UintType) {
				return checkRangeUnsigned((BigInteger) value, BigInteger.valueOf(0xffffL));
			} else if (type instanceof UdintType) {
				return checkRangeUnsigned((BigInteger) value, BigInteger.valueOf(0xffffffffL));
			} else if (type instanceof UlintType) {
				return checkRangeUnsigned((BigInteger) value, new BigInteger("ffffffffffffffff", 16)); //$NON-NLS-1$
			} else if (type instanceof BoolType) {
				return checkRangeUnsigned((BigInteger) value, BigInteger.ONE);
			} else if (type instanceof ByteType) {
				return checkRangeUnsigned((BigInteger) value, BigInteger.valueOf(0xffL));
			} else if (type instanceof WordType) {
				return checkRangeUnsigned((BigInteger) value, BigInteger.valueOf(0xffffL));
			} else if (type instanceof DwordType) {
				return checkRangeUnsigned((BigInteger) value, BigInteger.valueOf(0xffffffffL));
			} else if (type instanceof LwordType) {
				return checkRangeUnsigned((BigInteger) value, new BigInteger("ffffffffffffffff", 16)); //$NON-NLS-1$
			}
		}
		return false;
	}

	private static boolean checkRange(final BigInteger value, final long lower, final long upper) {
		return value.compareTo(BigInteger.valueOf(lower)) >= 0 && value.compareTo(BigInteger.valueOf(upper)) <= 0;
	}

	private static boolean checkRangeUnsigned(final BigInteger value, final BigInteger upper) {
		return value.signum() >= 0 && value.compareTo(upper) <= 0;
	}

	private ValueValidator() {
		throw new UnsupportedOperationException("helper class ValueValidator should not be instantiated"); //$NON-NLS-1$
	}
}
