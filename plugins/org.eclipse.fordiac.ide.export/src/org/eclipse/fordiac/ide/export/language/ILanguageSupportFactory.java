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
package org.eclipse.fordiac.ide.export.language;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.export.language.impl.LanguageSupportRegistryImpl;

public interface ILanguageSupportFactory {
	ILanguageSupport createLanguageSupport(final Object source);

	static <T extends EObject> ILanguageSupport createLanguageSupport(final String filter, final T source) {
		final ILanguageSupportFactory factory = Registry.INSTANCE.getFactory(filter,
				source.eClass().getInstanceClass());
		if (factory != null) {
			return factory.createLanguageSupport(source);
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
