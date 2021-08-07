/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.api;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class LambdaVisitor<A> implements Function<Object, A> {

	private final Map<Class<?>, Function<Object, A>> fMap = new HashMap<>();

	public <B> Acceptor<A, B> on(Class<B> clazz) {
		return new Acceptor<>(this, clazz);
	}

	@Override
	public A apply(Object o) {
		return fMap.entrySet().stream().parallel()
				.filter(e -> e.getKey().isInstance(o))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Cannot find any instance of: " + o.getClass().toString())) //$NON-NLS-1$
				.getValue()
				.apply(o)
				;
	}

	public static class Acceptor<A, B> {

		private final LambdaVisitor<A> visitor;
		private final Class<B> clazz;

		public Acceptor(LambdaVisitor<A> visitor, Class<B> clazz) {
			this.visitor = visitor;
			this.clazz = clazz;
		}

		@SuppressWarnings("unchecked")
		public LambdaVisitor<A> then(Function<B, A> f) {
			visitor.fMap.put(clazz, (Function<Object, A>) f);
			return visitor;
		}
	}
}
