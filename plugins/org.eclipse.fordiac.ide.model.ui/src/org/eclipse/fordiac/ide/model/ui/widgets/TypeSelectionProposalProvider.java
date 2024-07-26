/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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

import java.util.Comparator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.jface.fieldassist.ContentProposal;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;

public class TypeSelectionProposalProvider implements IContentProposalProvider {

	private final Supplier<TypeLibrary> supplier;
	private final ITypeSelectionContentProvider contentProvider;
	private final Predicate<TypeEntry> additionalFilter;

	public TypeSelectionProposalProvider(final Supplier<TypeLibrary> supplier,
			final ITypeSelectionContentProvider contentProvider) {
		this(supplier, contentProvider, entry -> true);
	}

	public TypeSelectionProposalProvider(final Supplier<TypeLibrary> supplier,
			final ITypeSelectionContentProvider contentProvider, final Predicate<TypeEntry> additionalFilter) {
		this.supplier = supplier;
		this.contentProvider = contentProvider;
		this.additionalFilter = additionalFilter;
	}

	@Override
	public IContentProposal[] getProposals(final String contents, final int position) {
		final TypeLibrary typeLibrary = supplier.get();
		contentProvider.getTypeEntries(typeLibrary).forEach(TypeEntry::getType);
		return Stream.concat(
				contentProvider.getTypes(typeLibrary).stream().filter(proposal -> matches(proposal.getName(), contents))
						.map(this::createProposal),
				contentProvider.getTypeEntries(typeLibrary).stream().filter(additionalFilter)
						.filter(proposal -> matches(proposal.getTypeName(), contents)).map(this::createProposal))
				.sorted(Comparator.comparing(IContentProposal::getLabel)).toArray(IContentProposal[]::new);
	}

	protected static boolean matches(final String name, final String contents) {
		return name.length() >= contents.length() && name.substring(0, contents.length()).equalsIgnoreCase(contents);
	}

	protected ContentProposal createProposal(final LibraryElement libraryElement) {
		return new ContentProposal(PackageNameHelper.getFullTypeName(libraryElement), libraryElement.getName(),
				PackageNameHelper.getFullTypeName(libraryElement));
	}

	protected ContentProposal createProposal(final TypeEntry typeEntry) {
		return new ContentProposal(typeEntry.getFullTypeName(), typeEntry.getTypeName(), typeEntry.getFullTypeName());
	}
}
