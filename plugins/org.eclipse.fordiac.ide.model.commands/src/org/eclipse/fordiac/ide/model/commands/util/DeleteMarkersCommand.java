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
package org.eclipse.fordiac.ide.model.commands.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class DeleteMarkersCommand extends AbstractMarkersCommand {

	public DeleteMarkersCommand(final String label, final List<IMarker> markers) {
		super(label, markers, new ArrayList<>());
	}

	@Override
	protected void doExecute(final IProgressMonitor monitor) throws CoreException {
		deleteMarkers();
	}

	@Override
	protected void doRedo(final IProgressMonitor monitor) throws CoreException {
		deleteMarkers();
	}

	@Override
	protected void doUndo(final IProgressMonitor monitor) throws CoreException {
		createMarkers();
	}
}
