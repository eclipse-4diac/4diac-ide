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
package org.eclipse.fordiac.ide.model.ui.widgets;

import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.jface.fieldassist.ContentProposal;

public class ImportTypeSelectionProposalProvider extends TypeSelectionProposalProvider {

	private final Supplier<? extends EObject> supplier;
	private final BiFunction<TypeLibrary, String, ? extends TypeEntry> typeResolver;

	public ImportTypeSelectionProposalProvider(final Supplier<? extends EObject> supplier,
			final BiFunction<TypeLibrary, String, ? extends TypeEntry> typeResolver,
			final ITypeSelectionContentProvider contentProvider) {
		super(() -> TypeLibraryManager.INSTANCE.getTypeLibraryFromContext(supplier.get()), contentProvider);
		this.supplier = supplier;
		this.typeResolver = typeResolver;
	}

	public ImportTypeSelectionProposalProvider(final Supplier<? extends EObject> supplier,
			final BiFunction<TypeLibrary, String, ? extends TypeEntry> typeResolver,
			final ITypeSelectionContentProvider contentProvider, final Predicate<TypeEntry> additionalFilter) {
		super(() -> TypeLibraryManager.INSTANCE.getTypeLibraryFromContext(supplier.get()), contentProvider,
				additionalFilter);
		this.supplier = supplier;
		this.typeResolver = typeResolver;
	}

	@Override
	protected ContentProposal createProposal(final TypeEntry typeEntry) {
		final TypeEntry resolvedEntry = ImportHelper.resolveImport(typeEntry.getTypeName(), supplier.get(),
				name -> typeResolver.apply(getTypeLibrary(), name), name -> null);
		if (resolvedEntry == null) { // no import yet -> use unqualified name and add import
			return new ImportContentProposal(typeEntry.getTypeName(), typeEntry.getTypeName(),
					typeEntry.getFullTypeName(), typeEntry.getFullTypeName());
		}
		if (resolvedEntry == typeEntry) { // already imported -> use unqualified name
			return new ContentProposal(typeEntry.getTypeName(), typeEntry.getTypeName(), typeEntry.getFullTypeName());
		}
		// different import -> use FQN
		return new ContentProposal(typeEntry.getFullTypeName(), typeEntry.getTypeName(), typeEntry.getFullTypeName());
	}
}
