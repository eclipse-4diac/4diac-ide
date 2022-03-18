/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.export.language.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory;
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory.Registry;

public class LanguageSupportRegistryImpl implements Registry {
	final Map<String, Map<Class<?>, ILanguageSupportFactory>> filterClassToFactoryMap = new ConcurrentHashMap<>();

	@Override
	public ILanguageSupportFactory getFactory(final String filter, final Class<?> sourceClass) {
		final var classToFactoryMap = filterClassToFactoryMap.get(filter);
		if (classToFactoryMap != null) {
			return classToFactoryMap.get(sourceClass);
		}
		return null;
	}

	@Override
	public ILanguageSupportFactory registerFactory(final String filter, final Class<?> sourceClass,
			final ILanguageSupportFactory factory) {
		final var classToFactoryMap = ILanguageSupportFactory.Registry.INSTANCE.getFilterClassToFactoryMap()
				.computeIfAbsent(filter, f -> new ConcurrentHashMap<>());
		return classToFactoryMap.put(sourceClass, factory);
	}

	@Override
	public Map<String, Map<Class<?>, ILanguageSupportFactory>> getFilterClassToFactoryMap() {
		return filterClassToFactoryMap;
	}
}
