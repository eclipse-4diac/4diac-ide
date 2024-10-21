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
 *   Mario Kastner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement.refactoring.move;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.commands.change.ChangePackageNameCommand;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.fordiac.ide.typemanagement.wizards.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class MoveTypeChange extends AbstractCommandChange<LibraryElement> {

	private final String newPackageName;
	private String oldPackageName;

	protected MoveTypeChange(final String newPackageName, final String name, final URI uri) {
		super(name, uri, LibraryElement.class);
		this.newPackageName = newPackageName;
	}

	@Override
	public void initializeValidationData(final LibraryElement element, final IProgressMonitor pm) {
		oldPackageName = PackageNameHelper.getPackageName(element);
	}

	@Override
	public RefactoringStatus isValid(final LibraryElement element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (!Objects.equals(PackageNameHelper.getPackageName(element), oldPackageName)) {
			status.addFatalError(Messages.MoveTypeToPackage_NameChanged);
		}
		if (oldPackageName.equals(newPackageName)) {
			status.addWarning(Messages.MoveTypeToPackage_PackageNameIsTheSame);
		}
		final Optional<String> errorMessage = IdentifierVerifier.verifyPackageName(newPackageName);
		if (errorMessage.isPresent()) {
			status.addFatalError(errorMessage.get());
		}
		return status;
	}

	@Override
	protected Command createCommand(final LibraryElement element) {
		return new ChangePackageNameCommand(element, newPackageName);
	}
}
