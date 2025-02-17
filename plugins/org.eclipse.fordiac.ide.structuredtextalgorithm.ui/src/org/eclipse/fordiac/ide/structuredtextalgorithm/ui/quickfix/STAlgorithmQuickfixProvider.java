/**
 * Copyright (c) 2022 Martin Erich Jobst
 *               2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst
 * 	 - initial API and implementation and/or initial documentation
 *   Ulzii Jargalsaikhan
 *   	 - add quick fixes for suggesting similar variables
 */
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.quickfix;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.create.AddNewImportCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInternalVariableCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateVarInOutCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmFactory;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.Messages;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.STAlgorithmEditorUtils;
import org.eclipse.fordiac.ide.structuredtextalgorithm.validation.STAlgorithmValidator;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreFactory;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.VarDeclarationKind;
import org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix.STCoreQuickfixProvider;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.model.edit.ISemanticModification;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

public class STAlgorithmQuickfixProvider extends STCoreQuickfixProvider {

	@Fix(STAlgorithmValidator.MISSING_ALGORITHM)
	public static void fixMissingAlgorithm(final Issue issue, final IssueResolutionAcceptor acceptor) {
		final String algorithmName = issue.getData()[0];
		acceptor.accept(issue,
				MessageFormat.format(Messages.STAlgorithmQuickfixProvider_Add_missing_algorithm, algorithmName),
				MessageFormat.format(Messages.STAlgorithmQuickfixProvider_Add_missing_algorithm, algorithmName), null,
				(element, context) -> {
					if (element instanceof final STAlgorithmSource source) {
						final STAlgorithm algorithm = STAlgorithmFactory.eINSTANCE.createSTAlgorithm();
						algorithm.setName(algorithmName);
						algorithm.setBody(STAlgorithmFactory.eINSTANCE.createSTAlgorithmBody());
						source.getElements().add(algorithm);
					}
				});
	}

	@Fix(STAlgorithmValidator.UNUSED_ALGORITHM)
	public static void fixUnusedAlgorithm(final Issue issue, final IssueResolutionAcceptor acceptor) {
		final String name = issue.getData()[0];
		acceptor.accept(issue, MessageFormat.format(Messages.STAlgorithmQuickfixProvider_Remove_unused_algorithm, name),
				MessageFormat.format(Messages.STAlgorithmQuickfixProvider_Remove_unused_algorithm, name), null,
				(element, context) -> {
					if (element.eContainer() instanceof final STAlgorithmSource source) {
						source.getElements().remove(element);
					}
				});
		acceptor.accept(issue, Messages.STAlgorithmQuickfixProvider_Remove_all_unused_algorithms,
				Messages.STAlgorithmQuickfixProvider_Remove_all_unused_algorithms, null,
				(ISemanticModification) (final EObject element, final IModificationContext context) -> {
					if (element.eContainer() instanceof final STAlgorithmSource source
							&& source.eResource() instanceof final STAlgorithmResource resource
							&& resource.getLibraryElement() instanceof final SimpleFBType fbType) {
						source.getElements()
								.removeIf(sourceElement -> sourceElement instanceof STAlgorithm
										&& fbType.getInterfaceList().getEventInputs().stream().noneMatch(
												event -> Objects.equals(sourceElement.getName(), event.getName())));
					}
				});
	}

	@Override
	@Fix(Diagnostic.LINKING_DIAGNOSTIC)
	public void createMissingVariable(final Issue issue, final IssueResolutionAcceptor acceptor) {
		super.createMissingVariable(issue, acceptor);
		acceptor.accept(issue, Messages.STAlgorithmQuickfixProvider_CreateMissingInternalVariable,
				Messages.STAlgorithmQuickfixProvider_CreateMissingInternalVariable, null,
				(element, context) -> createMissingVariable(element, VarDeclarationKind.PLAIN));
	}

	@Override
	protected void createMissingVariable(final ICallable callable, final String name, final INamedElement type,
			final VarDeclarationKind kind) {
		if (callable instanceof final STAlgorithm algorithm && kind == VarDeclarationKind.TEMP) {
			createSTVarTempDeclaration(algorithm.getBody().getVarTempDeclarations(), name, type);
		} else if (callable instanceof final STMethod method && kind != VarDeclarationKind.PLAIN) {
			createSTVarDeclaration(method.getBody().getVarDeclarations(), name, type, kind);
		} else {
			createVarDeclaration(callable, name, type, kind);
		}
	}

	protected static void createVarDeclaration(final ICallable callable, final String name, final INamedElement type,
			final VarDeclarationKind kind) {
		if (callable.eResource() instanceof final LibraryElementXtextResource resource
				&& resource.getLibraryElement() instanceof final BaseFBType baseFBType
				&& type instanceof final DataType dataType) {
			STAlgorithmEditorUtils.executeCommand(resource.getURI(), switch (kind) {
			case INPUT -> new CreateInterfaceElementCommand(dataType, name, baseFBType.getInterfaceList(), true, -1);
			case INOUT -> new CreateVarInOutCommand(dataType, name, baseFBType.getInterfaceList(), -1);
			case OUTPUT -> new CreateInterfaceElementCommand(dataType, name, baseFBType.getInterfaceList(), false, -1);
			case PLAIN -> new CreateInternalVariableCommand(baseFBType, -1, name, dataType);
			default -> null;
			});
		}
	}

	protected static void createSTVarTempDeclaration(final EList<STVarTempDeclarationBlock> blocks, final String name,
			final INamedElement type) {
		final STVarTempDeclarationBlock block = getOrCreateSTVarTempDeclarationBlock(blocks);
		final STVarDeclaration varDeclaration = createSTVarDeclaration(name, type);
		block.getVarDeclarations().add(varDeclaration);
	}

	protected static STVarTempDeclarationBlock getOrCreateSTVarTempDeclarationBlock(
			final EList<STVarTempDeclarationBlock> blocks) {
		return blocks.stream().filter(Predicate.not(STVarDeclarationBlock::isConstant)).findFirst().orElseGet(() -> {
			final STVarTempDeclarationBlock block = STCoreFactory.eINSTANCE.createSTVarTempDeclarationBlock();
			blocks.add(block);
			return block;
		});
	}

	@Override
	protected void createImportProposal(final Issue issue, final String label, final String importedNamespace,
			final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, label, label, null, (element, context) -> {
			if (element.eResource() instanceof final STAlgorithmResource resource
					&& !ImportHelper.matchesImports(importedNamespace, resource.getLibraryElement())) {
				STAlgorithmEditorUtils.executeCommand(resource.getURI(),
						new AddNewImportCommand(resource.getLibraryElement(), importedNamespace));
			}
		}, 100);
	}
}
