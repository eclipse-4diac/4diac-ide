/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextcore.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarPlainDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.AccessMode;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.xtext.validation.IssueSeverities;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

public class STCoreVariableUsageValidator {

	public enum VariableState {
		UNUSED, UNREAD, UNWRITTEN;
	}

	private final ValidationMessageAcceptor acceptor;
	private final IssueSeverities severities;

	private final Map<INamedElement, EnumSet<VariableState>> variables = new HashMap<>();
	private final Map<URI, EnumSet<VariableState>> variableUris = new HashMap<>();

	public STCoreVariableUsageValidator(final ValidationMessageAcceptor acceptor, final IssueSeverities severities) {
		this.acceptor = acceptor;
		this.severities = severities;
	}

	public void addVariables(final ICallable callable) {
		addVariables(callable.getInputParameters(), EnumSet.of(VariableState.UNUSED, VariableState.UNREAD));
		addVariables(callable.getOutputParameters(), EnumSet.of(VariableState.UNUSED, VariableState.UNWRITTEN));
		addVariables(callable.getInOutParameters(), EnumSet.of(VariableState.UNUSED));
		addReturnVariable(callable);
	}

	public void addVariableBlocks(final List<? extends STVarDeclarationBlock> blocks) {
		blocks.forEach(this::addVariableBlock);
	}

	public void addVariableBlock(final STVarDeclarationBlock block) {
		addVariables(block.getVarDeclarations(), switch (block) {
		case final STVarInputDeclarationBlock unused -> EnumSet.of(VariableState.UNUSED, VariableState.UNREAD);
		case final STVarOutputDeclarationBlock unused -> EnumSet.of(VariableState.UNUSED, VariableState.UNWRITTEN);
		case final STVarInOutDeclarationBlock unused -> EnumSet.of(VariableState.UNUSED);
		case final STVarPlainDeclarationBlock unused when block.isConstant() ->
			EnumSet.of(VariableState.UNUSED, VariableState.UNREAD);
		case final STVarTempDeclarationBlock unused when block.isConstant() ->
			EnumSet.of(VariableState.UNUSED, VariableState.UNREAD);
		default -> EnumSet.of(VariableState.UNUSED, VariableState.UNREAD, VariableState.UNWRITTEN);
		});
	}

	public void addReturnVariable(final ICallable callable) {
		if (callable.getReturnType() != null) {
			setState(callable, EnumSet.of(VariableState.UNUSED, VariableState.UNWRITTEN));
		}
	}

	public void addVariables(final List<? extends INamedElement> declarations) {
		declarations.forEach(declaration -> addVariable(declaration,
				EnumSet.of(VariableState.UNUSED, VariableState.UNREAD, VariableState.UNWRITTEN)));
	}

	public void addVariables(final List<? extends INamedElement> declarations, final Set<VariableState> initialState) {
		declarations.forEach(declaration -> addVariable(declaration, initialState));
	}

	protected void addVariable(final INamedElement declaration, final Set<VariableState> initialState) {
		setState(declaration, EnumSet.copyOf(initialState));
	}

	public void addReferences(final EObject container) {
		if (container != null) {
			StreamSupport.stream(Spliterators.spliteratorUnknownSize(container.eAllContents(), 0), false)
					.forEach(this::addReference);
		}
	}

	protected void addReference(final EObject object) {
		switch (object) {
		case final STForStatement forStatement -> addReference(forStatement);
		case final STFeatureExpression featureExpression -> addReference(featureExpression);
		default -> {// ignore
		}
		}
	}

	protected void addReference(final STFeatureExpression expression) {
		addAccess(expression.getFeature(), STCoreUtil.getAccessMode(expression));
	}

	protected void addReference(final STForStatement statement) {
		STCoreUtil.getFeaturePath(statement.getVariable()).stream().findFirst().map(this::getState).ifPresent(state -> {
			state.remove(VariableState.UNUSED);
			state.remove(VariableState.UNREAD);
			state.remove(VariableState.UNWRITTEN);
		});
	}

	protected void addAccess(final INamedElement feature, final AccessMode mode) {
		final EnumSet<VariableState> state = getState(feature);
		if (state != null) {
			state.remove(VariableState.UNUSED);
			switch (mode) {
			case READ -> state.remove(VariableState.UNREAD);
			case WRITE -> state.remove(VariableState.UNWRITTEN);
			case READ_WRITE -> {
				state.remove(VariableState.UNREAD);
				state.remove(VariableState.UNWRITTEN);
			}
			default -> {
				// ignore
			}
			}
		}
	}

	protected EnumSet<VariableState> getState(final INamedElement feature) {
		final var state = variables.get(feature);
		if (state != null) {
			return state;
		}
		return variableUris.get(EcoreUtil.getURI(feature).trimQuery());
	}

	protected void setState(final INamedElement element, final EnumSet<VariableState> state) {
		variables.put(element, state);
		variableUris.put(EcoreUtil.getURI(element), state);
	}

	public void validateUnused() {
		variables.forEach(this::validateUnused);
	}

	protected void validateUnused(final INamedElement feature, final EnumSet<VariableState> state) {
		if (state.contains(VariableState.UNUSED)) {
			addIssue(MessageFormat.format(Messages.STCoreVariableUsageValidator_UnusedVariable, feature.getName()),
					feature, LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, -1, STCoreValidator.UNUSED_VARIABLE);
		} else if (state.contains(VariableState.UNREAD)) {
			addIssue(MessageFormat.format(Messages.STCoreVariableUsageValidator_UnreadVariable, feature.getName()),
					feature, LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, -1, STCoreValidator.UNREAD_VARIABLE);
		} else if (state.contains(VariableState.UNWRITTEN)) {
			addIssue(MessageFormat.format(Messages.STCoreVariableUsageValidator_UnwrittenVariable, feature.getName()),
					feature, LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, -1,
					STCoreValidator.UNWRITTEN_VARIABLE);
		}
	}

	protected void addIssue(final String message, final EObject source, final EStructuralFeature feature,
			final int index, final String issueCode, final String... issueData) {
		STCoreValidator.addIssue(acceptor, severities, message, source, feature, index, issueCode, issueData);
	}

	protected static EnumSet<VariableState> merge(final EnumSet<VariableState> first,
			final EnumSet<VariableState> second) {
		final EnumSet<VariableState> result = first.clone();
		result.retainAll(second);
		return result;
	}
}
