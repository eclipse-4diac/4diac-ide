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
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

/**
 * Sends the HTTP requests of an {@link AbstractLibraryPackageDownloader}.
 *
 * <p>
 * The transport only moves bytes. It does not follow redirects and does not add
 * credentials, because the downloader decides which redirect to follow and
 * which request may carry credentials. Tests can replace the transport to
 * serve local fixtures.
 *
 * @see #getDefault()
 */
public interface LibraryPackageTransport {

	/**
	 * Sends a request and reads the response body with the given handler.
	 *
	 * @param <T>     the type of the response body
	 * @param request the request
	 * @param handler reads the response body
	 * @param monitor checked for cancellation while waiting for the response, can
	 *                be {@code null}
	 * @return the response, also for unsuccessful status codes and redirects
	 * @throws IOException                if the request fails or times out
	 * @throws OperationCanceledException if the monitor is canceled
	 */
	<T> HttpResponse<T> send(HttpRequest request, BodyHandler<T> handler, IProgressMonitor monitor)
			throws IOException;

	/**
	 * Returns the transport shared by all downloaders. It uses
	 * {@link java.net.http.HttpClient} with a connect timeout of 10 seconds, ends a
	 * request after 30 seconds without data and uses the default proxy selector.
	 *
	 * @return the default transport
	 */
	static LibraryPackageTransport getDefault() {
		return HttpClientTransport.INSTANCE;
	}
}
