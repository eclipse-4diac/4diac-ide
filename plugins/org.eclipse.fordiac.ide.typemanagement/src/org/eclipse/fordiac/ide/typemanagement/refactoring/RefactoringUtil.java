/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.ui.PlatformUI;

public final class RefactoringUtil {

	public static void saveAllAndBuild() throws InvocationTargetException, InterruptedException {
		PlatformUI.getWorkbench().saveAllEditors(false);
		PlatformUI.getWorkbench().getProgressService().busyCursorWhile(RefactoringUtil::waitForBuild);
	}

	private static void waitForBuild(final IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		try {
			final SubMonitor progress = SubMonitor.convert(monitor, 10);
			ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.INCREMENTAL_BUILD, progress.split(8));
			Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, progress.split(2));
		} catch (final OperationCanceledException | CoreException e) {
			throw new InvocationTargetException(e);
		}
	}

	public static boolean containsTypeEntryFile(final IResource resource) {
		try {
			if (containsTypeEntry(resource)) {
				return true;
			}
		} catch (final CoreException e) {
			return false;
		}
		return false;
	}

	private static boolean containsTypeEntry(final IResource resource) throws CoreException {
		if (resource instanceof final IFile file) {
			if (TypeLibraryManager.INSTANCE.getTypeEntryForFile(file) != null) {
				return true;
			}
		} else if (resource instanceof final IContainer container) {
			for (final IResource member : container.members()) {
				if (containsTypeEntry(member)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	private RefactoringUtil() {
		throw new UnsupportedOperationException();
	}
}
