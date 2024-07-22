/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.delete;

import java.text.MessageFormat;
import java.util.EnumSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.IFordiacPreviewChange;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class UpdateUntypedSubappPinChange extends AbstractCommandChange<VarDeclaration>
		implements IFordiacPreviewChange {
	private final EnumSet<ChangeState> state = EnumSet.noneOf(ChangeState.class);

	public UpdateUntypedSubappPinChange(final VarDeclaration varDecl) {
		super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_DeleteSubappPins, varDecl.getName(),
				getSubappName(varDecl)), EcoreUtil.getURI(varDecl), VarDeclaration.class);
		this.state.addAll(getDefaultSelection());
	}

	private static String getSubappName(final VarDeclaration varDecl) {
		return ((SubApp) varDecl.eContainer().eContainer()).getQualifiedName();
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
		return EnumSet.of(ChangeState.DELETE, ChangeState.CHANGE_TO_ANY, ChangeState.NO_CHANGE);
	}

	@Override
	public EnumSet<ChangeState> getDefaultSelection() {
		return EnumSet.of(ChangeState.NO_CHANGE);
	}

	@Override
	public void initializeValidationData(final VarDeclaration element, final IProgressMonitor pm) {

	}

	@Override
	protected Command createCommand(final VarDeclaration varDecl) {
		if (state.contains(ChangeState.DELETE)) {
			return new DeleteSubAppInterfaceElementCommand(varDecl);

		}
		if (state.contains(ChangeState.CHANGE_TO_ANY)) {
			return ChangeDataTypeCommand.forDataType(varDecl, IecTypes.GenericTypes.ANY_STRUCT);
		}

		if (state.contains(ChangeState.NO_CHANGE)) {
			return ChangeDataTypeCommand.forDataType(varDecl, varDecl.getType());
		}
		return null;
	}

	@Override
	public RefactoringStatus isValid(final VarDeclaration element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (!(element.eContainer() instanceof UntypedSubApp)) {
			status.addFatalError(Messages.UpdateUntypedSubappPinChange_0);
		}
		return status;
	}
}