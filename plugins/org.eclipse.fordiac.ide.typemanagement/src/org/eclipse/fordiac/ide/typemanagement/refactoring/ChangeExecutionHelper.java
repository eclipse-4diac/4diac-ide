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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.search.LiveSearchTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.widgets.Display;

/**
 * @deprecated Inherit from {@link AbstractCommandChange} instead.
 */
@Deprecated(forRemoval = true)
public final class ChangeExecutionHelper {
	private ChangeExecutionHelper() {
		throw new UnsupportedOperationException();
	}

	public static void executeChange(final Command cmd, final EObject modelObj, final IProgressMonitor pm) {
		final EObject rootContainer = EcoreUtil.getRootContainer(EcoreUtil.getRootContainer(modelObj));

		if (rootContainer instanceof final LibraryElement elem) {
			Display.getDefault().syncExec(() -> {
				final TypeEntry entry = elem.getTypeEntry();
				final LiveSearchTypeEntry liveEntry = new LiveSearchTypeEntry(entry);
				liveEntry.execute(cmd);
				liveEntry.safe();
			});
		}
	}
}
