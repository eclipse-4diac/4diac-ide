/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * Contributors:
 *  Michael Oberlehner
 *    - initial API and implementation and/or initial documentation
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ConfigureFBCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class UpdateConfigurableFBModelEdit extends ModelEdit<ConfigurableFB> {

	private final DataTypeEntry typeEntry;

	public UpdateConfigurableFBModelEdit(final ConfigurableFB instance, final DataTypeEntry typeEntry) {
		super(MessageFormat.format(Messages.UpdateFBInstances, FBNetworkHelper.getFullHierarchicalName(instance)),
				EcoreUtil.getURI(instance), ConfigurableFB.class);
		this.typeEntry = typeEntry;
	}

	@Override
	public void initializeValidationData(final ConfigurableFB element, final IProgressMonitor pm) {
		// no additional ValidationData needed
	}

	@Override
	public RefactoringStatus isValid(final ConfigurableFB element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (element.eContainer() == null) {
			status.addFatalError(element.getQualifiedName() + " eContainer is null"); //$NON-NLS-1$
		}
		return status;
	}

	@Override
	protected Command createCommand(final ConfigurableFB element) {
		if (element instanceof final StructManipulator structManipulator) {
			return new ChangeStructCommand(structManipulator, typeEntry.getType());
		}

		return new ConfigureFBCommand(element, typeEntry.getType());
	}
}
