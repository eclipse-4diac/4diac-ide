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
package org.eclipse.fordiac.ide.structuredtextcore.validation;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextcore.Messages;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.inject.Inject;

public class STCoreImportValidator {

	public static final String WILDCARD = "*"; //$NON-NLS-1$

	@Inject
	private IQualifiedNameConverter nameConverter;

	public void validateImports(final String packageName, final List<? extends Import> imports,
			final Set<QualifiedName> usedTypes, final ValidationMessageAcceptor acceptor) {
		if (imports.isEmpty()) {
			return;
		}

		final QualifiedName packageQualifiedName = Strings.isEmpty(packageName) ? QualifiedName.EMPTY
				: nameConverter.toQualifiedName(packageName);

		imports.stream().forEach(imp -> validateImport(imp, packageQualifiedName, usedTypes, acceptor));
	}

	protected void validateImport(final Import imp, final QualifiedName packageName, final Set<QualifiedName> usedTypes,
			final ValidationMessageAcceptor acceptor) {
		final String importedNamespace = imp.getImportedNamespace();
		if (Strings.isEmpty(importedNamespace)) {
			return;
		}

		final QualifiedName qualifiedName = nameConverter.toQualifiedName(importedNamespace);
		if (qualifiedName == null || qualifiedName.isEmpty()) {
			return;
		}

		if (WILDCARD.equals(qualifiedName.getLastSegment())) {
			if (qualifiedName.getSegmentCount() <= 1) {
				acceptor.acceptError(
						MessageFormat.format(Messages.STCoreValidator_InvalidWildcardImport, importedNamespace), imp,
						LibraryElementPackage.eINSTANCE.getImport_ImportedNamespace(),
						ValidationMessageAcceptor.INSIGNIFICANT_INDEX, STCoreValidator.INVALID_IMPORT,
						importedNamespace);
			} else {
				acceptor.acceptWarning(
						MessageFormat.format(Messages.STCoreValidator_WildcardImportDiscouraged, importedNamespace),
						imp, LibraryElementPackage.eINSTANCE.getImport_ImportedNamespace(),
						ValidationMessageAcceptor.INSIGNIFICANT_INDEX, STCoreValidator.WILDCARD_IMPORT,
						importedNamespace);
			}
		} else if (isImplicitImport(qualifiedName, packageName) || !usedTypes.contains(qualifiedName)) {
			acceptor.acceptWarning(MessageFormat.format(Messages.STCoreValidator_UnusedImport, importedNamespace), imp,
					LibraryElementPackage.eINSTANCE.getImport_ImportedNamespace(),
					ValidationMessageAcceptor.INSIGNIFICANT_INDEX, STCoreValidator.UNUSED_IMPORT, importedNamespace);
		}
	}

	public static boolean isImplicitImport(final QualifiedName imported, final QualifiedName packageName) {
		return imported.getSegmentCount() <= 1 || imported.skipLast(1).equals(packageName);
	}
}
