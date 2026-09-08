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

import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public final class RefactoringUtil {

	public static void saveAllAndBuild() throws InvocationTargetException, InterruptedException {
		checkDirtyEditors();
		PlatformUI.getWorkbench().saveAllEditors(false);
		PlatformUI.getWorkbench().getProgressService().busyCursorWhile(RefactoringUtil::waitForBuild);
	}

	public static void saveAllAndBuild(final IProject project) throws InvocationTargetException, InterruptedException {
		checkDirtyEditors();
		PlatformUI.getWorkbench().saveAllEditors(false);
		PlatformUI.getWorkbench().getProgressService()
				.busyCursorWhile(monitor -> waitForProjectBuild(project, monitor));
	}

	public static void checkDirtyEditors() throws OperationCanceledException {
		if (Display.getCurrent() != null && hasDirtyEditors() && !promptSaveAll()) {
			throw new OperationCanceledException();
		}
	}

	private static boolean hasDirtyEditors() {
		for (final IWorkbenchWindow window : PlatformUI.getWorkbench().getWorkbenchWindows()) {
			for (final IWorkbenchPage page : window.getPages()) {
				for (final IEditorReference editorReference : page.getEditorReferences()) {
					final IEditorPart editor = editorReference.getEditor(false);
					if (editor != null && editor.isDirty()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static boolean promptSaveAll() {
		return MessageDialog.open(MessageDialog.CONFIRM,
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), Messages.RefactoringUtil_SaveAllTitle,
				Messages.RefactoringUtil_SaveAllMessage, SWT.NONE, Messages.RefactoringUtil_SaveAllButton,
				IDialogConstants.CANCEL_LABEL) == 0;
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

	private static void waitForProjectBuild(final IProject project, final IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		try {
			final SubMonitor progress = SubMonitor.convert(monitor, 10);
			ResourcesPlugin.getWorkspace().build(new IBuildConfiguration[] { project.getActiveBuildConfig() },
					IncrementalProjectBuilder.INCREMENTAL_BUILD, true, progress.split(8));
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
