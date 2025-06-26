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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.util;

public class LibraryElementHashException extends Exception {

	private static final long serialVersionUID = -3086076523632402160L;

	public LibraryElementHashException(final String message) {
		super(message);
	}

	public LibraryElementHashException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
