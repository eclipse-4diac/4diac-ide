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
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmParseUtil;
import org.eclipse.xtext.parser.IParseResult;

public class ECTransitionSearchSupport extends StructuredTextSearchSupport {
	private final ECTransition transition;
	private IParseResult parseResult;

	public ECTransitionSearchSupport(final ECTransition transition) {
		this.transition = transition;
	}

	@Override
	public Stream<EObject> prepare() {
		return Optional.ofNullable(prepareConditionExpression()).map(IParseResult::getRootASTElement).stream();
	}

	protected IParseResult prepareConditionExpression() {
		if (parseResult == null && transition.getConditionExpression() != null
				&& !transition.getConditionExpression().isBlank()) {
			parseResult = STAlgorithmParseUtil.parseExpression(transition.getConditionExpression(),
					ElementaryTypes.BOOL, transition);
		}
		return parseResult;
	}

	@Override
	public boolean isIncompleteResult() {
		return parseResult != null && parseResult.hasSyntaxErrors();
	}
}
