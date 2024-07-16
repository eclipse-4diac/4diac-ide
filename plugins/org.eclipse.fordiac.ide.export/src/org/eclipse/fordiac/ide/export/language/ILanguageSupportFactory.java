/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.export.language;

import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.export.language.impl.LanguageSupportRegistryImpl;

public interface ILanguageSupportFactory {
	ILanguageSupport createLanguageSupport(final Object source, Map<?, ?> options);

	static <T extends EObject> ILanguageSupport createLanguageSupport(final String filter, final T source) {
		return createLanguageSupport(filter, source, Collections.emptyMap());
	}

	static <T extends EObject> ILanguageSupport createLanguageSupport(final String filter, final T source,
			final Map<?, ?> options) {
		final ILanguageSupportFactory factory = Registry.INSTANCE.getFactory(filter,
				source.eClass().getInstanceClass());
		if (factory != null) {
			return factory.createLanguageSupport(source, options);
		}
		return null;
	}

	public interface Registry {
		ILanguageSupportFactory getFactory(String filter, final Class<?> sourceClass);

		ILanguageSupportFactory registerFactory(String filter, final Class<?> sourceClass,
				ILanguageSupportFactory factory);

		Map<String, Map<Class<?>, ILanguageSupportFactory>> getFilterClassToFactoryMap();

		Registry INSTANCE = new LanguageSupportRegistryImpl();
	}
}
