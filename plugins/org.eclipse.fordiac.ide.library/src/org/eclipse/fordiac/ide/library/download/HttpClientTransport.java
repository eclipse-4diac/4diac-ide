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
import java.io.InterruptedIOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpTimeoutException;
import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.library.Messages;

/** Sends requests with {@link HttpClient}, see {@link LibraryPackageTransport#getDefault()}. */
final class HttpClientTransport implements LibraryPackageTransport {

	private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(10);
	private static final long READ_TIMEOUT_SECONDS = 30;
	private static final long POLL_INTERVAL_MILLIS = 100;

	// after the constants used by the constructor
	static final HttpClientTransport INSTANCE = new HttpClientTransport();

	private final HttpClient client = HttpClient.newBuilder().connectTimeout(CONNECT_TIMEOUT)
			.followRedirects(HttpClient.Redirect.NEVER).proxy(new DefaultProxySelector())
			.authenticator(new ProxyAuthenticator()).build();

	private HttpClientTransport() {
	}

	@Override
	public <T> HttpResponse<T> send(final HttpRequest request, final BodyHandler<T> handler,
			final IProgressMonitor monitor) throws IOException {
		final AtomicLong lastActivity = new AtomicLong(System.nanoTime());
		final CompletableFuture<HttpResponse<T>> response = client.sendAsync(request,
				info -> new ActivitySubscriber<>(handler.apply(info), lastActivity));
		try {
			while (true) {
				try {
					final HttpResponse<T> completed = response.get(POLL_INTERVAL_MILLIS, TimeUnit.MILLISECONDS);
					if (monitor != null && monitor.isCanceled()) {
						throw new OperationCanceledException();
					}
					return completed;
				} catch (final TimeoutException e) {
					if (monitor != null && monitor.isCanceled()) {
						response.cancel(true);
						throw new OperationCanceledException();
					}
					if (System.nanoTime() - lastActivity.get() > TimeUnit.SECONDS.toNanos(READ_TIMEOUT_SECONDS)) {
						response.cancel(true);
						throw new HttpTimeoutException(MessageFormat.format(Messages.LibraryPackageDownloader_ReadTimeout,
								AbstractLibraryPackageDownloader.describe(request.uri()),
								Long.valueOf(READ_TIMEOUT_SECONDS)));
					}
				}
			}
		} catch (final InterruptedException e) {
			response.cancel(true);
			Thread.currentThread().interrupt();
			throw new InterruptedIOException();
		} catch (final ExecutionException e) {
			final Throwable cause = e.getCause();
			if (cause instanceof final LimitingBodySubscriber.LimitExceededException exceeded) {
				throw exceeded;
			}
			// The JDK can handle an authentication challenge before invoking the body handler.
			if (cause instanceof ServerAuthenticationRequiredException
					|| (cause instanceof IOException && (cause.getCause() instanceof ServerAuthenticationRequiredException
							|| "WWW-Authenticate header missing for response code 401".equals(cause.getMessage())))) { //$NON-NLS-1$
				throw new LibraryPackageRequestException(request.uri(), HttpURLConnection.HTTP_UNAUTHORIZED);
			}
			if (cause instanceof IOException
					&& "Proxy-Authenticate header missing for response code 407".equals(cause.getMessage())) { //$NON-NLS-1$
				throw new LibraryPackageRequestException(request.uri(), HttpURLConnection.HTTP_PROXY_AUTH);
			}
			throw new IOException(MessageFormat.format(Messages.LibraryPackageDownloader_TransportError,
					AbstractLibraryPackageDownloader.describe(request.uri()), cause.getClass().getSimpleName()));
		}
	}

	/** Records when data was last received, for the read timeout. */
	private record ActivitySubscriber<T>(BodySubscriber<T> downstream, AtomicLong lastActivity)
			implements BodySubscriber<T> {

		@Override
		public CompletionStage<T> getBody() {
			return downstream.getBody();
		}

		@Override
		public void onSubscribe(final Flow.Subscription subscription) {
			lastActivity.set(System.nanoTime());
			downstream.onSubscribe(subscription);
		}

		@Override
		public void onNext(final List<ByteBuffer> item) {
			lastActivity.set(System.nanoTime());
			downstream.onNext(item);
		}

		@Override
		public void onError(final Throwable throwable) {
			downstream.onError(throwable);
		}

		@Override
		public void onComplete() {
			downstream.onComplete();
		}
	}

	/**
	 * Asks the default proxy selector on every request, as the platform may replace
	 * it after the client was built.
	 */
	private static final class DefaultProxySelector extends ProxySelector {

		@Override
		public List<Proxy> select(final URI uri) {
			final ProxySelector selector = ProxySelector.getDefault();
			return selector != null ? selector.select(uri) : List.of(Proxy.NO_PROXY);
		}

		@Override
		public void connectFailed(final URI uri, final SocketAddress address, final IOException e) {
			final ProxySelector selector = ProxySelector.getDefault();
			if (selector != null) {
				selector.connectFailed(uri, address, e);
			}
		}
	}

	/** Distinguishes a server authentication challenge from proxy authentication. */
	private static final class ServerAuthenticationRequiredException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	/**
	 * Passes proxy authentication on to the default authenticator, as
	 * {@link java.net.HttpURLConnection} does. Servers get no credentials from
	 * here; downloaders add them explicitly.
	 */
	private static final class ProxyAuthenticator extends Authenticator {

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			if (getRequestorType() == RequestorType.SERVER) {
				throw new ServerAuthenticationRequiredException();
			}
			return Authenticator.requestPasswordAuthentication(getRequestingHost(), getRequestingSite(),
					getRequestingPort(), getRequestingProtocol(), getRequestingPrompt(), getRequestingScheme(),
					getRequestingURL(), RequestorType.PROXY);
		}
	}
}
