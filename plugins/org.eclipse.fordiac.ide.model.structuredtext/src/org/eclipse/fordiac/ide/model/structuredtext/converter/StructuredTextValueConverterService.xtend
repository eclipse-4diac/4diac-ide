/*******************************************************************************
 * Copyright (c) 2015, 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst 
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.structuredtext.converter

import org.eclipse.xtext.conversion.impl.AbstractDeclarativeValueConverterService
import org.eclipse.xtext.conversion.ValueConverter
import org.eclipse.xtext.conversion.IValueConverter
import com.google.inject.Inject
import java.util.Date

class StructuredTextValueConverterService extends AbstractDeclarativeValueConverterService {

	@Inject
	private BINARY_INTValueConverter binaryIntValueConverter;

	@ValueConverter(rule="BINARY_INT")
	def IValueConverter<Long> BINARY_INT() {
		return binaryIntValueConverter;
	}

	@Inject
	private OCTAL_INTValueConverter octalIntValueConverter;

	@ValueConverter(rule="OCTAL_INT")
	def IValueConverter<Long> OCTAL_INT() {
		return octalIntValueConverter;
	}

	@Inject
	private HEX_INTValueConverter hexIntValueConverter;

	@ValueConverter(rule="HEX_INT")
	def IValueConverter<Long> HEX_INT() {
		return hexIntValueConverter;
	}

	@Inject
	private UNSIGNED_INTValueConverter unsignedIntValueConverter;

	@ValueConverter(rule="UNSIGNED_INT")
	def IValueConverter<Long> UNSIGNED_INT() {
		return unsignedIntValueConverter;
	}

	@Inject
	private S_BYTE_CHAR_STRValueConverter singleStringValueConverter;

	@ValueConverter(rule="S_BYTE_CHAR_STR")
	def IValueConverter<String> S_BYTE_CHAR_STR() {
		return singleStringValueConverter;
	}

	@Inject
	private D_BYTE_CHAR_STRValueConverter doubleStringValueConverter;

	@ValueConverter(rule="D_BYTE_CHAR_STR")
	def IValueConverter<String> D_BYTE_CHAR_STR() {
		return doubleStringValueConverter;
	}

	@Inject
	private Signed_IntValueConverter signedIntValueConverter;

	@ValueConverter(rule="Signed_Int")
	def IValueConverter<Long> Signed_int() {
		return signedIntValueConverter;
	}

	@Inject
	private Array_SizeValueConverter arraySizeValueConverter;

	@ValueConverter(rule="Array_Size")
	def IValueConverter<Integer> Array_Size() {
		return arraySizeValueConverter;
	}

	@Inject
	private Real_ValueValueConverter realValueConverter;

	@ValueConverter(rule="Real_Value")
	def IValueConverter<Double> Real_Value() {
		return realValueConverter;
	}

	@Inject
	private Bool_ValueValueConverter boolValueConverter;

	@ValueConverter(rule="Bool_Value")
	def IValueConverter<Boolean> Bool_Value() {
		return boolValueConverter;
	}

	@Inject
	private DaytimeValueConverter daytimeValueConverter;

	@ValueConverter(rule="Daytime")
	def IValueConverter<Date> Daytime() {
		return daytimeValueConverter;
	}

	@Inject
	private Date_LiteralValueConverter dateLiteralValueConverter;

	@ValueConverter(rule="Date_Literal")
	def IValueConverter<Date> Date_Literal() {
		return dateLiteralValueConverter;
	}

	@Inject
	private Date_And_Time_ValueValueConverter dateAndTimeValueConverter;

	@ValueConverter(rule="Date_And_Time_Value")
	def IValueConverter<Date> Date_And_Time_Value() {
		return dateAndTimeValueConverter;
	}

}
