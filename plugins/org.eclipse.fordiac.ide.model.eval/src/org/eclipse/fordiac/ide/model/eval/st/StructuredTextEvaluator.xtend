/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.eval.st

import java.util.Collection
import java.util.Map
import org.eclipse.fordiac.ide.model.eval.AbstractEvaluator
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.EvaluatorExitException
import org.eclipse.fordiac.ide.model.eval.value.BoolValue
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.eval.variable.ElementaryVariable
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AssignmentStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BinaryExpression
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BoolLiteral
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.CaseStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ContinueStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ExitStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Expression
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ForStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IfStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IntLiteral
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.LocalVariable
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.PrimaryVariable
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.RealLiteral
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.RepeatStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ReturnStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Statement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StatementList
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextAlgorithm
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.UnaryExpression
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.WhileStatement
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.parser.IParseResult

import static extension org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer
import static extension org.eclipse.fordiac.ide.model.eval.value.BoolValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LRealValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*
import static extension org.eclipse.fordiac.ide.model.structuredtext.util.StructuredTextParseUtil.*

final class StructuredTextEvaluator extends AbstractEvaluator {

	@Accessors final String name
	final String text
	final BaseFBType fbType
	final boolean singleExpression
	final Map<VarDeclaration, Variable> variables

	new(STAlgorithm alg, Collection<Variable> variables, Evaluator parent) {
		super(parent)
		this.fbType = alg.rootContainer as BaseFBType
		this.name = '''«fbType.name».«alg.name»'''
		this.text = alg.text
		this.singleExpression = false
		this.variables = variables.toMap[declaration]
	}

	new(String text, Collection<Variable> variables, BaseFBType fbType, Evaluator parent) {
		super(parent)
		this.name = "anonymous"
		this.text = text
		this.fbType = fbType
		this.singleExpression = true
		this.variables = variables.toMap[declaration]
	}

	override getVariables() {
		variables.values.unmodifiableView
	}

	override getSourceElement() {
		this.fbType
	}

	override Value evaluate() {
		val parseResult = parse()
		val root = parseResult.rootASTElement
		root.evaluate
	}

	def dispatch Value evaluate(StructuredTextAlgorithm alg) {
		alg.trap.evaluateStructuredTextAlgorithm
		null
	}

	def dispatch Value evaluate(Expression expr) {
		expr.trap.evaluateExpression
	}

	def private IParseResult parse() {
		val errors = newArrayList
		val parseResult = text.parse(singleExpression, name, fbType, errors)
		if (parseResult === null) {
			errors.forEach[error("Parse error: " + it)]
			throw new Exception("Parse error: " + errors.join(", "))
		}
		return parseResult
	}

	def private evaluateStructuredTextAlgorithm(StructuredTextAlgorithm alg) {
		alg.localVariables.filter(LocalVariable).forEach[evaluateLocalVariable]
		try {
			alg.statements.evaluateStatementList
		} catch (ReturnException e) {
			// return
		}
	}

	def private void evaluateLocalVariable(LocalVariable variable) {
		variables.put(variable, new ElementaryVariable(variable, variable.initialValue?.evaluateExpression))
	}

	def private void evaluateStatementList(StatementList list) {
		list.statements.forEach[evaluateStatement]
	}

	def private dispatch void evaluateStatement(Statement stmt) {
		error('''The statement «stmt.eClass.name» is not supported''')
		throw new UnsupportedOperationException('''The statement «stmt.eClass.name» is not supported''')
	}

	def private dispatch void evaluateStatement(AssignmentStatement stmt) {
		stmt.variable.evaluateVariable.value = stmt.expression.trap.evaluateExpression
	}

	def private dispatch void evaluateStatement(IfStatement stmt) {
		if (stmt.expression.trap.evaluateExpression.asBoolean) {
			stmt.statments.evaluateStatementList
		} else {
			(stmt.elseif.findFirst[expression.trap.evaluateExpression.asBoolean]?.statements ?:
				stmt.^else?.statements)?.evaluateStatementList
		}
	}

	def private dispatch void evaluateStatement(CaseStatement stmt) {
		val value = stmt.expression.trap.evaluateExpression;
		(stmt.^case.findFirst[^case.exists[evaluateExpression == value]]?.statements ?: stmt.^else?.statements)?.
			evaluateStatementList
	}

	def private dispatch void evaluateStatement(ForStatement stmt) {
		val variable = stmt.variable.evaluateVariable
		// from
		variable.value = stmt.from.trap.evaluateExpression
		// to
		val to = stmt.to.evaluateExpression
		// by
		val by = stmt.by?.evaluateExpression ?: 1.wrapValue(variable.declaration.type)
		// direction?
		if (by >= variable.declaration.type.defaultValue) {
			while (variable.value <= to) {
				try {
					stmt.statements.evaluateStatementList
				} catch (ContinueException e) {
					// continue
				}
				(stmt.by ?: stmt.from).trap
				variable.value = variable.value + by
			}
		} else {
			while (variable.value >= to) {
				try {
					stmt.statements.evaluateStatementList
				} catch (ContinueException e) {
					// continue
				}
				(stmt.by ?: stmt.from).trap
				variable.value = variable.value + by
			}
		}
	}

	def private dispatch void evaluateStatement(WhileStatement stmt) {
		while (stmt.expression.trap.evaluateExpression.asBoolean) {
			try {
				stmt.statements.evaluateStatementList
			} catch (ContinueException e) {
				// continue
			}
		}
	}

	def private dispatch void evaluateStatement(RepeatStatement stmt) {
		do {
			try {
				stmt.statements.evaluateStatementList
			} catch (ContinueException e) {
				// continue
			}
		} while (!stmt.expression.trap.evaluateExpression.asBoolean);
	}

	def private dispatch void evaluateStatement(ContinueStatement stmt) { throw new ContinueException(stmt.trap) }

	def private dispatch void evaluateStatement(ReturnStatement stmt) { throw new ReturnException(stmt.trap) }

	def private dispatch void evaluateStatement(ExitStatement stmt) {
		throw new StructuredTextExitException(stmt.trap, this)
	}

	def private dispatch Value evaluateExpression(Expression expr) {
		error('''The expression «expr.eClass.name» is not supported''')
		throw new UnsupportedOperationException('''The expression «expr.eClass.name» is not supported''')
	}

	def private dispatch Value evaluateExpression(BinaryExpression expr) {
		switch (expr.operator) {
			case ADD:
				expr.left.evaluateExpression + expr.right.evaluateExpression
			case SUB:
				expr.left.evaluateExpression - expr.right.evaluateExpression
			case MUL:
				expr.left.evaluateExpression * expr.right.evaluateExpression
			case DIV:
				expr.left.evaluateExpression / expr.right.evaluateExpression
			case MOD:
				expr.left.evaluateExpression % expr.right.evaluateExpression
			case POWER:
				expr.left.evaluateExpression ** expr.right.evaluateExpression
			case EQ:
				BoolValue.toBoolValue(expr.left.evaluateExpression == expr.right.evaluateExpression)
			case NE:
				BoolValue.toBoolValue(expr.left.evaluateExpression != expr.right.evaluateExpression)
			case LT:
				BoolValue.toBoolValue(expr.left.evaluateExpression < expr.right.evaluateExpression)
			case LE:
				BoolValue.toBoolValue(expr.left.evaluateExpression <= expr.right.evaluateExpression)
			case GT:
				BoolValue.toBoolValue(expr.left.evaluateExpression > expr.right.evaluateExpression)
			case GE:
				BoolValue.toBoolValue(expr.left.evaluateExpression >= expr.right.evaluateExpression)
			default: {
				error('''The operator «expr.operator» is not supported''')
				throw new UnsupportedOperationException('''The operator «expr.operator» is not supported''')
			}
		}
	}

	def private dispatch Value evaluateExpression(UnaryExpression expr) {
		switch (expr.operator) {
			case PLUS:
				+expr.expression.evaluateExpression
			case MINUS:
				-expr.expression.evaluateExpression
			case NOT:
				expr.expression.evaluateExpression.bitwiseNot
			default: {
				error('''The operator «expr.operator» is not supported''')
				throw new UnsupportedOperationException('''The operator «expr.operator» is not supported''')
			}
		}
	}

	def private dispatch Value evaluateExpression(BoolLiteral expr) { expr.value.toBoolValue }

	def private dispatch Value evaluateExpression(IntLiteral expr) { expr.value.toLIntValue }

	def private dispatch Value evaluateExpression(RealLiteral expr) { expr.value.toLRealValue }

	def private dispatch Value evaluateExpression(PrimaryVariable expr) {
		variables.get(expr.^var).value
	}

	def private dispatch Variable evaluateVariable(
		org.eclipse.fordiac.ide.model.structuredtext.structuredText.Variable variable) {
		throw new UnsupportedOperationException(variable.eClass + " not supported");
	}

	def private dispatch Variable evaluateVariable(PrimaryVariable variable) {
		variables.get(variable.^var)
	}

	static class StructuredTextException extends Exception {
		new(Statement statement) {
			super(statement.eClass.name)
		}
	}

	static class ContinueException extends StructuredTextException {
		new(Statement statement) {
			super(statement)
		}
	}

	static class ReturnException extends StructuredTextException {
		new(Statement statement) {
			super(statement)
		}
	}

	static class StructuredTextExitException extends EvaluatorExitException {
		new(Statement statement, Evaluator evaluator) {
			super(evaluator)
		}
	}
}
