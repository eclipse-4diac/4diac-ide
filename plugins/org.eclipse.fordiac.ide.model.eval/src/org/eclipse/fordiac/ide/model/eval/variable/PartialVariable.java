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

import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.ByteType;
import org.eclipse.fordiac.ide.model.data.DwordType;
import org.eclipse.fordiac.ide.model.data.LwordType;
import org.eclipse.fordiac.ide.model.data.WordType;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;

public class PartialVariable<T extends Value> extends AbstractVariable<T> {
	private final Variable<?> delegate;
	private final int index;

	public PartialVariable(final Variable<?> delegate, final AnyBitType partial, final int index) {
		super(delegate.getName() + ".%" + getPartialName(partial) + index, partial); //$NON-NLS-1$
		this.delegate = delegate;
		this.index = index;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getValue() {
		return (T) ValueOperations.partial(delegate.getValue(), getType(), index);
	}

	@Override
	public void setValue(final Value value) {
		delegate.setValue(ValueOperations.partial(delegate.getValue(), getType(), index, value));
	}

	@Override
	public AnyBitType getType() {
		return (AnyBitType) super.getType();
	}

	private static String getPartialName(final AnyBitType type) {
		if (type instanceof BoolType) {
			return "X"; //$NON-NLS-1$
		}
		if (type instanceof ByteType) {
			return "B"; //$NON-NLS-1$
		}
		if (type instanceof WordType) {
			return "W"; //$NON-NLS-1$
		}
		if (type instanceof DwordType) {
			return "D"; //$NON-NLS-1$
		}
		if (type instanceof LwordType) {
			return "L"; //$NON-NLS-1$
		}
		return null;
	}
}
