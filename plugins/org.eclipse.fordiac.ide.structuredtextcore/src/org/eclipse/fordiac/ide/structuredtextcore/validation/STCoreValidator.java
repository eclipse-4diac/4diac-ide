/*******************************************************************************
 * Copyright (c) 2021 - 2023 Primetals Technologies Austria GmbH
 *               2022 - 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *       - adds check for trailing underscore on identifiers
 *       - validation for unqualified FB calls (exactly one input event)
 *       - validation for partial bit access
 *       - validaton for array access
 *       - validaton for string access
 *   Ulzii Jargalsaikhan
 *       - custom validation for identifiers
 *   Martin Jobst
 *       - validation for reserved identifiers
 *       - validation for calls
 *       - validation for unary and binary operators
 *       - fix type validation for literals
 *       - validation for truncated string literals
 *       - validation for (initializer) expression source
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.validation;

import static org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.getFeatureType;
import static org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.isNumericValueValid;
import static org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.isStringValueValid;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.AnyIntType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;
import org.eclipse.fordiac.ide.structuredtextcore.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.converter.STStringValueConverter;
import org.eclipse.fordiac.ide.structuredtextcore.scoping.STStandardFunctionProvider;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.inject.Inject;

public class STCoreValidator extends AbstractSTCoreValidator {

	@Inject
	private STStandardFunctionProvider standardFunctionProvider;

	@Inject
	private STStringValueConverter stringValueConverter;

	public static final String ISSUE_CODE_PREFIX = "org.eclipse.fordiac.ide.structuredtextcore."; //$NON-NLS-1$
	public static final String CONSECUTIVE_UNDERSCORE_IN_IDENTIFIER_ERROR = ISSUE_CODE_PREFIX
			+ "consecutiveUnderscoreInIdentifierError"; //$NON-NLS-1$
	public static final String TRAILING_UNDERSCORE_IN_IDENTIFIER_ERROR = ISSUE_CODE_PREFIX
			+ "identiferEndsInUnderscoreError"; //$NON-NLS-1$
	public static final String NOT_ASSIGNABLE = ISSUE_CODE_PREFIX + "notAssignable"; //$NON-NLS-1$
	public static final String NON_COMPATIBLE_TYPES = ISSUE_CODE_PREFIX + "nonCompatibleTypes"; //$NON-NLS-1$
	public static final String WRONG_NAME_CASE = ISSUE_CODE_PREFIX + "wrongNameCase"; //$NON-NLS-1$
	public static final String RESERVED_IDENTIFIER_ERROR = ISSUE_CODE_PREFIX + "reservedIdentifierError"; //$NON-NLS-1$
	public static final String UNQUALIFIED_FB_CALL_ON_FB_WITH_INPUT_EVENT_SIZE_NOT_ONE = ISSUE_CODE_PREFIX
			+ "UnqualifiedFbCallOnFbWithInputEventSizeNotOne"; //$NON-NLS-1$
	public static final String MIXING_FORMAL_AND_NON_FORMAL_ARGUMENTS = ISSUE_CODE_PREFIX + "mixingFormalAndNonFormal"; //$NON-NLS-1$
	public static final String FEATURE_NOT_CALLABLE = ISSUE_CODE_PREFIX + "featureNotCallable"; //$NON-NLS-1$
	public static final String WRONG_NUMBER_OF_ARGUMENTS = ISSUE_CODE_PREFIX + "wrongNumberOfArguments"; //$NON-NLS-1$
	public static final String OPERATOR_NOT_APPLICABLE = ISSUE_CODE_PREFIX + "operatorNotApplicable"; //$NON-NLS-1$
	public static final String FOR_VARIABLE_NOT_INTEGRAL_TYPE = ISSUE_CODE_PREFIX + "forVariableNotIntegralType"; //$NON-NLS-1$
	public static final String INVALID_NUMERIC_LITERAL = ISSUE_CODE_PREFIX + "invalidNumericLiteral"; //$NON-NLS-1$
	public static final String INVALID_STRING_LITERAL = ISSUE_CODE_PREFIX + "invalidStringLiteral"; //$NON-NLS-1$
	public static final String STANDARD_FUNCTION_WITH_FORMAL_ARGUMENTS = ISSUE_CODE_PREFIX
			+ "standardFunctionWithFormalArguments"; //$NON-NLS-1$
	public static final String LITERAL_IMPLICIT_CONVERSION = ISSUE_CODE_PREFIX + "literalImplicitConversion"; //$NON-NLS-1$
	public static final String ICALLABLE_NOT_VISIBLE = ISSUE_CODE_PREFIX + "iCallableNotVisible"; //$NON-NLS-1$
	public static final String ICALLABLE_HAS_NO_RETURN_TYPE = ISSUE_CODE_PREFIX + "iCallableHasNoReturnType"; //$NON-NLS-1$
	public static final String BIT_ACCESS_INDEX_OUT_OF_RANGE = ISSUE_CODE_PREFIX + "bitAccessIndexOutOfRange"; //$NON-NLS-1$
	public static final String BIT_ACCESS_INDEX_INVALID_EXPRESSION = ISSUE_CODE_PREFIX + "bitAccessInvalidExpression"; //$NON-NLS-1$
	public static final String BIT_ACCESS_INVALID_FOR_TYPE = ISSUE_CODE_PREFIX + "bitAccessInvalidForType"; //$NON-NLS-1$
	public static final String BIT_ACCESS_INVALID_RECEIVER = ISSUE_CODE_PREFIX + "bitAccessInvalidForReceiver"; //$NON-NLS-1$
	public static final String BIT_ACCESS_EXPRESSION_NOT_OF_TYPE_ANY_INT = ISSUE_CODE_PREFIX
			+ "bitAccessExpressionNotOfTypeAnyInt"; //$NON-NLS-1$
	public static final String DUPLICATE_VARIABLE_NAME = ISSUE_CODE_PREFIX + "duplicateVariableName"; //$NON-NLS-1$
	public static final String INDEX_RANGE_TYPE_INVALID = ISSUE_CODE_PREFIX + "indexRangeTypeInvalid"; //$NON-NLS-1$
	public static final String INDEX_RANGE_NOT_A_LITERAL = ISSUE_CODE_PREFIX + "indexRangeNotALiteral"; //$NON-NLS-1$
	public static final String MAX_LENGTH_NOT_ALLOWED = ISSUE_CODE_PREFIX + "maxLengthNotAllowed"; //$NON-NLS-1$
	public static final String MAX_LENGTH_TYPE_INVALID = ISSUE_CODE_PREFIX + "maxLengthTypeInvalid"; //$NON-NLS-1$
	public static final String TOO_MANY_INDICES_GIVEN = ISSUE_CODE_PREFIX + "tooManyIndicesGiven"; //$NON-NLS-1$
	public static final String ARRAY_ACCESS_INVALID = ISSUE_CODE_PREFIX + "arrayAccessInvalid"; //$NON-NLS-1$
	public static final String ARRAY_INDEX_OUT_OF_BOUNDS = ISSUE_CODE_PREFIX + "arrayIndexOutOfBounds"; //$NON-NLS-1$
	public static final String TRUNCATED_LITERAL = ISSUE_CODE_PREFIX + "truncatedLiteral"; //$NON-NLS-1$
	public static final String STRING_INDEX_OUT_OF_BOUNDS = ISSUE_CODE_PREFIX + "stringIndexOutOfBounds"; //$NON-NLS-1$
	public static final String STRING_INDEX_ZERO_OR_LESS_INVALID = ISSUE_CODE_PREFIX + "stringIndexZeroOrLessInvalid"; //$NON-NLS-1$
	public static final String RETURNED_TYPE_IS_VOID = ISSUE_CODE_PREFIX + "returnedTypeIsVoid"; //$NON-NLS-1$
	public static final String LITERAL_REQUIRES_TYPE_SPECIFIER = ISSUE_CODE_PREFIX + "literalRequiresTypeSpecifier"; //$NON-NLS-1$

	private void checkRangeOnValidity(final STBinaryExpression subRangeExpression) {
		final DataType leftType = (DataType) subRangeExpression.getLeft().getResultType();
		if (!(leftType instanceof AnyIntType)) {
			error(MessageFormat.format(Messages.STCoreValidator_IndexRangeTypeInvalid, leftType.getName()),
					subRangeExpression, STCorePackage.Literals.ST_BINARY_EXPRESSION__LEFT, INDEX_RANGE_TYPE_INVALID,
					leftType.getName());
			// Currently we can only process literals
		}
		if (leftType instanceof AnyIntType && !(subRangeExpression.getLeft() instanceof STNumericLiteral)) {
			error(Messages.STCoreValidator_IndexRangeNotALiteral, subRangeExpression,
					STCorePackage.Literals.ST_BINARY_EXPRESSION__LEFT, INDEX_RANGE_NOT_A_LITERAL, leftType.getName());
		}
		final DataType rightType = (DataType) subRangeExpression.getRight().getResultType();
		if (!(rightType instanceof AnyIntType)) {
			error(MessageFormat.format(Messages.STCoreValidator_IndexRangeTypeInvalid, rightType.getName()),
					subRangeExpression, STCorePackage.Literals.ST_BINARY_EXPRESSION__RIGHT, INDEX_RANGE_TYPE_INVALID,
					rightType.getName());
			// Currently we can only process literals
		}
		if (rightType instanceof AnyIntType && !(subRangeExpression.getRight() instanceof STNumericLiteral)) {
			error(Messages.STCoreValidator_IndexRangeNotALiteral, subRangeExpression,
					STCorePackage.Literals.ST_BINARY_EXPRESSION__RIGHT, INDEX_RANGE_NOT_A_LITERAL, leftType.getName());
		}
	}

	@Check
	public void checkIndexRangeValueType(final STVarDeclaration varDeclaration) {
		if (varDeclaration.isArray()) {
			varDeclaration.getRanges().stream().filter(STBinaryExpression.class::isInstance)
			.map(STBinaryExpression.class::cast).forEach(this::checkRangeOnValidity);
		}
	}

	@Check
	public void checkIfMaxSizeOnVarDeclarationAllowed(final STVarDeclaration varDeclaration) {
		if (varDeclaration.getMaxLength() != null && !(varDeclaration.getType() instanceof AnyStringType)) {
			error(Messages.STCoreValidator_NonAnyStringNotMaxLengthSettingNotAllowed, varDeclaration,
					STCorePackage.Literals.ST_VAR_DECLARATION__MAX_LENGTH, MAX_LENGTH_NOT_ALLOWED);
		}
	}

	@Check
	public void checkTypeofStringSizeValue(final STVarDeclaration varDeclaration) {
		if (varDeclaration.getType() instanceof AnyStringType && varDeclaration.getMaxLength() != null
				&& !(varDeclaration.getMaxLength().getResultType() instanceof AnyIntType)) {
			error(MessageFormat.format(Messages.STCoreValidator_MaxLengthTypeInvalid,
					varDeclaration.getMaxLength().getResultType().getName()), varDeclaration,
					STCorePackage.Literals.ST_VAR_DECLARATION__MAX_LENGTH, MAX_LENGTH_TYPE_INVALID,
					varDeclaration.getMaxLength().getResultType().getName());
		}
	}

	@Check
	public void checkStringIndexInBounds(final STArrayAccessExpression accessExpression) {
		final var type = accessExpression.getReceiver().getResultType();
		final var anyStringType = type instanceof AnyStringType ? (AnyStringType) type : null;
		if (anyStringType != null) {
			final var indexExpressions = accessExpression.getIndex();
			if (indexExpressions.size() > 1) {
				// too many indices for a string
				error(MessageFormat.format(Messages.STCoreValidator_TooManyIndicesGivenForStringAccess,
						indexExpressions.size()), accessExpression,
						STCorePackage.Literals.ST_ARRAY_ACCESS_EXPRESSION__INDEX, TOO_MANY_INDICES_GIVEN);
			} else if (indexExpressions.size() == 1 && indexExpressions.get(0) instanceof STNumericLiteral) {
				final var maxLength = anyStringType.getMaxLength();
				final var index = (BigInteger) ((STNumericLiteral) indexExpressions.get(0)).getValue();
				if (anyStringType.isSetMaxLength() && index.compareTo(BigInteger.valueOf(maxLength)) > 0) {
					warning(MessageFormat.format(Messages.STCoreValidator_StringIndexOutOfBounds, index, maxLength),
							accessExpression, STCorePackage.Literals.ST_ARRAY_ACCESS_EXPRESSION__INDEX,
							STRING_INDEX_OUT_OF_BOUNDS);
				}
				if (index.compareTo(BigInteger.ONE) < 0) {
					// Index is invalid, as less than 1
					warning(MessageFormat.format(Messages.STCoreValidator_StringIndexZeroOrLess, index),
							accessExpression, STCorePackage.Literals.ST_ARRAY_ACCESS_EXPRESSION__INDEX,
							STRING_INDEX_ZERO_OR_LESS_INVALID);
				}
			}
			// No index at all, so no check needed
		}
	}

	@Check
	public void checkArrayAccessDimensions(final STArrayAccessExpression accessExpression) {
		final var featureExpression = accessExpression.getReceiver() instanceof STFeatureExpression
				? ((STFeatureExpression) accessExpression.getReceiver()).getFeature()
						: null;
		if (featureExpression instanceof STVarDeclaration) {
			final STVarDeclaration varDeclaration = (STVarDeclaration) featureExpression;
			if (varDeclaration.isArray()) {
				final var indexExpressions = accessExpression.getIndex();
				final var indexCounts = varDeclaration.getCount().size(); // unknown array dimensions [*]
				final var indexRanges = varDeclaration.getRanges().size();
				final var declaredArrayDimensions = Math.max(indexCounts, indexRanges);
				if (indexExpressions.size() > declaredArrayDimensions) {
					for (int i = 0; i < indexExpressions.size() - declaredArrayDimensions; i++) {
						error(MessageFormat.format(Messages.STCoreValidator_TooManyIndicesGiven,
								accessExpression.getIndex().size(), declaredArrayDimensions), accessExpression,
								STCorePackage.Literals.ST_ARRAY_ACCESS_EXPRESSION__INDEX, declaredArrayDimensions + i,
								TOO_MANY_INDICES_GIVEN);
					}
				}
			} else if (!(varDeclaration.getType() instanceof AnyStringType)) {
				// not an array in the first place, but exclude ANY_STRING as they are allowed
				// this type of access for chars
				final var indexExpressions = accessExpression.getIndex();
				for (int i = 0; i < indexExpressions.size(); i++) {
					error(Messages.STCoreValidator_ArrayAccessInvalidOnNonArrayVariable,
							STCorePackage.Literals.ST_ARRAY_ACCESS_EXPRESSION__INDEX, i, ARRAY_ACCESS_INVALID);
				}

			}
		}
	}

	@Check
	public void checkArrayAccessIndices(final STArrayAccessExpression accessExpression) {
		final var type = accessExpression.getReceiver().getResultType();
		final var arrayType = type instanceof ArrayType ? (ArrayType) type : null;

		if (arrayType != null) {
			final var indexExpressions = accessExpression.getIndex();
			for (int i = 0; i < indexExpressions.size(); i++) {
				final STExpression expression = indexExpressions.get(i);
				checkArrayIndexInArrayDimensionBounds(accessExpression, arrayType, i, expression);
				checkArrayAccessIndexType(accessExpression, i, expression);
			}
		}
	}

	private void checkArrayIndexInArrayDimensionBounds(final STArrayAccessExpression accessExpression,
			final ArrayType arrayType, final int i, final STExpression expression) {
		if (expression instanceof STNumericLiteral
				&& ((STNumericLiteral) expression).getResultType() instanceof AnyIntType
				&& arrayType.getSubranges() != null && arrayType.getSubranges().size() > i) {
			final var indexValue = (BigInteger) ((STNumericLiteral) expression).getValue();

			final var subrange = arrayType.getSubranges().get(i);
			if (subrange.isSetLowerLimit() && subrange.isSetUpperLimit()) {
				final BigInteger lowerBound = BigInteger.valueOf(subrange.getLowerLimit());
				final BigInteger upperBound = BigInteger.valueOf(subrange.getUpperLimit());

				if (indexValue.compareTo(lowerBound) < 0 || indexValue.compareTo(upperBound) > 0) {
					error(MessageFormat.format(Messages.STCoreValidator_ArrayIndexOutOfBounds, indexValue, lowerBound,
							upperBound), accessExpression, STCorePackage.Literals.ST_ARRAY_ACCESS_EXPRESSION__INDEX, i,
							ARRAY_INDEX_OUT_OF_BOUNDS);
				}
			}
		}
	}

	private void checkArrayAccessIndexType(final STArrayAccessExpression accessExpression, final int i,
			final STExpression expression) {
		if (!(expression.getResultType() instanceof AnyIntType)) {
			error(MessageFormat.format(Messages.STCoreValidator_IndexAccessTypeInvalid,
					expression.getResultType().getName()), accessExpression,
					STCorePackage.Literals.ST_ARRAY_ACCESS_EXPRESSION__INDEX, i, INDEX_RANGE_TYPE_INVALID);
		}
	}

	@Check
	public void checkConsecutiveUnderscoresInIdentifier(final INamedElement iNamedElement) {
		if (iNamedElement.getName().indexOf("__") != -1) { //$NON-NLS-1$
			error(Messages.STCoreValidator_Consecutive_Underscores_In_Identifier, iNamedElement,
					LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, CONSECUTIVE_UNDERSCORE_IN_IDENTIFIER_ERROR,
					iNamedElement.getName());
		}
	}

	@Check
	public void checkIdentiferForTrailingUnderscore(final INamedElement iNamedElement) {
		if (iNamedElement.getName().endsWith("_")) { //$NON-NLS-1$
			error(Messages.STCoreValidator_Trailing_Underscore_In_Identifier, iNamedElement,
					LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, TRAILING_UNDERSCORE_IN_IDENTIFIER_ERROR,
					iNamedElement.getName());
		}
	}

	@Check
	public void checkReservedIdentifer(final INamedElement iNamedElement) {
		if (StreamSupport.stream(standardFunctionProvider.get().spliterator(), false)
				.anyMatch(func -> func.getName().equalsIgnoreCase(iNamedElement.getName()))) {
			error(Messages.STCoreValidator_Identifier_Is_Reserved, iNamedElement,
					LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, RESERVED_IDENTIFIER_ERROR,
					iNamedElement.getName());
		}
	}

	@Check
	public void checkValidLHS(final STAssignmentStatement statement) {
		if (!STCoreUtil.isAssignable(statement.getLeft())) {
			error(Messages.STCoreValidator_Assignment_Invalid_Left_Side, statement,
					STCorePackage.Literals.ST_ASSIGNMENT_STATEMENT__LEFT, NOT_ASSIGNABLE);
		}
	}

	private boolean isValidCall(final STExpression expression) {
		return expression instanceof STFeatureExpression && ((STFeatureExpression) expression).isCall()
				&& ((STFeatureExpression) expression).getFeature() instanceof ICallable;
	}

	private boolean isInCallStatementOnly(final STFeatureExpression expression) {
		if (expression.eContainer() instanceof STMemberAccessExpression) {
			return expression.eContainer().eContainer() instanceof STCallStatement;
		}
		return expression.eContainer() instanceof STCallStatement;
	}

	private boolean hasReturnType(final ICallable callable) {
		return callable.getReturnType() != null;
	}

	@Check
	public void checkCallWithoutReturnIsOnlyInCallStatement(final STFeatureExpression expression) {
		if (isValidCall(expression) && !hasReturnType((ICallable) expression.getFeature())
				&& !isInCallStatementOnly(expression)) {
			final var callable = (ICallable) expression.getFeature();
			error(MessageFormat.format(Messages.STCoreValidator_AssignmentOfCallWithoutReturnType, callable.getName()),
					expression, STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, RETURNED_TYPE_IS_VOID);
		}
	}

	@Check
	public void checkFeatureExpression(final STFeatureExpression featureExpression) {

		final INamedElement feature = featureExpression.getFeature();
		final INode node = NodeModelUtils.getNode(featureExpression);

		if (node != null && feature != null) {

			final String originalName = feature.getName();
			final String nameInText = node.getText().trim().substring(0, originalName.length());

			if (originalName.equalsIgnoreCase(nameInText) && !originalName.equals(nameInText)) {
				warning(Messages.STCoreValidator_Wrong_Name_Case, STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE,
						WRONG_NAME_CASE, nameInText, originalName);
			}
		}
	}

	@Check
	public void checkAssignmentTypeCompatibility(final STAssignmentStatement statement) {
		final var leftType = statement.getLeft().getResultType();
		final var rightType = statement.getRight().getResultType();
		checkTypeCompatibility(leftType, rightType, STCorePackage.Literals.ST_ASSIGNMENT_STATEMENT__RIGHT);
	}

	@Check
	public void checkInitializerTypeCompatibility(final STVarDeclaration declaration) {
		if (declaration.getDefaultValue() != null) {
			final var type = getFeatureType(declaration);
			final var initializerType = declaration.getDefaultValue().getResultType();
			checkTypeCompatibility(type, initializerType, STCorePackage.Literals.ST_VAR_DECLARATION__DEFAULT_VALUE);
		}
	}

	@Check
	public void checkExpressionSourceTypeCompatibility(final STExpressionSource source) {
		if (source.getExpression() != null) {
			final var type = STCoreUtil.getExpectedType(source.getExpression());
			final var initializerType = source.getExpression().getResultType();
			checkTypeCompatibility(type, initializerType, STCorePackage.Literals.ST_EXPRESSION_SOURCE__EXPRESSION);
		}
	}

	@Check
	public void checkInitializerExpressionSourceTypeCompatibility(final STInitializerExpressionSource source) {
		if (source.getInitializerExpression() != null) {
			final var type = STCoreUtil.getExpectedType(source.getInitializerExpression());
			final var initializerType = source.getInitializerExpression().getResultType();
			checkTypeCompatibility(type, initializerType,
					STCorePackage.Literals.ST_INITIALIZER_EXPRESSION_SOURCE__INITIALIZER_EXPRESSION);
		}
	}

	@Check
	public void checkIfConditionType(final STIfStatement stmt) {
		if (stmt.getCondition() != null) {
			checkTypeCompatibility(ElementaryTypes.BOOL, stmt.getCondition().getResultType(),
					STCorePackage.Literals.ST_IF_STATEMENT__CONDITION);
		}
	}

	@Check
	public void checkWhileConditionType(final STWhileStatement stmt) {
		if (stmt.getCondition() != null) {
			checkTypeCompatibility(ElementaryTypes.BOOL, stmt.getCondition().getResultType(),
					STCorePackage.Literals.ST_WHILE_STATEMENT__CONDITION);
		}
	}

	@Check
	public void checkRepeatConditionType(final STRepeatStatement stmt) {
		if (stmt.getCondition() != null) {
			checkTypeCompatibility(ElementaryTypes.BOOL, stmt.getCondition().getResultType(),
					STCorePackage.Literals.ST_REPEAT_STATEMENT__CONDITION);
		}
	}

	@Check
	public void checkForTypes(final STForStatement stmt) {
		final var type = getFeatureType(stmt.getVariable());
		if (!(type instanceof AnyIntType)) {
			error(MessageFormat.format(Messages.STCoreValidator_For_Variable_Not_Integral_Type, type.getName()), stmt,
					STCorePackage.Literals.ST_FOR_STATEMENT__VARIABLE, FOR_VARIABLE_NOT_INTEGRAL_TYPE);
		}
		if (stmt.getFrom() != null) {
			checkTypeCompatibility(type, stmt.getFrom().getResultType(), STCorePackage.Literals.ST_FOR_STATEMENT__FROM);
		}
		if (stmt.getTo() != null) {
			checkTypeCompatibility(type, stmt.getTo().getResultType(), STCorePackage.Literals.ST_FOR_STATEMENT__TO);
		}
		if (stmt.getBy() != null) {
			checkTypeCompatibility(GenericTypes.ANY_NUM, stmt.getBy().getResultType(),
					STCorePackage.Literals.ST_FOR_STATEMENT__BY);
		}
	}

	@Check
	public void checkCaseConditionType(final STCaseCases stmt) {
		final var type = stmt.getStatement().getSelector().getResultType();
		stmt.getConditions().forEach(condition -> checkTypeCompatibility(type, condition.getResultType(),
				STCorePackage.Literals.ST_CASE_CASES__CONDITIONS, stmt.getConditions().indexOf(condition)));
	}

	@Check
	public void checkCallArguments(final STFeatureExpression expression) {
		final INamedElement feature = expression.getFeature();
		if (feature == null || feature.eIsProxy() || !expression.isCall()) {
			return;
		}

		// check feature is callable
		if (!(feature instanceof ICallable)) {
			error(MessageFormat.format(Messages.STCoreValidator_Feature_Not_Callable, feature.getName()), expression,
					STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, FEATURE_NOT_CALLABLE);
			return;
		}
		final ICallable callable = (ICallable) feature;

		// check unnamed arguments
		if (expression.getParameters().stream().anyMatch(STCallUnnamedArgument.class::isInstance)) {
			// check that arguments are either all formal or non-formal
			if (!expression.getParameters().stream().allMatch(STCallUnnamedArgument.class::isInstance)) {
				error(Messages.STCoreValidator_Mixing_Formal_And_NonFormal, expression,
						STCorePackage.Literals.ST_FEATURE_EXPRESSION__PARAMETERS,
						MIXING_FORMAL_AND_NON_FORMAL_ARGUMENTS);
				return;
			}
			// check argument count matches parameter count for non-formal call
			final int parameterCount = callable.getInputParameters().size() + callable.getInOutParameters().size()
					+ callable.getOutputParameters().size();
			if (expression.getParameters().size() != parameterCount) {
				error(MessageFormat.format(Messages.STCoreValidator_Wrong_Number_Of_Arguments, callable.getName(),
						Integer.toString(parameterCount), Integer.toString(expression.getParameters().size())),
						expression, STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, WRONG_NUMBER_OF_ARGUMENTS);
				return;
			}
			// check non-formal argument kind
			boolean error = false;
			for (int index = callable.getInputParameters().size(); index < expression.getParameters().size(); ++index) {
				final STCallUnnamedArgument arg = (STCallUnnamedArgument) expression.getParameters().get(index);
				if (!STCoreUtil.isAssignable(arg.getArgument())) {
					error(Messages.STCoreValidator_Argument_Not_Assignable, expression,
							STCorePackage.Literals.ST_FEATURE_EXPRESSION__PARAMETERS, index, NOT_ASSIGNABLE);
					error = true;
				}
			}
			if (error) {
				return;
			}
		} else if (!expression.getParameters().isEmpty()) {
			// check no formal arguments for standard functions
			if (callable instanceof STStandardFunction) {
				error(MessageFormat.format(
						Messages.STCoreValidator_Attempting_To_Call_Standard_Function_With_Formal_Arguments,
						callable.getName()), expression, STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE,
						STANDARD_FUNCTION_WITH_FORMAL_ARGUMENTS);
				return;
			}
			// check formal argument kind
			boolean error = false;
			for (int index = 0; index < expression.getParameters().size(); ++index) {
				final STCallArgument elem = expression.getParameters().get(index);
				if (elem instanceof STCallNamedInputArgument) {
					final STCallNamedInputArgument arg = (STCallNamedInputArgument) elem;
					if (callable.getInOutParameters().contains(arg.getParameter())
							&& !STCoreUtil.isAssignable(arg.getArgument())) {
						error(Messages.STCoreValidator_Argument_Not_Assignable, expression,
								STCorePackage.Literals.ST_FEATURE_EXPRESSION__PARAMETERS, index, NOT_ASSIGNABLE);
						error = true;
					}
				} else if (elem instanceof STCallNamedOutputArgument) {
					final STCallNamedOutputArgument arg = (STCallNamedOutputArgument) elem;
					if (!STCoreUtil.isAssignable(arg.getArgument())) {
						error(Messages.STCoreValidator_Argument_Not_Assignable, expression,
								STCorePackage.Literals.ST_FEATURE_EXPRESSION__PARAMETERS, index, NOT_ASSIGNABLE);
						error = true;
					}
				}
			}
			if (error) {
				return;
			}
		}

		// check argument type
		expression.getMappedInputArguments().forEach((param, arg) -> {
			if (arg != null) {
				checkTypeCompatibility(getFeatureType(param), arg.getResultType(),
						STCorePackage.Literals.ST_FEATURE_EXPRESSION__PARAMETERS,
						expression.getParameters().indexOf(arg.eContainer()));
			}
		});
		expression.getMappedOutputArguments().forEach((param, arg) -> {
			if (arg != null) {
				checkTypeCompatibility(arg.getResultType(), getFeatureType(param),
						STCorePackage.Literals.ST_FEATURE_EXPRESSION__PARAMETERS,
						expression.getParameters().indexOf(arg));
			}
		});
		expression.getMappedInOutArguments().forEach((param, arg) -> {
			if (arg != null) {
				final INamedElement destination = getFeatureType(param);
				final INamedElement source = arg.getResultType();
				if (!source.equals(destination)) { // in&out requires strict equality
					error(MessageFormat.format(Messages.STCoreValidator_Non_Compatible_Types, source.getName(),
							destination.getName()), STCorePackage.Literals.ST_FEATURE_EXPRESSION__PARAMETERS,
							expression.getParameters().indexOf(arg), NON_COMPATIBLE_TYPES, source.getName(),
							destination.getName());
				}
			}
		});
	}

	@Check
	public void checkUnaryExpressionOperator(final STUnaryExpression expression) {
		final var resultType = expression.getExpression().getResultType();
		if (!STCoreUtil.isApplicableTo(expression.getOp(), resultType)) {
			error(MessageFormat.format(Messages.STCoreValidator_UnaryOperator_Not_Applicable,
					expression.getOp().getLiteral(), resultType.getName()),
					STCorePackage.Literals.ST_UNARY_EXPRESSION__OP, OPERATOR_NOT_APPLICABLE);
		}
	}

	@Check
	public void checkBinaryExpressionOperator(final STBinaryExpression expression) {
		final var leftResultType = expression.getLeft().getResultType();
		final var rightResultType = expression.getRight().getResultType();
		if (!STCoreUtil.isApplicableTo(expression.getOp(), leftResultType, rightResultType)) {
			error(MessageFormat.format(Messages.STCoreValidator_BinaryOperator_Not_Applicable,
					expression.getOp().getLiteral(), leftResultType.getName(), rightResultType.getName()),
					STCorePackage.Literals.ST_BINARY_EXPRESSION__OP, OPERATOR_NOT_APPLICABLE);
		}
	}

	@Check
	public void checkCalledFBWithoutEventSpecificerHasOnlyOneInputEvent(final STFeatureExpression expression) {
		if (expression.isCall() && expression.getFeature() instanceof FB) {
			final FB functionBlock = (FB) expression.getFeature();
			if (functionBlock.getInterface().getEventInputs().size() != 1) {
				error(Messages.STCoreValidator_Unqualified_FB_Call_On_FB_With_Input_Event_Size_Not_One,
						STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE,
						UNQUALIFIED_FB_CALL_ON_FB_WITH_INPUT_EVENT_SIZE_NOT_ONE);
			}
		}
	}

	@Check
	public void checkNumericLiteral(final STNumericLiteral expression) {
		final var type = (DataType) expression.getResultType();
		final var expectedType = STCoreUtil.getExpectedType(expression);
		if (!isNumericValueValid(type, expression.getValue())) {
			error(MessageFormat.format(Messages.STCoreValidator_Invalid_Literal, type.getName(),
					NumericValueConverter.INSTANCE.toString(expression.getValue())),
					STCorePackage.Literals.ST_NUMERIC_LITERAL__VALUE, INVALID_NUMERIC_LITERAL);
		} else if (expectedType instanceof DataType && IecTypes.GenericTypes.isAnyType((DataType) expectedType)
				&& expression.getType() == null) {
			error(MessageFormat.format(Messages.STCoreValidator_Literal_Requires_Type_Specifier,
					expectedType.getName()), STCorePackage.Literals.ST_NUMERIC_LITERAL__VALUE,
					LITERAL_REQUIRES_TYPE_SPECIFIER);
		} else if (expectedType instanceof DataType && !type.eClass().equals(expectedType.eClass())
				&& ((DataType) expectedType).isAssignableFrom(type)) {
			warning(MessageFormat.format(Messages.STCoreValidator_Implicit_Conversion_In_Literal, type.getName(),
					expectedType.getName()), null, LITERAL_IMPLICIT_CONVERSION);
		}
	}

	@Check
	public void checkStringLiteral(final STStringLiteral expression) {
		final var type = (DataType) expression.getResultType();
		final var expectedType = STCoreUtil.getExpectedType(expression);
		if (!isStringValueValid(type, expression.getValue())) {
			error(MessageFormat.format(Messages.STCoreValidator_Invalid_Literal, type.getName(),
					stringValueConverter.toString(expression.getValue())),
					STCorePackage.Literals.ST_STRING_LITERAL__VALUE, INVALID_STRING_LITERAL);
		} else if (expectedType instanceof DataType && IecTypes.GenericTypes.isAnyType((DataType) expectedType)
				&& expression.getType() == null) {
			error(MessageFormat.format(Messages.STCoreValidator_Literal_Requires_Type_Specifier,
					expectedType.getName()), STCorePackage.Literals.ST_STRING_LITERAL__VALUE,
					LITERAL_REQUIRES_TYPE_SPECIFIER);
		} else if (expectedType instanceof AnyStringType && ((AnyStringType) expectedType).isSetMaxLength()
				&& expression.getValue().length() > ((AnyStringType) expectedType).getMaxLength()) {
			warning(MessageFormat.format(Messages.STCoreValidator_String_Literal_Truncated,
					Integer.toString(((AnyStringType) expectedType).getMaxLength())), null, TRUNCATED_LITERAL);
		} else if (expectedType instanceof DataType && !type.eClass().equals(expectedType.eClass())
				&& ((DataType) expectedType).isAssignableFrom(type)) {
			warning(MessageFormat.format(Messages.STCoreValidator_Implicit_Conversion_In_Literal, type.getName(),
					expectedType.getName()), null, LITERAL_IMPLICIT_CONVERSION);
		}
	}

	@Check
	public void checkReturnValueCanOnlyBeAssignedToCurrentICallable(final STFeatureExpression expression) {
		final var feature = expression.getFeature();
		if (feature instanceof ICallable && !expression.isCall() && !(feature instanceof FB)) {
			final var callable = (ICallable) feature;
			final var containingElement = getICallableContainer(expression);
			if (callable != containingElement) {
				error(MessageFormat.format(Messages.STCoreValidator_NameNotVisible, callable.getName()),
						STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, ICALLABLE_NOT_VISIBLE);
			} else if (callable.getReturnType() == null) {
				error(MessageFormat.format(Messages.STCoreValidator_CallableHasNoReturnType, callable.getName()),
						STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, ICALLABLE_HAS_NO_RETURN_TYPE);
			}
		}
	}

	@Check
	public void checkIndexOfBitwiseAccessExpression(final STMultibitPartialExpression expression) {
		final STMemberAccessExpression memberAccessExpr = (STMemberAccessExpression) expression.eContainer();
		final var receiverExpression = memberAccessExpr.getReceiver();
		final DataType accessType = (DataType) memberAccessExpr.getResultType();
		final DataType receiverType = (DataType) receiverExpression.getResultType();
		// Valid target receiver is a variable or a function name usable as variable
		if (memberAccessExpr.getReceiver() instanceof STMemberAccessExpression
				|| (memberAccessExpr.getReceiver() instanceof STFeatureExpression
						&& !((STFeatureExpression) memberAccessExpr.getReceiver()).isCall())) {
			checkMultibitPartialExpression(expression, accessType, receiverType);

		} else {
			error(Messages.STCoreValidator_BitAccessInvalidForReciever,
					STCorePackage.Literals.ST_MULTIBIT_PARTIAL_EXPRESSION__EXPRESSION, BIT_ACCESS_INVALID_RECEIVER);
		}
	}

	@Check
	public void checkDuplicateVariableNames(final STVarDeclaration varDeclaration) {
		final var varDeclarations = EcoreUtil2.getAllContentsOfType(varDeclaration.eContainer().eContainer(),
				STVarDeclaration.class);
		for (final STVarDeclaration declaration : varDeclarations) {
			if (declaration != varDeclaration && declaration.getName().equalsIgnoreCase(varDeclaration.getName())) {
				error(MessageFormat.format(Messages.STCoreValidator_Duplicate_Variable_Name, varDeclaration.getName()),
						varDeclaration, LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, DUPLICATE_VARIABLE_NAME);
			}
		}
	}

	/*
	 * Here we already know that we have a MultibitPartialExpression. This function
	 * checks bound on static access (without "()")
	 */
	private void checkMultibitPartialExpression(final STMultibitPartialExpression expression,
			final DataType accessorType, final DataType receiverType) {
		if (receiverType instanceof AnyBitType) {
			if (expression.getIndex() != null
					&& expression.getIndex().intValue() > getBitAccessMaxIndex(accessorType, receiverType)) {
				error(MessageFormat.format(Messages.STCoreValidator_BitAccessOutOfRange,
						expression.getSpecifier().getLiteral() + expression.getIndex()),
						STCorePackage.Literals.ST_MULTIBIT_PARTIAL_EXPRESSION__EXPRESSION,
						BIT_ACCESS_INDEX_OUT_OF_RANGE);
			} else if (expression.getExpression() != null) {
				final DataType multiBitAccessExpressionType = (DataType) expression.getExpression().getResultType();
				if (!(multiBitAccessExpressionType instanceof AnyIntType)) {
					error(MessageFormat.format(Messages.STCoreValidator_BitAccessExpressionNotOfTypeAnyInt,
							multiBitAccessExpressionType.getName()),
							STCorePackage.Literals.ST_MULTIBIT_PARTIAL_EXPRESSION__EXPRESSION,
							BIT_ACCESS_EXPRESSION_NOT_OF_TYPE_ANY_INT);
				}
			}
		} else { // member is an STMultibitPartialExpression but receiver is not ANY_BIT
			error(MessageFormat.format(Messages.STCoreValidator_BitAccessInvalidForType, receiverType.getName()),
					STCorePackage.Literals.ST_MULTIBIT_PARTIAL_EXPRESSION__EXPRESSION, BIT_ACCESS_INVALID_FOR_TYPE);
		}
	}

	protected static int getBitAccessMaxIndex(final DataType accessorType, final DataType receiverType) {
		if (accessorType instanceof AnyBitType && receiverType instanceof AnyBitType) {
			final var bitSize = ((AnyBitType) receiverType).getBitSize();
			final var bitFactor = ((AnyBitType) accessorType).getBitSize();
			return bitFactor > 0 && bitSize > bitFactor ? bitSize / bitFactor - 1 : -1;
		}
		return -1;
	}

	protected static ICallable getICallableContainer(final EObject eObject) {
		for (EObject parent = eObject.eContainer(); parent != null; parent = parent.eContainer()) {
			if (parent instanceof ICallable) {
				return (ICallable) parent;
			}
		}
		return null;
	}

	protected void checkTypeCompatibility(final INamedElement destination, final INamedElement source,
			final EStructuralFeature feature) {
		checkTypeCompatibility(destination, source, feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
	}

	protected void checkTypeCompatibility(final INamedElement destination, final INamedElement source,
			final EStructuralFeature feature, final int index) {
		if (destination instanceof DataType && source instanceof DataType) {
			checkTypeCompatibility((DataType) destination, (DataType) source, feature, index);
		} else if (source != null && destination != null) {
			error(MessageFormat.format(Messages.STCoreValidator_Non_Compatible_Types, source.getName(),
					destination.getName()), feature, index, NON_COMPATIBLE_TYPES, source.getName(),
					destination.getName());
		}
	}

	protected void checkTypeCompatibility(final DataType destination, final DataType source,
			final EStructuralFeature feature, final int index) {
		if (!(destination.isAssignableFrom(source)
				|| (GenericTypes.isAnyType(source) && source.isAssignableFrom(destination)))) {
			error(MessageFormat.format(Messages.STCoreValidator_Non_Compatible_Types, source.getName(),
					destination.getName()), feature, index, NON_COMPATIBLE_TYPES, source.getName(),
					destination.getName());
		}
	}
}
