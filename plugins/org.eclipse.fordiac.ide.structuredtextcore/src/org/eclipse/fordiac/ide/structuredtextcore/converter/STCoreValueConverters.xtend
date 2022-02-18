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
import java.math.BigDecimal
import org.eclipse.xtext.conversion.IValueConverter
import org.eclipse.xtext.conversion.ValueConverter
import org.eclipse.xtext.conversion.impl.AbstractDeclarativeValueConverterService

class STCoreValueConverters extends AbstractDeclarativeValueConverterService {
	
	@Inject
	BoolLiteralValueConverter boolLiteralValueConverter
	
	@Inject
	NonDecimalValueConverter nonDecimalValueConverter
	
	@ValueConverter(rule="BoolLiteral")
	def IValueConverter<BigDecimal> BOOL_LITERAL() {
		return boolLiteralValueConverter;
	}
	
	@ValueConverter(rule="NON_DECIMAL")
	def IValueConverter<BigDecimal> NON_DECIMAL() {
		return nonDecimalValueConverter;
	}
}