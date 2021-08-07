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

import java.math.BigDecimal;
import java.util.function.Function;

import org.eclipse.fordiac.ide.fb.interpreter.api.IEvaluateExpressionVisitor;
import org.eclipse.fordiac.ide.fb.interpreter.api.LambdaVisitor;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BinaryExpression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BinaryOperator;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BoolLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IntLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.PrimaryVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.RealLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StringLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.TimeLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.UnaryExpression;

public class EvaluateExpressionImpl implements IEvaluateExpressionVisitor {

	private static final String UNSUPPORTED_UNARY_OPERATOR_NAN = "Unsupported unary operator because the expression did not return a Number"; //$NON-NLS-1$

	public static Function<Object, Object> of(IEvaluateExpressionVisitor evaluateExpression) {
		return new LambdaVisitor<>().on(BinaryExpression.class).then(evaluateExpression::evaluate)
				.on(UnaryExpression.class).then(evaluateExpression::evaluate).on(BoolLiteral.class)
				.then(evaluateExpression::evaluate).on(StringLiteral.class).then(evaluateExpression::evaluate)
				.on(TimeLiteral.class).then(evaluateExpression::evaluate).on(IntLiteral.class)
				.then(evaluateExpression::evaluate).on(RealLiteral.class).then(evaluateExpression::evaluate)
				.on(AdapterVariable.class).then(evaluateExpression::evaluate).on(PrimaryVariable.class)
				.then(evaluateExpression::evaluate);
	}

	public static Function<Object, Object> of() {
		final var evaluateExpression = new EvaluateExpressionImpl();
		return of(evaluateExpression);
	}

	@Override
	public Object evaluate(BinaryExpression binaryExpression) {
		final var rightObject = of().apply(binaryExpression.getRight());
		final var leftObject = of().apply(binaryExpression.getLeft());
		// String values used equals
		if ((binaryExpression.getOperator().getValue() >= 0) && (binaryExpression.getOperator().getValue() <= 3)) {
			return booleanComparison(leftObject, rightObject, binaryExpression.getOperator());
		} else if ((binaryExpression.getOperator().getValue() >= BinaryOperator.EQ_VALUE)
				&& (binaryExpression.getOperator().getValue() <= BinaryOperator.NE_VALUE)) {
			return binaryComparator(leftObject, rightObject, binaryExpression);
		} else if ((binaryExpression.getOperator().getValue() >= BinaryOperator.LT_VALUE)
				&& (binaryExpression.getOperator().getValue() <= BinaryOperator.GE_VALUE)) {
			return numberComparison(leftObject, rightObject, binaryExpression.getOperator());
		} else if ((binaryExpression.getOperator().getValue() >= BinaryOperator.ADD_VALUE)
				&& (binaryExpression.getOperator().getValue() <= BinaryOperator.POWER_VALUE)) {
			return performBinaryOperation(leftObject, rightObject, binaryExpression.getOperator());
		}
		throw new UnsupportedOperationException("Unsupported Binary Expression: " + binaryExpression.getOperator()); //$NON-NLS-1$
	}

	private static Object performBinaryOperation(Object leftObject, Object rightObject, BinaryOperator binaryOperator) {
		if ((rightObject instanceof Number) && (leftObject instanceof Number)) {
			final var leftNumber = (Number) leftObject;
			final var rightNumber = (Number) rightObject;
			switch (binaryOperator) {
			case ADD:
				return new BigDecimal(rightNumber.toString()).add(new BigDecimal(leftNumber.toString()));
			case SUB:
				return new BigDecimal(leftNumber.toString()).subtract(new BigDecimal(rightNumber.toString()));
			case MUL:
				return new BigDecimal(rightNumber.toString()).multiply(new BigDecimal(leftNumber.toString()));
			case DIV:
				return new BigDecimal(leftNumber.toString()).divide(new BigDecimal(rightNumber.toString()));
			case MOD:
				return new BigDecimal(leftNumber.toString()).remainder(new BigDecimal(rightNumber.toString()));
			case POWER:
				return new BigDecimal(leftNumber.toString()).pow(Integer.parseInt(rightNumber.toString()));
			default:
				throw new IllegalArgumentException("Operator is not defined for Numbers:" + binaryOperator); //$NON-NLS-1$
			}
		}
		throw new IllegalStateException("The binary operation cannot be" //$NON-NLS-1$
				+ "performed because at least one of the inputs are not numbers: " //$NON-NLS-1$
				+ rightObject.toString() + " and " + rightObject.toString()); //$NON-NLS-1$
	}

	private static Object binaryComparator(Object leftObject, Object rightObject, BinaryExpression binaryExpression) {
		if ((rightObject instanceof Number) && (leftObject instanceof Number)) {
			return numberComparison(leftObject, rightObject, binaryExpression.getOperator());
		} else if ((rightObject instanceof Boolean) && (leftObject instanceof Boolean)) {
			return booleanComparison(leftObject, rightObject, binaryExpression.getOperator());
		} else if ((rightObject instanceof String) && (leftObject instanceof String)) {
			return stringComparison((String) leftObject, (String) rightObject, binaryExpression.getOperator());
		} else {
			throw new UnsupportedOperationException("Unsupported Operation, DataTypes are not equals"); //$NON-NLS-1$
		}
	}

	private static Boolean stringComparison(String leftObject, String rightObject, BinaryOperator binaryOperator) {
		switch (binaryOperator) {
		case EQ:
			return Boolean.valueOf(leftObject.equals(rightObject));

		case NE:
			return Boolean.valueOf(!leftObject.equals(rightObject));

		default:
			throw new IllegalArgumentException("Operator is not defined"); //$NON-NLS-1$
		}
	}

	private static Object booleanComparison(Object leftObject, Object rightObject, BinaryOperator binaryOperator) {
		if ((rightObject instanceof Boolean) && (leftObject instanceof Boolean)) {
			final var leftBoolean = (Boolean) leftObject;
			final var rightBoolean = (Boolean) rightObject;
			switch (binaryOperator) {
			case OR:
				return Boolean.valueOf(leftBoolean.booleanValue() || rightBoolean.booleanValue());

			case XOR:
				return Boolean.valueOf((leftBoolean.booleanValue() && !rightBoolean.booleanValue())
						|| (!leftBoolean.booleanValue() && rightBoolean.booleanValue()));

			case AND:
				return Boolean.valueOf(leftBoolean.booleanValue() && rightBoolean.booleanValue());

			case AMPERSAND:
				return Boolean.valueOf(leftBoolean.booleanValue() && rightBoolean.booleanValue());

			default:
				throw new IllegalArgumentException("Operator is not defined"); //$NON-NLS-1$
			}
		} else {
			throw new IllegalArgumentException("Operator can only compare Boolean values"); //$NON-NLS-1$
		}
	}

	private static Object numberComparison(Object leftObject, Object rightObject, BinaryOperator binaryOperator) {
		if ((rightObject instanceof Number) && (leftObject instanceof Number)) {
			final var leftNumber = (Number) leftObject;
			final var rightNumber = (Number) rightObject;
			switch (binaryOperator) {
			case EQ:
				return Boolean.valueOf(
						new BigDecimal(leftNumber.toString()).compareTo(new BigDecimal(rightNumber.toString())) == 0);

			case NE:
				return Boolean.valueOf(
						new BigDecimal(leftNumber.toString()).compareTo(new BigDecimal(rightNumber.toString())) != 0);

			case LT:
				return Boolean.valueOf(
						new BigDecimal(leftNumber.toString()).compareTo(new BigDecimal(rightNumber.toString())) < 0);

			case LE:
				return Boolean.valueOf(
						new BigDecimal(leftNumber.toString()).compareTo(new BigDecimal(rightNumber.toString())) <= 0);

			case GT:
				return Boolean.valueOf(
						new BigDecimal(leftNumber.toString()).compareTo(new BigDecimal(rightNumber.toString())) > 0);

			case GE:
				return Boolean.valueOf(
						new BigDecimal(leftNumber.toString()).compareTo(new BigDecimal(rightNumber.toString())) >= 0);

			default:
				throw new IllegalArgumentException("Operator is not defined"); //$NON-NLS-1$
			}
		} else {
			throw new IllegalArgumentException("Operator can only compare Number values"); //$NON-NLS-1$
		}
	}

	@Override
	public Object evaluate(UnaryExpression unaryExpression) {
		final var expression = of().apply(unaryExpression.getExpression());
		switch (unaryExpression.getOperator()) {
		case MINUS: {
			if (expression instanceof Number) {
				return new BigDecimal(expression.toString()).multiply(BigDecimal.valueOf(-1));
			} else {
				throw new UnsupportedOperationException(UNSUPPORTED_UNARY_OPERATOR_NAN);
			}
		}
		case PLUS: {
			if (expression instanceof Number) {
				return expression;
			} else {
				throw new UnsupportedOperationException(UNSUPPORTED_UNARY_OPERATOR_NAN);
			}
		}
		case NOT: {
			if (expression instanceof Boolean) {
				final boolean boolExpression = ((Boolean) expression).booleanValue();
				return Boolean.valueOf(!boolExpression);
			} else {
				throw new UnsupportedOperationException(UNSUPPORTED_UNARY_OPERATOR_NAN);
			}
		}
		default: {
			throw new UnsupportedOperationException("Unsupported unary operator"); //$NON-NLS-1$
		}
		}
	}

	@Override
	public Boolean evaluate(BoolLiteral boolLiteral) {
		return Boolean.valueOf(boolLiteral.isValue());
	}

	@Override
	public String evaluate(StringLiteral stringLiteral) {
		return stringLiteral.getValue();
	}

	@Override
	public String evaluate(TimeLiteral timeLiteral) {
		return timeLiteral.getLiteral();
	}

	@Override
	public long evaluate(IntLiteral intLiteral) {
		return intLiteral.getValue();
	}

	@Override
	public double evaluate(RealLiteral realLiteral) {
		return realLiteral.getValue();
	}

	@Override
	public Object evaluate(AdapterVariable variable) {
		throw new UnsupportedOperationException("Currently AdapterVariable is not supported"); //$NON-NLS-1$
	}

	@Override
	public Object evaluate(PrimaryVariable primaryVariable) {
		return PrimaryVariableImpl.of(primaryVariable).apply(primaryVariable.getVar().getType());
	}
}
