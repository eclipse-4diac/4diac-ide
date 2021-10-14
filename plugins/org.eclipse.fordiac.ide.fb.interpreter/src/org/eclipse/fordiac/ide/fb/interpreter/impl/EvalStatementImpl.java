/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.impl;

import java.util.function.Function;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.api.IStatementVisitor;
import org.eclipse.fordiac.ide.fb.interpreter.api.LambdaVisitor;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AssignmentStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IfStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Statement;

public class EvalStatementImpl implements IStatementVisitor {

	public EvalStatementImpl() {
		// Do nothing
	}

	public static Function<Object,Object> of(IStatementVisitor evaluateExpression) {
		return new LambdaVisitor<>()
				.on(AssignmentStatement.class).then(evaluateExpression::evaluate)
				.on(IfStatement.class).then(evaluateExpression::evaluate)
				;
	}

	@Override
	public void evaluateAllStatements(EList<Statement> listOfStatements) {
		final var evaluation = new EvalStatementImpl();
		for (final Statement statement : listOfStatements) {
			of(evaluation).apply(statement);
		}
	}

	@Override
	public AssignmentStatement evaluate(AssignmentStatement assigment) {
		final var result = EvaluateExpressionImpl.of().apply(assigment.getExpression());
		VariableSetVisitorImpl.setVariable(assigment.getVariable(), result);
		return assigment;
	}

	@Override
	public IfStatement evaluate(IfStatement ifAssigment) {
		final var result = EvaluateExpressionImpl.of().apply(ifAssigment.getExpression());
		if (result instanceof Boolean) {
			evaluateAllStatements(ifAssigment.getStatments().getStatements());
		} else {
			throw new IllegalArgumentException("The expression of the IfStatement does not return a Boolean type");		 //$NON-NLS-1$
		}
		return ifAssigment;
	}
}
