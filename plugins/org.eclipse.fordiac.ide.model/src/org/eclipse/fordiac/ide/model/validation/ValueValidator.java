/********************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.validation;

import org.eclipse.fordiac.ide.model.data.DataType;

public final class ValueValidator {

	/** Check the given value if it is valid literal for the data type
	 *
	 * @param type  data type for which the literal should be checked
	 * @param value the literal value to check
	 * @return empty string if the latieral is valid, otherwise an error message what is wrong with the literal */
	public static String validateValue(final DataType type, final String value) {
		return "";
	}

	private ValueValidator() {
		throw new UnsupportedOperationException("helper class ValueValidator should not be instantiated"); //$NON-NLS-1$
	}
}
