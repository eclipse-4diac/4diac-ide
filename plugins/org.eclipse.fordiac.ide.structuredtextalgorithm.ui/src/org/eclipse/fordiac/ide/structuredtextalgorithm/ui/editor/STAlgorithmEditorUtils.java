/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

public final class STAlgorithmEditorUtils {

	public static IEditorPart open(final URI uri, final boolean activate) {
		final IEditorInput editorInput = createEditorInput(uri);
		final IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			return IDE.openEditor(activePage, editorInput, getEditorId(uri), activate);
		} catch (final PartInitException partInitException) {
			FordiacLogHelper.logError("Error while opening editor part for " + uri, partInitException); //$NON-NLS-1$
		}
		return null;
	}

	public static void executeCommand(final URI uri, final Command command) {
		final IEditorPart editor = open(uri, false);
		if (editor != null) {
			final CommandStack commandStack = editor.getAdapter(CommandStack.class);
			if (commandStack != null) {
				commandStack.execute(command);
			}
		}
	}

	public static IEditorInput createEditorInput(final URI uri) {
		if (uri.isPlatformResource()) {
			return new FileEditorInput(
					ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true))));
		}
		if (uri.isFile()) {
			return new FileEditorInput(ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toFileString())));
		}
		return null;
	}

	public static String getEditorId(final URI uri) throws PartInitException, OperationCanceledException {
		final IEditorDescriptor editorDescriptor = getEditorDescriptor(uri);
		if (editorDescriptor != null) {
			return editorDescriptor.getId();
		}
		return null;
	}

	public static IEditorDescriptor getEditorDescriptor(final URI uri)
			throws PartInitException, OperationCanceledException {
		if (uri.isPlatformResource()) {
			return IDE.getEditorDescriptor(
					ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true))), true,
					false);
		}
		if (uri.isFile()) {
			return IDE.getEditorDescriptor(
					ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toFileString())), true, false);
		}
		return null;
	}

	private STAlgorithmEditorUtils() {
		throw new UnsupportedOperationException();
	}
}
