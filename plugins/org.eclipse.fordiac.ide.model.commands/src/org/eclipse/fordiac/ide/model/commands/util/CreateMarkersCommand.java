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
import java.util.Objects;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;

public class CreateMarkersCommand extends AbstractMarkersCommand {

	private final List<ErrorMarkerBuilder> markerBuilders;

	public CreateMarkersCommand(final String label, final List<ErrorMarkerBuilder> markerBuilders) {
		super(label, new ArrayList<>(), new ArrayList<>());
		this.markerBuilders = markerBuilders;
	}

	@Override
	protected void doExecute(final IProgressMonitor monitor) throws CoreException {
		markerBuilders.stream().map(CreateMarkersCommand::toMarkerDescription).filter(Objects::nonNull)
				.forEachOrdered(getMarkerDescriptions()::add);
		createMarkers();
	}

	@Override
	protected void doRedo(final IProgressMonitor monitor) throws CoreException {
		createMarkers();
	}

	@Override
	protected void doUndo(final IProgressMonitor monitor) throws CoreException {
		deleteMarkers();
	}

	protected static MarkerDescription toMarkerDescription(final ErrorMarkerBuilder builder) {
		final IResource resource = FordiacMarkerHelper.getResource(builder.getTarget());
		if (resource != null) {
			return new MarkerDescription(resource, builder.getType(), builder.getAttributes());
		}
		return null;
	}
}
