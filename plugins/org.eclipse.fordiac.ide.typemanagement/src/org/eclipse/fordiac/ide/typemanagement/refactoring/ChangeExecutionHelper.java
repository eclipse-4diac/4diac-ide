/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *   Patrick Aigner
 *     - extracted method into this helper class
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public final class ChangeExecutionHelper {
	private ChangeExecutionHelper() {
		throw new UnsupportedOperationException();
	}

	public static void executeChange(final Command cmd, final EObject modelObj, final IProgressMonitor pm) {
		final EObject rootContainer = EcoreUtil.getRootContainer(EcoreUtil.getRootContainer(modelObj));
		if (rootContainer instanceof final LibraryElement elem) {
			Display.getDefault().syncExec(() -> {
				final TypeEntry entry = elem.getTypeEntry();
				final IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.findEditor(new FileEditorInput(entry.getFile()));
				if (editor == null) {
					cmd.execute();
					try {
						entry.save(pm);
					} catch (final CoreException e) {
						FordiacLogHelper.logError(e.getMessage(), e);
					}
				} else {
					editor.getAdapter(CommandStack.class).execute(cmd);
					editor.doSave(pm);
				}
			});
		}
	}
}
