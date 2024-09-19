/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.value;

import java.util.Objects;
import java.util.Scanner;

public interface ValueConverter<T> {
	default String toString(final T value) {
		return Objects.toString(value).toUpperCase();
	}

	T toValue(String string) throws IllegalArgumentException;

	T toValue(Scanner scanner) throws IllegalArgumentException;
}
