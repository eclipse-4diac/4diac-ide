/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.validation;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.structuredtextcore.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElsePart;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.AccessMode;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

public class STCoreControlFlowValidator {

	private final ValidationMessageAcceptor acceptor;

	private Map<STVarDeclaration, VariableState> variables = new HashMap<>();

	public STCoreControlFlowValidator(final ValidationMessageAcceptor acceptor) {
		this.acceptor = acceptor;
	}

	public void validateVariableBlocks(final List<? extends STVarDeclarationBlock> blocks) {
		blocks.forEach(this::validateVariableBlock);
	}

	public void validateVariableBlock(final STVarDeclarationBlock block) {
		validateVariables(block.getVarDeclarations());
	}

	public void validateVariables(final List<? extends STVarDeclaration> declarations) {
		declarations.forEach(this::validateVariable);
	}

	public void validateVariable(final STVarDeclaration declaration) {
		validateFeatureReferences(declaration);
		variables.put(declaration, VariableState.DEFINED);
	}

	public void validateStatements(final List<? extends STStatement> statements) {
		statements.forEach(this::validateStatement);
	}

	protected Map<STVarDeclaration, VariableState> validateSubStatements(final List<? extends STStatement> statements) {
		final Map<STVarDeclaration, VariableState> originalVariables = variables;
		variables = new HashMap<>(variables);

		statements.forEach(this::validateStatement);

		final Map<STVarDeclaration, VariableState> subVariables = variables;
		variables = originalVariables;
		return subVariables;
	}

	public void validateStatement(final STStatement statement) {
		if (statement instanceof final STAssignment assignment) {
			validateStatement(assignment);
		} else if (statement instanceof final STIfStatement ifStatement) {
			validateStatement(ifStatement);
		} else if (statement instanceof final STCaseStatement caseStatement) {
			validateStatement(caseStatement);
		} else if (statement instanceof final STForStatement forStatement) {
			validateStatement(forStatement);
		} else if (statement instanceof final STWhileStatement whileStatement) {
			validateStatement(whileStatement);
		} else if (statement instanceof final STRepeatStatement repeatStatement) {
			validateStatement(repeatStatement);
		} else {
			validateFeatureReferences(statement);
		}
	}

	protected void validateStatement(final STAssignment assignment) {
		validateFeatureReferences(assignment.getRight());
		validateFeatureReferences(assignment.getLeft());
	}

	protected void validateStatement(final STIfStatement statement) {
		validateFeatureReferences(statement.getCondition());
		variables = Stream
				.concat(Stream.of(validateSubStatements(statement.getStatements()),
						validateElsePart(statement.getElse())),
						statement.getElseifs().stream().map(this::validateElseIfPart))
				.map(Map::entrySet).flatMap(Set::stream)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, VariableState::intersect));
	}

	protected Map<STVarDeclaration, VariableState> validateElseIfPart(final STElseIfPart statement) {
		validateFeatureReferences(statement.getCondition());
		return validateSubStatements(statement.getStatements());
	}

	protected Map<STVarDeclaration, VariableState> validateElsePart(final STElsePart statement) {
		if (statement != null) {
			return validateSubStatements(statement.getStatements());
		}
		return variables;
	}

	protected void validateStatement(final STCaseStatement statement) {
		validateFeatureReferences(statement.getSelector());
		variables = Stream
				.concat(Stream.of(validateElsePart(statement.getElse())),
						statement.getCases().stream().map(this::validateCasePart))
				.map(Map::entrySet).flatMap(Set::stream)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, VariableState::intersect));
	}

	protected Map<STVarDeclaration, VariableState> validateCasePart(final STCaseCases statement) {
		statement.getConditions().forEach(this::validateFeatureReferences);
		return validateSubStatements(statement.getStatements());
	}

	protected void validateStatement(final STForStatement statement) {
		validateFeatureReferences(statement.getVariable());
		validateFeatureReferences(statement.getFrom());
		validateFeatureReferences(statement.getTo());
		validateFeatureReferences(statement.getBy());
		validateSubStatements(statement.getStatements())
				.forEach((key, value) -> variables.merge(key, value, VariableState::intersect));
		STCoreUtil.getFeaturePath(statement.getVariable()).stream().findFirst()
				.filter(STVarDeclaration.class::isInstance).ifPresent(controlVariable -> variables
						.put((STVarDeclaration) controlVariable, VariableState.UNDEFINED_AFTER_FOR));
	}

	protected void validateStatement(final STWhileStatement statement) {
		validateFeatureReferences(statement.getCondition());
		validateSubStatements(statement.getStatements())
				.forEach((key, value) -> variables.merge(key, value, VariableState::intersect));
		validateFeatureReferences(statement.getCondition()); // validate again after inner loop
	}

	protected void validateStatement(final STRepeatStatement statement) {
		validateSubStatements(statement.getStatements())
				.forEach((key, value) -> variables.merge(key, value, VariableState::intersect));
		validateFeatureReferences(statement.getCondition());
	}

	protected void validateFeatureReferences(final EObject container) {
		if (container != null) {
			EcoreUtil2.eAllOfType(container, STFeatureExpression.class).forEach(this::validateFeatureReference);
		}
	}

	protected void validateFeatureReference(final STFeatureExpression expression) {
		validateAccess(expression.getFeature(), STCoreUtil.getAccessMode(expression), expression);
	}

	protected void validateAccess(final INamedElement feature, final AccessMode mode, final EObject context) {
		switch (mode) {
		case READ:
			validateReadAccess(feature, context);
			break;
		case WRITE:
			validateWriteAccess(feature, context);
			break;
		case READ_WRITE:
			validateReadAccess(feature, context);
			validateWriteAccess(feature, context);
			break;
		case NONE:
		default:
			break;
		}
	}

	protected void validateReadAccess(final INamedElement feature, final EObject context) {
		if (variables.get(feature) == VariableState.UNDEFINED_AFTER_FOR) {
			acceptor.acceptWarning(MessageFormat
					.format(Messages.STCoreControlFlowValidator_VariableUndefinedAfterForLoop, feature.getName()),
					context, null, -1, STCoreValidator.FOR_CONTROL_VARIABLE_UNDEFINED);
		}
	}

	protected void validateWriteAccess(final INamedElement feature, final EObject context) {
		if (feature instanceof final STVarDeclaration stVarDeclaration) {
			variables.put(stVarDeclaration, VariableState.DEFINED);
		}
	}

	public enum VariableState {
		DEFINED, UNDEFINED_AFTER_FOR;

		public VariableState intersect(final VariableState other) {
			return VariableState.values()[ordinal() | other.ordinal()];
		}
	}
}
