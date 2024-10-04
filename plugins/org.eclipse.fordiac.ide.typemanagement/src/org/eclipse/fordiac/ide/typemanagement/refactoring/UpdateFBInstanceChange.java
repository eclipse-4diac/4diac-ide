/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University
 *					  Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dario Romano - initial API and implementation and/or initial documentation
 *   Fabio Gandolfi - added FB type update
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ConfigureFBCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateInternalFBCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateUntypedSubAppInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class UpdateFBInstanceChange extends AbstractCommandChange<FBNetworkElement> {

	private final TypeEntry typeEntry;

	public UpdateFBInstanceChange(final FBNetworkElement instance, final TypeEntry typeEntry) {
		super(("Update FB instances - " + FBNetworkHelper.getFullHierarchicalName(instance)),
				EcoreUtil.getURI(instance), FBNetworkElement.class);
		this.typeEntry = typeEntry;

	}

	@Override
	public void initializeValidationData(final FBNetworkElement element, final IProgressMonitor pm) {
		// no additional ValidationData needed
	}

	@Override
	public RefactoringStatus isValid(final FBNetworkElement element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {

		final RefactoringStatus status = new RefactoringStatus();
		if (element.eContainer() == null) {
			status.addError(element.getQualifiedName() + " eContainer is null"); //$NON-NLS-1$
		}
		return status;
	}

	@Override
	protected Command createCommand(final FBNetworkElement element) {

		if (element instanceof final SubApp subApp && !subApp.isTyped()
				&& typeEntry instanceof final DataTypeEntry dtEntry) {
			return new UpdateUntypedSubAppInterfaceCommand(element, dtEntry);
		}

		if (element instanceof final StructManipulator muxer
				&& typeEntry.getType() instanceof final StructuredType structuredType) {
			return new ChangeStructCommand(muxer, structuredType);
		}

		if (element instanceof final ConfigurableFB confFb
				&& typeEntry.getType() instanceof final StructuredType structuredType) {
			return new ConfigureFBCommand(confFb, structuredType);
		}

		if (element.eContainer() instanceof BaseFBType && element instanceof final FB fb) {
			return new UpdateInternalFBCommand(fb, typeEntry);
		}

		return new UpdateFBTypeCommand(element, typeEntry);
	}

}
