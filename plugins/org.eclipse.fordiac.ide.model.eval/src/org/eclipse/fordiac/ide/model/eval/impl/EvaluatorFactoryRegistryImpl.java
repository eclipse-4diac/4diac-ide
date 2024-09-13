/**
 * Copyright (c) 2022, 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.eval.impl;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;

public class EvaluatorFactoryRegistryImpl implements EvaluatorFactory.Registry {
	private final Map<String, Map<Class<?>, EvaluatorFactory>> variantClassToFactoryMap = new ConcurrentHashMap<>();

	@Override
	public EvaluatorFactory getFactory(final String variant, final Class<?> sourceClass) {
		final Map<Class<?>, EvaluatorFactory> classToFactoryMap = variantClassToFactoryMap.get(variant);
		if (classToFactoryMap != null) {
			return classToFactoryMap.get(sourceClass);
		}
		return null;
	}

	@Override
	public EvaluatorFactory registerFactory(final String variant, final Class<?> sourceClass,
			final EvaluatorFactory factory) {
		return this.variantClassToFactoryMap
				.computeIfAbsent(Objects.requireNonNullElse(variant, EvaluatorFactory.DEFAULT_VARIANT),
						unused -> new ConcurrentHashMap<>())
				.put(sourceClass, factory);
	}

	@Override
	public Map<String, Map<Class<?>, EvaluatorFactory>> getVariantClassToFactoryMap() {
		return variantClassToFactoryMap;
	}
}
