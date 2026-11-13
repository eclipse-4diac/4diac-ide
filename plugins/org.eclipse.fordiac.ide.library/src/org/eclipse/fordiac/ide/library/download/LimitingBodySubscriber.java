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
import java.net.http.HttpResponse.BodySubscriber;
import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Flow;

import org.eclipse.fordiac.ide.library.Messages;

/**
 * Fails a response body that is larger than a limit, before the excess reaches
 * the downstream subscriber.
 */
final class LimitingBodySubscriber<T> implements BodySubscriber<T> {

	static final class LimitExceededException extends IOException {
		private static final long serialVersionUID = 1L;

		LimitExceededException(final String location, final long limit) {
			super(MessageFormat.format(Messages.LibraryPackageDownloader_ResponseTooLarge, location, Long.valueOf(limit)));
		}
	}

	private final BodySubscriber<T> downstream;
	private final long limit;
	private final String location;
	private Flow.Subscription subscription;
	private long received;
	private boolean exceeded;

	LimitingBodySubscriber(final BodySubscriber<T> downstream, final long limit, final String location) {
		this.downstream = downstream;
		this.limit = limit;
		this.location = location;
	}

	@Override
	public CompletionStage<T> getBody() {
		return downstream.getBody();
	}

	@Override
	public void onSubscribe(final Flow.Subscription newSubscription) {
		subscription = newSubscription;
		downstream.onSubscribe(newSubscription);
	}

	@Override
	public void onNext(final List<ByteBuffer> item) {
		if (exceeded) {
			return;
		}
		received += item.stream().mapToLong(ByteBuffer::remaining).sum();
		if (received > limit) {
			exceeded = true;
			subscription.cancel();
			downstream.onError(new LimitExceededException(location, limit));
			return;
		}
		downstream.onNext(item);
	}

	@Override
	public void onError(final Throwable throwable) {
		if (!exceeded) {
			downstream.onError(throwable);
		}
	}

	@Override
	public void onComplete() {
		if (!exceeded) {
			downstream.onComplete();
		}
	}
}
