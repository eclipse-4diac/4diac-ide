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

import org.eclipse.fordiac.ide.model.data.CharType;
import org.eclipse.fordiac.ide.model.data.StringType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.eval.value.AnyStringValue;
import org.eclipse.fordiac.ide.model.eval.value.CharValue;
import org.eclipse.fordiac.ide.model.eval.value.StringValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;

public class StringCharacterVariable extends AbstractVariable<CharValue> {
	private final Variable<StringValue> delegate;
	private final int index;

	public StringCharacterVariable(final Variable<StringValue> delegate, final int index) {
		super(delegate.getName() + "[" + index + "]", IecTypes.ElementaryTypes.CHAR); //$NON-NLS-1$ //$NON-NLS-2$
		this.delegate = delegate;
		this.index = index;
	}

	@Override
	public CharValue getValue() {
		return delegate.getValue().charAt(index);
	}

	@Override
	public void setValue(final Value value) {
		if (!(value instanceof final CharValue charValue)) {
			throw createCastException(value);
		}
		final StringType type = (StringType) delegate.getType();
		if (index > (type.isSetMaxLength() ? type.getMaxLength() : AnyStringValue.MAX_LENGTH)) {
			throw new StringIndexOutOfBoundsException(index);
		}
		delegate.setValue(delegate.getValue().withCharAt(index, charValue));
	}

	@Override
	public CharType getType() {
		return (CharType) super.getType();
	}
}
