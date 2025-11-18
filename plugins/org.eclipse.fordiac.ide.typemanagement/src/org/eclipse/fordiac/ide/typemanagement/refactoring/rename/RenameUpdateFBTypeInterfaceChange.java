/*******************************************************************************
 * Copyright (c) 2023, 2025 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.typemanagement.refactoring.rename;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class RenameUpdateFBTypeInterfaceChange extends AbstractCommandChange<FBType> {

	private final List<String> affectedVarNames = new ArrayList<>();
	private final String oldTypeName;
	private final String newTypeName;
	private final String packageName;

	public RenameUpdateFBTypeInterfaceChange(final FBType type, final String oldTypeName, final String newTypeName,
			final String packageName) {
		super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_DeleteFBTypeInterface, type.getName(),
				oldTypeName), EcoreUtil.getURI(type), FBType.class);
		this.oldTypeName = oldTypeName;
		this.newTypeName = newTypeName;
		this.packageName = packageName;
	}

	@Override
	public void initializeValidationData(final FBType element, final IProgressMonitor pm) {
		// nothing to do here
	}

	@Override
	public RefactoringStatus isValid(final FBType element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		affectedVarNames.clear();

		final String oldQualName = packageName + PackageNameHelper.PACKAGE_NAME_DELIMITER + oldTypeName;
		element.getInterfaceList().getAllInterfaceElements()
				.filter(elem -> elem instanceof final VarDeclaration vd
						&& (packageName + PackageNameHelper.PACKAGE_NAME_DELIMITER + vd.getType().getName())
								.equals(oldQualName))
				.forEach(ie -> affectedVarNames.add(ie.getName()));

		if (affectedVarNames.isEmpty()) {
			status.addError(oldTypeName + " is not part of the interface of " + getName());
		}

		if (element.getTypeLibrary() == null || element.getTypeLibrary().getDataTypeLibrary() == null) {
			status.addError("Type Library is null");
		}

		return status;
	}

	@Override
	protected Command createCommand(final FBType type) {
		if (affectedVarNames.isEmpty()) {
			return new Command() {
				// return empty Command, a CompoundCommand with zero entries is considered "not
				// executable", causing issues in AbstractCommandChange.performCommand(...)
			};
		}

		final CompoundCommand cmd = new CompoundCommand();

		for (final String varName : affectedVarNames) {
			final IInterfaceElement iie = type.getInterfaceList().getInterfaceElement(List.of(varName));
			if (iie instanceof final VarDeclaration vd) {
				cmd.add(ChangeDataTypeCommand.forTypeName(vd, newTypeName));
			}
		}

		return cmd;
	}
}
