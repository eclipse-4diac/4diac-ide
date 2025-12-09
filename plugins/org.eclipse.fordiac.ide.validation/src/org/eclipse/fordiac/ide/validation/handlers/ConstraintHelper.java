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

import org.eclipse.core.resources.IMarker;

public final class ConstraintHelper {
	private static final String FORDIAC_CONSTRAINT_PROPERTIES = "constraints"; //$NON-NLS-1$
	private static final ResourceBundle constraintProperties = ResourceBundle.getBundle(FORDIAC_CONSTRAINT_PROPERTIES);

	private final String message;
	private final int severity;

	public ConstraintHelper(final String information) {
		final String WARNING_PREFIX = "WARNING;"; //$NON-NLS-1$
		final String INFO_PREFIX = "INFO;"; //$NON-NLS-1$
		final String ERROR_PREFIX = "ERROR;"; //$NON-NLS-1$

		if (information.startsWith(WARNING_PREFIX)) {
			severity = IMarker.SEVERITY_WARNING;
			message = computeTranslatedMessage(information.substring(WARNING_PREFIX.length()));
		} else if (information.startsWith(INFO_PREFIX)) {
			severity = IMarker.SEVERITY_INFO;
			message = computeTranslatedMessage(information.substring(INFO_PREFIX.length()));
		} else if (information.startsWith(ERROR_PREFIX)) {
			severity = IMarker.SEVERITY_ERROR;
			message = computeTranslatedMessage(information.substring(ERROR_PREFIX.length()));
		} else {
			severity = IMarker.SEVERITY_ERROR;
			message = information;
		}
	}

	public String getMessage() {
		return message;
	}

	public int getSeverity() {
		return severity;
	}

	private static String computeTranslatedMessage(final String message) {
		if (message.startsWith("[") && message.endsWith("]")) { //$NON-NLS-1$ //$NON-NLS-2$
			final String key = message.substring(1, message.length() - 1);
			if (constraintProperties.containsKey(key)) {
				return constraintProperties.getString(key);
			}
		}
		return message;
	}
}
