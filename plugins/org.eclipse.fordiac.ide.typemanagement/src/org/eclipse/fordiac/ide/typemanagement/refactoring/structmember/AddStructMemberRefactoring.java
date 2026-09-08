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

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.CommandCompositeChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEditChange;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public final class AddStructMemberRefactoring extends Refactoring {
	private final AddStructMemberContext context;
	private AddStructMemberConfiguration configuration;

	public AddStructMemberRefactoring(final AddStructMemberContext context) {
		this.context = Objects.requireNonNull(context);
		configuration = context.getInitialConfiguration();
	}

	@Override
	public String getName() {
		return Messages.AddStructMemberRefactoring_Name;
	}

	@Override
	public RefactoringStatus checkInitialConditions(final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		final TypeEntry structTypeEntry = StructMemberRefactoringSupport.getTypeEntry(context.getStructTypeURI());
		final TypeEntry targetTypeEntry = StructMemberRefactoringSupport.getTypeEntry(context.getTargetModelURI());

		if (!(structTypeEntry instanceof final DataTypeEntry dataTypeEntry)
				|| !(structTypeEntry.getType() instanceof StructuredType)) {
			status.addFatalError(Messages.AddStructMemberRefactoring_InvalidContext);
		} else {
			if (!StructMemberRefactoringSupport.isWritable(structTypeEntry)) {
				status.addFatalError(Messages.AddStructMemberRefactoring_StructReadOnly);
			}
			addMultipleUsesWarning(dataTypeEntry, status);
		}

		if (!StructMemberRefactoringSupport.isWritable(targetTypeEntry)) {
			status.addFatalError(Messages.AddStructMemberRefactoring_TargetReadOnly);
		} else if (!context.matches(targetTypeEntry.getType())) {
			status.addFatalError(Messages.AddStructMemberRefactoring_InvalidContext);
		}
		pm.done();
		return status;
	}

	private static void addMultipleUsesWarning(final DataTypeEntry dataTypeEntry, final RefactoringStatus status) {
		final int useCount = DataTypeInstanceSearch.createSearchIncludingDerivedDataTypes(dataTypeEntry).performSearch()
				.size();
		if (useCount > 1) {
			status.addWarning(
					MessageFormat.format(Messages.AddStructMemberRefactoring_MultipleUsesWarning, Integer.valueOf(useCount)));
		}
	}

	@Override
	public RefactoringStatus checkFinalConditions(final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = checkInitialConditions(pm);
		if (!status.hasFatalError()) {
			status.merge(validateConfiguration(configuration));
		}
		return status;
	}

	public RefactoringStatus setConfiguration(final AddStructMemberConfiguration newConfiguration) {
		configuration = Objects.requireNonNull(newConfiguration);
		return validateConfiguration(configuration);
	}

	private RefactoringStatus validateConfiguration(final AddStructMemberConfiguration candidate) {
		final RefactoringStatus status = new RefactoringStatus();
		final StructuredType structType = StructMemberRefactoringSupport.getStructType(context.getStructTypeURI());
		if (structType == null) {
			status.addFatalError(Messages.AddStructMemberRefactoring_InvalidContext);
			return status;
		}

		AddStructMemberModelEdit.validateConfiguration(structType, candidate, status);
		if (!context.getConnectionArraySize().equals(candidate.arraySize())) {
			status.addFatalError(Messages.AddStructMemberRefactoring_InvalidContext);
		}

		final DataType memberType = StructMemberRefactoringSupport.resolveDataType(structType.getTypeLibrary(),
				candidate.memberTypeName());
		if (StructMemberRefactoringSupport.isConcreteType(memberType)
				&& !StructMemberRefactoringSupport.createsRecursiveType(structType, memberType)
				&& !isCompatibleConnectionType(memberType)) {
			status.addFatalError(Messages.AddStructMemberRefactoring_IncompatibleType);
		}
		return status;
	}

	public boolean isCompatibleMemberType(final DataType candidate) {
		if (!StructMemberRefactoringSupport.isConcreteType(candidate)) {
			return false;
		}
		final StructuredType structType = StructMemberRefactoringSupport.getStructType(context.getStructTypeURI());
		if (structType == null || StructMemberRefactoringSupport.createsRecursiveType(structType, candidate)) {
			return false;
		}
		return isCompatibleConnectionType(candidate);
	}

	private boolean isCompatibleConnectionType(final DataType candidate) {
		final DataType connectionType = StructMemberRefactoringSupport.resolveDataType(context.getTypeLibrary(),
				context.getConnectionTypeName());
		return connectionType != null && (context.isTypeSelectionRequired()
				? connectionType.isAssignableFrom(candidate)
				: context.getConnectionTypeName().equals(PackageNameHelper.getFullTypeName(candidate)));
	}

	public AddStructMemberConfiguration getConfiguration() {
		return configuration;
	}

	public boolean isTypeSelectionRequired() {
		return context.isTypeSelectionRequired();
	}

	public TypeLibrary getTypeLibrary() {
		return context.getTypeLibrary();
	}

	public List<String> getMemberNames() {
		final StructuredType structType = StructMemberRefactoringSupport.getStructType(context.getStructTypeURI());
		return structType == null ? List.of()
				: structType.getMemberVariables().stream().map(member -> member.getName()).toList();
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final CommandCompositeChange result = new CommandCompositeChange(Messages.AddStructMemberRefactoring_ChangeName);
		addChange(result, ModelEditChange.fromModelEdits(Messages.AddStructMemberRefactoring_ChangeName,
				List.of(new AddStructMemberModelEdit(context.getStructTypeURI(), configuration))));
		addChange(result, ModelEditChange.fromModelEdits(Messages.AddStructMemberRefactoring_ChangeName,
				List.of(new ConnectAddedStructMemberModelEdit(context, configuration.memberName()))));
		pm.done();
		return result;
	}

	private static void addChange(final CompositeChange parent, final CompositeChange child) {
		if (child != null) {
			parent.add(child);
		}
	}
}
