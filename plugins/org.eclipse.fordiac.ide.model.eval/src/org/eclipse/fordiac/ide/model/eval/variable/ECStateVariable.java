/**
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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

import org.eclipse.fordiac.ide.model.eval.value.ECStateValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class ECStateVariable extends AbstractVariable<ECStateValue> {
	public static final String NAME = "__STATE"; //$NON-NLS-1$

	private ECStateValue value;

	public ECStateVariable(final BasicFBType type) {
		this(type.getECC().getStart());
	}

	public ECStateVariable(final ECState state) {
		this(state.getECC().getBasicFBType(), new ECStateValue(state));
	}

	public ECStateVariable(final BasicFBType type, final String value) {
		super(ECStateVariable.NAME, type);
		setValue(value);
	}

	public ECStateVariable(final BasicFBType type, final Value value) {
		super(ECStateVariable.NAME, type);
		setValue(value);
	}

	@Override
	public void setValue(final Value value) {
		if (!(value instanceof final ECStateValue stateValue) || stateValue.getType() != getType()) {
			throw createCastException(value);
		}
		this.value = stateValue;
	}

	@Override
	public void setValue(final String value, final TypeLibrary typeLibrary) {
		this.value = new ECStateValue(
				getType().getECC().getECState().stream().filter(state -> state.getName().equals(value)).findFirst()
						.orElseThrow(() -> new IllegalArgumentException(
								"No state named " + value + " in type " + getType().getName()))); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public boolean validateValue(final String value, final TypeLibrary typeLibrary) {
		return getType().getECC().getECState().stream().anyMatch(state -> state.getName().equals(value));
	}

	@Override
	public BasicFBType getType() {
		return (BasicFBType) super.getType();
	}

	@Override
	public ECStateValue getValue() {
		return value;
	}
}
