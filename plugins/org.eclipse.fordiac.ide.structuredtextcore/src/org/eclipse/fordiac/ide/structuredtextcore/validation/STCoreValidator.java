/*******************************************************************************
 * Copyright (c) 2021, 2024 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
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
 *       - validation for array access
 *       - validation for string access
 *       - validation for assignability (cannot assign to consts/inputs)
 *       - validation that array access operator is only applicable to vars
 *   Ulzii Jargalsaikhan
 *       - custom validation for identifiers
 *   Martin Jobst
 *       - validation for reserved identifiers
 *       - validation for calls
 *       - validation for unary and binary operators
 *       - fix type validation for literals
 *       - validation for truncated string literals
 *       - validation for (initializer) expression source
 *       - validation for unused or wildcard imports
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.validation;

import static org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.getAccessMode;
import static org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.getFeaturePath;
import static org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.getFeatureType;
import static org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.isCallableVarargs;
import static org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.isNumericValueValid;
import static org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.isStringValueValid;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.AnyIntType;
import org.eclipse.fordiac.ide.model.data.AnyRealType;
import org.eclipse.fordiac.ide.model.data.AnySignedType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.AnyUnsignedType;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.Subrange;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;
import org.eclipse.fordiac.ide.structuredtextcore.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.converter.STStringValueConverter;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.fordiac.ide.structuredtextcore.scoping.STStandardFunctionProvider;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAttribute;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STPragma;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.AccessMode;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.Severity;
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
	public static final String VALUE_NOT_ASSIGNABLE = ISSUE_CODE_PREFIX + "valueNotAssignable"; //$NON-NLS-1$
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
	public static final String INDEX_RANGE_EXPRESSION_INVALID = ISSUE_CODE_PREFIX + "indexRangeExpressionInvalid"; //$NON-NLS-1$
	public static final String MAX_LENGTH_NOT_ALLOWED = ISSUE_CODE_PREFIX + "maxLengthNotAllowed"; //$NON-NLS-1$
	public static final String MAX_LENGTH_TYPE_INVALID = ISSUE_CODE_PREFIX + "maxLengthTypeInvalid"; //$NON-NLS-1$
	public static final String TOO_MANY_INDICES_GIVEN = ISSUE_CODE_PREFIX + "tooManyIndicesGiven"; //$NON-NLS-1$
	public static final String ARRAY_ACCESS_INVALID = ISSUE_CODE_PREFIX + "arrayAccessInvalid"; //$NON-NLS-1$
	public static final String ARRAY_ACCESS_RECEIVER_INVALID = ISSUE_CODE_PREFIX + "arrayAccessReceiverInvalid"; //$NON-NLS-1$
	public static final String ARRAY_INDEX_OUT_OF_BOUNDS = ISSUE_CODE_PREFIX + "arrayIndexOutOfBounds"; //$NON-NLS-1$
	public static final String TRUNCATED_LITERAL = ISSUE_CODE_PREFIX + "truncatedLiteral"; //$NON-NLS-1$
	public static final String STRING_INDEX_OUT_OF_BOUNDS = ISSUE_CODE_PREFIX + "stringIndexOutOfBounds"; //$NON-NLS-1$
	public static final String RETURNED_TYPE_IS_VOID = ISSUE_CODE_PREFIX + "returnedTypeIsVoid"; //$NON-NLS-1$
	public static final String LITERAL_REQUIRES_TYPE_SPECIFIER = ISSUE_CODE_PREFIX + "literalRequiresTypeSpecifier"; //$NON-NLS-1$
	public static final String INSUFFICIENT_ARRAY_DIMENSIONS = ISSUE_CODE_PREFIX + "insufficientArrayDimensions"; //$NON-NLS-1$
	public static final String UNNECESSARY_CONVERSION = ISSUE_CODE_PREFIX + "unnecessaryConversion"; //$NON-NLS-1$
	public static final String UNNECESSARY_WIDE_CONVERSION = ISSUE_CODE_PREFIX + "unnecessaryWideConversion"; //$NON-NLS-1$
	public static final String UNNECESSARY_NARROW_CONVERSION = ISSUE_CODE_PREFIX + "unnecessaryNarrowConversion"; //$NON-NLS-1$
	public static final String UNNECESSARY_LITERAL_CONVERSION = ISSUE_CODE_PREFIX + "unnecessaryLiteralConversion"; //$NON-NLS-1$
	public static final String NON_CONSTANT_DECLARATION = ISSUE_CODE_PREFIX + "nonConstantInInitializer"; //$NON-NLS-1$
	public static final String MAYBE_NOT_INITIALIZED = ISSUE_CODE_PREFIX + "maybeNotInitialized"; //$NON-NLS-1$
	public static final String FOR_CONTROL_VARIABLE_MODIFICATION = ISSUE_CODE_PREFIX + "forControlVariableModification"; //$NON-NLS-1$
	public static final String FOR_CONTROL_VARIABLE_NON_TEMPORARY = ISSUE_CODE_PREFIX
			+ "forControlVariableNonTemporary"; //$NON-NLS-1$
	public static final String FOR_CONTROL_VARIABLE_UNDEFINED = ISSUE_CODE_PREFIX + "forControlVariableUndefined"; //$NON-NLS-1$
	public static final String EXIT_NOT_IN_LOOP = ISSUE_CODE_PREFIX + "exitNotInLoop"; //$NON-NLS-1$
	public static final String CONTINUE_NOT_IN_LOOP = ISSUE_CODE_PREFIX + "continueNotInLoop"; //$NON-NLS-1$
	public static final String NESTED_ASSIGNMENT = ISSUE_CODE_PREFIX + "nestedAssignment"; //$NON-NLS-1$
	public static final String INVALID_IMPORT = ISSUE_CODE_PREFIX + "invalidImport"; //$NON-NLS-1$
	public static final String WILDCARD_IMPORT = ISSUE_CODE_PREFIX + "wildcardImport"; //$NON-NLS-1$
	public static final String UNUSED_IMPORT = ISSUE_CODE_PREFIX + "unusedImport"; //$NON-NLS-1$
	public static final String DUPLICATE_ATTRIBUTE = ISSUE_CODE_PREFIX + "duplicateAttribute"; //$NON-NLS-1$

	private static final Pattern CONVERSION_FUNCTION_PATTERN = Pattern.compile("[a-zA-Z]+_TO_[a-zA-Z]+"); //$NON-NLS-1$
	private static final Pattern IDENTIFIER_CONSECUTIVE_UNDERSCORES_PATTERN = Pattern.compile("_{2,}[^_]"); //$NON-NLS-1$

	private void checkRangeOnValidity(final STExpression expression) {
		if (expression instanceof final STBinaryExpression subRangeExpression) {
			final DataType leftType = (DataType) subRangeExpression.getLeft().getResultType();
			if (!(leftType instanceof AnyIntType)) {
				error(MessageFormat.format(Messages.STCoreValidator_IndexRangeTypeInvalid, leftType.getName()),
						subRangeExpression, STCorePackage.Literals.ST_BINARY_EXPRESSION__LEFT, INDEX_RANGE_TYPE_INVALID,
						leftType.getName());
				// Currently we can only process literals
			}
			final DataType rightType = (DataType) subRangeExpression.getRight().getResultType();
			if (!(rightType instanceof AnyIntType)) {
				error(MessageFormat.format(Messages.STCoreValidator_IndexRangeTypeInvalid, rightType.getName()),
						subRangeExpression, STCorePackage.Literals.ST_BINARY_EXPRESSION__RIGHT,
						INDEX_RANGE_TYPE_INVALID, rightType.getName());
				// Currently we can only process literals
			}
		} else {
			error(Messages.STCoreValidator_IndexRangeExpressionInvalid, expression, null,
					INDEX_RANGE_EXPRESSION_INVALID);
		}
	}

	@Check
	public void checkIndexRangeValueType(final STVarDeclaration varDeclaration) {
		if (varDeclaration.isArray()) {
			varDeclaration.getRanges().stream().forEach(this::checkRangeOnValidity);
		}
	}

	@Check
	public void checkIndexRangeValueType(final STTypeDeclaration typeDeclaration) {
		if (typeDeclaration.isArray()) {
			typeDeclaration.getRanges().stream().forEach(this::checkRangeOnValidity);
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
	public void checkIfMaxSizeOnVarDeclarationAllowed(final STTypeDeclaration typeDeclaration) {
		if (typeDeclaration.getMaxLength() != null && !(typeDeclaration.getType() instanceof AnyStringType)) {
			error(Messages.STCoreValidator_NonAnyStringNotMaxLengthSettingNotAllowed, typeDeclaration,
					STCorePackage.Literals.ST_TYPE_DECLARATION__MAX_LENGTH, MAX_LENGTH_NOT_ALLOWED);
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
	public void checkTypeofStringSizeValue(final STTypeDeclaration typeDeclaration) {
		if (typeDeclaration.getType() instanceof AnyStringType && typeDeclaration.getMaxLength() != null
				&& !(typeDeclaration.getMaxLength().getResultType() instanceof AnyIntType)) {
			error(MessageFormat.format(Messages.STCoreValidator_MaxLengthTypeInvalid,
					typeDeclaration.getMaxLength().getResultType().getName()), typeDeclaration,
					STCorePackage.Literals.ST_TYPE_DECLARATION__MAX_LENGTH, MAX_LENGTH_TYPE_INVALID,
					typeDeclaration.getMaxLength().getResultType().getName());
		}
	}

	@Check
	public void checkArrayAccessExpression(final STArrayAccessExpression accessExpression) {
		final INamedElement receiverType = accessExpression.getReceiver().getResultType();
		if (receiverType instanceof final ArrayType arrayType) {
			checkArrayAccessIndices(accessExpression, arrayType);
		} else if (receiverType instanceof final AnyStringType stringType) {
			checkStringAccessIndices(accessExpression, stringType);
		} else {
			error(Messages.STCoreValidator_ArrayAccessReceiverIsInvalid,
					STCorePackage.Literals.ST_ARRAY_ACCESS_EXPRESSION__RECEIVER, ARRAY_ACCESS_RECEIVER_INVALID);
		}
	}

	protected void checkArrayAccessIndices(final STArrayAccessExpression accessExpression,
			final ArrayType receiverType) {
		IntStream.range(0, accessExpression.getIndex().size()).forEachOrdered(index -> {
			final STExpression indexExpression = accessExpression.getIndex().get(index);
			final INamedElement resultType = indexExpression.getResultType();
			checkTypeCompatibility(GenericTypes.ANY_INT, resultType,
					STCorePackage.Literals.ST_ARRAY_ACCESS_EXPRESSION__INDEX, index);
			if (index < receiverType.getSubranges().size()) {
				try {
					final int indexValue = STCoreUtil.asConstantInt(indexExpression);
					final Subrange subrange = receiverType.getSubranges().get(index);
					if (isArrayIndexOutOfBounds(indexValue, subrange)) {
						error(MessageFormat.format(Messages.STCoreValidator_ArrayIndexOutOfBounds,
								Integer.toString(indexValue), Integer.toString(subrange.getLowerLimit()),
								Integer.toString(subrange.getUpperLimit())),
								STCorePackage.Literals.ST_ARRAY_ACCESS_EXPRESSION__INDEX, index,
								ARRAY_INDEX_OUT_OF_BOUNDS);
					}
				} catch (final ArithmeticException e) {
					// ignore (non-constant index)
				}
			} else {
				error(MessageFormat.format(Messages.STCoreValidator_TooManyIndicesGiven,
						Integer.toString(accessExpression.getIndex().size()),
						Integer.toString(receiverType.getSubranges().size()), receiverType.getName()),
						STCorePackage.Literals.ST_ARRAY_ACCESS_EXPRESSION__INDEX, index, TOO_MANY_INDICES_GIVEN);
			}
		});
	}

	protected static boolean isArrayIndexOutOfBounds(final int indexValue, final Subrange subrange) {
		return (subrange.isSetLowerLimit() && indexValue < subrange.getLowerLimit())
				|| (subrange.isSetUpperLimit() && indexValue > subrange.getUpperLimit());
	}

	protected void checkStringAccessIndices(final STArrayAccessExpression accessExpression,
			final AnyStringType receiverType) {
		IntStream.range(0, accessExpression.getIndex().size()).forEachOrdered(index -> {
			final STExpression indexExpression = accessExpression.getIndex().get(index);
			final INamedElement resultType = indexExpression.getResultType();
			checkTypeCompatibility(GenericTypes.ANY_INT, resultType,
					STCorePackage.Literals.ST_ARRAY_ACCESS_EXPRESSION__INDEX, index);
			if (index < 1) {
				try {
					final int indexValue = STCoreUtil.asConstantInt(indexExpression);
					if (isStringIndexOutOfBounds(indexValue, receiverType)) {
						warning(MessageFormat.format(Messages.STCoreValidator_StringIndexOutOfBounds,
								Integer.toString(indexValue), receiverType.getName()),
								STCorePackage.Literals.ST_ARRAY_ACCESS_EXPRESSION__INDEX, index,
								STRING_INDEX_OUT_OF_BOUNDS);
					}
				} catch (final ArithmeticException e) {
					// ignore (non-constant index)
				}
			} else {
				error(MessageFormat.format(Messages.STCoreValidator_TooManyIndicesGiven,
						Integer.toString(accessExpression.getIndex().size()), Integer.toString(1),
						receiverType.getName()), STCorePackage.Literals.ST_ARRAY_ACCESS_EXPRESSION__INDEX, index,
						TOO_MANY_INDICES_GIVEN);
			}
		});
	}

	protected static boolean isStringIndexOutOfBounds(final int indexValue, final AnyStringType receiverType) {
		return indexValue < 1 || (receiverType.isSetMaxLength() && indexValue > receiverType.getMaxLength());
	}

	@Check
	public void checkConsecutiveUnderscoresInIdentifier(final INamedElement iNamedElement) {
		if (IDENTIFIER_CONSECUTIVE_UNDERSCORES_PATTERN.matcher(iNamedElement.getName()).find()) {
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
	public void checkValidLHS(final STAssignment expression) {
		accept(isAssignable(expression.getLeft()), expression, STCorePackage.Literals.ST_ASSIGNMENT__LEFT);
	}

	@Check
	public void checkCallWithoutReturnIsOnlyInCallStatement(final STFeatureExpression expression) {
		if (expression.getFeature() instanceof final ICallable callable && callable.getReturnType() == null
				&& expression.isCall() && STCoreUtil.getAccessMode(expression) != AccessMode.NONE) {
			error(MessageFormat.format(Messages.STCoreValidator_AssignmentOfCallWithoutReturnType, callable.getName()),
					expression, STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, RETURNED_TYPE_IS_VOID);
		}
	}

	@Check
	public void checkFeatureExpression(final STFeatureExpression featureExpression) {
		final INamedElement feature = featureExpression.getFeature();
		if (feature != null && !feature.eIsProxy()) {
			final String originalName = feature.getName();
			if (originalName != null) {
				final List<INode> nodes = NodeModelUtils.findNodesForFeature(featureExpression,
						STCorePackage.eINSTANCE.getSTFeatureExpression_Feature());
				if (!nodes.isEmpty()) {
					final INode node = nodes.get(0);
					final String nameInText = node.getRootNode().getText().substring(node.getOffset(),
							node.getEndOffset());
					if (!originalName.equals(nameInText) && originalName.equalsIgnoreCase(nameInText)) {
						warning(Messages.STCoreValidator_Wrong_Name_Case,
								STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, WRONG_NAME_CASE, nameInText,
								originalName);
					}
				}
			}
		}
	}

	@Check
	public void checkUnnecessaryConversion(final STFeatureExpression featureExpression) {
		final INamedElement feature = featureExpression.getFeature();
		if (feature instanceof final STStandardFunction standardFunction
				&& CONVERSION_FUNCTION_PATTERN.matcher(feature.getName()).matches()
				&& standardFunction.getInputParameters().size() == 1 && featureExpression.getParameters().size() == 1) {
			final STCallArgument argument = featureExpression.getParameters().get(0);
			final INamedElement argumentType = argument.getResultType();
			final INamedElement expectedArgumentType = STCoreUtil.getExpectedType(argument.getArgument());
			final INamedElement returnType = featureExpression.getResultType();
			final INamedElement expectedReturnType = STCoreUtil.getExpectedType(featureExpression);

			if (argumentType instanceof final DataType argumentDataType
					&& expectedArgumentType instanceof final DataType expectedArgumentDataType
					&& returnType instanceof final DataType returnDataType
					&& expectedReturnType instanceof final DataType expectedReturnDataType) {
				checkUnnecessaryConversion(argument, argumentDataType, expectedArgumentDataType, returnDataType,
						expectedReturnDataType);
			}
		}
	}

	protected void checkUnnecessaryConversion(final STCallArgument argument, final DataType argumentDataType,
			final DataType expectedArgumentDataType, final DataType returnDataType,
			final DataType expectedReturnDataType) {
		if (expectedReturnDataType.isAssignableFrom(argumentDataType)
				&& !isCastSemanticallyRelevant(argumentDataType, returnDataType)) {
			warning(MessageFormat.format(Messages.STCoreValidator_UnnecessaryConversion,
					expectedArgumentDataType.getName(), returnDataType.getName()),
					STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, UNNECESSARY_CONVERSION,
					expectedArgumentDataType.getName(), returnDataType.getName());
		} else if (!expectedArgumentDataType.eClass().equals(argumentDataType.eClass())
				&& expectedArgumentDataType.isAssignableFrom(argumentDataType)
				&& isBetterCastPossible(argumentDataType, expectedReturnDataType)) {
			warning(MessageFormat.format(Messages.STCoreValidator_UnnecessaryWideConversion,
					expectedArgumentDataType.getName()), STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE,
					UNNECESSARY_WIDE_CONVERSION, expectedArgumentDataType.getName(), argumentDataType.getName(),
					expectedReturnDataType.getName());
		} else if (!expectedReturnDataType.eClass().equals(returnDataType.eClass())
				&& expectedReturnDataType.isAssignableFrom(returnDataType)
				&& isBetterCastPossible(argumentDataType, expectedReturnDataType)) {
			warning(MessageFormat.format(Messages.STCoreValidator_UnnecessaryNarrowConversion,
					returnDataType.getName()), STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE,
					UNNECESSARY_NARROW_CONVERSION, returnDataType.getName(), argumentDataType.getName(),
					expectedReturnDataType.getName());
		} else if (argument.getArgument() instanceof final STNumericLiteral numericLiteral) {
			try {
				final String value = ValueOperations
						.castValue(ValueOperations.wrapValue(numericLiteral.getValue(), argumentDataType),
								expectedReturnDataType)
						.toString();
				warning(MessageFormat.format(Messages.STCoreValidator_UnnecessaryLiteralConversion,
						returnDataType.getName()), null, UNNECESSARY_LITERAL_CONVERSION, returnDataType.getName(),
						expectedReturnDataType.getName(), value);
			} catch (final ClassCastException e) {
				// ignore (conversion is actually necessary)
			}
		} else if (argument.getArgument() instanceof final STStringLiteral stringLiteral) {
			try {
				final String value = ValueOperations
						.castValue(ValueOperations.wrapValue(stringLiteral.getValue(), argumentDataType),
								expectedReturnDataType)
						.toString();
				warning(MessageFormat.format(Messages.STCoreValidator_UnnecessaryLiteralConversion,
						returnDataType.getName()), null, UNNECESSARY_LITERAL_CONVERSION, returnDataType.getName(),
						expectedReturnDataType.getName(), value);
			} catch (final ClassCastException e) {
				// ignore (conversion is actually necessary)
			}
		}
	}

	private static boolean isCastSemanticallyRelevant(final DataType argumentDataType, final DataType returnDataType) {
		// semantically relevant casts:
		// - signed to unsigned
		// - integer to real
		// - involves bit types
		return (argumentDataType instanceof AnySignedType && returnDataType instanceof AnyUnsignedType)
				|| (argumentDataType instanceof AnyIntType && returnDataType instanceof AnyRealType)
				|| argumentDataType instanceof AnyBitType || returnDataType instanceof AnyBitType;
	}

	protected boolean isBetterCastPossible(final DataType argumentDataType, final DataType expectedReturnDataType) {
		// do not offer better cast involving ANY_BIT (semantically relevant)
		if (argumentDataType instanceof AnyBitType || expectedReturnDataType instanceof AnyBitType) {
			return false;
		}
		final String castName = argumentDataType.getName() + "_TO_" + expectedReturnDataType.getName(); //$NON-NLS-1$
		return standardFunctionProvider.find(castName, List.of(argumentDataType)).isPresent();
	}

	@Check
	public void checkVariableReference(final STFeatureExpression featureExpression) {
		final STVarDeclaration declaration = EcoreUtil2.getContainerOfType(featureExpression, STVarDeclaration.class);
		if (declaration != null
				&& featureExpression.getFeature() instanceof final STVarDeclaration referencedVariable) {
			if (referencedVariable.eContainer() instanceof final STVarDeclarationBlock referencedVariableBlock
					&& !referencedVariableBlock.isConstant()) {
				error(Messages.STCoreValidator_NonConstantExpressionInVariableDeclaration,
						STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, NON_CONSTANT_DECLARATION);
			} else if (STCoreUtil.getVariableScope(declaration) == STCoreUtil.getVariableScope(referencedVariable)) {
				final INode declarationNode = NodeModelUtils.getNode(declaration);
				final INode referencedVariableNode = NodeModelUtils.getNode(referencedVariable);
				if (declarationNode != null && referencedVariableNode != null
						&& declarationNode.getOffset() < referencedVariableNode.getOffset()) {
					if (EcoreUtil2.getContainerOfType(featureExpression, ICallable.class) != null) { // local variable
						error(Messages.STCoreValidator_VariableMaybeNotInitialized,
								STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, MAYBE_NOT_INITIALIZED);
					} else {
						warning(Messages.STCoreValidator_VariableMaybeNotInitialized,
								STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, MAYBE_NOT_INITIALIZED);
					}
				}
			}
		}
	}

	@Check
	public void checkNestedAssignment(final STAssignment expression) {
		if (expression.eContainer() instanceof STAssignment) {
			error(Messages.STCoreValidator_NestedAssignment, null, NESTED_ASSIGNMENT);
		}
	}

	@Check
	public void checkAssignmentTypeCompatibility(final STAssignment expression) {
		final var leftType = expression.getLeft().getResultType();
		final var rightType = expression.getRight().getResultType();
		checkTypeCompatibility(leftType, rightType, STCorePackage.Literals.ST_ASSIGNMENT__RIGHT,
				STCoreUtil.isAnyVariableReference(expression.getRight()));
	}

	@Check
	public void checkInitializerTypeCompatibility(final STElementaryInitializerExpression initializerExpression) {
		final var type = STCoreUtil.getExpectedType(initializerExpression);
		final var initializerType = initializerExpression.getValue().getResultType();
		checkTypeCompatibility(type, initializerType,
				STCorePackage.Literals.ST_ELEMENTARY_INITIALIZER_EXPRESSION__VALUE);
	}

	@Check
	public void checkInitializerTypeCompatibility(final STArrayInitializerExpression initializerExpression) {
		final var type = STCoreUtil.getExpectedType(initializerExpression);
		if (type != null && !(type instanceof ArrayType)) {
			error(Messages.STCoreValidator_InsufficientArrayDimensions, null, INSUFFICIENT_ARRAY_DIMENSIONS);
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
		accept(isAssignable(stmt.getVariable()), stmt, STCorePackage.Literals.ST_FOR_STATEMENT__VARIABLE);
		final var type = stmt.getVariable().getResultType();
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
	public void checkForControlVariable(final STForStatement stmt) {
		final List<INamedElement> featurePath = getFeaturePath(stmt.getVariable());
		featurePath.stream().filter(Predicate.not(STCoreUtil::isTemporary)).findFirst()
				.ifPresent(variable -> warning(
						MessageFormat.format(Messages.STCoreValidator_UsingNonTemporaryAsControlVariable,
								variable.getName()),
						stmt, STCorePackage.Literals.ST_FOR_STATEMENT__VARIABLE, FOR_CONTROL_VARIABLE_NON_TEMPORARY));
	}

	@Check
	public void checkForControlVariableModification(final STFeatureExpression expression) {
		if (getAccessMode(expression) == AccessMode.WRITE // written to feature
				&& StreamSupport.stream(EcoreUtil2.getAllContainers(expression).spliterator(), false) // all containers
						.filter(STForStatement.class::isInstance).map(STForStatement.class::cast) // that are FOR loops
						.map(STForStatement::getVariable) // where the variable
						.filter(variable -> !EcoreUtil.isAncestor(variable, expression)) // is not an ancestor of us
						.map(STCoreUtil::getFeaturePath) // where the path of features
						.anyMatch(path -> path.contains(expression.getFeature()))) { // contains our feature
			error(Messages.STCoreValidator_AttemptingToModifyControlVariable, expression,
					STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, FOR_CONTROL_VARIABLE_MODIFICATION);
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
		if (!(feature instanceof final ICallable callable)) {
			error(MessageFormat.format(Messages.STCoreValidator_Feature_Not_Callable, feature.getName()), expression,
					STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, FEATURE_NOT_CALLABLE);
			return;
		}
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
			if (expression.getParameters().size() != parameterCount
					&& !(isCallableVarargs(callable) && expression.getParameters().size() >= parameterCount)) {
				error(MessageFormat.format(Messages.STCoreValidator_Wrong_Number_Of_Arguments, callable.getName(),
						Integer.toString(parameterCount), Integer.toString(expression.getParameters().size())),
						expression, STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, WRONG_NUMBER_OF_ARGUMENTS);
				return;
			}
		} else if (callable instanceof STStandardFunction && !expression.getParameters().isEmpty()) {
			error(MessageFormat.format(
					Messages.STCoreValidator_Attempting_To_Call_Standard_Function_With_Formal_Arguments,
					callable.getName()), expression, STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE,
					STANDARD_FUNCTION_WITH_FORMAL_ARGUMENTS);
			return;
		}

		// check argument type
		expression.getMappedInputArguments().forEach((param, arg) -> {
			if (arg != null) {
				checkTypeCompatibility(getFeatureType(param), arg.getResultType(),
						STCorePackage.Literals.ST_FEATURE_EXPRESSION__PARAMETERS,
						expression.getParameters().indexOf(arg));
			}
		});
		expression.getMappedOutputArguments().forEach((param, arg) -> {
			if (arg != null) {
				checkTypeCompatibility(arg.getResultType(), getFeatureType(param),
						STCorePackage.Literals.ST_FEATURE_EXPRESSION__PARAMETERS,
						expression.getParameters().indexOf(arg), STCoreUtil.isAnyTypeVariable(param));
				if (isAssignable(arg.getArgument()) != IsAssignableResult.ASSIGNABLE) {
					error(Messages.STCoreValidator_Argument_Not_Assignable, expression,
							STCorePackage.Literals.ST_FEATURE_EXPRESSION__PARAMETERS,
							expression.getParameters().indexOf(arg), VALUE_NOT_ASSIGNABLE);
				}
			}
		});
		expression.getMappedInOutArguments().forEach((param, arg) -> {
			if (arg != null) {
				checkTypeStrictCompatibility(arg.getResultType(), getFeatureType(param),
						STCorePackage.Literals.ST_FEATURE_EXPRESSION__PARAMETERS,
						expression.getParameters().indexOf(arg));
				if (isAssignable(arg.getArgument()) != IsAssignableResult.ASSIGNABLE) {
					error(Messages.STCoreValidator_Argument_Not_Assignable, expression,
							STCorePackage.Literals.ST_FEATURE_EXPRESSION__PARAMETERS,
							expression.getParameters().indexOf(arg), VALUE_NOT_ASSIGNABLE);
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
		if (expression.isCall() && expression.getFeature() instanceof final FB functionBlock
				&& functionBlock.getInterface().getEventInputs().size() != 1) {
			error(Messages.STCoreValidator_Unqualified_FB_Call_On_FB_With_Input_Event_Size_Not_One,
					STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE,
					UNQUALIFIED_FB_CALL_ON_FB_WITH_INPUT_EVENT_SIZE_NOT_ONE);
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
		} else if (expectedType instanceof final DataType expectedDataType
				&& IecTypes.GenericTypes.isAnyType(expectedDataType) && expression.getType() == null
				&& STCoreUtil.isAncestor(STCorePackage.Literals.ST_ELEMENTARY_INITIALIZER_EXPRESSION, expression)) {
			error(MessageFormat.format(Messages.STCoreValidator_Literal_Requires_Type_Specifier,
					expectedDataType.getName()), STCorePackage.Literals.ST_NUMERIC_LITERAL__VALUE,
					LITERAL_REQUIRES_TYPE_SPECIFIER);
		} else if (expectedType instanceof final DataType expectedDataType
				&& !IecTypes.GenericTypes.isAnyType(expectedDataType)
				&& !(expectedDataType instanceof DirectlyDerivedType)
				&& !type.eClass().equals(expectedDataType.eClass()) && expectedDataType.isAssignableFrom(type)) {
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
		} else if (expectedType instanceof final DataType expectedDataType
				&& IecTypes.GenericTypes.isAnyType(expectedDataType) && expression.getType() == null
				&& STCoreUtil.isAncestor(STCorePackage.Literals.ST_ELEMENTARY_INITIALIZER_EXPRESSION, expression)) {
			error(MessageFormat.format(Messages.STCoreValidator_Literal_Requires_Type_Specifier,
					expectedDataType.getName()), STCorePackage.Literals.ST_STRING_LITERAL__VALUE,
					LITERAL_REQUIRES_TYPE_SPECIFIER);
		} else if (expectedType instanceof final AnyStringType expectedAnyStringType
				&& expectedAnyStringType.isSetMaxLength()
				&& expression.getValue().length() > ((AnyStringType) expectedType).getMaxLength()) {
			warning(MessageFormat.format(Messages.STCoreValidator_String_Literal_Truncated,
					Integer.toString(expectedAnyStringType.getMaxLength())), null, TRUNCATED_LITERAL);
		} else if (expectedType instanceof final DataType expectedDataType
				&& !IecTypes.GenericTypes.isAnyType(expectedDataType)
				&& !(expectedDataType instanceof DirectlyDerivedType)
				&& !type.eClass().equals(expectedDataType.eClass()) && (expectedDataType).isAssignableFrom(type)) {
			warning(MessageFormat.format(Messages.STCoreValidator_Implicit_Conversion_In_Literal, type.getName(),
					expectedDataType.getName()), null, LITERAL_IMPLICIT_CONVERSION);
		}
	}

	@Check
	public void checkReturnValueCanOnlyBeAssignedToCurrentICallable(final STFeatureExpression expression) {
		final var feature = expression.getFeature();
		if (feature instanceof final ICallable callable && !expression.isCall() && !(feature instanceof FB)) {
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
		// Valid target receiver is a variable, an array access or a function name
		// usable as variable
		if (memberAccessExpr.getReceiver() instanceof STMemberAccessExpression
				|| memberAccessExpr.getReceiver() instanceof STArrayAccessExpression
				|| (memberAccessExpr.getReceiver() instanceof final STFeatureExpression featureExpression
						&& !(featureExpression.isCall()))) {
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

	@Check
	public void checkExitIsInLoop(final STExit exitStatement) {
		if (StreamSupport.stream(EcoreUtil2.getAllContainers(exitStatement).spliterator(), false)
				.filter(STStatement.class::isInstance).map(STStatement.class::cast).noneMatch(this::isLoopStatement)) {
			error(MessageFormat.format(Messages.STCoreValidator_ControlFlowStatementNeedsToBeInsideALoop, "EXIT"), null, //$NON-NLS-1$
					EXIT_NOT_IN_LOOP);
		}
	}

	@Check
	public void checkContinueIsInLoop(final STContinue continueStatement) {
		if (StreamSupport.stream(EcoreUtil2.getAllContainers(continueStatement).spliterator(), false)
				.filter(STStatement.class::isInstance).map(STStatement.class::cast).noneMatch(this::isLoopStatement)) {
			error(MessageFormat.format(Messages.STCoreValidator_ControlFlowStatementNeedsToBeInsideALoop, "CONTINUE"), //$NON-NLS-1$
					null, CONTINUE_NOT_IN_LOOP);
		}
	}

	private boolean isLoopStatement(final STStatement statement) {
		return statement instanceof STForStatement || statement instanceof STWhileStatement
				|| statement instanceof STRepeatStatement;
	}

	@Check
	public void checkDuplicateAttribute(final STPragma pragma) {
		pragma.getAttributes().stream().filter(attribute -> attribute.getDeclaration() != null)
				.collect(Collectors.groupingBy(STAttribute::getDeclaration)).values().stream()
				.filter(list -> list.size() > 1).flatMap(List::stream)
				.forEach(attribute -> error(
						MessageFormat.format(Messages.STCoreValidator_DuplicateAttribute,
								PackageNameHelper.getFullTypeName(attribute.getDeclaration())),
						attribute, STCorePackage.Literals.ST_ATTRIBUTE__DECLARATION, DUPLICATE_ATTRIBUTE,
						PackageNameHelper.getFullTypeName(attribute.getDeclaration())));
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
		if (accessorType instanceof final AnyBitType accessorBitType
				&& receiverType instanceof final AnyBitType receiverBitType) {
			final var bitSize = receiverBitType.getBitSize();
			final var bitFactor = accessorBitType.getBitSize();
			return bitFactor > 0 && bitSize > bitFactor ? bitSize / bitFactor - 1 : -1;
		}
		return -1;
	}

	protected static ICallable getICallableContainer(final EObject eObject) {
		for (EObject parent = eObject.eContainer(); parent != null; parent = parent.eContainer()) {
			if (parent instanceof final ICallable callable) {
				return callable;
			}
		}
		return null;
	}

	protected void checkTypeCompatibility(final INamedElement destination, final INamedElement source,
			final EStructuralFeature feature) {
		checkTypeCompatibility(destination, source, feature, false);
	}

	protected void checkTypeCompatibility(final INamedElement destination, final INamedElement source,
			final EStructuralFeature feature, final int index) {
		checkTypeCompatibility(destination, source, feature, index, false);
	}

	protected void checkTypeCompatibility(final INamedElement destination, final INamedElement source,
			final EStructuralFeature feature, final boolean allowAnyAssignment) {
		checkTypeCompatibility(destination, source, feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
				allowAnyAssignment);
	}

	protected void checkTypeCompatibility(final INamedElement destination, final INamedElement source,
			final EStructuralFeature feature, final int index, final boolean allowAnyAssignment) {
		if (destination instanceof final DataType destinationDataType
				&& source instanceof final DataType sourceDataType) {
			checkTypeCompatibility(destinationDataType, sourceDataType, feature, index, allowAnyAssignment);
		} else if (source != null && destination != null) {
			error(MessageFormat.format(Messages.STCoreValidator_Non_Compatible_Types, source.getName(),
					destination.getName()), feature, index, NON_COMPATIBLE_TYPES, source.getName(),
					destination.getName());
		}
	}

	protected void checkTypeCompatibility(final DataType destination, final DataType source,
			final EStructuralFeature feature, final int index, final boolean allowAnyAssignment) {
		if (!destination.isAssignableFrom(source)
				&& !(allowAnyAssignment && GenericTypes.isAnyType(source) && source.isAssignableFrom(destination))) {
			error(MessageFormat.format(Messages.STCoreValidator_Non_Compatible_Types, source.getName(),
					destination.getName()), feature, index, NON_COMPATIBLE_TYPES, source.getName(),
					destination.getName());
		}
	}

	protected void checkTypeStrictCompatibility(final INamedElement destination, final INamedElement source,
			final EStructuralFeature feature, final int index) {
		if (destination instanceof final DataType destinationDataType
				&& source instanceof final DataType sourceDataType) {
			checkTypeStrictCompatibility(destinationDataType, sourceDataType, feature, index);
		} else if (source != null && destination != null) {
			error(MessageFormat.format(Messages.STCoreValidator_Non_Compatible_Types, source.getName(),
					destination.getName()), feature, index, NON_COMPATIBLE_TYPES, source.getName(),
					destination.getName());
		}
	}

	protected void checkTypeStrictCompatibility(final DataType destination, final DataType source,
			final EStructuralFeature feature, final int index) {
		if (!(destination.isAssignableFrom(source) && source.isAssignableFrom(destination))) {
			error(MessageFormat.format(Messages.STCoreValidator_Non_Compatible_Types, source.getName(),
					destination.getName()), feature, index, NON_COMPATIBLE_TYPES, source.getName(),
					destination.getName());
		}
	}

	protected static IsAssignableResult isAssignable(final STExpression expression) {
		if (expression instanceof STMultibitPartialExpression) {
			return IsAssignableResult.ASSIGNABLE;
		}
		if (expression instanceof final STFeatureExpression featureExpression) {
			return isFeatureExpressionAssignable(featureExpression);
		}
		if (expression instanceof final STBuiltinFeatureExpression featureExpression) {
			return isBuiltinFeatureExpressionAssignable(featureExpression);
		}
		if (expression instanceof final STArrayAccessExpression arrayAccessExpression) {
			return isAssignable(arrayAccessExpression.getReceiver());
		}
		if (expression instanceof final STMemberAccessExpression memberAccessExpression) {
			return isMemberExpressionAssignable(memberAccessExpression);
		}
		return IsAssignableResult.NOT_ASSIGNABLE;
	}

	private static IsAssignableResult isMemberExpressionAssignable(
			final STMemberAccessExpression memberAccessExpression) {
		final IsAssignableResult receiverResult = isAssignable(memberAccessExpression.getReceiver());
		final IsAssignableResult memberResult = isAssignable(memberAccessExpression.getMember());
		if (receiverResult != IsAssignableResult.ASSIGNABLE) {
			return receiverResult;
		}
		if (memberResult != IsAssignableResult.ASSIGNABLE) {
			return memberResult;
		}
		return IsAssignableResult.ASSIGNABLE;
	}

	private static IsAssignableResult isFeatureExpressionAssignable(final STFeatureExpression featureExpression) {
		if (featureExpression.isCall()) {
			return IsAssignableResult.CALL_NOT_ASSIGNABLE;
		}
		final var feature = featureExpression.getFeature();
		if (feature instanceof VarDeclaration) {
			if (feature.eContainmentFeature() == LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_CONST_VARS) {
				return IsAssignableResult.CONST_NOT_ASSIGNABLE;
			}
			if (feature.eContainmentFeature() == LibraryElementPackage.Literals.INTERFACE_LIST__INPUT_VARS
					&& featureExpression.eResource() instanceof final LibraryElementXtextResource libResource
					&& EcoreUtil2.getContainerOfType(feature, LibraryElement.class) == libResource
							.getInternalLibraryElement()) {
				return IsAssignableResult.INPUT_NOT_ASSIGNABLE;
			}
		} else if (feature instanceof final STVarDeclaration varDeclaration
				&& varDeclaration.eContainer() instanceof final STVarDeclarationBlock varBlock) {
			if (varBlock.isConstant()) {
				return IsAssignableResult.CONST_NOT_ASSIGNABLE;
			}
			if (varBlock instanceof STVarInputDeclarationBlock && EcoreUtil2.getContainerOfType(varBlock,
					ICallable.class) == EcoreUtil2.getContainerOfType(featureExpression, ICallable.class)) {
				return IsAssignableResult.INPUT_NOT_ASSIGNABLE;
			}
		}
		return IsAssignableResult.ASSIGNABLE;
	}

	private static IsAssignableResult isBuiltinFeatureExpressionAssignable(
			final STBuiltinFeatureExpression featureExpression) {
		if (featureExpression.isCall()) {
			return IsAssignableResult.CALL_NOT_ASSIGNABLE;
		}
		return switch (featureExpression.getFeature()) {
		case THIS -> IsAssignableResult.ASSIGNABLE;
		default -> IsAssignableResult.NOT_ASSIGNABLE;
		};
	}

	private enum IsAssignableResult {
		ASSIGNABLE(Severity.IGNORE, null, null),
		NOT_ASSIGNABLE(Severity.ERROR, VALUE_NOT_ASSIGNABLE, Messages.STCoreValidator_Assignment_Invalid_Left_Side),
		CALL_NOT_ASSIGNABLE(Severity.ERROR, VALUE_NOT_ASSIGNABLE, Messages.STCoreValidator_CallsCannotBeAssignedTo),
		CONST_NOT_ASSIGNABLE(Severity.ERROR, VALUE_NOT_ASSIGNABLE, Messages.STCoreValidator_ConstantsCannotBeAssigned),
		INPUT_NOT_ASSIGNABLE(Severity.WARNING, VALUE_NOT_ASSIGNABLE, Messages.STCoreValidator_InputsCannotBeAssigned);

		IsAssignableResult(final Severity severity, final String code, final String message) {
			this.severity = severity;
			this.code = code;
			this.message = message;
		}

		public Severity getSeverity() {
			return severity;
		}

		public String getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

		private final Severity severity;
		private final String code;
		private final String message;
	}

	protected void accept(final IsAssignableResult assignability, final EObject source,
			final EStructuralFeature feature, final String... issueData) {
		switch (assignability.getSeverity()) {
		case ERROR:
			error(assignability.getMessage(), source, feature, assignability.getCode(), issueData);
			break;
		case WARNING:
			warning(assignability.getMessage(), source, feature, assignability.getCode(), issueData);
			break;
		case INFO:
			info(assignability.getMessage(), source, feature, assignability.getCode(), issueData);
			break;
		default:
			break;
		}
	}
}
