/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.eval.st;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorPrepareException;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock;

@SuppressWarnings("java:S1452")
public abstract class STCallableEvaluator extends StructuredTextEvaluator {
	private Variable<?> returnVariable;

	protected STCallableEvaluator(final String name, final Variable<?> context, final Iterable<Variable<?>> variables,
			final Evaluator parent) {
		super(name, context, variables, parent);
	}

	protected void prepareCallableVariables(final List<? extends STVarDeclarationBlock> variables,
			final String returnVariableName, final DataType returnType) throws EvaluatorException {
		try {
			for (final STVarDeclarationBlock block : variables) {
				if (isInput(block)) {
					for (final STVarDeclaration varDeclaration : block.getVarDeclarations()) {
						if (!getVariables().containsKey(varDeclaration.getName())
								&& varDeclaration.getCount().isEmpty()) {
							evaluateVariableInitialization(varDeclaration);
						}
					}
				}
			}
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new EvaluatorPrepareException(Messages.STCallableEvaluator_InterruptedInInitializer, e, this);
		}
		if (returnType != null) {
			returnVariable = getVariables().computeIfAbsent(returnVariableName,
					name -> VariableOperations.newVariable(name, returnType));
		}
	}

	protected void evaluateCallableVariables(final List<? extends STVarDeclarationBlock> variables)
			throws EvaluatorException, InterruptedException {
		for (final STVarDeclarationBlock block : variables) {
			if (!isInput(block)) {
				for (final STVarDeclaration varDeclaration : block.getVarDeclarations()) {
					final Variable<?> variable = getVariables().get(varDeclaration.getName());
					if (variable != null) {
						evaluateInitializerExpression(variable, varDeclaration.getDefaultValue());
					} else {
						evaluateVariableInitialization(varDeclaration);
					}
				}
			}
		}
		variables.stream().map(STVarDeclarationBlock::getVarDeclarations).flatMap(List::stream)
				.filter(varDeclaration -> !getVariables().containsKey(varDeclaration.getName()))
				.forEach(varDeclaration -> {
					throw new IllegalArgumentException(
							MessageFormat.format(Messages.STCallableEvaluator_MissingArgument, varDeclaration.getName(),
									getSourceElement().getName()));
				});
	}

	protected static boolean isInput(final STVarDeclarationBlock block) {
		return block instanceof STVarInputDeclarationBlock || block instanceof STVarInOutDeclarationBlock;
	}

	@Override
	public abstract ICallable getSourceElement();

	public Variable<?> getReturnVariable() {
		return returnVariable;
	}
}
