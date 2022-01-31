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

import org.eclipse.xtext.conversion.impl.AbstractDeclarativeValueConverterService
import org.eclipse.xtext.conversion.ValueConverter
import com.google.inject.Inject
import org.eclipse.xtext.conversion.IValueConverter
import java.math.BigDecimal
import org.eclipse.fordiac.ide.model.data.DataType

class STCoreValueConverters extends AbstractDeclarativeValueConverterService {
	
	@Inject
	BoolLiteralValueConverter boolLiteralValueConverter
	
	@Inject
	NonDecimalValueConverter nonDecimalValueConverter
	
	@Inject
	STElementaryTypeConverter elementaryTypeConverter
	
	@ValueConverter(rule="BoolLiteral")
	def IValueConverter<BigDecimal> BOOL_LITERAL() {
		return boolLiteralValueConverter;
	}
	
	@ValueConverter(rule="NON_DECIMAL")
	def IValueConverter<BigDecimal> NON_DECIMAL() {
		return nonDecimalValueConverter;
	}
	
	@ValueConverter(rule="STNumericLiteralType")
	def IValueConverter<DataType> STNumericLiteralType() {
		return elementaryTypeConverter;
	}
		
	@ValueConverter(rule="STDateLiteralType")
	def IValueConverter<DataType> STDateLiteralType() {
		return elementaryTypeConverter;
	}
	
	
	@ValueConverter(rule="STTimeLiteralType")
	def IValueConverter<DataType> STTimeLiteralType() {
		return elementaryTypeConverter;
	}
	
	
	@ValueConverter(rule="STTimeOfDayLiteralType")
	def IValueConverter<DataType> STTimeOfDayLiteralType() {
		return elementaryTypeConverter;
	}
	
	@ValueConverter(rule="STDateAndTimeLiteralType")
	def IValueConverter<DataType> STDateAndTimeLiteralType() {
		return elementaryTypeConverter;
	}
	
	@ValueConverter(rule="STStringLiteralType")
	def IValueConverter<DataType> STStringLiteralType() {
		return elementaryTypeConverter;
	}
	
	
}