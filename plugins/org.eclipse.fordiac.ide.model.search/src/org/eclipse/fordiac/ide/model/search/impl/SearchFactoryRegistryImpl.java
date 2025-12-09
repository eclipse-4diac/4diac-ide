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
package org.eclipse.fordiac.ide.model.search.impl;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.fordiac.ide.model.search.ISearchFactory;

public class SearchFactoryRegistryImpl implements ISearchFactory.Registry {
	private final Map<Class<?>, ISearchFactory> classToFactoryMap = new ConcurrentHashMap<>();

	@Override
	public ISearchFactory getFactory(final Class<?> sourceClass) {
		return classToFactoryMap.get(sourceClass);
	}

	@Override
	public ISearchFactory registerFactory(final Class<?> sourceClass, final ISearchFactory factory) {
		return classToFactoryMap.put(sourceClass, factory);
	}

	@Override
	public Map<Class<?>, ISearchFactory> getClassToFactoryMap() {
		return Collections.unmodifiableMap(classToFactoryMap);
	}
}
