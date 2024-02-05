/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Objects;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class RenameElementChange extends Change {

	private final String name;
	private final URI elementURI;
	private final String newName;
	private String oldName;
	private ChangeNameCommand command;

	public RenameElementChange(final String name, final URI elementURI, final String newName) {
		this.name = name;
		this.elementURI = elementURI;
		this.newName = newName;
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		final INamedElement element = getTargetElement();
		if (element != null) {
			oldName = element.getName();
		}
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		final INamedElement element = getTargetElement();
		if (element == null) {
			status.addFatalError(Messages.RenameElementChange_NoSuchElement);
		} else if (!Objects.equals(element.getName(), oldName)) {
			status.addFatalError(Messages.RenameElementChange_NameChanged);
		} else if (!getCommand(element).canExecute()) {
			status.addFatalError(Messages.RenameElementChange_CannotExecuteCommand);
		}
		return status;
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		final INamedElement element = getTargetElement();
		if (element != null) {
			if (oldName == null) {
				oldName = element.getName();
			}
			ChangeExecutionHelper.executeChange(getCommand(element), element, pm);
			return new RenameElementChange(name, elementURI, oldName);
		}
		return null;
	}

	protected Command getCommand(final INamedElement element) {
		if (command == null) {
			command = ChangeNameCommand.forName(element, newName);
		}
		return command;
	}

	protected INamedElement getTargetElement() {
		final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(elementURI);
		if (entry != null) {
			final LibraryElement libraryElement = entry.getTypeEditable();
			if (libraryElement != null && libraryElement.eResource()
					.getEObject(elementURI.fragment()) instanceof final INamedElement element) {
				return element;
			}
		}
		return null;
	}

	@Override
	public IFile getModifiedElement() {
		if (elementURI.isPlatformResource()) {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(elementURI.toPlatformString(true)));
		}
		return null;
	}

	@Override
	public Object[] getAffectedObjects() {
		final IFile modifiedElement = getModifiedElement();
		if (modifiedElement != null) {
			return new Object[] { modifiedElement };
		}
		return super.getAffectedObjects();
	}

	@Override
	public String getName() {
		return name;
	}
}
