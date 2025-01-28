/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.eval.st;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.data.Subrange;
import org.eclipse.fordiac.ide.model.eval.AbstractEvaluator;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.fordiac.ide.model.eval.function.Functions;
import org.eclipse.fordiac.ide.model.eval.function.StandardFunctions;
import org.eclipse.fordiac.ide.model.eval.value.AnyStringValue;
import org.eclipse.fordiac.ide.model.eval.value.ArrayValue;
import org.eclipse.fordiac.ide.model.eval.value.BoolValue;
import org.eclipse.fordiac.ide.model.eval.value.StringValue;
import org.eclipse.fordiac.ide.model.eval.value.StructValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.eval.value.WStringValue;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.eval.variable.GenericVariable;
import org.eclipse.fordiac.ide.model.eval.variable.PartialVariable;
import org.eclipse.fordiac.ide.model.eval.variable.StringCharacterVariable;
import org.eclipse.fordiac.ide.model.eval.variable.StructVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.eval.variable.WStringCharacterVariable;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElsePart;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STEnumLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNop;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSingleArrayInitElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;

@SuppressWarnings("java:S1452")
public abstract class StructuredTextEvaluator extends AbstractEvaluator {

	public static final String RETURN_VARIABLE_NAME = ""; //$NON-NLS-1$

	private final String name;
	private final Map<String, Variable<?>> variables;
	private final Map<String, Variable<?>> cachedGlobalConstants = new HashMap<>();

	protected StructuredTextEvaluator(final String name, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		super(context, parent);
		this.name = name;
		this.variables = Stream.concat(StreamSupport.stream(variables.spliterator(), false), Stream.of(context))
				.filter(Objects::nonNull)
				.collect(Collectors.toMap(Variable::getName, Function.identity(), (oldValue, newValue) -> newValue));
	}

	@Override
	public Map<? extends ICallable, ? extends Evaluator> getChildren() {
		return Collections.emptyMap();
	}

	@Override
	public Map<String, Variable<?>> getVariables() {
		return variables;
	}

	@Override
	public void reset(final Iterable<Variable<?>> variables) {
		// nothing to do
	}

	protected Variable<?> findVariable(final VarDeclaration variable) {
		if (getContext() instanceof final FBVariable fbVariable) {
			final Variable<?> result = fbVariable.getMembers().get(variable.getName());
			if (result != null) {
				return result;
			}
		}
		return evaluateConstantInitialization(variable);
	}

	protected Variable<?> findVariable(final AdapterDeclaration variable) {
		return ((FBVariable) getContext()).getValue().getMembers().get(variable.getName());
	}

	protected Variable<?> findVariable(final FB variable) {
		return ((FBVariable) getContext()).getValue().getMembers().get(variable.getName());
	}

	protected Variable<?> findVariable(final STVarDeclaration variable)
			throws EvaluatorException, InterruptedException {
		final String globalConstantName = getGlobalConstantName(variable);
		if (globalConstantName != null) {
			final Variable<?> cachedGlobalConstant = cachedGlobalConstants.get(globalConstantName);
			if (cachedGlobalConstant != null) {
				return cachedGlobalConstant;
			}
			return evaluateGlobalConstantInitialization(variable);
		}
		final Variable<?> result = variables.get(variable.getName());
		if (result != null) {
			return result;
		}
		return evaluateVariableInitialization(variable);
	}

	protected Variable<?> findVariable(final ICallable variable) {
		final Variable<?> result = variables.get(variable.getName());
		if (result != null) {
			return result;
		}
		return variables.get(StructuredTextEvaluator.RETURN_VARIABLE_NAME);
	}

	protected Variable<?> evaluateVariableInitialization(final STVarDeclaration decl)
			throws EvaluatorException, InterruptedException {
		final Variable<?> variable = VariableOperations.newVariable(decl.getName(), evaluateType(decl));
		variables.put(variable.getName(), variable);
		return evaluateInitializerExpression(variable, decl.getDefaultValue());
	}

	protected Variable<?> evaluateGlobalConstantInitialization(final STVarDeclaration decl)
			throws EvaluatorException, InterruptedException {
		final Variable<?> variable = VariableOperations.newVariable(getGlobalConstantName(decl), evaluateType(decl));
		cachedGlobalConstants.put(variable.getName(), variable);
		return evaluateInitializerExpression(variable, decl.getDefaultValue());
	}

	protected static Variable<?> evaluateConstantInitialization(final VarDeclaration decl) {
		return switch (decl.eContainer()) {
		case final GlobalConstants globalConstants when globalConstants.getConstants().contains(decl) ->
			VariableOperations.newVariable(decl);
		case final BaseFBType baseFBType when baseFBType.getInternalConstVars().contains(decl) ->
			VariableOperations.newVariable(decl);
		case null, default -> throw new NoSuchElementException(MessageFormat
				.format(Messages.StructuredTextEvaluator_DeclarationNotAConstant, decl.getQualifiedName()));
		};
	}

	protected INamedElement evaluateType(final STVarDeclaration declaration)
			throws EvaluatorException, InterruptedException {
		final DataType type = switch (declaration.getType()) {
		case final AnyStringType anyStringType when declaration.getMaxLength() != null -> STCoreUtil.newStringType(
				anyStringType, ValueOperations.asInteger(evaluateExpression(declaration.getMaxLength())));
		case final DataType dataType -> dataType;
		case null, default -> null;
		};
		if (declaration.isArray()) {
			if (declaration.getRanges().isEmpty()) {
				return STCoreUtil.newArrayType(type,
						declaration.getCount().stream().map(unused -> DataFactory.eINSTANCE.createSubrange()).toList());
			}
			final List<Subrange> subranges = new ArrayList<>(declaration.getRanges().size());
			for (final STExpression range : declaration.getRanges()) {
				subranges.add(evaluateSubrange(range));
			}
			return STCoreUtil.newArrayType(type, subranges);
		}
		return type;
	}

	protected Subrange evaluateSubrange(final STExpression expr) throws EvaluatorException, InterruptedException {
		return switch (expr) {
		case final STBinaryExpression binaryExpression when binaryExpression.getOp() == STBinaryOperator.RANGE ->
			STCoreUtil.newSubrange(ValueOperations.asInteger(evaluateExpression(binaryExpression.getLeft())),
					ValueOperations.asInteger(evaluateExpression(binaryExpression.getRight())));
		default -> STCoreUtil.newSubrange(0, ValueOperations.asInteger(evaluateExpression(expr)));
		};
	}

	protected Variable<?> evaluateInitializerExpression(final Variable<?> variable,
			final STInitializerExpression expression) throws EvaluatorException, InterruptedException {
		return switch (expression) {
		case null -> variable;
		case final STElementaryInitializerExpression elementaryInitializerExpression ->
			evaluateInitializerExpression(variable, elementaryInitializerExpression);
		case final STArrayInitializerExpression arrayInitializerExpression ->
			evaluateInitializerExpression(variable, arrayInitializerExpression);
		case final STStructInitializerExpression structInitializerExpression ->
			evaluateInitializerExpression(variable, structInitializerExpression);
		default -> throw createUnsupportedOperationException(expression);
		};
	}

	protected Variable<?> evaluateInitializerExpression(final Variable<?> variable,
			final STElementaryInitializerExpression expression) throws EvaluatorException, InterruptedException {
		variable.setValue(evaluateExpression(expression.getValue()));
		return variable;
	}

	protected Variable<?> evaluateInitializerExpression(final Variable<?> variable,
			final STArrayInitializerExpression expression) throws EvaluatorException, InterruptedException {
		if (variable instanceof GenericVariable) {
			variable.setValue(new ArrayValue((ArrayType) expression.getResultType()));
		}
		final ArrayValue value = (ArrayValue) variable.getValue();
		int index = 0;
		final int size = value.size();
		for (final STArrayInitElement elem : expression.getValues()) {
			if (elem instanceof final STSingleArrayInitElement singleArrayInitElement) {
				if (index >= size) {
					return variable; // ignore excess initializers
				}
				evaluateInitializerExpression(value.getRaw(index), singleArrayInitElement.getInitExpression());
				index++;
			} else if (elem instanceof final STRepeatArrayInitElement repeatArrayInitElement) {
				final int count = repeatArrayInitElement.getRepetitions().intValueExact();
				for (int i = 0; i < count; i++) {
					for (final STInitializerExpression initElement : repeatArrayInitElement.getInitExpressions()) {
						if (index >= size) {
							return variable; // ignore excess initializers
						}
						evaluateInitializerExpression(value.getRaw(index), initElement);
						index++;
					}
				}
			}
		}
		return variable;
	}

	protected Variable<?> evaluateInitializerExpression(final Variable<?> variable,
			final STStructInitializerExpression expression) throws EvaluatorException, InterruptedException {
		if (variable instanceof GenericVariable) {
			variable.setValue(new StructValue((StructuredType) expression.getResultType()));
		}
		final StructValue value = (StructValue) variable.getValue();
		for (final STStructInitElement elem : expression.getValues()) {
			evaluateInitializerExpression(value.get(elem.getVariable().getName()), elem.getValue());
		}
		return variable;
	}

	protected void evaluateStatementList(final List<STStatement> statements)
			throws EvaluatorException, InterruptedException {
		for (final STStatement statement : statements) {
			evaluateStatement(statement);
		}
	}

	protected void evaluateStatement(final STStatement stmt) throws EvaluatorException, InterruptedException {
		switch (stmt) {
		case final STExpression stExpression -> evaluateStatement(stExpression);
		case final STIfStatement stIfStatement -> evaluateStatement(stIfStatement);
		case final STCaseStatement stCaseStatement -> evaluateStatement(stCaseStatement);
		case final STForStatement stForStatement -> evaluateStatement(stForStatement);
		case final STWhileStatement stWhileStatement -> evaluateStatement(stWhileStatement);
		case final STRepeatStatement stRepeatStatement -> evaluateStatement(stRepeatStatement);
		case final STContinue stContinue -> evaluateStatement(stContinue);
		case final STReturn stReturn -> evaluateStatement(stReturn);
		case final STExit stExit -> evaluateStatement(stExit);
		case final STNop stNop -> evaluateStatement(stNop);
		case null, default -> throw createUnsupportedOperationException(stmt);
		}
	}

	protected void evaluateStatement(final STExpression stmt) throws EvaluatorException, InterruptedException {
		evaluateExpression(trap(stmt));
	}

	protected void evaluateStatement(final STIfStatement stmt) throws EvaluatorException, InterruptedException {
		if (ValueOperations.asBoolean(evaluateExpression(trap(stmt.getCondition())))) {
			evaluateStatementList(stmt.getStatements());
		} else {
			for (final STElseIfPart elseIfPart : stmt.getElseifs()) {
				if (ValueOperations.asBoolean(evaluateExpression(trap(elseIfPart.getCondition())))) {
					evaluateStatementList(elseIfPart.getStatements());
					return;
				}
			}
			final STElsePart elsePart = stmt.getElse();
			if (elsePart != null) {
				evaluateStatementList(elsePart.getStatements());
			}
		}
	}

	protected void evaluateStatement(final STCaseStatement stmt) throws EvaluatorException, InterruptedException {
		final Value value = evaluateExpression(trap(stmt.getSelector()));
		for (final STCaseCases caseBlock : stmt.getCases()) {
			for (final STExpression condition : caseBlock.getConditions()) {
				if (evaluateCaseCondition(trap(condition), value)) {
					evaluateStatementList(caseBlock.getStatements());
					return;
				}
			}
		}
		final STElsePart elseBlock = stmt.getElse();
		if (elseBlock != null) {
			evaluateStatementList(elseBlock.getStatements());
		}
	}

	protected boolean evaluateCaseCondition(final STExpression expr, final Value value)
			throws EvaluatorException, InterruptedException {
		return switch (expr) {
		case final STBinaryExpression binaryExpression when binaryExpression.getOp() == STBinaryOperator.RANGE ->
			ValueOperations.greaterEquals(value, evaluateExpression(binaryExpression.getLeft()))
					&& ValueOperations.lessEquals(value, evaluateExpression(binaryExpression.getRight()));
		default -> ValueOperations.equals(value, evaluateExpression(expr));
		};
	}

	protected void evaluateStatement(final STForStatement stmt) throws EvaluatorException, InterruptedException {
		final Variable<?> variable = evaluateVariable(stmt.getVariable());
		variable.setValue(evaluateExpression(trap(stmt.getFrom())));
		final Value to = evaluateExpression(stmt.getTo());
		final Value by;
		STExpression trapExpression;
		if (stmt.getBy() != null) {
			by = evaluateExpression(stmt.getBy());
			trapExpression = stmt.getBy();
		} else {
			by = ValueOperations.wrapValue(Integer.valueOf(1), variable.getType());
			trapExpression = stmt.getFrom();
		}
		final EList<STStatement> statements = stmt.getStatements();
		if (ValueOperations.greaterEquals(by, ValueOperations.defaultValue(variable.getType()))) {
			try {
				while (ValueOperations.lessEquals(variable.getValue(), to)) {
					evaluateForIteration(variable, by, trapExpression, statements);
				}
			} catch (final ExitException e) {
				// exit
			}
		} else {
			try {
				while (ValueOperations.greaterEquals(variable.getValue(), to)) {
					evaluateForIteration(variable, by, trapExpression, statements);
				}
			} catch (final ExitException e) {
				// exit
			}
		}
	}

	private void evaluateForIteration(final Variable<?> variable, final Value by, final STExpression trapExpression,
			final EList<STStatement> statements) throws EvaluatorException, InterruptedException, ClassCastException {
		try {
			evaluateStatementList(statements);
		} catch (final ContinueException e) {
			// continue
		}
		trap(trapExpression);
		variable.setValue(ValueOperations.add(variable.getValue(), by));
	}

	protected void evaluateStatement(final STWhileStatement stmt) throws EvaluatorException, InterruptedException {
		try {
			final STExpression condition = stmt.getCondition();
			final EList<STStatement> statements = stmt.getStatements();
			while (ValueOperations.asBoolean(evaluateExpression(trap(condition)))) {
				try { // NOSONAR
					evaluateStatementList(statements);
				} catch (final ContinueException e) {
					// continue
				}
			}
		} catch (final ExitException e) {
			// exit
		}
	}

	protected void evaluateStatement(final STRepeatStatement stmt) throws EvaluatorException, InterruptedException {
		try {
			final STExpression condition = stmt.getCondition();
			final EList<STStatement> statements = stmt.getStatements();
			do {
				try { // NOSONAR
					evaluateStatementList(statements);
				} catch (final ContinueException e) {
					// continue
				}
			} while ((!ValueOperations.asBoolean(evaluateExpression(trap(condition)))));
		} catch (final ExitException e) {
			// exit
		}
	}

	protected void evaluateStatement(final STContinue stmt) throws EvaluatorException, InterruptedException {
		throw new ContinueException(trap(stmt), this);
	}

	protected void evaluateStatement(final STReturn stmt) throws EvaluatorException, InterruptedException {
		throw new ReturnException(trap(stmt), this);
	}

	protected void evaluateStatement(final STExit stmt) throws EvaluatorException, InterruptedException {
		throw new ExitException(trap(stmt), this);
	}

	protected void evaluateStatement(final STNop stmt) {
		// nothing to do
	}

	protected Value evaluateExpression(final STExpression expr) throws EvaluatorException, InterruptedException {
		return switch (expr) {
		case final STAssignment stAssignment -> evaluateExpression(stAssignment);
		case final STBinaryExpression stBinaryExpression -> evaluateExpression(stBinaryExpression);
		case final STUnaryExpression stUnaryExpression -> evaluateExpression(stUnaryExpression);
		case final STNumericLiteral stNumericLiteral -> evaluateExpression(stNumericLiteral);
		case final STStringLiteral stStringLiteral -> evaluateExpression(stStringLiteral);
		case final STDateLiteral stDateLiteral -> evaluateExpression(stDateLiteral);
		case final STTimeLiteral stTimeLiteral -> evaluateExpression(stTimeLiteral);
		case final STTimeOfDayLiteral stTimeOfDayLiteral -> evaluateExpression(stTimeOfDayLiteral);
		case final STDateAndTimeLiteral stDateAndTimeLiteral -> evaluateExpression(stDateAndTimeLiteral);
		case final STEnumLiteral stEnumLiteral -> evaluateExpression(stEnumLiteral);
		case final STFeatureExpression stFeatureExpression -> evaluateExpression(stFeatureExpression);
		case final STBuiltinFeatureExpression stBuiltinExpression -> evaluateExpression(stBuiltinExpression);
		case final STMemberAccessExpression stMemberAccessExpression -> evaluateExpression(stMemberAccessExpression);
		case final STArrayAccessExpression stArrayAccessExpression -> evaluateExpression(stArrayAccessExpression);
		case null, default -> throw createUnsupportedOperationException(expr);
		};
	}

	protected Value evaluateExpression(final STAssignment expr) throws EvaluatorException, InterruptedException {
		final Value value = evaluateExpression(expr.getRight());
		evaluateVariable(expr.getLeft()).setValue(value);
		return value;
	}

	protected Value evaluateExpression(final STBinaryExpression expr) throws EvaluatorException, InterruptedException {
		final Value left = evaluateExpression(expr.getLeft());
		if (isShortCircuit(expr, left)) {
			return left;
		}
		final Value right = evaluateExpression(expr.getRight());
		return switch (expr.getOp()) {
		case ADD -> ValueOperations.add(left, right);
		case SUB -> ValueOperations.subtract(left, right);
		case MUL -> ValueOperations.multiply(left, right);
		case DIV -> ValueOperations.divideBy(left, right);
		case MOD -> ValueOperations.remainderBy(left, right);
		case POWER -> ValueOperations.power(left, right);
		case AND, AMPERSAND -> ValueOperations.bitwiseAnd(left, right);
		case OR -> ValueOperations.bitwiseOr(left, right);
		case XOR -> ValueOperations.bitwiseXor(left, right);
		case EQ -> BoolValue.toBoolValue(ValueOperations.equals(left, right));
		case NE -> BoolValue.toBoolValue(!ValueOperations.equals(left, right));
		case LT -> BoolValue.toBoolValue(ValueOperations.lessThan(left, right));
		case LE -> BoolValue.toBoolValue(ValueOperations.lessEquals(left, right));
		case GT -> BoolValue.toBoolValue(ValueOperations.greaterThan(left, right));
		case GE -> BoolValue.toBoolValue(ValueOperations.greaterEquals(left, right));
		case RANGE -> throw new UnsupportedOperationException(Messages.StructuredTextEvaluator_RangeNotSupported);
		};
	}

	private static boolean isShortCircuit(final STBinaryExpression expr, final Value left) {
		return switch (expr.getOp()) {
		case AND, AMPERSAND -> expr.getResultType() instanceof BoolType && !ValueOperations.asBoolean(left);
		case OR -> expr.getResultType() instanceof BoolType && ValueOperations.asBoolean(left);
		default -> false;
		};
	}

	protected Value evaluateExpression(final STUnaryExpression expr) throws EvaluatorException, InterruptedException {
		final Value value = evaluateExpression(expr.getExpression());
		return switch (expr.getOp()) {
		case PLUS -> ValueOperations.abs(value);
		case MINUS -> ValueOperations.negate(value);
		case NOT -> ValueOperations.bitwiseNot(value);
		};
	}

	protected static Value evaluateExpression(final STNumericLiteral expr) {
		return ValueOperations.wrapValue(expr.getValue(), expr.getResultType());
	}

	protected static Value evaluateExpression(final STStringLiteral expr) {
		return ValueOperations.wrapValue(expr.getValue(), expr.getResultType());
	}

	protected static Value evaluateExpression(final STDateLiteral expr) {
		return ValueOperations.wrapValue(expr.getValue(), expr.getResultType());
	}

	protected static Value evaluateExpression(final STTimeLiteral expr) {
		return ValueOperations.wrapValue(expr.getValue(), expr.getResultType());
	}

	protected static Value evaluateExpression(final STTimeOfDayLiteral expr) {
		return ValueOperations.wrapValue(expr.getValue(), expr.getResultType());
	}

	protected static Value evaluateExpression(final STDateAndTimeLiteral expr) {
		return ValueOperations.wrapValue(expr.getValue(), expr.getResultType());
	}

	protected static Value evaluateExpression(final STEnumLiteral expr) {
		return ValueOperations.wrapValue(expr.getValue(), expr.getResultType());
	}

	protected Value evaluateExpression(final STFeatureExpression expr) throws EvaluatorException, InterruptedException {
		return switch (expr.getFeature()) {
		case final VarDeclaration varDeclaration -> findVariable(varDeclaration).getValue();
		case final STVarDeclaration stVarDeclaration -> findVariable(stVarDeclaration).getValue();
		case final AdapterDeclaration adapterDeclaration -> findVariable(adapterDeclaration).getValue();
		case final FB fb when !expr.isCall() -> findVariable(fb).getValue();
		case final ICallable callable when !expr.isCall() -> findVariable(callable).getValue();
		case final STStandardFunction standardFunction when expr.isCall() ->
			ValueOperations.castValue(evaluateStandardFunctionCall(standardFunction, expr.getMappedInputArguments(),
					expr.getMappedOutputArguments()), expr.getResultType());
		case final FB fb when expr.isCall() -> {
			final FBVariable receiver = (FBVariable) findVariable(fb);
			final Event event = fb.getType().getInterfaceList().getEventInputs().getFirst();
			yield evaluateFBCall(receiver, event, expr.getMappedInputArguments(), expr.getMappedOutputArguments(),
					expr.getMappedInOutArguments());
		}
		case final ICallable callable when expr.isCall() -> evaluateCall(getContext(), callable,
				expr.getMappedInputArguments(), expr.getMappedOutputArguments(), expr.getMappedInOutArguments());
		case null, default -> throw createUnsupportedOperationException(expr.getFeature());
		};
	}

	protected Value evaluateExpression(final STBuiltinFeatureExpression expr)
			throws EvaluatorException, InterruptedException {
		return switch (expr.getFeature()) {
		case THIS -> {
			if (expr.isCall()) {
				final FBVariable receiver = (FBVariable) getContext();
				final Event event = receiver.getType().getInterfaceList().getEventInputs().getFirst();
				yield evaluateFBCall(receiver, event, expr.getMappedInputArguments(), expr.getMappedOutputArguments(),
						expr.getMappedInOutArguments());
			}
			yield getContext().getValue();
		}
		case null, default -> throw createUnsupportedOperationException(expr.getFeature());
		};
	}

	protected Value evaluateExpression(final STMemberAccessExpression expr)
			throws EvaluatorException, InterruptedException {
		return evaluateExpression(expr.getMember(), evaluateVariable(expr.getReceiver()));
	}

	protected Value evaluateExpression(final STArrayAccessExpression expr)
			throws EvaluatorException, InterruptedException {
		final Value receiver = evaluateExpression(expr.getReceiver());
		return switch (receiver) {
		case final ArrayValue arrayValue -> {
			final List<Integer> indices = new ArrayList<>(expr.getIndex().size());
			for (final STExpression index : expr.getIndex()) {
				indices.add(Integer.valueOf(ValueOperations.asInteger(evaluateExpression(index))));
			}
			yield arrayValue.get(indices).getValue();
		}
		case final AnyStringValue stringValue ->
			stringValue.charAt(ValueOperations.asInteger(evaluateExpression(expr.getIndex().getFirst())));
		case null, default -> throw createUnsupportedOperationException(receiver);
		};
	}

	protected Value evaluateExpression(final STExpression expr, final Variable<?> receiver)
			throws EvaluatorException, InterruptedException {
		return switch (expr) {
		case final STFeatureExpression stFeatureExpression -> evaluateExpression(stFeatureExpression, receiver);
		case final STMultibitPartialExpression stMultibitPartialExpression ->
			evaluateExpression(stMultibitPartialExpression, receiver);
		case null, default -> throw createUnsupportedOperationException(expr);
		};
	}

	protected Value evaluateExpression(final STMultibitPartialExpression expr, final Variable<?> receiver)
			throws EvaluatorException, InterruptedException {
		final int index;
		if (expr.getExpression() != null) {
			index = ValueOperations.asInteger(evaluateExpression(expr.getExpression()));
		} else {
			index = expr.getIndex().intValueExact();
		}
		return ValueOperations.partial(receiver.getValue(), (DataType) expr.getResultType(), index);
	}

	protected Value evaluateExpression(final STFeatureExpression expr, final Variable<?> receiver)
			throws EvaluatorException, InterruptedException {
		return switch (receiver) {
		case final StructVariable structVariable ->
			structVariable.getMembers().get(expr.getFeature().getName()).getValue();
		case final FBVariable fbVariable -> switch (expr.getFeature()) {
		case final VarDeclaration varDeclaration -> fbVariable.getMembers().get(varDeclaration.getName()).getValue();
		case final AdapterDeclaration adapterDeclaration ->
			fbVariable.getMembers().get(adapterDeclaration.getName()).getValue();
		case final FB fb when !expr.isCall() -> fbVariable.getMembers().get(fb.getName()).getValue();
		case final FB fb when expr.isCall() -> {
			final FBVariable callReceiver = (FBVariable) fbVariable.getMembers().get(expr.getFeature().getName());
			final Event event = fb.getType().getInterfaceList().getEventInputs().getFirst();
			yield evaluateFBCall(callReceiver, event, expr.getMappedInputArguments(), expr.getMappedOutputArguments(),
					expr.getMappedInOutArguments());
		}
		case final Event event when expr.isCall() -> evaluateFBCall(fbVariable, event, expr.getMappedInputArguments(),
				expr.getMappedOutputArguments(), expr.getMappedInOutArguments());
		case final ICallable callable when expr.isCall() -> evaluateCall(receiver, callable,
				expr.getMappedInputArguments(), expr.getMappedOutputArguments(), expr.getMappedInOutArguments());
		case null, default -> throw createUnsupportedOperationException(expr.getFeature());
		};
		case null, default -> throw createUnsupportedOperationException(receiver);
		};
	}

	protected Variable<?> evaluateVariable(final STExpression expr) throws EvaluatorException, InterruptedException {
		return switch (expr) {
		case final STArrayAccessExpression stArrayAccessExpression -> evaluateVariable(stArrayAccessExpression);
		case final STBuiltinFeatureExpression stBuiltinFeatureExpression ->
			evaluateVariable(stBuiltinFeatureExpression);
		case final STFeatureExpression stFeatureExpression -> evaluateVariable(stFeatureExpression);
		case final STMemberAccessExpression stMemberAccessExpression -> evaluateVariable(stMemberAccessExpression);
		case null, default -> throw createUnsupportedOperationException(expr);
		};
	}

	protected Variable<?> evaluateVariable(final STFeatureExpression expr)
			throws EvaluatorException, InterruptedException {
		return switch (expr.getFeature()) {
		case final VarDeclaration varDeclaration -> findVariable(varDeclaration);
		case final AdapterDeclaration adapterDeclaration -> findVariable(adapterDeclaration);
		case final STVarDeclaration stVarDeclaration -> findVariable(stVarDeclaration);
		case final FB fb -> findVariable(fb);
		case final ICallable callable when !expr.isCall() -> findVariable(callable);
		case null, default -> throw createUnsupportedOperationException(expr.getFeature());
		};
	}

	protected Variable<?> evaluateVariable(final STBuiltinFeatureExpression expr) {
		return switch (expr.getFeature()) {
		case THIS -> getContext();
		case null, default -> throw createUnsupportedOperationException(expr.getFeature());
		};
	}

	protected Variable<?> evaluateVariable(final STMemberAccessExpression expr)
			throws EvaluatorException, InterruptedException {
		return evaluateVariable(expr.getMember(), evaluateVariable(expr.getReceiver()));
	}

	@SuppressWarnings("unchecked")
	protected Variable<?> evaluateVariable(final STArrayAccessExpression expr)
			throws EvaluatorException, InterruptedException {
		final Variable<?> receiver = evaluateVariable(expr.getReceiver());
		return switch (receiver.getValue()) {
		case final ArrayValue arrayValue -> {
			final List<Integer> indices = new ArrayList<>(expr.getIndex().size());
			for (final STExpression index : expr.getIndex()) {
				indices.add(Integer.valueOf(ValueOperations.asInteger(evaluateExpression(index))));
			}
			yield arrayValue.get(indices);
		}
		case final StringValue unused -> new StringCharacterVariable((Variable<StringValue>) receiver,
				ValueOperations.asInteger(evaluateExpression(expr.getIndex().getFirst())));
		case final WStringValue unused -> new WStringCharacterVariable((Variable<WStringValue>) receiver,
				ValueOperations.asInteger(evaluateExpression(expr.getIndex().getFirst())));
		case null, default -> throw createUnsupportedOperationException(receiver.getValue());
		};
	}

	protected Variable<?> evaluateVariable(final STExpression expr, final Variable<?> receiver)
			throws EvaluatorException, InterruptedException {
		return switch (expr) {
		case final STFeatureExpression featureExpression -> switch (receiver) {
		case final FBVariable fbVariable -> evaluateVariable(featureExpression, fbVariable);
		case final StructVariable structVariable -> evaluateVariable(featureExpression, structVariable);
		case null, default -> throw createUnsupportedOperationException(receiver);
		};
		case final STBuiltinFeatureExpression builtinFeatureExpression ->
			evaluateVariable(builtinFeatureExpression, receiver);
		case final STMultibitPartialExpression multibitPartialExpression ->
			evaluateVariable(multibitPartialExpression, receiver);
		case null, default -> throw createUnsupportedOperationException(expr);
		};
	}

	protected static Variable<?> evaluateVariable(final STFeatureExpression expr, final StructVariable receiver) {
		return receiver.getValue().get(expr.getFeature().getName());
	}

	protected static Variable<?> evaluateVariable(final STFeatureExpression expr, final FBVariable receiver) {
		return receiver.getValue().get(expr.getFeature().getName());
	}

	protected static Variable<?> evaluateVariable(final STBuiltinFeatureExpression expr, final Variable<?> receiver) {
		throw new UnsupportedOperationException(
				MessageFormat.format(Messages.StructuredTextEvaluator_FeatureNotSupported, expr.getFeature().getName(),
						receiver.getType().getName()));
	}

	protected Variable<?> evaluateVariable(final STMultibitPartialExpression expr, final Variable<?> receiver)
			throws EvaluatorException, InterruptedException {
		int index = 0;
		if (expr.getExpression() != null) {
			index = ValueOperations.asInteger(evaluateExpression(expr.getExpression()));
		} else {
			index = expr.getIndex().intValueExact();
		}
		return new PartialVariable<>(receiver, (AnyBitType) expr.getResultType(), index);
	}

	protected Variable<?> newVariable(final INamedElement variable, final Value value)
			throws EvaluatorException, InterruptedException {
		return switch (variable) {
		case final VarDeclaration varDeclaration -> VariableOperations.newVariable(varDeclaration, value);
		case final STVarDeclaration stVarDeclaration ->
			VariableOperations.newVariable(stVarDeclaration.getName(), evaluateType(stVarDeclaration), value);
		case null, default -> throw createUnsupportedOperationException(variable);
		};
	}

	private Value evaluateStandardFunctionCall(final STStandardFunction standardFunction,
			final Map<INamedElement, STCallArgument> inputs, final Map<INamedElement, STCallArgument> outputs)
			throws EvaluatorException, InterruptedException {
		final List<Object> arguments = new ArrayList<>(inputs.size() + outputs.size());
		for (final var arg : inputs.entrySet()) {
			arguments.add(ValueOperations.castValue(evaluateExpression(arg.getValue().getArgument()),
					STCoreUtil.getFeatureType(arg.getKey())));
		}
		for (final var arg : outputs.entrySet()) {
			arguments.add(evaluateVariable(arg.getValue().getArgument()));
		}
		try {
			return Functions.invoke(StandardFunctions.class, standardFunction.getName(), arguments);
		} catch (final Throwable e) {
			throw new EvaluatorException(e.getMessage(), e, this);
		}
	}

	protected Value evaluateCall(final Variable<?> receiver, final ICallable feature,
			final Map<INamedElement, STCallArgument> inputs, final Map<INamedElement, STCallArgument> outputs,
			final Map<INamedElement, STCallArgument> inouts) throws EvaluatorException, InterruptedException {
		final List<Variable<?>> arguments = new ArrayList<>(inputs.size() + inouts.size());
		createArguments(arguments, inputs);
		createArguments(arguments, inouts);
		final Evaluator eval = EvaluatorFactory.createEvaluator(feature, feature.eClass().getInstanceClass(), receiver,
				arguments, this);
		if (eval == null) {
			throw new UnsupportedOperationException(MessageFormat
					.format(Messages.StructuredTextEvaluator_CannotCreateEvaluator, feature.eClass().getName()));
		}
		final Value result = eval.evaluate();
		readArguments(eval, inouts);
		readArguments(eval, outputs);
		return result;
	}

	protected Value evaluateFBCall(final FBVariable receiver, final Event event,
			final Map<INamedElement, STCallArgument> inputs, final Map<INamedElement, STCallArgument> outputs,
			final Map<INamedElement, STCallArgument> inouts) throws EvaluatorException, InterruptedException {
		final FBEvaluator<?> eval = getEvaluator(receiver);
		if (eval == null) {
			throw new UnsupportedOperationException(MessageFormat.format(
					Messages.StructuredTextEvaluator_CannotCreateEvaluator, receiver.getType().eClass().getName()));
		}
		writeArguments(eval, inputs);
		writeArguments(eval, inouts);
		eval.evaluate(event);
		readArguments(eval, inouts);
		readArguments(eval, outputs);
		final Variable<?> returnVariable = eval.getVariables().get(StructuredTextEvaluator.RETURN_VARIABLE_NAME);
		if (returnVariable != null) {
			return returnVariable.getValue();
		}
		return null;
	}

	protected void createArguments(final List<Variable<?>> result, final Map<INamedElement, STCallArgument> arguments)
			throws EvaluatorException, InterruptedException {
		for (final var arg : arguments.entrySet()) {
			if (arg.getValue() != null) {
				result.add(newVariable(arg.getKey(), evaluateExpression(arg.getValue().getArgument())));
			}
		}
	}

	protected void writeArguments(final Evaluator eval, final Map<INamedElement, STCallArgument> arguments)
			throws EvaluatorException, InterruptedException {
		for (final var arg : arguments.entrySet()) {
			if (arg.getValue() != null) {
				eval.getVariables().get(arg.getKey().getName())
						.setValue(evaluateExpression(arg.getValue().getArgument()));
			}
		}
	}

	protected void readArguments(final Evaluator eval, final Map<INamedElement, STCallArgument> arguments)
			throws EvaluatorException, InterruptedException {
		for (final var arg : arguments.entrySet()) {
			switch (arg.getValue()) {
			case null -> {
				// unassigned
			}
			case final STCallNamedOutputArgument namedOutputArgument when namedOutputArgument.isNot() ->
				evaluateVariable(arg.getValue().getArgument()).setValue(
						ValueOperations.bitwiseNot(eval.getVariables().get(arg.getKey().getName()).getValue()));
			default -> evaluateVariable(arg.getValue().getArgument())
					.setValue(eval.getVariables().get(arg.getKey().getName()).getValue());
			}
		}
	}

	protected FBEvaluator<?> getEvaluator(final FBVariable receiver) {
		final Evaluator parent = getParent();
		if (parent instanceof FBEvaluator) {
			if (Evaluator.CONTEXT_NAME.equals(receiver.getName())) {
				return (FBEvaluator<?>) getParent();
			}
			return (FBEvaluator<?>) parent.getChildren().entrySet().stream()
					.filter(entry -> entry.getKey() instanceof FB
							&& Objects.equals(entry.getKey().getName(), receiver.getName()))
					.findFirst().orElseThrow().getValue();
		}
		if (parent instanceof final StructuredTextEvaluator structuredTextEvaluator
				&& parent.getContext() == getContext()) {
			return structuredTextEvaluator.getEvaluator(receiver);
		}
		return createEvaluator(receiver);
	}

	protected FBEvaluator<?> createEvaluator(final FBVariable receiver) {
		final FBEvaluator<?> result = ((FBEvaluator<?>) EvaluatorFactory.createEvaluator(receiver.getType(),
				receiver.getType().eClass().getInstanceClass(), receiver, receiver.getMembers().values(), this));
		result.prepare(); // make sure evaluator is prepared
		return result;
	}

	protected static String getGlobalConstantName(final STVarDeclaration decl) {
		if (decl.eContainer() instanceof final STVarGlobalDeclarationBlock block) {
			if (block.eContainer() instanceof final STGlobalConstsSource source && source.getName() != null
					&& !source.getName().isEmpty()) {
				return source.getName() + PackageNameHelper.PACKAGE_NAME_DELIMITER + decl.getName();
			}
			return decl.getName();
		}
		return null;
	}

	protected static UnsupportedOperationException createUnsupportedOperationException(final Object element) {
		return new UnsupportedOperationException(
				MessageFormat.format(Messages.StructuredTextEvaluator_ElementNotSupported,
						element != null ? element.getClass().getName() : null));
	}

	protected static UnsupportedOperationException createUnsupportedOperationException(final EObject element) {
		return new UnsupportedOperationException(
				MessageFormat.format(Messages.StructuredTextEvaluator_ElementNotSupported,
						element != null ? element.eClass().getName() : null));
	}

	protected static UnsupportedOperationException createUnsupportedOperationException(final Enum<?> element) {
		return new UnsupportedOperationException(MessageFormat
				.format(Messages.StructuredTextEvaluator_ElementNotSupported, element != null ? element.name() : null));
	}

	@Override
	public String getName() {
		return name;
	}

	public static class StructuredTextException extends EvaluatorException {
		private static final long serialVersionUID = 1L;

		public StructuredTextException(final STStatement statement, final Evaluator evaluator) {
			super(statement.eClass().getName(), evaluator);
		}
	}

	public static class ContinueException extends StructuredTextEvaluator.StructuredTextException {
		private static final long serialVersionUID = 1L;

		public ContinueException(final STStatement statement, final Evaluator evaluator) {
			super(statement, evaluator);
		}
	}

	public static class ReturnException extends StructuredTextEvaluator.StructuredTextException {
		private static final long serialVersionUID = 1L;

		public ReturnException(final STStatement statement, final Evaluator evaluator) {
			super(statement, evaluator);
		}
	}

	public static class ExitException extends StructuredTextEvaluator.StructuredTextException {
		private static final long serialVersionUID = 1L;

		public ExitException(final STStatement statement, final Evaluator evaluator) {
			super(statement, evaluator);
		}
	}
}
