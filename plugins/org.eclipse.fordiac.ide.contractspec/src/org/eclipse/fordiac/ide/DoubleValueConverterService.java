/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide;

import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractDeclarativeValueConverterService;
import org.eclipse.xtext.nodemodel.INode;

public class DoubleValueConverterService extends AbstractDeclarativeValueConverterService {

	@ValueConverter(rule = "Value")
	public static IValueConverter<Double> getValueConverter() {
		return new IValueConverter<>() {
			@Override
			public Double toValue(final String string, final INode node) throws ValueConverterException {
				try {
					return Double.valueOf(string);
				} catch (final Exception e) {
					throw new ValueConverterException(e.getMessage(), node, e);
				}
			}

			@Override
			public String toString(final Double value) throws ValueConverterException {
				try {
					// TODO: could remove trailing zeros here, e.g. 10 is printed as 10.0
					return value.toString();
				} catch (final Exception e) {
					throw new ValueConverterException(e.getMessage(), null, e);
				}
			}
		};
	}
}
