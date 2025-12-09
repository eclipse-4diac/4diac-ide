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
package org.eclipse.fordiac.ide.model.eval.value;

import java.util.Objects;

import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;

public final class ECStateValue implements Value {
	private final ECState state;

	public ECStateValue(final ECState state) {
		this.state = state;
	}

	@Override
	public BasicFBType getType() {
		return state.getECC().getBasicFBType();
	}

	@Override
	public int hashCode() {
		return state.getName().hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ECStateValue other = (ECStateValue) obj;
		return Objects.equals(state, other.state);
	}

	@Override
	public String toString() {
		return state.getName();
	}

	public ECState getState() {
		return state;
	}
}
