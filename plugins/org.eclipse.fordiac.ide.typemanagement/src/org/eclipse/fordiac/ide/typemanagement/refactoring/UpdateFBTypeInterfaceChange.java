/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *   Michael Oberlehner - outsourced inner class to own file
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class UpdateFBTypeInterfaceChange extends AbstractCommandChange<FBType> implements IFordiacPreviewChange {

	final StructuredType struct;

	private final EnumSet<ChangeState> state = EnumSet.noneOf(ChangeState.class);

	public UpdateFBTypeInterfaceChange(final FBType type, final StructuredType struct) {
		super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_DeleteFBTypeInterface, type.getName(),
				struct.getName()), EcoreUtil.getURI(type), FBType.class);
		this.struct = struct;
		this.state.addAll(getDefaultSelection());
	}

	@Override
	public EnumSet<ChangeState> getState() {
		return state;
	}

	@Override
	public void addState(final ChangeState newState) {
		state.add(newState);
	}

	@Override
	public EnumSet<ChangeState> getAllowedChoices() {
		return EnumSet.of(ChangeState.CHANGE_TO_ANY, ChangeState.DELETE, ChangeState.NO_CHANGE);
	}

	@Override
	public EnumSet<ChangeState> getDefaultSelection() {
		return EnumSet.of(ChangeState.NO_CHANGE);
	}

	@Override
	public void initializeValidationData(final FBType element, final IProgressMonitor pm) {
		// nothing to do here
	}

	@Override
	public RefactoringStatus isValid(final FBType element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();

		final List<VarDeclaration> varDeclaration = getVarDeclarationsForStruct(element);

		if (varDeclaration.isEmpty()) {
			status.addError(struct.getQualifiedName() + " is not part of the interface of " + getName());
		}

		if (element.getTypeLibrary() == null || element.getTypeLibrary().getDataTypeLibrary() == null) {
			status.addError("Type Library is null");
		}

		return status;
	}

	@Override
	protected Command createCommand(final FBType type) {

		final List<VarDeclaration> varDeclarations = getVarDeclarationsForStruct(type);
		final CompoundCommand cmd = new CompoundCommand();

		for (final VarDeclaration varDec : varDeclarations) {

			if (state.contains(ChangeState.CHANGE_TO_ANY)) {
				cmd.add(ChangeDataTypeCommand.forDataType(varDec, IecTypes.GenericTypes.ANY_STRUCT));
			} else if (state.contains(ChangeState.DELETE)) {
				cmd.add(new DeleteInterfaceCommand(varDec));
			} else {
				final ErrorMarkerDataType markerType = type.getTypeLibrary().getDataTypeLibrary()
						.createErrorMarkerType(varDec.getTypeName(), "");
				cmd.add(ChangeDataTypeCommand.forDataType(varDec, markerType));
			}
		}

		return cmd;
	}

	private List<VarDeclaration> getVarDeclarationsForStruct(final FBType element) {
		return element.getInterfaceList().getAllInterfaceElements().stream().filter(VarDeclaration.class::isInstance)
				.map(VarDeclaration.class::cast).filter(decl -> PackageNameHelper.getFullTypeName(decl.getType())
						.equalsIgnoreCase(PackageNameHelper.getFullTypeName(struct)))
				.toList();
	}
}