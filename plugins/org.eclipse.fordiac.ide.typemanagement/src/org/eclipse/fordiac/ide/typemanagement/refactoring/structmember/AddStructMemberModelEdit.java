/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.structmember;

import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.getArraySize;
import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.setArraySize;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.commands.insert.InsertVariableCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEdit;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

final class AddStructMemberModelEdit extends ModelEdit<StructuredType> {
	private final AddStructMemberConfiguration configuration;
	private List<MemberSignature> validationMembers = List.of();

	AddStructMemberModelEdit(final URI structTypeURI, final AddStructMemberConfiguration configuration) {
		super(MessageFormat.format(Messages.AddStructMemberRefactoring_AddMemberChange, configuration.memberName(),
				structTypeURI.trimFileExtension().lastSegment()), structTypeURI, StructuredType.class);
		this.configuration = configuration;
	}

	@Override
	public void initializeValidationData(final StructuredType element, final IProgressMonitor pm) {
		validationMembers = getMemberSignatures(element);
	}

	@Override
	public RefactoringStatus isValid(final StructuredType element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (!validationMembers.equals(getMemberSignatures(element))) {
			status.addFatalError(Messages.AddStructMemberRefactoring_ModelChanged);
			return status;
		}
		validateConfiguration(element, configuration, status);
		return status;
	}

	@Override
	protected Command createCommand(final StructuredType element) {
		final DataType memberType = StructMemberRefactoringSupport.resolveDataType(element.getTypeLibrary(),
				configuration.memberTypeName());
		final VarDeclaration prototype = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		prototype.setName(configuration.memberName());
		prototype.setComment(configuration.comment());
		prototype.setType(memberType);
		setArraySize(prototype, configuration.arraySize());
		return new InsertVariableCommand(element, element.getMemberVariables(), prototype,
				StructMemberRefactoringSupport.getInsertionIndex(element, configuration.insertBefore()));
	}

	static void validateConfiguration(final StructuredType structType,
			final AddStructMemberConfiguration configuration, final RefactoringStatus status) {
		if (IdentifierVerifier.verifyIdentifier(configuration.memberName()).isPresent()) {
			status.addFatalError(Messages.AddStructMemberRefactoring_InvalidName);
		}
		if (StructMemberRefactoringSupport.containsMember(structType, configuration.memberName())) {
			status.addFatalError(MessageFormat.format(Messages.AddStructMemberRefactoring_DuplicateName,
					configuration.memberName()));
		}

		final DataType memberType = StructMemberRefactoringSupport.resolveDataType(structType.getTypeLibrary(),
				configuration.memberTypeName());
		if (memberType == null) {
			status.addFatalError(Messages.AddStructMemberRefactoring_MissingType);
		} else if (!StructMemberRefactoringSupport.isConcreteType(memberType)) {
			status.addFatalError(Messages.AddStructMemberRefactoring_GenericType);
		} else if (StructMemberRefactoringSupport.createsRecursiveType(structType, memberType)) {
			status.addFatalError(Messages.AddStructMemberRefactoring_RecursiveType);
		}

		if (StructMemberRefactoringSupport.getInsertionIndex(structType, configuration.insertBefore()) < 0) {
			status.addFatalError(Messages.AddStructMemberRefactoring_InvalidPosition);
		}
	}

	private static List<MemberSignature> getMemberSignatures(final StructuredType type) {
		return type.getMemberVariables().stream().map(MemberSignature::new).toList();
	}

	private record MemberSignature(String name, String typeName, String arraySize) {
		MemberSignature(final VarDeclaration member) {
			this(member.getName(), PackageNameHelper.getFullTypeName(member.getType()), getArraySize(member));
		}
	}
}
