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
package org.eclipse.fordiac.ide.model.eval.variable;

import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public interface Variable<T extends Value> {
	/**
	 * Get the name of the variable
	 *
	 * @return The name
	 */
	String getName();

	/**
	 * Get the type of the variable
	 *
	 * @return The type
	 */
	INamedElement getType();

	/**
	 * Get the current value of the variable
	 *
	 * @return The current value
	 */
	T getValue();

	/**
	 * Set the value of the variable
	 *
	 * @param value The new value
	 * @throws ClassCastException If the value if not assignable to the type of this
	 *                            variable
	 */
	void setValue(final Value value) throws ClassCastException;

	/**
	 * Set the value of the variable
	 *
	 * @param value The new value
	 * @throws IllegalArgumentException If the new value is illegal for this
	 *                                  variable
	 */
	void setValue(final String value) throws IllegalArgumentException;

	/**
	 * Set the value of the variable
	 *
	 * @param value       The new value
	 * @param typeLibrary The type library to resolve type specifiers (may be null)
	 * @throws IllegalArgumentException If the new value is illegal for this
	 *                                  variable
	 */
	void setValue(final String value, TypeLibrary typeLibrary) throws IllegalArgumentException;

	/**
	 * Validate if the value is assignable to this variable
	 *
	 * @param value The new value
	 * @return true if the value is valid, false otherwise
	 */
	boolean validateValue(final String value);

	/**
	 * Validate if the value is assignable to this variable
	 *
	 * @param value       The new value
	 * @param typeLibrary The type library to resolve type specifiers (may be null)
	 * @return true if the value is valid, false otherwise
	 */
	boolean validateValue(final String value, TypeLibrary typeLibrary);

	/**
	 * Returns a string representation of the variable, with optional pretty
	 * formatting.
	 *
	 * @param pretty whether to use pretty formatting
	 * @see java.lang.Object#toString()
	 */
	String toString(boolean pretty);

	/**
	 * Get the children variables
	 *
	 * @return The children variables
	 */
	default Stream<Variable<?>> getChildren() {
		return Stream.empty();
	}
}
