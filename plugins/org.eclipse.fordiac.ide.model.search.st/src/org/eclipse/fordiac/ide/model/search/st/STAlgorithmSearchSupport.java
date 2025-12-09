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
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmParseUtil;
import org.eclipse.xtext.parser.IParseResult;

public class STAlgorithmSearchSupport extends StructuredTextSearchSupport {
	private final STAlgorithm algorithm;
	private IParseResult parseResult;

	public STAlgorithmSearchSupport(final STAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	@Override
	public Stream<EObject> prepare() {
		return Optional.ofNullable(prepareAlgorithm()).map(IParseResult::getRootASTElement).stream();
	}

	protected IParseResult prepareAlgorithm() {
		if (parseResult == null && algorithm.getText() != null && !algorithm.getText().isBlank()) {
			parseResult = STAlgorithmParseUtil.parseAlgorithm(algorithm);
		}
		return parseResult;
	}

	@Override
	public boolean isIncompleteResult() {
		return parseResult != null && parseResult.hasSyntaxErrors();
	}
}
