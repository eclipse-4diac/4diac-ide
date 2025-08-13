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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.xtext.ide.serializer.hooks.IReferenceUpdaterContext;
import org.eclipse.xtext.ide.serializer.impl.EObjectDescriptionDeltaProvider.Delta;
import org.eclipse.xtext.ide.serializer.impl.EObjectDescriptionDeltaProvider.Deltas;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STCoreImportUpdater {

	@Inject
	private IQualifiedNameConverter nameConverter;

	@Inject
	private IQualifiedNameProvider nameProvider;

	public void updateImports(final IReferenceUpdaterContext context) {
		if (context.getResource().getParseResult().getRootASTElement() instanceof final STSource source) {
			updateImports(context.getEObjectDescriptionDeltas(), source, STCoreImportUpdater::updateImport);
		}
		if (context.getResource() instanceof final LibraryElementXtextResource libResource) {
			updateImports(context.getEObjectDescriptionDeltas(), libResource.getInternalLibraryElement(),
					STCoreImportUpdater::updateImport);
		}
	}

	protected static void updateImport(final Import imp, final String importedNamespace) {
		if (importedNamespace == null || importedNamespace.isBlank()) {
			EcoreUtil.remove(imp);
		}
		imp.setImportedNamespace(importedNamespace);
	}

	public void updateImports(final Deltas deltas, final STSource source,
			final BiConsumer<? super Import, String> updater) {
		final QualifiedName packageName = getPackageName(source);
		source.eContents().stream().filter(STImport.class::isInstance).map(STImport.class::cast)
				.forEach(imp -> updateImport(deltas, packageName, imp, updater));
	}

	public void updateImports(final Deltas deltas, final LibraryElement libraryElement,
			final BiConsumer<? super Import, String> updater) {
		final QualifiedName packageName = getPackageName(libraryElement);
		List.copyOf(ImportHelper.getImports(libraryElement))
				.forEach(imp -> updateImport(deltas, packageName, imp, updater));
	}

	protected void updateImport(final Deltas deltas, final QualifiedName packageName, final Import imp,
			final BiConsumer<? super Import, String> updater) {
		final QualifiedName imported = nameConverter.toQualifiedName(imp.getImportedNamespace());
		if (isWildcardImport(imported)) {
			return; // wildcard import needs no update
		}
		// find matching delta
		final Optional<Delta> delta = findDelta(deltas, imported);
		if (delta.isEmpty()) {
			return; // no delta for import
		}
		// find longest qualified name and set as imported namespace
		// or remove import
		updater.accept(imp, delta.flatMap(STCoreImportUpdater::findLongestQualifiedName)
				.filter(name -> !matchesPackageName(name, packageName)).map(nameConverter::toString).orElse(null));
	}

	protected static Optional<Delta> findDelta(final Deltas deltas, final QualifiedName imported) {
		return deltas.getDeltas().stream()
				.filter(candidate -> candidate.hasQualifiedNameChanged() && matchesImport(candidate, imported))
				.findFirst();
	}

	protected static boolean matchesImport(final Delta delta, final QualifiedName imported) {
		return delta.getSnapshot() != null && delta.getSnapshot().getDescriptions().stream()
				.anyMatch(desc -> desc.getQualifiedName().equals(imported));
	}

	protected static boolean matchesPackageName(final QualifiedName imported, final QualifiedName packageName) {
		return imported.skipLast(1).equalsIgnoreCase(packageName);
	}

	protected static boolean isWildcardImport(final QualifiedName imported) {
		return ImportHelper.WILDCARD_IMPORT.equals(imported.getLastSegment());
	}

	protected static Optional<QualifiedName> findLongestQualifiedName(final Delta delta) {
		return delta.getDescriptions().stream().map(IEObjectDescription::getQualifiedName)
				.max(Comparator.comparingInt(QualifiedName::getSegmentCount));
	}

	protected QualifiedName getPackageName(final STSource source) {
		final QualifiedName packageName = nameProvider.getFullyQualifiedName(source);
		if (packageName == null || packageName.isEmpty()) {
			return QualifiedName.EMPTY;
		}
		return packageName.skipLast(1);
	}

	protected QualifiedName getPackageName(final LibraryElement libraryElement) {
		final String packageName = PackageNameHelper.getPackageName(libraryElement);
		if (packageName.isBlank()) {
			return QualifiedName.EMPTY;
		}
		return nameConverter.toQualifiedName(packageName);
	}
}
