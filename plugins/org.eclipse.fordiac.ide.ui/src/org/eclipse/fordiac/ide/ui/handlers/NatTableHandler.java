/*******************************************************************************
 * Copyright (c) 2023 Johanees Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.handlers;

public final class NatTableHandler {
	private NatTableHandler() {

	}

	public static Boolean parseNewValueObject(final Object newValue) {
		if (newValue != null) {
			if (newValue instanceof Boolean) {
				return (Boolean) newValue;

			} else if (newValue instanceof String && (newValue.toString().equalsIgnoreCase(Boolean.TRUE.toString())
					|| newValue.toString().equalsIgnoreCase(Boolean.FALSE.toString()))) {
				return Boolean.valueOf(String.valueOf(newValue));
			}
		}

		return null;
	}

}
