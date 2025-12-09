/**
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.structuredtextcore.converter

import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.fordiac.ide.model.value.ValueConverter
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

@FinalFieldsConstructor
class DelegatingValueConverter<T> extends AbstractNullSafeConverter<T> {

	final ValueConverter<T> delegate

	override protected internalToString(T value) {
		delegate.toString(value)
	}

	override protected internalToValue(String string, INode node) throws ValueConverterException {
		try {
			delegate.toValue(string)
		} catch (Exception e) {
			throw new ValueConverterException(e.message, node, e)
		}
	}
}
