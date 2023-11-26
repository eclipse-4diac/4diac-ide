/*******************************************************************************
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
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
import java.util.NoSuchElementException
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock
import org.eclipse.fordiac.ide.model.data.AnyBitType
import org.eclipse.fordiac.ide.model.data.AnyStringType
import org.eclipse.fordiac.ide.model.data.DataFactory
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.StringType
import org.eclipse.fordiac.ide.model.data.Subrange
import org.eclipse.fordiac.ide.model.data.WstringType
import org.eclipse.fordiac.ide.model.eval.AbstractEvaluator
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator
import org.eclipse.fordiac.ide.model.eval.function.StandardFunctions
import org.eclipse.fordiac.ide.model.eval.value.AnyStringValue
import org.eclipse.fordiac.ide.model.eval.value.ArrayValue
import org.eclipse.fordiac.ide.model.eval.value.BoolValue
import org.eclipse.fordiac.ide.model.eval.value.FBValue
import org.eclipse.fordiac.ide.model.eval.value.StringValue
import org.eclipse.fordiac.ide.model.eval.value.StructValue
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.eval.value.WStringValue
import org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable
import org.eclipse.fordiac.ide.model.eval.variable.PartialVariable
import org.eclipse.fordiac.ide.model.eval.variable.StringCharacterVariable
import org.eclipse.fordiac.ide.model.eval.variable.StructVariable
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations
import org.eclipse.fordiac.ide.model.eval.variable.WStringCharacterVariable
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument
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
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNop
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement
import org.eclipse.xtend.lib.annotations.Accessors

import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

import static extension org.eclipse.fordiac.ide.model.eval.function.Functions.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*
import static extension org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.*

abstract class StructuredTextEvaluator extends AbstractEvaluator {
	@Accessors final String name
	protected final Map<String, Variable<?>> variables
	protected final Map<String, Variable<?>> cachedGlobalConstants = newHashMap

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

	override reset(Iterable<Variable<?>> variables) {
		// do nothing (reset anyway on every invocation)
	}

	def protected dispatch Variable<?> findVariable(VarDeclaration variable) {
		(context as FBVariable)?.value?.members?.get(variable.name) ?: variable.evaluateConstantInitialization
	}

	def protected dispatch Variable<?> findVariable(AdapterDeclaration variable) {
		(context as FBVariable).value.members.get(variable.name)
	}

	def protected dispatch Variable<?> findVariable(FB variable) {
		(context as FBVariable).value.members.get(variable.name)
	}

	def protected dispatch Variable<?> findVariable(STVarDeclaration variable) {
		if (variable.eContainer instanceof STVarGlobalDeclarationBlock)
			cachedGlobalConstants.get(variable.name) ?: variable.evaluateGlobalConstantInitialization
		else
			variables.get(variable.name) ?: variable.evaluateVariableInitialization
	}

	def protected dispatch Variable<?> findVariable(ICallable variable) {
		variables.get(variable.name) ?: variables.get("")
	}

	def protected Variable<?> evaluateVariableInitialization(STVarDeclaration decl) {
		val variable = newVariable(decl.name, decl.evaluateType)
		variables.put(variable.name, variable)
		variable.evaluateInitializerExpression(decl.defaultValue)
	}

	def protected Variable<?> evaluateGlobalConstantInitialization(STVarDeclaration decl) {
		val variable = newVariable(decl.name, decl.evaluateType)
		cachedGlobalConstants.put(variable.name, variable)
		variable.evaluateInitializerExpression(decl.defaultValue)
	}

	def protected Variable<?> evaluateConstantInitialization(VarDeclaration decl) {
		switch (container : decl.eContainer) {
			GlobalConstants case container.constants.contains(decl),
			BaseFBType case container.internalConstVars.contains(decl): newVariable(decl)
			default: throw new NoSuchElementException('''Cannot find FB variable «decl.qualifiedName»''')
		}
	}

	def protected INamedElement evaluateType(STVarDeclaration declaration) {
		val type = switch (type: declaration.type) {
			AnyStringType case declaration.maxLength !== null:
				type.newStringType(declaration.maxLength.evaluateExpression.asInteger)
			DataType:
				type
		}
		if (declaration.array)
			type.newArrayType(
				if (declaration.ranges.empty)
					declaration.count.map[DataFactory.eINSTANCE.createSubrange]
				else
					declaration.ranges.map[evaluateSubrange]
			)
		else
			type
	}

	def protected Subrange evaluateSubrange(STExpression expr) {
		switch (expr) {
			STBinaryExpression case expr.op === STBinaryOperator.RANGE:
				newSubrange(expr.left.evaluateExpression.asInteger, expr.right.evaluateExpression.asInteger)
			default:
				newSubrange(0, expr.evaluateExpression.asInteger)
		}
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
					for (initElement : elem.initExpressions) {
						value.getRaw(index++).evaluateInitializerExpression(initElement)
					}
				}
			}
		}
		variable
	}

	def protected dispatch Variable<?> evaluateInitializerExpression(Variable<?> variable,
		STStructInitializerExpression expression) {
		val value = variable.value as StructValue
		for (elem : expression.values) {
			value.get(elem.variable.name).evaluateInitializerExpression(elem.value)
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

	def protected dispatch void evaluateStatement(STExpression stmt) {
		stmt.trap.evaluateExpression
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
		val variable = stmt.variable.evaluateVariable
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

	def protected dispatch void evaluateStatement(STNop stmt) { /* do nothing */ }

	def protected dispatch Value evaluateExpression(STExpression expr) {
		error('''The expression «expr.eClass.name» is not supported''')
		throw new UnsupportedOperationException('''The expression «expr.eClass.name» is not supported''')
	}

	def protected dispatch Value evaluateExpression(STAssignment expr) {
		val value = expr.right.evaluateExpression
		expr.left.evaluateVariable.value = value
		value
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
			AdapterDeclaration,
			STVarDeclaration,
			ICallable case !expr.call:
				feature.findVariable.value
			STStandardFunction case expr.call: {
				val arguments = (expr.mappedInputArguments.entrySet.map [
					value?.argument?.evaluateExpression.castValue(key.featureType)
				] + expr.mappedOutputArguments.entrySet.map [
					value?.argument?.evaluateVariable
				]).toList
				StandardFunctions.invoke(feature.name, arguments)
			}
			FB case expr.call: {
				val fb = feature.findVariable.value as FBValue
				val event = fb.type.interfaceList.eventInputs.head
				fb.evaluateFBCall(event, expr.mappedInputArguments, expr.mappedOutputArguments)
			}
			ICallable case expr.call:
				context.evaluateCall(feature, expr.mappedInputArguments, expr.mappedOutputArguments,
					expr.mappedInOutArguments)
			default: {
				error('''The feature «feature.eClass.name» is not supported''')
				throw new UnsupportedOperationException('''The feature «feature.eClass.name» is not supported''')
			}
		}
	}

	def protected dispatch Value evaluateExpression(STBuiltinFeatureExpression expr) {
		switch (expr.feature) {
			case THIS:
				if (expr.call) {
					val fb = context.value as FBValue
					val event = fb.type.interfaceList.eventInputs.head
					fb.evaluateFBCall(event, expr.mappedInputArguments, expr.mappedOutputArguments)
				} else
					context.value
		}
	}

	def protected dispatch Value evaluateExpression(STMemberAccessExpression expr) {
		expr.member.evaluateExpression(expr.receiver.evaluateVariable)
	}

	def protected dispatch Value evaluateExpression(STArrayAccessExpression expr) {
		val receiver = expr.receiver.evaluateExpression
		val index = expr.index.map[evaluateExpression.asInteger].toList
		switch (receiver) {
			ArrayValue:
				receiver.get(index).value
			AnyStringValue:
				receiver.charAt(index.head)
		}
	}

	def protected dispatch Value evaluateExpression(STExpression expr, Variable<?> receiver) {
		error('''The expression «expr.eClass.name» is not supported''')
		throw new UnsupportedOperationException('''The expression «expr.eClass.name» is not supported''')
	}

	def protected dispatch Value evaluateExpression(STMultibitPartialExpression expr, Variable<?> receiver) {
		receiver.value.partial(expr.resultType as DataType,
			if(expr.expression !== null) expr.expression.evaluateExpression.asInteger else expr.index.intValueExact)
	}

	def protected dispatch Value evaluateExpression(STFeatureExpression expr, Variable<?> receiver) {
		switch (value : receiver.value) {
			StructValue:
				value.get(expr.feature.name).value
			FBValue:
				switch (feature : expr.feature) {
					VarDeclaration,
					AdapterDeclaration,
					FB case !expr.call:
						value.get(expr.feature.name).value
					FB case expr.call: {
						val fb = value.get(expr.feature.name).value as FBValue
						val event = fb.type.interfaceList.eventInputs.head
						fb.evaluateFBCall(event, expr.mappedInputArguments, expr.mappedOutputArguments)
					}
					Event case expr.call:
						value.evaluateFBCall(feature, expr.mappedInputArguments, expr.mappedOutputArguments)
					ICallable case expr.call:
						receiver.evaluateCall(feature, expr.mappedInputArguments, expr.mappedOutputArguments,
							expr.mappedInOutArguments)
					default: {
						error('''The feature «expr.feature.eClass.name» is not supported on «receiver.type.name»''')
						throw new UnsupportedOperationException('''The feature «expr.feature.eClass.name» is not supported on «receiver.type.name»''')
					}
				}
			default: {
				error('''The feature «expr.feature.eClass.name» is not supported on «receiver.type.name»''')
				throw new UnsupportedOperationException('''The feature «expr.feature.eClass.name» is not supported on «receiver.type.name»''')
			}
		}
	}

	def protected dispatch Variable<?> evaluateVariable(STExpression expr) {
		error('''The lvalue expression «expr.eClass.name» is not supported''')
		throw new UnsupportedOperationException('''The lvalue expression «expr.eClass.name» is not supported''')
	}

	def protected dispatch Variable<?> evaluateVariable(STFeatureExpression expr) {
		switch (feature: expr.feature) {
			VarDeclaration,
			AdapterDeclaration,
			STVarDeclaration,
			ICallable case !expr.call:
				feature.findVariable
			default: {
				error('''The feature «feature.eClass.name» is not supported''')
				throw new UnsupportedOperationException('''The feature «feature.eClass.name» is not supported''')
			}
		}
	}

	def protected dispatch Variable<?> evaluateVariable(STBuiltinFeatureExpression expr) {
		switch (expr.feature) {
			case THIS: context
		}
	}

	def protected dispatch Variable<?> evaluateVariable(STMemberAccessExpression expr) {
		expr.member.evaluateVariable(expr.receiver.evaluateVariable)
	}

	def protected dispatch Variable<?> evaluateVariable(STArrayAccessExpression expr) {
		val receiver = expr.receiver.evaluateVariable
		val index = expr.index.map[evaluateExpression.asInteger].toList
		switch (receiver) {
			ArrayVariable:
				receiver.value.get(index)
			Variable<StringValue> case receiver.type instanceof StringType:
				new StringCharacterVariable(receiver, index.head)
			Variable<WStringValue> case receiver.type instanceof WstringType:
				new WStringCharacterVariable(receiver, index.head)
		}
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

	def protected dispatch Variable<?> evaluateVariable(STBuiltinFeatureExpression expr, Variable<?> receiver) {
		error('''The feature «expr.feature.getName» is not supported on «receiver.type.name»''')
		throw new UnsupportedOperationException('''The feature «expr.feature.getName» is not supported on «receiver.type.name»''')
	}

	def protected dispatch Variable<?> evaluateVariable(STMultibitPartialExpression expr, Variable<?> receiver) {
		new PartialVariable(receiver, expr.resultType as AnyBitType,
			if(expr.expression !== null) expr.expression.evaluateExpression.asInteger else expr.index.intValueExact)
	}

	def protected dispatch Variable<?> newVariable(INamedElement v, Value value) {
		error('''The variable «v.name» with type «v.eClass.name» is not supported''')
		throw new UnsupportedOperationException('''The variable «v.name» with type «v.eClass.name» is not supported''')
	}

	def protected dispatch Variable<?> newVariable(VarDeclaration v, Value value) {
		VariableOperations.newVariable(v, value)
	}

	def protected dispatch Variable<?> newVariable(STVarDeclaration v, Value value) {
		newVariable(v.name, v.evaluateType, value)
	}

	def protected Value evaluateCall(Variable<?> receiver, ICallable feature, Map<INamedElement, STCallArgument> inputs,
		Map<INamedElement, STCallArgument> outputs, Map<INamedElement, STCallArgument> inouts) {
		val arguments = (inputs.entrySet.filter[value !== null].map [
			newVariable(key, value.argument.evaluateExpression)
		] + inouts.entrySet.filter[value !== null].map [
			newVariable(key, value.argument.evaluateVariable.value)
		]).toList
		val eval = EvaluatorFactory.createEvaluator(feature, feature.eClass.instanceClass as Class<? extends ICallable>,
			receiver, arguments, this)
		if (eval === null) {
			error('''Cannot create evaluator for callable «feature.eClass.name»''')
			throw new UnsupportedOperationException('''Cannot create evaluator for callable «feature.eClass.name»''')
		}
		val result = eval.evaluate
		inouts.forEach [ parameter, argument |
			if(argument !== null) argument.argument.evaluateVariable.value = eval.variables.get(parameter.name).value
		]
		outputs.forEach [ parameter, argument |
			switch (argument) {
				STCallNamedOutputArgument case argument.not:
					argument.argument.evaluateVariable.value = eval.variables.get(parameter.name).value.bitwiseNot
				case argument !== null:
					argument.argument.evaluateVariable.value = eval.variables.get(parameter.name).value
			}
		]
		result
	}

	def protected Value evaluateFBCall(FBValue fb, Event event, Map<INamedElement, STCallArgument> inputs,
		Map<INamedElement, STCallArgument> outputs) {
		inputs.forEach [ parameter, argument |
			fb.get(parameter.name).value = argument?.argument?.evaluateExpression ?:
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
			switch (argument) {
				STCallNamedOutputArgument case argument.not:
					argument.argument.evaluateVariable.value = eval.variables.get(parameter.name).value.bitwiseNot
				case argument !== null:
					argument.argument.evaluateVariable.value = eval.variables.get(parameter.name).value
			}
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
