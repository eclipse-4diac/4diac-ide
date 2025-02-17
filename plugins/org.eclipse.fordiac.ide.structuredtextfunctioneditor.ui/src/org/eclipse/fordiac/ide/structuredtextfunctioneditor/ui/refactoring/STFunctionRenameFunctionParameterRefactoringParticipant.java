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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.refactoring;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.resource.STFunctionResource;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.Messages;
import org.eclipse.xtext.ui.refactoring.impl.AbstractProcessorBasedRenameParticipant;

@SuppressWarnings("restriction")
public class STFunctionRenameFunctionParameterRefactoringParticipant extends AbstractProcessorBasedRenameParticipant {

	@Override
	protected List<EObject> getRenamedElementsOrProxies(final EObject originalTarget) {
		return switch (originalTarget) {
		case final VarDeclaration varDeclaration -> getRenamedElementsOrProxies(varDeclaration);
		case final STVarDeclaration stVarDeclaration -> getRenamedElementsOrProxies(stVarDeclaration);
		default -> List.of();
		};
	}

	protected static List<EObject> getRenamedElementsOrProxies(final VarDeclaration originalTarget) {
		final STFunctionSource source = getSource(originalTarget);
		final FunctionFBType functionFBType = getFunctionFBType(originalTarget);
		if (source != null && functionFBType != null) {
			final Optional<STVarDeclaration> parameter = getPrimaryFunction(source, functionFBType)
					.flatMap(function -> getParameter(function, originalTarget.getName()));
			if (parameter.isPresent()) {
				return List.of(parameter.get());
			}
		}
		return List.of();
	}

	protected static List<EObject> getRenamedElementsOrProxies(final STVarDeclaration originalTarget) {
		final STFunction function = getFunction(originalTarget);
		final FunctionFBType functionFBType = getInternalLibraryElement(originalTarget);
		if (function != null && functionFBType != null && isPrimaryFunction(function, functionFBType)) {
			final VarDeclaration variable = functionFBType.getInterfaceList().getVariable(originalTarget.getName());
			if (variable != null) {
				return List.of(variable);
			}
		}
		return List.of();
	}

	protected static FunctionFBType getFunctionFBType(final VarDeclaration varDeclaration) {
		return varDeclaration.eContainer() instanceof final InterfaceList interfaceList
				&& interfaceList.eContainer() instanceof final FunctionFBType functionFBType ? functionFBType : null;
	}

	protected static STFunction getFunction(final STVarDeclaration varDeclaration) {
		return varDeclaration.eContainer() instanceof final STVarDeclarationBlock block
				&& block.eContainer() instanceof final STFunction function ? function : null;
	}

	protected static STFunctionSource getSource(final EObject object) {
		return object.eResource() instanceof final STFunctionResource resource
				&& resource.getParseResult().getRootASTElement() instanceof final STFunctionSource source ? source
						: null;
	}

	protected static FunctionFBType getInternalLibraryElement(final EObject object) {
		return object.eResource() instanceof final STFunctionResource resource
				&& resource.getInternalLibraryElement() instanceof final FunctionFBType functionFBType ? functionFBType
						: null;
	}

	protected static Optional<STFunction> getPrimaryFunction(final STFunctionSource source,
			final FunctionFBType functionFBType) {
		return source.getFunctions().stream().filter(function -> isPrimaryFunction(function, functionFBType)).findAny();
	}

	protected static boolean isPrimaryFunction(final STFunction function, final FunctionFBType functionFBType) {
		return Objects.equals(function.getName(), functionFBType.getName());
	}

	protected static Optional<STVarDeclaration> getParameter(final STFunction function, final String name) {
		return function.getVarDeclarations().stream().map(STVarDeclarationBlock::getVarDeclarations)
				.flatMap(List::stream).filter(parameter -> Objects.equals(parameter.getName(), name)).findAny();
	}

	@Override
	public String getName() {
		return Messages.STFunctionRenameFunctionParameterRefactoringParticpant_Name;
	}
}
