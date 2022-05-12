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

import java.util.List
import java.util.Map
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.eval.AbstractEvaluator
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator
import org.eclipse.fordiac.ide.model.eval.function.StandardFunctions
import org.eclipse.fordiac.ide.model.eval.st.variable.STVariableOperations
import org.eclipse.fordiac.ide.model.eval.value.ArrayValue
import org.eclipse.fordiac.ide.model.eval.value.BoolValue
import org.eclipse.fordiac.ide.model.eval.value.FBValue
import org.eclipse.fordiac.ide.model.eval.value.StructValue
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable
import org.eclipse.fordiac.ide.model.eval.variable.PartialVariable
import org.eclipse.fordiac.ide.model.eval.variable.StructVariable
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement
import org.eclipse.xtend.lib.annotations.Accessors

import static org.eclipse.fordiac.ide.model.eval.st.variable.STVariableOperations.*

import static extension org.eclipse.fordiac.ide.model.eval.function.Functions.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*
import static extension org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.*

abstract class StructuredTextEvaluator extends AbstractEvaluator {
	@Accessors final String name
	protected final Map<String, Variable<?>> variables

	new(String name, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		super(context, parent)
		this.name = name
		this.variables = (variables + #[context]).filterNull.toMap[getName]
	}

	override getChildren() {
		emptyMap
	}

	override getVariables() {
		variables.unmodifiableView
	}

	def protected dispatch Variable<?> findVariable(VarDeclaration variable) {
		(context as FBVariable).value.members.get(variable.name)
	}

	def protected dispatch Variable<?> findVariable(FB variable) {
		(context as FBVariable).value.members.get(variable.name)
	}

	def protected dispatch Variable<?> findVariable(STVarDeclaration variable) {
		variables.get(variable.name)
	}

	def protected dispatch Variable<?> findVariable(ICallable variable) {
		variables.get(variable.name)
	}

	def protected void evaluateVariableInitialization(STVarDeclaration variable) {
		variables.put(variable.name, newVariable(variable).evaluateInitializerExpression(variable.defaultValue))
	}

	def protected dispatch Variable<?> evaluateInitializerExpression(Variable<?> variable, Void expression) {
		variable
	}

	def protected dispatch Variable<?> evaluateInitializerExpression(Variable<?> variable,
		STElementaryInitializerExpression expression) {
		variable.value = expression.value.evaluateExpression
		variable
	}

	def protected dispatch Variable<?> evaluateInitializerExpression(Variable<?> variable,
		STArrayInitializerExpression expression) {
		val value = variable.value as ArrayValue
		var index = 0;
		for (elem : expression.values) {
			if (elem.initExpressions.empty)
				value.getRaw(index++).evaluateInitializerExpression(elem.indexOrInitExpression)
			else {
				val repeatExpression = (elem.indexOrInitExpression as STElementaryInitializerExpression).value
				val repeat = repeatExpression.evaluateExpression.asInteger
				for (unused : 0 ..< repeat) {
					for(initElement : elem.initExpressions) {
						value.getRaw(index++).evaluateInitializerExpression(initElement)
					}
				}
			}
		}
		variable
	}

	def protected void evaluateStatementList(List<STStatement> statements) {
		statements.forEach[evaluateStatement]
	}

	def protected dispatch void evaluateStatement(STStatement stmt) {
		error('''The statement «stmt.eClass.name» is not supported''')
		throw new UnsupportedOperationException('''The statement «stmt.eClass.name» is not supported''')
	}

	def protected dispatch void evaluateStatement(STAssignmentStatement stmt) {
		stmt.left.evaluateVariable.value = stmt.right.trap.evaluateExpression
	}

	def protected dispatch void evaluateStatement(STCallStatement stmt) {
		stmt.call.trap.evaluateExpression
	}

	def protected dispatch void evaluateStatement(STIfStatement stmt) {
		if (stmt.condition.trap.evaluateExpression.asBoolean) {
			stmt.statements.evaluateStatementList
		} else {
			(stmt.elseifs.findFirst[condition.trap.evaluateExpression.asBoolean]?.statements ?:
				stmt.^else?.statements)?.evaluateStatementList
		}
	}

	def protected dispatch void evaluateStatement(STCaseStatement stmt) {
		val value = stmt.selector.trap.evaluateExpression;
		(stmt.cases.findFirst[conditions.exists[trap.evaluateCaseCondition(value)]]?.statements ?:
			stmt.^else?.statements)?.evaluateStatementList
	}

	def protected boolean evaluateCaseCondition(STExpression expr, Value value) {
		switch (expr) {
			STBinaryExpression case expr.op.range:
				value >= expr.left.evaluateExpression && value <= expr.right.evaluateExpression
			default:
				value == expr.evaluateExpression
		}
	}

	def protected dispatch void evaluateStatement(STForStatement stmt) {
		val variable = stmt.variable.findVariable
		// from
		variable.value = stmt.from.trap.evaluateExpression
		// to
		val to = stmt.to.evaluateExpression
		// by
		val by = stmt.by?.evaluateExpression ?: 1.wrapValue(variable.type)
		// direction?
		if (by >= variable.type.defaultValue) {
			try {
				while (variable.value <= to) {
					try {
						stmt.statements.evaluateStatementList
					} catch (ContinueException e) {
						// continue
					}
					(stmt.by ?: stmt.from).trap
					variable.value = variable.value + by
				}
			} catch (ExitException e) {
				// break
			}
		} else {
			try {
				while (variable.value >= to) {
					try {
						stmt.statements.evaluateStatementList
					} catch (ContinueException e) {
						// continue
					}
					stmt.by.trap
					variable.value = variable.value + by
				}
			} catch (ExitException e) {
				// break
			}
		}
	}

	def protected dispatch void evaluateStatement(STWhileStatement stmt) {
		try {
			while (stmt.condition.trap.evaluateExpression.asBoolean) {
				try {
					stmt.statements.evaluateStatementList
				} catch (ContinueException e) {
					// continue
				}
			}
		} catch (ExitException e) {
			// break
		}
	}

	def protected dispatch void evaluateStatement(STRepeatStatement stmt) {
		try {
			do {
				try {
					stmt.statements.evaluateStatementList
				} catch (ContinueException e) {
					// continue
				}
			} while (!stmt.condition.trap.evaluateExpression.asBoolean);
		} catch (ExitException e) {
			// break
		}
	}

	def protected dispatch void evaluateStatement(STContinue stmt) { throw new ContinueException(stmt.trap) }

	def protected dispatch void evaluateStatement(STReturn stmt) { throw new ReturnException(stmt.trap) }

	def protected dispatch void evaluateStatement(STExit stmt) { throw new ExitException(stmt.trap) }

	def protected dispatch Value evaluateExpression(STExpression expr) {
		error('''The expression «expr.eClass.name» is not supported''')
		throw new UnsupportedOperationException('''The expression «expr.eClass.name» is not supported''')
	}

	def protected dispatch Value evaluateExpression(STBinaryExpression expr) {
		switch (expr.op) {
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
			case AND,
			case AMPERSAND:
				switch (left: expr.left.evaluateExpression) {
					BoolValue case left.boolValue: expr.right.evaluateExpression
					BoolValue: BoolValue.FALSE
					default: left.bitwiseAnd(expr.right.evaluateExpression)
				}
			case OR:
				switch (left: expr.left.evaluateExpression) {
					BoolValue case !left.boolValue: expr.right.evaluateExpression
					BoolValue: BoolValue.TRUE
					default: left.bitwiseOr(expr.right.evaluateExpression)
				}
			case XOR:
				switch (left: expr.left.evaluateExpression) {
					BoolValue: BoolValue.toBoolValue(left.boolValue.xor(expr.right.evaluateExpression.asBoolean))
					default: left.bitwiseXor(expr.right.evaluateExpression)
				}
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
				error('''The operator «expr.op» is not supported''')
				throw new UnsupportedOperationException('''The operator «expr.op» is not supported''')
			}
		}
	}

	def protected dispatch Value evaluateExpression(STUnaryExpression expr) {
		switch (expr.op) {
			case PLUS:
				+expr.expression.evaluateExpression
			case MINUS:
				-expr.expression.evaluateExpression
			case NOT:
				switch (value: expr.expression.evaluateExpression) {
					BoolValue: BoolValue.toBoolValue(!value.boolValue)
					default: value.bitwiseNot
				}
			default: {
				error('''The operator «expr.op» is not supported''')
				throw new UnsupportedOperationException('''The operator «expr.op» is not supported''')
			}
		}
	}

	def protected dispatch Value evaluateExpression(STNumericLiteral expr) {
		expr.value.wrapValue(expr.resultType as DataType)
	}

	def protected dispatch Value evaluateExpression(STStringLiteral expr) {
		expr.value.wrapValue(expr.resultType as DataType)
	}

	def protected dispatch Value evaluateExpression(STDateLiteral expr) {
		expr.value.wrapValue(expr.resultType as DataType)
	}

	def protected dispatch Value evaluateExpression(STTimeLiteral expr) {
		expr.value.wrapValue(expr.resultType as DataType)
	}

	def protected dispatch Value evaluateExpression(STTimeOfDayLiteral expr) {
		expr.value.wrapValue(expr.resultType as DataType)
	}

	def protected dispatch Value evaluateExpression(STDateAndTimeLiteral expr) {
		expr.value.wrapValue(expr.resultType as DataType)
	}

	def protected dispatch Value evaluateExpression(STFeatureExpression expr) {
		switch (feature: expr.feature) {
			VarDeclaration,
			STVarDeclaration,
			ICallable case !expr.call:
				feature.findVariable.value
			STStandardFunction case expr.call: {
				val arguments = (expr.mappedInputArguments.entrySet.map [
					value?.evaluateExpression
				] + expr.mappedOutputArguments.entrySet.map [
					value?.evaluateVariable
				]).toList
				StandardFunctions.invoke(feature.name, arguments)
			}
			FB case expr.call: {
				val fb = feature.findVariable.value as FBValue
				val event = fb.type.interfaceList.eventInputs.head
				fb.evaluateFBCall(event, expr.mappedInputArguments, expr.mappedOutputArguments)
			}
			ICallable case expr.call: {
				val arguments = (expr.mappedInputArguments.entrySet.filter[value !== null].map [
					newVariable(key, value.evaluateExpression)
				] + expr.mappedInOutArguments.entrySet.filter[value !== null].map [
					newVariable(key, value.evaluateVariable.value)
				]).toList
				val eval = EvaluatorFactory.createEvaluator(feature,
					feature.eClass.instanceClass as Class<? extends ICallable>, context, arguments, this)
				if (eval === null) {
					error('''Cannot create evaluator for callable «feature.eClass.name»''')
					throw new UnsupportedOperationException('''Cannot create evaluator for callable «feature.eClass.name»''')
				}
				val result = eval.evaluate
				expr.mappedInOutArguments.forEach [ parameter, argument |
					argument.evaluateVariable.value = eval.variables.get(parameter.name).value
				]
				expr.mappedOutputArguments.forEach [ parameter, argument |
					argument.evaluateVariable.value = eval.variables.get(parameter.name).value
				]
				result
			}
			default: {
				error('''The feature «feature.eClass.name» is not supported''')
				throw new UnsupportedOperationException('''The feature «feature.eClass.name» is not supported''')
			}
		}
	}

	def protected dispatch Value evaluateExpression(STMemberAccessExpression expr) {
		expr.member.evaluateExpression(expr.receiver.evaluateExpression)
	}

	def protected dispatch Value evaluateExpression(STArrayAccessExpression expr) {
		val receiver = expr.receiver.evaluateExpression as ArrayValue
		val index = expr.index.map[evaluateExpression.asInteger].toList
		receiver.get(index).value
	}

	def protected dispatch Value evaluateExpression(STExpression expr, Value receiver) {
		error('''The expression «expr.eClass.name» is not supported''')
		throw new UnsupportedOperationException('''The expression «expr.eClass.name» is not supported''')
	}

	def protected dispatch Value evaluateExpression(STMultibitPartialExpression expr, Value receiver) {
		receiver.partial(expr.resultType as DataType,
			if(expr.expression !== null) expr.expression.evaluateExpression.asInteger else expr.index.intValueExact)
	}

	def protected dispatch Value evaluateExpression(STFeatureExpression expr, Value receiver) {
		error('''The feature «expr.feature.eClass.name» is not supported on «receiver.type.name»''')
		throw new UnsupportedOperationException('''The feature «expr.feature.eClass.name» is not supported on «receiver.type.name»''')
	}

	def protected dispatch Value evaluateExpression(STFeatureExpression expr, StructValue receiver) {
		receiver.get(expr.feature.name).value
	}

	def protected dispatch Value evaluateExpression(STFeatureExpression expr, FBValue receiver) {
		if (expr.call)
			receiver.evaluateFBCall(expr.feature as Event, expr.mappedInputArguments, expr.mappedOutputArguments)
		else
			receiver.get(expr.feature.name).value
	}

	def protected dispatch Variable<?> evaluateVariable(STExpression expr) {
		error('''The lvalue expression «expr.eClass.name» is not supported''')
		throw new UnsupportedOperationException('''The lvalue expression «expr.eClass.name» is not supported''')
	}

	def protected dispatch Variable<?> evaluateVariable(STFeatureExpression expr) {
		switch (feature: expr.feature) {
			VarDeclaration,
			STVarDeclaration,
			ICallable case !expr.call:
				feature.findVariable
			default: {
				error('''The feature «feature.eClass.name» is not supported''')
				throw new UnsupportedOperationException('''The feature «feature.eClass.name» is not supported''')
			}
		}
	}

	def protected dispatch Variable<?> evaluateVariable(STMemberAccessExpression expr) {
		expr.member.evaluateVariable(expr.receiver.evaluateVariable)
	}

	def protected dispatch Variable<?> evaluateVariable(STArrayAccessExpression expr) {
		val receiver = expr.receiver.evaluateExpression as ArrayValue
		val index = expr.index.map[evaluateExpression.asInteger].toList
		receiver.get(index)
	}

	def protected dispatch Variable<?> evaluateVariable(STExpression expr, Variable<?> receiver) {
		error('''The expression «expr.eClass.name» is not supported''')
		throw new UnsupportedOperationException('''The lvalue expression «expr.eClass.name» is not supported''')
	}

	def protected dispatch Variable<?> evaluateVariable(STFeatureExpression expr, Variable<?> receiver) {
		error('''The feature «expr.feature.eClass.name» is not supported on «receiver.type.name»''')
		throw new UnsupportedOperationException('''The feature «expr.feature.eClass.name» is not supported on «receiver.type.name»''')
	}

	def protected dispatch Variable<?> evaluateVariable(STFeatureExpression expr, StructVariable receiver) {
		receiver.value.get(expr.feature.name)
	}

	def protected dispatch Variable<?> evaluateVariable(STFeatureExpression expr, FBVariable receiver) {
		receiver.value.get(expr.feature.name)
	}

	def protected dispatch Variable<?> evaluateVariable(STMultibitPartialExpression expr, Variable<?> receiver) {
		new PartialVariable(receiver, expr.resultType as DataType,
			if(expr.expression !== null) expr.expression.evaluateExpression.asInteger else expr.index.intValueExact)
	}

	def protected static dispatch Variable<?> newVariable(VarDeclaration v, Value value) {
		VariableOperations.newVariable(v, value)
	}

	def protected static dispatch Variable<?> newVariable(STVarDeclaration v, Value value) {
		STVariableOperations.newVariable(v, value)
	}

	def protected Value evaluateFBCall(FBValue fb, Event event, Map<INamedElement, STExpression> inputs,
		Map<INamedElement, STExpression> outputs) {
		inputs.forEach [ parameter, argument |
			fb.get(parameter.name).value = argument?.evaluateExpression ?:
				(parameter as VarDeclaration).type.defaultValue
		]
		val eval = EvaluatorFactory.createEvaluator(fb.type, fb.type.eClass.instanceClass as Class<? extends ICallable>,
			context, fb.members.values, this) as FBEvaluator<?>
		if (eval === null) {
			error('''Cannot create evaluator for callable «fb.type.eClass.name»''')
			throw new UnsupportedOperationException('''Cannot create evaluator for callable «fb.type.eClass.name»''')
		}
		eval.evaluate(event)
		outputs.forEach [ parameter, argument |
			if(argument !== null) argument.evaluateVariable.value = eval.variables.get(parameter.name).value
		]
		null
	}

	static class StructuredTextException extends Exception {
		new(STStatement statement) {
			super(statement.eClass.name)
		}
	}

	static class ContinueException extends StructuredTextException {
		new(STStatement statement) {
			super(statement)
		}
	}

	static class ReturnException extends StructuredTextException {
		new(STStatement statement) {
			super(statement)
		}
	}

	static class ExitException extends StructuredTextException {
		new(STStatement statement) {
			super(statement)
		}
	}
}
