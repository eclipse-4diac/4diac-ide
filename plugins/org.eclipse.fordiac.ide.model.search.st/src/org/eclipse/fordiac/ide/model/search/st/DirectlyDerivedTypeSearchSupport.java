/**
 * Copyright (c) 2024, 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.search.st;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmParseUtil;
import org.eclipse.xtext.parser.IParseResult;

public class DirectlyDerivedTypeSearchSupport extends StructuredTextSearchSupport {
	private final DirectlyDerivedType directlyDerivedType;
	private IParseResult parseResult;

	public DirectlyDerivedTypeSearchSupport(final DirectlyDerivedType directlyDerivedType) {
		this.directlyDerivedType = directlyDerivedType;
	}

	@Override
	public Set<String> getImportedNamespaces() {
		final Set<String> result = super.getImportedNamespaces();
		if (!PackageNameHelper.getPackageName(directlyDerivedType.getBaseType()).isEmpty()) {
			result.add(PackageNameHelper.getFullTypeName(directlyDerivedType.getBaseType()));
		}
		return result;
	}

	@Override
	public Stream<EObject> prepare() {
		return Optional.ofNullable(prepareInitialValue()).map(IParseResult::getRootASTElement).stream();
	}

	protected IParseResult prepareInitialValue() {
		if (parseResult == null && directlyDerivedType.getInitialValue() != null
				&& !directlyDerivedType.getInitialValue().isBlank()) {
			parseResult = STAlgorithmParseUtil.parseInitializerExpression(directlyDerivedType.getInitialValue(),
					directlyDerivedType.getBaseType(), directlyDerivedType);
		}
		return parseResult;
	}

	@Override
	public boolean isIncompleteResult() {
		return parseResult != null && parseResult.hasSyntaxErrors();
	}
}
