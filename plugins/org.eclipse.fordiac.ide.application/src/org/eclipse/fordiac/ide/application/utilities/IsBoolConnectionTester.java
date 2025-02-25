/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Stemmer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;

public class IsBoolConnectionTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		final List<Object> selection = getList(receiver);
		for (final Object sel : selection) {
			if (sel instanceof final ConnectionEditPart cep) {
				return cep.getModel().getSource().getType() == IecTypes.ElementaryTypes.BOOL
						&& cep.getModel().getDestination().getType() == IecTypes.ElementaryTypes.BOOL;
			}
		}
		return false;
	}

	private static List<Object> getList(final Object reciever) {
		if (reciever instanceof final List<?> list) {
			return Collections.unmodifiableList(list);
		}
		return Arrays.asList(reciever);
	}

}
