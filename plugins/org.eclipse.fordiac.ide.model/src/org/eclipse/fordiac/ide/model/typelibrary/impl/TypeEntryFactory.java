/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH, Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Alois Zoitl - initial API and implementation and/or initial documentation
 *    Martin Erich Jobst - add create type entry for classes
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.typelibrary.ITypeEntryCreator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;

public enum TypeEntryFactory {
	INSTANCE;

	private static final String PLUGIN_ID = "org.eclipse.fordiac.ide.model"; //$NON-NLS-1$
	private static final String TYPE_CREATION_EXT_POINT_ID = "TypeEntryCreator"; //$NON-NLS-1$

	/** An array of type entry creators */
	private final ITypeEntryCreator[] typeCreators;

	TypeEntryFactory() {
		typeCreators = loadTypeCreators();
	}

	public TypeEntry createTypeEntry(final IFile file) {
		for (final ITypeEntryCreator in : typeCreators) {
			if (in.canHandle(file)) {
				final TypeEntry entry = in.createTypeEntry();
				configureTypeEntry(entry, file);
				return entry;
			}
		}
		return null;
	}

	public TypeEntry createTypeEntry(final EClass eClass) {
		for (final ITypeEntryCreator in : typeCreators) {
			if (in.canHandle(eClass)) {
				return in.createTypeEntry();
			}
		}
		return null;
	}

	private static ITypeEntryCreator[] loadTypeCreators() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] elems = registry.getConfigurationElementsFor(PLUGIN_ID,
				TYPE_CREATION_EXT_POINT_ID);
		int numTypeCreators = 0;
		final ITypeEntryCreator[] typeCreators = new ITypeEntryCreator[elems.length];

		for (final IConfigurationElement elem : elems) {
			try {
				final Object object = elem.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof ITypeEntryCreator) {
					typeCreators[numTypeCreators] = (ITypeEntryCreator) object;
					numTypeCreators++;
				}
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
		return Arrays.copyOf(typeCreators, numTypeCreators);
	}

	private static void configureTypeEntry(final TypeEntry entry, final IFile file) {
		entry.setType(null);
		entry.setFile(file);
	}

}
