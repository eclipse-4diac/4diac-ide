/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dario Romano - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateUntypedSubAppInterfaceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.swt.widgets.Display;

public class UpdateInstancesChange extends Change {

	private final Collection<FBNetworkElement> instances;
	private final DataTypeEntry dataTypeEntry;

	public UpdateInstancesChange(final Collection<FBNetworkElement> instances, final DataTypeEntry dataTypeEntry) {
		this.instances = instances;
		this.dataTypeEntry = dataTypeEntry;
	}

	public UpdateInstancesChange(final FBNetworkElement instance, final DataTypeEntry dataTypeEntry) {
		this(new ArrayList<>(), dataTypeEntry);
		this.instances.add(instance);
	}

	@Override
	public String getName() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Update FB instances - ");//$NON-NLS-1$
		for (final FBNetworkElement fb : instances) {
			sb.append(fb.getTypeName());
			sb.append(" - ");//$NON-NLS-1$
			sb.append(fb.getName());
		}
		return sb.toString();
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		// can't think of a way to validate an update
	}

	private Command getUpdateInstancesCommand() {
		final List<Command> commands = new ArrayList<>();
		instances.stream().forEach(instance -> {
			if (instance instanceof final SubApp subApp && !subApp.isTyped()) {
				commands.add(new UpdateUntypedSubAppInterfaceCommand(instance, dataTypeEntry));
			} else {
				commands.add(new UpdateFBTypeCommand(instance, instance.getTypeEntry()));
			}
		});
		Command cmd = new CompoundCommand();
		for (final Command subCmd : commands) {
			cmd = cmd.chain(subCmd);
		}
		return cmd;
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		final Command cmd = getUpdateInstancesCommand();
		Display.getDefault().asyncExec(cmd::execute);
		return null;
	}

	@Override
	public Object getModifiedElement() {
		return null;
	}

}
