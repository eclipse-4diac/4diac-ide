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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.scoping;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.structuredtextcore.scoping.STCoreImportedNamespaceAwareLocalScopeProvider;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;

public class STFunctionImportedNamespaceAwareLocalScopeProvider extends STCoreImportedNamespaceAwareLocalScopeProvider {
	@Override
	protected List<ImportNormalizer> internalGetImportedNamespaceResolvers(final EObject context,
			final boolean ignoreCase) {
		final List<ImportNormalizer> result = super.internalGetImportedNamespaceResolvers(context, ignoreCase);
		if (context instanceof final STFunctionSource source && source.getName() != null) {
			final QualifiedName name = getQualifiedNameConverter().toQualifiedName(source.getName());
			if (name != null && !name.isEmpty()) {
				result.add(doCreateImportNormalizer(name, true, ignoreCase));
			}
		}
		return result;
	}
}
