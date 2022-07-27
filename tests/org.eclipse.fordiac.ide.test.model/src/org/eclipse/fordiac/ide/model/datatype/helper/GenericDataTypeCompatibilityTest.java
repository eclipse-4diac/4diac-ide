/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.datatype.helper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method", "squid:S5960" })
class GenericDataTypeCompatibilityTest {

	@DisplayName("Implicit cast checks for generic types")
	@ParameterizedTest(name = "Cast type {2} to {3}")
	@MethodSource("typeArgumentsCartesianProvider")
	void genericCastTest(final DataType type, final DataType castType, final String typeName,
			final String castTypeName) {
		if (castType.eClass().isSuperTypeOf(type.eClass())) {
			assertTrue(castType.isAssignableFrom(type));
		} else {
			assertFalse(castType.isAssignableFrom(type));
		}
	}

	static Stream<Arguments> typeArgumentsCartesianProvider() {
		return Stream
				.concat(ElementaryTypes.getAllElementaryType().stream(), GenericTypes.getAllGenericTypes().stream())
				.flatMap(first -> GenericTypes.getAllGenericTypes().stream()
						.map(second -> arguments(first, second, first.getName(), second.getName())));
	}
}
