/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.inputgenerator;

interface BaseRandomNextValue {

	default String nextBool() {
		throw new UnsupportedOperationException();
	}

	default String nextByte() {
		throw new UnsupportedOperationException();
	}

	default String nextSint() {
		throw new UnsupportedOperationException();
	}

	default String nextUint() {
		throw new UnsupportedOperationException();
	}

	default String nextInteger() {
		throw new UnsupportedOperationException();
	}

	default String nextWord() {
		throw new UnsupportedOperationException();
	}

	default String nextDword() {
		throw new UnsupportedOperationException();
	}

	default String nextLword() {
		throw new UnsupportedOperationException();
	}

	default String nextChar() {
		throw new UnsupportedOperationException();
	}

	default String nextWchar() {
		throw new UnsupportedOperationException();
	}

	default String nextDint() {
		throw new UnsupportedOperationException();
	}

	default String nextLint() {
		throw new UnsupportedOperationException();
	}

	default String nextUsint() {
		throw new UnsupportedOperationException();
	}

	default String nextUdint() {
		throw new UnsupportedOperationException();
	}

	default String nextUlint() {
		throw new UnsupportedOperationException();
	}

	default String nextReal() {
		throw new UnsupportedOperationException();
	}

	default String nextLreal() {
		throw new UnsupportedOperationException();
	}
}
