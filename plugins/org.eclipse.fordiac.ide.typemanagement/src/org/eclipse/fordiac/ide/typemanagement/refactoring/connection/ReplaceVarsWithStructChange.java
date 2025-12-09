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
 *   Mathias Garstenauer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.connection;

import java.util.Collection;
import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.connection.commands.ReplaceVarsWithStructCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 * A Change which represents replacing Vars of a FB with a structured type,
 * using
 * {@link org.eclipse.fordiac.ide.typemanagement.refactoring.connection.commands.ReplaceVarsWithStructCommand
 * ReplaceVarsWithStructCommand}
 *
 */
public class ReplaceVarsWithStructChange extends AbstractCommandChange<FBType> {
	private final Collection<String> vars;
	final URI structURI;
	private final String name;
	private final boolean isInput;
	private final int position;

	/**
	 * Creates a new Instance
	 *
	 * @param elementURI URI of the FBType
	 * @param vars       Names of the Variables
	 * @param structURI  Struct of the new In- or Output
	 * @param name       Name of the new In/Output
	 * @param isInput    Whether the Variables to be deleted as well as the new
	 *                   In/Output is an Input
	 * @param position   Position in the respective List
	 */
	protected ReplaceVarsWithStructChange(final URI elementURI, final Collection<String> vars, final URI structURI,
			final String name, final boolean isInput, final int position) {
		super(elementURI.trimFileExtension().lastSegment() + Messages.ReplaceVarsWithStructChange_Replace
				+ (isInput ? Messages.ReplaceVarsWithStructChange_Inputs : Messages.ReplaceVarsWithStructChange_Outputs)
				+ vars + Messages.ReplaceVarsWithStructChange_Struct + structURI.trimFileExtension().lastSegment(),
				elementURI, FBType.class);
		this.vars = Objects.requireNonNull(vars);
		this.structURI = Objects.requireNonNull(structURI);
		this.name = Objects.requireNonNull(name);
		this.isInput = isInput;
		this.position = position;
	}

	@Override
	public void initializeValidationData(final FBType element, final IProgressMonitor pm) {
		// no additional ValidationData needed
	}

	@Override
	public RefactoringStatus isValid(final FBType element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		vars.forEach(varName -> {
			if (isInput && element.getInterfaceList().getInput(varName) == null
					|| !isInput && element.getInterfaceList().getOutput(varName) == null) {
				status.merge(RefactoringStatus.createFatalErrorStatus(
						varName + Messages.ReplaceVarsWithStructChange_NotContained + this.getElementURI()));
			}
		});
		return status;
	}

	@Override
	protected Command createCommand(final FBType element) {
		if (TypeLibraryManager.INSTANCE.getTypeEntryForURI(structURI)
				.getType() instanceof final StructuredType struct) {
			return new ReplaceVarsWithStructCommand(vars, struct, name, element.getInterfaceList(), isInput, position);
		}
		return null;
	}

}
