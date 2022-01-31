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

import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter
import org.eclipse.xtext.nodemodel.INode

class STElementaryTypeConverter extends AbstractNullSafeConverter<DataType> {

	override protected internalToValue(String string, INode node) throws ValueConverterException {
		return DataTypeLibrary.nonUserDefinedDataTypes.findFirst[it.name == string.substring(0, string.length - 1)]
	}

	override protected internalToString(DataType value) '''«value.name»#'''

}
