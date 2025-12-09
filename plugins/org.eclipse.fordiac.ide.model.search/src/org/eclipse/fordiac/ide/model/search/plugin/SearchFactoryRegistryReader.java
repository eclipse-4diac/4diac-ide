/**
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
 */
package org.eclipse.fordiac.ide.model.search.plugin;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.fordiac.ide.model.search.ISearchFactory;
import org.eclipse.fordiac.ide.model.search.Messages;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class SearchFactoryRegistryReader {
	private static final String FACTORY_ID = "factory"; //$NON-NLS-1$
	private static final String ATT_CLASS = "class"; //$NON-NLS-1$
	private static final String ATT_TYPE = "type"; //$NON-NLS-1$
	private static final String CHILD_SOURCE = "source"; //$NON-NLS-1$

	public static void readRegistry() {
		final IConfigurationElement[] config = RegistryFactory.getRegistry()
				.getConfigurationElementsFor(SearchPlugin.PLUGIN_ID, FACTORY_ID);
		for (final IConfigurationElement element : config) {
			final ISearchFactory factory;
			try {
				factory = (ISearchFactory) element.createExecutableExtension(ATT_CLASS);
			} catch (final Exception e) {
				log(element, Messages.SearchFactoryRegistryReader_CannotCreateInstance, e);
				continue;
			}
			final IConfigurationElement[] sources = element.getChildren(CHILD_SOURCE);
			for (final IConfigurationElement source : sources) {
				final String type = source.getAttribute(ATT_TYPE);
				final Class<?> sourceClass;
				try {
					sourceClass = CommonPlugin.loadClass(source.getDeclaringExtension().getContributor().getName(),
							type);
				} catch (final ClassNotFoundException e) {
					log(source, Messages.SearchFactoryRegistryReader_SourceInvalidClass, e);
					continue;
				}
				final ISearchFactory previousFactory = ISearchFactory.Registry.INSTANCE.registerFactory(sourceClass,
						factory);
				if (previousFactory != null) {
					log(source, MessageFormat.format(Messages.SearchFactoryRegistryReader_AlreadyRegistered,
							previousFactory.getClass().getName(), sourceClass.getName()));
				}
			}
		}
	}

	private static void log(final IConfigurationElement element, final String message) {
		FordiacLogHelper.logError(MessageFormat.format(Messages.SearchFactoryRegistryReader_LogMessage, message,
				element.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
				element.getDeclaringExtension().getContributor().getName()));
	}

	private static void log(final IConfigurationElement element, final String message, final Throwable t) {
		FordiacLogHelper.logError(MessageFormat.format(Messages.SearchFactoryRegistryReader_LogMessage, message,
				element.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
				element.getDeclaringExtension().getContributor().getName()), t);
	}

	private SearchFactoryRegistryReader() {
		throw new UnsupportedOperationException();
	}
}
