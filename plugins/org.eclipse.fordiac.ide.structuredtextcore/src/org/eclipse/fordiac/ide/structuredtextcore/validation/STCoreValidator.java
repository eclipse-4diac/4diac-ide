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
 *       - validation for unary and binary operators
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.validation;

import java.text.MessageFormat;
import java.util.stream.StreamSupport;

import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextcore.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.scoping.STStandardFunctionProvider;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Check;

import com.google.inject.Inject;

public class STCoreValidator extends AbstractSTCoreValidator {

	@Inject
	private STStandardFunctionProvider standardFunctionProvider;

	public static final String ISSUE_CODE_PREFIX = "org.eclipse.fordiac.ide.structuredtextcore."; //$NON-NLS-1$
	public static final String CONSECUTIVE_UNDERSCORE_IN_IDENTIFIER_ERROR = ISSUE_CODE_PREFIX
			+ "consecutiveUnderscoreInIdentifierError"; //$NON-NLS-1$
	public static final String TRAILING_UNDERSCORE_IN_IDENTIFIER_ERROR = ISSUE_CODE_PREFIX
			+ "identiferEndsInUnderscoreError"; //$NON-NLS-1$
	public static final String ASSIGNMENT_INVALID_LEFT = ISSUE_CODE_PREFIX + "invalidLeftSide"; //$NON-NLS-1$
	public static final String NON_COMPATIBLE_TYPES = ISSUE_CODE_PREFIX + "nonCompatibleTypes"; //$NON-NLS-1$
	public static final String NO_CAST_AVAILABLE = ISSUE_CODE_PREFIX + "noCastAvailable"; //$NON-NLS-1$
	public static final String WRONG_NAME_CASE = ISSUE_CODE_PREFIX + "wrongNameCase"; //$NON-NLS-1$
	public static final String RESERVED_IDENTIFIER_ERROR = ISSUE_CODE_PREFIX + "reservedIdentifierError"; //$NON-NLS-1$
	public static final String UNQUALIFIED_FB_CALL_ON_FB_WITH_INPUT_EVENT_SIZE_NOT_ONE = ISSUE_CODE_PREFIX
			+ "UnqualifiedFbCallOnFbWithInputEventSizeNotOne"; //$NON-NLS-1$
	public static final String OPERATOR_NOT_APPLICABLE = ISSUE_CODE_PREFIX + "operatorNotApplicable"; //$NON-NLS-1$

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
		if (!STCoreUtil.isValidLeftAssignment(statement.getLeft())) {
			error(Messages.STCoreValidator_Assignment_Invalid_Left_Side, statement,
					STCorePackage.Literals.ST_ASSIGNMENT_STATEMENT__LEFT,
					ASSIGNMENT_INVALID_LEFT);
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
						WRONG_NAME_CASE,
						nameInText, originalName);
			}
		}
	}

	@Check
	public void checkAssignmentTypeCompatibility(final STAssignmentStatement statement) {

		DataType leftType = (DataType) statement.getLeft().getResultType();
		DataType rightType = (DataType) statement.getRight().getResultType();

		if (leftType instanceof ArrayType || rightType instanceof ArrayType) {
			if (leftType instanceof ArrayType && rightType instanceof ArrayType) {
				leftType = ((ArrayType) leftType).getBaseType();
				rightType = ((ArrayType) rightType).getBaseType();
			} else {
				error(MessageFormat.format(Messages.STCoreValidator_Non_Compatible_Types_In_Assignment, rightType.getName(), rightType.getName()),
						STCorePackage.Literals.ST_ASSIGNMENT_STATEMENT__RIGHT, NON_COMPATIBLE_TYPES,
						rightType.getName(), leftType.getName());
			}
		}

		if (leftType.getClass() == rightType.getClass()) {
			return;
		}

		final String castName = rightType.getName() + "_TO_" + leftType.getName(); //$NON-NLS-1$
		final boolean castPossible = StreamSupport.stream(standardFunctionProvider.get().spliterator(), true)
				.anyMatch(func -> func.getName().equals(castName));

		if (!rightType.isCompatibleWith(leftType)) {
			if (!castPossible) {
				error(MessageFormat.format(Messages.STCoreValidator_No_Cast_Available, rightType.getName(),
						leftType.getName()),
						STCorePackage.Literals.ST_ASSIGNMENT_STATEMENT__RIGHT, NO_CAST_AVAILABLE, rightType.getName(),
						leftType.getName());
			} else {
				error(MessageFormat.format(Messages.STCoreValidator_Non_Compatible_Types_In_Assignment,
						rightType.getName(),
						leftType.getName()),
						STCorePackage.Literals.ST_ASSIGNMENT_STATEMENT__RIGHT, NON_COMPATIBLE_TYPES,
						rightType.getName(), leftType.getName(),
						NodeModelUtils.getNode(statement.getRight()).getText().trim());
			}
		}
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
		if(expression.isCall() && expression.getFeature() instanceof FB) {
			final FB functionBlock = (FB)expression.getFeature();
			if(functionBlock.getInterface().getEventInputs().size() != 1) {
				error(Messages.STCoreValidator_Unqualified_FB_Call_On_FB_With_Input_Event_Size_Not_One,
						STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE,
						UNQUALIFIED_FB_CALL_ON_FB_WITH_INPUT_EVENT_SIZE_NOT_ONE);
			}
		}
	}

}
