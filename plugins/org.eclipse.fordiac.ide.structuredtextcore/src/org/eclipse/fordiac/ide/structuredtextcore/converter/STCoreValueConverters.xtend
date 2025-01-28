/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.converter

import com.google.inject.Inject
import com.google.inject.Singleton
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import org.eclipse.fordiac.ide.model.value.DateAndTimeValueConverter
import org.eclipse.fordiac.ide.model.value.DateValueConverter
import org.eclipse.fordiac.ide.model.value.NumericValueConverter
import org.eclipse.fordiac.ide.model.value.TimeOfDayValueConverter
import org.eclipse.fordiac.ide.model.value.TimeValueConverter
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STString
import org.eclipse.xtext.conversion.IValueConverter
import org.eclipse.xtext.conversion.ValueConverter
import org.eclipse.xtext.conversion.impl.AbstractDeclarativeValueConverterService

@Singleton
class STCoreValueConverters extends AbstractDeclarativeValueConverterService {

	DelegatingValueConverter<Object> numericValueConverter = new DelegatingValueConverter(
		NumericValueConverter.INSTANCE)

	DelegatingValueConverter<Duration> timeValueConverter = new DelegatingValueConverter(TimeValueConverter.INSTANCE)

	DelegatingValueConverter<LocalDate> dateValueConverter = new DelegatingValueConverter(DateValueConverter.INSTANCE)

	DelegatingValueConverter<LocalTime> timeOfDayValueConverter = new DelegatingValueConverter(
		TimeOfDayValueConverter.INSTANCE)

	DelegatingValueConverter<LocalDateTime> dateAndTimeValueConverter = new DelegatingValueConverter(
		DateAndTimeValueConverter.INSTANCE)

	@Inject
	STStringValueConverter stringValueConverter

	@Inject
	STEnumValueConverter enumValueConverter

	@ValueConverter(rule="Numeric")
	def IValueConverter<Object> Numeric() {
		return numericValueConverter;
	}
	
	@ValueConverter(rule="SignedNumeric")
	def IValueConverter<Object> SignedNumeric() {
		return numericValueConverter;
	}
	
	@ValueConverter(rule="Time")
	def IValueConverter<Duration> Time() {
		return timeValueConverter;
	}

	@ValueConverter(rule="Date")
	def IValueConverter<LocalDate> Date() {
		return dateValueConverter;
	}

	@ValueConverter(rule="TimeOfDay")
	def IValueConverter<LocalTime> TimeOfDay() {
		return timeOfDayValueConverter;
	}

	@ValueConverter(rule="DateAndTime")
	def IValueConverter<LocalDateTime> DateAndTime() {
		return dateAndTimeValueConverter;
	}

	@ValueConverter(rule="STRING")
	def IValueConverter<STString> STRING() {
		return stringValueConverter;
	}

	@ValueConverter(rule="EnumValue")
	def IValueConverter<String> EnumValue() {
		return enumValueConverter;
	}
}
