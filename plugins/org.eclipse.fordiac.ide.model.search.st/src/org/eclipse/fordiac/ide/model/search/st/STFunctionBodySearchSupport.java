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
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionParseUtil2;
import org.eclipse.xtext.parser.IParseResult;

public class STFunctionBodySearchSupport extends StructuredTextSearchSupport {
	private final STFunctionBody body;
	private IParseResult parseResult;

	public STFunctionBodySearchSupport(final STFunctionBody body) {
		this.body = body;
	}

	@Override
	public Stream<EObject> prepare() {
		return Optional.ofNullable(prepareAlgorithm()).map(IParseResult::getRootASTElement).stream();
	}

	protected IParseResult prepareAlgorithm() {
		if (parseResult == null && body.getText() != null && !body.getText().isBlank()) {
			parseResult = STFunctionParseUtil2.parseFunctionBody(body);
		}
		return parseResult;
	}

	@Override
	public boolean isIncompleteResult() {
		return parseResult != null && parseResult.hasSyntaxErrors();
	}
}
