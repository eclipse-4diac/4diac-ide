/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.scoping;

import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;

public class STCoreImportNormalizer extends ImportNormalizer {

	public STCoreImportNormalizer(final QualifiedName importedNamespace, final boolean wildCard,
			final boolean ignoreCase) {
		super(importedNamespace, wildCard, ignoreCase);
	}

	@Override
	public QualifiedName deresolve(final QualifiedName fullyQualifiedName) {
		final QualifiedName result = super.deresolve(fullyQualifiedName);
		if (result != null) {
			return result;
		}
		final QualifiedName importedNamespacePrefix = getImportedNamespacePrefix();
		if (!hasWildCard() && importedNamespacePrefix.getSegmentCount() < fullyQualifiedName.getSegmentCount()
				&& (isIgnoreCase() ? fullyQualifiedName.startsWithIgnoreCase(importedNamespacePrefix)
						: fullyQualifiedName.startsWith(importedNamespacePrefix))) {
			return fullyQualifiedName.skipFirst(importedNamespacePrefix.getSegmentCount() - 1);
		}
		return null;
	}

	@Override
	public QualifiedName resolve(final QualifiedName relativeName) {
		final QualifiedName result = super.resolve(relativeName);
		if (result != null) {
			return result;
		}
		final QualifiedName importedNamespacePrefix = getImportedNamespacePrefix();
		if (!hasWildCard() && importedNamespacePrefix.getSegmentCount() > 1 && relativeName.getSegmentCount() > 1
				&& (isIgnoreCase()
						? importedNamespacePrefix.getLastSegment().equalsIgnoreCase(relativeName.getFirstSegment())
						: importedNamespacePrefix.getLastSegment().equals(relativeName.getFirstSegment()))) {
			return importedNamespacePrefix.skipLast(1).append(relativeName);
		}
		return null;
	}
}
