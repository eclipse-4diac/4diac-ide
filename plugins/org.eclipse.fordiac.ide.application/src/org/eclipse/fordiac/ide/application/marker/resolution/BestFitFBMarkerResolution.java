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
 *   Patrick Aigner
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.marker.resolution;

import java.text.MessageFormat;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateInternalFBCommand;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.Image;

public class BestFitFBMarkerResolution extends AbstractCommandMarkerResolution<FBNetworkElement> {

	private final TypeEntry selectedEntry;

	public BestFitFBMarkerResolution(final IMarker marker, final TypeEntry entry) {
		super(marker, FBNetworkElement.class);
		selectedEntry = entry;
	}

	@Override
	protected boolean prepare(final IMarker[] markers, final IProgressMonitor monitor) throws CoreException {
		return true;
	}

	@Override
	protected Command createCommand(final FBNetworkElement element, final IProgressMonitor monitor)
			throws CoreException {
		return switch (element) {
		case final FB fb when fb.eContainer() instanceof final BaseFBType base && base.getInternalFbs().contains(fb) ->
			new UpdateInternalFBCommand(fb, selectedEntry);
		default -> new UpdateFBTypeCommand(element, selectedEntry);
		};
	}

	@Override
	public String getDescription() {
		return MessageFormat.format(FordiacMessages.Repair_Dialog_BestFitFBType, selectedEntry.getFullTypeName());
	}

	@Override
	public String getLabel() {
		return MessageFormat.format(FordiacMessages.Repair_Dialog_BestFitFBType, selectedEntry.getFullTypeName());
	}

	@Override
	public Image getImage() {
		return null;
	}

	public static Stream<BestFitFBMarkerResolution> createResolutions(final IMarker marker) {
		final String typeName = FordiacErrorMarker.getData(marker)[0];
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(marker.getResource().getProject());

		return typeLibrary.findUnqualified(typeName).stream().filter(FBTypeEntry.class::isInstance)
				.map(FBTypeEntry.class::cast).map(entry -> new BestFitFBMarkerResolution(marker, entry));
	}
}
