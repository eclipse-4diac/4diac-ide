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

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.typelibrary.GlobalConstantsEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.jface.fieldassist.ContentProposal;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;

public class ImportSelectionProposalProvider implements IContentProposalProvider {

	private final Supplier<TypeLibrary> supplier;

	public ImportSelectionProposalProvider(final Supplier<TypeLibrary> supplier) {
		this.supplier = supplier;
	}

	@Override
	public IContentProposal[] getProposals(final String contents, final int position) {
		final TypeLibrary typeLibrary = supplier.get();
		if (typeLibrary != null) {
			return Stream
					.of(getImportableTypeNames(typeLibrary), getGlobalConstantImportableNames(typeLibrary),
							getWildcardImports(typeLibrary))
					.flatMap(Function.identity()).distinct().filter(proposal -> matches(proposal, contents, position))
					.map(this::createProposal).sorted(Comparator.comparing(IContentProposal::getLabel))
					.toArray(IContentProposal[]::new);
		}
		return new IContentProposal[0];
	}

	protected static Stream<String> getImportableTypeNames(final TypeLibrary typeLibrary) {
		return Stream
				.of(typeLibrary.getDataTypeLibrary().getDerivedDataTypes(), typeLibrary.getAdapterTypes().values(),
						typeLibrary.getFbTypes().values(), typeLibrary.getAttributeTypes().values())
				.flatMap(Collection::stream).map(TypeEntry::getFullTypeName)
				.filter(fullName -> fullName.contains(PackageNameHelper.PACKAGE_NAME_DELIMITER)).distinct();
	}

	protected static Stream<String> getGlobalConstantImportableNames(final TypeLibrary typeLibrary) {
		return typeLibrary.getGlobalConstants().values().stream().map(GlobalConstantsEntry::getType)
				.filter(Objects::nonNull).flatMap(ImportSelectionProposalProvider::getGlobalConstantImportableNames);
	}

	protected static Stream<String> getGlobalConstantImportableNames(final GlobalConstants globalConstants) {
		final String packageName = PackageNameHelper.getPackageName(globalConstants);
		if (packageName.isEmpty()) {
			return Stream.empty();
		}
		return globalConstants.getConstants().stream()
				.map(constant -> packageName + ImportHelper.PACKAGE_NAME_DELIMITER + constant.getName());
	}

	protected static Stream<String> getWildcardImports(final TypeLibrary typeLibrary) {
		return typeLibrary.getPackages().stream().map(packageName -> packageName + ImportHelper.WILDCARD_IMPORT_SUFFIX);
	}

	protected static boolean matches(final String name, final String prefix, final int position) {
		return name.regionMatches(true, 0, prefix, 0, position);
	}

	protected ContentProposal createProposal(final String importedNamespace) {
		return new ContentProposal(importedNamespace);
	}
}
