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
package org.eclipse.fordiac.ide.model.eval.function;

import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.eval.value.AnyCharsValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyMagnitudeValue;
import org.eclipse.fordiac.ide.model.eval.value.ULIntValue;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;

public interface StandardFunctions extends Functions {

	@SuppressWarnings("unchecked")
	static <T extends AnyMagnitudeValue> T ADD(final T... values) {
		return Stream.of(values).reduce((a, b) -> (T) ValueOperations.add(a, b)).orElseThrow();
	}

	static <T extends AnyCharsValue> ULIntValue LEN(final T string) {
		return ULIntValue.toULIntValue(string.length());
	}
}
