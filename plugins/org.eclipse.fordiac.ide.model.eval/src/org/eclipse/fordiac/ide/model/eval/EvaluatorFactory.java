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
package org.eclipse.fordiac.ide.model.eval;

import java.util.Map;

import org.eclipse.fordiac.ide.model.eval.impl.EvaluatorFactoryRegistryImpl;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;

public interface EvaluatorFactory {

	/**
	 * The default evaluator variant
	 */
	String DEFAULT_VARIANT = "default"; //$NON-NLS-1$

	/**
	 * Check whether this factory can handle the given source
	 *
	 * @param source The source
	 * @return true if this factory can handle the source, false otherwise
	 */
	default boolean canHandle(final Object source) {
		return true;
	}

	/**
	 * Create a new evaluator
	 *
	 * @param source    The source
	 * @param context   The context
	 * @param variables The variables
	 * @param parent    The parent evaluator
	 * @return The new evaluator or null if no applicable evaluator could be created
	 */
	Evaluator createEvaluator(final Object source, final Variable<?> context, final Iterable<Variable<?>> variables,
			final Evaluator parent);

	/**
	 * Create a new evaluator
	 *
	 * @param <T>         The source type
	 * @param source      The source
	 * @param sourceClass The source class
	 * @param context     The context
	 * @param variables   The variables
	 * @param parent      The parent evaluator
	 * @return The new evaluator or null if no applicable evaluator could be created
	 */
	static <T extends Object> Evaluator createEvaluator(final T source, final Class<? extends T> sourceClass,
			final Variable<?> context, final Iterable<Variable<?>> variables, final Evaluator parent) {
		return createEvaluator(source, sourceClass, EvaluatorFactory.DEFAULT_VARIANT, context, variables, parent);
	}

	/**
	 * Create a new evaluator
	 *
	 * @param <T>         The source type
	 * @param source      The source
	 * @param sourceClass The source class
	 * @param variant     The evaluator variant
	 * @param context     The context
	 * @param variables   The variables
	 * @param parent      The parent evaluator
	 * @return The new evaluator or null if no applicable evaluator could be created
	 */
	static <T extends Object> Evaluator createEvaluator(final T source, final Class<? extends T> sourceClass,
			final String variant, final Variable<?> context, final Iterable<Variable<?>> variables,
			final Evaluator parent) {
		final EvaluatorFactory factory = EvaluatorFactory.Registry.INSTANCE.getFactory(variant, sourceClass);
		if (factory != null) {
			return factory.createEvaluator(source, context, variables, parent);
		}
		return null;
	}

	interface Registry {
		EvaluatorFactory getFactory(final String variant, final Class<?> sourceClass);

		EvaluatorFactory registerFactory(final String variant, final Class<?> sourceClass,
				final EvaluatorFactory factory);

		Map<String, Map<Class<?>, EvaluatorFactory>> getVariantClassToFactoryMap();

		EvaluatorFactory.Registry INSTANCE = new EvaluatorFactoryRegistryImpl();
	}
}
