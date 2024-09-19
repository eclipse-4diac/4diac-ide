/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.eval;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;

public final class EvaluatorCache implements AutoCloseable {

	private static final ThreadLocal<EvaluatorCache> threadCaches = ThreadLocal.withInitial(EvaluatorCache::new);

	private final AtomicInteger stackDepth = new AtomicInteger(0);

	private final Map<ITypedElement, Value> cachedInitialValues = new ConcurrentHashMap<>();
	private final Map<ITypedElement, INamedElement> cachedResultType = new ConcurrentHashMap<>();

	private EvaluatorCache() {
	}

	public static EvaluatorCache open() {
		final EvaluatorCache cache = threadCaches.get();
		cache.stackDepth.incrementAndGet();
		return cache;
	}

	@Override
	public void close() {
		if (stackDepth.decrementAndGet() == 0) {
			threadCaches.remove();
		}
	}

	public <K extends ITypedElement> Value computeInitialValueIfAbsent(final K key,
			final CacheFunction<? super K, ? extends Value> comp) throws EvaluatorException, InterruptedException {
		// cannot use computeIfAbsent due to recursive update
		// use optimistic computation and putIfAbsent instead
		final Value value = cachedInitialValues.get(key);
		if (value == null) {
			final Value newValue = comp.apply(key);
			if (newValue != null) {
				final Value oldValue = cachedInitialValues.putIfAbsent(key, newValue);
				if (oldValue != null) {
					return oldValue; // concurrent update -> discard computed value and return current value
				}
				return newValue;
			}
		}
		return value;
	}

	public <K extends ITypedElement> INamedElement computeResultTypeIfAbsent(final K key,
			final CacheFunction<? super K, ? extends INamedElement> comp)
			throws EvaluatorException, InterruptedException {
		// cannot use computeIfAbsent due to recursive update
		// use optimistic computation and putIfAbsent instead
		final INamedElement type = cachedResultType.get(key);
		if (type == null) {
			final INamedElement newType = comp.apply(key);
			if (newType != null) {
				final INamedElement oldType = cachedResultType.putIfAbsent(key, newType);
				if (oldType != null) {
					return oldType; // concurrent update -> discard computed value and return current value
				}
				return newType;
			}
		}
		return type;
	}

	@FunctionalInterface
	public interface CacheFunction<T, R> {

		/**
		 * Applies this function to the given argument.
		 *
		 * @param t the function argument
		 * @return the function result
		 */
		R apply(T t) throws EvaluatorException, InterruptedException;
	}
}
