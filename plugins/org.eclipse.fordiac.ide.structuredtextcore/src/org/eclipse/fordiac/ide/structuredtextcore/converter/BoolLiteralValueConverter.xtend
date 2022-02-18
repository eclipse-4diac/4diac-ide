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

import java.math.BigDecimal
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter
import org.eclipse.xtext.nodemodel.INode

class BoolLiteralValueConverter extends AbstractNullSafeConverter<BigDecimal> {
	
	val TRUE = "TRUE"
	val FALSE = "FALSE"

	override protected internalToValue(String string, INode node) throws ValueConverterException {
		Boolean.parseBoolean(string) ? BigDecimal.ONE : BigDecimal.ZERO
	}
	
	override protected internalToString(BigDecimal value) {
		BigDecimal.ONE == value ? TRUE : FALSE
	}
}
