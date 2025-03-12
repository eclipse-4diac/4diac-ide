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

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmParseUtil;
import org.eclipse.xtext.parser.IParseResult;

public class VarDeclarationSearchSupport extends StructuredTextSearchSupport {
	private final VarDeclaration varDeclaration;
	private IParseResult parseResultType;
	private IParseResult parseResult;

	public VarDeclarationSearchSupport(final VarDeclaration varDeclaration) {
		this.varDeclaration = varDeclaration;
	}

	@Override
	public Set<String> getImportedNamespaces() {
		final Set<String> result = super.getImportedNamespaces();
		if (!PackageNameHelper.getPackageName(varDeclaration.getType()).isEmpty()) {
			result.add(PackageNameHelper.getFullTypeName(varDeclaration.getType()));
		}
		return result;
	}

	@Override
	public Stream<EObject> prepare() {
		return Stream.of(prepareResultType(), prepareInitialValue()).filter(Objects::nonNull)
				.map(IParseResult::getRootASTElement);
	}

	protected IParseResult prepareResultType() {
		if (parseResultType == null && varDeclaration.isArray()) {
			parseResultType = STAlgorithmParseUtil.parseTypeDeclaration(varDeclaration.getFullTypeName(),
					varDeclaration);
		}
		return parseResultType;
	}

	protected IParseResult prepareInitialValue() {
		if (parseResult == null && varDeclaration.getValue() != null && varDeclaration.getValue().getValue() != null
				&& !varDeclaration.getValue().getValue().isBlank()) {
			parseResult = STAlgorithmParseUtil.parseInitializerExpression(varDeclaration.getValue().getValue(), null,
					varDeclaration);
		}
		return parseResult;
	}

	@Override
	public boolean isIncompleteResult() {
		return (parseResultType != null && parseResultType.hasSyntaxErrors())
				|| (parseResult != null && parseResult.hasSyntaxErrors());
	}
}
