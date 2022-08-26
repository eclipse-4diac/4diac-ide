/*******************************************************************************
 * Copyright (c) 2021, 2022 Johannes Kepler University Linz and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmenda, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *   Paul Pavlicek
 *   	 -cleanup and extend matching
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.mm.utils;

import java.util.List;

import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class SequenceMatcher {

	private final FBType fbType;

	public SequenceMatcher(final FBType fbType) {
		this.fbType = fbType;
	}

	public boolean matchPrimitive(final Primitive p) {
		return matchEvent(p.getEvent(), p instanceof InputPrimitive)
				&& matchParameters(p.getParameters(), p instanceof InputPrimitive);
	}

	private boolean matchEvent(final String eventName, final boolean isInput) {
		final IInterfaceElement el = fbType.getInterfaceList().getInterfaceElement(eventName);
		if (el instanceof Event) {
			return el.isIsInput() == isInput;
		}
		return false;
	}

	private boolean matchParameters(final String parameters, final boolean isInput) {
		// split parameters at ";"
		final List<String> params=ServiceSequenceUtils.splitList(parameters);

		// parse each parameter assumption a:=b
		return params.stream().allMatch(param -> matchVariable(param, isInput));
	}

	public boolean matchVariable(final String assumption, final boolean isInput) {
		// parse the assumption of the form XY:=UINT#0
		final List<String> str = ServiceSequenceUtils.splitParameter(assumption);
		if (str.size() == 2) {
			final IInterfaceElement el = fbType.getInterfaceList().getInterfaceElement(str.get(0));
			if (el instanceof VarDeclaration && el.isIsInput() == isInput) {
				final Value val = ((VarDeclaration) el).getValue();
				if (val != null) {
					String value = val.getValue();
					value = ServiceSequenceUtils.removeKeyword(value);
					if (value != null) {
						return matchValue(value, str.get(1), el.getType());
					}
				}
			}
		}

		return false;
	}

	private static boolean matchValue(final String typeValue, final String expectedValue, final DataType dtp) {
		if (dtp instanceof BoolType) {
			return matchBoolean(typeValue,
					Boolean.parseBoolean(expectedValue.strip()) || "1".equals(expectedValue.strip())); //$NON-NLS-1$
		}
		return typeValue.strip().equals(expectedValue.strip());
	}

	private static boolean matchBoolean(final String typeValue, final boolean expectedValue) {
		final String BOOL_FALSE = "FALSE"; //$NON-NLS-1$
		final String BOOL_TRUE = "TRUE"; //$NON-NLS-1$
		final String BOOL_ZERO = "0"; //$NON-NLS-1$
		final String BOOL_ONE = "1"; //$NON-NLS-1$
		if (BOOL_ONE.equalsIgnoreCase(typeValue)) {
			return (expectedValue);
		} else if (BOOL_ZERO.equalsIgnoreCase(typeValue)) {
			return (!expectedValue);
		} else if (BOOL_TRUE.equalsIgnoreCase(typeValue)) {
			return (expectedValue);
		} else if (BOOL_FALSE.equalsIgnoreCase(typeValue)) {
			return (!expectedValue);
		}

		return false;
	}
}
