/*******************************************************************************
 * Copyright (c) 2020 Sandor Bacsi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sandor Bacsi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.handlers;

import java.util.ResourceBundle;

public class ConstraintHelper {
	private static final String FORDIAC_CONSTRAINT_PROPERTIES = "constraints";
	private static ResourceBundle fordiacConstraintProperties = ResourceBundle.getBundle(FORDIAC_CONSTRAINT_PROPERTIES);

	public static String[] getConstraintProperties(String name) {
		return fordiacConstraintProperties.getString(name).split(";");
	}
}
