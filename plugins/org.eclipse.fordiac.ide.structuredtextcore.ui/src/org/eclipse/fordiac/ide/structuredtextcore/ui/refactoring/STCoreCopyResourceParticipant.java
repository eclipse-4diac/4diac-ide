/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.CopyArguments;
import org.eclipse.ltk.core.refactoring.participants.CopyParticipant;
import org.eclipse.ltk.core.refactoring.participants.ISharableParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.eclipse.xtext.ide.refactoring.ResourceRelocationContext;
import org.eclipse.xtext.ui.refactoring.ui.SyncUtil;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STCoreCopyResourceParticipant extends CopyParticipant implements ISharableParticipant {

	@Inject
	private SyncUtil syncUtil;

	@Inject
	private STCoreResourceRelocationProcessor processor;

	private Change change;

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {
		change = processor.createChange(getName(), ResourceRelocationContext.ChangeType.COPY, pm);
		return processor.getIssues().getRefactoringStatus();
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return change;
	}

	@Override
	public String getName() {
		return Messages.STCoreCopyResourceParticipant_Name;
	}

	@Override
	protected boolean initialize(final Object element) {
		try {
			syncUtil.totalSync(true, true, false);
		} catch (final InvocationTargetException e) {
			return false;
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			return false;
		}
		addElement(element, getArguments());
		return true;
	}

	@Override
	public void addElement(final Object element, final RefactoringArguments arguments) {
		if (element instanceof final IResource resource && arguments instanceof final CopyArguments copyArguments
				&& copyArguments.getDestination() instanceof final IContainer destinationContainer) {
			String newName = copyArguments.getExecutionLog().getNewName(resource);
			if (newName == null) {
				newName = resource.getName();
			}
			final IFile destinationFile = destinationContainer.getFile(new Path(newName));
			processor.addChangedResource(resource, resource.getFullPath(), destinationFile.getFullPath());
		}
	}
}
