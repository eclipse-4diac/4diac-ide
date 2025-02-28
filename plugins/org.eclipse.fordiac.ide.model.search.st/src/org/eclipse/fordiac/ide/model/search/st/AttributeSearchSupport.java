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
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmParseUtil;
import org.eclipse.xtext.parser.IParseResult;

public class AttributeSearchSupport extends StructuredTextSearchSupport {
	private final Attribute attribute;
	private IParseResult parseResult;

	public AttributeSearchSupport(final Attribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public Set<String> getImportedNamespaces() {
		final Set<String> result = super.getImportedNamespaces();
		if (attribute.getAttributeDeclaration() != null) {
			if (!PackageNameHelper.getPackageName(attribute.getAttributeDeclaration()).isEmpty()) {
				result.add(PackageNameHelper.getFullTypeName(attribute.getAttributeDeclaration()));
			}
		} else if (!PackageNameHelper.getPackageName(attribute.getType()).isEmpty()) {
			result.add(PackageNameHelper.getFullTypeName(attribute.getType()));
		}
		return result;
	}

	@Override
	public Stream<EObject> prepare() {
		return Optional.ofNullable(prepareValue()).map(IParseResult::getRootASTElement).stream();
	}

	protected IParseResult prepareValue() {
		if (parseResult == null && attribute.getType() instanceof AnyType && attribute.getValue() != null
				&& !attribute.getValue().isBlank()) {
			parseResult = STAlgorithmParseUtil.parseInitializerExpression(attribute.getValue(), attribute.getType(),
					attribute);
		}
		return parseResult;
	}

	@Override
	public boolean isIncompleteResult() {
		return parseResult != null && parseResult.hasSyntaxErrors();
	}
}
