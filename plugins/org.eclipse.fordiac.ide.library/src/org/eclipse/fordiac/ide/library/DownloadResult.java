/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library;

// result is provided with status OK - it is null in all other cases
public record DownloadResult<T>(T result, Status status, String message) {

	public DownloadResult(final T result) {
		this(result, result != null ? Status.OK : Status.ERROR, result != null ? null : Messages.DownloadNullResult);
	}

	public DownloadResult(final Status status) {
		this(null, status, null);
	}

	public DownloadResult(final Status status, final String message) {
		this(null, status, message);
	}

	public enum Status {
		OK, NOT_FOUND, NO_CONFIG, CONFIG_ERROR, ERROR
	}

	@Override
	public String message() {
		return message != null ? message : Messages.DownloadUnexpectedError;
	}
}
