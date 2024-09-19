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

import org.eclipse.fordiac.ide.model.data.WcharType;
import org.eclipse.fordiac.ide.model.data.WstringType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.eval.value.AnyStringValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.WCharValue;
import org.eclipse.fordiac.ide.model.eval.value.WStringValue;

public class WStringCharacterVariable extends AbstractVariable<WCharValue> {
	private final Variable<WStringValue> delegate;
	private final int index;

	public WStringCharacterVariable(final Variable<WStringValue> delegate, final int index) {
		super(delegate.getName() + "[" + index + "]", IecTypes.ElementaryTypes.WCHAR); //$NON-NLS-1$ //$NON-NLS-2$
		this.delegate = delegate;
		this.index = index;
	}

	@Override
	public WCharValue getValue() {
		return delegate.getValue().charAt(index);
	}

	@Override
	public void setValue(final Value value) {
		if (!(value instanceof final WCharValue charValue)) {
			throw createCastException(value);
		}
		final WstringType type = (WstringType) delegate.getType();
		if (index > (type.isSetMaxLength() ? type.getMaxLength() : AnyStringValue.MAX_LENGTH)) {
			throw new StringIndexOutOfBoundsException(index);
		}
		delegate.setValue(delegate.getValue().withCharAt(index, charValue));
	}

	@Override
	public WcharType getType() {
		return (WcharType) super.getType();
	}
}
