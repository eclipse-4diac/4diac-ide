/*******************************************************************************
 * Copyright (c) 2021 - 2022 Primetals Technologies Austria GmbH
 *               2022 Martin Erich Jobst
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
 *   Ulzii Jargalsaikhan
 *       - custom validation for identifiers
 *   Martin Jobst
 *       - validation for reserved identifiers
 *       - validation for calls
 *       - validation for unary and binary operators
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.validation;

import static org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.getFeatureType;
import static org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.isNumericValueValid;
import static org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.isStringValueValid;

import java.text.MessageFormat;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fordiac.ide.model.data.AnyIntType;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextcore.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.scoping.STStandardFunctionProvider;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.inject.Inject;

public class STCoreValidator extends AbstractSTCoreValidator {

	@Inject
	private STStandardFunctionProvider standardFunctionProvider;

	public static final String ISSUE_CODE_PREFIX = "org.eclipse.fordiac.ide.structuredtextcore."; //$NON-NLS-1$
	public static final String CONSECUTIVE_UNDERSCORE_IN_IDENTIFIER_ERROR = ISSUE_CODE_PREFIX
			+ "consecutiveUnderscoreInIdentifierError"; //$NON-NLS-1$
	public static final String TRAILING_UNDERSCORE_IN_IDENTIFIER_ERROR = ISSUE_CODE_PREFIX
			+ "identiferEndsInUnderscoreError"; //$NON-NLS-1$
	public static final String NOT_ASSIGNABLE = ISSUE_CODE_PREFIX + "notAssignable"; //$NON-NLS-1$
	public static final String NON_COMPATIBLE_TYPES = ISSUE_CODE_PREFIX + "nonCompatibleTypes"; //$NON-NLS-1$
	public static final String NO_CAST_AVAILABLE = ISSUE_CODE_PREFIX + "noCastAvailable"; //$NON-NLS-1$
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
		if (iNamedElement.getName().charAt(iNamedElement.getName().length() - 1) == '_') {
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
		final DataType leftType = (DataType) statement.getLeft().getResultType();
		final DataType rightType = (DataType) statement.getRight().getResultType();
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
		expression.getMappedInputArguments()
		.forEach((param, arg) -> checkTypeCompatibility(getFeatureType(param), arg.getResultType(),
				STCorePackage.Literals.ST_FEATURE_EXPRESSION__PARAMETERS,
				expression.getParameters().indexOf(arg.eContainer())));
		expression.getMappedOutputArguments()
		.forEach((param, arg) -> checkTypeCompatibility(arg.getResultType(), getFeatureType(param),
				STCorePackage.Literals.ST_FEATURE_EXPRESSION__PARAMETERS,
				expression.getParameters().indexOf(arg.eContainer())));
		expression.getMappedInOutArguments().forEach((param, arg) -> {
			final INamedElement destination = getFeatureType(param);
			final INamedElement source = arg.getResultType();
			if (!source.equals(destination)) { // in&out requires strict equality
				error(MessageFormat.format(Messages.STCoreValidator_Non_Compatible_Types, source.getName(),
						destination.getName()), STCorePackage.Literals.ST_FEATURE_EXPRESSION__PARAMETERS,
						expression.getParameters().indexOf(arg.eContainer()), NON_COMPATIBLE_TYPES, source.getName(),
						destination.getName());
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
		if (!isNumericValueValid(type, expression.getValue())) {
			error(MessageFormat.format(Messages.STCoreValidator_Invalid_Literal, type.getName(), expression.getValue()),
					STCorePackage.Literals.ST_NUMERIC_LITERAL__VALUE, INVALID_NUMERIC_LITERAL);
		}
	}

	@Check
	public void checkStringLiteral(final STStringLiteral expression) {
		final var type = (DataType) expression.getResultType();
		if (!isStringValueValid(type, expression.getValue())) {
			error(MessageFormat.format(Messages.STCoreValidator_Invalid_Literal, type.getName(), expression.getValue()),
					STCorePackage.Literals.ST_STRING_LITERAL__VALUE, INVALID_STRING_LITERAL);
		}
	}

	protected void checkTypeCompatibility(final INamedElement destination, final INamedElement source,
			final EStructuralFeature feature) {
		checkTypeCompatibility(destination, source, feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
	}

	protected void checkTypeCompatibility(final INamedElement destination, final INamedElement source,
			final EStructuralFeature feature, final int index) {
		if (destination instanceof DataType && source instanceof DataType) {
			checkTypeCompatibility((DataType) destination, (DataType) source, feature, index);
		} else {
			error(MessageFormat.format(Messages.STCoreValidator_Non_Compatible_Types, source.getName(),
					source.getName()), feature, NON_COMPATIBLE_TYPES, source.getName(), destination.getName());
		}
	}

	protected void checkTypeCompatibility(DataType destination, DataType source, final EStructuralFeature feature,
			final int index) {
		if (destination instanceof ArrayType || source instanceof ArrayType) {
			if (destination instanceof ArrayType && source instanceof ArrayType
					&& ((ArrayType) destination).getSubranges().size() == ((ArrayType) source).getSubranges().size()) {
				destination = ((ArrayType) destination).getBaseType();
				source = ((ArrayType) source).getBaseType();
			} else {
				error(MessageFormat.format(Messages.STCoreValidator_Non_Compatible_Types, source.getName(),
						source.getName()), feature, index, NON_COMPATIBLE_TYPES, source.getName(),
						destination.getName());
			}
		}

		if (!source.isCompatibleWith(destination)) {
			final String castName = source.getName() + "_TO_" + destination.getName(); //$NON-NLS-1$
			final boolean castPossible = StreamSupport.stream(standardFunctionProvider.get().spliterator(), true)
					.anyMatch(func -> func.getName().equals(castName));
			if (castPossible) {
				error(MessageFormat.format(Messages.STCoreValidator_Non_Compatible_Types, source.getName(),
						destination.getName()), feature, index, NON_COMPATIBLE_TYPES, source.getName(),
						destination.getName());
			} else {
				error(MessageFormat.format(Messages.STCoreValidator_No_Cast_Available, source.getName(),
						destination.getName()), feature, index, NO_CAST_AVAILABLE, source.getName(),
						destination.getName());
			}
		}
	}
}
