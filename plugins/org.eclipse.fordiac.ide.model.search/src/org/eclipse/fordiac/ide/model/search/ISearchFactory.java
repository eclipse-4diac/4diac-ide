/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.search;

import java.util.Map;

import org.eclipse.fordiac.ide.model.search.impl.SearchFactoryRegistryImpl;

/**
 * A factory for search support classes
 */
public interface ISearchFactory {

	/**
	 * Create a new search support
	 *
	 * @param source The source
	 * @return The new search support or null if no applicable support could be
	 *         created
	 */
	ISearchSupport createSearchSupport(final Object source);

	/**
	 * Create a new search support
	 *
	 * @param <T>         The source type
	 * @param source      The source
	 * @param sourceClass The source class
	 * @return The new search support or null if no applicable support could be
	 *         created
	 */
	static <T extends Object> ISearchSupport createSearchSupport(final T source, final Class<? extends T> sourceClass) {
		final ISearchFactory factory = ISearchFactory.Registry.INSTANCE.getFactory(sourceClass);
		if (factory != null) {
			return factory.createSearchSupport(source);
		}
		return null;
	}

	interface Registry {
		ISearchFactory getFactory(final Class<?> sourceClass);

		ISearchFactory registerFactory(final Class<?> sourceClass, final ISearchFactory factory);

		Map<Class<?>, ISearchFactory> getClassToFactoryMap();

		ISearchFactory.Registry INSTANCE = new SearchFactoryRegistryImpl();
	}
}
