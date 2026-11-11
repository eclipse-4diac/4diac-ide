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
package org.eclipse.fordiac.ide.gitlab.management;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.ResponseInfo;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.SSLSession;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.library.download.LibraryPackageTransport;

/**
 * Serves local fixtures per request URI through the body handlers of the
 * downloader, and records the requests. A request without fixture fails like
 * an unreachable host.
 */
final class FakeTransport implements LibraryPackageTransport {

	private record Fixture(int status, Map<String, List<String>> headers, byte[] body) {
	}

	private record Info(int statusCode, HttpHeaders headers, HttpClient.Version version) implements ResponseInfo {
	}

	private record Response<T>(int statusCode, HttpRequest request, HttpHeaders headers, T body)
			implements HttpResponse<T> {

		@Override
		public Optional<HttpResponse<T>> previousResponse() {
			return Optional.empty();
		}

		@Override
		public Optional<SSLSession> sslSession() {
			return Optional.empty();
		}

		@Override
		public URI uri() {
			return request.uri();
		}

		@Override
		public HttpClient.Version version() {
			return HttpClient.Version.HTTP_1_1;
		}
	}

	private final Map<URI, Fixture> fixtures = new HashMap<>();
	private final List<HttpRequest> requests = new ArrayList<>();
	private Runnable onRequest = () -> {
		// nothing by default
	};

	FakeTransport respond(final String uri, final int status, final String body, final String... headers) {
		return respond(uri, status, body.getBytes(StandardCharsets.UTF_8), headers);
	}

	FakeTransport respond(final String uri, final int status, final byte[] body, final String... headers) {
		final Map<String, List<String>> headerMap = new HashMap<>();
		for (int i = 0; i + 1 < headers.length; i += 2) {
			headerMap.put(headers[i], List.of(headers[i + 1]));
		}
		fixtures.put(URI.create(uri), new Fixture(status, headerMap, body));
		return this;
	}

	void onRequest(final Runnable action) {
		onRequest = action;
	}

	List<HttpRequest> requests() {
		return requests;
	}

	@Override
	public <T> HttpResponse<T> send(final HttpRequest request, final BodyHandler<T> handler,
			final IProgressMonitor monitor) throws IOException {
		if (monitor != null && monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		requests.add(request);
		onRequest.run();
		final Fixture fixture = fixtures.get(request.uri());
		if (fixture == null) {
			throw new ConnectException("No fixture for " + request.uri()); //$NON-NLS-1$
		}
		final HttpHeaders headers = HttpHeaders.of(fixture.headers(), (_, _) -> true);
		final BodySubscriber<T> subscriber = handler
				.apply(new Info(fixture.status(), headers, HttpClient.Version.HTTP_1_1));
		subscriber.onSubscribe(new Flow.Subscription() {
			@Override
			public void request(final long n) {
				// all data is delivered at once
			}

			@Override
			public void cancel() {
				// nothing to stop
			}
		});
		subscriber.onNext(List.of(ByteBuffer.wrap(fixture.body())));
		subscriber.onComplete();
		try {
			return new Response<>(fixture.status(), request, headers,
					subscriber.getBody().toCompletableFuture().get(5, TimeUnit.SECONDS));
		} catch (final ExecutionException e) {
			if (e.getCause() instanceof final IOException cause) {
				throw cause;
			}
			throw new IOException(e.getCause());
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IOException(e);
		} catch (final TimeoutException e) {
			throw new IOException(e);
		}
	}
}
