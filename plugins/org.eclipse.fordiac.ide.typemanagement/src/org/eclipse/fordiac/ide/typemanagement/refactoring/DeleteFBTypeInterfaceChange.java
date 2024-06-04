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
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

public class DeleteFBTypeInterfaceChange extends CompositeChange implements IFordiacPreviewChange {

	final FBType type;
	final StructuredType struct;

	private final EnumSet<ChangeState> state = EnumSet.noneOf(ChangeState.class);

	public DeleteFBTypeInterfaceChange(final FBType type, final StructuredType struct) {
		super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_DeleteFBTypeInterface, type.getName()));
		this.type = type;
		this.struct = struct;
		this.state.addAll(getDefaultSelection());
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		// @formatter:off
		final List<VarDeclaration> varDeclaration = type.getInterfaceList().getAllInterfaceElements().stream()
				.filter(VarDeclaration.class::isInstance)
				.map(VarDeclaration.class::cast)
				.filter(decl -> decl.getType().getName().equals(struct.getName())).toList();
		// @formatter:on

		for (final VarDeclaration varDec : varDeclaration) {
			final ErrorMarkerDataType markerType = LibraryElementFactory.eINSTANCE.createErrorMarkerDataType();
			markerType.setName(varDec.getTypeName());

			Command cmd = ChangeDataTypeCommand.forDataType(varDec, markerType);

			// TODO this has to be added to the update changes
			if (state.contains(ChangeState.CHANGE_TO_ANY)) {
				cmd = ChangeDataTypeCommand.forDataType(varDec, IecTypes.GenericTypes.ANY_STRUCT);
			}

			if (state.contains(ChangeState.DELETE)) {
				cmd = new DeleteInterfaceCommand(varDec);
			}

			ChangeExecutionHelper.executeChange(cmd, type, pm);
		}
		return super.perform(pm);
	}

	public FBType getFBType() {
		return type;
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
		return EnumSet.of(ChangeState.CHANGE_TO_ANY, ChangeState.DELETE);
	}

	@Override
	public EnumSet<ChangeState> getDefaultSelection() {
		return EnumSet.of(ChangeState.CHANGE_TO_ANY);
	}
}