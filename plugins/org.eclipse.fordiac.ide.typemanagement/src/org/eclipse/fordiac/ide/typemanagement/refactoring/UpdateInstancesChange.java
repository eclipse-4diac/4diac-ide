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

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateInternalFBCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateUntypedSubAppInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class UpdateInstancesChange extends Change {

	private final FBNetworkElement instance;
	private final TypeEntry typeEntry;

	public UpdateInstancesChange(final FBNetworkElement instance, final TypeEntry typeEntry) {
		this.instance = instance;
		this.typeEntry = typeEntry;
	}

	public UpdateInstancesChange(final FBNetworkElement instance) {
		this(instance, null);
	}

	@Override
	public String getName() {
		return "Update FB instance: " + instance.getName(); //$NON-NLS-1$
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		// can't think of a way to validate an update
	}

	private Command getCommand(final FBNetworkElement instance) {
		if (instance instanceof final SubApp subApp && !subApp.isTyped()) {
			return new UpdateUntypedSubAppInterfaceCommand(instance, (DataTypeEntry) typeEntry);
		}

		if (instance instanceof final StructManipulator muxer) {
			final LibraryElement structuredType = typeEntry.getTypeEditable();
			Assert.isTrue(structuredType instanceof StructuredType);
			return new ChangeStructCommand(muxer, (StructuredType) structuredType);
		}
		if (instance.eContainer() instanceof BaseFBType && instance instanceof final FB fb) {
			return new UpdateInternalFBCommand(fb, typeEntry);
		}

		return new UpdateFBTypeCommand(instance, typeEntry);
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		ChangeExecutionHelper.executeChange(getCommand(instance), instance, pm);
		return null;
	}

	@Override
	public Object getModifiedElement() {
		return null;
	}

}
