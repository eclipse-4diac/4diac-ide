/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.download;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.text.MessageFormat;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.library.Messages;

/**
 * Signals that a library source answered a request with an unsuccessful HTTP
 * status. The message names the requested location without query and user
 * information.
 */
public class LibraryPackageRequestException extends IOException {

	private static final long serialVersionUID = 1L;

	private static final int TOO_MANY_REQUESTS = 429;
	// only a delay in seconds is shown, not an HTTP date or other server text
	private static final Pattern SECONDS = Pattern.compile("\\d{1,6}"); //$NON-NLS-1$

	private final int statusCode;

	/**
	 * Creates the exception for an unsuccessful response.
	 *
	 * @param location   the requested location
	 * @param statusCode the HTTP status code of the response
	 */
	public LibraryPackageRequestException(final URI location, final int statusCode) {
		this(location, statusCode, null);
	}

	/**
	 * Creates the exception for an unsuccessful response.
	 *
	 * @param location   the requested location
	 * @param statusCode the HTTP status code of the response
	 * @param retryAfter the {@code Retry-After} header of the response, can be
	 *                   {@code null}
	 */
	public LibraryPackageRequestException(final URI location, final int statusCode, final String retryAfter) {
		super(message(AbstractLibraryPackageDownloader.describe(location), statusCode, retryAfter));
		this.statusCode = statusCode;
	}

	private static String message(final String location, final int statusCode, final String retryAfter) {
		return switch (statusCode) {
		case HttpURLConnection.HTTP_UNAUTHORIZED ->
			MessageFormat.format(Messages.LibraryPackageDownloader_Unauthorized, location);
		case HttpURLConnection.HTTP_FORBIDDEN ->
			MessageFormat.format(Messages.LibraryPackageDownloader_Forbidden, location);
		case HttpURLConnection.HTTP_NOT_FOUND ->
			MessageFormat.format(Messages.LibraryPackageDownloader_NotFound, location);
		case HttpURLConnection.HTTP_PROXY_AUTH ->
			MessageFormat.format(Messages.LibraryPackageDownloader_ProxyAuthenticationRequired, location);
		case TOO_MANY_REQUESTS -> retryAfter != null && SECONDS.matcher(retryAfter.strip()).matches()
				? MessageFormat.format(Messages.LibraryPackageDownloader_TooManyRequestsRetryAfter, location,
						retryAfter.strip())
				: MessageFormat.format(Messages.LibraryPackageDownloader_TooManyRequests, location);
		default -> statusCode >= 500 && statusCode < 600
				? MessageFormat.format(Messages.LibraryPackageDownloader_ServerError, location,
						Integer.toString(statusCode))
				: MessageFormat.format(Messages.LibraryPackageDownloader_RequestFailed, location,
						Integer.toString(statusCode));
		};
	}

	/**
	 * Returns whether the source rejected the credentials or their permissions
	 * (HTTP 401 or 403), which usually needs a change of the configuration.
	 *
	 * @return {@code true} for HTTP 401 and 403
	 */
	public boolean isAccessDenied() {
		return statusCode == HttpURLConnection.HTTP_UNAUTHORIZED || statusCode == HttpURLConnection.HTTP_FORBIDDEN;
	}

	/**
	 * Returns the HTTP status code of the response.
	 *
	 * @return the status code
	 */
	public int getStatusCode() {
		return statusCode;
	}
}
