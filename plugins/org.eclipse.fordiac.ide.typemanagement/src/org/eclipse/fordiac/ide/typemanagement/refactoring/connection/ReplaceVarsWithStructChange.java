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
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ReplaceVarsWithStructCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class ReplaceVarsWithStructChange extends AbstractCommandChange<FBType> {
	private final Collection<String> vars;
	final URI structURI;
	private final String name;
	private final boolean isInput;
	private final int position;

	protected ReplaceVarsWithStructChange(final URI elementURI, final Class<FBType> elementClass,
			final Collection<String> vars, final URI structURI, final String name, final boolean isInput,
			final int position) {
		super(elementURI.lastSegment() + ": Replace " + (isInput ? "Inputs" : "Outputs ") + vars + " with new Struct "
				+ structURI.trimFileExtension().lastSegment(), elementURI, elementClass);
		this.vars = Objects.requireNonNull(vars);
		this.structURI = Objects.requireNonNull(structURI);
		this.name = Objects.requireNonNull(name);
		this.isInput = isInput;
		this.position = position;
	}

	@Override
	public void initializeValidationData(final FBType element, final IProgressMonitor pm) {
		// TODO Auto-generated method stub

	}

	@Override
	public RefactoringStatus isValid(final FBType element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command createCommand(final FBType element) {
		if (TypeLibraryManager.INSTANCE.getTypeEntryForURI(structURI)
				.getType() instanceof final StructuredType struct) {
			return new ReplaceVarsWithStructCommand(vars, struct, name, element.getInterfaceList(), isInput, position);
		}
		// TODO: handling maybe move to is Valid
		return null;
	}

}
