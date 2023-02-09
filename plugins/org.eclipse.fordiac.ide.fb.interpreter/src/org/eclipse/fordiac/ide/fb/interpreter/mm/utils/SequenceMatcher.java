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
import java.util.Optional;

import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.value.TypedValueConverter;

public class SequenceMatcher {

	private final FBType fbType;

	public SequenceMatcher(final FBType fbType) {
		this.fbType = fbType;
	}

	public Optional<String> matchPrimitive(final Primitive p) {
		final Optional<String> errorMsg = matchEvent(p.getEvent(), p instanceof InputPrimitive);
		if (errorMsg.isPresent()) {
			return errorMsg;
		}
		return matchParameters(p.getParameters(), p instanceof InputPrimitive);
	}

	private Optional<String> matchEvent(final String eventName, final boolean isInput) {
		final IInterfaceElement el = fbType.getInterfaceList().getInterfaceElement(eventName);
		if (!(el instanceof Event)) {
			return Optional.of("Event " + eventName + " not present in FB interface");  //$NON-NLS-1$//$NON-NLS-2$
		}
		if (el.isIsInput() != isInput) {
			return Optional.of("Event pin " + eventName + " is not at the correct interface (input or output"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return Optional.empty();
	}

	public Optional<String> matchParameters(final String parameters, final boolean isInput) {
		// split parameters at ";"
		final List<String> params = ServiceSequenceUtils.splitList(parameters);
		// parse each parameter assumption a:=b
		for (final String param : params) {
			final Optional<String> errorMsg = matchVariable(param, isInput);
			if (errorMsg.isPresent()) {
				return errorMsg;
			}
		}
		return Optional.empty();
	}

	public Optional<String> matchVariable(final String assumption, final boolean isInput) {
		// parse the assumption of the form XY:=UINT#0
		final List<String> str = ServiceSequenceUtils.splitParameter(assumption);
		if (str.size() == 2) {
			final IInterfaceElement el = fbType.getInterfaceList().getInterfaceElement(str.get(0));
			if (!(el instanceof VarDeclaration) || (el.isIsInput() != isInput)) {
				return Optional.of("No matching data pin with name " + str.get(0)); //$NON-NLS-1$
			}
			final Value val = ((VarDeclaration) el).getValue();
			if (val != null) {
				try {
					final TypedValueConverter converter = new TypedValueConverter(el.getType());
					final Object value = converter.toValue(val.getValue());
					final Object expectedValue = converter.toValue(str.get(1));
					if (!value.equals(expectedValue)) {
						return Optional.of("Values do not match, expected: " + expectedValue + ", received: " + value); //$NON-NLS-1$ //$NON-NLS-2$
					}
				} catch (final Exception e) {
					return Optional.of(e.getMessage());
				}
			}
		}
		return Optional.empty();
	}
}
